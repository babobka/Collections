package ru.kpfu.hash;

import ru.kpfu.collection.GenericArray;
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
				collisionList.removeByIndex(indexToDelete);
				if (collisionList.isEmpty()) {
					collisionListArray.set(hash, null);
				}
				size--;
			}

		}
	}

	@Override
	public boolean containsKey(K key) {
		int hash = hash(key);
		List<Entry<K, V>> collisionList = collisionListArray.get(hash);
		if (collisionList == null) {
			return false;
		} else {
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
		List<Entry<K, V>> entryList = this.getEntries();
		Iterator<Entry<K, V>> iterator = entryList.getIterator();
		while (iterator.hasNext()) {
			keysList.add(iterator.next().getKey());
		}
		return keysList;
	}

	private void expandAndRehash() {
		GenericArray<List<Entry<K, V>>> newCollisionListArray = new GenericArray<>(
				MathUtil.getNextPrime((int) (collisionListArray.getSize() * GROWTH_COEFFICIENT)));
		for (int i = 0; i < collisionListArray.getSize(); i++) {
			List<Entry<K, V>> collisonList = collisionListArray.get(i);
			if (collisonList != null) {
				Iterator<Entry<K, V>> iterator = collisonList.getIterator();
				Entry<K, V> tempEntry;
				while (iterator.hasNext()) {
					tempEntry = iterator.next();
					insert(newCollisionListArray, tempEntry.getKey(), tempEntry.getValue());
				}
			}
		}
		collisionListArray = newCollisionListArray;
	}

	private boolean insert(GenericArray<List<Entry<K, V>>> collisionListArray, K key, V value) {
		int hash = hash(key, collisionListArray.getSize());
		List<Entry<K, V>> collisionList = collisionListArray.get(hash);
		if (collisionList == null) {
			collisionList=new LinkedList<>();
			collisionList.add(new Entry<>(key, value));
			collisionListArray.set(hash, collisionList);
			return true;
		} else {
			Iterator<Entry<K, V>> iterator = collisionList.getIterator();
			Entry<K, V> tempEntry;

			while (iterator.hasNext()) {
				tempEntry = iterator.next();
				if (tempEntry.getKey().equals(key)) {
					tempEntry.setValue(value);
					return false;
				}
			}
			collisionList.add(new Entry<>(key, value));
			return true;
		}
	}

	@Override
	public boolean put(K key, V value) {
		if (size == collisionListArray.getSize() - 1) {
			expandAndRehash();
		}
		if (insert(collisionListArray, key, value)) {
			size++;
			return true;
		}
		return false;
	}

	private int hash(K k) {
		return hash(k, collisionListArray.getSize());
	}

	private int hash(K k, int n) {
		return Math.abs(k.hashCode() % n);
	}

	@Override
	public void clear() {
		collisionListArray = new GenericArray<>(MIN_SIZE);
		size = 0;

	}

	@Override
	public List<Entry<K, V>> getEntries() {
		List<Entry<K, V>> list = new LinkedList<>();
		int addedCounter = 0;
		if (!isEmpty()) {
			for (int i = 0; i < collisionListArray.getSize(); i++) {
				if (addedCounter >= size) {
					break;
				}
				List<Entry<K, V>> collisionList = collisionListArray.get(i);
				if (collisionList != null) {
					list.addAll(collisionList);
				}
			}
		}
		return list;
	}
}
