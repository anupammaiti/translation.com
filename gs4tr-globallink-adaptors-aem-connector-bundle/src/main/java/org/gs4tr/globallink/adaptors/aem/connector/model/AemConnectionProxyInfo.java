package org.gs4tr.globallink.adaptors.aem.connector.model;

public class AemConnectionProxyInfo {
    
    public static final String PROXY_ENABLED = "proxyEnabled";
    
    public static final String PROXY_HOST = "proxyHost";
    
    public static final String PROXY_PORT = "proxyPort";
    
    public static final String PROXY_USER_NAME = "proxyUserName";
    
    public static final String PROXY_USER_PASSWORD = "proxyUserPassword";
    
    private Boolean _proxyEnabled = Boolean.FALSE;
    
    private String _proxyHost;
    
    private int _proxyPort;
    
    private String _proxyUserName;
    
    private String _proxyUserPassword;

    public Boolean isProxyEnabled() {
        return _proxyEnabled;
    }

    public String getProxyHost() {
        return _proxyHost;
    }

    public int getProxyPort() {
        return _proxyPort;
    }

    public String getProxyUserName() {
        return _proxyUserName;
    }

    public String getProxyUserPassword() {
        return _proxyUserPassword;
    }

    public Boolean isValid() {
	
	if (_proxyEnabled) {
	    
	    if (_proxyHost == null
		    || _proxyHost.isEmpty()
		    || _proxyPort == 0) {
		
		return Boolean.FALSE;
	    }
	}
	
	return Boolean.TRUE;
    }
    
    public void setProxyEnabled(Boolean proxyEnabled) {
        _proxyEnabled = proxyEnabled;
    }

    public void setProxyHost(String proxyHost) {
        _proxyHost = proxyHost;
    }

    public void setProxyPort(int proxyPort) {
        _proxyPort = proxyPort;
    }

    public void setProxyUserName(String proxyUserName) {
        _proxyUserName = proxyUserName;
    }

    public void setProxyUserPassword(String proxyUserPassword) {
        _proxyUserPassword = proxyUserPassword;
    }
        
}