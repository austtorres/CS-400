// Project Title: P1 TestADT
//
// Description: My own data structure with no public or package level fields
// which is to be tested by the TestDS_My class. Creating this helps practice
// testing without knowing the implementation and helps identify things to test
// for.
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

/**
 * @author Austin Torres This class creates an array data structure without any
 *         public or package level fields which will allow it to be tested by
 *         the TestDS_My class
 */
public class DS_My implements DataStructureADT {

  /**
   * @author Austin Torres Private inner class with key and value data
   */
  private class KVP {
    private Object value; // Value of key
    private Comparable key;

    /**
     * Constructor initializes key and value
     * 
     * @param k - key
     * @param v - value
     */
    private KVP(Comparable k, Object v) {
      this.key = k;
      this.value = v;
    }
  }

  // Private Fields of the class

  private KVP[] pairArray; // Array of pairs
  private int cap; // Capacity for amount of pairs
  private int size; // Amount of pairs in array


  /**
   * Constructor: Initializes capacity and size in addition to creating new
   * array
   */
  public DS_My() {
    this.size = 0;
    this.cap = 500; // Capacity is 500 as P1 directions specify
    this.pairArray = new KVP[cap]; // New KVP array with capacity 500
  }

  /**
   * Inserts legal key value pair objects into array, avoiding duplicate keys
   * and expanding capacity dynamically using deep copies to transfer elements
   * from one array to a larger array
   */
  @Override
  public void insert(Comparable k, Object v) {
    if (k == null) { // Prevent insertion of null key
      throw new IllegalArgumentException("The key you tried to insert is null");
    }

    // Insert into empty array
    else if (this.size == 0) {
      pairArray[this.size] = new KVP(k, v);
      this.size++;
      return;

    }
    // Array has reached capacity
    else if (this.cap == this.size) {

      // Dynamically increase capacity of array by 500 as previous array becomes
      // full
      KVP[] largerPairArray = new KVP[this.cap + 500];

      // Copy over all of the elements in the old array
      for (int i = 0; i < this.size; i++) {
        if (pairArray[i].key.equals(k)) {
          throw new RuntimeException("A duplicate key cannot be inserted");
        }
        largerPairArray[i] = this.pairArray[i]; // Copy existing values to same
                                                // index of new array
      }
      // Transfer fields to new array
      this.pairArray = largerPairArray;
      this.pairArray[size] = new KVP(k, v);

      // Increase capacity by 500
      this.cap += 500;

      // Update size
      size++;
      return;
    }

    // Size is between 1 and capacity
    else {
      for (int i = 0; i < this.size; i++) {

        // Check to make sure key does not already exist
        if (pairArray[i].key.equals(k)) {
          throw new RuntimeException("A duplicate key cannot be inserted");
        }
      }

      // Successfully add pair to array
      pairArray[this.size] = new KVP(k, v);
      this.size++;
    }
  }

  /**
   * Method for removing key value pair from array if it exists
   */
  @Override
  public boolean remove(Comparable k) {

    // Boolean to determine if key already exists. Initialized to false until
    // inserted
    boolean exists = false;

    // Prevent insertion of null keys
    if (k == null) {
      throw new IllegalArgumentException("Attempted to insert null key");
    }

    else {

      // Create new array that will not have the removed element
      KVP[] temp = new KVP[this.cap];

      // Loop through key value pairs to see if key being removed exists
      for (int i = 0; i < this.size; i++) {
        if (pairArray[i].key.equals(k)) {

          // Change exists to true
          exists = true;

          // Decrement size
          this.size--;

          // Loop through old array and skip removed element r when copying over
          for (int r = i; r < this.size; r++) {

            // Skip removed element r
            temp[r] = pairArray[r + 1];
          }

          // pairArray references temp
          this.pairArray = temp;

          return exists;
        }

        else {
          // If key value pair does not exist
          temp[i] = pairArray[i];
        }
      }

      this.pairArray = temp;
      return exists;
    }
  }


  /**
   * Checks to see if array contains specified key
   */
  @Override
  public boolean contains(Comparable k) {

    // Loop through array
    for (int i = 0; i < this.size; i++) {
      if (pairArray[i].key.equals(k)) {
        return true;
      }
    }

    // Returns false if key is not in array
    return false;
  }


  /**
   * Retrieves value of specified key
   */
  @Override
  public Object get(Comparable k) {

    // As usual with previous methods, prevent null keys
    if (k == null) {
      throw new IllegalArgumentException("Specified key is null");
    }

    else {
      // Loop through array to find key
      for (int i = 0; i < this.size; i++) {

        // Return value of key if it is found
        if (pairArray[i].key.equals(k)) {
          return pairArray[i].value;
        }
      }

      // If key is not found, return null as it has no value
      return null;
    }
  }


  /**
   * Returns number of elements stored in the array
   */
  @Override
  public int size() {

    // Size has been updated with every insertion and removal, so it can simply
    // be returned
    return this.size;
  }

}
