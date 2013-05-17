package dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DaoFactory {
	private static final ApplicationContext cxt = new ClassPathXmlApplicationContext(
			"spring-config.xml","spring-bean.xml");
	

	public static TLinksHome getTLinksHome() {
		return cxt.getBean(TLinksHome.class);
	}
	
	public static TActionHome getTActionHome() {
		return cxt.getBean(TActionHome.class);
	}
	
	public static TSeedHome getTSeedHome() {
		return cxt.getBean(TSeedHome.class);
	}
}
