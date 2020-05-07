////////////////////////////////////////////////////////////////////////////////
//
// Assignment: P4 Package Manager
// This File: PackageManager.java
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


/**
 * Filename: Package.java Project: p4 Authors: Debra Deppeler
 * 
 * Class representation of the package object found in a json file.
 * 
 * A package is a package name and an array of the names of other packages that
 * this package depends upon.
 * 
 */
public class Package {
  private String name;
  private String[] dependencies;

  public Package() {

  }

  public Package(String name, String[] dependencies) {
    this.name = name;
    this.dependencies = dependencies;
  }

  public String getName() {
    return this.name;
  }

  public String[] getDependencies() {
    return this.dependencies;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDependencies(String[] dependencies) {
    this.dependencies = dependencies;
  }
}
