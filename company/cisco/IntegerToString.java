package company.cisco;

public class IntegerToString {
	
	public static void main(String[] args) {
		System.out.println(convertToString(123456));
	}

	public static String convertToString(int a) {
		int c;
	    char m;
	    StringBuilder ans = new StringBuilder();
	    // convert the String to int
	    while (a > 0) {
	        c = a % 10;
	        a = a / 10;
	        m = (char) ('0' + c);
	        ans.append(m);
	    }
	    return ans.reverse().toString();
	}
	
}
