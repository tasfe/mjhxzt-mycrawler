package com.mycrawler.lucene;

import java.io.File;
import java.io.IOException;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

public class LuceneUtils {

	private static Directory directory;
	private static Analyzer analyzer;
	private static IndexWriterConfig config;

	static {
		analyzer = new PaodingAnalyzer();
		config = new IndexWriterConfig(LuceneConstants.VERSION, analyzer);
		try {
			directory = new SimpleFSDirectory(new File(LuceneConstants.DIRECTORY));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void write(Document doc) throws IOException {
		IndexWriter writer = new IndexWriter(directory, config);
		writer.addDocument(doc);
		writer.commit();
		writer.close();
	}

	public static Analyzer getAnalyzer() {
		return analyzer;
	}

	public static void setAnalyzer(Analyzer analyzer) {
		LuceneUtils.analyzer = analyzer;
	}

	public static Directory getDirectory() {
		return directory;
	}

	public static void setDirectory(Directory directory) {
		LuceneUtils.directory = directory;
	}

}
