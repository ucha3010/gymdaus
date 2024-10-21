package com.gymdaus.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Level;

public class LoggerMapper {

	public static void methodIn (Level level, String method, Object message, Class<?> clase) {
		log(level, "ENTRADA " + method, message, clase);
	}

	public static void methodOut (Level level, String method, Object message, Class<?> clase) {
		log(level, "SALIDA " + method, message, clase);
	}
	
	public static void log(Level level, String method, Object message, Class<?> clase) {
		final Log LOGGER = LogFactory.getLog(clase);
		if(Level.DEBUG.equals(level)) {
			LOGGER.debug(method + " - " + message);
		} else if(Level.INFO.equals(level)) {
			LOGGER.info(method + " - " + message);
		} else if(Level.WARN.equals(level)) {
			LOGGER.warn(method + " - " + message);
		} else if(Level.ERROR.equals(level)) {
			LOGGER.error(method + " - " + message);
		} else {
			LOGGER.error(method + " - " + message);
		}
	}

}
