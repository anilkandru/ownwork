
package company.flip;

public class SolutionTwo {
	static int finalSum = 100;
	static int arrayCounter = 0;
//	static int mainArray[] = new int []{5,10,20};
	static int mainArray[] = new int[] { 10,9,8,7,6,5,4,3,2,1 };
//	static int mainArray[] = new int[] { 25, 10, 5, 1 };
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
