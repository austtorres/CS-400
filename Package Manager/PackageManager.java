////////////////////////////////////////////////////////////////////////////////
//
// Assignment: P4 Package Manager
//
// Files: CycleException.java, Graph.java, Package.java,
// PackageNotFoundException.java, GraphADT.java, PackageManager.java
// Semester: CS 354 Fall 2019
//
// Author: Austin Torres
// Email: artorres3@wisc.edu
// CS Login: atorres
//
// Known Bugs: None
//
/////////////////////////// OTHER SOURCES OF HELP //////////////////////////////
// fully acknowledge and credit all sources of help,
// other than Instructors and TAs.
//
// Persons: Identify persons by name, relationship to you, and email.
// Describe in detail the the ideas and help they provided.
//
// Online sources: avoid web searches to solve your problems, but if you do
// search, be sure to include Web URLs and description of
// of any information you find.
//
// Parsing with Json
// https://www.geeksforgeeks.org/parse-json-java/
//
// Where I got the idea to use a stack to track cycles
// https://www.geeksforgeeks.org/detect-cycle-undirected-graph/
//
// Using labels in loops
// https://stackoverflow.com/questions/5670051/java-multi-level-break
// https://howtodoinjava.com/java/basics/labeled-statements-in-java/
//
// Depth first search algorithm (already familiar with it from AI class)
// https://www.geeksforgeeks.org/depth-first-search-or-dfs-for-a-graph/
//////////////////////////// 80 columns wide ///////////////////////////////////
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.io.FileReader;
import java.util.Stack;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Filename: PackageManager.java Project: p4 Authors: Austin Torres
 * 
 * PackageManager is used to process json package dependency files and provide
 * function that make that information available to other users.
 * 
 * Each package that depends upon other packages has its own entry in the json
 * file.
 * 
 * Package dependencies are important when building software, as you must
 * install packages in an order such that each package is installed after all of
 * the packages that it depends on have been installed.
 * 
 * For example: package A depends upon package B, then package B must be
 * installed before package A.
 * 
 * This program will read package information and provide information about the
 * packages that must be installed before any given package can be installed.
 * all of the packages in
 * 
 * You may add a main method, but we will test all methods with our own Test
 * classes.
 */
public class PackageManager {

  private Graph graph;

  /**
   * Package Manager default no-argument constructor.
   */
  public PackageManager() {
    graph = new Graph();
  }

  /**
   * Takes in a file path for a json file and builds the package dependency
   * graph from it.
   * 
   * @param jsonFilepath the name of json data file with package dependency
   *                     information
   * @throws FileNotFoundException if file path is incorrect
   * @throws IOException           if the give file cannot be read
   * @throws ParseException        if the given json cannot be parsed
   */
  public void constructGraph(String jsonFilepath)
      throws FileNotFoundException, IOException, ParseException {

    // Passes file name into new JSONParser object
    Object obj = new JSONParser().parse(new FileReader(jsonFilepath));

    // Creates a new JSON Object from the JSON Parser Object
    JSONObject jo = (JSONObject) obj;

    // Creates a new JSON Array from the packages
    JSONArray packages = (JSONArray) jo.get("packages");

    // Loop through packages
    for (int i = 0; i < packages.size(); i++) {

      // Creates a Json object for each object
      JSONObject currPackage = (JSONObject) packages.get(i);

      // Maintains current package
      String packageName = (String) currPackage.get("name");

      // Add vertex to graph
      graph.addVertex(packageName);

      // Creates json array with required dependencies
      JSONArray dependencies = (JSONArray) currPackage.get("dependencies");

      // Adds the dependencies to the graph
      for (int j = 0; j < dependencies.size(); j++) {
        // Holds the current dependency
        String dep = (String) dependencies.get(j);

        // Add vertex to graph
        graph.addVertex(dep);

        // Add directed edge to graph
        graph.addEdge(packageName, dep);
      }
    }
  }

  /**
   * Return a set of all available packages which were obtained from the json
   * file. Each vertex is a package.
   * 
   * @return Set<String> of all the packages
   */
  public Set<String> getAllPackages() {
    return graph.getAllVertices();
  }

