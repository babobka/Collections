package ru.kpfu.linkedlist;

import ru.kpfu.collection.Collection;
import ru.kpfu.list.Iterator;
import ru.kpfu.list.List;

public class LinkedList<T> implements List<T>, Cloneable {

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
		size++;
		if (size == 1) {
			lastNode = node;
		}
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
		size++;
		if (size == 1) {
			firstNode = node;
		}
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
		return new ListIterator<T>(firstNode);
	}

	@Override
	public LinkedList<T> clone() {
		LinkedList<T> list = new LinkedList<>();
		list.addAll(this);
		return list;
	}

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

	public static String toString(LinkedList<?> list) {
		StringBuilder sb = new StringBuilder();
		String separator = "";
		Iterator<?> iterator = list.getIterator();
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
	public void add(T t) {
		addFirst(t);

	}

	@Override
	public void remove() {
		removeFirst();

	}

	@Override
	public T get(int i) {
		if (i < 0 || i >= size) {
			throw new IllegalArgumentException("Index " + i + " is out of bounds");
		}
		int counter = 0;
		Iterator<T> iterator = this.getIterator();
		while (iterator.hasNext()) {
			if (counter == i) {
				return iterator.next();
			}
			counter++;
		}
		return null;

	}

	@Override
	public void remove(int i) {
		if (i < 0 || i >= size) {
			throw new IllegalArgumentException("Index " + i + " is out of bounds");
		}
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
