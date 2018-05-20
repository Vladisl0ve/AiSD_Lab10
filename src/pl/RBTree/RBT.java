package pl.RBTree;

import java.util.ArrayList;
import java.util.List;

public class RBT {
	public Node root;

	static final int BLACK = 1;
	static final int RED = 0;
	private static final int NEGATIVE_RED = -1;
	private static final int DOUBLE_BLACK = 2;

	public RBT() {
		root = null;
	}

	public void add(Comparable obj, int wiersz) {

		Node newNode = new Node(obj);

		if (find(obj) == true)
			getNode(obj).setWierszy(wiersz);
		else
			newNode.setWierszy(wiersz);
		newNode.data = obj;
		newNode.left = null;
		newNode.right = null;

		if (root == null) {
			root = newNode;
		} else {
			root.addNode(newNode);
		}

		fixAfterAdd(newNode);

	}

	public boolean find(Comparable obj) {
		Node current = root;
		while (current != null) {
			int d = current.data.compareTo(obj);
			if (d == 0) {
				return true;
			} else if (d > 0) {
				current = current.left;
			} else {
				current = current.right;
			}
		}
		return false;
	}

	public Node getNode(Comparable obj) {
		Node current = root;
		while (current != null) {
			int d = current.data.compareTo(obj);
			if (d == 0) {
				return current;
			} else if (d > 0) {
				current = current.left;
			} else {
				current = current.right;
			}
		}
		return null;
	}

	public void remove(Comparable obj) {

		Node toBeRemoved = root;
		boolean found = false;
		while (!found && toBeRemoved != null) {
			int d = toBeRemoved.data.compareTo(obj);
			if (d == 0) {
				found = true;
			} else {
				if (d > 0) {
					toBeRemoved = toBeRemoved.left;
				} else {
					toBeRemoved = toBeRemoved.right;
				}
			}
		}

		if (!found) {
			return;
		}

		if (toBeRemoved.left == null || toBeRemoved.right == null) {
			Node newChild;
			if (toBeRemoved.left == null) {
				newChild = toBeRemoved.right;
			} else {
				newChild = toBeRemoved.left;
			}

			fixBeforeRemove(toBeRemoved);
			replaceWith(toBeRemoved, newChild);
			return;
		}

		Node smallest = toBeRemoved.right;
		while (smallest.left != null) {
			smallest = smallest.left;
		}

		toBeRemoved.data = smallest.data;
		fixBeforeRemove(smallest);
		replaceWith(smallest, smallest.right);
	}

	public String toString() {
		return toString(root);
	}

	private String toString(Node parent) {
		if (parent == null) {
			return "";
		}
		System.out.print(toString(parent.left));
		System.out.print(parent.data);
		System.out.println(parent.getWierszy());
		System.out.print(toString(parent.right));

		return "";
	}

	class Node {
		public Comparable data;
		public Node left;
		public Node right;
		public Node parent;
		public int color;
		public ArrayList<Integer> wierszy = new ArrayList<>();

		public Node(Comparable data) {
			this.data = data;
			this.wierszy = new ArrayList<>();
		}

		public void setLeftChild(Node child) {
			left = child;
			if (child != null) {
				child.parent = this;
			}
		}

		public void setRightChild(Node child) {
			right = child;
			if (child != null) {
				child.parent = this;
			}
		}

		public void addNode(Node newNode) {
			int comp = newNode.data.compareTo(data);
			if (comp < 0) {
				if (left == null) {
					left = newNode;
					left.parent = this;
				} else {
					left.addNode(newNode);
				}
			} else if (comp > 0) {
				if (right == null) {
					right = newNode;
					right.parent = this;
				} else {
					right.addNode(newNode);
				}
			}
		}

		public List<Integer> getWierszy() {
			return wierszy;
		}

		public void setWierszy(int i) {
			this.wierszy.add(i);
		}

		public Comparable getData() {
			return data;
		}

		public void setData(Comparable data) {
			this.data = data;
		}

		public Node getLeft() {
			return left;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public Node getRight() {
			return right;
		}

		public void setRight(Node right) {
			this.right = right;
		}

		public Node getParent() {
			return parent;
		}

		public void setParent(Node parent) {
			this.parent = parent;
		}

		public int getColor() {
			return color;
		}

		public void setColor(int color) {
			this.color = color;
		}
	}

	private void replaceWith(Node toBeReplaced, Node replacement) {
		if (toBeReplaced.parent == null) {
			replacement.parent = null;
			root = replacement;
		} else if (toBeReplaced == toBeReplaced.parent.left) {
			toBeReplaced.parent.setLeftChild(replacement);
		} else {
			toBeReplaced.parent.setRightChild(replacement);
		}
	}

