/**
 * Filename:   AVLTree.java
 * Project:    p2
 * Authors:    Debra Deppeler, Zetong Qi
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    TODO: replace with your lecture number
 * 
 * Due Date:   Before 10pm on September 24, 2018
 * Version:    1.0
 * 
 * Credits:    TODO: name individuals and sources outside of course staff
 * 
 * Bugs:       no known bugs, but not complete either
 */

import java.lang.IllegalArgumentException;
import java.util.*;
import java.lang.*;

/** TODO: add class header comments here
 * @param <K>
 */
public class AVLTree<K extends Comparable<K>> implements AVLTreeADT<K> {

	/** TODO: add class header comments here
	 * Represents a tree node.
	 * @param <K>
	 */
	class BSTNode<K> {
		/* fields */
		private K key;	// TODO: variable declaration comment
		private int height;	// TODO: variable declaration comment
		private BSTNode<K> left, right;	// TODO: variable declaration comment
		private BSTNode<K> parent;
		
		/**
		 * Constructor for a BST node.
		 * @param key
		 */
		BSTNode(K key) {
			this.key = key;
			this.height = 1;
			this.left = null;
			this.right = null;
			this.parent = null;
		}

		/* accessors */
		public K getKey()
		{
			return this.key;
		}

		public int getHeight()
		{
			return this.height;
		}

		public BSTNode<K> getLeft()
		{
			return this.left;
		}

		public BSTNode<K> getRight()
		{
			return this.right;
		}

		public BSTNode<K> getParent()
		{
			return this.parent;
		}

		/* mutators */
		public void setKey(K key)
		{
			this.key = key;
		}

		public void setHeight(int height)
		{
			this.height = height;
		}

		public void setLeft(BSTNode<K> left)
		{
			this.left = left;
			if (this.left != null)
				this.left.setParent(this);
		}

		public void setRight(BSTNode<K> right)
		{
			this.right = right;
			if (this.right != null)
				this.right.setParent(this);
		}

		private void setParent(BSTNode<K> parent)
		{
			this.parent = parent;
		}
	}
	
	private BSTNode<K> root;

	AVLTree()
	{
		this.root = null;
	}

	public void printRoot()
		{
			System.out.println(this.root.getKey());
		}

	/**
	 * TODO: add method header comments here
	 */
	@Override
	public boolean isEmpty() {
		if (this.root == null)
			return true;
		else
			return false;
	}

	/**
	 * TODO: add method header comments here
	 */

	public BSTNode<K> rightRotate(BSTNode<K> node)
	{
		BSTNode<K> tempT3 = node.getRight();
		node.setRight(node.getLeft());
		K tempNodeKey = node.getKey();
		node.setKey(node.getRight().getKey());
		node.getRight().setKey(tempNodeKey);
		node.setLeft(node.getRight().getLeft());
		node.getRight().setLeft(node.getRight().getRight());
		node.getRight().setRight(tempT3);
		return node;
	}

	public BSTNode<K> leftRotate(BSTNode<K> node)
	{
		BSTNode<K> tempT1 = node.getLeft();
		node.setLeft(node.getRight());
		K tempNodeKey = node.getKey();
		node.setKey(node.getLeft().getKey());
		node.getLeft().setKey(tempNodeKey);
		node.setRight(node.getLeft().getRight());
		node.getLeft().setRight(node.getLeft().getLeft());
		node.getLeft().setLeft(tempT1);
		return node;
	}

	private int getHeight(BSTNode<K> node, int leftHeight, int rightHeight)
	{
		if (node == null)
		{
			return 0;
		}
		else
		{
			leftHeight = getHeight(node.getLeft(), leftHeight, rightHeight);
			rightHeight = getHeight(node.getRight(), leftHeight, rightHeight);
			leftHeight++;
			rightHeight++;
			if (rightHeight >= leftHeight)
				return rightHeight;
			else
				return leftHeight;
		}
	}

	public int getHeight()
	{
		return getHeight(this.root, 0, 0);
	}

