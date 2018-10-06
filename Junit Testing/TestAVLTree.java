/**
 * Filename:   TestAVLTree.java
 * Project:    p2
 * Authors:    Debra Deppeler, Zetong Qi
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    001
 * 
 * Due Date:   Before 10pm on September 24, 2018
 * Version:    1.0
 * 
 * Credits:    
 * 
 * Bugs:       no known bugs, but not complete either
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;
import java.lang.IllegalArgumentException;
import org.junit.Test;

/**
 * The AVLTree test class
 * @author Zetong Qi
 */
public class TestAVLTree {

	/**
	 * Tests that an AVLTree is empty upon initialization.
	 */
	@Test
	public void test01isEmpty() {
		AVLTree<Integer> tree = new AVLTree<Integer>();
		assertTrue(tree.isEmpty());
	}

	/**
	 * Tests that an AVLTree is not empty after adding a node.
	 */
	@Test
	public void test02isNotEmpty() {
		AVLTree<Integer> tree = new AVLTree<Integer>();
		try {
			tree.insert(1);
			assertFalse(tree.isEmpty());
		} catch (DuplicateKeyException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Tests functionality of a single delete following several inserts.
	 */
	@Test
	public void test03insertManyDeleteOne() {
		AVLTree<Integer> tree = new AVLTree<Integer>();
		try
		{
			for (int i = 0; i < 1000; i++)
			{
				tree.insert(i);
			}
			tree.delete(0);
			for (int i = 1; i < 1000; i++)
			{
				assertTrue(tree.search(i));
			}
			assertFalse(tree.search(0));
		}
		catch (DuplicateKeyException e)
		{
			System.out.println(e.getMessage());
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Tests functionality of many deletes following several inserts.
	 */
	@Test
	public void test04insertManyDeleteMany() {
		AVLTree<Integer> tree = new AVLTree<Integer>();
		try
		{
			for (int i = 0; i < 1000; i++)
			{
				tree.insert(i);
			}
			for (int i = 0; i < 500; i++)
			{
				tree.delete(i);
			}
			for (int i = 501; i < 1000; i++)
			{
				assertTrue(tree.search(i));
			}
			for (int i = 0; i < 500; i++)
			{
				assertFalse(tree.search(i));
			}
		}
		catch (DuplicateKeyException e)
		{
			System.out.println(e.getMessage());
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Tests functionality of throwing a IllegalArgumentException from insert()
	 */
	@Test
	public void test05insertNull()
	{
		AVLTree<Integer> tree = new AVLTree<Integer>();
		try
		{
			tree.insert(null);
			fail("an IllegalArgumentException should be thrown");
		}
		catch (DuplicateKeyException e)
		{
			System.out.println(e.getMessage());
			fail("an IllegalArgumentException should be thrown");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
			return;
		}
	}
	
	/**
	 * Tests functionality of throwing a DuplicateKeyException from insert()
	 */
	@Test
	public void test06insertDuplicate()
	{
		AVLTree<Integer> tree= new AVLTree<Integer>();
		try
		{
			tree.insert(1);
			tree.insert(1);
			fail("an DuplicateKeyException should be thrown");
		}
		catch (DuplicateKeyException e)
		{
			System.out.println(e.getMessage());
			return;
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
			fail("an DuplicateKeyException should be thrown");
		}
	}
	
	/**
	 * Tests functionality of throwing a IllegalArgumentException from search()
	 */
	@Test
	public void test07searchNull()
	{
		AVLTree<Integer> tree = new AVLTree<Integer>();
		try
		{
			tree.insert(1);
			tree.search(null);
			fail("an IllegalArgumentException should be thrown");
		}
		catch (DuplicateKeyException e)
		{
			System.out.println(e.getMessage());
			fail("an IllegalArgumentException should be thrown");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
			return;
		}
	}
	
	/**
	 * Tests functionality of throwing a IllegalArgumentException from delete()
	 */
	@Test
	public void test07deleteNull()
	{
		AVLTree<Integer> tree = new AVLTree<Integer>();
		try
		{
			tree.insert(1);
			tree.delete(null);
			fail("an IllegalArgumentException should be thrown");
		}
		catch (DuplicateKeyException e)
		{
			System.out.println(e.getMessage());
			fail("an IllegalArgumentException should be thrown");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println(e.getMessage());
			return;
		}
	}
	
	/**
	 * Tests the print method
	 */
	@Test
	public void test08checkPrintString()
	{
		AVLTree<Integer> tree = new AVLTree<Integer>();
		try
		{
			tree.insert(1);
			tree.insert(2);
			tree.insert(3);
			tree.insert(4);
			String s = "1234";
			String printResult = tree.print();
			assertEquals(s, printResult);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			fail();
		}
	}
	
	/**
	 * Tests if the print method prints elements in order
	 */
	@Test
	public void test09checkPrintInorderString()
	{
		AVLTree<Integer> tree = new AVLTree<Integer>();
		try
		{
			tree.insert(4);
			tree.insert(2);
			tree.insert(3);
			tree.insert(1);
			String s = "1234";
			String printResult = tree.print();
			assertEquals(s, printResult);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			fail();
		}
	}
	
	/**
	 * Tests the checkForBalancedTree() method
	 */
	@Test
	public void test10checkBanlancedTree()
	{
		AVLTree<Integer> tree = new AVLTree<Integer>();
		try
		{
			tree.insert(4);
			tree.insert(2);
			tree.insert(3);
			tree.insert(5);
			tree.insert(11);
			tree.insert(16);
			tree.insert(19);
			tree.insert(21);
			tree.insert(6);
			tree.insert(33);
			assertTrue(tree.checkForBalancedTree());
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			fail();
		}
	}
	
	/**
	 * Tests the checkForBinarySearchTree() method
	 */
	@Test
	public void test10checkBinarySearchTree()
	{
		AVLTree<Integer> tree = new AVLTree<Integer>();
		try
		{
			tree.insert(4);
			tree.insert(2);
			tree.insert(3);
			tree.insert(12);
			tree.insert(21);
			tree.insert(31);
			tree.insert(16);
			tree.insert(19);
			tree.insert(5);
			tree.insert(1);
			tree.insert(6);
			tree.insert(9);
			tree.insert(33);
			assertTrue(tree.checkForBinarySearchTree());
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			fail();
		}
	}
	
}