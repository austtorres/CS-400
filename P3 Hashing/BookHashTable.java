//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Program 3 Hash Table
// Files: Book.java, BookHashTableTest.java, BookHashTable.java, BookParser.java
// Course: Fall 2019
//
// Author: Austin Torres
// Email: artorres3@wisc.edu
// Lecturer's Name: Deb Deppeler
// Lecture Number: 001
//
// Description of Program: This program implements a hashTable of Book objects
//
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

// Commented and completed HashTableADT implementation
//
// Implemented all required methods
// DO ADD REQUIRED PUBLIC METHODS TO IMPLEMENT interfaces
//
// DO NOT ADD ADDITIONAL PUBLIC MEMBERS TO YOUR CLASS
// (no public or package methods that are not in implemented interfaces)
//
// Describe the collision resolution scheme you have chosen
// identify your scheme as open addressing or bucket
//
// Bucket: Used array of ArrayLists
//
// Used abs(hashCode()) % capacity

/**
 * HashTable implementation that uses:
 * 
 * @param <K> unique comparable identifier for each <K,V> pair, may not be null
 * @param <V> associated value with a key, value may be null
 */
public class BookHashTable implements HashTableADT<String, Book> {

  /** The initial capacity that is used if none is specifed user */
  static final int DEFAULT_CAPACITY = 101;

  /** The load factor that is used if none is specified by user */
  static final double DEFAULT_LOAD_FACTOR_THRESHOLD = 0.75;

  private int capacity; // Current capacity

  private int numKeys; // Current number of keys

  private double loadFactor; // Current load factor

  private double loadFactorThreshold; // Current loadFactorThreshold

  private class hashNode {
    private String key;
    private Book value;

    /**
     * Inner class that creates key-value pairs
     * 
     * @param key:   Book ID
     * @param value: Book value
     */
    public hashNode(String key, Book value) {
      this.key = key;
      this.value = value;
    }
  }

  // The hashTable being used for this program
  private ArrayList<hashNode>[] hashTable;

