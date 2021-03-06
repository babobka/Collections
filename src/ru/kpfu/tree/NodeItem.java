package ru.kpfu.tree;

import ru.kpfu.map.Entry;

public class NodeItem<K, V> {

	private Entry<K, V> entry;

	private NodeItem<K, V> parentNode;

	private NodeItem<K, V> leftNode;

	private NodeItem<K, V> rightNode;

	public NodeItem(Entry<K, V> entry, NodeItem<K, V> parentNode, NodeItem<K, V> leftNode, NodeItem<K, V> rightNode) {
		super();
		this.entry = entry;
		this.leftNode = leftNode;
		this.rightNode = rightNode;
		this.parentNode = parentNode;
	}

	public Entry<K, V> getEntry() {
		return entry;
	}

	public void setEntry(Entry<K, V> entry) {
		this.entry = entry;
	}

	public NodeItem<K, V> getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(NodeItem<K, V> leftNode) {
		if (this == leftNode) {
			throw new IllegalArgumentException("Recursive node");
		}
		this.leftNode = leftNode;
	}

	public NodeItem<K, V> getRightNode() {
		return rightNode;
	}

	public void setRightNode(NodeItem<K, V> rightNode) {
		if (this == rightNode) {
			throw new IllegalArgumentException("Recursive node");
		}
		this.rightNode = rightNode;
	}

	public String toString() {
		return entry.toString();
	}

	public NodeItem<K, V> getParentNode() {
		return parentNode;
	}

	public void setParentNode(NodeItem<K, V> parentNode) {
		if (this == parentNode) {
			throw new IllegalArgumentException("Recursive node");
		}
		this.parentNode = parentNode;
	}

}
