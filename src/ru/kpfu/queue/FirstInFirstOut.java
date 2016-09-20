package ru.kpfu.queue;

import ru.kpfu.list.Iterator;

public interface FirstInFirstOut<T> {

	public void enqueue(T t);

	public T dequeue();
	
	public Iterator<T> getIterator();
	
	public boolean isEmpty();

}
