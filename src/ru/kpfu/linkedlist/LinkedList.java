package ru.kpfu.linkedlist;

import ru.kpfu.collection.Collection;
import ru.kpfu.list.Iterator;
import ru.kpfu.list.List;

public class LinkedList<T> implements List<T> {

	private Node<T> firstNode;

	private Node<T> lastNode;

	private int size;

	public void clear() {
		lastNode = null;
		firstNode = null;
		size = 0;
	}

	public T getFirst() {
		if (firstNode != null)
			return firstNode.getValue();
		return null;
	}

	public T getLast() {
		if (lastNode != null)
			return lastNode.getValue();
		return null;
	}

	public void addFirst(T value) {

		Node<T> node = new Node<>(value, firstNode, null);
		if (firstNode != null) {
			firstNode.setPrevious(node);
		}
		firstNode = node;
		if (lastNode == null) {
			lastNode = node;
		}
		size++;
	}

	private void remove(Node<T> node) {
		if (!isEmpty()) {
			if (node == firstNode) {
				firstNode = node.getNext();
			}
			if (node == lastNode) {
				lastNode = node.getPrevious();
			}
			if (node.getPrevious() != null) {
				node.getPrevious().setNext(node.getNext());
			}
			if (node.getNext() != null) {
				node.getNext().setPrevious(node.getPrevious());
			}
			size--;
		}
	}

	public void removeFirst() {
		remove(firstNode);
	}

	public void addLast(T value) {
		Node<T> node = new Node<>(value, null, lastNode);
		if (lastNode != null) {
			lastNode.setNext(node);
		}
		lastNode = node;
		if (firstNode == null) {
			firstNode = node;
		}
		size++;
	}

	public void removeLast() {
		remove(lastNode);
	}

	public int getSize() {
		return size;
	}

	public boolean isEmpty() {
		if (firstNode == null) {
			return true;
		}
		return false;
	}

	public Iterator<T> getIterator() {
		return new ListIterator<>(firstNode);
	}

	public LinkedList<T> copy() {
		LinkedList<T> list = new LinkedList<>();
		list.addAll(this);
		return list;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof LinkedList) {
			LinkedList<?> list = (LinkedList<?>) o;
			if (list.size == this.size) {
				Iterator<?> listIterator = list.getIterator();
				Iterator<T> thisIterator = this.getIterator();
				while (listIterator.hasNext()) {
					if (!listIterator.next().equals(thisIterator.next())) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String separator = "";
		Iterator<T> iterator = this.getIterator();
		while (iterator.hasNext()) {
			sb.append(separator);
			sb.append(iterator.next());
			separator = ", ";
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		Iterator<T> thisIterator = this.getIterator();
		while (thisIterator.hasNext()) {
			result += thisIterator.next().hashCode();

		}
		return result * prime + this.size;

	}

	@Override
	public void addAll(Collection<T> collection) {
		Iterator<T> iterator = collection.getIterator();
		while (iterator.hasNext()) {
			this.addFirst(iterator.next());
		}

	}

	@Override
	public boolean add(T t) {
		addLast(t);
		return true;

	}

	@Override
	public void remove() {
		removeLast();

	}

	@Override
	public T get(int i) {
		checkRange(i);
		if (i == 0) {
			return getFirst();
		} else if (i == size - 1) {
			return getLast();
		}
		int counter = 0;
		Iterator<T> iterator = this.getIterator();
		T tempValue;
		while (iterator.hasNext()) {
			tempValue = iterator.next();
			if (counter == i) {
				return tempValue;
			}
			counter++;
		}
		return null;

	}

	@Override
	public void removeByIndex(int i) {
		checkRange(i);
		if (i == 0) {
			removeFirst();
		} else if (i == size - 1) {
			removeLast();
		} else {
			Node<T> currentNode = firstNode;
			int counter = 0;
			while (currentNode != null) {
				if (counter == i) {
					remove(currentNode);
					break;
				}
				currentNode = currentNode.getNext();
				counter++;
			}
		}

	}

	private void checkRange(int i) {
		if (i < 0 || i >= size) {
			throw new IllegalArgumentException("Index " + i + " is out of bounds");
		}
	}

	@Override
	public boolean remove(T value) {
		if (value == null) {
			throw new IllegalArgumentException("Value is null");
		}
		Node<T> currentNode = firstNode;
		while (currentNode != null) {
			if (value.equals(currentNode.getValue())) {
				remove(currentNode);
				return true;
			}
			currentNode = currentNode.getNext();

		}
		return false;
	}

	@Override
	public boolean contains(T value) {
		Iterator<T> iterator = this.getIterator();
		while (iterator.hasNext()) {
			if (iterator.next().equals(value)) {
				return true;
			}
		}
		return false;
	}

}
