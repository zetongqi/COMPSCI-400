/**
 * Filename:   HashTable.java
 * Project:    p3
 * Authors:    Zetong Qi LEC001
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * 
 * Due Date:   TODO: add assignment due date and time
 * Version:    1.0
 * 
 * Credits:    
 * 
 * Bugs:       
 */


import java.util.NoSuchElementException;
import java.util.*;


// HashTableADT implementation
// DO NOT ADD PUBLIC MEMBERS TO YOUR CLASS
//
// using LinkedList
// bucket
// 
// if open addressing: describe probe sequence 
// if buckets: describe data structure for each bucket: LinkedList
//
// hashing takes the key and return key.hashCode() % hash table size as index
// NOTE: you are not required to design your own algorithm for hashing,
//       you may use the hashCode provided by the <K key> object
//       
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
	
	private class HashNode<K extends Comparable<K>, V>
	{
		private K key;
		private V value;
		public HashNode(K key, V value)
		{
			this.key = key;
			this.value = value;
		}

		public K getKey()
		{
			return this.key;
		}

		public V getValue()
		{
			return this.value;
		}

		public void setKey(K key)
		{
			this.key = key;
		}

		public void setKey(V value)
		{
			this.value = value;
		}
	}

	private ArrayList<LinkedList<HashNode<K, V>>> hash_table;
	private double loadFactor;
	private int tableSize;
		
	/**
	 * Constructor
	 * default tableSize = 10, loadFactor = 0.5
	 */
	public HashTable() {
		this.tableSize = 10;
		this.loadFactor = 0.5;
		this.hash_table = new ArrayList<LinkedList<HashNode<K, V>>> (this.tableSize);
		for (int i = 0; i < 10; i++)
		{
			this.hash_table.add(new LinkedList<HashNode<K, V>> ());
		}

	}
	
	/**
	 * Constructor
	 * @param int initialCapacity, double loadFactor
	 */
	public HashTable(int initialCapacity, double loadFactor) {
		this.tableSize = initialCapacity;
		this.loadFactor = loadFactor;
		this.hash_table = new ArrayList<LinkedList<HashNode<K, V>>> (this.tableSize);
		for (int i = 0; i < this.tableSize; i++)
		{
			this.hash_table.add(new LinkedList<HashNode<K, V>> ());
		}

	}

	// helper method to calculate the number of elements in a HashTable
	private int totalSize()
	{
		int cnt = 0;
		for (int i = 0; i < this.hash_table.size(); i++)
		{
			cnt += this.hash_table.get(i).size();
		}
		return cnt;
	}

	// calculate the hash index
	private int hash_function(K key)
	{
		return key.hashCode()%this.tableSize;
	}

	/**
	 * put (key, value) pair in the HashTable
	 * store (key, value) pair in the HashNode
	 * @param K key, V value
	 */
	@Override
	public void put(K key, V value) throws IllegalArgumentException {
		if (key == null)
		{
			throw new IllegalArgumentException();
		}
		try
		{
			//System.out.println(this.loadFactor);
			//if rehashing is needed
			if ((Double.valueOf(this.totalSize()) / Double.valueOf(this.tableSize)) > this.loadFactor)
			{
				//System.out.println("here");
				this.tableSize *= 2;
				ArrayList<LinkedList<HashNode<K, V>>> new_hash_table = new ArrayList<LinkedList<HashNode<K, V>>> (this.tableSize);
				for (int i = 0; i < this.tableSize; i++)
				{
					new_hash_table.add(new LinkedList<HashNode<K, V>> ());
				}
				//System.out.println(this.hash_table.size());

				for (int i = 0; i < this.hash_table.size(); i++)
				{
					for (int j = 0; j < this.hash_table.get(i).size(); j++)
					{
						//rehashing each element
						new_hash_table.get(this.hash_function(this.hash_table.get(i).get(j).getKey())).add(this.hash_table.get(i).get(j));
					}	
				}
				this.hash_table = new_hash_table;
				HashNode<K, V> node = new HashNode<K, V>(key, value);
				this.hash_table.get(this.hash_function(key)).add(node);
			}	
			else
			{	
				//System.out.println(this.hash_function(key));
				//System.out.println(this.hash_table.size());
				HashNode<K, V> node = new HashNode<K, V>(key, value);
				this.hash_table.get(this.hash_function(key)).add(node);
			}
		}
		catch (Exception e)
		{
			//System.out.println(this.hash_function(key) +"  "+this.hash_table.size());
			System.out.println("Exception thrown at key: " + key + "   " + e.getClass().getName());
			return;
		}
	}
	
	/**
	 * return the V value with the K key
	 * @param K key
	 * @return V value
	 */
	@Override
	public V get(K key) throws IllegalArgumentException, NoSuchElementException {
		if (key == null)
			{
				throw new IllegalArgumentException();
			}
		try
		{
			int index = this.hash_function(key);
			for (int i = 0; i < this.hash_table.get(index).size(); i++)
			{
				if (this.hash_table.get(index).get(i).getKey() == key)
					return this.hash_table.get(index).get(i).getValue();
			}
			throw new NoSuchElementException();
		}
		catch (Exception e)
		{
			System.out.println("Exception thrown: " + e.getClass().getName());
			return null;
		}
	}

	/*public void get_info()
	{
		System.out.println(this.totalSize());
		System.out.println(this.hash_table.size());
		System.out.println(Double.valueOf(this.totalSize()) / Double.valueOf(this.tableSize));
	}*/

	// TODO: comment and complete this method
	@Override
	public void remove(K key) throws IllegalArgumentException, NoSuchElementException {

	}
	
	/**
	 * return the number of elements in the HashTable
	 */
	@Override
	public int size() {
		return this.totalSize();
	}
		
}
