package com.mycrawler.taobao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.FieldType.NumericType;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.alibaba.fastjson.JSON;
import com.mycrawler.core.CoreController;
import com.mycrawler.lucene.LuceneConstants;
import com.mycrawler.lucene.LuceneUtils;
import com.mycrawler.pojo.TLink;
import com.mycrawler.pojo.TSeed;
import com.mycrawler.taobao.crawler.HomePageHotWordsCrawler;
import com.mycrawler.taobao.crawler.RelativeWordsCrawler;
import com.mycrawler.taobao.crawler.TaobaoCrawler;

public class TaobaoLucene {

	//	private static HashMap<Class, java.lang.reflect.Field[]> fieldMap = new HashMap<Class, java.lang.reflect.Field[]>();
	private static final Log log = LogFactory.getLog(TaobaoLucene.class);

	//	public static void save(List<TLink> relativeLinks) throws IOException {
	//		if (CollectionUtils.isEmpty(relativeLinks)) {
	//			return;
	//		}
	//
	//		for (TLink TLink : relativeLinks) {
	//			Document doc = geTLinkField(TLink);
	//			LuceneUtils.write(doc);
	//		}
	//	}

	public static void save(TSeed seed) throws IOException {
		Document doc = getDoc(seed);
		LuceneUtils.write(doc);
	}

	private static Document getDoc(TSeed seed) {

		Document doc = new Document();

		FieldType StrIsType = new FieldType();
		StrIsType.setStored(true);
		StrIsType.setIndexed(true);

		FieldType StrSType = new FieldType();
		StrSType.setStored(true);

		StringField fSeed = new StringField("seed", seed.getSeed(), Store.YES);
		doc.add(fSeed);

		Field key = new Field("key", seed.getKey(), StrIsType);
		doc.add(key);

		LongField lastVisitTime = new LongField("lastVisitTime", seed.getLastVisitTime().getTime(), Store.YES);
		doc.add(lastVisitTime);

		Field links = new Field("links", JSON.toJSONString(seed.getTLinkses()), StrSType);
		doc.add(links);

		return doc;
	}

	public static List<TLink> getTLinks(Document doc) {
		List<TLink> links = null;
		String jsonLinks = doc.get("links");
		if (StringUtils.isNotEmpty(jsonLinks)) {
			links = JSON.parseArray(jsonLinks, TLink.class);
		}
		return links;

	}

	public List<TLink> search(TSeed param) throws Exception {
		log.info("search:"+BeanUtils.describe(param));
		DirectoryReader ireader = DirectoryReader.open(LuceneUtils.getDirectory());
		IndexSearcher searcher = new IndexSearcher(ireader);
		QueryParser parser = new QueryParser(LuceneConstants.VERSION, "key", LuceneUtils.getAnalyzer());
		Query query = parser.parse(param.getKey());
		TopDocs docs = searcher.search(query, 20);
		List<TLink> result = new ArrayList<TLink>();
		for (ScoreDoc scoreDoc : docs.scoreDocs) {
			Document doc = searcher.doc(scoreDoc.doc);
			result.addAll(getTLinks(doc));
		}
		return result;
	}

	/**
	 * 查找索引包含关键字key的，没有则返回空
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List<TLink> search(String key) throws Exception {
		TSeed seed = new TSeed();
		seed.setKey(key);
		return search(seed);
	}

	/**
	 * 查找索引包含关键字key的，没有则自动爬取，并返回爬取结果
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List<TLink> get(String key) throws Exception {
		TSeed seed = new TSeed();
		seed.setKey(key);
		List<TLink> result = search(seed);
		if (CollectionUtils.isEmpty(result)) {
			StringBuffer seedUrl = new StringBuffer();
			if (TaobaoSearch.LUCENE_KEY_HOME.equals(key)) {
				result = new TaobaoCrawler().crawlingHomePage();
			} else {
				result = new TaobaoCrawler().crawlingRelativeWords(key,seedUrl);
			}

			if (CollectionUtils.isNotEmpty(result)) {
				seed.setTLinkses(result);
				seed.setLastVisitTime(new Date());
				seed.setSeed(seedUrl.toString());
				save(seed);
			}
		}
		return result;
	}
}
