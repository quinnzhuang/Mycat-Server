package io.mycat.sql.dialect.mysql.insert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mycat.config.model.SystemConfig;

public final class PartionKeyUpdateHandlerFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(PartionKeyUpdateHandlerFactory.class);
	
	private static final String USER_CONF_FILE_NAME = "user-config.properties";
	
	private static final String HANDLER_CLASS_NAME = "partion.key.update.handler";
	
	private static Properties properties = new Properties();
	
	static {
		InputStream inStream = null;
		try {
			inStream = getConfigStream();
			properties.load(inStream);
		} catch (Exception e) {
		} finally {
			if (inStream != null)
				try {
					inStream.close();
				} catch (IOException e) {
				}
		}
	}
	
	private static InputStream getConfigStream() throws IOException {
		String home = SystemConfig.getHomePath();
		File conf = new File(new File(home, "conf"), USER_CONF_FILE_NAME);
		if (conf.exists()) {
			return new FileInputStream(conf);
		}
		return PartionKeyUpdateHandlerFactory.class.getResourceAsStream("/" + USER_CONF_FILE_NAME);
	}
	
	public static PartionKeyUpdateHandler createHandler() {
		if (properties.containsKey(HANDLER_CLASS_NAME)) {
			String className = properties.get(HANDLER_CLASS_NAME).toString();
			Class<?> clazz = null;
			try {
				clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
			} catch (Exception e) {
			}
			if (clazz == null) {
				try {
					clazz = PartionKeyUpdateHandlerFactory.class.getClassLoader().loadClass(className);
				} catch (Exception e) {
				}
			}
			try {
				return (PartionKeyUpdateHandler)clazz.newInstance();
			} catch (Exception e) {
				logger.warn("Can't create the handler class {}, Please check the {} file", className, USER_CONF_FILE_NAME);
			}
		}
		return new DefaultPartionKeyUpdateHandler();
	}

}
