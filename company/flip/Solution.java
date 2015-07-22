
package company.flip;

/**
 * BruteForce. Never do this.
 * @author Anil
 *
 */
public class Solution {
	static int finalSum = 200;
	static int arrayCounter = 0;
//	static int mainArray[] = new int []{5,10,20};
	static int mainArray[] = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
//	static int mainArray[] = new int[] { 1, 2 };
	static int finalCountOfCOmbs = 0;
public static void main(String[] args) {
	for (int i = 0; i < args.length; i++) {
		mainArray[i] = Integer.parseInt(args[i]);
	}
	long startTime = System.currentTimeMillis();
	countCombinations(0, mainArray[0], 0);
	System.out.println("final Result: "+ finalCountOfCOmbs);
	System.out.println("total time: " + (System.currentTimeMillis() - startTime));
}
public static void countCombinations(int valueTillNow, int nextNum, int numberPosition){
	for (int i = 0; i <= finalSum/nextNum; i++) {
		if(i>0){
		valueTillNow += nextNum;
		}
		if(valueTillNow == finalSum){
//			System.out.println("success for: "+nextNum +"when i is at: "+i);
			finalCountOfCOmbs++;
		}
		else if(valueTillNow > finalSum){
			return;
		}
		else
		{
			if(numberPosition == mainArray.length - 1)
				continue;
			else{
				int temp = numberPosition;
			countCombinations(valueTillNow, mainArray[++temp], temp);
			}
			
		}
		
	}
}
}
