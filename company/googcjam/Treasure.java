package company.googcjam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Treasure {

	static int startKeys[];
	static int chests[];
	static int[] keyChestMap;
	static HashMap<Integer, List<Integer>> chestKeyMap = new HashMap<Integer, List<Integer>>(); // all keys present in all chests
	static {
		startKeys = new int[]{2};
		chests = new int []{1,2,3,4,5,6,7,8,9,10};
		keyChestMap = new int[]{5,4,1,3,5,2,1,3,3,4}; // key type needed to open a chest
		
		chestKeyMap.put(1, Arrays.asList(3,3));
		chestKeyMap.put(2, Arrays.asList(5,5));
		chestKeyMap.put(3, Arrays.asList(1,4));
		chestKeyMap.put(4, Arrays.asList(3,5));
		chestKeyMap.put(5, Arrays.asList(4));
		chestKeyMap.put(6, Arrays.asList(3));
		chestKeyMap.put(7, Arrays.asList(3));
		chestKeyMap.put(9, Arrays.asList(3));
		chestKeyMap.put(10, Arrays.asList(1,2,3,4));
				
	}
	
	public static void main(String[] args) {
//		int[] chests = new int[]{1,2,3,4};
//		int[] keys = new int[]{1,1,1,2,2,4,4};
		if (!canKeysetOpenGivenChestsEver()) {
			System.out.println("no path");
		} else {
			int[] finalResults = tryUnlock(startKeys, chests);
			for (int i = 0; i < finalResults.length; i++) {
				System.out.print(finalResults[i] + ",");
			}
		}
//		boolean result = canKeysetOpenChests(keys, chests);
//		System.out.println("result is :" + result);
	}
	
	private static int[] tryUnlock(int[] keys, int[] tobeOpened) {
		if (keys.length == 0) {
			return keys;
		} else if (canKeysetOpenAllChestsNow(keys, tobeOpened)) {
			return tobeOpened;
		} else if(tobeOpened.length == 0) {
			return tobeOpened;
		}
		HashSet<Integer> keysTried = new HashSet<Integer>();
		//find out unique keys and try each unique key in the proper order
		for (int i = 0; i < keys.length; i++) {
			if (!keysTried.contains(keys[i])) {
				int newKeyArray[] = getNewArrayByRemovingValue(keys, i); // Array of key types with present key removed.

				// explore all possible chests in lexicographic order for a given keytype
				ArrayList<Integer> possibleChests = getPossiblesChestsForKeyType(keys[i]);
				for(int chest : possibleChests) {
					int chestIndex = getChestIndexFromToBeOpenedList(tobeOpened, chest);
					if(chestIndex == -1){
						continue;
					}
					//remove the chest about to be opened from the tobeOpened list
					int newTobeOpened[] = getNewArrayByRemovingValue(tobeOpened, chestIndex);

					// add the keys inside the just opened chest to the existing list of keys.
					List<Integer> keysInChest = (List<Integer>)chestKeyMap.get(chest);
					if(keysInChest == null) {
						continue;
					}
					int[] resultantKeyArray = new int[keysInChest.size() + newKeyArray.length];
					int k=0;
					for (int keyInChest : keysInChest) {
						resultantKeyArray[k++] = keyInChest;
					}
					System.arraycopy(newKeyArray, 0, resultantKeyArray, k, newKeyArray.length);
					Arrays.sort(resultantKeyArray);

					// call recursively, for the new keyset and the tobeOpened list formed.
					int[] opened = tryUnlock(resultantKeyArray, newTobeOpened);
					if(opened.length == 0) {
						continue;
					}
					else {
						int openedByNow[] = new int[opened.length + 1];
						openedByNow[0] = chest;
						System.arraycopy(opened, 0, openedByNow, 1, opened.length);
						return openedByNow;
					}
				}
			}
			}
		return new int[]{};
	}

	private static int getChestIndexFromToBeOpenedList(int[] tobeOpened,
			int chest) {
		int index = -1;
		for (int j = 0; j < tobeOpened.length; j++) {
			if (chest == tobeOpened[j]) {
				index = j;
			}
		}
		return index;
	}
	
	private static int[] getNewArrayByRemovingValue(int[] originalArray, int index) {
		int[] newArray = new int[originalArray.length - 1];
		int j=0;
		for (int i = 0; i < originalArray.length; i++) {
			if(i != index) {
				newArray[j++] = originalArray[i];
			}
		}
		return newArray;
	}
	
	private static ArrayList<Integer> getPossiblesChestsForKeyType(int keyType) {
		ArrayList<Integer> possibleChests = new ArrayList<Integer>();
		for (int i = 0; i < keyChestMap.length; i++) {
			if(keyChestMap[i] == keyType){
				possibleChests.add(i+1);
			}
		}
		return possibleChests;
	}
	
	/**
	 * Verifies whether a given keyset can open the chests passed.
	 * Expects the keys and chests be sorted
	 * @param keys
	 * @param tobeOpened
	 * @return
	 */
	private static boolean canKeysetOpenAllChestsNow(int[] keys, int[] tobeOpened) {
		
		HashMap<Integer, Integer> keysRequired = new HashMap<Integer, Integer>();
		for (int i = 0; i < tobeOpened.length; i++) {
			int keyType = keyChestMap[tobeOpened[i]-1];
			if(keysRequired.containsKey(keyType)) {
				Integer keyCount = keysRequired.get(keyType);
				keysRequired.put(keyType, keyCount+1);
			} else {
				keysRequired.put(keyType, 1);
			}
		}
		
		HashMap<Integer, Integer> keysPresent = new HashMap<Integer, Integer>();
		for (int i = 0; i < keys.length; i++) {
			int keyType = keys[i];
			if(keysPresent.containsKey(keyType)) {
				Integer keyCount = keysPresent.get(keyType);
				keysPresent.put(keyType, keyCount+1);
			} else {
				keysPresent.put(keyType, 1);
			}
		}
		
		for (int keyType : keysRequired.keySet()) {
			if( !keysPresent.containsKey(keyType) || keysPresent.get(keyType).compareTo(keysRequired.get(keyType)) < 0) {
				return false;
			}
		}
		
		return true;
	}
	
	private static boolean canKeysetOpenGivenChestsEver() {
		ArrayList<Integer> allKeys = new ArrayList<Integer>();
		for(int chest : chestKeyMap.keySet()) {
			allKeys.addAll(chestKeyMap.get(chest));
		}
		for (int i = 0; i < startKeys.length; i++) {
			allKeys.add(startKeys[i]);
		}
		int[] allKeysArray = new int[allKeys.size()];
		int i = 0;
		for (int key : allKeys) {
			allKeysArray[i++] = key;
		}
		Arrays.sort(allKeysArray);
		
		return canKeysetOpenAllChestsNow(allKeysArray, chests);
	}
}
