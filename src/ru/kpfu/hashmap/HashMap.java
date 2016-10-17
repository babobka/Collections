package ru.kpfu.hashmap;

import ru.kpfu.linkedlist.LinkedList;
import ru.kpfu.list.Iterator;
import ru.kpfu.list.List;
import ru.kpfu.map.Entry;
import ru.kpfu.map.Map;
import ru.kpfu.util.MathUtil;

public class HashMap<K, V> implements Map<K, V> {

	private static final int MIN_SIZE = 11;

	private static final double GROWTH_COEFFICIENT = 1.5D;

	private int size;

	private GenericArray<List<Entry<K, V>>> collisionListArray = new GenericArray<>(MIN_SIZE);

	@Override
	public V get(K key) {
		int hash = hash(key);
		List<Entry<K, V>> collisionList = collisionListArray.get(hash);
		if (collisionList != null) {
			Iterator<Entry<K, V>> iterator = collisionList.getIterator();
			Entry<K, V> tempEntry;
			while (iterator.hasNext()) {
				tempEntry = iterator.next();
				if (tempEntry.getKey().equals(key)) {
					return tempEntry.getValue();
				}
			}
		}
		return null;
	}

	@Override
	public void remove(K key) {
		int hash = hash(key);
		List<Entry<K, V>> collisionList = collisionListArray.get(hash);
		if (collisionList != null) {
			Iterator<Entry<K, V>> iterator = collisionList.getIterator();
			Entry<K, V> tempEntry;
			int indexToDelete = -1;
			int counter = 0;
			while (iterator.hasNext()) {
				tempEntry = iterator.next();
				if (tempEntry.getKey().equals(key)) {
					indexToDelete = counter;
					break;
				}
				counter++;
			}
			if (indexToDelete != -1) {
				collisionList.remove(indexToDelete);
				if (collisionList.isEmpty()) {
					collisionListArray.set(hash, null);
				}
			}
			size--;
		}
	}

	@Override
	public boolean containsKey(K key) {
		int hash = hash(key);
		if (collisionListArray.get(hash) == null) {
			return false;
		} else {
			List<Entry<K, V>> collisionList = collisionListArray.get(hash);
			Iterator<Entry<K, V>> iterator = collisionList.getIterator();
			Entry<K, V> tempEntry;
			while (iterator.hasNext()) {
				tempEntry = iterator.next();
				if (tempEntry.getKey().equals(key)) {
					return true;
				}
			}
		}
		return false;
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
	public List<K> getKeys() {
		List<K> keysList = new LinkedList<>();
		int addedCounter = 0;
		if (!isEmpty()) {
			for (int i = 0; i < collisionListArray.getSize(); i++) {
				if (addedCounter >= size) {
					break;
				}
				List<Entry<K, V>> collisionList = collisionListArray.get(i);
				Iterator<Entry<K, V>> iterator = collisionList.getIterator();
				while (iterator.hasNext()) {
					keysList.add(iterator.next().getKey());
					addedCounter++;
				}

			}

		}
		return keysList;
	}

	private void expandAndRehash() {
		GenericArray<List<Entry<K, V>>> newCollisionListArray = new GenericArray<>(
				MathUtil.getNextPrime((int) (collisionListArray.getSize() * GROWTH_COEFFICIENT)));
		for (int i = 0; i < collisionListArray.getSize(); i++) {
			if (collisionListArray.get(i) != null) {
				List<Entry<K, V>> collisonList = collisionListArray.get(i);
				Iterator<Entry<K, V>> iterator = collisonList.getIterator();
				Entry<K, V> tempEntry;
				while (iterator.hasNext()) {
					tempEntry = iterator.next();
					put(newCollisionListArray, tempEntry.getKey(), tempEntry.getValue());
				}
			}
		}
		collisionListArray = newCollisionListArray;
	}

	private int put(GenericArray<List<Entry<K, V>>> collisionListArray, K key, V value) {
		int added = 0;
		int hash = hash(key, collisionListArray.getSize());
		if (collisionListArray.get(hash) == null) {
			List<Entry<K, V>> collisionList = new LinkedList<>();
			collisionList.add(new Entry<>(key, value));
			collisionListArray.set(hash, collisionList);
			added = 1;
		} else {
			List<Entry<K, V>> collisionList = collisionListArray.get(hash);
			Iterator<Entry<K, V>> iterator = collisionList.getIterator();
			Entry<K, V> tempEntry;
			boolean keyExists = false;
			while (iterator.hasNext()) {
				tempEntry = iterator.next();
				if (tempEntry.getKey().equals(key)) {
					tempEntry.setValue(value);
					keyExists = true;
					break;
				}
			}
			if (!keyExists) {
				collisionList.add(new Entry<>(key, value));
				added = 1;
			}
		}
		return added;
	}

	@Override
	public void put(K key, V value) {
		if (size == collisionListArray.getSize() - 1) {
			expandAndRehash();
		}
		size += put(collisionListArray, key, value);
	}

	private int hash(K k) {
		return hash(k, collisionListArray.getSize());
	}

	private int hash(K k, int n) {
		return (k.hashCode()) % n;
	}

	@Override
	public void clear() {
		collisionListArray = new GenericArray<>(MIN_SIZE);
		size = 0;

	}

}
