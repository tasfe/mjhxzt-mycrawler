package crawler;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import com.mycrawler.core.CoreController;
import com.mycrawler.dao.DaoFactory;
import com.mycrawler.dao.TLinksHome;
import com.mycrawler.pojo.TLinks;
import com.mycrawler.pojo.TSeed;
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
}
