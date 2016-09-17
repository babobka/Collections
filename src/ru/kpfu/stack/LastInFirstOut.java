package ru.kpfu.stack;

import ru.kpfu.list.Iterator;

public interface LastInFirstOut<T> {

	public void push(T t);

	public T pop();
	
	public Iterator<T> getIterator();

}
