package ru.kpfu.collection;

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
	
	public void swap(int i1, int i2) {
		if (i1 != i2) {
			Object temp = array[i1];
			array[i1] = array[i2];
			array[i2] = temp;
		}
	}
	
	

}
