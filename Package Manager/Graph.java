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
//////////////////////////// 80 columns wide ///////////////////////////////////
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;



/**
 * Implement graph
 * 
 * @author Austin Torres
 *
 */
public class Graph implements GraphADT {
  // Track vertices and edges
  private int vertices, edges;

  // Array of linked lists used to maintain adjacency
  private LinkedList<String> adjArray[];

  /**
   * Default no-argument constructor
   */
  public Graph() {
    adjArray = new LinkedList[25]; // 26 Letters
  }


  /**
   * Standard array resizing. Make array with double the capacity and then copy
   * over all values to new array.
   */
  private void resize() {
    // Creates the new size of the array which is double the old size
    int newSize = adjArray.length * 2;

    // Creates the new Array of Linked Lists
    LinkedList<String> newAdjListArray[] = new LinkedList[newSize];

    // Iterates through the old array copying over the elements
    for (int i = 0; i < adjArray.length; i++) {
      newAdjListArray[i] = adjArray[i];
    }

    // Sets the old array to be the newly created and copied array
    adjArray = newAdjListArray;
  }

  /**
   * Add new vertex to the graph.
   *
   * If vertex is null or already exists, method ends without adding a vertex or
   * throwing an exception.
   * 
   * Valid argument conditions: 1. vertex is non-null 2. vertex is not already
   * in the graph
   */
  public void addVertex(String vertex) {

    // Vertex is null
    if (vertex == null) {
      return;
    }

    // Loop through list to see if vertex already exists
    for (int i = 0; i < vertices; i++) {
      if (adjArray[i].getFirst().equals(vertex)) {
        return;
      }
    }

    // Prevent adding out of bounds by resizing
    if (vertices == adjArray.length) {
      resize();
    }

    // Create a new vertex in the graph
    adjArray[vertices] = new LinkedList<String>();

    // Add vertex to linked list
    adjArray[vertices].add(vertex);

    // Maintain number of vertices
    vertices++;
  }

  /**
   * Remove a vertex and all associated edges from the graph.
   * 
   * If vertex is null or does not exist, method ends without removing a vertex,
   * edges, or throwing an exception.
   * 
   * Valid argument conditions: 1. vertex is non-null 2. vertex is not already
   * in the graph
   */
  public void removeVertex(String vertex) {
    if (vertex == null) {
      return;
    }

    // Initialize index to -1 to prevent bugs from accessing index at 0
    int index = -1;

    // Check if vertex is in list
    for (int i = 0; i < vertices; i++) {

      // Found vertex to remove
      if (adjArray[i].getFirst().equals(vertex)) {

        // Save index
        index = i;

        // Remove vertex from array
        adjArray[i] = null;

        // Break if not found
      }
    }

    // If index was changed, then that means removal was successful
    if (index >= 0) {
      // Loop through vertices and shift them down one
      shiftVertices(index);

      // maintain number of vertices
      vertices--;

      // Remove edges connected to the removed vertex
      for (int i = 0; i < vertices; i++) {
        if (adjArray[i].contains(vertex)) {
          adjArray[i].remove(vertex);

          // Maintain edge count
          edges--;
        }
      }
    }

    // Vertex not found
    return;
  }

  /**
   * Shifts all of the vertices down in the graph after removing one so there
   * are no gaps.
   * 
   * @param index - The index of the vertex that was removed
   */
  private void shiftVertices(int index) {
    // Iterates through the array and shifts all the vertices down 1 position
    for (int i = index; i < vertices - 1; i++) {
      adjArray[i] = adjArray[i + 1];
    }

    // Sets the last vertex that was shifted to null
    adjArray[vertices - 1] = null;
  }

