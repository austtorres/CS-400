// Project Title: P2 AVL Search
//
// Description: Implementing an AVL tree
//
// Files: BALST.java, BALSTADT.java, BALSTTEST.java, BSTNode.java,
// DuplicateKeyException.java, IllegalNullKeyException.java,
// KeyNotFoundException.java
//
// Author: Austin Torres
// Email: artorres3@wisc.edu
// Lecturer's Name: Deb Deppeler
// Lecture: 001
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class to implement a BalanceSearchTree. Can be of type AVL or Red-Black. Note
 * which tree you implement here and as a comment when you submit.
 * 
 * AVL Search
 * 
 * @param <K> is the generic type of key
 * @param <V> is the generic type of value
 */
public class BALST<K extends Comparable<K>, V> implements BALSTADT<K, V> {

  private BSTNode<K, V> root;

  private int numKeys;

  public BALST() {
  }


  /**
   * @author Austin Torres
   * 
   *         Inner class that represents a node in a BST
   *
   * @param <K> key
   * @param <V> value
   */
  private class BSTNode<K, V> {

    K key;
    V value;
    BSTNode<K, V> left;
    BSTNode<K, V> right;
    int balanceFactor;
    int height;



    /**
     * @param key
     * @param value
     * @param leftChild
     * @param rightChild
     */
    BSTNode(K key, V value, BSTNode<K, V> leftChild, BSTNode<K, V> rightChild) {
      this.key = key;
      this.value = value;
      this.left = leftChild;
      this.right = rightChild;
      this.height = 0;
      this.balanceFactor = 0;

    }

    BSTNode(K key, V value) {
      this(key, value, null, null);
    }

  }

  /**
   * Check if tree is empty
   * 
   * @return true if root is empty
   */
  public boolean isEmpty() {
    return root == null;
  }

  /**
   * Returns the key that is in the root node of this BST. If root is null,
   * returns null.
   * 
   * @return key found at root node, or null
   */
  @Override
  public K getKeyAtRoot() {
    if (root == null) {
      return null;
    }
    return root.key;
  }



  /**
   * In order to use the getKeyOfLeftChildOf and getKeyOfRightChildOf, this
   * helper method recursively looks up the specified key to determine what node
   * contains it (if any) by making comparisons
   * 
   * @param node contains specified key
   * @param key  to find
   * @return node that contains specified key
   * @throws KeyNotFoundException thrown if key is never found in tree
   */
  BSTNode<K, V> lookup(BSTNode<K, V> node, K key) throws KeyNotFoundException {
    if (node == null) {
      throw new KeyNotFoundException();
    }

    // return node that contains key
    if (key.compareTo(node.key) == 0) {
      return node;
    }

    // Look left if value is negative
    if (key.compareTo(node.key) < 0) {
      return lookup(node.left, key);
    }

    // Look right if value is positive
    else {
      return lookup(node.right, key);
    }
  }

  public BSTNode<K, V> lookup(K Key) throws KeyNotFoundException {
    return lookup(root, Key);
  }



