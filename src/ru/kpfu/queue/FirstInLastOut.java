package ru.kpfu.queue;

import ru.kpfu.list.Iterator;

public interface FirstInLastOut<T> {

	public void enqueue(T t);

	public T dequeue();
	
	public Iterator<T> getIterator();

}
