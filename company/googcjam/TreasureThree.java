package company.googcjam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class TreasureThree {

	static int startKeys[];
	static int chests[];
	static int[] keyChestMap;
	static HashMap<Integer, List<Integer>> chestKeyMap = new HashMap<Integer, List<Integer>>(); // all keys present in all chests
	
	public static void main(String[] args) {
		int[] finalResults = tryUnlock(startKeys, chests);
		for (int i = 0; i < finalResults.length; i++) {
			System.out.print(finalResults[i] + ",");
		}
	}
	
	private static int[] tryUnlock(int[] keys, int[] tobeOpened) {
		if (keys.length == 0) {
			return keys;
		} 
		else if(tobeOpened.length == 0) {
			return tobeOpened;
		} else if(!canKeysetOpenGivenChestsEver(keys, tobeOpened)) {
			return new int[]{};
		}
		//find out unique keys and try each unique key in the proper order
		HashSet<Integer> keyTrialSequence = getKeyTrialSequence(keys, tobeOpened);
		for(int keyBeingTried : keyTrialSequence) {
			int newKeyArray[] = getNewArrayByRemovingValue(keys, getIndex(keys, keyBeingTried)); 

			ArrayList<Integer> possibleChests = getPossiblesChestsForKeyType(keyBeingTried);
			for(int chest : possibleChests) {
				int chestIndex = getChestIndexFromToBeOpenedList(tobeOpened, chest);
				if(chestIndex == -1){
					continue;
				}
				//remove the chest about to be opened from the tobeOpened list
				int newTobeOpened[] = getNewArrayByRemovingValue(tobeOpened, chestIndex);

				// add the keys inside the just opened chest to the existing list of keys.
				int[] resultantKeyArray;
				List<Integer> keysInChest = (List<Integer>)chestKeyMap.get(chest);
				if(keysInChest != null) {
					resultantKeyArray = new int[keysInChest.size() + newKeyArray.length];
					int k=0;
					for (int keyInChest : keysInChest) {
						resultantKeyArray[k++] = keyInChest;
					}
					System.arraycopy(newKeyArray, 0, resultantKeyArray, k, newKeyArray.length);
					Arrays.sort(resultantKeyArray);
				} else {
					resultantKeyArray = newKeyArray;
				}


				// call recursively, for the new keyset and the tobeOpened list formed.
				if(canKeysetOpenGivenChestsEver(resultantKeyArray, newTobeOpened)) {
					int[] opened = tryUnlock(resultantKeyArray, newTobeOpened);
					int openedByNow[] = new int[opened.length + 1];
					openedByNow[0] = chest;
					System.arraycopy(opened, 0, openedByNow, 1, opened.length);
					return openedByNow;
				}
			}
		}
		return new int[]{};
	}

	private static int getIndex(int[] keys, int keyBeingTried) {
		for (int i = 0; i < keys.length; i++) {
			if(keys[i] == keyBeingTried) {
				return i;
			}
		}
		return -1;
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
	
	private static HashSet<Integer> getKeyTrialSequence(int[] keys, int[] tobeOpened) {
		HashSet<Integer> orderedSet = new LinkedHashSet<Integer>();
		for (int i = 0; i < tobeOpened.length; i++) {
			if(isKeyPresent(keys, keyChestMap[tobeOpened[i]-1])){
				orderedSet.add(keyChestMap[tobeOpened[i]-1]);
			}
			
		}
		return orderedSet;
	}
	
	private static boolean isKeyPresent(int[] keys, int key) {
		for (int i = 0; i < keys.length; i++) {
			if(keys[i] == key) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Verifies whether a given keyset can open the chests passed.
	 * Expects the keys and chests be sorted
	 * @param keys
	 * @param tobeOpened
	 * @return
	 */
	private static boolean canKeysetOpenGivenChests(int[] keys, int[] tobeOpened) {
		
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
	
	private static boolean canKeysetOpenGivenChestsEver(int [] keys, int[] chestsTobeOpened) {
		ArrayList<Integer> allKeys = new ArrayList<Integer>();
		
		for (int i = 0; i < chestsTobeOpened.length; i++) {
			List<Integer> list = chestKeyMap.get(chestsTobeOpened[i]);
			if(list != null) {
				allKeys.addAll(list);
			}
		}
		
		for (int i = 0; i < keys.length; i++) {
			allKeys.add(keys[i]);
		}
		int[] allKeysArray = new int[allKeys.size()];
		int i = 0;
		for (int key : allKeys) {
			allKeysArray[i++] = key;
		}
		Arrays.sort(allKeysArray);
		
		return canKeysetOpenGivenChests(allKeysArray, chestsTobeOpened);
	}
}