  /**
   * Given a package name, returns a list of packages in a valid installation
   * order.
   * 
   * Valid installation order means that each package is listed before any
   * packages that depend upon that package.
   * 
   * @return List<String>, order in which the packages have to be installed
   * 
   * @throws CycleException           if you encounter a cycle in the graph
   *                                  while finding the installation order for a
   *                                  particular package. Tip: Cycles in some
   *                                  other part of the graph that do not affect
   *                                  the installation order for the specified
   *                                  package, should not throw this exception.
   * 
   * @throws PackageNotFoundException if the package passed does not exist in
   *                                  the dependency graph.
   */
  public List<String> getInstallationOrder(String pkg)
      throws CycleException, PackageNotFoundException {

    // Determine if package is in graph
    if (!getAllPackages().contains(pkg)) {
      throw new PackageNotFoundException();
    }

    // Use stack to track cycles
    Stack<String> stackCycles = new Stack<String>();

    // topologically ordered list
    List<String> order = new ArrayList<String>();

    // Track visited vertices to detect cycles
    List<String> visited = new ArrayList<String>();

    // Maintain stack and list of visited vertices
    stackCycles.push(pkg);
    visited.add(pkg);

    // Loop through stack
    outerloop: while (!stackCycles.isEmpty()) {

      // Peek at top of stack
      String curr = stackCycles.peek();

      // Get adjacent vertices
      List<String> adjVert = graph.getAdjacentVerticesOf(curr);

      // Loop through adjacent vertices, keeping track of current vertex
      for (int i = 0; i < adjVert.size(); i++) {

        // Keeps track of the current vertex
        String vertex = adjVert.get(i);

        // Maintain stack and list if vertex is not visited
        if (!visited.contains(vertex)) {
          stackCycles.push(vertex);
          visited.add(vertex);

          // Keep chugging along
          continue outerloop;

          // Vertex already in stack (cycle)
        } else if (stackCycles.contains(vertex)) {
          throw new CycleException();
        }
      }

      // Item on top of stack goes to the ordered list
      order.add(stackCycles.pop());
    }

    // Return ordered list
    return order;
  }



  /**
   * Given two packages - one to be installed and the other installed, return a
   * List of the packages that need to be newly installed.
   * 
   * For example, refer to shared_dependecies.json - toInstall("A","B") If
   * package A needs to be installed and packageB is already installed, return
   * the list ["A", "C"] since D will have been installed when B was previously
   * installed.
   * 
   * @return List<String>, packages that need to be newly installed.
   * 
   * @throws CycleException           if you encounter a cycle in the graph
   *                                  while finding the dependencies of the
   *                                  given packages. If there is a cycle in
   *                                  some other part of the graph that doesn't
   *                                  affect the parsing of these dependencies,
   *                                  cycle exception should not be thrown.
   * 
   * @throws PackageNotFoundException if any of the packages passed do not exist
   *                                  in the dependency graph.
   */
  public List<String> toInstall(String newPkg, String installedPkg)
      throws CycleException, PackageNotFoundException {

    // If either package is not in the graph
    if (!(getAllPackages().contains(newPkg)
        && getAllPackages().contains(installedPkg))) {
      throw new PackageNotFoundException();
    }

    // Uses lists and stack to maintain visited nodes and detect cycles (again)
    Stack<String> stack = new Stack<String>();
    List<String> visited = new ArrayList<String>();
    List<String> toInstall = new ArrayList<String>();


    // Maintain list and stack using passed in package
    visited.add(installedPkg);
    stack.push(installedPkg);

    // Loop through stack
    while (!stack.isEmpty()) {

      // get current vertex
      String currentVertex = stack.pop();
      List<String> adjVert = graph.getAdjacentVerticesOf(currentVertex);

      // Loop through adjacent vertices
      for (int i = 0; i < adjVert.size(); i++) {

        // Unvisited vertices
        if (!visited.contains(adjVert.get(i))) {

          // Maintain stack and list
          stack.push(adjVert.get(i));
          visited.add(adjVert.get(i));
        }
      }
    }

    // Recursive depth first search
    depthFirstSearch(newPkg, visited, stack, toInstall);

    return toInstall;
  }

