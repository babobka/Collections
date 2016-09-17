package ru.kpfu.arraylist;

import ru.kpfu.list.Iterator;

public class ArrayListIterator<T> implements Iterator<T> {

	private int currentIndex;

	private T[] array;

	private int size;

	public ArrayListIterator(T[] array, int currentIndex, int size) {
		this.currentIndex = currentIndex;
		this.size = size;
		this.array = array;
	}

	@Override
	public T next() {
		T value = array[currentIndex];
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
