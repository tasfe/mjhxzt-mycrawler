package com.mycrawler.core;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 项目配置文件
 * @author Zhoutao
 *
 */
public class MyCrawlerConfig {

	private static Log logger = LogFactory.getLog(MyCrawlerConfig.class);
	private static MyCrawlerConfig config;

	/**
	 * 加载配置
	 * @return
	 * @throws IOException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 */
	public static MyCrawlerConfig getInstance()  {
		if (config == null) {
			try {
				InputStream is = MyCrawlerConfig.class.getResourceAsStream("/mycrawler.properties");
				Properties props = new Properties();
				props.load(is);
				config = new MyCrawlerConfig();
				for (Object key : props.keySet()) {
					PropertyUtils.setProperty(config, key.toString(), props.get(key));
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
			}
			
		}

		return config;
	}

	private String storeType;
	
	private String indexPath;

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getIndexPath() {
		return indexPath;
	}

	public void setIndexPath(String indexPath) {
		this.indexPath = indexPath;
	}

}
