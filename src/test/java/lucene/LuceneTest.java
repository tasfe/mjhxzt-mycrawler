package lucene;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

public class LuceneTest extends TestCase {
	Directory directory;
	Analyzer analyzer;

	public void setUp() throws IOException {
		directory = new SimpleFSDirectory(new File("D:/test"));
		analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
	}

	public void testWriter() throws Exception {
		IndexWriterConfig config = new IndexWriterConfig(
				Version.LUCENE_CURRENT, analyzer);

		IndexWriter writer = new IndexWriter(directory, config);
		Document doc = new Document();
		FieldType fType = new FieldType();
		fType.setStored(true);
		fType.setIndexed(true);
		doc.add(new Field("name", "周涛", fType));
		doc.add(new Field("age", "28", fType));
		writer.addDocument(doc);
		writer.commit();
		writer.close();
	}

	public void testSearch() throws Exception
	{
		DirectoryReader ireader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(ireader);
		QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "name", analyzer);
		Query query = parser.parse("涛");
		TopDocs docs = searcher.search(query, 10);
		System.out.println(docs.scoreDocs.length);
	}
}
