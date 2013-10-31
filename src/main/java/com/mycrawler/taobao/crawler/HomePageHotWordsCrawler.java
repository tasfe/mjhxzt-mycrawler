package com.mycrawler.taobao.crawler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
			
			Document doc = Jsoup.parse(html, "http://www.taobao.com"); 
			Elements links = doc.select("div.search-hots>a");
			
			for (Element ele : links) {
					log.info("发现相关词，链接："
							+ ele.absUrl("href") + "名称：" + ele.text());
					
					TLink link = new TLink();
					link.setAnchor(ele.text());
					link.setUrl(ele.absUrl("href"));
					link.setDepth(0);
					link.setParentDocId(0);
					link.setCreateTime(new Date());
					relativeLinks.add(link);
				

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