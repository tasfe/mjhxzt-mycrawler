package com.mycrawler.core;

import java.util.List;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CoreController {

	/**
	 * 爬取淘宝搜索相关字
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> crawling(String seed, Class crawlerClass)
			throws Exception {
		if (seed == null || seed == "") {
			return null;
		}
		String crawlStorageFolder = "E:/data/crawl/root";
		int numberOfCrawlers = 1;

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
		controller.addSeed(seed);
		controller.start(crawlerClass, numberOfCrawlers);

		List<Object> crawlersLocalData = controller.getCrawlersLocalData();
		return crawlersLocalData;
	}

}