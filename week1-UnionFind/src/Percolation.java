/**
 * 
 */

/**
 * @author yonghui
 *
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {	
	private int dimenSize;
	private int bottomNode;
	
	private boolean[] sites; // whether is open or not in 2-D		
	private int openSite;
	
	private WeightedQuickUnionUF uf;// a UF using the WQUUF from the princeton lib
	// creates n-by-n grid, with all sites initially blocked	
	private WeightedQuickUnionUF ufBottom;
	
	public Percolation(int n) {
		if (n <= 0)
			throw new IndexOutOfBoundsException("dimension number  " + n + "< 0");	
		dimenSize = n; // the dimension
		bottomNode = n*n + 1; // dummyNode set to save the open-nodes bottom edge(2-d)
		sites = new boolean[n*n + 2];
		
		openSite = 0;
		
		ufBottom = new WeightedQuickUnionUF(dimenSize*dimenSize + 2);
		uf = new WeightedQuickUnionUF(dimenSize*dimenSize + 1);	
	}	
	// opens the site(row, col) 
	public void open(int row, int col) {
		validIndex(row,col); // validate index	
		
		if (isOpen(row, col)) return; // mark the site as open
		
		sites[xyTo1D(row, col)] = true; // make it open
		
		if (row == 1) {
			ufBottom.union(xyTo1D(row, col), 0); // connect to bottom	
			uf.union(xyTo1D(row, col), 0);	// connect to top	

		} 
		if (row == dimenSize){
			ufBottom.union(xyTo1D(row, col), bottomNode); // connect to bottom	
		} 
		
		int[] x = {1, -1, 0, 0};
		int[] y = {0, 0, 1, -1};
		
		for (int i = 0; i < 4; i ++) {
			int newx = row + x[i];
			int newy = col + y[i];
			if(isBoundsValid(newx, newy) && isOpen(newx, newy)) {
				ufBottom.union(xyTo1D(row, col), xyTo1D(newx, newy));
				uf.union(xyTo1D(row, col), xyTo1D(newx, newy));
			}			
		}		
//			if (row - 1 >= 1) {if (isOpen(row - 1, col)) uf.union(xyTo1D(row, col), xyTo1D(row, col) - dimenSize);}
//			if (row + 1 <= dimenSize) {if (isOpen(row + 1, col)) uf.union(xyTo1D(row, col), xyTo1D(row, col) + dimenSize);}
//			if (col - 1 >= 1) {if (isOpen(row, col - 1)) uf.union(xyTo1D(row, col), xyTo1D(row, col) - 1);}
//			if (col + 1 <= dimenSize) {if (isOpen(row, col + 1)) uf.union(xyTo1D(row, col), xyTo1D(row, col) + 1);}

		openSite ++;
	}	
	// is the site(row, col) parent == node(1)?
	public boolean isOpen(int row, int col) {
		validIndex(row, col);
		return sites[xyTo1D(row, col)]; //if it is true
	}	
	// is the site(row, col) connect to top? 
	public boolean isFull(int row, int col) {
		validIndex(row,col);
		return uf.find(xyTo1D(row, col)) == uf.find(0);		
	}	
	// returns the number of open sites
	public int numberOfOpenSites() {
		return openSite;
	}	
	// does the system percolate?
	public boolean percolates() {
		return ufBottom.find(0) == ufBottom.find(bottomNode);
		
	}
	// change a 2-dimensional pair to a 1-dimensional index
	private int xyTo1D(int row, int col) {
		return (row - 1)*dimenSize + col;
	}	
	// check whether the index is valid or not	
	private void validIndex(int row, int col) {
		if (row <= 0 || row > dimenSize || col <= 0 || col > dimenSize)
            throw new IllegalArgumentException("index out of range");     	
	}
	
	private boolean isBoundsValid(int row, int col) {
		return !(row > dimenSize || row < 1 || col > dimenSize || col < 1);
	}
	
	// test client (optional)
	public static void main(String[] args) {
		int n = 3;
		Percolation per = new Percolation(n);
		int row_1 = 1, col_1 = 2;
		int row_2 = 2, col_2 = 2;
		//int row_3 = 0, col_3 = 2;
		per.open(row_1, col_1);
		per.open(row_2, col_2);		
		//per.open(row_3, col_3);	

		//System.out.println(per.isOpen(0,0)); 
		System.out.println(per.numberOfOpenSites()); 
		System.out.println("the system is open?" + per.percolates()); 
	}

}
