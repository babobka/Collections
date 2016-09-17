package ru.kpfu.collection;

import ru.kpfu.list.Iterator;

public interface Collection<T> {

	public void clear();
	
	public boolean isEmpty();
	
	public void addAll(Collection<T> collection);
	
	public Iterator<T> getIterator();
	
	public int getSize();
	
}
