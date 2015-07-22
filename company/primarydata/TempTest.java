package company.primarydata;

import java.util.HashMap;

public class TempTest {
public static void main(String[] args) {
	HashMap<String, Long> localWordCountMap = new HashMap<String, Long>();
	localWordCountMap.put("kandru", 10L);
	Long count = localWordCountMap.get("anil");
	if(count != null) {
		localWordCountMap.put("anil", count++);
	} else {
		localWordCountMap.put("anil", 1L);
	}
	Long kandruCount = localWordCountMap.get("kandru");
	kandruCount++;
	System.out.println(localWordCountMap.get("anil"));
	System.out.println(localWordCountMap.get("kandru"));
}
}
