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
			boolean leftNode = true;
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
			if (leftNode) {
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

		int counter = 0;
		NodeItem<K, V> currentNode = rootNode;
		while (currentNode != null && counter < size) {
			int compareResult = currentNode.getEntry().getKey().compareTo(key);
			if (compareResult > 0) {
				currentNode = currentNode.getLeftNode();
			} else if (compareResult < 0) {
				currentNode = currentNode.getRightNode();
			} else {
				return currentNode;
			}
			counter++;
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
					successorNode.setRightNode(nodeItem.getRightNode());
					nodeItem.getRightNode().setParentNode(successorNode);
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
		LinkedList<K> list = new LinkedList<>();
		List<Entry<K, V>> entriesList = this.getEntries();
		Iterator<Entry<K, V>> iterator = entriesList.getIterator();
		while (iterator.hasNext()) {
			list.addFirst(iterator.next().getKey());
		}
		return list;

	}



	@Override
	public void clear() {
		rootNode = null;
		size = 0;

	}

	@Override
	public List<Entry<K, V>> getEntries() {
		if (rootNode == null)
			return new LinkedList<>();
		Stack<NodeItem<K, V>> stack = new Stack<>();
		NodeItem<K, V> currentNode = rootNode;
		List<Entry<K, V>> list = new LinkedList<>();
		while (!stack.isEmpty() || currentNode!=null) {
			if (currentNode != null) {
				stack.push(currentNode);
				currentNode = currentNode.getRightNode();
			} else if ((currentNode = stack.pop()) != null) {
				list.add(currentNode.getEntry());
				currentNode = currentNode.getLeftNode();
			}
		}
		return list;
	}

}
