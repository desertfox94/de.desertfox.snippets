package de.desertfox.snippets.xml;

import java.util.AbstractMap.SimpleEntry;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XPathEvaluator {

	private final Document document;

	public XPathEvaluator(Document document) {
		this.document = document;
	}

	public NList nodesByTagAndAttribute(String tag, SimpleEntry<String, String> attribute) throws XPathExpressionException {
		return getElementsBy(new String[] { tag }, attribute);
	}

	public NList nodesByTagHierarchy(String... tags) throws XPathExpressionException {
		return eval(buildTagHierarchy(tags));
	}

	public NList nodesByTag(String tagName) throws XPathExpressionException {
		return eval(buildTagHierarchy(tagName));
	}

	public NList getElementsBy(String[] nodeHierarchy, SimpleEntry<String, String> attribute) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public NList eval(String xpath) throws XPathExpressionException {
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
