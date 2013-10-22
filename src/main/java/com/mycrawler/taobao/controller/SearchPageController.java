package com.mycrawler.taobao.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.mycrawler.core.CoreController;
import com.mycrawler.lucene.TaobaoLucene;
import com.mycrawler.pojo.TLinks;
import com.mycrawler.taobao.crawler.RelativeWordsCrawler;

/**
 * 搜索页控制器
 * @author Zhoutao
 *
 */
public class SearchPageController {

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
			TaobaoLucene.save(relativeLinks);
		}
	}
}
