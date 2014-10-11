package com.rbtree;

import java.awt.Color;

public class redBlackTree<K extends Comparable<K>, V> {

	public redBlackTreeNode<K, V> rootNode;

	public redBlackTree() {
		rootNode = null;
	}

	private static Color nodeColor(redBlackTreeNode<?, ?> n) {
		return n == null ? Color.BLACK : n.color;
	}

	// Search for a node
	private redBlackTreeNode<K, V> searchNode(K key) {
		redBlackTreeNode<K, V> n = rootNode;
		while (n != null) {
			int compResult = key.compareTo(n.key);
			if (compResult == 0) {
				return n;
			} else if (compResult < 0) {
				n = n.left;
			} else {
				n = n.right;
			}
		}
		return n;
	}

	public V search(K key) {
		redBlackTreeNode<K, V> n = searchNode(key);
		return n == null ? null : n.value;
	}

	// update a node
	private redBlackTreeNode<K, V> updateNode(K key) {
		redBlackTreeNode<K, V> n = rootNode;
		while (n != null) {
			int compResult = key.compareTo(n.key);
			if (compResult == 0) {
				return n;
			} else if (compResult < 0) {
				n = n.left;
			} else {
				n = n.right;
			}
		}
		return n;

	}

	public redBlackTreeNode<K, V> update(K key) {
		redBlackTreeNode<K, V> n = updateNode(key);
		return n == null ? null : n;
	}

	//

	// RotateLeft function
	private void leftRotation(redBlackTreeNode<K, V> n) {
		redBlackTreeNode<K, V> r = n.right;
		replaceNode(n, r);
		n.right = r.left;
		if (r.left != null) {
			r.left.parent = n;
		}
		r.left = n;
		n.parent = r;
	}

	// RotateRight function
	private void rightRotation(redBlackTreeNode<K, V> n) {
		redBlackTreeNode<K, V> l = n.left;
		replaceNode(n, l);
		n.left = l.right;
		if (l.right != null) {
			l.right.parent = n;
		}
		l.right = n;
		n.parent = l;
	}

	// function to replace nodes during rotation.
	private void replaceNode(redBlackTreeNode<K, V> oldn,
			redBlackTreeNode<K, V> newn) {
		if (oldn.parent == null) {
			rootNode = newn;
		} else {
			if (oldn == oldn.parent.left)
				oldn.parent.left = newn;
			else
				oldn.parent.right = newn;
		}
		if (newn != null) {
			newn.parent = oldn.parent;
		}
	}

	public void insert(K key, V value) {
		// Create a new node with default values color = RED, leftChild = null,
		// rightChild = null
		redBlackTreeNode<K, V> insertedNode = new redBlackTreeNode<K, V>(key,
				value, Color.RED, null, null);
		if (rootNode == null) {
			// check if root is null, if yes, then inserted node is the root
			// node
			rootNode = insertedNode;
		} else {
			redBlackTreeNode<K, V> n = rootNode;
			while (true) {
				int compResult = key.compareTo(n.key); // Compare the key with
														// newKey
				if (compResult == 0) {
					n.value = value; // if result is zero, then make that the
										// new node.
					return;
				} else if (compResult < 0) {
					if (n.left == null) { // else follow on the left subtree
											// till u get a nullNode and attach
											// the inserted node to n.left
						n.left = insertedNode;
						break;
					} else {
						n = n.left; // traverse the left sub tree
					}
				} else {
					if (n.right == null) { // else follow on the right subtree
											// till u get a nullNode and attach
											// the inserted node to n.right
						n.right = insertedNode;
						break;
					} else {
						n = n.right; // traverse the right sub tree
					}
				}
			}
			insertedNode.parent = n; // make n the parent of newNode
		}
		insertFixup(insertedNode);
	}

	private void insertFixup(redBlackTreeNode<K, V> n) {
		if (n.parent == null)
			// Check if the parent of n is null, if yes then this is first node
			// that is being inserted.
			n.color = Color.BLACK; // Colour it Black since it is the root
		else {
			// If the parent of n is not null, check its colour if its black
			// then return, since the black height is not changed.
			if (nodeColor(n.parent) == Color.BLACK)
				return;
			else {
				// if the parent of n is red then the child of red node is red,
				// so make parent and uncle BLACK and grand parent RED
				if (nodeColor(n.uncle()) == Color.RED) {
					n.parent.color = Color.BLACK;
					n.uncle().color = Color.BLACK;
					n.grandparent().color = Color.RED;
					insertFixup(n.grandparent());
				} else {
					// if uncle of N is not red(uncle is BLACK) and parent is
					// red.
					if (n == n.parent.right && n.parent == n.grandparent().left) {
						// if N is right child and parent is left of
						// grandparent, RotateLeft(parent)
						leftRotation(n.parent);
						n = n.left;
					} else if (n == n.parent.left
							&& n.parent == n.grandparent().right) {
						// if N is left child and parent is right of grandparent
						// RotateRight(parent)
						rightRotation(n.parent);
						n = n.right;
					}
					fixAfterRotation(n);
				}
			}
		}

	}

