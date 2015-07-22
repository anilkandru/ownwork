package company.cap;

public class CountTheChocolates {
	public static void main(String[] args) {
		CountTheChocolates test = new CountTheChocolates();
		// int m = 5, n = 8;
		// int p = 10, q = 12;
		int m = 2500, n = 3500;
		int p = 8000, q = 20000;
		int finalResult = 0;
		for (int i = m; i <= n; i++) {
			for (int j = p; j <= q; j++) {
				finalResult += test.choc(i, j);
			}
		}

		System.out.println("Total No. of Combinations: " + finalResult);
	}

	public static int chocolates(int input1,int input2,int input3,int input4)
    {
		int finalResult = 0;
		for (int i = input1; i <= input2; i++) {
			for (int j = input3; j <= input4; j++) {
				finalResult += choc(i, j);
			}
		}
		return finalResult;
    }
	
	private static int choc(int x, int y) {
		int length = (x > y) ? x : y;
		int breadth = (x < y) ? x : y;
		if (length % breadth == 0) {
			return length / breadth;
		} else {
			int temp = 1 + choc(length - breadth, breadth);
			return temp;
		}
	}

}
