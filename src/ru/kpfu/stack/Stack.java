package ru.kpfu.stack;

import ru.kpfu.linkedlist.LinkedList;
import ru.kpfu.list.Iterator;

public class Stack<T> implements LastInFirstOut<T> {

	private LinkedList<T> list = new LinkedList<>();

	@Override
	public void push(T value) {
		list.addFirst(value);
	}

	@Override
	public T pop() {
		T value = list.getFirst();
		list.removeFirst();
		return value;
	}

	@Override
	public Iterator<T> getIterator() {
		return list.getIterator();
	}

	public boolean equals(Object o) {
		if (o != null && o instanceof Stack) {
			Stack<?> queue = (Stack<?>) o;
			return list.equals(queue.list);
		}
		return false;
	}

	public int hashCode() {
		return list.hashCode();
	}

	@Override
	public boolean isEmpty() {
		
		return list.isEmpty();
	}

}
