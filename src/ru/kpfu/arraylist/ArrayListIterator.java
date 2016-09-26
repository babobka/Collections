package ru.kpfu.arraylist;

import ru.kpfu.list.Iterator;

public class ArrayListIterator<T> implements Iterator<T> {

	private int currentIndex;

	private Object[] array;

	private int size;

	public ArrayListIterator(Object[] array2, int currentIndex, int size) {
		this.currentIndex = currentIndex;
		this.size = size;
		this.array = array2;
	}

	@Override
	public T next() {
		T value = (T)array[currentIndex];
		currentIndex++;
		return value;
	}

	@Override
	public boolean hasNext() {
		if (currentIndex < size) {
			return true;
		}
		return false;
	}

}
