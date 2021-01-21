// package _2week;

/**
 * @description a client program Permutation.java that takes an integer k as a command-line argument; reads a sequence of strings from standard input using StdIn.readString(); and prints exactly k of them, uniformly at random. 
 * Print each item from the sequence at most once. 
 * @author yonghui
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		if (k == 0)
			return;
		
		int n = 0;
		
		RandomizedQueue<String> q = new RandomizedQueue<>();
		
		while (!StdIn.isEmpty()) {
			n++;
			int indexDequeue = StdRandom.uniform(n);
			String stringIn = StdIn.readString();
			if (n > k) {
				if (indexDequeue >= k)
					continue;
				q.dequeue();
			}
			q.enqueue(stringIn);
		}
		for (int i = 0; i < k; i++)
			StdOut.println(q.dequeue());
	}

}
