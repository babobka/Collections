package ru.kpfu.tree;

import ru.kpfu.linkedlist.LinkedList;
import ru.kpfu.list.Iterator;
import ru.kpfu.list.List;
import ru.kpfu.map.Entry;
import ru.kpfu.map.Map;
import ru.kpfu.stack.Stack;

public class TreeMap<K extends Comparable<K>, V> implements Map<K, V> {

	private NodeItem<K, V> rootNode;

	private int size;

	@Override
	public boolean put(K key, V value) {
		NodeItem<K, V> nodeToPut = new NodeItem<>(new Entry<K, V>(key, value), null, null, null);
		if (rootNode == null) {
			rootNode = nodeToPut;
			size++;
			return true;
		} else {

			NodeItem<K, V> currentNode = rootNode;
			NodeItem<K, V> previousNode = null;
			Boolean leftNode = null;
			while (currentNode != null) {
				int compareResult = currentNode.getEntry().getKey().compareTo(key);
				previousNode = currentNode;
				if (compareResult > 0) {
					currentNode = currentNode.getLeftNode();
					leftNode = true;
				} else if (compareResult < 0) {
					currentNode = currentNode.getRightNode();
					leftNode = false;
				} else {
					currentNode.setEntry(nodeToPut.getEntry());
					return false;
				}
			}
			nodeToPut.setParentNode(previousNode);
			if (leftNode == null) {
				throw new NullPointerException();
			} else if (leftNode) {
				previousNode.setLeftNode(nodeToPut);
			} else {
				previousNode.setRightNode(nodeToPut);
			}
			size++;
			return true;

		}
	}

	@Override
	public V get(K key) {
		NodeItem<K, V> nodeItem = getNode(key);
		if (nodeItem != null) {
			return nodeItem.getEntry().getValue();
		}
		return null;
	}

	private NodeItem<K, V> getNode(K key) {
		NodeItem<K, V> currentNode = rootNode;
		while (currentNode != null) {
			int compareResult = currentNode.getEntry().getKey().compareTo(key);
			if (compareResult > 0) {

				currentNode = currentNode.getLeftNode();
			} else if (compareResult < 0) {

				currentNode = currentNode.getRightNode();
			} else {
				return currentNode;
			}
		}
		return null;
	}

	@Override
	public void remove(K key) {

		if (!isEmpty()) {

			NodeItem<K, V> nodeItem = getNode(key);
			if (nodeItem == null) {
				return;
			}

			if (nodeItem.getRightNode() == null && nodeItem.getLeftNode() == null) {

				NodeItem<K, V> parentNode = nodeItem.getParentNode();
				if (parentNode != null) {
					if (parentNode.getLeftNode() == nodeItem) {
						parentNode.setLeftNode(null);
					} else {
						parentNode.setRightNode(null);
					}
				} else {
					rootNode = null;
				}
			} else if (nodeItem.getRightNode() == null ^ nodeItem.getLeftNode() == null) {

				NodeItem<K, V> childNode;
				if (nodeItem.getRightNode() != null) {
					childNode = nodeItem.getRightNode();
				} else {
					childNode = nodeItem.getLeftNode();
				}
				NodeItem<K, V> parentNode = nodeItem.getParentNode();
				if (parentNode != null) {
					if (parentNode.getLeftNode() == nodeItem) {
						parentNode.setLeftNode(childNode);
					} else {
						parentNode.setRightNode(childNode);
					}
					childNode.setParentNode(parentNode);
				} else {

					childNode.setParentNode(null);
					rootNode = childNode;
				}
			} else {
				NodeItem<K, V> successorNode = getSuccessor(nodeItem);
				if (successorNode != nodeItem.getRightNode()) {
					successorNode.getParentNode().setLeftNode(null);
					if (successorNode.getRightNode() != null) {
						successorNode.getRightNode().setParentNode(successorNode.getParentNode());
						successorNode.getParentNode().setLeftNode(successorNode.getRightNode());
					}
				}

				NodeItem<K, V> parentNode = nodeItem.getParentNode();
				if (parentNode != null) {
					successorNode.setParentNode(parentNode);
					if (parentNode.getLeftNode() == nodeItem) {
						parentNode.setLeftNode(successorNode);
					} else {
						parentNode.setRightNode(successorNode);
					}
				} else {
					successorNode.setParentNode(null);
					rootNode = successorNode;
				}

				successorNode.setLeftNode(nodeItem.getLeftNode());
				nodeItem.getLeftNode().setParentNode(successorNode);
				if (successorNode != nodeItem.getRightNode()) {
					successorNode.setRightNode(nodeItem.getRightNode());
					nodeItem.getRightNode().setParentNode(successorNode);
				}
			}

		}
		size--;

	}

	private NodeItem<K, V> getSuccessor(NodeItem<K, V> nodeItemToDelete) {
		NodeItem<K, V> successorNode = nodeItemToDelete.getRightNode();
		while (successorNode.getLeftNode() != null) {
			successorNode = successorNode.getLeftNode();
		}
		return successorNode;
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
		List<Entry<K, V>> entriesList = this.getEntries();
		Iterator<Entry<K, V>> iterator = entriesList.getIterator();
		while (iterator.hasNext()) {
			list.add(iterator.next().getKey());
		}
		return list;

	}

	public List<K> getSortedKeys() {
		List<K> list = new LinkedList<>();
		addKeysToList(rootNode, list);
		return list;
	}

	private void addKeysToList(NodeItem<K, V> nodeItem, List<K> list) {
		if (nodeItem != null) {
			addKeysToList(nodeItem.getRightNode(), list);
			list.add(nodeItem.getEntry().getKey());
			addKeysToList(nodeItem.getLeftNode(), list);
		}
	}

	@Override
	public void clear() {
		rootNode = null;
		size = 0;

	}

	@Override
	public List<Entry<K, V>> getEntries() {
		List<Entry<K, V>> list = new LinkedList<>();
		Stack<NodeItem<K, V>> stack = new Stack<>();
		NodeItem<K, V> currentNode = rootNode;

		while (currentNode != null) {
			list.add(currentNode.getEntry());
			if (currentNode.getRightNode() != null) {
				stack.push(currentNode.getRightNode());
			}
			if (currentNode.getLeftNode() != null) {
				stack.push(currentNode.getLeftNode());
			}
			currentNode = stack.pop();
		}
		return list;
	}

}
