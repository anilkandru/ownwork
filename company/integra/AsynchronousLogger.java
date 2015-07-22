package company.integra;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Logger to log the messages asynchronously to the corresponding files mentioned in the log method call.
 * Assumptions: 
 * This logger instance will be singleton
 * 
 * Instead of handling the exceptions while writing to file, they were thrown back at the moment.
 * 
 * Executor framework is not used, instead the simple threads are used. If the load is so huge, this API will
 * break because it permits creating as many threads as it wants, which is not sustainable. Instead all the writing
 * tasks should be queued and later handled by the executor threadpool.
 * @author Anil kandru
 *
 */
public class AsynchronousLogger {
	
	ConcurrentHashMap<String, FileWriter> writerCache = new ConcurrentHashMap<String, FileWriter>();
	
	public void log(String fileName, String message) throws IOException {
		FileWriter fileWriter = writerCache.get(fileName);
		if(fileWriter == null){
			File logFile = new File(fileName);
			FileWriter writer = new FileWriter(logFile, true);
			writerCache.putIfAbsent(fileName, writer);
		}
		fileWriter = writerCache.get(fileName);
		FileWriteTask fileWriteTask = new FileWriteTask(fileWriter, message);
		Thread writerThread = new Thread(fileWriteTask);
		writerThread.start();
	}

	public void shutdown() throws IOException{
		Iterator<String> iterator = writerCache.keySet().iterator();
		while(iterator.hasNext()){
			FileWriter fileWriter = writerCache.get(iterator.next());
			fileWriter.close();
		}
	}
	
	class FileWriteTask implements Runnable {
		private final String message;
		private final FileWriter fileWriter;

		public FileWriteTask(FileWriter fileWriter, String message) {
			this.fileWriter = fileWriter;
			this.message = message;
		}

		public void run() {
			try {
				synchronized (fileWriter) {
					fileWriter.write(message);
				}
			} catch (IOException ioe) {
				//TODO:
			}
		}
	}
}
