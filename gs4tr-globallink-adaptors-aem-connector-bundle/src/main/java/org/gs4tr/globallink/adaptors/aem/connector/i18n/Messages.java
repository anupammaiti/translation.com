package org.gs4tr.globallink.adaptors.aem.connector.i18n;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

    private static final String BUNDLE_NAME = "org.gs4tr.globallink.adaptors.aem.connector.i18n.messages";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    public static String getString(String key) {
	
	try {
	    
	    return RESOURCE_BUNDLE.getString(key);
	    
	} catch (MissingResourceException exception) {
	    return '!' + key + '!';
	}
    }

    private Messages() {

    }
    
}