	void fixAfterRotation(redBlackTreeNode<K, V> n) {
		n.parent.color = Color.BLACK;
		n.grandparent().color = Color.RED;
		if (n == n.parent.left && n.parent == n.grandparent().left) {
			rightRotation(n.grandparent());
		} else {
			leftRotation(n.grandparent());
		}
	}

	// to find predecessor of the node to be deleted
	private static <K extends Comparable<K>, V> redBlackTreeNode<K, V> findPredecessor(
			redBlackTreeNode<K, V> n) {
		while (n.right != null) {
			n = n.right;
		}
		return n;
	}

	// Deleting a node
	public void delete(K key) {
		redBlackTreeNode<K, V> n = searchNode(key); // Check if the node exists
													// or not.
		if (n == null)
			return; // If the node doesn't exist then return.
		if (n.left != null && n.right != null) {
			// Copy key/value from predecessor and then delete it instead
			redBlackTreeNode<K, V> predecessor = findPredecessor(n.left);
			n.key = predecessor.key;
			n.value = predecessor.value;
			n = predecessor;
		}

		redBlackTreeNode<K, V> child = (n.right == null) ? n.left : n.right;
		if (nodeColor(n) == Color.BLACK) {
			n.color = nodeColor(child);
			deleteFixup(n);
		}
		replaceNode(n, child);
	}

	// Fixing up delete after replacing
	private void deleteFixup(redBlackTreeNode<K, V> n) {
		if (n.parent == null)
			return;
		else {
			if (nodeColor(n.sibling()) == Color.RED) {
				n.parent.color = Color.RED;
				n.sibling().color = Color.BLACK;
				if (n == n.parent.left)
					leftRotation(n.parent);
				else
					rightRotation(n.parent);
			}
			deleteFixRotation(n);
		}
	}

	private void deleteFixRotation(redBlackTreeNode<K, V> n) {
		if (nodeColor(n.parent) == Color.BLACK
				&& nodeColor(n.sibling()) == Color.BLACK
				&& nodeColor(n.sibling().left) == Color.BLACK
				&& nodeColor(n.sibling().right) == Color.BLACK) {
			n.sibling().color = Color.RED;
			deleteFixup(n.parent);
		} else {
			if (nodeColor(n.parent) == Color.RED
					&& nodeColor(n.sibling()) == Color.BLACK
					&& nodeColor(n.sibling().left) == Color.BLACK
					&& nodeColor(n.sibling().right) == Color.BLACK) {
				n.sibling().color = Color.RED;
				n.parent.color = Color.BLACK;
			} else {
				if (n == n.parent.left && nodeColor(n.sibling()) == Color.BLACK
						&& nodeColor(n.sibling().left) == Color.RED
						&& nodeColor(n.sibling().right) == Color.BLACK) {
					n.sibling().color = Color.RED;
					n.sibling().left.color = Color.BLACK;
					rightRotation(n.sibling());
				} else if (n == n.parent.right
						&& nodeColor(n.sibling()) == Color.BLACK
						&& nodeColor(n.sibling().right) == Color.RED
						&& nodeColor(n.sibling().left) == Color.BLACK) {
					n.sibling().color = Color.RED;
					n.sibling().right.color = Color.BLACK;
					leftRotation(n.sibling());
				}
				deleteFixAfterRotate(n);
			}
		}
	}

	private void deleteFixAfterRotate(redBlackTreeNode<K, V> n) {
		n.sibling().color = nodeColor(n.parent);
		n.parent.color = Color.BLACK;
		if (n == n.parent.left) {
			n.sibling().right.color = Color.BLACK;
			leftRotation(n.parent);
		} else {
			n.sibling().left.color = Color.BLACK;
			rightRotation(n.parent);
		}
	}

	public void inOrderPrint() {
		inOrderPrintHelper(rootNode);
	}

	private void inOrderPrintHelper(redBlackTreeNode<K, V> n) {
		// TODO Auto-generated method stub
		if (n != null) {
			inOrderPrintHelper(n.left);
			char c;
			if (n.color == Color.RED)
				c = 'R';
			else
				c = 'B';
			System.out.print(" " + n.key + " color:" + c + " " + n.value);
			inOrderPrintHelper(n.right);
		}

	}

}