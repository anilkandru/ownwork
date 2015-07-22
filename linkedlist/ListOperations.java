package linkedlist;

import java.util.Random;
import java.util.Stack;

public class ListOperations {
	public static void main(String[] args) {
		add();
	}
	
	static Node add() {
		Node one = formLinkedList(getDigitArray(5));
		System.out.println("=============");
		Node two = formLinkedList(getDigitArray(5));
		System.out.println("=============");
		Node result = addLists(one, two);
		System.out.println("=============");
		printList(result);
		return result;
	}
	
	static void printList(Node start) {
		Node temp = start;
		while(temp != null) {
			System.out.print(temp.value+",");
			temp = temp.next;
		}
	}
	
	/**
	 * Adds two linked lists and return the resulting list.
	 * @param one linked list of plain digits
	 * @param two linked list of plain digits
	 * @return
	 */
	static Node addLists(Node one, Node two) {
		Stack<Integer> stack1 = new Stack<Integer>();
		Stack<Integer> stack2 = new Stack<Integer>();
		Node temp1 = one, temp2 = two;
		while(temp1 != null) {
			stack1.push(temp1.value);
			temp1 = temp1.next;
		}
		while(temp2 != null) {
			stack2.push(temp2.value);
			temp2 = temp2.next;
		}
		Stack<Integer> bigger = null, smaller = null;
		if(stack1.size() >= stack2.size()) {
			bigger = stack1;
			smaller = stack2;
		} else {
			bigger = stack2;
			smaller = stack1;
		}
		Node finalList = null;
		int carry = 0;
		while(!bigger.empty()) {
			int tempSum = 0;
			if(smaller.empty()) {
				tempSum = bigger.pop() + carry;
			} else {
				tempSum = bigger.pop() + smaller.pop() + carry;
			}
			carry = tempSum / 10;
			if(tempSum >= 10) {
				tempSum = tempSum % 10;
			}
			
			Node newNode = new Node();
			newNode.value = tempSum;
			if(finalList == null) {
				finalList = newNode;
			} else {
				newNode.next = finalList;
				finalList = newNode;
			}
		}
		if(carry > 0) {
			Node newNode = new Node();
			newNode.value = carry;
			newNode.next = finalList;
			finalList = newNode;
		}
		
		return finalList;
	}
	
	static int[] getDigitArray(int size) {
		Random random = new Random();
		int a[] = new int[size];
		for (int i = 0; i < size; i++) {
			a[i] = random.nextInt(9);
		}
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + ",");
		}
		return a;
	}
	
	static Node formLinkedList(int[] array) {
		Node start = new Node();
		start.value = array[0];
		Node temp = start;
		for (int i = 1; i < array.length; i++) {
			Node newOne = new Node();
			newOne.value = array[i];
			temp.next = newOne;
			temp = newOne;
		}
		return start;
	}
	
	static class Node {
		int value;
		Node next;
	}
}
