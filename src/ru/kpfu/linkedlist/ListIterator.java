package ru.kpfu.linkedlist;

import ru.kpfu.list.Iterator;

class ListIterator<T> implements Iterator<T>{

	private Node<T> current;
	
	public ListIterator(Node<T> first)
	{
		current=first;
	}
	
	@Override
	public T next() {
		T value=current.getValue();
		current=current.getNext();
		return value;
	}

	@Override
	public boolean hasNext() {
		if(current!=null)
		{
			return true;
		}
		return false;
	}
	
}
