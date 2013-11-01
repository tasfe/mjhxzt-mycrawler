package com.crawler.taobao;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import com.mycrawler.pojo.TLink;
import com.mycrawler.taobao.TaobaoLucene;
import com.mycrawler.taobao.TaobaoSearch;

public class TaobaoLuceneTest extends TestCase {
	public void testSearch() throws Exception {
		List<TLink> links = new TaobaoLucene().search("奥迪");
		System.out.println(links.size());
	}
	
	
	public void testGetRelativeWords() throws Exception {
		List<TLink> links = new TaobaoLucene().get("abcde");
		if (CollectionUtils.isNotEmpty(links)) {
			for (TLink tLink : links) {
				System.out.println(BeanUtils.describe(tLink));
			}
		}
		
	}
	
	public void testGetHomePage() throws Exception {
		List<TLink> links = new TaobaoLucene().get(TaobaoSearch.LUCENE_KEY_HOME);
		if (CollectionUtils.isNotEmpty(links)) {
			for (TLink tLink : links) {
				System.out.println(BeanUtils.describe(tLink));
			}
		}
		
	}
	
}
