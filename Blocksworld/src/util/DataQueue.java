package util;

import java.util.PriorityQueue;


public class DataQueue {
	PriorityQueue<State> blocks = new PriorityQueue<State>();

	public State popNextElement() {
		return blocks.poll();
	}

	public boolean isEmpty() {
		return blocks.isEmpty();
	}

	public void addElement(State element) {
		blocks.add(element);
	}

	public int getSize() {
		return blocks.size();
	}
	
	
	
}
