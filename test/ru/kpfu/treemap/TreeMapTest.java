package ru.kpfu.treemap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ru.kpfu.arraylist.ArrayList;
import ru.kpfu.list.Iterator;
import ru.kpfu.list.List;
import ru.kpfu.tree.TreeMap;

public class TreeMapTest {

	private TreeMap<Integer, String> map;

	private int n = 10000;

	@Before
	public void init() {
		map = new TreeMap<Integer, String>();
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

		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			list.add(i);
		}
		list.shuffle();
		for (int i = 0; i < n; i++) {
			map.put(list.get(i), "abc");
		}

		assertEquals(map.getSize(), n);
		assertEquals(map.getSize(), map.getKeys().getSize());
	}

	@Test
	public void testPutTheSame() {

		for (int i = 0; i < n; i++) {
			map.put(0, "abc");
		}
		assertEquals(map.getSize(), 1);
	}

	@Test
	public void testContainsInOrder() {

		for (int i = 0; i < n; i++) {
			map.put(i, "abc");
		}

		for (int i = 0; i < n; i++) {
			assertTrue(map.containsKey(i));
		}

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
	public void testRemoveOrdered() {

		for (int i = 0; i < n; i++) {
			map.put(i, "abc");
		}
		for (int i = 0; i < n; i++) {
			map.remove(n - i - 1);
		}
		assertTrue(map.isEmpty());
		assertEquals(map.getSize(), 0);
		assertEquals(map.getKeys().getSize(), 0);

	}

	@Test
	public void testSpecialCaseRemove() {

		int[] keys = { 15, 16, 10, 9, 12, 13, 11 };

		for (int i = 0; i < keys.length; i++) {
			map.put(keys[i], "abc");
		}
		map.remove(10);
		for (int i = 0; i < keys.length; i++) {
			map.remove(keys[i]);
		}

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
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			list.add(i);
		}
		list.shuffle();
		for (int i = 0; i < n; i++) {
			map.put(list.get(i), "abc");
		}
		for (int i = 0; i < n; i++) {
			assertTrue(map.containsKey(list.get(i)));
		}
	}

	@Test
	public void testRemoveALot() {
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			list.add(i);
		}
		list.shuffle();
		for (int i = 0; i < n; i++) {
			map.put(list.get(i), "abc");
		}

		for (int i = 0; i < n; i++) {
			map.remove(list.get(i));
		}

		assertEquals(map.getSize(), 0);
		assertEquals(map.getSize(),map.getKeys().getSize());

	}
	
	
	@Test
	public void testRemoveHalf() {
		int half=n/2;
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			list.add(i);
		}
		list.shuffle();
		for (int i = 0; i < n; i++) {
			map.put(list.get(i), "abc");
		}

		for (int i = 0; i < half; i++) {
			map.remove(list.get(i));
		}
		assertTrue(isListSorted(map.getKeys()));
		assertEquals(map.getSize(), half);
		assertEquals(map.getSize(),map.getKeys().getSize());

	}


	@Test
	public void testSorted() {
		for (int i = 0; i < n; i++) {
			map.put((int) (Math.random() * n), "abc");
		}
		List<Integer> keysList = map.getKeys();
		assertTrue(isListSorted(keysList));

	}

	private boolean isListSorted(List<Integer> list) {
		Iterator<Integer> iterator = list.getIterator();
		Integer current, previous = null;
		while (iterator.hasNext()) {
			current = iterator.next();
			if (previous != null) {
				if (current < previous) {

					return false;
				}
			}
			previous = current;

		}
		return true;
	}

}
