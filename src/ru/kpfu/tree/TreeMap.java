package ru.kpfu.tree;

import ru.kpfu.collection.Comparator;
import ru.kpfu.linkedlist.LinkedList;
import ru.kpfu.list.List;
import ru.kpfu.map.Entry;
import ru.kpfu.map.Map;

public class TreeMap<K, V> implements Map<K, V> {

	private NodeItem<K, V> rootNode;

	private Comparator<K> comparator;

	private int size;

	public TreeMap(Comparator<K> comparator) {
		if (comparator == null) {
			throw new IllegalArgumentException("Comparator can not be null");
		}
		this.comparator = comparator;
	}

	@Override
	public void put(K key, V value) {
		NodeItem<K, V> nodeToPut = new NodeItem<K, V>(new Entry<K, V>(key, value), null, null);
		if (rootNode == null) {
			rootNode = nodeToPut;
			size++;
		} else {

			NodeItem<K, V> currentNode = rootNode;
			NodeItem<K, V> previousNode = null;
			Boolean leftNode = null;
			while (currentNode != null) {
				int compareResult = comparator.compare(currentNode.getEntry().getKey(), key);
				previousNode = currentNode;
				if (compareResult > 0) {
					currentNode = currentNode.getLeftNode();
					leftNode = true;
				} else if (compareResult < 0) {
					currentNode = currentNode.getRightNode();
					leftNode = false;
				} else {
					currentNode.setEntry(nodeToPut.getEntry());
					return;
				}
			}
			if (leftNode == null) {
				throw new NullPointerException();
			} else if (leftNode) {
				previousNode.setLeftNode(nodeToPut);
			} else {
				previousNode.setRightNode(nodeToPut);
			}
			size++;
		}
	}

	@Override
	public V get(K key) {
		NodeItem<K, V> currentNode = rootNode;
		while (currentNode != null) {
			int compareResult = comparator.compare(currentNode.getEntry().getKey(), key);
			if (compareResult > 0) {
				currentNode = currentNode.getLeftNode();
			} else if (compareResult < 0) {
				currentNode = currentNode.getRightNode();
			} else {
				return currentNode.getEntry().getValue();
			}
		}
		return null;
	}

	@Override
	public void remove(K key) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean containsKey(K key) {
		return get(key) != null;
	}

	@Override
	public boolean isEmpty() {

		return size == 0;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public List<K> getKeys() {
		List<K> list = new LinkedList<>();
		addKeysToList(rootNode, list);
		return list;
	}

	public void addKeysToList(NodeItem<K, V> nodeItem, List<K> list) {

		if (nodeItem != null) {
			addKeysToList(nodeItem.getRightNode(), list);
			list.add(nodeItem.getEntry().getKey());
			addKeysToList(nodeItem.getLeftNode(), list);
		}

	}

}
