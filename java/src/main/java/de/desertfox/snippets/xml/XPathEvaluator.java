package de.desertfox.snippets.xml;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPathEvaluator {

	private final Document document;

	public XPathEvaluator(Document document) {
		this.document = document;
	}

	public List<Node> nodesByTagAndAttribute(String tag, SimpleEntry<String, String> attribute) throws XPathExpressionException {
		return getElementsBy(new String[] { tag }, attribute);
	}

	public List<Node> nodesByTagHierarchy(String... tags) throws XPathExpressionException {
		return eval(buildTagHierarchy(tags));
	}

	public List<Node> nodesByTag(String tagName) throws XPathExpressionException {
		return eval(buildTagHierarchy(tagName));
	}

	public List<Node> getElementsBy(String[] nodeHierarchy, SimpleEntry<String, String> attribute) {
		try {
			String xpathString = buildTagHierarchy(nodeHierarchy);
			if (attribute != null) {
				StringBuilder builder = new StringBuilder(xpathString);
				String value = attribute.getValue();
				if (value.equals("*")) {
					builder.append("[@").append(attribute.getKey()).append("]");
				} else {
					builder.append(String.format("[%s='%s']", attribute.getKey(), value));
				}
				xpathString = builder.toString();
			}
			return eval(xpathString);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Node> eval(String xpath) throws XPathExpressionException {
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPathExpression expr = xPathfactory.newXPath().compile(xpath);
		return new NList((NodeList)expr.evaluate(document.getDocumentElement(), XPathConstants.NODESET));
	}

	public static String buildTagHierarchy(String... tags) {
		if (tags == null || tags.length == 0) {
			return "";
		}
		StringBuilder builder = new StringBuilder("//");
		builder.append(tags[0]);
		for (int i = 1; i < tags.length; i++) {
			builder.append('/').append(tags[i]);
		}
		return builder.toString();
	}

}
