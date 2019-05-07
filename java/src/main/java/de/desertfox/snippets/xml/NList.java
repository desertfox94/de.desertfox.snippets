package de.desertfox.snippets.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NList implements List<Node> {

	private final NodeList nodeList;

	public NList(NodeList nodeList) {
		this.nodeList = nodeList;
	}

	@Override
	public int size() {
		return nodeList.getLength();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean contains(Object o) {
		if (o == null || !(o instanceof Node)) {
			return false;
		}
		for (int i = 0; i < nodeList.getLength(); ++i) {
			if (o == nodeList.item(i)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Iterator<Node> iterator() {
		return new NodeListIterator(nodeList);
	}

	@Override
	public Object[] toArray() {
		final Node[] array = new Node[nodeList.getLength()];
		for (int i = 0; i < array.length; ++i) {
			array[i] = nodeList.item(i);
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		if (!a.getClass().getComponentType().isAssignableFrom(Node.class)) {
			throw new ArrayStoreException(a.getClass().getComponentType().getName() + " is not the same or a supertype of Node");
		}

		if (a.length >= nodeList.getLength()) {
			for (int i = 0; i < nodeList.getLength(); ++i) {
				a[i] = (T)nodeList.item(i);
			}
			if (a.length > nodeList.getLength()) {
				a[nodeList.getLength()] = null;
			}
			return a;
		}

		return (T[])toArray();
	}

	@Override
	public boolean add(Node e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (final Object o : c) {
			if (!this.contains(o)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends Node> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends Node> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node get(int index) {
		return nodeList.item(index);
	}

	@Override
	public Node set(int index, Node element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, Node element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Node remove(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOf(Object o) {
		if (o == null || !(o instanceof Node)) {
			return -1;
		}
		for (int i = 0; i < nodeList.getLength(); ++i) {
			if (o == nodeList.item(i)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		if (o == null || !(o instanceof Node)) {
			return -1;
		}
		int lastIndex = -1;
		for (int i = 0; i < nodeList.getLength(); ++i) {
			if (o == nodeList.item(i)) {
				lastIndex = i;
			}
		}
		return lastIndex;
	}

	@Override
	public ListIterator<Node> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<Node> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Node> subList(int fromIndex, int toIndex) {
		List<Node> list = new ArrayList<>(toIndex - fromIndex + 1);
		for (int i = fromIndex; i < toIndex; i++) {
			list.add(nodeList.item(i));
		}
		return list;
	}

}
