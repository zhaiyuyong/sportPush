package com.zyy.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.zyy.po.Magazine;
import com.zyy.po.Store;

public class DOMMagazineService {

	public static List<Magazine> getMagazines(InputStream inStream)
			throws Throwable {
		List<Magazine> magazines = new ArrayList<Magazine>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inStream);
		org.w3c.dom.Element root = document.getDocumentElement();
		NodeList magazineNodes = root.getElementsByTagName("item");
		for (int i = 0; i < magazineNodes.getLength(); i++) {
			Magazine magazine = new Magazine();
			Element magazineElement = (Element) magazineNodes.item(i);
			magazine.setId(new Integer(magazineElement.getAttribute("id")));
			NodeList magazineChilds = magazineElement.getChildNodes();
			for (int y = 0; y < magazineChilds.getLength(); y++) {
				if (magazineChilds.item(y).getNodeType() == Node.ELEMENT_NODE) {
					Element childElement = (Element) magazineChilds.item(y);
					if ("name".equals(childElement.getNodeName())) {
						magazine.setName(childElement.getFirstChild()
								.getNodeValue());
					} else if ("createTime".equals(childElement.getNodeName())) {
						magazine.setCreateTime(new Date());
					} else if ("picture".equals(childElement.getNodeName())) {
						magazine.setPicture(childElement.getFirstChild()
								.getNodeValue());
					}
				}
			}
			magazines.add(magazine);
		}
		return magazines;
	}

	public static List<Store> getStores(InputStream inStream) throws Throwable {
		List<Store> stores = new ArrayList<Store>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inStream);
		Element root = document.getDocumentElement();
		String version = root.getAttribute("version");

		NodeList arrayNodeList = root.getElementsByTagName("array");
		Element arrayNode = (Element) arrayNodeList.item(0);
		NodeList dictNodeList = arrayNode.getElementsByTagName("dict");
		// System.out.println(dictNodeList.getLength());//6
		for (int i = 0; i < dictNodeList.getLength(); i++) {
			Store store = new Store();
			Element dict = (Element) dictNodeList.item(i);
			NodeList storeNodeList = dict.getChildNodes();
			// System.out.println(storeNodeList.getLength());//37

			for (int y = 0; y < storeNodeList.getLength(); y++) {
				// System.out.println(y);
				if (storeNodeList.item(y).getNodeType() == Node.ELEMENT_NODE) {
					Element storeNode = (Element) storeNodeList.item(y);
					// System.out.println("y=="+y);
					if (storeNode.getFirstChild() != null) {

						if ("ID".equals(storeNode.getFirstChild()
								.getNodeValue())) {

							++y;
							while (storeNodeList.item(++y).getNodeType() == Node.ELEMENT_NODE) {

								Element element = (Element) storeNodeList
										.item(y);

								if (element.getFirstChild() != null) {

									store.setId(element.getFirstChild()
											.getNodeValue());
								}
							}
						}

						if ("Title".equals(storeNode.getFirstChild()
								.getNodeValue())) {

							++y;
							while (storeNodeList.item(++y).getNodeType() == Node.ELEMENT_NODE) {

								Element element = (Element) storeNodeList
										.item(y);

								if (element.getFirstChild() != null) {

									store.setTitle(element.getFirstChild()
											.getNodeValue());
								}
							}
						}

						if ("Release date".equals(storeNode.getFirstChild()
								.getNodeValue())) {

							++y;
							while (storeNodeList.item(++y).getNodeType() == Node.ELEMENT_NODE) {

								Element element = (Element) storeNodeList
										.item(y);

								if (element.getFirstChild() != null) {

									store.setRelease_date(element
											.getFirstChild().getNodeValue());
								}
							}
						}

						if ("Cover URL".equals(storeNode.getFirstChild()
								.getNodeValue())) {

							++y;
							while (storeNodeList.item(++y).getNodeType() == Node.ELEMENT_NODE) {

								Element element = (Element) storeNodeList
										.item(y);

								if (element.getFirstChild() != null) {

									store.setCover_URL(element.getFirstChild()
											.getNodeValue());
								}
							}
						}

						if ("Free".equals(storeNode.getFirstChild()
								.getNodeValue())) {

							++y;
							while (storeNodeList.item(++y).getNodeType() == Node.ELEMENT_NODE) {

								Element element = (Element) storeNodeList
										.item(y);

								if (element.getFirstChild() != null) {
									store.setFree(true);

								}
							}
						}

						if ("Download URL".equals(storeNode.getFirstChild()
								.getNodeValue())) {

							++y;
							while (storeNodeList.item(++y).getNodeType() == Node.ELEMENT_NODE) {

								Element element = (Element) storeNodeList
										.item(y);

								if (element.getFirstChild() != null) {

									store.setDownload_URL(element
											.getFirstChild().getNodeValue());
								}
							}
						}

						if ("Folder".equals(storeNode.getFirstChild()
								.getNodeValue())) {

							++y;
							while (storeNodeList.item(++y).getNodeType() == Node.ELEMENT_NODE) {

								Element element = (Element) storeNodeList
										.item(y);

								if (element.getFirstChild() != null) {

									store.setFolder(element.getFirstChild()
											.getNodeValue());
								}
							}
						}
						if ("Bundle identifier".equals(storeNode
								.getFirstChild().getNodeValue())) {

							++y;
							while (storeNodeList.item(++y).getNodeType() == Node.ELEMENT_NODE) {

								Element element = (Element) storeNodeList
										.item(y);

								if (element.getFirstChild() != null) {

									store.setBundle_identifier(element
											.getFirstChild().getNodeValue());
								}
							}
						}
						if ("Download date".equals(storeNode.getFirstChild()
								.getNodeValue())) {

							++y;
							while (storeNodeList.item(++y).getNodeType() == Node.ELEMENT_NODE) {

								Element element = (Element) storeNodeList
										.item(y);

								if (element.getFirstChild() != null) {

									store.setDownload_date(element
											.getFirstChild().getNodeValue());
								}
							}
						}

					}
				}

			}
			stores.add(store);

		}

		return stores;
	}
	
	
	

}
