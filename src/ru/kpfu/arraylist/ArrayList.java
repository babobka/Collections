package ru.kpfu.arraylist;

import ru.kpfu.collection.Collection;
import ru.kpfu.collection.GenericArray;
import ru.kpfu.list.Iterator;
import ru.kpfu.list.List;

public class ArrayList<T> implements List<T> {

	private static final int MIN_SIZE = 10;

	private static final double EXPAND_COEFFICIENT = 1.5;

	private GenericArray<T> array = new GenericArray<>(MIN_SIZE);

	private int size;

	public boolean add(T element) {
		if (size == array.getSize() - 1) {
			expandArray();
		}
		array.set(size++, element);
		return true;
	}

	public void removeByIndex(int i) {
		checkRange(i);
		for (int j = i; j < size - 1; j++) {
			array.set(j, array.get(j + 1));
		}
		array.set(size--, null);
	}

	public void set(int i, T value) {
		if (i < size) {
			array.set(i, value);
		}
	}

	public T get(int i) {
		checkRange(i);
		return array.get(i);
	}

	public Iterator<T> getIterator() {
		return new ArrayListIterator<>(array, 0, size);
	}

	private void expandArray() {
		expandArray((int) (array.getSize() * EXPAND_COEFFICIENT));
	}

	private void expandArray(int size) {
		if (size < this.size) {
			throw new IllegalArgumentException("New size can not be lower than current size");
		}
		GenericArray<T> newArray = new GenericArray<>(size);
		for (int i = 0; i < this.size; i++) {
			newArray.set(i, array.get(i));
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
		this.array = new GenericArray<>(MIN_SIZE);
	}

	@Override
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

	public ArrayList<T> copy() {
		ArrayList<T> list = new ArrayList<>();
		Iterator<T> iterator = this.getIterator();
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
		return list;
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
		removeByIndex(0);
	}

	@Override
	public boolean remove(T value) {
		for (int i = 0; i < size; i++) {
			if (array.get(i).equals(value)) {
				removeByIndex(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean contains(T value) {
		for (int i = 0; i < size; i++) {
			if (array.get(i).equals(value)) {
				return true;
			}
		}
		return false;
	}

	public void shuffle() {
		int n = size / 2;
		for (int i = 0; i < n; i++) {
			array.swap(i, (int) (Math.random() * size));
		}
	}

	private void checkRange(int i) {
		if (i < 0 || i > size) {
			throw new IllegalArgumentException("Index " + i + " is out of bounds");
		}

	}

}
