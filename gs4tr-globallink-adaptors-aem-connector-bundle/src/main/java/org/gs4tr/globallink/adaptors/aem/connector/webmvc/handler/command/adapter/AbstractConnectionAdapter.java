package org.gs4tr.globallink.adaptors.aem.connector.webmvc.handler.command.adapter;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.SlingHttpServletRequest;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnection;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnectionProjectInfo;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnectionProxyInfo;
import org.gs4tr.globallink.adaptors.aem.connector.model.adapter.impl.AemConnectionAdapter;

public abstract class AbstractConnectionAdapter extends AbstractTaskCommandAdapter {
	 
    private static final Log LOGGER = LogFactory.getLog(AbstractConnectionAdapter.class);
    
    protected AemConnection adaptToConnection(SlingHttpServletRequest request) {

	if (request != null) {
	    
	    AemConnection connection = new AemConnection();
	    
	    try {
		
		URL webServicesURL= new URL(request
			.getParameter(AemConnection.WEB_SERVICES_URL));
	    
		connection.setWebServicesURL(webServicesURL);
	    
		connection.setUserName(request
			.getParameter(AemConnection.USER_NAME));
	    
		connection.setUserPassword(request
			.getParameter(AemConnection.USER_PASSWORD));
	    
		connection.setProxyInfo(getProxyInfo(request));
		
		connection.getProjectInfo().setShortCode(request
			.getParameter(AemConnectionProjectInfo.SHORT_CODE));
		
		connection.getProjectInfo().setDefaultClassifier(request
			.getParameter(AemConnectionProjectInfo.DEFAULT_CLASSIFIER));
		
		String[] customerClassifierArray=request.getParameterValues(AemConnectionProjectInfo.CUSTOM_CLASSIFIER);
		if(customerClassifierArray!=null){
			LOGGER.debug("outer : "+customerClassifierArray.length);
			for(int i=0;i<customerClassifierArray.length;i++){
				LOGGER.debug(customerClassifierArray[i]+" inner : "+customerClassifierArray.length);
			}
		}
		connection.getProjectInfo().setCustomClassifier(customerClassifierArray);
	    
	    } catch (MalformedURLException exception) {
		getLogger().error(exception.getMessage(), exception);
	    }
	    
	    return connection;
	}
	
	return null;
    }
    
    private AemConnectionProxyInfo getProxyInfo(SlingHttpServletRequest request) {
	
	AemConnectionProxyInfo proxyInfo = new AemConnectionProxyInfo();
	
	String proxyEnabled = request.getParameter(AemConnectionProxyInfo.PROXY_ENABLED);
	
	if (proxyEnabled != null && !proxyEnabled.isEmpty() && Boolean.valueOf(proxyEnabled)) {
	    	    
	    proxyInfo.setProxyEnabled(Boolean.TRUE);
	    
	    String proxyHost = request.getParameter(AemConnectionProxyInfo.PROXY_HOST);
	    if (proxyHost != null && !proxyHost.isEmpty()) {
	
		proxyInfo.setProxyHost(proxyHost);
	
		String proxyPort = request.getParameter(AemConnectionProxyInfo.PROXY_PORT);
		if (proxyPort != null && !proxyPort.isEmpty()) {
		
		    proxyInfo.setProxyPort(Integer.valueOf(proxyPort));
		}
		
		proxyInfo.setProxyUserName(request
			.getParameter(AemConnectionProxyInfo.PROXY_USER_NAME));
	
		proxyInfo.setProxyUserPassword(request
			.getParameter(AemConnectionProxyInfo.PROXY_USER_PASSWORD));
	    }
	}
    	    
	return proxyInfo;
    }
        
}