  /**
   * REQUIRED default no-arg constructor Uses default capacity and sets load
   * factor threshold for the newly created hash table.
   */
  public BookHashTable() {
    this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR_THRESHOLD);
  }

  /**
   * Creates an empty hash table with the specified capacity and load factor.
   * 
   * @param initialCapacity     number of elements table should hold at start.
   * @param loadFactorThreshold the ratio of items/capacity that causes table to
   *                            resize and rehash
   */
  public BookHashTable(int initialCapacity, double loadFactorThreshold) {
    // Comment and complete a constructor that accepts initial capacity
    // and load factor threshold and initializes all fields

    // Sets current capacity to capacity given as a parameter
    capacity = initialCapacity;

    numKeys = 0; // At start, number of keys is zero

    loadFactor = 0; // With zero keys, LF = 0

    // Creates hashTable with given capacity
    hashTable = new ArrayList[initialCapacity];

    // Initialize loadFactorThreshold given by parameter
    this.loadFactorThreshold = loadFactorThreshold;
  }

  /**
   * This is the hashing function needed to return the hash index for a book
   * 
   * @param key: Book ID
   * @return Book's hash index
   */
  private int hashFunction(String key) {
    return Math.abs(key.hashCode() % capacity);
  }

  /**
   * Add key-value pair to data structure and increment keys. Throws exceptions
   * when duplicate or null key insertion is attempted
   * 
   * @param - key: book ID
   * @param - value: value of Book
   */
  @Override
  public void insert(String key, Book value)
      throws IllegalNullKeyException, DuplicateKeyException {

    // Handle null keys
    if (key == null) {
      throw new IllegalNullKeyException();
    }

    // Handle resizing before insertion
    if (loadFactor >= loadFactorThreshold) {

      // Increase table with odd size
      int newTS = (capacity * 2) + 1;

      // Create new hashTable
      ArrayList<hashNode>[] newHashTable = new ArrayList[newTS];

      // Loop through old hashTable and rehash it (resizing)
      for (int i = 0; i < hashTable.length; i++) {

        // index of hashTable could potentially be null or size could be 0. If
        // this happens, do not rehash
        if (hashTable[i] == null) {
          // Nothing to do
        } else if (hashTable[i].size() == 0) {
          // Nothing to do
        } else {

          // Loop to compute new hash index and copy book to new index
          for (int j = 0; j < hashTable[i].size(); j++) {

            // Create bucket if it does not exist and insert book into it
            // Uses the absolute value of the hash code % capacity
            if (newHashTable[Math.abs(hashTable[i].get(j).key.hashCode())
                % newTS] == null) {
              newHashTable[Math.abs(hashTable[i].get(j).key.hashCode())
                  % newTS] = new ArrayList<hashNode>();

              newHashTable[Math.abs(hashTable[i].get(j).key.hashCode()) % newTS]
                  .add(hashTable[i].get(j));

            } else {
              newHashTable[Math.abs(hashTable[i].get(j).key.hashCode()) % newTS]
                  .add(hashTable[i].get(j));
            }
          }

        }

      }

      // Increase hashTable's capacity
      capacity = (capacity * 2) + 1;

      // Current hashTable becomes new hashTable
      hashTable = newHashTable;
    } // End resizing

    // Nodes to be manipulated
    hashNode node = new hashNode(key, value);

    // Inserting requires a computation of the hashIndex to know where to insert
    int hashIndex = hashFunction(key);

    // Get bucket at specified hash index
    ArrayList<hashNode> currentBucket = hashTable[hashIndex];

    // In the event of a null bucket
    if (currentBucket == null) {

      // Create a bucket
      hashTable[hashIndex] = new ArrayList<hashNode>();

      // Put value in bucket
      hashTable[hashIndex].add(node);

      // Increment numKeys
      numKeys++;
    }

    // In the event of an empty bucket
    else if (currentBucket.size() == 0) {
      currentBucket.add(node);
      numKeys++;
    }

    // If this point is reached, bucket is not empty
    else {
      // Loop through items in bucket
      for (int i = 0; i < currentBucket.size(); i++) {

        // Handle duplicate keys
        if (currentBucket.get(i).key.equals(key)) {
          throw new DuplicateKeyException();
        }
      }

      // Put value in bucket
      currentBucket.add(node);

      // Maintain numKeys
      numKeys++;
    }

    // Maintain load factor
    loadFactor = (double) numKeys / capacity;
  }

  /**
   * Remove key-value pair from data structure if it exists and decrement keys.
   * Throws IllegalNullKeyException if key is null and returns false if key is
   * not found (this one was a lot easier than insert)
   * 
   * @param - key: Book ID
   * @return - true if removal is successful, false otherwise
   */
  @Override
  public boolean remove(String key) throws IllegalNullKeyException {

    // Handle null keys
    if (key == null) {
      throw new IllegalNullKeyException();
    }

    // Handles empty table
    if (numKeys == 0) {
      return false;
    }

    // Use hash function to know where to look for key
    int hashIndex = hashFunction(key);

    // Get bucket at that index
    ArrayList<hashNode> currentBucket = hashTable[hashIndex];

    for (int i = 0; i < currentBucket.size(); i++) {

      // When the key is found, remove book from hash table
      if (key.equals(currentBucket.get(i).key)) {
        currentBucket.remove(i);

        // Maintain numKeys
        numKeys--;

        // Maintain load factor
        loadFactor = numKeys / capacity;

        // Getting to this point means removing worked
        return true;
      }
    }
    return false;
  }

  /**
   * Returns value of item with given key. Throws exception if key is null or
   * not found
   * 
   * @param - key: Book ID
   * @return - Value of given key
   */
  @Override
  public Book get(String key)
      throws IllegalNullKeyException, KeyNotFoundException {

    // Handle null keys
    if (key == null) {
      throw new IllegalNullKeyException();
    }

    // Handle empty table
    if (numKeys == 0) {
      throw new KeyNotFoundException();
    }

    // Use hash function to know where to look for key, same as above
    int hashIndex = hashFunction(key);

    // Get bucket, same as above
    ArrayList<hashNode> currentBucket = hashTable[hashIndex];

    // Loop through bucket to find specified key
    for (int i = 0; i < currentBucket.size(); i++) {

      // If key is found in bucket
      if (key.equals(currentBucket.get(i).key)) {

        // Return the book
        return currentBucket.get(i).value;
      }
    }

    // Otherwise, key was not found resulting in an exception
    throw new KeyNotFoundException();
  }

  /**
   * numKeys has been maintained throughout the class, so simply return it
   * 
   * @return number of key-value pairs in the data structure
   */
  @Override
  public int numKeys() {
    return numKeys;
  }

  /**
   * load factor threshold has been maintained throughout the class, so simply
   * return it
   * 
   * @return load factor threshold for data structure
   */
  @Override
  public double getLoadFactorThreshold() {
    return loadFactorThreshold;
  }

  /**
   * Capacity is the available space in the hash table. When load factor
   * threshold is reached, the data structure must increase in size. This value
   * is maintained throughout the class
   * 
   * @return current capacity of data structure
   */
  @Override
  public int getCapacity() {
    return capacity;
  }

  /**
   * Returns collision scheme to use for this hash table. 4 = chained bucket:
   * array of array lists
   */
  @Override
  public int getCollisionResolutionScheme() {
    return 4;
  }
}
