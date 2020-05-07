// Project Title: P1 TestADT
//
// Description: A series of tests to ensure proper functionality of a data
// structure to which I do not possess the implementation of.
//
// Files: DS_My.java, TestDS_My.java, DataStructureADTTest.java
// Course: CS400 Fall 2020
//
// Author: Austin Torres
// Email: artorres3@wisc.edu
// Lecturer's Name: Deb Deppeler
// Lecture: 001
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Austin Torres This class performs test on ADTs without knowing their
 *         implementations
 *
 * @param <T>
 */
abstract class DataStructureADTTest<T extends DataStructureADT<String, String>> {

  private T dataStructureInstance;

  protected abstract T createInstance();

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
  }

  @AfterAll
  static void tearDownAfterClass() throws Exception {
  }

  @BeforeEach
  void setUp() throws Exception {
    dataStructureInstance = createInstance();
  }

  @AfterEach
  void tearDown() throws Exception {
    dataStructureInstance = null;
  }


  /**
   * Test to confirm that the size is 0 when empty. If not, a message is
   * displayed indicating the current non-zero size.
   */
  @Test
  void test00_empty_ds_size() {
    if (dataStructureInstance.size() != 0)
      fail("data structure should be empty, with size = 0, but size = "
          + dataStructureInstance.size());
  }

  /**
   * Test to confirm that the size is 1 when insert is called one time. If not,
   * a message is displayed indicating the current size.
   */
  @Test
  void test01_after_insert_one_size_is_one() {
    dataStructureInstance.insert("key01", "01");
    if (dataStructureInstance.size() != 1) {
      fail("data structure size should be equal to 1, but instead size = "
          + dataStructureInstance.size());
    }
  }

  /**
   * This test confirms that once insert is called once, the size is now 1.
   * Then, when remove is called, the size should be 0. If this does not happen,
   * a message is displayed indicating the current size.
   */
  @Test
  void test02_after_insert_one_remove_one_size_is_0() {
    dataStructureInstance.insert("key02", "02");
    if (dataStructureInstance.size() != 1) {
      fail("data structure size should be equal to 1 after insert was called, "
          + "but instead size = " + dataStructureInstance.size());
    } else
      dataStructureInstance.remove("key02");
    if (dataStructureInstance.size() != 0) {
      fail("data structure size should be equal to 0, but instead size = "
          + dataStructureInstance.size());
    }
  }

  /**
   * Inserts a few key,value pairs such that one of them has the same key as an
   * earlier one. Confirms that a RuntimeException is thrown.
   */
  @Test
  void test03_duplicate_exception_is_thrown() {
    dataStructureInstance.insert("key01", "01");
    dataStructureInstance.insert("key02", "02");
    dataStructureInstance.insert("key03", "02"); // Duplicate values are okay
    dataStructureInstance.insert("key04", "05");
    try {
      dataStructureInstance.insert("key02", "04"); // Duplicate keys should not
                                                   // be allowed
      fail(
          "A duplicate key has been inserted and should have thrown a RuntimeException");
    } catch (RuntimeException i) {
    }
  }

  /**
   * Confirms that remove returns false when key does not exist
   */
  @Test
  void test04_remove_returns_false_when_key_not_present() {
    dataStructureInstance.insert("key01", "01");
    boolean successfulRemoval = dataStructureInstance.remove("key02");
    if (successfulRemoval != false) {
      fail("Remove should have returned false as the key02 was never inserted");
    }
  }

  /**
   * Tests that the contains method returns true when checking for a key that
   * has been inserted
   */
  @Test
  void test05_insert_one_check_contains() {
    dataStructureInstance.insert("key01", "01");
    if (dataStructureInstance.contains("key01") != true) {
      fail("key01 was inserted but contains('key01') returned false");
    }
  }

  /**
   * This test checks that the data structure does not contained a key that was
   * removed
   */
  @Test
  void test06_add_then_remove_check_contains() {
    dataStructureInstance.insert("key01", "01");
    dataStructureInstance.insert("key02", "02");
    dataStructureInstance.remove("key02");
    if (dataStructureInstance.contains("key02") != false) {
      fail("contains method returns true for an element that was removed");
    }
  }

  /**
   * Tests that an exception is thrown when attempting to insert a null key
   */
  @Test
  void test07_insert_null_key() {
    try {
      dataStructureInstance.insert(null, "01"); // Key is null while value isn't
      fail("Exception should have been thrown after inserting a null key");
    } catch (IllegalArgumentException i) {
    }
  }

  /**
   * Tests that an exception is thrown when attempting to remove a null key
   */
  @Test
  void test08_remove_null_key() {
    try {
      dataStructureInstance.remove(null); // removed key is null
      fail("Exception should have been thrown when removing a null key");
    } catch (IllegalArgumentException i) {
    }
  }

  /**
   * Confirms that get method should throw an exception when attempting to get a
   * null key
   */
  @Test
  void test09_get_null_key() {
    try {
      dataStructureInstance.get(null);
      fail("Exception should have been thrown when getting a null key");
    } catch (IllegalArgumentException i) {
    }
  }

  /**
   * Tests that the size is 5 after inserting 3, removing 2, and then inserting
   * 4 to the data structure
   */
  @Test
  void test10_insert_3_remove_2_insert_4_check_size() {
    // Inserting 3 elements to empty data structure
    dataStructureInstance.insert("key01", "01");
    dataStructureInstance.insert("key02", "02");
    dataStructureInstance.insert("key03", "03");
    if (dataStructureInstance.size() != 3) { // size should be 3
      fail("After inserting 3, the size should be 3 but is "
          + dataStructureInstance.size());
    } else {
      // remove two elements from data structure
      dataStructureInstance.remove("key01");
      dataStructureInstance.remove("key03");
      if (dataStructureInstance.size() != 1) { // size should be 1
        fail("Removing 2 from 3 should make the size 1, but it is "
            + dataStructureInstance.size());
      } else {
        // Inserting 4 elements to data structure of size 1
        dataStructureInstance.insert("key05", "05");
        dataStructureInstance.insert("key06", "06");
        dataStructureInstance.insert("key07", "07");
        dataStructureInstance.insert("key08", "07");
        if (dataStructureInstance.size() != 5) { // size should be 5
          fail(
              "Inserting 4 to a data structure of size 2 should equal a size of 5, but instead it is "
                  + dataStructureInstance.size());
        }
      }
    }
  }

  /**
   * Checks that contains is true for an inserted element
   */
  @Test
  void test11_insert_one_check_contains() {
    dataStructureInstance.insert("key01", "01");
    if (dataStructureInstance.contains("key01") != true) {
      fail("Data structure should contain key01 but contains is false");
    }
  }

  /**
   * This test adds 20 keys to the data structure and fails if size is not 20
   */
  @Test
  void test12_insert_many_check_size() {
    dataStructureInstance.insert("key01", "01");
    dataStructureInstance.insert("key02", "01");
    dataStructureInstance.insert("key03", "01");
    dataStructureInstance.insert("key04", "01");
    dataStructureInstance.insert("key05", "01");
    dataStructureInstance.insert("key06", "01");
    dataStructureInstance.insert("key07", "01");
    dataStructureInstance.insert("key08", "01");
    dataStructureInstance.insert("key09", "01");
    dataStructureInstance.insert("key10", "01");
    dataStructureInstance.insert("key11", "01");
    dataStructureInstance.insert("key12", "01");
    dataStructureInstance.insert("key13", "01");
    dataStructureInstance.insert("key14", "01");
    dataStructureInstance.insert("key15", "01");
    dataStructureInstance.insert("key16", "01");
    dataStructureInstance.insert("key17", "01");
    dataStructureInstance.insert("key18", "01");
    dataStructureInstance.insert("key19", "01");
    dataStructureInstance.insert("key20", "01");
    if (dataStructureInstance.size() != 20) {
      fail("Size should be 20 but is " + dataStructureInstance.size());
    }
  }

  /**
   * This test ensures that no duplicate errors occur when an element is removed
   * and then inserted again
   */
  @Test
  void test13_removed_duplicate_can_insert() {
    dataStructureInstance.insert("key01", "01");
    dataStructureInstance.remove("key01");
    try {
      dataStructureInstance.insert("key01", "01");
    } catch (RuntimeException i) {
      fail(
          "There should not be an issue with inserting an element that was once removed");
    }
  }
}
