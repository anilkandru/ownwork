package company.groupon;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Bounded Circular Queue with the size configured by client. Calls to enqueue and
 * dequeue are blocked when the Queue is full/empty respectively.
 * 
 */
public class CircularQueue {

	private volatile Integer[] elements = null;
	private int size, tail = 0, head = 0;
	ReentrantLock queueLock = new ReentrantLock();
	Condition notFull = null;
	Condition notEmpty = null;

	public CircularQueue() {
		this.notFull = queueLock.newCondition();
		this.notEmpty = queueLock.newCondition();
	}

	/**
	 * Method to initialize the queue with a size. Initialization is permitted
	 * only once. ReInitialization after one attempt is no-op.
	 * 
	 * @param size
	 *            size of the queue
	 */
	public void initialize(int size) {
		if (elements == null) {
			synchronized (this) {
				if (elements == null) {
					elements = new Integer[size];
					this.size = size;
				}
			}
		}
	}

	/**
	 * Method to fetch an element from the queue.
	 * 
	 * @return Element from the head of the queue
	 */
	public Integer dequeue() throws InterruptedException {
		if (elements == null) {
			throw new IllegalStateException("Queue not initialized");
		}

		Integer element = null;

		queueLock.lock();
		try {
			while (elements[head] == null) {
				notEmpty.await();
			}
			element = elements[head];
			elements[head] = null;
			if (++head == size) {
				head = 0;
			}
//			System.out.println("dequeue");
			notFull.signal();
		} finally {
			queueLock.unlock();
		}

		return element;
	}

	/**
	 * Method to insert an element into the queue.
	 * 
	 * @param element
	 *            Element to insert to the queue.
	 */
	public void enqueue(Integer element) throws InterruptedException {
		if (elements == null) {
			throw new IllegalStateException("Queue not initialized");
		}

		queueLock.lock();
		try {
			while (elements[tail] != null) {
				notFull.await();
			}
			elements[tail] = element;
			if (++tail == size) {
				tail = 0;
			}
			notEmpty.signal();
			printQueue();
		} finally {
			queueLock.unlock();
		}
	}
	
	public void printQueue() {
//		queueLock.lock();
//		try{
		for (int i = 0; i < elements.length; i++) {
			System.out.print(elements[i]+",");
		}
		System.out.println();
//		}finally {
//			queueLock.unlock();
//		}
	}
}
