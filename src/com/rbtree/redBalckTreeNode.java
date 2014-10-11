package com.rbtree;

import java.awt.Color;

class redBlackTreeNode<K extends Comparable<K>, V> {
	public K key;
	public V value;
	public redBlackTreeNode<K, V> left;
	public redBlackTreeNode<K, V> right;
	public redBlackTreeNode<K, V> parent;
	public Color color;

	public redBlackTreeNode(K key, V value, Color nodeColor,
			redBlackTreeNode<K, V> left, redBlackTreeNode<K, V> right) {
		this.key = key;
		this.value = value;
		this.color = nodeColor;
		this.left = left;
		this.right = right;
		if (left != null)
			left.parent = this;
		if (right != null)
			right.parent = this;
		this.parent = null;
	}

	public redBlackTreeNode<K, V> grandparent() {
		return parent.parent;
	}

	public redBlackTreeNode<K, V> sibling() {
		if (this == parent.left)
			return parent.right;
		else
			return parent.left;
	}

	public redBlackTreeNode<K, V> uncle() {
		return parent.sibling();
	}
}
