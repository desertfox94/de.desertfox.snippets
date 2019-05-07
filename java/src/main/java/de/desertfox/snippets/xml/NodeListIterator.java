package de.desertfox.snippets.xml;

import java.util.Iterator;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NodeListIterator implements Iterator<Node> {

	private NodeList nodeList;

	private int i = 0;

	public NodeListIterator(NodeList nodeList) {
		this.nodeList = nodeList;
	}

	@Override
	public boolean hasNext() {
		return i < nodeList.getLength();
	}

	@Override
	public Node next() {
		return nodeList.item(++i);
	}

}