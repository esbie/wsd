
import java.io.IOException;

import javax.xml.parsers.*;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WSDParser {
	
	public WSDParser() throws ParserConfigurationException, SAXException, IOException{
		//initializing the xml parsers
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse("training/EnglishLStrain.xml");
		Element docEle = doc.getDocumentElement();
		
		//test
		Element instance = (Element) docEle.getElementsByTagName("instance").item(0);
		Element context = (Element) instance.getElementsByTagName("context").item(0);
		String text = getText(context);
		System.out.println(text);
	}
	
	public String getText(Element elem){
		return elem.getFirstChild().getNodeValue();
	}
}
