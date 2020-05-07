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

import static org.junit.jupiter.api.Assertions.*;
import java.awt.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class GraphTest {

  Graph graph;
  Set<String> vertices;

  /**
   * Set up before class
   * 
   * @throws Exception if not properly set up
   */
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
  }

  /**
   * Set up empty graph each test
   * 
   * @throws Exception if not properly set up
   */
  @BeforeEach
  void setUp() throws Exception {
    graph = new Graph();
  }

  /**
   * Tear down variables after each run
   * 
   * @throws Exception if not properly torn down
   */
  @AfterEach
  void tearDown() throws Exception {
    graph = null;
    vertices = null;

  }

  /**
   * Tests the addEdge method to ensure edges are added when they should be and
   * are not added when they already exist or are invalid
   * 
   */
  @Test
  void test_add_edge() {

    // Valid vertices
    graph.addVertex("0");
    graph.addVertex("1");
    graph.addVertex("2");

    // Valid edges
    graph.addEdge("0", "1");
    graph.addEdge("0", "2");

    // Invalid edges
    graph.addEdge("0", "1");
    graph.addEdge(null, "1");
    graph.addEdge(null, null);
    graph.addEdge("0", "3");
    graph.addEdge("3", "3");

    if (graph.size() != 2) {
      fail("The number of edges should be 2 but is: " + graph.size());
    }
  }


  /**
   * Tests the removeEdge method to ensure that edges are removed when they
   * should be and are not removed when necessary
   * 
   */
  @Test
  void test_remove_edge() {

    // Valid vertices
    graph.addVertex("0");
    graph.addVertex("1");
    graph.addVertex("2");

    // Valid edges
    graph.addEdge("0", "1");
    graph.addEdge("0", "2");

    // Invalid edges
    graph.addEdge("0", "1");
    graph.addEdge(null, "1");
    graph.addEdge(null, null);
    graph.addEdge("0", "3");
    graph.addEdge("3", "3");

    if (graph.size() != 2) {
      fail("The number of edges should be 2 but is: " + graph.size());
    }

    // Valid removal
    graph.removeEdge("0", "1");

    if (graph.size() != 1) {
      fail("The number of edges should be 2 but is: " + graph.size());
    }

    // Invalid removal
    graph.removeEdge("0", "1");
    graph.removeEdge(null, null);
    graph.removeEdge("1", null);
    graph.removeEdge("3", "3");

    if (graph.size() != 1) {
      fail("The number of edges should be 2 but is: " + graph.size());
    }
  }



  /**
   * Add the same vertex multiple times. There should only be one vertex because
   * multiples are not allowed
   */
  @Test
  void test_insert_vertex_multiple_times() {
    graph.addVertex("0");
    graph.addVertex("0");
    graph.addVertex("0");
    vertices = graph.getAllVertices();
    if (vertices.size() != 1 || graph.order() != 1) {
      fail();
    }
  }



  /**
   * Null vertex should not be successfully inserted
   */
  @Test
  void test_insert_null_vertex() {
    graph.addVertex(null);
    vertices = graph.getAllVertices();
    if (vertices.size() != 0 || graph.order() != 0) {
      fail();
    }

  }

  /**
   * Test that removing vertices works correctly
   */
  @Test
  void test_remove_vertex() {
    graph.addVertex("0");
    graph.addVertex("1");
    graph.addVertex("2");

    // Valid removal
    graph.removeVertex("2");
    if (graph.order() != 2) {
      fail("The number of vertices should be 2 but is: " + graph.order());
    }

    // Invalid removals
    graph.removeVertex("2");
    graph.removeVertex("3");
    graph.removeVertex(null);
    if (graph.order() != 2) {
      fail("The number of vertices should be 2 but is: " + graph.order());
    }
  }

  /**
   * Tests to confirm that getAdjacentVerticesOf returns the valid adjacent
   * vertices
   * 
   */
  @Test
  void test_get_adjacent_verticies_of() {

    // valid vertices
    graph.addVertex("0");
    graph.addVertex("1");
    graph.addVertex("2");


    // valid edges
    graph.addEdge("0", "1");
    graph.addEdge("0", "2");


    if (graph.getAdjacentVerticesOf("0").size() != 2) {
      fail("Number of adjacent verticse should be 2 but is: "
          + graph.getAdjacentVerticesOf("1").size());
    }

    if (graph.getAdjacentVerticesOf("1").size() != 0) {
      fail(
          "The size of the adjacent verices list of 1 should be 0 but was actually: "
              + graph.getAdjacentVerticesOf("1").size());
    }
  }

  /**
   * Tests that getAllVertices returns list of vertices
   * 
   */
  @Test
  void test_get_all_verticies() {

    // Valid insertion
    for (int i = 0; i < 100; i++) {
      graph.addVertex("" + i);
    }

    if (graph.order() != 100) {
      fail(graph.order() + " improper number of vertices");
    }

    if (graph.getAllVertices().size() != 100) {
      fail("The number of vertices should be 100 but is: "
          + graph.getAllVertices().size());
    }
  }

}

