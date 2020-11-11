package splitters;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SlackSplitter implements Splitter {
	//private String inputMessage;
	private Map<String, String> outputMessages;
	
	@Override
	public Map<String, String> split(String inMessage)  {
		outputMessages = new HashMap<String, String>();
		DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc = null;
		try {
			doc = documentBuilder.parse(new InputSource(new StringReader(inMessage)));
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		doc.getDocumentElement().normalize();
		NodeList messages = doc.getChildNodes().item(0).getChildNodes();

		for (int i = 0; i < messages.getLength(); i++) {
			System.out.println(messages.item(i).getChildNodes().item(0).getTextContent() +" : " + messages.item(i).getChildNodes().item(1).getTextContent());
			outputMessages.put(messages.item(i).getChildNodes().item(0).getTextContent(), messages.item(i).getChildNodes().item(1).getTextContent());
		}

		
		return outputMessages;
	}

}
