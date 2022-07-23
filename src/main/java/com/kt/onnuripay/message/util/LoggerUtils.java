package com.kt.onnuripay.message.util;

import org.slf4j.Logger;

public class LoggerUtils {

    /**
     * 
     * @param log sl4j log instance
     * @param format 
     * @param args
     */
    public static void logDebug(Logger log, String format, Object ...args) {
        if(log.isDebugEnabled()) log.debug(format, args); 
    }
}
