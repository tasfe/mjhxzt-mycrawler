package crawler;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import pojo.TLinks;
import pojo.TSeed;
import dao.DaoFactory;
import dao.TLinksHome;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class TaobaoSearchController {
	public static void main(String[] args) throws Exception {

	}

	/**
	 * 爬取淘宝搜索相关字
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void crawlingTaobaoSearch(String seed) throws Exception {
		if (seed == null || seed == "") {
			return;
		}
		String crawlStorageFolder = "E:/data/crawl/root";
		int numberOfCrawlers = 3;

		CrawlConfig config = new CrawlConfig();
		config.setUserAgentString("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0)");
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setMaxDepthOfCrawling(0);
		config.setPolitenessDelay(3000);
		// config.setMaxPagesToFetch(10);
		// config.setResumableCrawling(true);
		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig,
				pageFetcher);
		MyCrawlController controller = new MyCrawlController(config,
				pageFetcher, robotstxtServer);
		/*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */
		// controller.addSeed("http://trade.taobao.com/trade/detail/tradeSnap.htm?spm=a1z09.2.9.27.SB0qV1&tradeID=340541168492417");
		controller.addSeed(seed);
		// "http://s.taobao.com/search?q=T&initiative_id=staobaoz_20130508"

		// controller.addSeed("http://www.ics.uci.edu/");

		/*
		 * Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished.
		 */
		// controller.start(MyCrawler.class, numberOfCrawlers);
		controller.start(TaobaoSearchCrawler.class, numberOfCrawlers);

		List<Object> crawlersLocalData = controller.getCrawlersLocalData();
		if (CollectionUtils.isNotEmpty(crawlersLocalData)) {
			List<TLinks> relativeLinks = (List<TLinks>) crawlersLocalData.get(0);
			if (CollectionUtils.isNotEmpty(relativeLinks)) {
				TSeed tSeed = new TSeed();
				tSeed.setSeed(seed);
				tSeed.setLastVisitTime(new Date());
				DaoFactory.getTSeedHome().persist(tSeed);
				TLinksHome linksHome = DaoFactory.getTLinksHome();
				for (TLinks tLinks : relativeLinks) {
					tLinks.setTSeed(tSeed);
					linksHome.persist(tLinks);
				}
			}
		}
	}

}