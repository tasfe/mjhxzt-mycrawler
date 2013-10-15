/**
 * @Project: crawler4j
 *           <p>
 *           Description:
 *           </p>
 *           <p>
 *           ��ģ��ֹ��ܣ�
 *           </p>
 *           <p>
 *           2013-5-23
 *           </p>
 * @author Zhoutao
 * @version 2.x
 */
package crawler.prase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import crawler.prase.handler.BaiduContentHandler;

public class ParseTest extends TestCase {

	/**
	 * 解析html用HtmlParser
	 * @throws Exception
	 */
	public void testTbHomepageParse() throws Exception {
		Parser parser = new Parser("d:\\a.txt");
		parser.setEncoding("gbk");
		HasParentFilter acceptedFilter = new HasParentFilter(new HasAttributeFilter("class", "search-hots"));
		NodeList nodes = null;
		try {
			nodes = parser.extractAllNodesThatMatch(acceptedFilter);
			
		} catch (ParserException e) {
			e.printStackTrace();
		}

		StringBuffer sb = new StringBuffer();
		SimpleNodeIterator iter = nodes.elements();
		while (iter.hasMoreNodes()) {
			
			Node node = iter.nextNode();
			if (node instanceof LinkTag) {
				LinkTag tag = (LinkTag) node;
				sb.append(tag.getLinkText() + "--" + tag.getLink() + "\n");
			}
		}
		parser.reset();
		System.out.println(sb);
	}

	/**
	 * 解析html用Jsoup
	 * @throws Exception
	 */
	public void testTbHomepageParse2() throws Exception {
		Document doc = Jsoup.parse(new File("d:\\a.txt"), null, "http://http://www.taobao.com/");
		Element ele = doc.select(".search-hots").first();
		if (ele.childNodeSize() > 0) {
			for (Element child : ele.children()) {
				System.out.println(child.text() + ":" + child.attr("abs:href"));
			}
		}

	}

	public static void main(String[] args) throws Exception {
		//		URL url = new URL("http://www.baidu.com");
		File file = new File("d:\\a.txt");
		InputStream content = new FileInputStream(file);
		org.apache.tika.parser.html.HtmlParser htmlParser = new org.apache.tika.parser.html.HtmlParser();
		Metadata metadata = new Metadata();

		BaiduContentHandler contentHandler = new BaiduContentHandler();
		InputStream inputStream = null;
		try {
			ParseContext parseContext = new ParseContext();
			// inputStream = new ByteArrayInputStream(content);
			htmlParser.parse(content, contentHandler, metadata, parseContext);
			/*
			 * if (CollectionUtils.isNotEmpty(contentHandler.getOutgoingUrls()))
			 * {
			 * for (ExtractedUrlAnchorPair pair :
			 * contentHandler.getOutgoingUrls()) {
			 * System.out.println(pair.getHref());
			 * }
			 * }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
			}
		}

		System.out.println(contentHandler.toString());

	}
}
