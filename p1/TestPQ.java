/**
 * Filename:   TestPQ.java
 * Project:    p1TestPQ
 * Authors:    Debra Deppeler, Zetong Qi
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    9:30am Tuesday/Thrusday Social Sciences
 * 
 * Note: Warnings are suppressed on methods that construct new instances of 
 * generic PriorityQueue types.  The exceptions that occur are caught and 
 * such types are not able to be tested with this program.
 * 
 * Due Date:   Before 10pm on September 17, 2018
 * Version:    2.0
 * 
 * Credits:    TODO name individuals and sources outside of course staff
 *             NOTE: this is an individual assignment, you are not allowed
 *             to view or use code written by anyone but yourself.
 * 
 * Bugs:       no known bugs, but not complete either
 */


import java.util.NoSuchElementException;
import java.util.*;

/**
 * Runs black-box unit tests on the priority queue implementations
 * passed in as command-line arguments (CLA).
 * 
 * If a class with the specified class name does not exist 
 * or does not implement the PriorityQueueADT interface,
 * the class name is reported as unable to test.
 * 
 * If the class exists, but does not have a default constructor,
 * it will also be reported as unable to test.
 * 
 * If the class exists and implements PriorityQueueADT, 
 * and has a default constructor, the tests will be run.  
 * 
 * Successful tests will be reported as passed.
 * 
 * Unsuccessful tests will include:
 *     input, expected output, and actual output
 *     
 * Example Output:
 * Testing priority queue class: PQ01
 *    5 PASSED
 *    0 FAILED
 *    5 TOTAL TESTS RUN
 * Testing priority queue class: PQ02
 *    FAILED test00isEmpty: unexpectedly threw java.lang.NullPointerException
 *    FAILED test04insertRemoveMany: unexpectedly threw java.lang.ArrayIndexOutOfBoundsException
 *    3 PASSED
 *    2 FAILED
 *    5 TOTAL TESTS RUN
 * 
 *   ... more test results here
 * 
 * @author deppeler
 */
public class TestPQ {

	// set to true to see call stack trace for exceptions
	private static final boolean DEBUG = false;

	/**
	 * Run tests to determine if each Priority Queue implementation
	 * works as specified. User names the Priority Queue types to test.
	 * If there are no command-line arguments, nothing will be tested.
	 * 
	 * @param args names of PriorityQueueADT implementation class types 
	 * to be tested.
	 */
	public static void main(String[] args) {
		for (int i=0; i < args.length; i++) 
			test(args[i]);

		if ( args.length < 1 ) 
			print("no PQs to test");
	}

	/** 
	 * Run all tests on each priority queue type that is passed as a classname.
	 * 
	 * If constructing the priority queue in the first test causes exceptions, 
	 * then no other tests are run.
	 * 
	 * @param className the name of the class that contains the 
	 * priority queue implementation.
	 */
	private static void test(String className) {
		print("Testing priority queue class: "+className);
		int passCount = 0;
		int failCount = 0;
		try {

			if (test00isEmpty(className)) passCount++; else failCount++;		
			if (test01getMaxEXCEPTION(className)) passCount++; else failCount++;

			if (test02removeMaxEXCEPTION(className)) passCount++; else failCount++;
			if (test03insertRemoveOne(className)) passCount++; else failCount++;
			if (test04insertRemoveMany(className)) passCount++; else failCount++;
			if (test05duplicatesAllowed(className)) passCount++; else failCount++;
			if (test06manyDataItems(className)) passCount++; else failCount++;
			if (test07constructorTest(className)) passCount++; else failCount++;
			if (test08removeMaxString(className)) passCount++; else failCount++;
			if (test09insertRemoveManyString(className)) passCount++; else failCount++;

			String passMsg = String.format("%4d PASSED", passCount);
			String failMsg = String.format("%4d FAILED", failCount);
			print(passMsg);
			print(failMsg);

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			if (DEBUG) e.printStackTrace();
			print(className + " FAIL: Unable to construct instance of " + className);
		} finally {
			String msg = String.format("%4d TOTAL TESTS RUN", passCount+failCount);
			print(msg);
		}

	}


