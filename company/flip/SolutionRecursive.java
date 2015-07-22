package company.flip;

public class SolutionRecursive {
	public static void main(String[] args) {
		int s[] = new int[]{10,9,8,7,6,5,4,3,2,1};
		int n = 1000;
		System.out.println(count(s, s.length, n));
	}
	
	static int count( int s[], int m, int n )
	{
	    // If n is 0 then there is 1 solution (do not include any coin)
	    if (n == 0)
	        return 1;
	     
	    // If n is less than 0 then no solution exists
	    if (n < 0)
	        return 0;
	 
	    // If there are no coins and n is greater than 0, then no solution exist
	    if (m <=0 && n >= 1)
	        return 0;
	 
	    // count is sum of solutions (i) including S[m-1] (ii) excluding S[m-1]
	    return count( s, m - 1, n ) + count( s, m, n-s[m-1] );
	}
}
