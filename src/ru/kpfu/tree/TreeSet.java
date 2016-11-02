package ru.kpfu.tree;

import ru.kpfu.collection.Collection;
import ru.kpfu.list.Iterator;
import ru.kpfu.map.Map;
import ru.kpfu.set.Set;

public class TreeSet<T extends Comparable<T>> implements Set<T> {

	private static final Object DUMMY_OBJECT = new Object();

	private Map<T, Object> map = new TreeMap<>();

	@Override
	public void clear() {
		map.clear();

	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public void addAll(Collection<T> collection) {
		Iterator<T> iterator = collection.getIterator();
		while (iterator.hasNext()) {
			map.put(iterator.next(), DUMMY_OBJECT);
		}
	}

	@Override
	public Iterator<T> getIterator() {
		return map.getKeys().getIterator();
	}

	@Override
	public int getSize() {
		return map.getSize();
	}

	@Override
	public boolean add(T value) {
		return map.put(value, DUMMY_OBJECT);
	}

	@Override
	public void remove(T value) {
		map.remove(value);
	}

	@Override
	public boolean contains(T value) {
		return map.containsKey(value);
	}

}
