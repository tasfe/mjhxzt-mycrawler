package crawler.prase.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Zhoutao
 *
 */
public class TbHomepageSearchHots extends DefaultHandler {

	boolean searchHotsStart;
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.println("Element的开始 uri: " + uri + "| localName: " + localName + "| qName: " + qName +"attributes:"+attributes.getLength() );
		if (attributes.getLength() > 0) {
			for (int i = 0; i < attributes.getLength(); i++) {
				System.out.println(attributes.getQName(i) + " = " + attributes.getValue(i) +"--"+attributes.getValue("data-spm-anchor-id"));
			}
		}
		/*if (HtmlTags.DIV.equals(localName) && attributes.getValue("class").equals("search-hots") ) {
			searchHotsStart = true;
		}
		
		if (searchHotsStart) {
			System.out.println(attributes.getValue("href"));
			
		}*/
		
		
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		System.out.println("Element的结束 uri: " + uri + "localName: " + localName + "qName: " + name); 
        System.out.println("=============================================================");   
	}
	
	

}
