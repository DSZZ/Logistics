package edu.nju.logistics.ui.courier.inputOrder.JComboboxOfChina;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLDao {
	
	public static ArrayList<String> provinceList;
	static {
		provinceList = new ArrayList<>();
		provinceList.add("北京市");
		provinceList.add("广东省");
		provinceList.add("江苏省");
		provinceList.add("上海市");
	}
	public static ArrayList<String> cityList;
	static {
		cityList = new ArrayList<>();
		cityList.add("北京市");
		cityList.add("广州市");
		cityList.add("南京市");
		cityList.add("上海市");
	}
	/**
	 * ����ĳ�����л�ȡ��ʡ�е����е���
	 * @param districts
	 * @return
	 */
	public static List<String> getDistricts(String districts) {
		List<String> list = new ArrayList<String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			factory.setIgnoringElementContentWhitespace(true);
			
			DocumentBuilder db = factory.newDocumentBuilder();
			Document xmldoc = db.parse(new File("xml/Districts.xml"));
			Element root = xmldoc.getDocumentElement();
			
			NodeList nodes = selectNodes("//District[@city='"+districts+"']", root);
			for(int i=0; i<nodes.getLength(); i++) {
				Node node = nodes.item(i);
				String name = node.getTextContent();
				list.add(name);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * ����ĳ��ʡ�ݵ����ֻ�ȡ��ʡ�ݵ����г���
	 * @param provinces
	 * @return
	 */
	public static List<String> getCities(String provinces) {
		List<String> list = new ArrayList<String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			factory.setIgnoringElementContentWhitespace(true);
			
			DocumentBuilder db = factory.newDocumentBuilder();
			Document xmldoc = db.parse(new File("xml/Cities.xml"));
			Element root = xmldoc.getDocumentElement();
			
			NodeList nodes = selectNodes("//City[@Province='"+provinces+"']", root);
			for(int i=0; i<nodes.getLength(); i++) {
				Node node = nodes.item(i);
				String name = node.getTextContent();
				if(cityList.contains(name)){
					list.add(name);
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * ��ȡ����ʡ��
	 * @return 
	 */
	public static List<String> getProvinces() {
		List<String> list = new ArrayList<String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			factory.setIgnoringElementContentWhitespace(true);
			
			DocumentBuilder db = factory.newDocumentBuilder();
			Document xmldoc = db.parse(new File("xml/Provinces.xml"));
			Element root = xmldoc.getDocumentElement();
			
			NodeList nodes = selectNodes("/Provinces/Province", root);
			for(int i=0; i<nodes.getLength(); i++) {
				Node node = nodes.item(i);
				String name = node.getTextContent();
				if(provinceList.contains(name)){
					list.add(name);
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * ����xpath��ȡĳһ���ڵ�
	 * @param express
	 * @param source
	 * @return
	 */
	public static Node selectSingleNode(String express, Object source) {// ���ҽڵ㣬�����ص�һ�����������ڵ�
		Node result = null;
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		try {
			result = (Node) xpath
					.evaluate(express, source, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * ����xpath��ȡ�������������нڵ�
	 * @param express
	 * @param source
	 * @return
	 */
	public static NodeList selectNodes(String express, Object source) {// ���ҽڵ㣬���ط��������Ľڵ㼯��
		NodeList result = null;
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		try {
			result = (NodeList) xpath.evaluate(express, source,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return result;
	}
}
