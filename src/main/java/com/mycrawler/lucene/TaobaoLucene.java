package com.mycrawler.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.FieldType.NumericType;
import org.apache.lucene.document.IntField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import com.mycrawler.pojo.TLinks;

public class TaobaoLucene {

//	private static HashMap<Class, java.lang.reflect.Field[]> fieldMap = new HashMap<Class, java.lang.reflect.Field[]>();
	private static final Log log = LogFactory
			.getLog(TaobaoLucene.class);
	
	public static void save(List<TLinks> relativeLinks) throws IOException {
		if (CollectionUtils.isEmpty(relativeLinks)) {
			return ;
		}
		
		for (TLinks tLinks : relativeLinks) {
			Document doc = getDoc(tLinks);
			LuceneUtils.write(doc);
		}
	}

	private static Document getDoc(TLinks tLinks) {
		
		Document doc  = new Document();
		FieldType IntIsType = new FieldType();
		IntIsType.setStored(true);
		IntIsType.setIndexed(true);
		IntIsType.setNumericType(NumericType.INT);
		
		FieldType StrIsType = new FieldType();
		StrIsType.setStored(true);
		StrIsType.setIndexed(true);
		
		Field depth = new IntField("depth", tLinks.getDepth(), IntIsType);
		doc.add(depth);
		Field anchor = new Field("anchor", tLinks.getAnchor(), StrIsType);
		doc.add(anchor);
		Field url = new Field("url", tLinks.getUrl(), StrIsType);
		doc.add(url);
		Field createTime = new Field("createTime", DateTools.dateToString(tLinks.getCreateTime(), Resolution.SECOND),
				StrIsType);
		doc.add(createTime);
		Field docId = new IntField("docId", tLinks.getDocId() , IntIsType);
		doc.add(docId);
		Field parentDocId = new IntField("parentDocId", tLinks.getParentDocId(), IntIsType);
		doc.add(parentDocId);
		
		return doc;
	}
	
	public static TLinks getTlinks(Document doc)
	{
		TLinks tLinks = new TLinks();
		tLinks.setAnchor(doc.get("anchor"));
		tLinks.setUrl(doc.get("url"));
		tLinks.setDepth(Integer.parseInt(doc.get("depth") ));
		tLinks.setDocId(doc.getField("docId").numericValue().intValue());
		tLinks.setParentDocId(doc.getField("parentDocId").numericValue().intValue());
		
		return tLinks;
		
	}

	public static List<TLinks> search(TLinks param) throws Exception {
		DirectoryReader ireader = DirectoryReader.open(LuceneUtils.getDirectory());
		IndexSearcher searcher = new IndexSearcher(ireader);
		QueryParser parser = new QueryParser(LuceneConstants.VERSION, "anchor", LuceneUtils.getAnalyzer());
		Query query = parser.parse(param.getAnchor());
		TopDocs docs = searcher.search(query, 20);
		List<TLinks> result = new ArrayList<TLinks>();
		for (ScoreDoc scoreDoc : docs.scoreDocs) {
			System.out.println(scoreDoc.score);
			Document doc = searcher.doc(scoreDoc.doc);
			result.add(getTlinks(doc));
		}
		return result;
	}
	
}
