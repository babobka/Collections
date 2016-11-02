package ru.kpfu.set;

import ru.kpfu.collection.Collection;

public interface Set<T> extends Collection<T>{
	
	public boolean add(T value);
	
	public void remove(T value);
	
	public boolean contains(T value);

}
