package com.mycrawler.taobao.crawler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;
import org.xml.sax.SAXException;

import com.mycrawler.pojo.TLink;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 淘宝首页热搜关键词抓取
 * 
 * @author Zhoutao
 * 
 */
public class HomePageHotWordsCrawler extends WebCrawler {

	private static final Log log = LogFactory
			.getLog(HomePageHotWordsCrawler.class);

	private List<TLink> relativeLinks = new ArrayList<TLink>();

	private Pattern filters = Pattern
			.compile("^http://s.taobao.com/search\\?(.*)(&?)q=(.*?)(&.*?)source=tbsy(.*)$");

	/**
	 * You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic).
	 */
	@Override
	public boolean shouldVisit(WebURL url) {
		return isRelativeLink(url);
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String html = htmlParseData.getHtml();
			
			//获取html
//			System.out.println(html);
			parseLinks(html);
			
			List<WebURL> links = htmlParseData.getOutgoingUrls();
			for (WebURL webURL : links) {
				if (isRelativeLink(webURL)) {
					log.info("发现相关词，深度：" + webURL.getDepth() + "，链接："
							+ webURL.getURL() + "名称：" + webURL.getAnchor());
					Matcher m = filters.matcher(webURL.getURL().toLowerCase());
					if (m.matches()) {
						String anchor = "";
						try {
							anchor = new String (URLDecoder.decode(m.group(3), "gbk").getBytes(),"utf-8");
						} catch (UnsupportedEncodingException e) {
							log.error(e);
						}
						TLink link = new TLink();
						link.setAnchor(anchor);
						link.setUrl(webURL.getURL());
						link.setDepth(webURL.getDepth());
						link.setParentDocId(webURL.getParentDocid());
						link.setCreateTime(new Date());
						relativeLinks.add(link);
					}

				}
			}
		}
	}

	private void parseLinks(String html) {
		Parser parser = null;
		try {
			parser = new Parser(html);
//			parser.setEncoding("gbk");
		} catch (ParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
				System.out.println(tag.extractLink()+"--"+tag.isHTTPLink());
				sb.append(tag.getLinkText() + "--" + tag.getLink() + "\n");
			}
			
			
		}
		parser.reset();
		System.out.println(sb);
		
	}

	private boolean isRelativeLink(WebURL webURL) {

		String href = webURL.getURL().toLowerCase();

		return filters.matcher(href).matches();
	}

	// This function is called by controller to get the local data of this
	// crawler when job is finished
	@Override
	public Object getMyLocalData() {
		return relativeLinks;
	}

}