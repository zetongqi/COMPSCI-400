/**
 * Filename:   MyPQ.java
 * Project:    p1TestPQ
 * Version:    1.0
 * User:       deppeler
 * Date:       Aug 29, 2018
 * Authors:    Debra Deppeler, Zetong Qi
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Instructor: Deppeler (deppeler@cs.wisc.edu)
 * Credits:    n/a
 * Bugs:       no known bugs, but not complete either
 *
 * Due Date:   before 10:00 pm on September 17th
 */


import java.util.*;

/** MyPQ Implementation
 *  MyPQ is an implementation of PriorityQueueADT
 * @param <T>
 *
 */


public class MyPQ<T extends Comparable<T>> implements PriorityQueueADT<T>
{

	/* (non-Javadoc)
	 * @see p1.PriorityQueueADT#isEmpty()
	 */
	private ArrayList<T> queue = new ArrayList<T>();

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	/* an implementation of insertion sort to sort the queue in an increasing order*/
	private void Sort()
	{
		if (this.queue.size() <= 1)
		{
			return;
		}
		else
		{
			T key;
			int i;
			for (int j=1; j<this.queue.size(); j++)
			{
				key = this.queue.get(j);
				i = j-1;
				while(i>-1 && this.queue.get(i).compareTo(key) > 0)
				{
					this.queue.set(i+1, this.queue.get(i));
					i = i-1;
				}
				this.queue.set(i+1, key);
			}
		}
	}

	/* (non-Javadoc)
	 * @see p1.PriorityQueueADT#insert(java.lang.Comparable)
	 */
	@Override
	public void insert(T p) {
		this.queue.add(p);
		this.Sort();
	}

	/* (non-Javadoc)
	 * @see p1.PriorityQueueADT#removeMax()
	 */
	@Override
	public T removeMax() throws NoSuchElementException {
		if (this.queue.size() == 0)
		{
			throw new NoSuchElementException();
		}
		else
		{
			return this.queue.remove(this.queue.size() - 1);
		}
	}

	/* (non-Javadoc)
	 * @see p1.PriorityQueueADT#getMax()
	 */
	@Override
	public T getMax() throws NoSuchElementException {
		if (this.queue.size() == 0)
		{
			throw new NoSuchElementException();
		}
		else
		{
			return this.queue.get(this.queue.size() - 1);
		}
	}

}
