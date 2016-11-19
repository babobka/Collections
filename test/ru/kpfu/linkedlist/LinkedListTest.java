package ru.kpfu.linkedlist;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.kpfu.linkedlist.LinkedList;
import ru.kpfu.list.Iterator;

public class LinkedListTest {

	private LinkedList<Integer> list;

	int n = 10000;

	@Before
	public void init() {
		list = new LinkedList<>();
	}

	@After
	public void tearDown() {
		list.clear();
	}

	@Test
	public void testEmpty() {
		assertTrue(list.isEmpty());
	}

	@Test
	public void testAdd() {
		int first = 10;
		int last = 11;
		list.addFirst(first);
		assertTrue(!list.isEmpty());
		assertEquals(list.getSize(), 1);
		assertEquals(list.getFirst(), list.getLast());
		list.addLast(last);
		assertEquals(list.getSize(), 2);
		assertNotEquals(list.getFirst(), list.getLast());
		assertTrue(list.getFirst().equals(first) && list.getLast().equals(last));
		list.clear();
		for (int i = 0; i < n; i++) {
			list.addLast(i);
		}
		assertEquals(list.get(n-2), (Integer)(n - 2));
	}

	@Test
	public void testRemove() {

		for (int i = 0; i < n; i++) {
			list.add(i);
		}

		for (int i = 0; i < n; i++) {
			list.removeFirst();

		}
		assertEquals(list.getSize(), 0);
		assertTrue(list.isEmpty());

		for (int i = 0; i < n; i++) {
			list.add(i);
		}

		for (int i = 0; i < n; i++) {
			list.removeLast();
		}
		assertEquals(list.getSize(), 0);
		assertTrue(list.isEmpty());
		assertNull(list.getFirst());
		assertNull(list.getLast());
	}

	@Test
	public void testRemoveByIndex() {

		for (int i = 0; i < n; i++) {
			list.add(i);
		}

		for (int i = 0; i < n; i++) {
			list.removeByIndex(n - 1 - i);
		}
		assertEquals(list.getSize(), 0);
		assertTrue(list.isEmpty());
	}

	@Test
	public void testClear() {
		int value = 10;
		list.addFirst(value);
		list.addFirst(value);
		list.clear();
		assertTrue(list.isEmpty());
		assertEquals(list.getSize(), 0);
	}

	@Test
	public void testIterator() {

		for (int i = 0; i < n; i++) {
			list.addFirst(i);
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
	public void testGet() {
		for (int i = 0; i < n; i++) {
			list.add(i);
		}
		for (int i = 0; i < n; i++) {
			assertNotNull(list.get(i));
		}

	}

	@Test
	public void testEquality() {

		assertEquals(list, list);
	}

	@Test
	public void testInequality() {
		LinkedList<Integer> otherList = list.copy();
		otherList.addFirst(10);
		assertNotEquals(list, otherList);
	}

	@Test
	public void testAddAll() {
		list.addFirst(10);
		list.addFirst(11);
		list.addFirst(12);
		int expectedSize = list.getSize() * 2;
		list.addAll(list);
		assertEquals(list.getSize(), expectedSize);
	}

}