package ru.kpfu.queue;

import ru.kpfu.linkedlist.LinkedList;
import ru.kpfu.list.Iterator;

public class Queue<T> implements FirstInFirstOut<T> {

	private LinkedList<T> list = new LinkedList<>();

	@Override
	public void enqueue(T t) {
		list.addLast(t);
	}

	@Override
	public T dequeue() {
		T value = list.getFirst();
		list.removeFirst();
		return value;
	}

	@Override
	public Iterator<T> getIterator() {
		return list.getIterator();
	}

	public boolean equals(Object o) {
		if (o != null && o instanceof Queue) {
			Queue<?> queue = (Queue<?>) o;
			return list.equals(queue.list);
		}
		return false;
	}

	public int hashCode() {
		return list.hashCode();
	}

}
