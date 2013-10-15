package crawler;

import java.util.List;

import junit.framework.TestCase;

import com.mycrawler.core.CoreController;
import com.mycrawler.core.MyCrawlController;
import com.mycrawler.taobao.controller.SearchController;
import com.mycrawler.taobao.crawler.HomePageHotWordsCrawler;
import com.mycrawler.taobao.crawler.RelativeWordsCrawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class ControllerTest extends TestCase {
	
	public void testConn() throws Exception {
		String baseUrl = "http://s.taobao.com/search?q=${1}&initiative_id=staobaoz_20130508";
		String url = baseUrl.replace("${1}", "阿迪达斯");
		new SearchController().crawlingTaobaoSearch(url);
	}
	
	public void testControl() throws Exception {
//		String baseUrl = "http://s.taobao.com/search?q=你好";
		String baseUrl = "http://www.taobao.com";
		new SearchController().crawlingTaobaoSearch(baseUrl);
	}
	
	public void testControl2() throws Exception {
		String baseUrl = "http://www.taobao.com";
		
		String crawlStorageFolder = "E:/data/crawl/root";
		int numberOfCrawlers = 3;

		CrawlConfig config = new CrawlConfig();
		config.setUserAgentString("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0)");
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setMaxDepthOfCrawling(0);
		config.setPolitenessDelay(3000);
		// config.setMaxPagesToFetch(10);
		// config.setResumableCrawling(true);
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig,
				pageFetcher);
		MyCrawlController controller = new MyCrawlController(config,
				pageFetcher, robotstxtServer);
		controller.addSeed(baseUrl);
		controller.start(RelativeWordsCrawler.class, numberOfCrawlers);
	}
	
	public void testHomePageControl() throws Exception {
		new SearchController().crawlingHomepageHotWords();
	}
	public void testRelativeWordsController() throws Exception {
		String seed = "http://search.taobao.com/search?q=%CA%D6%BB%FA%BF%C7&suggest=0_1&wq=%CA%D6%BB%FA&suggest_query=%CA%D6%BB%FA&source=suggest&atab=stats_click%3Dsearch_radio_all%253A1&jc=1&initiative_id=staobaoz_20131015";
		List<Object> crawlersLocalData = new CoreController().crawling(seed , RelativeWordsCrawler.class);
	}
	
	public void testCoreController() throws Exception {
		List<Object> list = new CoreController().crawling("http://www.taobao.com/", HomePageHotWordsCrawler.class);
	}
	
	
	

}
