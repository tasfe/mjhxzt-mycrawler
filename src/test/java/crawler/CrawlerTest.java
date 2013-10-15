package crawler;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

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
import com.mycrawler.pojo.TLinks;
import com.mycrawler.taobao.crawler.HomePageHotWordsCrawler;

public class CrawlerTest extends TestCase {

	public void testHomePageCrawler() throws Exception {
		String seed = "http://www.taobao.com/";
		List<Object> crawlersLocalData = new CoreController().crawling(seed, HomePageHotWordsCrawler.class);
		if (CollectionUtils.isNotEmpty(crawlersLocalData)) {
			List<TLinks> relativeLinks = (List<TLinks>) crawlersLocalData.get(0);
			if (CollectionUtils.isNotEmpty(relativeLinks)) {
				for (TLinks tLinks : relativeLinks) {
					System.out.println(BeanUtils.describe(tLinks));
				}
			}
		}
	}
	
	public void testHtmlUnit() throws Exception
	{
		WebClient wc = new WebClient(BrowserVersion.INTERNET_EXPLORER_8);
//		Page page =  wc.getPage("http://search.taobao.com/search?q=%CA%D6%BB%FA%BF%C7&suggest=0_1&wq=%CA%D6%BB%FA&suggest_query=%CA%D6%BB%FA&source=suggest&atab=stats_click%3Dsearch_radio_all%253A1&jc=1&initiative_id=staobaoz_20131015");
		HtmlPage page =  wc.getPage("http://cq.qq.com/baoliao/detail.htm?294064");
		WebWindow ww = new WebWindowImpl() {
			
			
			public WebWindow getTopWindow() {
				return this;
			}
			
			public WebWindow getParentWindow() {
				return this;
			}
			
			@Override
			protected boolean isJavaScriptInitializationNeeded() {
				final Page enclosedPage = getEnclosedPage();
				System.out.println("---------------");
				System.out.println(getScriptObject() == null
		            || enclosedPage.getUrl() == WebClient.URL_ABOUT_BLANK
		            || !(enclosedPage.getWebResponse() instanceof StringWebResponse));
		        return getScriptObject() == null
		            || enclosedPage.getUrl() == WebClient.URL_ABOUT_BLANK
		            || !(enclosedPage.getWebResponse() instanceof StringWebResponse);
			}
		};
//		ww.setScriptObject(BackgroundJavaScriptFactory.theFactory().createJavaScriptJobManager(ww));
		Page newPage= wc.loadWebResponseInto(page.getWebResponse(), ww);
//		System.out.println(page.getWebResponse().getContentAsString());
		System.out.println(newPage.getWebResponse().getContentAsString());
		
	}
}
