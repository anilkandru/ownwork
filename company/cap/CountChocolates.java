package company.cap;


public class CountChocolates {
//	static HashMap<Pair, Integer> intermediateResults = new HashMap<CountChocolates.Pair, Integer>();
public static void main(String[] args) {
	CountChocolates test = new CountChocolates();
//	int m = 5, n = 8;
//	int p = 10, q = 12;
	int m = 2500, n = 3500;
	int p = 8000, q = 20000;
	int finalResult = 0;
	for (int i = m; i <= n; i++) {
		for(int j = p; j <= q; j++) {
			finalResult += test.choc(i, j);
		}
	}
	
	System.out.println("Total No. of Combinations: "+finalResult);
}


private int choc(int x, int y) {
//	Integer tempResult = intermediateResults.get(new Pair(x,y));
//	if(tempResult != null) {
//		return tempResult;
//	}
	int length = (x > y)? x : y;
	int breadth = (x < y)? x : y;
	if(length % breadth == 0) {
		return length/breadth;
	} else {
		int temp = 1 + choc(length-breadth, breadth);
//		intermediateResults.put(new Pair(x, y), temp);
		return temp;
	}
	
}

class Pair {
	int x, y;
	
	Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		Pair temp = (Pair)obj;
		if(temp.x == this.x && temp.y == this.y)
			return true;
		else if(temp.x == this.y && temp.y == this.x)
			return true;
		else
			return false;
	}
}
}