	private void fixAfterAdd(Node newNode) {
		if (newNode.parent == null) {
			newNode.color = BLACK;
		} else {
			newNode.color = RED;
			if (newNode.parent.color == RED) {
				fixDoubleRed(newNode);
			}
		}
	}

	private void fixBeforeRemove(Node toBeRemoved) {
		if (toBeRemoved.color == RED) {
			return;
		}

		if (toBeRemoved.left != null || toBeRemoved.right != null) {
			// Color the child black
			if (toBeRemoved.left == null) {
				toBeRemoved.right.color = BLACK;
			} else {
				toBeRemoved.left.color = BLACK;
			}
		} else {
			bubbleUp(toBeRemoved.parent);
		}
	}

	private void bubbleUp(Node parent) {
		if (parent == null) {
			return;
		}
		parent.color++;
		parent.left.color--;
		parent.right.color--;

		if (bubbleUpFix(parent.left)) {
			return;
		}
		if (bubbleUpFix(parent.right)) {
			return;
		}

		if (parent.color == DOUBLE_BLACK) {
			if (parent.parent == null) {
				parent.color = BLACK;
			} else {
				bubbleUp(parent.parent);
			}
		}
	}

	private boolean bubbleUpFix(Node child) {
		if (child.color == NEGATIVE_RED) {
			fixNegativeRed(child);
			return true;
		} else if (child.color == RED) {
			if (child.left != null && child.left.color == RED) {
				fixDoubleRed(child.left);
				return true;
			}
			if (child.right != null && child.right.color == RED) {
				fixDoubleRed(child.right);
				return true;
			}
		}
		return false;
	}

	private void fixDoubleRed(Node child) {
		Node parent = child.parent;
		Node grandParent = parent.parent;
		if (grandParent == null) {
			parent.color = BLACK;
			return;
		}
		Node n1, n2, n3, t1, t2, t3, t4;
		if (parent == grandParent.left) {
			n3 = grandParent;
			t4 = grandParent.right;
			if (child == parent.left) {
				n1 = child;
				n2 = parent;
				t1 = child.left;
				t2 = child.right;
				t3 = parent.right;
			} else {
				n1 = parent;
				n2 = child;
				t1 = parent.left;
				t2 = child.left;
				t3 = child.right;
			}
		} else {
			n1 = grandParent;
			t1 = grandParent.left;
			if (child == parent.left) {
				n2 = child;
				n3 = parent;
				t2 = child.left;
				t3 = child.right;
				t4 = parent.right;
			} else {
				n2 = parent;
				n3 = child;
				t2 = parent.left;
				t3 = child.left;
				t4 = child.right;
			}
		}

		replaceWith(grandParent, n2);
		n1.setLeftChild(t1);
		n1.setRightChild(t2);
		n2.setLeftChild(n1);
		n2.setRightChild(n3);
		n3.setLeftChild(t3);
		n3.setRightChild(t4);
		n2.color = grandParent.color - 1;
		n1.color = BLACK;
		n3.color = BLACK;

		if (n2 == root) {
			root.color = BLACK;
		} else if (n2.color == RED && n2.parent.color == RED) {
			fixDoubleRed(n2);
		}
	}

	private void fixNegativeRed(Node negRed) {
		Node parent = negRed.parent;
		Node child;
		if (parent.left == negRed) {
			Node n1 = negRed.left;
			Node n2 = negRed;
			Node n3 = negRed.right;
			Node n4 = parent;
			Node t1 = n3.left;
			Node t2 = n3.right;
			Node t3 = n4.right;
			n1.color = RED;
			n2.color = BLACK;
			n4.color = BLACK;

			replaceWith(n4, n3);
			n3.setLeftChild(n2);
			n3.setRightChild(n4);
			n2.setLeftChild(n1);
			n2.setRightChild(t1);
			n4.setLeftChild(t2);
			n4.setRightChild(t3);

			child = n1;
		} else {
			Node n4 = negRed.right;
			Node n3 = negRed;
			Node n2 = negRed.left;
			Node n1 = parent;
			Node t3 = n2.right;
			Node t2 = n2.left;
			Node t1 = n1.left;
			n4.color = RED;
			n3.color = BLACK;
			n1.color = BLACK;

			replaceWith(n1, n2);
			n2.setRightChild(n3);
			n2.setLeftChild(n1);
			n3.setRightChild(n4);
			n3.setLeftChild(t3);
			n1.setRightChild(t2);
			n1.setLeftChild(t1);

			child = n4;
		}

		if (child.left != null && child.left.color == RED) {
			fixDoubleRed(child.left);
		} else if (child.right != null && child.right.color == RED) {
			fixDoubleRed(child.right);
		}
	}

}
