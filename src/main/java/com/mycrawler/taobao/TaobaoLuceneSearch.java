package com.mycrawler.taobao;

import java.util.List;

import com.mycrawler.dao.DaoFactory;
import com.mycrawler.dao.TSeedHome;
import com.mycrawler.pojo.TLink;
import com.mycrawler.pojo.TSeed;

public class TaobaoLuceneSearch extends TaobaoSearch {
	
	private TaobaoLucene taobaoLucene = new TaobaoLucene();

	/* (non-Javadoc)
	 * @see com.mycrawler.taobao.TaobaoSearch1#get(java.lang.String)
	 */
	public List<TLink> get(String key) throws Exception {
		if ("home".equals(key)) {
			//搜索首页
			
		}
		return taobaoLucene.get(key);
	}
	
	
	
}
