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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests all methods in PackageManager.java
 * 
 * @author Austin Torres
 *
 */
class PackageManagerTest {

  // Object to be used in tests
  private PackageManager packageManager;

  /**
   * Package Manager used in each test
   */
  @BeforeEach
  public void setUp() throws Exception {
    packageManager = new PackageManager();
  }

  /**
   * Tear down variables after each test
   */
  @AfterEach
  public void tearDown() throws Exception {
    packageManager = null;
  }



  // TESTS


  /**
   * Tests proper graph consruction and Json parsing
   * 
   */
  @Test
  void test_construct_graph() {
    try {
      packageManager.constructGraph("valid.json");
    } catch (IOException | ParseException e) {
      fail("Should not throw exception");
    }
  }

  /**
   * This tests the getInstallationOrderForAllPackages method to make sure it
   * returns the correct list of the Installation orders to be installed.
   * 
   */
  @Test
  void test_get_installation_order_for_all_packages() {
    ArrayList<String> installationOrder = null;
    try {
      packageManager.constructGraph("shared_dependencies.json");
      installationOrder = (ArrayList<String>) packageManager
          .getInstallationOrderForAllPackages();
    } catch (IOException | ParseException | CycleException e) {
      fail("Should not throw an exception");
      e.printStackTrace();
    }

    // Checks to make sure the correct packages are returned at each index and
    // are in the correct
    // order for the package dependencies.
    if (!installationOrder.get(0).equals("D")) {
      fail("The first item in the list should have been D");
    }

    if (!installationOrder.get(1).equals("B")) {
      fail("The first item in the list should have been B");
    }

    if (!installationOrder.get(2).equals("C")) {
      fail("The first item in the list should have been C");
    }

    if (!installationOrder.get(3).equals("A")) {
      fail("The first item in the list should have been A");
    }
  }

  /**
   * This tests the getPackageWithMaxDependencies method to make sure it returns
   * the package with the highest number of dependencies.
   * 
   */
  @Test
  void test_get_package_with_max_dependencies() {
    String maxDependencies = null;
    try {
      packageManager.constructGraph("valid.json");
      maxDependencies = packageManager.getPackageWithMaxDependencies();
    } catch (IOException | ParseException | CycleException e) {
      fail("Should not throw an exception");
      e.printStackTrace();
    }

  }


}
