// package _2week;

/**
 * A randomized queue is similar to a stack or queue, 
 * except that the item removed is chosen uniformly at random among items in the data structure. 
 * @author yonghui
 *
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int size;
	private Item[] randQue;
	private int randIndex;
	
    // construct an empty randomized queue
    public RandomizedQueue() {
    	size = 0;
    	randIndex = 0;
		Item[] a = (Item[]) new Object[2];
        randQue = a;
    }
	
	private void assertItem(Item item) {
	if (item == null) 
		throw new IllegalArgumentException("input cannot be null");
    }
	
	private void assertNotEmpty() {
		if (isEmpty()) 
			throw new NoSuchElementException("Queque is empty");
	}


    // is the randomized queue empty?
    public boolean isEmpty() {
    	return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
    	return size;
    }
    
    private void resize(int capacity) {
    	// assert capacity >= size;
		Item[] resizeArray = (Item[]) new Object[capacity];
    	for (int i = 0; i < size; i++) {
    		resizeArray[i] = randQue[i];
    	}
    	randQue = resizeArray;
    }

    // add the item
    public void enqueue(Item item) {
    	assertItem(item);
    	size++;
    	if (size == randQue.length)
    		resize(2*size);
    	randQue[size - 1] = item;
    }    	

    // remove and return a random item
    public Item dequeue() {
    	assertNotEmpty();
    	Item item = sample();
    	size--;
    	if (randIndex != size)
    		randQue[randIndex] = randQue[size];
    	randQue[size] = null;
    	if (size == randQue.length / 4)
    		resize(randQue.length / 2);
    	return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
    	assertNotEmpty();
    	randIndex = StdRandom.uniform(size);
    	return randQue[randIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
    	return new QueueIterator();
    }
    
    private class QueueIterator implements Iterator<Item> {  
    	private int numNonIterable;
    	private Item[] temp;
    	
    	public QueueIterator() {
    		numNonIterable = size;
			Item[] a = (Item[]) new Object[randQue.length];
    		
    		for (int i = 0; i < randQue.length; i++) {
    			a[i] = randQue[i];
            }
            temp = a;
    	}
    	
    	public boolean hasNext() {
    		return numNonIterable != 0;
    	}
    	
    	public Item next() {
    		if (!hasNext())
    			throw new NoSuchElementException();
    		int rdmIndex = StdRandom.uniform(0, numNonIterable);
    		Item item = temp[rdmIndex];
    		temp[rdmIndex] = temp[--numNonIterable];
    		temp[numNonIterable] = item;
    		
    		return item;
    	}    		
    	
    	public void remove() {
    		throw new UnsupportedOperationException("remove command is not supported");
    	}    	
    }


    // unit testing (required)
    public static void main(String[] args) {
    	
    	/**
    	int n = 5;
    	RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
    	for (int i = 0; i < n; i++)
    	    queue.enqueue(i);
    	for (int a : queue) {
    	    for (int b : queue)
    	        StdOut.print(a + "-" + b + " ");
    	    StdOut.println();
    	}
    
    	 *A possible results:
	    	1-1 1-3 1-2 1-4 1-0 
	    	3-1 3-2 3-0 3-3 3-4 
	    	4-4 4-0 4-2 4-3 4-1 
	    	0-1 0-3 0-0 0-2 0-4 
	    	2-2 2-4 2-3 2-0 2-1 
    	 */
    	
    	RandomizedQueue<String> q = new RandomizedQueue<String>();
    	String s = null;
    	while (!StdIn.isEmpty()) {
    		s = StdIn.readString();
    		if (!s.equals("-"))
    			q.enqueue(s);
    		else
    			StdOut.print(q.dequeue() + " ");
    	}
    	
    	StdOut.println();
    	
    	for (String x: q)
    		StdOut.print(x + " ");
   }



}
