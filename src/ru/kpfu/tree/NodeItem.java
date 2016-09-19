package ru.kpfu.tree;

import ru.kpfu.map.Entry;

public class NodeItem<K, V> {

	private Entry<K, V> entry;

	private NodeItem<K, V> leftNode;

	private NodeItem<K, V> rightNode;

	public NodeItem(Entry<K, V> entry, NodeItem<K, V> leftNode, NodeItem<K, V> rightNode) {
		super();
		this.entry = entry;
		this.leftNode = leftNode;
		this.rightNode = rightNode;
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
		this.leftNode = leftNode;
	}

	public NodeItem<K, V> getRightNode() {
		return rightNode;
	}

	public void setRightNode(NodeItem<K, V> rightNode) {
		this.rightNode = rightNode;
	}

	public String toString() {
		return entry.toString();
	}

}
