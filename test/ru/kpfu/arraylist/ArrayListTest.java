package ru.kpfu.arraylist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ru.kpfu.list.Iterator;

public class ArrayListTest {

	private ArrayList<Integer> list;
	
	int n = 10000;

	@Before
	public void init() {
		list = new ArrayList<>();
	}

	@Test
	public void testEmpty() {
		assertTrue(list.isEmpty());
	}

	@Test
	public void testAdd() {
		int first = 10;
		int last = 11;
		list.add(first);
		assertTrue(!list.isEmpty());
		assertEquals(list.getSize(), 1);
		assertEquals(list.getFirst(), list.getLast());
		list.add(last);
		assertEquals(list.getSize(), 2);
		assertNotEquals(list.getFirst(), list.getLast());
		assertTrue(list.getFirst().equals(first) && list.getLast().equals(last));
	}

	@Test
	public void testRemove() {

		for (int i = 0; i < n; i++) {
			list.add(i);
		}

		for (int i = 0; i < n; i++) {
			list.remove();

		}
		assertEquals(list.getSize(), 0);
		assertTrue(list.isEmpty());
	}

	@Test
	public void testClear() {
		int value = 10;
		list.add(value);
		list.add(value);
		list.clear();
		assertTrue(list.isEmpty());
		assertEquals(list.getSize(), 0);
	}

	@Test
	public void testIterator() {
	
		for (int i = 0; i < n; i++) {
			list.add(i);
		}
		assertEquals(list.getSize(), n);
		Iterator<Integer> iterator = list.getIterator();
		int counter = 0;
		while (iterator.hasNext()) {
			iterator.next();
			counter++;
		}
		assertEquals(n, counter);
	}

	@Test
	public void testEquality() {
		assertEquals(list, list);
	}

	@Test
	public void testInequality() {
		ArrayList<Integer> otherList = list.copy();
		otherList.add(10);
		assertNotEquals(list, otherList);
	}

	@Test
	public void testAddAll() {
		list.add(10);
		list.add(11);
		list.add(12);
		int expectedSize = list.getSize() * 2;
		list.addAll(list);
		assertEquals(list.getSize(), expectedSize);
	}
}
