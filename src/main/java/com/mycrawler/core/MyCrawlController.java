package com.mycrawler.core;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * 继承控制器，添加额外功能
 * @author Zhoutao
 *
 */
public class MyCrawlController extends CrawlController {

	public MyCrawlController(CrawlConfig config, PageFetcher pageFetcher,
			RobotstxtServer robotstxtServer) throws Exception {
		super(config, pageFetcher, robotstxtServer);
	}

	@Override
	public void addSeed(String pageUrl) {
		super.addSeed(pageUrl);
		/*TSeed seed = new TSeed();
		seed.setSeed(pageUrl);
		seed.setLastVisitTime(new Date());
		DaoFactory.getTSeedHome().persist(seed);*/
	}

}
