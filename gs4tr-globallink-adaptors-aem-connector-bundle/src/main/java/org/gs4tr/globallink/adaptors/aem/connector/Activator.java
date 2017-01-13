package org.gs4tr.globallink.adaptors.aem.connector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gs4tr.globallink.adaptors.aem.connector.i18n.Messages;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
    
    private static final Log LOGGER = LogFactory.getLog(Activator.class);
    
    private static final String ENABLE_SNI_EXTENSION = "jsse.enableSNIExtension";
    
    private static final String FALSE = "false";
    
    
    public void start(BundleContext context) throws Exception {
	
	System.setProperty(ENABLE_SNI_EXTENSION, FALSE);
	
	LOGGER.info(Messages.getString("Activator.0"));
    }

    
    public void stop(BundleContext context) throws Exception {
	
	LOGGER.info(Messages.getString("Activator.1"));
    }

}