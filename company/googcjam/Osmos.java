package company.googcjam;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Armin is playing Osmos, a physics-based puzzle game developed by Hemisphere
 * Games. In this game, he plays a "mote", moving around and absorbing smaller
 * motes.
 * 
 * A "mote" in English is a small particle. In this game, it's a thing that
 * absorbs (or is absorbed by) other things! The game in this problem has a
 * similar idea to Osmos, but does not assume you have played the game.
 * 
 * When Armin's mote absorbs a smaller mote, his mote becomes bigger by the
 * smaller mote's size. Now that it's bigger, it might be able to absorb even
 * more motes. For example: suppose Armin's mote has size 10, and there are
 * other motes of sizes 9, 13 and 19. At the start, Armin's mote can only absorb
 * the mote of size 9. When it absorbs that, it will have size 19. Then it can
 * only absorb the mote of size 13. When it absorbs that, it'll have size 32.
 * Now Armin's mote can absorb the last mote.
 * 
 * Note that Armin's mote can absorb another mote if and only if the other mote
 * is smaller. If the other mote is the same size as his, his mote can't absorb
 * it.
 * 
 * You are responsible for the program that creates motes for Armin to absorb.
 * The program has already created some motes, of various sizes, and has created
 * Armin's mote. Unfortunately, given his mote's size and the list of other
 * motes, it's possible that there's no way for Armin's mote to absorb them all.
 * 
 * You want to fix that. There are two kinds of operations you can perform, in
 * any order, any number of times: you can add a mote of any positive integer
 * size to the game, or you can remove any one of the existing motes. What is
 * the minimum number of times you can perform those operations in order to make
 * it possible for Armin's mote to absorb every other mote?
 * 
 * For example, suppose Armin's mote is of size 10 and the other motes are of
 * sizes [9, 20, 25, 100]. This game isn't currently solvable, but by adding a
 * mote of size 3 and removing the mote of size 100, you can make it solvable in
 * only 2 operations. The answer here is 2.
 * 
 * Input
 * 
 * The first line of the input gives the number of test cases, T. T test cases
 * follow. The first line of each test case gives the size of Armin's mote, A,
 * and the number of other motes, N. The second line contains the N sizes of the
 * other motes. All the mote sizes given will be integers.
 * 
 * Output
 * 
 * For each test case, output one line containing "Case #x: y", where x is the
 * case number (starting from 1) and y is the minimum number of operations
 * needed to make the game solvable.
 * 
 * Limits
 * 
 * 1 ≤ T ≤ 100. Small dataset
 * 
 * 1 ≤ A ≤ 100. 1 ≤ all mote sizes ≤ 100. 1 ≤ N ≤ 10.
 * 
 * Large dataset
 * 
 * 1 ≤ A ≤ 106. 1 ≤ all mote sizes ≤ 106. 1 ≤ N ≤ 100.
 * 
 */
public class Osmos {
	static int[] list;
	static int start;

	public static void main(String[] args) {

		// list = new int[]{2, 4, 8, 16, 32, 64, 100, 100, 100, 100};
		// list = new int[]{100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
		// list = new int[]{9, 64, 19, 16, 81, 80};
		// list = new int[]{27, 729, 9, 243, 81};
		// start = 3;
		// myTesting(start);
		// list = new int[] { 11, 20, 60, 22, 100 };
		// list = new int[] { 46, 80, 59, 62, 7, 31, 89, 71, 94 };
		// list = new int[] { 74, 11, 52, 15, 9, 62, 6, 95};
		// list = new int[] {91, 1, 1, 6, 33, 15, 14};
		readAndExecuteTests();
	}

	private static void myTesting(int start) {
		int currentOptimal = list.length, tempOpsCount = 0;
		int index = 0, presentTotal = start;
		Arrays.sort(list);
		while (index < list.length) {
			while (index < list.length && list[index] < presentTotal) {
				presentTotal += list[index];
				index++;
			}
			int motesTobeAdded = 0;
			int remainingMotes = (list.length - (index) >= 0) ? list.length
					- (index) : 0;
			if (index < list.length) {

				if (tempOpsCount > currentOptimal || presentTotal == 1) {
					break;
				} else {
					if (tempOpsCount + remainingMotes < currentOptimal)
						currentOptimal = tempOpsCount + remainingMotes;
				}

				motesTobeAdded = (int) getMotes(presentTotal,
						(presentTotal == list[index]) ? list[index] + 1
								: list[index]);
				presentTotal = (int) eatMotes(presentTotal, motesTobeAdded);

				tempOpsCount = tempOpsCount + motesTobeAdded;
				presentTotal += list[index];
				index++;

			} else {
				currentOptimal = (currentOptimal > tempOpsCount) ? tempOpsCount
						: currentOptimal;
			}
		}
		System.out.println(currentOptimal);
	}

	private static void executeTest(Scanner sc, PrintWriter pw) {
		int start = sc.nextInt();
		int noOfElements = sc.nextInt();
		int list[] = new int[noOfElements];
		sc.nextLine();
		for (int i = 0; i < noOfElements; i++) {
			list[i] = sc.nextInt();
		}
		int currentOptimal = list.length, tempOpsCount = 0;

		int index = 0, presentTotal = start;
		Arrays.sort(list);
		while (index < list.length) {
			while (index < list.length && list[index] < presentTotal) {
				presentTotal += list[index];
				index++;
			}
			int motesTobeAdded = 0;
			int remainingMotes = (list.length - (index) >= 0) ? list.length
					- (index) : 0;
			if (index < list.length) {

				if (tempOpsCount > currentOptimal || presentTotal == 1) {
					break;
				} else {
					if (tempOpsCount + remainingMotes < currentOptimal)
						currentOptimal = tempOpsCount + remainingMotes;
				}

				motesTobeAdded = (int) getMotes(presentTotal,
						(presentTotal == list[index]) ? list[index] + 1
								: list[index]);
				presentTotal = (int) eatMotes(presentTotal, motesTobeAdded);

				tempOpsCount = tempOpsCount + motesTobeAdded;
				presentTotal += list[index];
				index++;

			} else {
				currentOptimal = (currentOptimal > tempOpsCount) ? tempOpsCount
						: currentOptimal;
			}
		}
		pw.print(currentOptimal);
	}

	private static void readAndExecuteTests() {
		String base = "C:\\Mywork\\Temp\\codeJam\\MyInput\\Osmos\\";
		String input = base + "A-small-practice.in";
		String output = base + "A-small-practice.out";

		try {
			Scanner sc = new Scanner(new FileReader(input));
			PrintWriter pw = new PrintWriter(output);

			int n = sc.nextInt();
			sc.nextLine();
			for (int c = 0; c < n; c++) {
				System.out.println("Test case " + (c + 1) + "...");
				pw.print("Case #" + (c + 1) + ": ");
				executeTest(sc, pw);
				pw.println();
			}
			pw.println();
			pw.flush();
			pw.close();
			sc.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Treasure.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	/**
	 * return value should be greater than log(((end-start)/(start-1)) + 1)
	 * Based on the formula of the sum of a geometric progression.
	 * This method is finding out the number of motes to be added, given the start mote weight and the 
	 * target value of the mote.
	 * @param start
	 * @param end
	 */
	private static double getMotes(double start, double end) {
		double result = ((end - start) / (start - 1)) + 1;
		result = Math.log(result) / Math.log(2);
		if (Math.ceil(result) - result == 0) {
			return result + 1;
		}
		return Math.ceil(result);
	}

	private static double eatMotes(double start, int noOfMotes) {
		double result = start + (start - 1) * (Math.pow(2, noOfMotes) - 1);
		return result;
	}
}
