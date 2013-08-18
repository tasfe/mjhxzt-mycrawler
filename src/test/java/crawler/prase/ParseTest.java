/**
 * @Project: crawler4j
 *           <p>Description: </p>
 *           <p>��ģ��ֹ��ܣ�</p>
 *           <p> 2013-5-23 </p>
 * @author Zhoutao
 * @version 2.x
 */
package crawler.prase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;


public class ParseTest {
	public static void main(String[] args) throws Exception {
//		URL url = new URL("http://www.baidu.com");
		File file = new File("C:\\Users\\Administrator\\Desktop\\a.html");
		InputStream content =  new FileInputStream(file);
		HtmlParser htmlParser = new HtmlParser();
		Metadata metadata = new Metadata();
		
		BaiduContentHandler contentHandler = new BaiduContentHandler();
		InputStream inputStream = null;
		try {
			ParseContext parseContext = new ParseContext();
			// inputStream = new ByteArrayInputStream(content);
			htmlParser.parse(content, contentHandler, metadata, parseContext);
			/*if (CollectionUtils.isNotEmpty(contentHandler.getOutgoingUrls())) {
				for (ExtractedUrlAnchorPair pair : contentHandler.getOutgoingUrls()) {
					System.out.println(pair.getHref());
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
			}
		}
		
		System.out.println(contentHandler.toString());

	}
}