  /**
   * Tries to find a node with a key that matches the specified key. If a
   * matching node is found, it returns the returns the key that is in the left
   * child. If the left child of the found node is null, returns null.
   * 
   * @param key A key to search for
   * @return The key that is in the left child of the found key
   * 
   * @throws IllegalNullKeyException if key argument is null
   * @throws KeyNotFoundException    if key is not found in this BST
   */
  @Override
  public K getKeyOfLeftChildOf(K key)
      throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null) {
      throw new IllegalArgumentException("Key is null");
    }

    // Looks at left child of the node containing the specified key
    BSTNode<K, V> LeftChild = lookup(root, key).left;

    // If there is no left child, return null
    if (LeftChild == null) {
      return null;
    }

    // Return key of left child of node that contains specified key
    else {
      return LeftChild.key;
    }
  }



  /**
   * Tries to find a node with a key that matches the specified key. If a
   * matching node is found, it returns the returns the key that is in the right
   * child. If the right child of the found node is null, returns null.
   * 
   * @param key A key to search for
   * @return The key that is in the right child of the found key
   * 
   * @throws IllegalNullKeyException if key is null
   * @throws KeyNotFoundException    if key is not found in this BST
   */
  @Override
  public K getKeyOfRightChildOf(K key)
      throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null) {
      throw new IllegalArgumentException();
    }

    // Looks at right child of the node containing the specified key
    BSTNode<K, V> RightChild = lookup(root, key).right;

    // If there is no left child, return null
    if (RightChild == null) {
      return null;
    }

    // Return key of right child of node that contains specified key
    else {
      return RightChild.key;// returns key of right child
    }
  }



  /**
   * Another helper, because RECURSION = HELPERS ON HELPERS
   * 
   * This recursively searches a tree and returns the height by maintaining and
   * updating a max height. You have to add one to this final number, since the
   * root node is not being added in the initial comparison
   * 
   * @param node
   * @return total height
   */
  int height(BSTNode<K, V> node) {

    // Empty tree
    if (node == null) {
      return 0;
    }

    // If tree is one lonely root
    if (node.left == null && node.right == null) {
      return 1;
    }

    // Recursively find height of left subtree
    int leftTreeHeight = height(node.left);

    // Recursively find height of right subtree
    int rightTreeHeight = height(node.right);

    // Essentially a counter that gets updated whenever a new max height is
    // found
    int maxHeight = 0;

    // Ties go to the left tree according to lecture
    if (leftTreeHeight >= rightTreeHeight) {

      // Left tree's height is the max height (deepest tree)
      maxHeight = leftTreeHeight;
    }

    // If right tree is taller
    else {
      maxHeight = rightTreeHeight;
    }

    // Total height = maxHeight + root
    return maxHeight + 1;
  }



  /**
   * Returns the height of this BST. H is defined as the number of levels in the
   * tree.
   * 
   * If root is null, return 0 If root is a leaf, return 1 Else return 1 + max(
   * height(root.left), height(root.right) )
   * 
   * Examples: A BST with no keys, has a height of zero (0). A BST with one key,
   * has a height of one (1). A BST with two keys, has a height of two (2). A
   * BST with three keys, can be balanced with a height of two(2) or it may be
   * linear with a height of three (3) ... and so on for tree with other heights
   * 
   * @return the number of levels that contain keys in this BINARY SEARCH TREE
   */
  @Override
  public int getHeight() {
    return height(root);
  }



  /**
   * Helper for getInOrderTraversal that recursively adds traversal order to an
   * arraylist in L V R order
   * 
   * @param node      being traversed
   * @param traversal list of nodes in order
   */
  void inOrderHelper(BSTNode<K, V> node, ArrayList<K> traversal) {

    // End search
    if (node == null) {
      return;
    }

    // Search in L V R order
    inOrderHelper(node.left, traversal);
    traversal.add(node.key);
    inOrderHelper(node.right, traversal);
  }



  /**
   * Returns the keys of the data structure in sorted order. In the case of
   * binary search trees, the visit order is: L V R
   * 
   * If the SearchTree is empty, an empty list is returned.
   * 
   * @return List of Keys in-order
   */
  @Override
  public List<K> getInOrderTraversal() {

    // Create array list to store traversal order, and then get traversal
    ArrayList<K> inOrderTraversal = new ArrayList<K>();
    inOrderHelper(root, inOrderTraversal);
    return inOrderTraversal;
  }



  /**
   * Helper for getPreOrderTraversal that recursively adds traversal order to an
   * arraylist in V L R order
   * 
   * @param node      being traversed
   * @param traversal list of nodes in order
   */
  void preOrderHelper(BSTNode<K, V> node, ArrayList<K> traversal) {

    // End search
    if (node == null) {
      return;
    }

    // Search in V L R order
    traversal.add(node.key);
    preOrderHelper(node.left, traversal);
    preOrderHelper(node.right, traversal);
  }

  /**
   * Returns the keys of the data structure in pre-order traversal order. In the
   * case of binary search trees, the order is: V L R
   * 
   * If the SearchTree is empty, an empty list is returned.
   * 
   * @return List of Keys in pre-order
   */
  @Override
  public List<K> getPreOrderTraversal() {

    // Create array list to store traversal order, and then get traversal
    ArrayList<K> preOrderTraversal = new ArrayList<K>();
    preOrderHelper(root, preOrderTraversal);
    return preOrderTraversal;
  }


  /**
   * Helper for getPostOrderTraversal that recursively adds traversal order to
   * an arraylist in L R V order
   * 
   * @param node      being traversed
   * @param traversal list of nodes in order
   */
  void postOrderHelper(BSTNode<K, V> node, ArrayList<K> traversal) {

    // End search
    if (node == null) {
      return;
    }

    // Search in L R V order
    postOrderHelper(node.left, traversal);
    postOrderHelper(node.right, traversal);
    traversal.add(node.key);
  }


  /**
   * Returns the keys of the data structure in post-order traversal order. In
   * the case of binary search trees, the order is: L R V
   * 
   * If the SearchTree is empty, an empty list is returned.
   * 
   * @return List of Keys in post-order
   */
  @Override
  public List<K> getPostOrderTraversal() {

    // Create array list to store traversal order, and then get traversal
    ArrayList<K> postOrderTraversal = new ArrayList<K>();
    postOrderHelper(root, postOrderTraversal);
    return postOrderTraversal;
  }



  /**
   * Helper for getLevelOrderTraversal that recursively adds traversal order to
   * an arraylist in level order
   * 
   * @param node      being traversed
   * @param traversal list of nodes in order
   */
  void levelOrderHelper(BSTNode<K, V> node, int level, ArrayList<K> traversal) {

    // End search
    if (node == null) {
      return;
    }

    // Starting level (root)
    if (level == 0) {
      traversal.add(node.key);
    }

    // Move level by level
    levelOrderHelper(node.left, level - 1, traversal);
    levelOrderHelper(node.right, level - 1, traversal);
  }

  /**
   * Returns the keys of the data structure in level-order traversal order.
   * 
   * The root is first in the list, then the keys found in the next level down,
   * and so on.
   * 
   * If the SearchTree is empty, an empty list is returned.
   * 
   * @return List of Keys in level-order
   */
  @Override
  public List<K> getLevelOrderTraversal() {


    // Create array list to store traversal order, and then get traversal
    ArrayList<K> levelOrderTraversal = new ArrayList<K>();

    // Loop through tree until max height is reached
    for (int i = 0; i < getHeight(); i++) {
      levelOrderHelper(root, i, levelOrderTraversal);
    }
    return levelOrderTraversal;
  }



  /**
   * Add the key,value pair to the data structure and increase the number of
   * keys. If key is null, throw IllegalNullKeyException; If key is already in
   * data structure, throw DuplicateKeyException(); Do not increase the num of
   * keys in the structure, if key,value pair is not added.
   */
  @Override
  public void insert(K key, V value)
      throws IllegalNullKeyException, DuplicateKeyException {
    if (key == null) {
      throw new IllegalNullKeyException();
    }
    if (value == null) {
      try {
        remove(key);
        return;
      } catch (KeyNotFoundException e) {
        e.printStackTrace();
      }
    } else {
      this.root = insert(root, key, value);

      this.numKeys++;
    }
  }

  private BSTNode<K, V> insert(BSTNode<K, V> n, K key, V value)
      throws IllegalNullKeyException, DuplicateKeyException {
    if (n == null)
      return new BSTNode<K, V>(key, value, null, null);
    int comp = key.compareTo(n.key);
    if (comp < 0) {
      n.left = insert(n.left, key, value);
      n.height = height(n);
      return correctInsertBalance(n, key);
    } else if (comp > 0) {
      n.right = insert(n.right, key, value);
      n.height = height(n);
      return correctInsertBalance(n, key);
    } else {
      throw new DuplicateKeyException();

    }

  }

  /**
   * A private helper method for checking and correcting the balance of a node n
   * after an insertion of some other node has occurred.
   * 
   * @param n   The current node n to check if rebalancing is needed now after
   *            inserting a new node with the provided key
   * @param key The actual value (key) of a BSTNode that was inserted
   * @return a BSTNode with the correct balance after insertion of a new node
   *         with the given key has occurred. If no rebalancing was needed, n is
   *         returned.
   */
  private BSTNode<K, V> correctInsertBalance(BSTNode<K, V> n, K key) {

    int balanceFactor = getBalance(n);

    // determines if rebalancing is needed
    if (balanceFactor < -1 || balanceFactor > 1) {


      if (balanceFactor > 1 && key.compareTo(n.key) < 0) {
        return rightRotate(n);
      }

      if (balanceFactor < -1 && key.compareTo(n.key) > 0) {
        return leftRotate(n);
      }

      if (balanceFactor > 1 && key.compareTo(n.key) > 0) {
        n.left = leftRotate(n.left);
        return rightRotate(n);
      }

      if (balanceFactor < -1 && key.compareTo(n.key) < 0) {
        n.right = rightRotate(n);
        return leftRotate(n);
      }
    }

    return n;
  }

  /**
   * A private helper method for checking and correcting the balance of a node n
   * after a deletion of some other node has occurred.
   *
   * @param n The current node n to check if rebalancing is needed now after
   *          deleting a given node
   * @return a BSTNode with the correct balance after deletion of a given node
   *         has occurred. If no rebalancing was needed, n is returned.
   */
  private BSTNode<K, V> correctDeleteBalance(BSTNode<K, V> n) {
    int balanceFactor = getBalance(n);
    // if rebalancing is needed
    if (balanceFactor < -1 || balanceFactor > 1) {
      // left-left rotation case
      if (balanceFactor > 1 && getBalance(n.left) >= 0) {
        return rightRotate(n);
      }
      // right-right rotation case
      if (balanceFactor < -1 && getBalance(n.right) <= 0) {
        return leftRotate(n);
      }
      // left-right rotation case
      if (balanceFactor > 1 && getBalance(n.left) < 0) {
        n.left = (leftRotate(n.right));
        return rightRotate(n);
      }
      // right-left rotation case
      if (balanceFactor < -1 && getBalance(n.right) > 0) {
        n.right = (rightRotate(n.right));
        return leftRotate(n);
      }
    }
    return n;
  }

  /**
   * Checks if AVL property is consistent.
   * 
   * @return {@code true} if AVL property is consistent.
   */
  private boolean isAVL() {
    return isAVL(root);
  }

  /**
   * Checks if AVL property is consistent in the subtree.
   * 
   * @param x the subtree
   * @return true if AVL property is consistent in the subtree
   */
  private boolean isAVL(BSTNode<K, V> x) {
    if (x == null)
      return true;
    int bf = getBalance(x);
    if (bf > 1 || bf < -1)
      return false;
    return isAVL(x.left) && isAVL(x.right);
  }

  /**
   * Checks if the symmetric order is consistent.
   * 
   * @return {@code true} if the symmetric order is consistent
   */
  private boolean isBST() {
    return isBST(root, null, null);
  }

  /**
   * Checks if the tree rooted at x is a BST with all keys strictly between min
   * and max (if min or max is null, treat as empty constraint)
   * 
   * @param x   the subtree
   * @param min the minimum key in subtree
   * @param max the maximum key in subtree
   * @return {@code true} if if the symmetric order is consistent
   */
  private boolean isBST(BSTNode<K, V> x, K min, K max) {
    if (x == null)
      return true;
    if (min != null && ((Comparable<K>) x.key).compareTo(min) <= 0)
      return false;
    if (max != null && ((Comparable<K>) x.key).compareTo(max) >= 0)
      return false;
    return isBST(x.left, min, (K) x.key) && isBST(x.right, (K) x.key, max);
  }

  private boolean checkTree() {
    return isBST() && isAVL();
  }

  /**
   * Removes node containing key
   */
  @Override
  public boolean remove(K key)
      throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null) {
      throw new IllegalNullKeyException();
    } else {
      this.root = remove(this.root, key);
    }
    return true; // If key was removed
  }

  /**
   * A private recursive helper method for deleting a node with the given key
   * from an AVL Tree, assuming key is not null. This helper compares keys for
   * deletion and reconnects the AVL tree nodes after deleting the node with the
   * given key if it was found.
   *
   * @param n   The current BSTNode to compare the key to be deleted with
   * @param key The actual value (key) of a BSTNode to be deleted (not null)
   * @return a BSTNode that will connect the child to its parent and assists in
   *         reconnecting the tree
   */
  private BSTNode<K, V> remove(BSTNode<K, V> n, K key)
      throws KeyNotFoundException {
    if (n == null) {
      throw new KeyNotFoundException();
    }

    // If key is found
    if (key.compareTo(n.key) == 0) {

      // If no children
      if (n.left == null && n.right == null) {
        this.numKeys--;
        return null;
      }

      // If only one child (left or right)
      if (n.left == null) {
        this.numKeys--;
        return n.right;
      } else if (n.right == null) {
        this.numKeys--;
        return n.left;
      }

      // If 2 children
      K inOrderSucc = smallest(n.right);
      n.key = inOrderSucc;
      n.right = remove(n.right, inOrderSucc);
      numKeys--;
      n.height = height(n);
    }

    // Key not found
    else if (key.compareTo(n.key) < 0) {
      n.left = remove(n.left, key);

      n.height = height(n);
    }

    else {
      n.right = remove(n.right, key);
      n.height = height(n);
    }


    return correctDeleteBalance(n);
  }

  /**
   * Finds and returns the key of the in-order successor of the BSTNode
   * provided, which is the smallest value in the right subtree. The BSTNode n
   * should represent the starting node of the right subtree.
   *
   * @param n The BSTNode to use as a starting point for finding the in-order
   *          successor and getting its key.
   * @return a key K that represents the smallest value of the right subtree
   */
  private K smallest(BSTNode<K, V> n) {
    // keep going left until not possible
    while (n.left != null) {
      n = n.left;
    }
    // return smallest key in right subtree
    return n.key;
  }

  /**
   * Returns the value associated with the specified key
   *
   * Does not remove key or decrease number of keys If key is null, throw
   * IllegalNullKeyException If key is not found, throw KeyNotFoundException().
   */
  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {

    // If key is null, throw null key exception
    if (key == null) {
      throw new IllegalNullKeyException();
    }

    // Look up value of node at containing specified key
    BSTNode<K, V> node = lookup(root, key);
    return node.value;
  }


  /**
   * Returns true if the key is in the data structure If key is null, throw
   * IllegalNullKeyException Returns false if key is not null and is not present
   */
  @Override
  public boolean contains(K key) throws IllegalNullKeyException {

    // If key is null, throw null key exception
    if (key == null) {
      throw new IllegalNullKeyException();
    }

    // If key does not exist, a KeyNotFoundException is triggered and return
    // false
    try {
      lookup(root, key);
    } catch (KeyNotFoundException e) {
      return false;
    }

    // If key exists, return true
    return true;
  }


  /**
   * Returns the number of key,value pairs in the data structure
   */
  @Override
  public int numKeys() {
    return numKeys;
  }

  @Override
  public void print() {
    // TODO Auto-generated method stub

  }



  /**
   * Rotate node right to rebalance tree
   * 
   * @param y
   * @return
   */
  BSTNode<K, V> rightRotate(BSTNode<K, V> y) {
    BSTNode<K, V> x = y.left;
    BSTNode<K, V> temp = x.right;

    // Rotate nodes
    x.right = y;
    y.left = temp;

    // Maintain height
    y.height = Math.max(height(y.left), height(y.right)) + 1;
    x.height = Math.max(height(x.left), height(x.right)) + 1;

    // Return new root
    return x;
  }

  BSTNode<K, V> leftRotate(BSTNode<K, V> x) {
    BSTNode<K, V> y = x.right;
    BSTNode<K, V> temp = y.left;

    // Rotate
    y.left = x;
    x.right = temp;

    // Maintain height
    x.height = Math.max(height(x.left), height(x.right)) + 1;
    y.height = Math.max(height(y.left), height(y.right)) + 1;

    // Return new root
    return y;
  }


  // Get Balance factor of node N
  int getBalance(BSTNode<K, V> node) {
    if (node == null)
      return 0;

    // Balance factor is height of left subtree minus height of right subtree
    return height(node.left) - height(node.right);
  }
}
