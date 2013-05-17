package crawler;

import java.net.URLDecoder;
import java.util.List;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Read {
	public static void main(String[] args) throws Exception {
//		String regex = "^http://s.taobao.com/search\\?q=(.*)&rsclick=(\\d+)$";
//		System.out.println("http://s.taobao.com/search?q=%B6%CC%D0%E4t&rsclick=12".matches(regex));
		String crawlStorageFolder = "E:/data/crawl/root";
        int numberOfCrawlers = 7;

        CrawlConfig config = new CrawlConfig();
        config.setResumableCrawling(true);
        config.setCrawlStorageFolder(crawlStorageFolder);

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController ctl = new CrawlController(config, pageFetcher, robotstxtServer);
		List<Object> crawlersLocalData = ctl.getCrawlersLocalData();
		System.out.println(crawlersLocalData.size());
	}
}
