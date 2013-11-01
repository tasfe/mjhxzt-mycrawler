package com.crawler.taobao;

import java.util.List;

import junit.framework.TestCase;

import com.mycrawler.pojo.TLink;
import com.mycrawler.taobao.TaobaoDbSearch;

public class TaobaoDbSearchTest extends TestCase{

	
	public void testGet() throws Exception
	{
		List<TLink> links =  new TaobaoDbSearch().get("123456789");
		System.out.println(links.size());
	}
}
