package com.mycrawler.lucene;

import org.apache.lucene.util.Version;

import com.mycrawler.core.MyCrawlerConfig;

public class LuceneConstants {
	
	public static final String DIRECTORY = MyCrawlerConfig.getInstance().getIndexPath(); 
	
	public static final Version VERSION = Version.LUCENE_40;
	
}