  /**
   * Add the edge from vertex1 to vertex2 to this graph. (edge is directed and
   * unweighted) If either vertex does not exist, add vertex, and add edge, no
   * exception is thrown. If the edge exists in the graph, no edge is added and
   * no exception is thrown.
   * 
   * Valid argument conditions: 1. neither vertex is null 2. both vertices are
   * in the graph 3. the edge is not in the graph
   */
  public void addEdge(String vertex1, String vertex2) {

    // Null vertices end the run
    if (vertex1 == null || vertex2 == null) {
      return;
    }

    // Initialize index
    int index = 0;

    // Track if vertex pairs are valid
    boolean isValidVertex1 = false;
    boolean isValidVertex2 = false;

    // Ensure vertex exists and that the edge does not already exist from V1->V2
    for (int i = 0; i < vertices; i++) {

      // V1 exists
      if (vertex1.equals(adjArray[i].getFirst())) {

        // Edge doesn't exist
        if (!adjArray[i].contains(vertex2)) {

          // Vertex is valid
          isValidVertex1 = true;

          // Update index
          index = i;
        }
      }
      // Check that second vertex is valid
      if (vertex2.equals(adjArray[i].getFirst())) {
        isValidVertex2 = true;
      }
    }

    // If both vertices exist add edge
    if (isValidVertex1 && isValidVertex2) {
      // Add the edge to the Linked List at the specified index
      adjArray[index].add(vertex2);

      // Maintain edges
      edges++;
    }

    return;
  }

  /**
   * Remove the edge from vertex1 to vertex2 from this graph. (edge is directed
   * and unweighted) If either vertex does not exist, or if an edge from vertex1
   * to vertex2 does not exist, no edge is removed and no exception is thrown.
   * 
   * Valid argument conditions: 1. neither vertex is null 2. both vertices are
   * in the graph 3. the edge from vertex1 to vertex2 is in the graph
   */
  public void removeEdge(String vertex1, String vertex2) {

    // Null vertices end the run
    if (vertex1 == null || vertex2 == null) {
      return;
    }

    // Similar implementation to addEdge. Create index and boolean values
    int index = 0;
    boolean isValidVertex1 = false;
    boolean isValidVertex2 = false;

    // Loop through list to see if first vertex exists exist
    for (int i = 0; i < vertices; i++) {
      if (vertex1.equals(adjArray[i].getFirst())) {

        // If edge exists it is valid
        if (adjArray[i].contains(vertex2)) {
          isValidVertex1 = true;
          index = i;
        }
      }
      // Determine if second vertex is adjacent
      if (vertex2.equals(adjArray[i].getFirst())) {
        isValidVertex2 = true;
      }
    }

    // If both vertexes exist remove the directed edge from 1 to 2
    if (isValidVertex1 && isValidVertex2) {
      adjArray[index].remove(vertex2);

      // Maintain edge count
      edges--;
    }

    return;
  }

  /**
   * Returns a Set that contains all the vertices
   * 
   */
  public Set<String> getAllVertices() {

    // Create set to return
    Set<String> allVertices = new HashSet<String>();

    // Loop through list and add all vertices to set
    for (int i = 0; i < vertices; i++) {
      allVertices.add(adjArray[i].getFirst());
    }

    return allVertices;
  }

  /**
   * Get all the neighbor (adjacent) vertices of a vertex
   *
   */
  public List<String> getAdjacentVerticesOf(String vertex) {

    // Create list of adjacent vertices
    List<String> adjacentVertices = new ArrayList<String>();

    // No vertex exists
    if (vertex == null) {
      return adjacentVertices;
    }

    // Initialize index to -1 to avoid bugs like above
    int index = -1;

    // Loop through adj list to see if vertex exists
    for (int i = 0; i < vertices; i++) {
      if (adjArray[i].getFirst().equals(vertex)) {

        // Set index
        index = i;
      }
    }

    // Index was unchanged
    if (index == -1) {
      return adjacentVertices;
    }

    // Linked list of vertices adj to vertex at index
    LinkedList<String> adjListForVertex = adjArray[index];

    // Add all elements to list except for vertex itself
    for (int i = 1; i < adjListForVertex.size(); i++) {
      adjacentVertices.add(adjListForVertex.get(i));
    }

    return adjacentVertices;
  }

  /**
   * Returns the number of edges in this graph.
   */
  public int size() {
    return edges;// Edges were maintained throughout program
  }

  /**
   * Returns the number of vertices in this graph.
   */
  public int order() {
    return vertices; // Vertices were maintained throughout program
  }
}
