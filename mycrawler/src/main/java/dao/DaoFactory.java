package dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DaoFactory {
	private static final ApplicationContext cxt = new ClassPathXmlApplicationContext(
			"applicationContext.xml");

	public static TLinksHome getTLinksHome() {
		return cxt.getBean(TLinksHome.class);
	}
}
