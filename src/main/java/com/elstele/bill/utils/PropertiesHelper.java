package com.elstele.bill.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by ivan on 16/01/13.
 */
@Service
public class PropertiesHelper {

    final static Logger logger = LogManager.getLogger(PropertiesHelper.class);
    private Properties properties = null;
    private final static String KDF_UPLOAD_DIR = "kdfUploadFileDir";

    private void init(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        properties = new Properties();
        try {
            properties.load(classLoader.getResourceAsStream("bill_app.properties"));
        } catch (IOException e) {
            logger.log(Level.ERROR,"Exceptionduring loading properties file bill_app.properties" + e.getMessage(), e);
        }
    }

    public String getKDFFilesDirectory(){
        if (properties == null){
            this.init();
        }
        return properties.getProperty(KDF_UPLOAD_DIR);
    }

}