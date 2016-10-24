package ru.kpfu.hashmap;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import ru.kpfu.map.Map;

public class BadHashMapTest {

	private Map<Integer, String> map;

	private int n = 1000;

	@Before
	public void setUp() {
		map = new BadHashMap<>();
	}

	@Test
	public void testPut() {
		for (int i = 0; i < n; i++) {
			map.put(i, "abc");
		}
		assertEquals(map.getSize(), n);
		assertEquals(map.getKeys().getSize(), n);
	}

	@Test
	public void testRemove() {
		for (int i = 0; i < n; i++) {
			map.put(i, "abc");
		}

		for (int i = 0; i < n; i++) {
			map.remove(i);
		}
		assertEquals(map.getSize(), 0);
	}

	@Test
	public void testCollision() {

		map.put(11, "abc");
		map.put(22, "abc");
		//Fail here
		assertTrue(map.containsKey(11));

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
