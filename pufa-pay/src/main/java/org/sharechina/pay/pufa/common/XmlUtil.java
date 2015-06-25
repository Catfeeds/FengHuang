package org.sharechina.pay.pufa.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlUtil {

	/**
	 * ��xml������Map
	 * 
	 * @param xmlString
	 * @return never @null
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static Map<String, Object> getMapFromXml(String xmlString) throws ParserConfigurationException,IOException,SAXException {
		if(StringUtils.isBlank(xmlString))return new HashMap<String, Object>();
		
		//������Dom�ķ�ʽ�����ذ�������ҪĿ���Ƿ�ֹAPI�����ذ��ֶ�
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlString);
      //��ȡ��document�����ȫ�����
        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < allNodes.getLength(); i++) {
        	node = allNodes.item(i);
        	if(node instanceof Element){
                map.put(node.getNodeName(),node.getTextContent());
            }
		}
        return map;
	}
	
	public static <T> T getObjectFromXml(String xml, Class<T> tClass, String rootNodeName, String encode) {
		XStream xStreamForResponseData;
		if(encode==null) {
			xStreamForResponseData = new XStream();
		} else {
			xStreamForResponseData=new XStream(new DomDriver(encode));
		}		
        xStreamForResponseData.alias(rootNodeName, tClass);
        xStreamForResponseData.ignoreUnknownElements();//��ʱ���Ե�һЩ�������ֶ�
        return tClass.cast(xStreamForResponseData.fromXML(xml));
	}
}
