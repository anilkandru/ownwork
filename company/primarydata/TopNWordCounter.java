package company.primarydata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class TopNWordCounter {

	public static void main(String[] args) {
		if(args.length < 2) {
			System.err.println("Not enough arguments. ");
			System.err.println("Usage: TopNWordCounter N Path1 Path2 ...");
			System.exit(0);
		}
		int noOfWords = 0;
		try {
			noOfWords = Integer.parseInt(args[0]);
		} catch(NumberFormatException nfe) {
			System.err.println("Bad arguments. ");
			System.err.println("Usage: TopNWordCounter <No of Words> Path1 Path2 ...");
			System.exit(0);
		}
		
		String[] givenPaths = new String[args.length - 1];
		System.arraycopy(args, 1, givenPaths, 0, args.length - 1);
		
		TopNWordCounter counter = new TopNWordCounter();
		counter.scanAndParseFiles(givenPaths, noOfWords);
	}

	private void scanAndParseFiles(String[] paths, int noOfWords) {
		Set<File> validPaths = processPaths(paths);
		Set<File> fileQueue = new HashSet<File>();
		for (File rootPath : validPaths) {
			scanFiles(rootPath, fileQueue);
		}
		
		String configThreadCount = System.getProperty("ThreadCount", "25");
		int threadCount = Integer.parseInt(configThreadCount);
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		CompletionService<Boolean> completionService = new ExecutorCompletionService<Boolean>(executor);
		for (File file : fileQueue) {
			WordCountTask fileTask = new WordCountTask(file);
			completionService.submit(fileTask);
		}
		
		try {
            for (int i = 0, n =  fileQueue.size(); i < n;  i++) {
                completionService.take();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
		executor.shutdownNow();
		// Once all files are processed, print the top most N frequent words.
		printMostFrequentWords(noOfWords);
	}
	
	
	private void printMostFrequentWords(int noOfWords) {
		// In the current case, we sort all values and get the top most N words.
		// Though Max heap of size N is the best option for this scenario, performance gain with
		// the heap is negligible when compared to the heavy file IO we are doing.

		// translate to plain map from atomic long map, for convenience while sorting.
		HashMap<String, Long> plainMap = new HashMap<String, Long>();
		for (String word : wordCountMap.keySet()) {
			plainMap.put(word, wordCountMap.get(word).get());
		}
		
		// sorting the plain map by value.
		Map<String, Long> sortedMap = 
				plainMap.entrySet().stream()
			    .sorted(Entry.comparingByValue())
			    .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
			                              (e1, e2) -> e1, LinkedHashMap::new));
		
		int i = 0;
		System.out.println(noOfWords + " Most frequent words in all given files");
		System.out.println("===========================================");
		int wordSetSize = sortedMap.keySet().size();
		for (String word : sortedMap.keySet()) {
			if(i < wordSetSize - noOfWords) {
				i++;
				continue;
			}
			System.out.println("Word " + word + " occurred " + sortedMap.get(word) + " times");
		}
	}
	
	
	private void scanFiles(File root, Set<File> fileQueue) {
		if(!root.isDirectory()) {
			fileQueue.add(root);
		}
		File[] entries = root.listFiles();
		if (entries != null) {
			for (File entry : entries) {
				if (entry.isDirectory()) {
					scanFiles(entry, fileQueue);
				} else {
					fileQueue.add(entry);
				}
			}
		}
	}
	
	private Set<File> processPaths(String[] args) {
		Set<String> givenPaths = new HashSet<String>();
		for (int i = 0; i < args.length; i++) {
			// ignoring duplicates.
			boolean added = givenPaths.add(args[i]);
			if(!added) {
				System.out.println("ignoring the duplicate path: " + args[i]);
			}
		}
		Set<File> rootPaths = new HashSet<File>();
		for(String path : givenPaths) {
			File file = new File(path);
			if(file.exists()) {
				rootPaths.add(file);
			} else {
				System.out.println("ignoring the invalid path: " + path);
			}
		}
		return rootPaths;
	}
	
	// global word count map
	private ConcurrentHashMap<String, AtomicLong> wordCountMap = new ConcurrentHashMap<String, AtomicLong>();
	
	class WordCountTask implements Callable<Boolean> {
		File file;

		public WordCountTask(File file) {
			this.file = file;
		}

		public Boolean call() throws Exception {
			BufferedReader br = new BufferedReader(new FileReader(file));
			try {
				String currentLine;
				HashMap<String, Long> localWordCountMap = new HashMap<String, Long>();
				while ((currentLine = br.readLine()) != null) {
					StringTokenizer tokenizer = new StringTokenizer(currentLine);
					while(tokenizer.hasMoreTokens()) {
						String word = tokenizer.nextToken();
						
						Long count = localWordCountMap.get(word);
						if(count != null) {
							localWordCountMap.put(word, ++count);
						} else {
							localWordCountMap.put(word, 1L);
						}
					}
				}
				// if file processing is complete, then merge local count with the global one.
				for (String word : localWordCountMap.keySet()) {
					AtomicLong value = wordCountMap.get(word);
					if(value == null) {
						AtomicLong zero = new AtomicLong();
						wordCountMap.putIfAbsent(word, zero);
						value = wordCountMap.get(word);
					}
					value.addAndGet(localWordCountMap.get(word));
				}
			} catch(IOException ioe) {
				// If a file is not read or half read, it's word count won't be considered.
				System.err.println("Error while parsing the file: " + file.getAbsolutePath());
				return false;
			} catch(Throwable t) {
				return false;
			} finally {
				br.close();
			}
			return true;
		}
	}
	
}
