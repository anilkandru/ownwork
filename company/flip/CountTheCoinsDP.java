package company.flip;

/**
 * Perfect dynamic programming solution for counting coin combinations
 * problem. Taken from geeksforgeeks website.
 * Doesn't work for bigger numbers of coins and bigger target number.
 * @author Internet
 *
 */
public class CountTheCoinsDP {
	public static void main(String[] args) {
//		int array[] = new int []{10,9,8,7,6,5,4,3,2,1};
//		int array[] = new int []{1,5,10,25,50,100};
		int array[] = new int []{1,2,5};
		System.out.println("final result: "+count(array, array.length, 10));
	}
	static int count( int s[], int m, int n )
	{
	    // table[i] will be storing the number of solutions for
	    // value i. We need n+1 rows as the table is consturcted
	    // in bottom up manner using the base case (n = 0)
	    int table[] = new int[n+1];
	 
	    // Initialize all table values as 0
	    for (int i = 0; i < table.length; i++) {
			table[i] = 0;
		}
	 
	    // Base case (If given value is 0)
	    table[0] = 1;
	 
	    // Pick all coins one by one and update the table[] values
	    // after the index greater than or equal to the value of the
	    // picked coin
	    for(int i=0; i<m; i++) {
	        for(int j=s[i]; j<=n; j++) 
	            table[j] += table[j-s[i]];
	        printArray(table);
	    }
	    return table[n];
	}
	static void printArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ",");
		}
		System.out.println();
	}
}
