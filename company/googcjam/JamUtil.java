package company.googcjam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class JamUtil {
	public static void main(String[] args) {
		processTestData();
	}

	static void processTestData() {
		try{
			BufferedReader reader = new BufferedReader(new FileReader("C:\\Mywork\\Temp\\codeJam\\MyInput\\Treasure\\D-MyPractice.in"));
			int noOfTestCases = Integer.parseInt(reader.readLine());
			for (int i = 0; i < noOfTestCases; i++) {
				String startKeysAndChests = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(startKeysAndChests);
				int startKeysCount = Integer.parseInt(tokenizer.nextToken());
				int chestCount = Integer.parseInt(tokenizer.nextToken());
				TreasureThree.chests = new int[chestCount];
				TreasureThree.keyChestMap = new int[chestCount];
				TreasureThree.startKeys = new int[startKeysCount];
				for (int j = 1; j <= chestCount; j++) {
					TreasureThree.chests[j-1] = j;
				}
				String startKeysLine = reader.readLine();
				StringTokenizer startKeysTokenizer = new StringTokenizer(startKeysLine);
				int k=0;
				while(startKeysTokenizer.hasMoreTokens()) {
					TreasureThree.startKeys[k++] = Integer.parseInt(startKeysTokenizer.nextToken());
				}
				for (int j = 0; j < chestCount; j++) {
					String chestLine = reader.readLine();
					StringTokenizer chestLineTokenizer = new StringTokenizer(chestLine);
					TreasureThree.keyChestMap[j] = Integer.parseInt(chestLineTokenizer.nextToken());
					chestLineTokenizer.nextToken(); //ignore no. of keys inside chest.. Just take care of the keys itself.
					ArrayList<Integer> keysInsideChest = new ArrayList<Integer>();
					while(chestLineTokenizer.hasMoreTokens()){
						keysInsideChest.add(Integer.parseInt(chestLineTokenizer.nextToken()));
					}
					if(keysInsideChest.size() > 0) {
						Collections.sort(keysInsideChest);
						TreasureThree.chestKeyMap.put(j+1, keysInsideChest);
					}
				}

				TreasureThree.main(new String[]{});
				System.out.println();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
