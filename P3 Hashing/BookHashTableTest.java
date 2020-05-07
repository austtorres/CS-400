//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Program 3 Hash Table
// Files: Book.java, BookHashTableTest.java, BookHashTable.java, BookParser.java
// Course: Fall 2019
//
// Authors: Austin Torres, Deb Deppeler
// Email: artorres3@wisc.edu
// Lecturer's Name: Deb Deppeler
// Lecture Number: 001
//
// Description of Program: This program tests the implementation of
// BookHashTable.java
//
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////

import org.junit.After;
import java.io.FileNotFoundException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test HashTable class implementation to ensure that required functionality
 * works for all cases.
 */
public class BookHashTableTest {

  // Default name of books data file or try books_clean.csv
  public static final String BOOKS = "books_clean.csv";

  // Empty hash tables that can be used by tests
  static BookHashTable bookObject;
  static ArrayList<Book> bookTable;

  static final int INIT_CAPACITY = 2;
  static final double LOAD_FACTOR_THRESHOLD = 0.49;

  static Random RNG = new Random(0); // seeded to make results repeatable
                                     // (deterministic)

  /** Create a large array of keys and matching values for use in any test */
  @BeforeAll
  public static void beforeClass() throws Exception {
    bookTable = BookParser.parse(BOOKS);
  }

  /** Initialize empty hash table to be used in each test */
  @BeforeEach
  public void setUp() throws Exception {
    bookObject = new BookHashTable(INIT_CAPACITY, LOAD_FACTOR_THRESHOLD);
  }

  /** Not much to do, just make sure that variables are reset */
  @AfterEach
  public void tearDown() throws Exception {
    bookObject = null;
  }

  private void insertMany(ArrayList<Book> bookTable, int x)
      throws IllegalNullKeyException, DuplicateKeyException {
    for (int i = 0; i < bookTable.size(); i++) {
      bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));
    }
  }

  /**
   * IMPLEMENTED AS EXAMPLE FOR YOU Tests that a HashTable is empty upon
   * initialization
   */
  @Test
  public void test000_collision_scheme() {
    if (bookObject == null)
      fail("Gg");
    int scheme = bookObject.getCollisionResolutionScheme();
    if (scheme < 1 || scheme > 9)
      fail("collision resolution must be indicated with 1-9");
  }


  /**
   * IMPLEMENTED AS EXAMPLE FOR YOU Tests that a HashTable is empty upon
   * initialization
   */
  @Test
  public void test000_IsEmpty() {
    // "size with 0 entries:"
    assertEquals(0, bookObject.numKeys());
  }

  /**
   * IMPLEMENTED AS EXAMPLE FOR YOU Tests that a HashTable is not empty after
   * adding one (key,book) pair
   * 
   * @throws DuplicateKeyException
   * @throws IllegalNullKeyException
   */
  @Test
  public void test001_IsNotEmpty()
      throws IllegalNullKeyException, DuplicateKeyException {
    bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
    String expected = "" + 1;
    // "size with one entry:"
    assertEquals(expected, "" + bookObject.numKeys());
  }

  /**
   * IMPLEMENTED AS EXAMPLE FOR YOU Test if the hash table will be resized after
   * adding two (key,book) pairs given the load factor is 0.49 and initial
   * capacity to be 2.
   */

  @Test
  public void test002_Resize()
      throws IllegalNullKeyException, DuplicateKeyException {
    bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
    int cap1 = bookObject.getCapacity();
    bookObject.insert(bookTable.get(1).getKey(), bookTable.get(1));
    int cap2 = bookObject.getCapacity();

    // "size with one entry:"
    assertTrue(cap2 > cap1 & cap1 == 2);
  }


  @Test
  public void test003_get_empty_table() {
    try {
      bookObject.get(bookTable.get(0).getKey());
      fail("Get should not work for an empty table");
    } catch (IllegalNullKeyException e) {
      fail("No IllegalNullKeyException should be thrown");
    } catch (KeyNotFoundException e) {
    }
  }

  /**
   * Test insert and remove methods by checking the value of numKeys after each
   * method is called.
   */
  @Test
  public void test004_insert_then_remove() {
    try {
      // Insert one
      bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
      if (bookObject.numKeys() != 1) {
        fail("Failed to insert, numKeys was not increased");
      }
      bookObject.remove(bookTable.get(0).getKey());
      if (bookObject.numKeys() != 0) {
        fail("Key was not removed correctly, numKeys was not decremented");
      }
    } catch (DuplicateKeyException e) {
      fail("Duplicate key exception should not be thrown");
    } catch (IllegalNullKeyException e) {
      fail("Illegal null key exception should not be thrown");
    }
  }

  /**
   * Test that get method works for an inserted book
   * 
   * @throws IllegalNullKeyException
   * @throws DuplicateKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void test005_get_after_insert() throws IllegalNullKeyException,
      DuplicateKeyException, KeyNotFoundException {
    bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
    if (!bookObject.get(bookTable.get(0).getKey()).equals(bookTable.get(0))) {
      fail("Book was inserted but get method did not find it");
    }
  }


  /**
   * Test that removing a null key throws an IllegalNullKeyException
   * 
   * @throws IllegalNullKeyException when key is null
   */
  @Test
  public void test006_remove_null_key() throws IllegalNullKeyException {
    try {
      bookObject.remove(null);
      fail("IllegalNullKeyException should have been thrown");
    } catch (IllegalNullKeyException e) {
    }
  }


  /**
   * Tests that removing a key from the table returns the correct boolean
   * 
   * @throws IllegalNullKeyException when null key is removed
   * @throws DuplicateKeyException   when duplicate key inserted
   */
  @Test
  public void test007_removing_returns_true()
      throws IllegalNullKeyException, DuplicateKeyException {
    bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
    if (bookObject.remove(bookTable.get(1).getKey())) {
      fail("Removing key 1 should return false");
    }
    if (!bookObject.remove(bookTable.get(0).getKey())) {
      fail("Failed to remove key 0");
    }
  }

  /**
   * @throws IllegalNullKeyException
   * @throws DuplicateKeyException
   * @throws KeyNotFoundException
   */
  @Test
  public void test008_get_after_remove() throws IllegalNullKeyException,
      DuplicateKeyException, KeyNotFoundException {
    bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
    bookObject.remove(bookTable.get(0).getKey());
    try {
      bookObject.get(bookTable.get(0).getKey());
      fail("Get should not work on a removed book");
    } catch (KeyNotFoundException e) {
      // pass
    }
  }

  /**
   * Test that a key can be inserted, removed, and then inserted again without
   * throwing an exception
   * 
   * @throws IllegalNullKeyException if key is null
   * @throws DuplicateKeyException   if key is a duplicate in table
   */
  @Test
  public void test009_inserting_removed_key()
      throws IllegalNullKeyException, DuplicateKeyException {
    bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
    bookObject.remove(bookTable.get(0).getKey());
    try {
      bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
    } catch (DuplicateKeyException e) {
      fail("Should not throw duplicate key exception");
    }
  }
}

