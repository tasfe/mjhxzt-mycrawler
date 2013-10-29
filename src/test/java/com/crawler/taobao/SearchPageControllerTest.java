package com.crawler.taobao;

import java.util.Date;
import java.util.List;

import javassist.expr.Instanceof;

import junit.framework.TestCase;

import org.apache.commons.collections.CollectionUtils;

import com.mycrawler.core.CoreController;
import com.mycrawler.dao.DaoFactory;
import com.mycrawler.dao.TLinksHome;
import com.mycrawler.pojo.TLink;
import com.mycrawler.pojo.TSeed;
import com.mycrawler.taobao.controller.SearchPageController;
import com.mycrawler.taobao.crawler.RelativeWordsCrawler;

/**
 * 搜索页控制器
 * @author Zhoutao
 *
 */
public class SearchPageControllerTest extends TestCase{

	
	public void testCrawlingTaobaoSearch() throws Exception {
		new SearchPageController().crawlingTaobaoSearch("http://s.taobao.com/search?initiative_id=staobaoz_20131021&jc=1&q=%B0%A2%B5%CF%B4%EF%CB%B9&stats_click=search_radio_all%3A1");
	}
	
	
	public static void main(String[] args) {
		Number num = 1F;
	}
}
