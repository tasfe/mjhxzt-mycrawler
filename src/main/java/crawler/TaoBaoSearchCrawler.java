package crawler;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pojo.TLinks;
import dao.DaoFactory;
import dao.TLinksHome;
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
public class TaoBaoSearchCrawler extends WebCrawler {

	private static final Log log = LogFactory.getLog(TaoBaoSearchCrawler.class);
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
			List<WebURL> links = htmlParseData.getOutgoingUrls();
			for (WebURL webURL : links) {
				if (isRelativeLink(webURL)) {
					log.info("发现相关词，深度：" + webURL.getDepth() + "，链接："
							+ webURL.getURL() + "名称：" + webURL.getAnchor() + webURL.getParentDocid());
					TLinks link = new TLinks();
					link.setAnchor(webURL.getAnchor());
					link.setUrl(webURL.getURL());
					link.setDepth(webURL.getDepth());
					link.setParentDocId(webURL.getParentDocid());
					TLinksHome linksHome = DaoFactory.getTLinksHome();
					linksHome.persist(link);
				}
			}
		}
	}

	private boolean isRelativeLink(WebURL webURL) {
		if (webURL.getDepth() < 0) {
			return false;
		}
		String href = webURL.getURL().toLowerCase();

		boolean isRelative = href
				.matches("^http://s.taobao.com/search\\?q=(.*)&rsclick=(\\d+)$");

		return isRelative;
	}

}