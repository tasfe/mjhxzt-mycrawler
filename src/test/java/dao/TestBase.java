package dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

public class TestBase extends TestCase {
	
	protected ApplicationContext getCxt() {
		ApplicationContext cxt = new ClassPathXmlApplicationContext(
				"spring-config.xml","spring-bean.xml");
		return cxt;
	}

}
