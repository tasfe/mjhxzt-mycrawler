package dao;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TLinksHomeTest extends TestCase {
	private TLinksHome linksHome = new TLinksHome();

	public void testConn() {
		ApplicationContext cxt = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		linksHome = cxt.getBean(TLinksHome.class);
		assertNull(linksHome.findById(1));
	}

}
