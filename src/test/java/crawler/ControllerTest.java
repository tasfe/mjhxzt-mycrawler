package crawler;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import junit.framework.TestCase;
import controller.CoreController;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class ControllerTest extends TestCase {
	
	public void testConn() throws Exception {
		String baseUrl = "http://s.taobao.com/search?q=${1}&initiative_id=staobaoz_20130508";
		String url = baseUrl.replace("${1}", "阿迪达斯");
		new TaobaoSearchController().crawlingTaobaoSearch(url);
	}
	
	public void testControl() throws Exception {
//		String baseUrl = "http://s.taobao.com/search?q=你好";
		String baseUrl = "http://www.taobao.com";
		new TaobaoSearchController().crawlingTaobaoSearch(baseUrl);
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
		controller.start(TaobaoSearchCrawler.class, numberOfCrawlers);
	}
	
	public void testHomePageControl() throws Exception {
		new TaobaoSearchController().crawlingTaobaoSearchHot();
	}
	
	public void testCoreController() throws Exception {
		List<Object> list = new CoreController().crawling("http://www.taobao.com/", TbHomePageHotSearchCrawler.class);
	}
	
	
	

}
