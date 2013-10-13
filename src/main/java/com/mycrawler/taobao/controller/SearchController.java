package com.mycrawler.taobao.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.mycrawler.core.CoreController;
import com.mycrawler.dao.DaoFactory;
import com.mycrawler.dao.TLinksHome;
import com.mycrawler.pojo.TLinks;
import com.mycrawler.pojo.TSeed;
import com.mycrawler.taobao.crawler.HomePageHotWordsCrawler;
import com.mycrawler.taobao.crawler.RelativeWordsCrawler;

public class SearchController {

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

		List<Object> crawlersLocalData = new CoreController().crawling(seed, RelativeWordsCrawler.class);
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
	
	/**
	 * 爬取淘宝首页热搜
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void crawlingHomepageHotWords() throws Exception {
		String seed = "http://www.taobao.com/";

		List<Object> crawlersLocalData = new CoreController().crawling(seed, HomePageHotWordsCrawler.class);
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