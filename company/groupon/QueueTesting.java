package company.groupon;

public class QueueTesting {
	public static void main(String[] args) {
		QueueTesting testing = new QueueTesting();
		testing.doTest();
	}
	
	CircularQueue myQueue = new CircularQueue();
	public void doTest() {
		myQueue.initialize(5);
		Thread producer = new Thread(new Producer());
		Thread consumer = new Thread(new Consumer());
		Thread producer2 = new Thread(new Producer());
		Thread consumer2 = new Thread(new Consumer());
		
		producer.start();
		consumer.start();
		
		producer2.start();
		consumer2.start();
	}
	
	class Producer implements Runnable {
		public void run() {
			int i = 0;
			while(true) {
				try {
					myQueue.enqueue(new Integer(i));
//					myQueue.printQueue();
					i++;
					if(i==10) break;
					Thread.sleep(1000);
				} catch (InterruptedException e) { }
			}
		}
	}
	
	class Consumer implements Runnable {
		public void run() {
			while(true) {
				try {
//					System.out.println(myQueue.dequeue());
					myQueue.dequeue();
					Thread.sleep(10000);
				} catch (InterruptedException e) { }
			}
		}
	}
}

