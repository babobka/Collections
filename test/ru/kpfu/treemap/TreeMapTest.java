package ru.kpfu.treemap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ru.kpfu.collection.Comparator;
import ru.kpfu.list.Iterator;
import ru.kpfu.list.List;
import ru.kpfu.map.Map;
import ru.kpfu.tree.TreeMap;

public class TreeMapTest {

	private Map<Integer, String> map;

	private int n = 100000;

	@Before
	public void init() {
		map = new TreeMap<Integer, String>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1 > o2) {
					return 1;
				} else if (o1 < o2) {
					return -1;
				}
				return 0;
			}
		});
	}

	@Test
	public void testEmpty() {
		assertTrue(map.isEmpty());
	}

	@Test
	public void testPut() {
		int key = 0;
		String value = "abc";
		map.put(key, value);
		assertEquals(map.getSize(), 1);
		assertFalse(map.isEmpty());
		assertEquals(map.get(key), value);
		value = "xyz";
		map.put(key, value);
		assertEquals(map.getSize(), 1);
		assertFalse(map.isEmpty());
		assertEquals(map.get(key), value);
	}

	@Test
	public void testPutALot() {

		for (int i = 0; i < n; i++) {
			map.put((int) (Math.random() * n), "abc");
		}
		assertTrue(map.getSize() <= n);
		assertEquals(map.getSize(), map.getKeys().getSize());
	}

	@Test
	public void testRemove() {

		for (int i = 0; i < n; i++) {
			map.put((int) (Math.random() * n), "abc");
		}
		List<Integer> keysList = map.getKeys();
		Iterator<Integer> iterator = keysList.getIterator();
		while (iterator.hasNext()) {
			map.remove(iterator.next());
		}
		assertTrue(map.isEmpty());
		assertEquals(map.getSize(), 0);
		assertEquals(map.getKeys().getSize(), 0);

	}

	@Test
	public void testClear() {

		for (int i = 0; i < n; i++) {
			map.put((int) (Math.random() * n), "abc");
		}
		map.clear();
		assertTrue(map.isEmpty());
		assertEquals(map.getSize(), 0);
		assertEquals(map.getKeys().getSize(), map.getSize());
	}

	@Test
	public void testGetKeys() {

		for (int i = 0; i < n; i++) {
			map.put((int) (Math.random() * n), "abc");
		}
		assertEquals(map.getKeys().getSize(), map.getSize());
	}

	@Test
	public void testContains() {

		for (int i = 0; i < n; i++) {
			map.put((int) (Math.random() * n), "abc");
		}
		List<Integer> keysList = map.getKeys();
		Iterator<Integer> iterator = keysList.getIterator();
		while (iterator.hasNext()) {
			assertTrue(map.containsKey(iterator.next()));
		}
	}

	/*
	 * private boolean isListSorted(List<Integer> list) { Iterator<Integer>
	 * iterator = list.getIterator(); Integer current, previous = null; while
	 * (iterator.hasNext()) { current = iterator.next(); if (previous != null) {
	 * if (current < previous) { return false; } } previous = current;
	 * 
	 * } return true; }
	 */

}
