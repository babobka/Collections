package ru.kpfu.hashmap;

import ru.kpfu.linkedlist.LinkedList;
import ru.kpfu.list.Iterator;
import ru.kpfu.list.List;
import ru.kpfu.map.Entry;
import ru.kpfu.map.Map;
import ru.kpfu.util.MathUtil;

public class BadHashMap<K, V> implements Map<K, V> {

	private GenericArray<Entry<K, V>> array;

	private static final int BEGIN_SIZE = 11;

	private int size;

	public BadHashMap() {
		this.array = new GenericArray<>(BEGIN_SIZE);
	}

	@Override
	public boolean put(K key, V value) {
		if (size == array.getSize() - 1) {
			expandAndRehash();
		}
		int hashCode = hash(key);
		Entry<K, V> entry = array.get(hashCode);
		if (entry != null && entry.getKey().equals(key)) {
			entry.setValue(value);
			return false;
		}
		array.set(hashCode, new Entry<>(key, value));
		size++;
		return true;

	}

	@Override
	public V get(K key) {
		Entry<K, V> entry = array.get(hash(key));
		if (entry != null && entry.getKey().equals(key)) {
			return entry.getValue();
		}
		return null;
	}

	@Override
	public void remove(K key) {
		int hashCode = hash(key);
		Entry<K, V> entry = array.get(hashCode);

		if (entry != null && entry.getKey().equals(key)) {

			size--;
			array.set(hashCode, null);
		}

	}

	@Override
	public boolean containsKey(K key) {
		Entry<K, V> entry = array.get(hash(key));
		return entry != null && entry.getKey().equals(key);
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int getSize() {

		return size;
	}

	@Override
	public void clear() {
		size = 0;
		this.array = new GenericArray<>(BEGIN_SIZE);
	}

	private void expandAndRehash() {
		GenericArray<Entry<K, V>> newArray = new GenericArray<>(MathUtil.getNextPrime(array.getSize()));
		for (int i = 0; i < array.getSize(); i++) {
			Entry<K, V> entry = array.get(i);
			if (entry != null) {
				newArray.set(hash(entry.getKey(), newArray.getSize()), entry);
			}
		}
		this.array = newArray;
	}

	@Override
	public List<K> getKeys() {
		List<Entry<K, V>> entries = getEntries();
		List<K> keys = new LinkedList<>();
		Iterator<Entry<K, V>> iterator = entries.getIterator();
		while (iterator.hasNext()) {
			keys.add(iterator.next().getKey());
		}
		return keys;
	}

	@Override
	public List<Entry<K, V>> getEntries() {
		List<Entry<K, V>> entries = new LinkedList<>();

		for (int i = 0; i < array.getSize(); i++) {
			Entry<K, V> entry = array.get(i);
			if (entry != null)
				entries.add(entry);
		}

		return entries;
	}

	private int hash(K k) {
		return hash(k, array.getSize());
	}

	private int hash(K k, int n) {
		return Math.abs(k.hashCode() % n);
	}

}
