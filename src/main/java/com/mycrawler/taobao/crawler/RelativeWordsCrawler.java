package com.mycrawler.taobao.crawler;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.mycrawler.pojo.TLink;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 淘宝搜索数据抓取
 * 
 * @author Zhoutao
 * 
 */
public class RelativeWordsCrawler extends WebCrawler {

	private static final Logger logger = Logger.getLogger(RelativeWordsCrawler.class);
	
	private List<TLink> relativeLinks = new ArrayList<TLink>();
	
	private Pattern filters = Pattern.compile("^http://s.taobao.com/search\\?q=(.*)&rsclick=(\\d+)$");
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
//			System.out.println(html);
			
			/*Document doc = Jsoup.parse(html);
			Elements eles = doc.select("a[trace='relatedSearch']");
			if (eles != null && eles.size() > 0) {
				for (Element child : eles) {
//					System.out.println(child.text() + ":" + child.attr("abs:href"));
				}
			}*/
			
			
			List<WebURL> links = htmlParseData.getOutgoingUrls();
			for (WebURL webURL : links) {
				if (isRelativeLink(webURL)) {
					logger.info("发现相关词，深度：" + webURL.getDepth() + "，链接："
							+ webURL.getURL() + "名称：" + webURL.getAnchor() + webURL.getParentDocid());
					TLink link = new TLink();
					link.setAnchor(webURL.getAnchor());
					link.setUrl(webURL.getURL());
					link.setDepth(webURL.getDepth());
					link.setParentDocId(webURL.getParentDocid());
					link.setDocId(webURL.getDocid());
					link.setCreateTime(new Date());
					relativeLinks.add(link);
				}
			}
		}
	}

	private boolean isRelativeLink(WebURL webURL) {
		if (webURL.getDepth() < 0) {
			return false;
		}
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