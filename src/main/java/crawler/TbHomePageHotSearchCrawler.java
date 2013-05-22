package crawler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class TbHomePageHotSearchCrawler extends WebCrawler {

	private static final Log log = LogFactory
			.getLog(TbHomePageHotSearchCrawler.class);

	private List<TLinks> relativeLinks = new ArrayList<TLinks>();

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
						TLinks link = new TLinks();
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