package crawler;

import java.io.File;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Assert;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.StringWebResponse;
import com.gargoylesoftware.htmlunit.TopLevelWindow;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.WebWindowImpl;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.background.BackgroundJavaScriptFactory;
import com.mycrawler.core.CoreController;
import com.mycrawler.pojo.TLink;
import com.mycrawler.taobao.crawler.HomePageHotWordsCrawler;
import com.mycrawler.taobao.crawler.RelativeWordsCrawler;

public class CrawlerTest extends TestCase {

	public void testHomePageCrawler() throws Exception {
		String seed = "http://www.taobao.com/";
		List<Object> crawlersLocalData = new CoreController().crawling(seed, HomePageHotWordsCrawler.class);
		if (CollectionUtils.isNotEmpty(crawlersLocalData)) {
			List<TLink> relativeLinks = (List<TLink>) crawlersLocalData.get(0);
			if (CollectionUtils.isNotEmpty(relativeLinks)) {
				for (TLink tLinks : relativeLinks) {
					System.out.println(BeanUtils.describe(tLinks));
				}
			}
		}
	}
	
	//
	public void testRelativesCrawler() throws Exception {
		String seed = "http://s.taobao.com/search?q=奥迪&suggest=0_1&wq=aodi&suggest_query=aodi&source=suggest&initiative_id=tbindexz_20131028&spm=1.6659421.754896237.1&sourceId=tb.index&search_type=item&ssid=s5-e&commend=all";
		List<Object> crawlersLocalData = new CoreController().crawling(seed, RelativeWordsCrawler.class);
		if (CollectionUtils.isNotEmpty(crawlersLocalData)) {
			List<TLink> relativeLinks = (List<TLink>) crawlersLocalData.get(0);
			if (CollectionUtils.isNotEmpty(relativeLinks)) {
				for (TLink tLinks : relativeLinks) {
					System.out.println(BeanUtils.describe(tLinks));
				}
			}
		}
	}

	public void testHtmlUnitAJsoup() throws Exception {
		/**HtmlUnit请求web页面*/
		WebClient wc = new WebClient();
		wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
		wc.getOptions().setCssEnabled(false); //禁用css支持
		wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
		wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
		HtmlPage page = wc.getPage("http://cq.qq.com/baoliao/detail.htm?294064");
		String pageXml = page.asXml(); //以xml的形式获取响应文本
		
		/**jsoup解析文档*/
		Document doc = Jsoup.parse(pageXml, "http://cq.qq.com"); 
		Element pv = doc.select("#feed_content span").get(1);
		System.out.println(pv.text());
		Assert.assertTrue(pv.text().contains("浏览"));
		
		System.out.println("Thank God!");
	}
}
