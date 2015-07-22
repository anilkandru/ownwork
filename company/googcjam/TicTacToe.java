package company.googcjam;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Tic-Tac-Toe-Tomek is a game played on a 4 x 4 square board. The board starts
 * empty, except that a single 'T' symbol may appear in one of the 16 squares.
 * There are two players: X and O. They take turns to make moves, with X
 * starting. In each move a player puts her symbol in one of the empty squares.
 * Player X's symbol is 'X', and player O's symbol is 'O'.
 * 
 * After a player's move, if there is a row, column or a diagonal containing 4
 * of that player's symbols, or containing 3 of her symbols and the 'T' symbol,
 * she wins and the game ends. Otherwise the game continues with the other
 * player's move. If all of the fields are filled with symbols and nobody won,
 * the game ends in a draw. See the sample input for examples of various winning
 * positions.
 * 
 * Given a 4 x 4 board description containing 'X', 'O', 'T' and '.' characters
 * (where '.' represents an empty square), describing the current state of a
 * game, determine the status of the Tic-Tac-Toe-Tomek game going on. The
 * statuses to choose from are:
 * 
 * "X won" (the game is over, and X won) "O won" (the game is over, and O won)
 * "Draw" (the game is over, and it ended in a draw) "Game has not completed"
 * (the game is not over yet) If there are empty cells, and the game is not
 * over, you should output "Game has not completed", even if the outcome of the
 * game is inevitable. Input
 * 
 * The first line of the input gives the number of test cases, T. T test cases
 * follow. Each test case consists of 4 lines with 4 characters each, with each
 * character being 'X', 'O', '.' or 'T' (quotes for clarity only). Each test
 * case is followed by an empty line.
 * 
 * Output
 * 
 * For each test case, output one line containing "Case #x: y", where x is the
 * case number (starting from 1) and y is one of the statuses given above. Make
 * sure to get the statuses exactly right. When you run your code on the sample
 * input, it should create the sample output exactly, including the "Case #1: ",
 * the capital letter "O" rather than the number "0", and so on.
 * 
 * Limits
 * 
 * The game board provided will represent a valid state that was reached through
 * play of the game Tic-Tac-Toe-Tomek as described above.
 * 
 * Small dataset
 * 
 * 1 ≤ T ≤ 10.
 * 
 * Large dataset
 * 
 * 1 ≤ T ≤ 1000.
 * 
 * Sample
 * 
 * 
 * Input
 * 
 * Output
 * 
 * 6 
 * XXXT .... OO.. ....
 * 
 * XOXT XXOO OXOX XXOO
 * 
 * XOX. OX.. .... ....
 * 
 * OOXX OXXX OX.T O..O
 * 
 * XXXO ..O. .O.. T...
 * 
 * OXXX XO.. ..O. ...O
 * 
 * Case #1: X won Case #2: Draw Case #3: Game has not completed Case #4: O won
 * Case #5: O won Case #6: O won
 * 
 * 
 */

/**
 * Solution Approach: Assign good prime numbers for all four possible letters X, O, ., T (7,5,-3,0)
 * Then formulate the sum of those values for all the possible 10 lines i.e)4 rows, 4 columns, 2 diagonals.
 * If any of those sums is divisible by the prime numbers of X or O, it means that the X or O won. (except for 14 when 7 chosen for X, in a special case OOX.)
 * If nobody won and any of the sums are less than 8, it means game is not yet done. Otherwise it's a draw.
 */
public class TicTacToe {
	static int map[];
	static {
		int size = (int) 'X';
		map = new int[size + 1];
		map[(int) 'X'] = 7;
		map[(int) 'O'] = 5;
		map[(int) '.'] = -3;
		map[(int) 'T'] = 0;
	}

	public static void main(String[] args) {
		try {
			processTestData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void processTestData() throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(
				"C:\\Mywork\\Temp\\codeJam\\MyInput\\A-large-practice.in"));
		int row = 0;
		int testCaseID = 1;
		String currentLine;
		int linesSum[] = new int[10];
		while ((currentLine = reader.readLine()) != null) {
			if (currentLine.equals("")) {
				String testResult = verifyTestCase(linesSum);
				System.out.println("Case #"+testCaseID +": "+testResult);
				linesSum = new int[10];
				row = 0;
				testCaseID++;
				continue;
			}
			for (int i = 0; i < 4; i++) {
				char currentChar = currentLine.charAt(i);
				int mapValue = map[(int) currentChar];
				if (row == i) {
					linesSum[8] += mapValue;
				} else if(3 - i == row){
					linesSum[9] += mapValue;
				}
				linesSum[row] += mapValue;
				linesSum[i + 4] += mapValue;
			}
			row++;
		}
		// last test case
		String testResult = verifyTestCase(linesSum);
		System.out.println("Case #"+testCaseID +": "+testResult);
	}

	static String verifyTestCase(int[] linesSum) {
		boolean resultOccurred = false;
		boolean stillSomeBlanks = false;
		String finalResult = "Draw";

		for (int i = 0; i < linesSum.length; i++) {
			if (linesSum[i] % 5 == 0) {
				resultOccurred = true;
				finalResult = "O won";
				break; 
			} else if (linesSum[i] % 7 == 0 && linesSum[i] != 14) {
				finalResult = "X won";
				resultOccurred = true;
				break;
			} else if (linesSum[i] <= 8) {
				stillSomeBlanks = true;
			}
		}
		if (!resultOccurred) {
			if (stillSomeBlanks) {
				finalResult = "Game has not completed";
			}
		}
		return finalResult;
	}

}