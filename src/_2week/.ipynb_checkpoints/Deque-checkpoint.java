// package _2week;
/**
 * A double-ended queue or deque is a generalization of a stack and a queue that supports adding and removing 
 * items from either the front or the back of the data structure. 
 * @author yonghui
 * @Date
 */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node<Item> head;
    private Node<Item> tail;
    
	// construct an empty deque
	public Deque() {
		size = 0;
		head = null;
		tail = null;
	}
	
	// Node: prev|val|next
	private static class Node<Item> {
		Item val;
		Node<Item> prev;
		Node<Item> next;
		
		Node(Item val) {
			this.val = val;
		}
	}
	
	private void assertItem(Item item) {
	if (item == null) 
		throw new IllegalArgumentException("input cannot be null");
    }
	
	private void assertNotEmpty() {
		if (isEmpty()) 
			throw new NoSuchElementException("deque is empty");
	}
	
	// is the deque empty?
	public boolean isEmpty() {
		return size == 0;
	}	
	// return the number of items on the deque
	public int size() {
		return size;
	}	
	// add the item to the front
	public void addFirst(Item item) {
		assertItem(item);
		
		Node<Item> oldHead = head;
		head = new Node<Item>(item);
		head.next = oldHead; // the next of new head is the old head.
		head.prev = null;
		
		if (oldHead != null)
			oldHead.prev = head;
		size++;
	}

	// add the item to the back
	public void addLast(Item item) {
		assertItem(item);
		
		Node<Item> oldTail = tail;
		tail = new Node<Item>(item);
		tail.next = null;
		tail.prev = oldTail;
		
		if (oldTail != null)
			oldTail.next = tail;
		size++;
	}
	
	// remove and return the item from the front
	public Item removeFirst() {
		assertNotEmpty();
		
		Item headVal = head.val;
		if (size == 1) {
			head = null;
			tail = null;
		} else {
			head = head.next;
			head.prev = null;			
		}
		size--;
		return headVal;
	}
	
	// remove and return the item from the back
	public Item removeLast() {
		assertNotEmpty();
		
		Item tailVal = tail.val;
		
		if (size == 1) {
			tail = null;
			head = null;
		} else {
			tail = tail.prev;
			tail.next = null;
		}
		
		size--;
		return tailVal;
	}
	
	// return an iterator over items in order from front to back
	public Iterator<Item> iterator() {
		return new CustomIterator(head);		
	}
	
	private class CustomIterator implements Iterator<Item> {
		private Node<Item> curr;
		
		CustomIterator(Node<Item> head) {
			curr = head;
		}
		
		public boolean hasNext() {
			return curr.next != null;			
		}
		
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException("No more item.");
			Item currVal = curr.val;
			curr = curr.next;
			return currVal;
		}
		
		public void remove() {
			throw new UnsupportedOperationException("remove command unsupported!");
		}
	}
	
	// unit testing (required)
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Deque<String> deque = new Deque<String>();
		while (!StdIn.isEmpty()) {
			String s = StdIn.readString();
			if (s.equals("+")) {
				if (StdIn.hasNextChar())
					deque.addLast(StdIn.readString());
			} else if (s.equals("-")) {
				StdOut.print(deque.removeFirst() + " ");
				for (String x: deque)
					StdOut.print("iterator left" + x);			
			} else if (s.equals("--")) {
				StdOut.print(deque.removeLast() + " ");
			} else deque.addFirst(s);
		}
		
		StdOut.println();
		for (String i: deque) {
			StdOut.print(i);
		}

	}

}
