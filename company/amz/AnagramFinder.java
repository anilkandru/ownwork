package company.amz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

public class AnagramFinder {
	public static void main(String[] args) {
		try {
			// Read the input from the STDIN
			InputStreamReader inp = new InputStreamReader(System.in);
			BufferedReader bReader = new BufferedReader(inp);
			String inputLine = bReader.readLine();

			// check if input is given at all
			if(inputLine == null || inputLine.trim().equals("")){
				System.out.println("No input is given");
				return;
			}
			
			// Tokenize the input
			StringTokenizer tokenizer = new StringTokenizer(inputLine, " ",
					false);
			LinkedHashMap<String, ArrayList<String>> finalMap = new LinkedHashMap<String, ArrayList<String>>();
			while (tokenizer.hasMoreTokens()) {
				String currentString = tokenizer.nextToken();
				char[] sortedArray = sortString(currentString);
				String sortedString = new String(sortedArray);
				ArrayList<String> presentList = finalMap.get(sortedString);
				if (presentList == null) {
					ArrayList<String> strings = new ArrayList<String>();
					strings.add(currentString);
					finalMap.put(sortedString, strings);
				} else {
					presentList.add(currentString);
				}
			}

			// print groups of anagrams in separate lines
			Iterator<String> iterator = finalMap.keySet().iterator();
			while (iterator.hasNext()) {
				ArrayList<String> arrayList = finalMap.get(iterator.next());
				for (int i = 0; i < arrayList.size(); i++) {
					System.out.print(arrayList.get(i) + " ");
				}
				System.out.println("");
			}
		} catch (IOException e) {
			System.out.println("Exception occurred while reading the input");
		}
	}

	public static char[] sortString(String str) {
		char[] charArray = str.toCharArray();
		Arrays.sort(charArray);
		return charArray;
	}
}
