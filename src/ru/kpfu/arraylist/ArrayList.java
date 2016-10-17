package ru.kpfu.arraylist;

import java.util.Arrays;

import ru.kpfu.collection.Collection;
import ru.kpfu.list.Iterator;
import ru.kpfu.list.List;

public class ArrayList<T> implements List<T>, Cloneable {

	private static final int MIN_SIZE = 10;

	private static final double EXPAND_COEFFICIENT = 1.5;

	private Object[] array = new Object[MIN_SIZE];

	private int size = 0;

	public void add(T element) {
		if (size == array.length - 1) {
			expandArray();
		}
		array[size] = element;
		size++;
	}

	public void remove(int i) {
		if (i < 0 || i > size) {
			throw new IllegalArgumentException("Index " + i + " is out of bounds");
		}
		for (int j = i; j < size - 1; j++) {
			array[j] = array[j + 1];
		}
		array[size - 1] = null;
		size--;
	}

	public void set(int i, T value) {
		if (i < size) {
			array[i] = value;
		}
	}

	public T get(int i) {
		if (i < 0 || i > size) {
			throw new IllegalArgumentException("Index " + i + " is out of bounds");
		}
		return (T) array[i];
	}

	public Iterator<T> getIterator() {
		return new ArrayListIterator<>(array, 0, size);
	}

	private void expandArray() {
		expandArray((int) (array.length * EXPAND_COEFFICIENT));
	}

	private void expandArray(int size) {
		if (size < this.size) {
			throw new IllegalArgumentException("New size can not be lower than current size");
		}
		Object[] newArray = new Object[size];
		for (int i = 0; i < this.size; i++) {
			newArray[i] = array[i];
		}
		array = newArray;
	}

	public int getSize() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		this.size = 0;
		this.array = new Object[MIN_SIZE];
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		String separator = "";
		Iterator<T> iterator = this.getIterator();
		while (iterator.hasNext()) {
			sb.append(separator);
			sb.append(iterator.next());
			separator = ", ";
		}
		return sb.toString();
	}

	public T getFirst() {
		if (!isEmpty()) {
			return get(0);
		}
		return null;
	}

	public T getLast() {
		if (!isEmpty()) {
			return get(size - 1);
		}
		return null;
	}

	public ArrayList<T> clone() {
		ArrayList<T> list = new ArrayList<>();
		Iterator<T> iterator = this.getIterator();
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
		return list;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(array);
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArrayList<?> other = (ArrayList<?>) obj;
		if (!Arrays.equals(array, other.array))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	@Override
	public void addAll(Collection<T> collection) {
		if (!collection.isEmpty()) {
			expandArray(this.size + collection.getSize());
			Iterator<T> iterator = this.getIterator();
			while (iterator.hasNext()) {
				this.add(iterator.next());
			}
		}

	}

	@Override
	public void remove() {
		remove(0);
	}

}
