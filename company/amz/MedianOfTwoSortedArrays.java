package company.amz;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MedianOfTwoSortedArrays {
	public static void main(String[] args) {

		// read the input from STDIN
		try {
//			InputStreamReader inp = new InputStreamReader(System.in);
//			BufferedReader bReader = new BufferedReader(inp);
//			String length1 = bReader.readLine();
//			String array1 = bReader.readLine();
//			String length2 = bReader.readLine();
//			String array2 = bReader.readLine();

//			int[] a = getIntArray(array1, length1);
//			int[] b = getIntArray(array2, length2);
//			int len1 = Integer.parseInt(length1);
//			int len2 = Integer.parseInt(length2);

			int a[] = new int[]{9, 10, 15, 17, 25};
			int b[] = new int[]{1, 3, 5, 34, 89, 91, 92, 93, 94, 95};
			int len1 = 5;
			int len2 = 10;
			
			// actual processing

			if ((len1 + len2) % 2 == 0) {
				System.out.println("No median for Even Sized Arrays");
				return;
			}
			int medianPosition = (len1 + len2) / 2;
			int medianCounter = -1;
			int median = 0;
			for (int i = 0, j = 0; i <= medianPosition || j <= medianPosition;) {
				if (i < len1 && j < len2) {
					if (a[i] < b[j]) {
						median = a[i];
						i++;
					} else {
						median = b[j];
						j++;
					}
					medianCounter++;
				} else {
					int index = 0;
					if (i == len1) {
						index = medianPosition - i;
						System.out.println(b[index]);
					} else {
						index = medianPosition - j;
						System.out.println(a[index]);
					}
					break;
				}

				if (medianCounter == medianPosition) {
					System.out.println(median);
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Error occurred while reading the input");
		}
	}

	private static int[] getIntArray(String str, String arrayLength) {
		StringTokenizer tokenizer = new StringTokenizer(str, " ", false);
		int[] numArray = new int[Integer.parseInt(arrayLength)];
		int i = 0;
		while (tokenizer.hasMoreTokens()) {
			String nextToken = tokenizer.nextToken();
			int presentNum = Integer.parseInt(nextToken);
			numArray[i++] = presentNum;
		}
		return numArray;
	}

}