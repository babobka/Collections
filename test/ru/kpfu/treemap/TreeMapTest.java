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
		int n = 100;
		for (int i = 0; i < n; i++) {
			map.put(i, "abc");
		}
		assertEquals(map.getSize(), n);
		assertTrue(isListSorted(map.getKeys()));
	}

	@Test
	public void testGetKeys() {
		int n = 1000;
		for (int i = 0; i < n; i++) {
			map.put((int) (Math.random() * n), "abc");
		}
		assertTrue(isListSorted(map.getKeys()));
	}

	@Test
	public void testContains() {
		int n = 100;
		for (int i = 0; i < n; i++) {
			map.put(i, "abc");
		}
		for (int i = 0; i < n; i++) {
			assertTrue(map.containsKey(i));
		}
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
