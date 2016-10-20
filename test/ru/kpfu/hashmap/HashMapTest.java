package ru.kpfu.hashmap;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ru.kpfu.map.Map;

public class HashMapTest {

	private Map<Integer, String> map;
	private Map<String, String> collisionMap;

	int n = 100000;

	int collisions = 1000;

	@Before
	public void init() {
		map = new LinkedChainHashMap<>();
		collisionMap = new LinkedChainHashMap<>();
		//map = new TreeChainHashMap<>();
		//collisionMap = new TreeChainHashMap<>();
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

		collisionMap.put(key1, value1);
		collisionMap.put(key2, value2);
		assertEquals(collisionMap.getSize(), 2);
		assertEquals(collisionMap.get(key1), value1);
		assertEquals(collisionMap.get(key2), value2);
		collisionMap.remove(key1);
		assertEquals(collisionMap.getSize(), 1);
		assertFalse(collisionMap.containsKey(key1));
		assertTrue(collisionMap.containsKey(key2));

	}

	@Test
	public void testPutALot() {
		for (int i = 0; i < n; i++) {
			map.put(i, "abc");
		}

		assertEquals(map.getSize(), n);
	}

	//@Test
	public void testCollisionsPutALot() {
		for (int i = 0; i < collisions; i++) {
			collisionMap.put(generateCollisionString(i + 1), "abc");
		}

		assertEquals(collisionMap.getSize(), collisions);
	}

	@Test
	public void testRemoveNotExisting() {
		for (int i = 0; i < n; i++) {
			map.put(i, "abc");
		}
		for (int i = 0; i < n; i++) {
			map.remove(n + i);
		}
		assertFalse(map.isEmpty());
		assertEquals(map.getSize(), n);

	}

	@Test
	public void testCollisionsRemoveALot() {
		int nonCollisions = 10;
		for (int i = 0; i < collisions; i++) {
			collisionMap.put(generateCollisionString(i + 1), "abc");
		}
		for (int i = 0; i < nonCollisions; i++) {
			collisionMap.put(String.valueOf(i), "abc");
		}

		assertEquals(collisionMap.getSize(), collisions + nonCollisions);

		for (int i = 0; i < collisions; i++) {
			collisionMap.remove(generateCollisionString(i + 1));
		}
		assertEquals(collisionMap.getSize(), nonCollisions);

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

	private String generateCollisionString(int chars) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chars; i++) {
			sb.append(Character.toString((char) 0));
		}
		return sb.toString();
	}

}
