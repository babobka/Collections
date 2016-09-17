package ru.kpfu.list;

import ru.kpfu.collection.Collection;

public interface List<T> extends Collection<T>{

	public void add(T t);
	
	public void remove();
	
	public void remove(int i);
	
	public T get(int i);
	
	public T getFirst();
	
	public T getLast();
	
	
	
	
}
