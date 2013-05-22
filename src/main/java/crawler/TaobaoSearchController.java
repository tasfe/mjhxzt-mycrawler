package crawler;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import pojo.TLinks;
import pojo.TSeed;
import controller.CoreController;
import dao.DaoFactory;
import dao.TLinksHome;

public class TaobaoSearchController {
	public static void main(String[] args) throws Exception {

	}

	/**
	 * 爬取淘宝搜索相关字
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void crawlingTaobaoSearch(String seed) throws Exception {
		if (seed == null || seed == "") {
			return;
		}

		List<Object> crawlersLocalData = new CoreController().crawling(seed, TaobaoSearchCrawler.class);
		if (CollectionUtils.isNotEmpty(crawlersLocalData)) {
			List<TLinks> relativeLinks = (List<TLinks>) crawlersLocalData.get(0);
			if (CollectionUtils.isNotEmpty(relativeLinks)) {
				TSeed tSeed = new TSeed();
				tSeed.setSeed(seed);
				tSeed.setLastVisitTime(new Date());
				DaoFactory.getTSeedHome().persist(tSeed);
				TLinksHome linksHome = DaoFactory.getTLinksHome();
				for (TLinks tLinks : relativeLinks) {
					tLinks.setTSeed(tSeed);
					linksHome.persist(tLinks);
				}
			}
		}
	}
	
	/**
	 * 爬取淘宝首页热搜
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void crawlingTaobaoSearchHot() throws Exception {
		String seed = "http://www.taobao.com/";

		List<Object> crawlersLocalData = new CoreController().crawling(seed, TbHomePageHotSearchCrawler.class);
		if (CollectionUtils.isNotEmpty(crawlersLocalData)) {
			List<TLinks> relativeLinks = (List<TLinks>) crawlersLocalData.get(0);
			if (CollectionUtils.isNotEmpty(relativeLinks)) {
				TSeed tSeed = new TSeed();
				tSeed.setSeed(seed);
				tSeed.setLastVisitTime(new Date());
				DaoFactory.getTSeedHome().persist(tSeed);
				TLinksHome linksHome = DaoFactory.getTLinksHome();
				for (TLinks tLinks : relativeLinks) {
					tLinks.setTSeed(tSeed);
					linksHome.persist(tLinks);
				}
			}
		}
	}

}