package org.gs4tr.globallink.adaptors.aem.connector.model;

import java.net.URL;

import org.apache.sling.api.adapter.Adaptable;
import org.apache.sling.api.adapter.SlingAdaptable;

public class AemConnection extends SlingAdaptable implements Adaptable {
    
    public static final String WEB_SERVICES_URL = "pdWebServicesURL";
    
    public static final String USER_NAME = "pdUserName";
    
    public static final String USER_PASSWORD = "pdUserPassword";
        
    private URL _webServicesURL;
    
    private String _userName;
    
    private String _userPassword;
    
    private AemConnectionProxyInfo _proxyInfo = new AemConnectionProxyInfo();
    
    private AemConnectionProjectInfo _projectInfo = new AemConnectionProjectInfo();
    
    public URL getWebServicesURL() {
        return _webServicesURL;
    }

    public String getUserName() {
        return _userName;
    }

    public String getUserPassword() {
        return _userPassword;
    }
    
    public AemConnectionProxyInfo getProxyInfo() {
        return _proxyInfo;
    }
    
    public AemConnectionProjectInfo getProjectInfo() {
        return _projectInfo;
    }

    public Boolean isValid() {
	
	if (_webServicesURL == null
		|| _userName == null || _userName.isEmpty()
		|| _userPassword == null || _userPassword.isEmpty()) {
	    
	    return Boolean.FALSE;
	}
	
	return Boolean.TRUE;
    }
    
    public void setWebServicesURL(URL webServicesURL) {
        _webServicesURL = webServicesURL;
    }

    public void setUserName(String userName) {
        _userName = userName;
    }

    public void setUserPassword(String userPassword) {
        _userPassword = userPassword;
    }

    public void setProxyInfo(AemConnectionProxyInfo proxyInfo) {
        _proxyInfo = proxyInfo;
    }
    
    public void setProjectInfo(AemConnectionProjectInfo projectInfo) {
        _projectInfo = projectInfo;
    }

}