  /**
   * Return a valid global installation order of all the packages in the
   * dependency graph.
   * 
   * assumes: no package has been installed and you are required to install all
   * the packages
   * 
   * returns a valid installation order that will not violate any dependencies
   * 
   * @return List<String>, order in which all the packages have to be installed
   * @throws CycleException if you encounter a cycle in the graph
   */
  public List<String> getInstallationOrderForAllPackages()
      throws CycleException {

    // Stack and lists to maintain order, cycles, and visited vertices
    Stack<String> stack = new Stack<String>();
    List<String> visited = new ArrayList<String>();
    List<String> order = new ArrayList<String>();

    // All vertices stored in a sorted array list
    List<String> allVertices = new ArrayList<String>(getAllPackages());
    allVertices.sort(String.CASE_INSENSITIVE_ORDER);


    // Loop through all vertices using DFS
    for (String vertex : allVertices) {
      List<String> firstDFS = new ArrayList<String>();
      Stack<String> stackDFS = new Stack<String>();
      List<String> orderDFS = new ArrayList<String>();

      // Perform DFS
      depthFirstSearch(vertex, firstDFS, stackDFS, orderDFS);
    }

    // Maintain adjacent vertices
    List<List<String>> adjList = new ArrayList<List<String>>();

    // Add adj vertices to list
    for (int i = 0; i < allVertices.size(); i++) {
      adjList.add(graph.getAdjacentVerticesOf(allVertices.get(i)));
    }

    // Iterate through vertices
    outerloop: for (int i = 0; i < allVertices.size(); i++) {

      // Track current vertex
      String vertex = allVertices.get(i);

      // Loop through adjacent vertices
      for (int j = 0; j < adjList.size(); j++) {

        // If vertex is in list
        if (adjList.get(j).contains(vertex)) {
          continue outerloop;
        }

      }

      // Maintain stack and list
      stack.push(vertex);
      visited.add(vertex);
    }

    // Loop through stack
    outerloop: while (!stack.isEmpty()) {

      // get adjacent vertices of vertex at top of stack
      List<String> adjVert = graph.getAdjacentVerticesOf(stack.peek());

      // Loop through adjacent vertices
      for (int i = 0; i < adjVert.size(); i++) {

        // Keeps track of the current adjacent vertex
        String vertex = adjVert.get(i);

        // Checks if the current vertex has been visited
        if (!visited.contains(vertex)) {

          // Add to stack
          stack.push(vertex);
          visited.add(vertex);
          continue outerloop;
        }
      }

      // Add top of stack to ordered list
      order.add(stack.pop());
    }

    return order;
  }

  /**
   * Find and return the name of the package with the maximum number of
   * dependencies.
   * 
   * Tip: it's not just the number of dependencies given in the json file. The
   * number of dependencies includes the dependencies of its dependencies. But,
   * if a package is listed in multiple places, it is only counted once.
   * 
   * Example: if A depends on B and C, and B depends on C, and C depends on D.
   * Then, A has 3 dependencies - B,C and D.
   * 
   * @return String, name of the package with most dependencies.
   * @throws CycleException if you encounter a cycle in the graph
   */
  public String getPackageWithMaxDependencies() throws CycleException {

    // List of all packages
    List<String> allPackages = new ArrayList<String>(getAllPackages());

    // Number of dependencies
    int dep = -1;

    // Keeps track of the package with the most dependencies
    String maxDep = "";

    // Iterates through all of the packages
    for (int i = 0; i < allPackages.size(); i++) {

      try {

        // Keeps track of the current package
        String currentPackage = allPackages.get(i);

        // Number of packages
        int numPaks = getInstallationOrder(currentPackage).size();

        // If the num is greater than the value change max
        if (numPaks > dep) {
          dep = numPaks;
          maxDep = currentPackage;
        }
        // Exception if numPaks is not found
      } catch (PackageNotFoundException e) {
        e.printStackTrace();
      }
    }

    // Package with most dependencies
    return maxDep;
  }


  /**
   * Perform depth first search (dfs) on graph using simple DFS algorithm
   * 
   * @param curr      current vertex
   * @param visited   visited vertex
   * @param stack     stack to detect cycles
   * @param topoOrder ordered list
   * @throws CycleException
   */
  private void depthFirstSearch(String curr, List<String> visited,
      Stack<String> stackCycles, List<String> order) throws CycleException {

    // If vertex is in the stack
    if (stackCycles.contains(curr)) {
      throw new CycleException();
    }

    // If vertex is unvisited
    if (!visited.contains(curr)) {

      // Maintain stack and ordered list
      stackCycles.push(curr);

      visited.add(curr);

      order.add(curr);
    } else {
      return;
    }

    // Get vertices adjacent to vertex on top of stack
    List<String> adjVert = graph.getAdjacentVerticesOf(stackCycles.peek());
    for (int i = 0; i < adjVert.size(); i++) {

      // Recursive depth first search
      depthFirstSearch(adjVert.get(i), visited, stackCycles, order);
    }

    // Pop vertex off of stack
    stackCycles.pop();
  }

  public static void main(String[] args) {
    System.out.println("PackageManager.main()");
  }

}
