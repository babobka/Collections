package ru.kpfu.map;

import ru.kpfu.list.List;

public interface Map<K, V> {

	public void put(K key, V value);

	public V get(K key);

	public void remove(K key);

	public boolean containsKey(K key);

	public boolean isEmpty();

	public int getSize();
	
	public void clear();

	public List<K> getKeys();

}
