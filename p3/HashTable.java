/**
 * Filename:   HashTable.java
 * Project:    p3
 * Authors:    Zetong Qi LEC001
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * 
 * Due Date:   OCT 29 10PM
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
	
	//HashNode object that stores the key value pair
	private class HashNode<K extends Comparable<K>, V>
	{
		//HashNode memebers
		private K key;
		private V value;

		//constructor
		public HashNode(K key, V value)
		{
			this.key = key;
			this.value = value;
		}

		//accessors
		public K getKey()
		{
			return this.key;
		}

		public V getValue()
		{
			return this.value;
		}

		//mutators
		public void setKey(K key)
		{
			this.key = key;
		}

		public void setValue(V value)
		{
			this.value = value;
		}
	}

	//HashTable mambers
	private ArrayList<LinkedList<HashNode<K, V>>> hash_table;
	private double loadFactor;
	private int tableSize;
		
	/**
	 * Constructor
	 * default tableSize = 123, loadFactor = 0.7
	 */
	public HashTable() {
		this.tableSize = 123;
		this.loadFactor = 0.7;
		this.hash_table = new ArrayList<LinkedList<HashNode<K, V>>> (this.tableSize);
		for (int i = 0; i < this.tableSize; i++)
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
	 * throw IllegalArgumentException when key is null
	 * @param K key, V value pair
	 */
	@Override
	public void put(K key, V value) throws IllegalArgumentException {
		if (key == null)
		{
			throw new IllegalArgumentException();
		}
		try
		{
			//if rehashing is needed, double this.hash_table and rehash every element
			if ((Double.valueOf(this.totalSize()) / Double.valueOf(this.tableSize)) > this.loadFactor)
			{
				this.tableSize = this.tableSize*2;
				ArrayList<LinkedList<HashNode<K, V>>> new_hash_table = new ArrayList<LinkedList<HashNode<K, V>>> (this.tableSize);
				for (int i = 0; i < this.tableSize; i++)
				{
					new_hash_table.add(new LinkedList<HashNode<K, V>> ());
				}

				for (int i = 0; i < this.hash_table.size(); i++)
				{
					for (int j = 0; j < this.hash_table.get(i).size(); j++)
					{
						//rehashing each element
						new_hash_table.get(this.hash_function(this.hash_table.get(i).get(j).getKey())).add(this.hash_table.get(i).get(j));
					}	
				}
				this.hash_table = new_hash_table;
			}

			//insert the key value pair in the HashTable
			int hashIndex = this.hash_function(key);
			for (int i = 0; i < this.hash_table.get(hashIndex).size(); i++)
			{
				//if the key already exists in the HashTable
				if (this.hash_table.get(hashIndex).get(i).getKey().equals(key))
				{
					//update the value in that HashNode
					this.hash_table.get(hashIndex).get(i).setValue(value);
					return;
				}
			}
			//if the key is not in the HashTable, add a new HashNode
			HashNode<K, V> node = new HashNode<K, V>(key, value);
			this.hash_table.get(hashIndex).add(node);
			return;
		}
		catch (Exception e)
		{
			System.out.println("Exception: " + e.getClass().getName() + " thrown at key: " + key + " when putting ");
			return;
		}
	}
	
	/**
	 * return the V value with the K key
	 * throw IllegalArgumentException if key is null 
	 * throw NoSuchElementException if key does not exist 
	 * @param K key value
	 * @return V value corresponding to the K key
	 */
	@Override
	public V get(K key) throws IllegalArgumentException, NoSuchElementException {
		if (key == null)
			throw new IllegalArgumentException();
		try
		{
			int index = this.hash_function(key);
			for (int i = 0; i < this.hash_table.get(index).size(); i++)
			{
				if (this.hash_table.get(index).get(i).getKey().equals(key))
					return this.hash_table.get(index).get(i).getValue();
			}
			throw new NoSuchElementException();
		}
		catch (Exception e)
		{
			System.out.println("Exception: " + e.getClass().getName() + " thrown at key: " + key + " when geting ");
			return null;
		}
	}

	/**
	 * remove the (key,value) entry for the specified key
	 * throw IllegalArgumentException if key is null
	 * throw NoSuchElementException if key does not exist in the tree
	 * @param K key value
	 */
	@Override
	public void remove(K key) throws IllegalArgumentException, NoSuchElementException {
		if (key == null)
		{
			throw new IllegalArgumentException();
		}
		try
		{
			int hash_index = this.hash_function(key);
			for (int i = 0; i < this.hash_table.get(hash_index).size(); i++)
			{
				if (this.hash_table.get(hash_index).get(i).getKey().equals(key))
				{
					this.hash_table.get(hash_index).remove(i);
					return;
				}
			}
			throw new NoSuchElementException();
		}
		catch (Exception e)
		{
			System.out.println("Exception: " + e.getClass().getName() + " thrown at key: " + key + " when deleting ");
			return;
		}
	}
	
	/**
	 * return the number of elements in the HashTable
	 * @return the integer number of elements in the HashTable
	 */
	@Override
	public int size() {
		return this.totalSize();
	}
		
}
