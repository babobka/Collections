package ru.kpfu.hash;

public class GenericArray<T> {

	private final Object[] array;

	public GenericArray(int size) {
		array = new Object[size];
	}

	public int getSize() {
		return array.length;
	}

	public T get(int i) {
		return (T) array[i];
	}

	public void set(int i, T t) {
		array[i] = t;
	}

}