	public BSTNode<K> find_Unbalanced_Node(BSTNode<K> node)
	{
		BSTNode<K> pointer = node;
		while (pointer.getParent() != null)
		{
			pointer = pointer.getParent();
			int rightHeight = getHeight(pointer.getRight(), 0, 0);
			int leftHeight = getHeight(pointer.getLeft(), 0, 0);
			if (java.lang.Math.abs(rightHeight-leftHeight) > 1)
				return pointer;
		}
		return null;
	}

	//find a path between node from bottom to top
	public ArrayList<BSTNode<K>> findPath(BSTNode<K> top, BSTNode<K> down)
	{
		ArrayList<BSTNode<K>> nodeList = new ArrayList<BSTNode<K>>();
		BSTNode<K> pointer = down;
		while (pointer != top)
		{
			nodeList.add(pointer);
			pointer = pointer.getParent();
		}
		Collections.reverse(nodeList);
		return nodeList;
	}

	public int findRelation(BSTNode<K> parent, BSTNode<K> child)
	{
		if (parent.getRight() != null)	
			if (parent.getRight() == child)
				return 1;
		if(parent.getLeft() != null)
			if (parent.getLeft() == child)
				return 2;
		return 0;
	}

	private BSTNode insert(BSTNode<K> node, BSTNode<K> n) throws DuplicateKeyException, IllegalArgumentException
	{
		if (node == null)
		{
			return n;
		}

		if (n.getKey().equals(node.getKey()) || n == null)
		{
			throw new IllegalArgumentException();
		}

		if (n.getKey().compareTo(node.getKey()) <= 0)
		{
			node.setLeft(insert(node.getLeft(), n));
			return node;
		}
		else
		{
			node.setRight(insert(node.getRight(), n));
			return node;
		}
	}

	//AVL insert
	@Override
	public void insert(K key) throws DuplicateKeyException, IllegalArgumentException {
		BSTNode<K> n = new BSTNode<K>(key);
		if (this.root == null)
			{
				this.root = n;
				return;
			}
		this.insert(this.root, n);
		BSTNode<K> unbalanced = this.find_Unbalanced_Node(n);
		//System.out.println(unbalanced);
		if (unbalanced == null)
			return;
		ArrayList<BSTNode<K>> connection = this.findPath(unbalanced, n);
		int firstRelation = this.findRelation(unbalanced, connection.get(0));
		int secondRelation = this.findRelation(connection.get(0), connection.get(1));
		//System.out.println(firstRelation + "   "+ secondRelation);
		//left left
		if (firstRelation == 2 && secondRelation == 2)
			this.rightRotate(unbalanced);
		//left right
		if (firstRelation == 2 && secondRelation == 1)
		{
			this.leftRotate(connection.get(0));
			this.rightRotate(unbalanced);
		}
		//right left
		if (firstRelation == 1 && secondRelation == 2)
		{
			this.rightRotate(connection.get(0));
			this.leftRotate(unbalanced);
		}
		//right right
		if (firstRelation == 1 && secondRelation == 1)
			this.leftRotate(unbalanced);
	}

	/**
	 * TODO: add method header comments here
	 */

	private BSTNode<K> getSmallestNode(BSTNode<K> node)
	{
		if (node.getLeft() == null)
			return node;
		else
			return getSmallestNode(node.getLeft());
	}

	//get the balance factor of a node
	int getBalance(BSTNode<K> node)  
    {  
        if (node == null)  
            return 0;  
        return this.getHeight(node.left,0, 0) - this.getHeight(node.right,0, 0);  
    } 

