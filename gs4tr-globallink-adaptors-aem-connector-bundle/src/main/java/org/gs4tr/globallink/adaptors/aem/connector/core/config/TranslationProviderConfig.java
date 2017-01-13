package org.gs4tr.globallink.adaptors.aem.connector.core.config;

import java.net.URL;
import java.util.Map;

import org.apache.sling.api.adapter.Adaptable;
import org.apache.sling.api.adapter.SlingAdaptable;

public class TranslationProviderConfig extends SlingAdaptable implements Adaptable {
    
    public static final String WEB_SERVICES_URL = "pdWebServicesURL";
    
    public static final String USER_NAME = "pdUserName";
    
    public static final String USER_PASSWORD = "pdUserPassword";
    
    public static final String PROJECT_DEFAULT_CLASSIFIER = "pdProjectDefaultClassifier";
    
    public static final String PROJECT_SHORT_CODE = "pdProjectShortCode";
    
    public static final String PROJECT_CUSTOM_CLASSIFIER= "pdProjectCustomClassifier";
    
    private URL _webServicesURL;
    
    private String _userName;
    
    private String _userPassword;
    
    private String _projectDefaultClassifier;
    
    private Map<String,String> _projectCustomClassifier;
    
    private String _classifier;
    
    public String getClassifier(String mimeType) {
    	if(getProjectCustomClassifier().containsKey(mimeType)){
    		_classifier = getProjectCustomClassifier().get(mimeType);
    	}else{
    		_classifier = getProjectDefaultClassifier();
    	}
		return _classifier;
	}

	public  Map<String,String> getProjectCustomClassifier() {
		return _projectCustomClassifier;
	}

	public void setProjectCustomClassifier(Map<String,String> projectCustomClassifier) {
		this._projectCustomClassifier = projectCustomClassifier;
	}

	private String _projectShortCode;

    public URL getWebServicesURL() {
        return _webServicesURL;
    }

    public String getUserName() {
        return _userName;
    }

    public String getUserPassword() {
        return _userPassword;
    }

    public String getProjectDefaultClassifier() {
        return _projectDefaultClassifier;
    }

    public String getProjectShortCode() {
        return _projectShortCode;
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

    public void setProjectDefaultClassifier(String projectDefaultClassifier) {
        _projectDefaultClassifier = projectDefaultClassifier;
    }

    public void setProjectShortCode(String projectShortCode) {
        _projectShortCode = projectShortCode;
    }
    
}