	/** Confirm that the implementation of priority queue's can insert 1000 elements and
	 * remove top 100 largest strings correctly without throwing any exceptions
	 *
	 * @param className name of the priority queue implementation to test.
	 * @return true if insert removeMax function correctly and no exceptions thrown
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static boolean test09insertRemoveManyString(String className)
	throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		PriorityQueueADT<String> pq = newStringPQ(className);
		PriorityQueue<String> reference = new PriorityQueue<String> (1000, Collections.reverseOrder());
		try
		{
			for (int i = 0; i < 1000; i++)
			{
				String rand = Integer.toString((int)(Math.random() * 100));
				pq.insert(rand);
				reference.add(rand);
			}
			for (int i = 0; i < 100; i++)
			{	
				String trueMax = reference.poll();
				String getMax_max = pq.getMax();
				String max = pq.removeMax();
				//print("removed max" + max + "  true max" + trueMax);
				if (! max.equals(trueMax))
				{
					print("FAILED test09insertRemoveManyString: removed value isn't true max value");
					return false;
				}
				if (! getMax_max.equals(trueMax))
				{
					print("FAILED test09insertRemoveManyString: getMax() didn't return true max value");
					return false;
				}
			}
			return true;
		}
			
			catch (Exception e)
		{
			if (DEBUG) e.printStackTrace();
			print("FAILED test09insertRemoveManyString: unexpectedly threw " + e.getClass().getName());
			return false;
		}
	}

	/** Confirm that the String version of priority queue's removeMax() can work correctly
	 *
	 * @param className name of the priority queue implementation to test.
	 * @return true removeMax() removes max string from priority queue and no exceptions thrown
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static boolean test08removeMaxString(String className)
	throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		PriorityQueueADT<String> pqString = newStringPQ(className);
		try
		{
			String s1 = "1";
			String s2 = "2";
			pqString.insert(s1);
			pqString.insert(s2);
			String removed = pqString.removeMax();
			if (s1.compareTo(s2) < 0 && removed.compareTo(s2) == 0)
			{
				return true;
			}
			else
			{
				print("FAILED test08removeMaxString: removed value isn't string with maximum value");
				return false;
			}
		}
		catch (Exception e)
		{
			if (DEBUG) e.printStackTrace();
			print("FAILED test08removeMaxString: unexpectedly threw " + e.getClass().getName());
			return false;
		}
	}

	/** Confirm that the implementation of priority queue's constructor for Integer,
	 * Double and String PriorityQueueADT as well as insert and remove works correctly
	 *
	 * @param className name of the priority queue implementation to test.
	 * @return true if getMax on empty priority queue throws NoSuchElementException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static boolean test07constructorTest(String className)
	throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		PriorityQueueADT<Integer> pqInt = newIntegerPQ(className);
		PriorityQueueADT<Double> pqDouble = newDoublePQ(className);
		PriorityQueueADT<String> pqString = newStringPQ(className);
		try
		{
			pqInt.insert(5);
			pqDouble.insert(12.35);
			pqString.insert("this_is_a_string");
			int intRemoved = pqInt.removeMax();
			double doubleRemoved = pqDouble.removeMax();
			String stringRemoved = pqString.removeMax();
			if (intRemoved == 5 && doubleRemoved == 12.35 && stringRemoved == "this_is_a_string")
				return true;
			else
				return false;
		}
		catch (Exception e)
		{
			if (DEBUG) e.printStackTrace();
			print("FAILED test07constructorTest: unexpectedly threw " + e.getClass().getName());
			return false;
		}
	}

	/** Confirm that the implementation of priority queue can insert 10000 elements and
	 * remove all of them with any exceptions thrown and the removeMax() and getMax() can
	 * return max value in the queue
	 *
	 * @param className name of the priority queue implementation to test.
	 * @return true if priority queue can correctly insert and remove a large amount of
	 * 	       elements without error or exceptions
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static boolean test06manyDataItems(String className) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		PriorityQueue<Integer> reference = new PriorityQueue<Integer> (10000, Collections.reverseOrder());
		try
		{
			for (int i = 0; i < 10000; i++)
			{
				int rand = (int) (Math.random() * 100);
				pq.insert(rand);
				reference.add(rand);
			}
			for (int i = 0; i < 10000; i++)
			{	
				int trueMax = reference.poll();
				int getMax_max = pq.getMax();
				int max = pq.removeMax();
				//print("removed max" + max + "  true max" + trueMax);
				if (max != trueMax)
				{
					print("FAILED test06manyDataItems: removed value isn't true max value");
					return false;
				}
				if (getMax_max != trueMax)
				{
					print("FAILED test06manyDataItems: getMax() didn't return true max value");
					return false;
				}
			}
			return true;
		}
		catch (Exception e)
		{
			if (DEBUG) e.printStackTrace();
			print("FAILED test06manyDataItems: unexpectedly threw " + e.getClass().getName());
			return false;
		}

		}

	/** Confirm if duplicates are allowed in the priority queue implementation
	 *
	 * @param className name of the priority queue implementation to test.
	 * @return true if duplicates are allowed
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static boolean test05duplicatesAllowed(String className) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try
		{
			pq.insert(5);
			pq.insert(8);
			pq.insert(8);
			int pop1 = pq.removeMax();
			int pop2 = pq.removeMax();
			//print("pop1 " + pop1 + "    pop2 " + pop2);
			if (pop1 == 8 && pop2 == 5)
			{
				print("FAILED test05duplicatesAllowed: no duplicates are allowed");
				return false;
			}
			if (pop1 == pop2)
			{
				return true;
			}
			else
				return false;
		}
		catch (Exception e)
		{
			if (DEBUG) e.printStackTrace();
			print("FAILED test05duplicatesAllowed: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		}


	/** Confirm that the implementation of PriorityQueueADT can insert and 1000 can remove the
	 * top 100 largest values in the priority queue. Fail if the value returned by removeMax() 
	 * or getMax() is not the largest in the currect priority queue and have any exceptions.
	 *
	 * @param className name of the priority queue implementation to test.
	 * @return true if the priority queue can insert and remove many elements correctly
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static boolean test04insertRemoveMany(String className) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		PriorityQueue<Integer> reference = new PriorityQueue<Integer> (1000, Collections.reverseOrder());
		try
		{
			for (int i = 0; i < 1000; i++)
			{
				int rand = (int) (Math.random() * 100);
				pq.insert(rand);
				reference.add(rand);
			}
			for (int i = 0; i < 100; i++)
			{	
				int trueMax = reference.poll();
				int getMax_max = pq.getMax();
				int max = pq.removeMax();
				//print("removed max" + max + "  true max" + trueMax);
				if (max != trueMax)
				{
					print("FAILED test04insertRemoveMany: removed value isn't true max value");
					return false;
				}
				if (getMax_max != trueMax)
				{
					print("FAILED test04insertRemoveMany: getMax() didn't return true max value");
					return false;
				}
			}
			return true;
		}
		catch (Exception e)
		{
			if (DEBUG) e.printStackTrace();
			print("FAILED test04insertRemoveMany: unexpectedly threw " + e.getClass().getName());
			return false;
		}
	}

	/** Confirm that the implementation of the PriorityQueueADT can insert and remove
	 * elements as excepted. Any exceptions indicates a fail.
	 *
	 * @param className name of the priority queue implementation to test.
	 * @return true if the implementation can insert and remove a single element correctly
	 * 		   can the inserted element and the removed element is the same element
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static boolean test03insertRemoveOne(String className) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try
		{
			pq.insert(8);
			int max = pq.removeMax();
			if (max == 8)
				return true;
			else
			{
				print("FAILED test03insertRemoveOne: did not remove the max value in pq");
				return false;
			}
		}
		catch (Exception e)
		{
			if (DEBUG) e.printStackTrace();
			print("FAILED test03insertRemoveOne: unexpectedly threw " + e.getClass().getName());
			return false;
		}
	}

	/** Confirm that removeMax throws NoSuchElementException if called on
	 * an empty priority queue. Any other exception indicates a fail.
	 *
	 * @param className name of the priority queue implementation to test.
	 * @return true if getMax on empty priority queue throws NoSuchElementException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static boolean test02removeMaxEXCEPTION(String className) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try
		{
			pq.removeMax();
		}
		catch (NoSuchElementException e)
		{
			return true;
		}
		catch (Exception e)
		{
			if (DEBUG) e.printStackTrace();
			print("FAILED test02removeMaxEXCEPTION: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test02removeMaxEXCEPTION: removeMax did not throw NoSuchElement exception on newly constructed PQ");
		return false;
	}

	/** DO NOT EDIT -- provided as an example
	 * Confirm that getMax throws NoSuchElementException if called on 
	 * an empty priority queue.  Any other exception indicates a fail.
	 * 
	 * @param className name of the priority queue implementation to test.
	 * @return true if getMax on empty priority queue throws NoSuchElementException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static boolean test01getMaxEXCEPTION(String className) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
			pq.getMax();
		} catch (NoSuchElementException e) {
			return true;			
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test01getMaxEXCEPTION: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test01getMaxEXCEPTION: getMax did not throw NoSuchElement exception on newly constructed PQ");
		return false;
	}

	/** DO NOT EDIT THIS METHOD
	 * @return true if able to construct Integer priority queue and 
	 * the instance isEmpty.
	 * 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static boolean test00isEmpty(String className) 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		PriorityQueueADT<Integer> pq = newIntegerPQ(className);
		try {
		if (pq.isEmpty()) 
			return true;
		} catch (Exception e) {
			if (DEBUG) e.printStackTrace();
			print("FAILED test00isEmpty: unexpectedly threw " + e.getClass().getName());
			return false;
		}
		print("FAILED test00isEmpty: isEmpty returned false on newly constructed PQ");
		return false;
	}

	/** DO NOT EDIT THIS METHOD
	 * Constructs a max Priority Queue of Integer using the class that is name.
	 * @param className The specific Priority Queue to construct.
	 * @return a PriorityQueue
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings({ "unchecked" })
	public static final PriorityQueueADT<Integer> newIntegerPQ(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> pqClass = Class.forName(className);
		Object obj = pqClass.newInstance();	
		if (obj instanceof PriorityQueueADT) {
			return (PriorityQueueADT<Integer>) obj;
		}
		return null;
	}

	/** DO NOT EDIT THIS METHOD
	 * Constructs a max Priority Queue of Double using the class that is named.
	 * @param className The specific Priority Queue to construct.
	 * @return a PriorityQueue
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings({ "unchecked" })
	public static final PriorityQueueADT<Double> newDoublePQ(final String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> pqClass = Class.forName(className);
		Object obj = pqClass.newInstance();	
		if (obj instanceof PriorityQueueADT) {
			return (PriorityQueueADT<Double>) obj;
		}
		return null;
	}

	/** DO NOT EDIT THIS METHOD
	 * Constructs a max Priority Queue of String using the class that is named.
	 * @param className The specific Priority Queue to construct.
	 * @return a PriorityQueue
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings({ "unchecked" })
	public static final PriorityQueueADT<String> newStringPQ(final String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> pqClass = Class.forName(className);
		Object obj = pqClass.newInstance();	
		if (obj instanceof PriorityQueueADT) {
			return (PriorityQueueADT<String>) obj;
		}
		return null;
	}


	/** DO NOT EDIT THIS METHOD
	 * Write the message to the standard output stream.
	 * Always adds a new line to ensure each message is on its own line.
	 * @param message Text string to be output to screen or other.
	 */
	private static void print(String message) {
		System.out.println(message);
	}

}
