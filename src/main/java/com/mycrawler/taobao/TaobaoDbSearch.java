package com.mycrawler.taobao;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.mycrawler.dao.DaoFactory;
import com.mycrawler.dao.TLinksHome;
import com.mycrawler.dao.TSeedHome;
import com.mycrawler.pojo.TLink;
import com.mycrawler.pojo.TSeed;
import com.mycrawler.taobao.crawler.TaobaoCrawler;

public class TaobaoDbSearch extends TaobaoSearch {

	private TLinksHome linksHome = DaoFactory.getTLinksHome();

	public List<TLink> get(String key) throws Exception {
		TSeed instance = new TSeed();
		instance.setKey(key);
		TLink cLinks = new TLink();
		cLinks.setTSeed(instance);
		List<TLink> result = linksHome.list(cLinks);
		if (CollectionUtils.isEmpty(result)) {
			StringBuffer seedUrl = new StringBuffer();
			if (TaobaoSearch.LUCENE_KEY_HOME.equals(key)) {
				result = new TaobaoCrawler().crawlingHomePage();
			} else {
				result = new TaobaoCrawler().crawlingRelativeWords(key, seedUrl);
			}
			if (CollectionUtils.isNotEmpty(result)) {
				TSeed tSeed = new TSeed();
				tSeed.setKey(key);
				tSeed.setSeed(seedUrl.toString());
				tSeed.setLastVisitTime(new Date());
				DaoFactory.getTSeedHome().persist(tSeed);
				TLinksHome linksHome = DaoFactory.getTLinksHome();
				for (TLink tLinks : result) {
					tLinks.setTSeed(tSeed);
					linksHome.persist(tLinks);
				}
			}
		}

		return result;
	}

}
