package ru.kpfu.hashmap;

import ru.kpfu.linkedlist.LinkedList;
import ru.kpfu.list.Iterator;
import ru.kpfu.list.List;
import ru.kpfu.map.Entry;
import ru.kpfu.map.Map;
import ru.kpfu.tree.TreeMap;
import ru.kpfu.util.MathUtil;

public class TreeChainHashMap<K extends Comparable<K>, V> implements Map<K, V> {

	private static final int MIN_SIZE = 11;

	private static final double GROWTH_COEFFICIENT = 1.5D;

	private int size;

	private GenericArray<TreeMap<K, V>> collisionMapArray = new GenericArray<>(MIN_SIZE);

	@Override
	public V get(K key) {
		int hash = hash(key);
		TreeMap<K, V> collisionMap = collisionMapArray.get(hash);
		if (collisionMap != null) {
			return collisionMap.get(key);
		}
		return null;
	}

	@Override
	public void remove(K key) {
		int hash = hash(key);
		TreeMap<K, V> collisionMap = collisionMapArray.get(hash);
		if (collisionMap != null) {
			if (collisionMap.containsKey(key)) {
				collisionMap.remove(key);
				size--;
			}
		}
	}

	@Override
	public boolean containsKey(K key) {
		int hash = hash(key);
		TreeMap<K, V> collisionMap = collisionMapArray.get(hash);
		if (collisionMap == null) {
			return false;
		} else {
			return collisionMap.containsKey(key);
		}
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
		GenericArray<TreeMap<K, V>> newCollisionMapArray = new GenericArray<>(
				MathUtil.getNextPrime((int) (collisionMapArray.getSize() * GROWTH_COEFFICIENT)));
		for (int i = 0; i < collisionMapArray.getSize(); i++) {
			TreeMap<K, V> collisionMap = collisionMapArray.get(i);
			if (collisionMap != null) {
				Iterator<Entry<K, V>> iterator = collisionMap.getEntries().getIterator();
				Entry<K, V> tempEntry;
				while (iterator.hasNext()) {
					tempEntry = iterator.next();
					insert(newCollisionMapArray, tempEntry.getKey(), tempEntry.getValue());
				}
			}
		}
		collisionMapArray = newCollisionMapArray;
	}

	private boolean insert(GenericArray<TreeMap<K, V>> collisionMapArray, K key, V value) {
		int hash = hash(key, collisionMapArray.getSize());
		TreeMap<K, V> collisionMap = collisionMapArray.get(hash);
		if (collisionMap == null) {
			collisionMap = new TreeMap<>();
			collisionMap.put(key, value);
			collisionMapArray.set(hash, collisionMap);
			return true;
		} else {
			return collisionMap.put(key, value);
		}
	}

	@Override
	public boolean put(K key, V value) {
		if (size == collisionMapArray.getSize() - 1) {
			expandAndRehash();
		}
		if (insert(collisionMapArray, key, value)) {
			size++;
			return true;
		}
		return false;
	}

	private int hash(K k) {
		return hash(k, collisionMapArray.getSize());
	}

	private int hash(K k, int n) {
		return Math.abs(k.hashCode() % n);
	}

	@Override
	public void clear() {
		collisionMapArray = new GenericArray<>(MIN_SIZE);
		size = 0;

	}

	@Override
	public List<Entry<K, V>> getEntries() {
		List<Entry<K, V>> list = new LinkedList<>();
		int addedCounter = 0;
		if (!isEmpty()) {
			for (int i = 0; i < collisionMapArray.getSize(); i++) {
				if (addedCounter >= size) {
					break;
				}
				TreeMap<K, V> collisionMap = collisionMapArray.get(i);
				if (collisionMap != null) {
					list.addAll(collisionMap.getEntries());
				}
			}
		}
		return list;
	}

}
