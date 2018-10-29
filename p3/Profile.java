/**
 * Filename:   Profile.java
 * Project:    p3
 * Authors:    Zetong Qi, LEC001
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
import java.util.TreeMap;

public class Profile<K extends Comparable<K>, V> {

	public HashTableADT<K, V> hashtable;
	TreeMap<K, V> treemap;
	
	//initialize the hashtable and treemap
	public Profile() {
		this.hashtable = new HashTable<K, V>(5071, 0.75);
		this.treemap = new TreeMap<K, V>();
	}
	
	//insert a key value pair in the hashtable and treemap
	public void insert(K key, V value) {
		this.hashtable.put(key, value);
		this.treemap.put(key, value);
	}
	
	//retrieve an element from hashtable and treemap
	public void retrieve(K key) {
		this.hashtable.get(key);
		this.treemap.get(key);
	}
	
	//insert and retrieve from hashtable and treemap for numElements times
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Expected 1 argument: <num_elements>");
			System.exit(1);
		}

		int numElements = Integer.parseInt(args[0]);
		
		Profile<Integer, Integer> profile = new Profile<Integer, Integer>();
		for (int i = 0; i < numElements; i++)
		{
			profile.insert(i, i+1);
			profile.retrieve(i);
		}

		String msg = String.format("Successfully inserted and retreived %d elements into the hash table and treemap", numElements);
		System.out.println(msg);
	}
}
