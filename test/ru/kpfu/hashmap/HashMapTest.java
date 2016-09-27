package ru.kpfu.hashmap;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ru.kpfu.map.Map;

public class HashMapTest {

	private Map<Integer, String> map;

	int n=100000;
	
	@Before
	public void init() {
		map = new HashMap<>();
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
	public void testCollision() {
		String key1 = Character.toString((char) 0);
		String key2 = key1 + key1;
		String value1 = "abc";
		String value2 = "xyz";
		Map<String, String> map = new HashMap<String, String>();
		map.put(key1, value1);
		map.put(key2, value2);
		assertEquals(map.getSize(), 2);
		assertEquals(map.get(key1), value1);
		assertEquals(map.get(key2), value2);
		map.remove(key1);
		assertEquals(map.getSize(), 1);
		assertFalse(map.containsKey(key1));
		assertTrue(map.containsKey(key2));

	}

	@Test
	public void testPutALot() {
		for (int i = 0; i < n; i++) {
			map.put(i, "abc");
		}
		
		assertEquals(map.getSize(), n);
	}

	@Test
	public void testRemove() {
		for (int i = 0; i < n; i++) {
			map.put(i, "abc");
		}
		for (int i = 0; i < n; i++) {
			map.put(i, "xyz");
		}

		for (int i = 0; i < n; i++) {
			map.remove(i);
		}
		assertEquals(map.getSize(), 0);
	}

	@Test
	public void testContains() {
		for (int i = 0; i < n; i++) {
			map.put(i, "abc");
		}

		for (int i = 0; i < n; i++) {
			assertTrue(map.containsKey(i));
		}
	}

}
