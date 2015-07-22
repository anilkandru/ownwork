package company.googcjam.round1c;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;

public class GarbledEmail {
	public static void main(String[] args) {
		try {
			Dictionary dict = new Dictionary();
			Scanner scanner = new Scanner(new File("C:\\Mywork\\Temp\\codeJam\\MyInput\\GarbledEmail\\garbled_email_dictionary.txt"));
			while(scanner.hasNext()) {
				dict.addString(scanner.nextLine());
			}
			//		System.out.println(dict.findString("aaabcab"));
			//		System.out.println(findMinStrings("cockrjoy", dict, 0));
			System.out.println(findMinChanges("gnhb", dict));
//			lcnbk  zbccc
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	static int findMinStrings(String originalString, Dictionary dict, int index) {
		ArrayList<String> allSubstrings = dict.getAllSubstrings(originalString.substring(index, originalString.length()));
		TreeSet<Integer> sortedSet = new TreeSet<Integer>();
		if(allSubstrings.size() == 0) {
			return -1;
		}
		for (String subString : allSubstrings) {
			if(subString.length() == originalString.length() - index) {
				return 1;
			}
			int subResult = 1 + findMinStrings(originalString, dict, index + subString.length());
			sortedSet.add(subResult);
		}
		sortedSet.remove(0);
		return sortedSet.size() > 0 ? sortedSet.first() : -1;
	}

	static int findMin(String originalString, Dictionary dict) {
		int length = originalString.length();
		int[] results = new int[length+1];
		results[0] = 0;
		for (int i = 1; i <= length; i++) {
			results[i] = length + 1;
		}
		for (int i = 0; i < originalString.length(); i++) {
			ArrayList<String> allSubstrings = dict.getAllSubstrings(originalString.substring(i, originalString.length()));
			for (String subString : allSubstrings) {

				results[i + subString.length()] = Math.min(results[i + subString.length()], 1 + results[i]);

			}
		}
		return results[length];
	}

	/**
	 * return the minimum number of characters to be changed to arrive at the given string and dictionary.
	 * @param originalString
	 * @param dict
	 * @return
	 */
	static int findMinChanges(String originalString, Dictionary dict) {
		int length = originalString.length();
		int[] results = new int[length+1];
		int[] wcPositions = new int[length+1];
		results[0] = 0; wcPositions[0] = length + 1;
		for (int i = 1; i <= length; i++) {
			results[i] = length + 1;
			wcPositions[i] = length +1;
		}
		for (int i = 0; i < originalString.length(); i++) {
			ArrayList<String> allSubstrings = dict.getAllSubstrings(originalString.substring(i, originalString.length()));
			for (String subString : allSubstrings) {

				results[i + subString.length()] = Math.min(results[i + subString.length()], results[i]);
			}
			processWildCard(originalString, i, wcPositions, results, 1, wcPositions[i], dict.root);
		}
		return results[length];
	}

	static void processWildCard(String originalString, int index, int[] wcPositions, int[] results, int wcCount, int recentwcPosition, Dictionary.Node pointer) {
		
		if(index >= originalString.length() ) {
			return;
		}
		
		int i = index;
		int charCount = 0;
		// move to the next possible wild card location.
		if(wcPositions[index] < wcPositions.length && wcCount < 2) {
			while(wcPositions[index] + 5 > i) {
				if(pointer == null || i >= originalString.length()) {
					return;
				} else {
					pointer = pointer.children.get(originalString.charAt(i));
					i++;
				}
			}
		}

		if(pointer == null) {
			return;
		}
		
		// wild card can be at any location after 4 locations from the previous wild card
		while(i < originalString.length()) {
			Dictionary.Node tempPointer = pointer;
			int wcPosition = i;
			for (Character key : pointer.children.keySet()) {
				int j = i + 1;
				if(originalString.charAt(j-1) == key) {
					if(wcCount > 1 && pointer.children.get(key).children != null && pointer.children.get(key).children.containsKey('\n')) {
						if(results[j] > results[index] + (wcCount-1)) {
							results[j] = results[index] + (wcCount-1);
							wcPositions[j] = recentwcPosition;
						}
					}
					continue;
				} else if(key == '\n') {
					continue;
				}
				tempPointer = pointer.children.get(key);
				if(j == originalString.length()) {
					if(tempPointer.children.keySet().contains('\n') && results[j] > results[index] + wcCount) {
						results[j] = results[index] + wcCount;
						wcPositions[j] = wcPosition;
					}
					continue;
				}
				// Ignore the current pointer and traverse to the child of this, because this is a wildcard.
				tempPointer = tempPointer.children.get(originalString.charAt(j));

				charCount = 0;
				while(tempPointer != null && j < originalString.length() && tempPointer.c == originalString.charAt(j) && charCount < 4) {
					if(tempPointer.children.keySet().contains('\n')) {
						//update the results array, that a string ends here.
						//update the wcPositions array with most recent wild card location.
						if(results[j+1] > results[index] + wcCount) {
							results[j+1] = results[index] + wcCount;
							wcPositions[j+1] = wcPosition;
						} else if(results[j+1] == results[index] + wcCount) {
							if(wcPositions[j+1] > wcPosition) {
								wcPositions[j+1] = wcPosition;
							}
						}
					}
					charCount++;
					j++;
					if(j < originalString.length()) {
						tempPointer = tempPointer.children.get(originalString.charAt(j));
					}
				}
				if(tempPointer != null && charCount == 4) {
					processWildCard(originalString, j, wcPositions, results, wcCount+1, wcPosition, tempPointer);
				}
			}

			// move the position to the next character to make that one as wildcard.
			if(!pointer.children.containsKey(originalString.charAt(i))) {
				break;
			} else {
				pointer = pointer.children.get(originalString.charAt(i));
				i++;
			}
		}
	}
}

class Dictionary {

	Node root;

	Dictionary() {
		root = new Node();
		root.c = '\0';
	}

	ArrayList<String> getAllSubstrings(String str) {
		ArrayList<String>  results = new ArrayList<String>();
		Node pointer = root;
		int i = 0;
		while(i < str.length()) {
			if(pointer.children.containsKey(str.charAt(i))) { 
				pointer = pointer.children.get(str.charAt(i));
				if(pointer.children.containsKey('\n')) {
					results.add(str.substring(0, i+1));
				}
				i++;
			} else {
				break;
			}
		}
		return results;
	}

	void addString(String str) {
		Node pointer = root;
		int i = 0;
		while(i < str.length()) {
			if(pointer.children.containsKey(str.charAt(i))) {
				pointer = pointer.children.get(str.charAt(i));
				i++;
				continue;
			}
			if(i == str.length()) {
				break;
			}
			Node newNode = new Node();
			newNode.c = str.charAt(i);
			pointer.children.put(str.charAt(i), newNode);
			pointer = pointer.children.get(str.charAt(i));
			i++;
		}
		pointer.children.put('\n', null);
	}

	boolean findString(String str) {
		Node pointer = root;
		int i = 0;
		while(i < str.length()) {
			if(!pointer.children.containsKey(str.charAt(i))) {
				break;
			}
			pointer = pointer.children.get(str.charAt(i));
			i++;
		}
		if(i == str.length() && pointer.children.containsKey('\n')) {
			return true;
		}
		return false;
	}

	class Node {
		char c;
		HashMap<Character,Node> children = new HashMap<Character,Node>();
	}
}