package company.googcjam;

import java.math.BigInteger;

/**
 * Maria has been hired by the Ghastly Chemicals Junkies (GCJ) company to help
 * them manufacture bullseyes. A bullseye consists of a number of concentric
 * rings (rings that are centered at the same point), and it usually represents
 * an archery target. GCJ is interested in manufacturing black-and-white
 * bullseyes.
 * 
 * 
 * 
 * Maria starts with t millilitres of black paint, which she will use to draw
 * rings of thickness 1cm (one centimetre). A ring of thickness 1cm is the space
 * between two concentric circles whose radii differ by 1cm.
 * 
 * Maria draws the first black ring around a white circle of radius r cm. Then
 * she repeats the following process for as long as she has enough paint to do
 * so:
 * 
 * Maria imagines a white ring of thickness 1cm around the last black ring. Then
 * she draws a new black ring of thickness 1cm around that white ring. Note that
 * each "white ring" is simply the space between two black rings. The area of a
 * disk with radius 1cm is π cm2. One millilitre of paint is required to cover
 * area π cm2. What is the maximum number of black rings that Maria can draw?
 * Please note that:
 * 
 * Maria only draws complete rings. If the remaining paint is not enough to draw
 * a complete black ring, she stops painting immediately. There will always be
 * enough paint to draw at least one black ring. Input
 * 
 * The first line of the input gives the number of test cases, T. T test cases
 * follow. Each test case consists of a line containing two space separated
 * integers: r and t.
 * 
 * Output
 * 
 * For each test case, output one line containing "Case #x: y", where x is the
 * case number (starting from 1) and y is the maximum number of black rings that
 * Maria can draw.
 * 
 * Limits
 * 
 * Small dataset
 * 
 * 1 ≤ T ≤ 1000. 1 ≤ r, t ≤ 1000.
 * 
 * Large dataset
 * 
 * 1 ≤ T ≤ 6000. 1 ≤ r ≤ 1018. 1 ≤ t ≤ 2 × 1018.
 * 
 * Sample
 * 
 * 
 * Input
 * 
 * 5 1 9 1 10 3 40 1 1000000000000000000 10000000000000000 1000000000000000000
 * 
 * 
 * Output
 * 
 * Case #1: 1 Case #2: 2 Case #3: 3 Case #4: 707106780 Case #5: 49
 * 
 * 
 * 
 */

/**
 * Solution Approach: First black ring will need 2*r+1 ml of paint because it's equal to PI*(r+1)2 - PI*r2 and PIcm2 = 1ml as per the problem description.
 * Afterwards each consecutive black ring needs 4ml extra than the current black ring.(as per the similar formula when worked at x+1 and x levels).
 * Solution has to be written with byte array operations rather than with BigInteger, because it's adding huge overhead in terms of object creations and GC.
 */
public class BullsEye {

	public static void main(String[] args) {

		myTesting();
	}

	private static void myTesting() {
		BigInteger t = new BigInteger("1000000000000000000");
		BigInteger r = new BigInteger("1");
		BigInteger ONE = new BigInteger("1");
		BigInteger temp = r.multiply(new BigInteger("2")).add(ONE);
		t = t.subtract(temp);
		BigInteger ZERO = new BigInteger("0");
		BigInteger FOUR = new BigInteger("4");
		BigInteger i = new BigInteger("1");
		do {
			temp = temp.add(FOUR);
			t = t.subtract(temp);
			i = i.add(ONE);
		} while (t.compareTo(ZERO) >= 0);
		System.out.println(i.subtract(ONE));
	}

}
