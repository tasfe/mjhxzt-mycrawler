package com.mycrawler.taobao.crawler;

import java.util.List;

import com.mycrawler.core.CoreController;
import com.mycrawler.pojo.TLink;

public class TaobaoCrawler {

	public List<TLink> crawlingHomePage() throws Exception {
		Class crawlingImpl = HomePageHotWordsCrawler.class;
		String seedUrl = "http://www.taobao.com/";

		return (List<TLink>) new CoreController().crawling(seedUrl, crawlingImpl).get(0);
	}

	public List<TLink> crawlingRelativeWords(String key) throws Exception {
		return crawlingRelativeWords(key, new StringBuffer());
	}

	public List<TLink> crawlingRelativeWords(String key, StringBuffer seedUrl) throws Exception {
		Class crawlingImpl = RelativeWordsCrawler.class;
		seedUrl.append( "http://s.taobao.com/search?q=").append(key).append("&suggest=0_1&wq=aodi&suggest_query=aodi&source=suggest&initiative_id=tbindexz_20131028&spm=1.6659421.754896237.1&sourceId=tb.index&search_type=item&ssid=s5-e&commend=all");

		return (List<TLink>) new CoreController().crawling(seedUrl.toString(), crawlingImpl).get(0);
	}
}
