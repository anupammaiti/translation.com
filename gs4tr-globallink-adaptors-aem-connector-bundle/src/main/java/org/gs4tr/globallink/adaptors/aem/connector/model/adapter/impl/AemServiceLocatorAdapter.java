package org.gs4tr.globallink.adaptors.aem.connector.model.adapter.impl;

import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnection;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemConnectionProxyInfo;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.HandlerConstraints;
import org.gs4tr.projectdirector.ws.client.ServiceLocator;
import org.gs4tr.projectdirector.ws.client.commons.ProxyInfo;

public class AemServiceLocatorAdapter {
    
    public ServiceLocator adaptFromConnection(AemConnection connection) {
	
	if (connection != null && connection.isValid()) {
	    
	    if (connection.getProxyInfo() != null
		    && connection.getProxyInfo().isProxyEnabled()
		    && connection.getProxyInfo().isValid()) {
		
		AemConnectionProxyInfo connectionProxyInfo = connection.getProxyInfo();
		
		ProxyInfo proxyInfo = new ProxyInfo(
			connectionProxyInfo.getProxyHost(),
			connectionProxyInfo.getProxyPort(),
			connectionProxyInfo.getProxyUserName(),
			connectionProxyInfo.getProxyUserPassword());
		
		return new ServiceLocator(connection.getWebServicesURL(), proxyInfo,
			HandlerConstraints.DEFAULT_TIMEOUT,
			connection.getUserName(), connection.getUserPassword(),
			Boolean.TRUE);		
	    }
		
	    return new ServiceLocator(connection.getWebServicesURL(),
		    HandlerConstraints.DEFAULT_TIMEOUT,
		    connection.getUserName(), connection.getUserPassword(),
		    Boolean.TRUE);
	}
	
	return null;
    }
    
}