    private BSTNode<K> delete(BSTNode<K> node, K key) throws IllegalArgumentException
    {
    	//System.out.println("here");
    	if (node == null)
    	{
    		return null;
    	}
    	if (key.compareTo(node.getKey()) < 0)
    	{
    		node.setLeft(delete(node.getLeft(), key));
    	}
    	else if (key.compareTo(node.getKey()) > 0)
    	{
    		node.setRight(delete(node.getRight(), key));
    	}
    	else
    	{
    		if (node.getLeft() == null)
    		{
    			node = node.getRight();
    		}
    		else if (node.getRight() == null)
    		{
    			node = node.getLeft();
    		}
    		else if (node.getRight() == null && node.getLeft() == null)
    		{
    			node = null;
    		}
    		else
    		{
    			BSTNode<K> smallest = getSmallestNode(node.getRight());
    			node.setKey(smallest.getKey());
    			node.setRight(delete(node.getRight(), smallest.getKey()));
    		}
    	}
    	if (node == null)
    		return node;
		int node_balance = getBalance(node);
		//System.out.println(node.getKey() + "   "+node_balance+"    "+getBalance(node.getLeft()));
		if (node_balance > 1 && getBalance(node.getLeft()) >= 0)
		{
			return this.rightRotate(node);
		}
		if (node_balance > 1 && getBalance(node.getLeft()) < 0)
		{
			node.setLeft(this.leftRotate(node.getLeft()));
			return this.rightRotate(node);
		}
		if (node_balance < -1 && getBalance(node.getRight()) <= 0)
		{
			return this.leftRotate(node);
		}
		if (node_balance < -1 && getBalance(node.getRight()) > 0)
		{
			node.setRight(this.rightRotate(node.getRight()));
			return this.leftRotate(node);
		}
    	return node;
    }

	@Override
	public void delete(K key) throws IllegalArgumentException
	{
		this.delete(this.root, key);
	}

	/**
	 * TODO: add method header comments here
	 */

	private boolean search(BSTNode<K> node, K key) throws IllegalArgumentException
	{
		if (node == null)
			return false;
		if (node.getKey().equals(key))
			return true;
		else if (key.compareTo(node.getKey()) < 0)
			return search(node.getLeft(), key);
		else
			return search(node.getRight(), key);
	}

	@Override
	public boolean search(K key) throws IllegalArgumentException {
		return this.search(this.root, key);
	}

	/**
	 * TODO: add method header comments here
	 */
	private String print(BSTNode<K> node, String s)
	{
		if (node == null)
		{
			return "";
		}
		s = print(node.getLeft(), s);
		s += node.getKey().toString();
		s += print(node.getRight(), s);
		return s;
	}

	@Override
	public String print() {
		return this.print(this.root, "");
	}

	/**
	 * TODO: add method header comments here
	 */

	private int height(BSTNode<K> node)  
    { 
        if (node == null) 
            return 0; 
        return 1 + Math.max(height(node.getLeft()), height(node.getRight())); 
    } 

    private boolean checkForBalancedTree(BSTNode<K> node)  
    { 
        int lh;
        int rh;
        if (node == null) 
            return true; 
        lh = height(node.getLeft()); 
        rh = height(node.getRight()); 
        if (Math.abs(lh - rh) <= 1 && checkForBalancedTree(node.getLeft()) && checkForBalancedTree(node.getRight()))  
            return true;
        return false;
    }        

	@Override
	public boolean checkForBalancedTree() {
		return this.checkForBalancedTree(this.root);
	}

	/**
	 * TODO: add method header comments here
	 */

	private void traverse(BSTNode<K> node, ArrayList<K> elements)
	{
		if (node == null)
		{
			return;
		}
		traverse(node.getLeft(), elements);
		elements.add(node.getKey());
		traverse(node.getRight(), elements);
	}

	private ArrayList<K> traverse()
	{
		ArrayList<K> elements = new ArrayList<K>();
		traverse(this.root, elements);
		return elements;
	}

	@Override
	public boolean checkForBinarySearchTree() {
		for (int i = 1; i < elements.size(); i++)
		{
			System.out.println(elements.get(i-1)+"   "+elements.get(i));
			if (elements.get(i-1).compareTo(elements.get(i)) > 0)
			{
				System.out.println(elements.get(i));
				return false;
			}
		}
		return true;
	}
}
