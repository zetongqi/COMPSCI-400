/**
 * Filename:   TestAVLTree.java
 * Project:    p2
 * Authors:    Debra Deppeler, TODO: add your name here
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.lang.IllegalArgumentException;
import org.junit.Test;

/** TODO: add class header comments here*/
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
		// TODO: implement this test
	}
	
	/**
	 * Tests functionality of many deletes following several inserts.
	 */
	@Test
	public void test04insertManyDeleteMany() {
		// TODO: implement this test
	}

	// TODO: add tests to completely test the functionality of your AVLTree class
}
