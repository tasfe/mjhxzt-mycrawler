package lucene;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import junit.framework.Test;
import junit.framework.TestCase;
import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class LuceneTest extends TestCase {
	Directory directory;
	Analyzer analyzer;
	public static final Version VERSION = Version.LUCENE_40;

	public void setUp() throws IOException {
		directory = new SimpleFSDirectory(new File("D:/test"));
		// directory = new SimpleFSDirectory(new File(
		// "C:/Users/Administrator/Desktop/indexfile/master"));
		// analyzer = new StandardAnalyzer(VERSION);
		analyzer = new PaodingAnalyzer();
		// analyzer = new ChineseAnalyzer();
	}

	/**
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void testWriterConcurrent() throws IOException, InterruptedException {
		
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		 
		IndexWriterConfig config = new IndexWriterConfig(VERSION,
				analyzer);
		IndexWriter writer = new IndexWriter(directory, config);
		for (int i = 0; i < 100; i++) {
			Thread writerThread = new WriterThread(writer);
			executorService.execute(writerThread);
		}
		
		executorService.shutdown();
		executorService.awaitTermination(10, TimeUnit.MINUTES);
		writer.close();
	}
	
	class WriterThread extends Thread
	{
		IndexWriter writer;
		public WriterThread(IndexWriter writer) {
			this.writer = writer;
		}
		@Override
		public void run() {
			try {
//				IndexWriterConfig config = new IndexWriterConfig(VERSION,
//						analyzer);
//
//				IndexWriter writer = new IndexWriter(directory, config);
				System.out.println("new IndexWriter");
				FieldType fType = new FieldType();
				fType.setStored(true);
				fType.setIndexed(true);
				Document doc = new Document();
				doc.add(new Field("name", "周涛1", fType));
				doc.add(new Field("age", "28", fType));

				writer.addDocument(doc);
				System.out.println("before commit");
				writer.commit();
				
				System.out.println(Thread.currentThread().getName()+ " write success!");
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
		
	}

	public void testWriter() throws Exception {
		IndexWriterConfig config = new IndexWriterConfig(VERSION, analyzer);

		IndexWriter writer = new IndexWriter(directory, config);
		FieldType fType = new FieldType();
		fType.setStored(true);
		fType.setIndexed(true);
		writer.commit();

		if (!containsDoc("周涛")) {
			Document doc = new Document();
			doc.add(new Field("name", "周涛", fType));
			doc.add(new Field("age", "28", fType));

			writer.addDocument(doc);
		}

		if (!containsDoc("崔博")) {
			Document doc2 = new Document();
			doc2.add(new Field("name", "崔博", fType));
			doc2.add(new Field("age", "29", fType));
			writer.addDocument(doc2);
		}

		if (!containsDoc("崔永圆")) {
			Document doc3 = new Document();
			doc3.add(new Field("name", "崔永圆", fType));
			doc3.add(new Field("age", "30", fType));
			writer.addDocument(doc3);

		}
		writer.commit();
		writer.close();
	}

	public boolean containsDoc(String name) throws Exception {
		DirectoryReader ireader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(ireader);
		QueryParser parser = new QueryParser(VERSION, "name", analyzer);
		Query query = parser.parse(name);
		TopDocs docs = searcher.search(query, 1);
		System.out.println(docs.totalHits);
		if (docs.totalHits > 0) {
			return true;
		}
		return false;
	}

	public void testSearchWithParser() throws Exception {
		DirectoryReader ireader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(ireader);
		QueryParser parser = new QueryParser(VERSION, "name", analyzer);
		Query query = parser.parse("涛 OR 崔");
		TopDocs docs = searcher.search(query, 10);
		printResult(searcher, docs.scoreDocs);
	}

	public void testSearchWithBoolean() throws Exception {
		String paramName = "崔永圆";
		String paramAge = "29";
		DirectoryReader ireader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(ireader);
		QueryParser parser = new QueryParser(VERSION, "name", analyzer);
		Query query = parser.parse(paramName);
		BooleanQuery bq = new BooleanQuery();
		TermQuery nameTq = new TermQuery(new Term("name", paramName));
		TermQuery ageTq = new TermQuery(new Term("age", paramAge));
		bq.add(nameTq, Occur.SHOULD);
		bq.add(ageTq, Occur.SHOULD);
		System.out.println("BooleanQuery:");
		TopDocs docs = searcher.search(bq, 10);

		printResult(searcher, docs.scoreDocs);

		System.out.println("QueryParser:");
		docs = searcher.search(query, 10);
		printResult(searcher, docs.scoreDocs);

		System.out.println("TermQuery:");
		docs = searcher.search(nameTq, 10);
		printResult(searcher, docs.scoreDocs);

	}

	private void printResult(IndexSearcher searcher, ScoreDoc[] scoreDocs)
			throws IOException {
		if (ArrayUtils.isEmpty(scoreDocs)) {
			System.out.println("null");
		} else {
			for (ScoreDoc scoreDoc : scoreDocs) {
				System.out.println(scoreDoc.score);
				Document doc = searcher.doc(scoreDoc.doc);
				System.out.println(doc.getField("name"));
			}
		}

		System.out.println("=============================");

	}

	public void testContains() throws Exception {
		boolean contains = containsDoc("周大涛");
		System.out.println(contains);
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		new LuceneTest().testWriterConcurrent();
	}
	

}
