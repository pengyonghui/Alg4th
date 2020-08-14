/**
 * @author yonghui
 * @date 08-12-2020
 */
import edu.princeton.cs.algs4.StdRandom; // generate random numbers
import edu.princeton.cs.algs4.StdStats;// compute the sample mean and sample standard deviation

public class PercolationStats {
	
    // perform independent trials on an n-by-n grid	
	private double[] threshold;
	
    public PercolationStats(int n, int trials) {
    	if (n <= 0 || trials <= 0) throw new IndexOutOfBoundsException("index " + n + "or" + trials + "< 0");		
		 // generate random integers < n*n
			
		int i = 0;
		threshold = new double[trials];
		while (i < trials) {
			Percolation per = new Percolation(n);
			while(!per.percolates()) {	
				int row = StdRandom.uniform(n) + 1;
				int col = StdRandom.uniform(n) + 1;
				if(!per.isOpen(row, col)) per.open(row, col);	
			}	
			threshold[i] = 1.0*per.numberOfOpenSites()/(n*n);
			i += 1;
		}
	}

    // sample mean of percolation threshold
    public double mean() {
    	return StdStats.mean(threshold);    	
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
    	return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
    	return mean() - 1.96*stddev()/java.lang.Math.sqrt(threshold.length);    	
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	return mean() + 1.96*stddev()/java.lang.Math.sqrt(threshold.length);       	
    }

   // test client (see below)
   public static void main(String[] args) {
       int N = 600;
       int T = 100;
       PercolationStats pers = new PercolationStats(N, T);
       System.out.println("mean" + "\t="+ pers.mean());
       System.out.println("stddev" + "\t="+ pers.stddev());
       System.out.println("95% confidence interval" + "\t="+ "[" + pers.confidenceLo() + ", " + pers.confidenceHi() + "]");
   }

}
