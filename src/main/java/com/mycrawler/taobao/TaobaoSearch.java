package com.mycrawler.taobao;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.mycrawler.core.MyCrawlerConfig;
import com.mycrawler.pojo.TLink;

public abstract class TaobaoSearch {

	public static final String LUCENE_KEY_HOME = "home";

	public abstract List<TLink> get(String key) throws Exception;
	
	public TaobaoSearch getSearcher() throws Exception
	{
		MyCrawlerConfig config = MyCrawlerConfig.getInstance();
		if ("db".equalsIgnoreCase(config.getStoreType())) {
			return new TaobaoDbSearch();
		}else {
			return new TaobaoLuceneSearch();
		}
		
	}

}