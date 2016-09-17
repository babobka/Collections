package ru.kpfu.hashmap;

import ru.kpfu.linkedlist.LinkedList;
import ru.kpfu.list.Iterator;
import ru.kpfu.list.List;
import ru.kpfu.map.Map;

public class HashMap<K, V> implements Map<K, V> {

	private static final int MIN_SIZE = 10;

	private static final double GROWTH_COEFFICIENT = 1.5D;

	private int size;

	private List<Entry<K, V>>[] collisionListArray = new LinkedList[MIN_SIZE];

	@Override
	public V get(K key) {
		int hash = hash(key);
		List<Entry<K, V>> collisionList = collisionListArray[hash];
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
		List<Entry<K, V>> collisionList = collisionListArray[hash];
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
					collisionListArray[hash] = null;
				}
			}
			size--;
		}
	}

	@Override
	public boolean containsKey(K key) {
		int hash = hash(key);
		if (collisionListArray[hash] == null) {
			return false;
		} else {
			List<Entry<K, V>> collisionList = collisionListArray[hash];
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
			for (int i = 0; i < collisionListArray.length; i++) {
				if (addedCounter >= size) {
					break;
				}
				List<Entry<K, V>> collisionList = collisionListArray[i];
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
		List<Entry<K, V>>[] newCollisionListArray = new LinkedList[(int) (collisionListArray.length
				* GROWTH_COEFFICIENT)];
		for (int i = 0; i < collisionListArray.length; i++) {
			if (collisionListArray[i] != null) {
				List<Entry<K, V>> collisonList = collisionListArray[i];
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

	private int put(List<Entry<K, V>>[] collisionListArray, K key, V value) {
		int added = 0;
		int hash = hash(key, collisionListArray.length);
		if (collisionListArray[hash] == null) {
			List<Entry<K, V>> collisionList = new LinkedList<>();
			collisionList.add(new Entry<>(key, value));
			collisionListArray[hash] = collisionList;
			added = 1;
		} else {
			List<Entry<K, V>> collisionList = collisionListArray[hash];
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
		if (size == collisionListArray.length - 1) {
			expandAndRehash();
		}
		size += put(collisionListArray, key, value);
	}

	private int hash(K k) {
		return hash(k, collisionListArray.length);
	}

	private int hash(K k, int n) {
		return (k.hashCode() * 31) % n;
	}

}
