package org.gs4tr.globallink.adaptors.aem.connector.model.adapter.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnection;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnectionProjectInfo;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnectionProxyInfo;

public class AemConnectionAdapter {
    
    private static final Log LOGGER = LogFactory.getLog(AemConnectionAdapter.class);
    
    public AemConnection adaptFromResource(Resource resource) {
	
	if (resource != null) {
	    
	    LOGGER.info("adaptTo(AemConnection.class): ".concat(resource.getPath()));
	    
	    try {
		
		AemConnection connection = new AemConnection();
		
		ValueMap properties = resource.adaptTo(ValueMap.class);
		
		if (properties.containsKey(AemConnection.WEB_SERVICES_URL)) {
		    
		    String webServicesURL = properties.get(
			    AemConnection.WEB_SERVICES_URL, String.class);
		    
		    connection.setWebServicesURL(new URL(webServicesURL));
		    
		    if (properties.containsKey(AemConnection.USER_NAME)) {
			
			connection.setUserName(properties.get(
				AemConnection.USER_NAME, String.class));
		    }
		    
		    if (properties.containsKey(AemConnection.USER_PASSWORD)) {
			
			connection.setUserPassword(properties.get(
				AemConnection.USER_PASSWORD, String.class));
		    }
		    
		    connection.setProxyInfo(getProxyInfo(properties));
		    
		    connection.setProjectInfo(getProjectInfo(properties));
		}
		
		return connection;
		
	    } catch (MalformedURLException exception) {
		LOGGER.error(exception.getMessage(), exception);
	    }
	}
	
	return null;
    }
    
    private AemConnectionProxyInfo getProxyInfo(ValueMap properties) {
	
	AemConnectionProxyInfo proxyInfo = new AemConnectionProxyInfo();
	
	if (properties.containsKey(AemConnectionProxyInfo.PROXY_ENABLED)
		&& properties.get(AemConnectionProxyInfo.PROXY_ENABLED, Boolean.class)) {
	    
	    proxyInfo.setProxyEnabled(Boolean.TRUE);
	    
	    if (properties.containsKey(AemConnectionProxyInfo.PROXY_HOST)) {
		
		proxyInfo.setProxyHost(properties.get(
			AemConnectionProxyInfo.PROXY_HOST, String.class));
		
		if (properties.containsKey(AemConnectionProxyInfo.PROXY_PORT)) {
		    
		    proxyInfo.setProxyPort(Integer.parseInt(properties.get(
			    AemConnectionProxyInfo.PROXY_PORT, String.class)));
		}
		
		if (properties.containsKey(AemConnectionProxyInfo.PROXY_USER_NAME)) {
		    
		    proxyInfo.setProxyUserName(properties.get(
			    AemConnectionProxyInfo.PROXY_USER_NAME, String.class));
		}
		
		if (properties.containsKey(AemConnectionProxyInfo.PROXY_USER_PASSWORD)) {
		    
		    proxyInfo.setProxyUserPassword(properties.get(
			    AemConnectionProxyInfo.PROXY_USER_PASSWORD, String.class));
		}
	    }
	}
	
	return proxyInfo;
    }
    
    private AemConnectionProjectInfo getProjectInfo(ValueMap properties) {
	
	AemConnectionProjectInfo projectInfo = new AemConnectionProjectInfo();
	
	if (properties.containsKey(AemConnectionProjectInfo.DEFAULT_CLASSIFIER)) {
	    projectInfo.setDefaultClassifier(properties.get(
		    AemConnectionProjectInfo.DEFAULT_CLASSIFIER, String.class));
	}
if (properties.containsKey(AemConnectionProjectInfo.CUSTOM_CLASSIFIER)) {
	    projectInfo.setCustomClassifier(properties.get(
		    AemConnectionProjectInfo.CUSTOM_CLASSIFIER, String[].class));
	}
	if (properties.containsKey(AemConnectionProjectInfo.SHORT_CODE)) {
	    
	    projectInfo.setShortCode(properties.get(
		    AemConnectionProjectInfo.SHORT_CODE, String.class));
	}
	
	return projectInfo;
    }
    
}