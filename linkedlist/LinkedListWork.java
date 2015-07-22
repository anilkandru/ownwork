package linkedlist;

import util.Util;

public class LinkedListWork {

	public static void main(String[] args) {
		int a[] = Util.getRandomNumbers(10);
		Element start = constructList(a);
		traverseList(start);
		System.out.println("======================");
		Element newStart = reverseList(start);
		traverseList(newStart);
	}
	
	public static Element constructList(int a[]) {
		Element start = new Element(a[0]);
		Element temp = start;
		for (int i = 1; i < a.length; i++) {
			Element newElem = new Element(a[i]);
			temp.next = newElem;
			temp = newElem;
		}
		return start;
	}
	
	public static void traverseList(Element start) {
		Element temp = start;
		while(temp != null) {
			System.out.print(temp.value + ",");
			temp = temp.next;
		}
	}
	
	public static Element reverseList(Element start) {
		Element t = start;
		Element t1 = start.next;
		Element t2 = null;
		if(t1 != null) {
			t2 = t1.next;
		}
		Element old = null;
		while(t1 != null) {
			t.next = old;
			t1.next = t;
			old = t1;
			if(t2 == null) {
				t = old;
				break;
			} else {
				t = t2;
			}
			if(t != null) {
				t1 = t.next;
			}
			if(t1 != null) {
				t2 = t1.next;
			} else {
				t.next = old;
			}
		}
		return t;
	}
	
}

class Element {
	int value;
	Element next;
	
	Element(int value) {
		this.value = value;
	}
}