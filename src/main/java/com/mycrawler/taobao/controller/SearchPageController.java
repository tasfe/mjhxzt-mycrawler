package com.mycrawler.taobao.controller;

import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.mycrawler.core.CoreController;
import com.mycrawler.lucene.TaobaoLucene;
import com.mycrawler.pojo.TLink;
import com.mycrawler.pojo.TSeed;
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
	public void crawlingTaobaoSearch(String key) throws Exception {
		if (key == null || key == "") {
			return;
		}
		
		String baseUrl = "http://s.taobao.com/search?q=${1}";
		String url = baseUrl.replace("${1}", URLEncoder.encode(key,"gbk"));

		List<Object> crawlersLocalData = new CoreController().crawling(url, RelativeWordsCrawler.class);
		if (CollectionUtils.isNotEmpty(crawlersLocalData)) {
			TSeed seed = new TSeed();
			seed.setKey(key);
			seed.setSeed(url);
			List<TLink> relativeLinks = (List<TLink>) crawlersLocalData.get(0);
			TaobaoLucene.save(seed);
		}
	}
}
