package crawler;

import junit.framework.TestCase;

public class ControllerTest extends TestCase {
	
	public void testConn() throws Exception {
		String baseUrl = "http://s.taobao.com/search?q=${1}&initiative_id=staobaoz_20130508";
		String url = baseUrl.replace("${1}", "阿迪达斯");
		new Controller().crawlingTaobaoSearch(url);
		assertEquals(url, "http://s.taobao.com/search?q=阿迪达斯&initiative_id=staobaoz_20130508");
	}
}
