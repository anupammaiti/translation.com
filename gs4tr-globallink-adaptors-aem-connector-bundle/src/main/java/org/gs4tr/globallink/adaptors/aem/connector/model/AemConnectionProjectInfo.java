package org.gs4tr.globallink.adaptors.aem.connector.model;

public class AemConnectionProjectInfo {
    
    public static final String DEFAULT_CLASSIFIER = "pdProjectDefaultClassifier";
    
    public static final String SHORT_CODE = "pdProjectShortCode";
    
    public static final String CUSTOM_CLASSIFIER="pdProjectCustomClassifier";
    
    private String _defaultClassifier;
 
    private String[] _customClassifier;
    
    public String[] getCustomClassifier() {
		return _customClassifier;
	}

	public void setCustomClassifier(String[] customClassifier) {
		this._customClassifier = customClassifier;
	}

	private String _shortCode;

    public String getDefaultClassifier() {
        return _defaultClassifier;
    }

    public String getShortCode() {
        return _shortCode;
    }

    public void setDefaultClassifier(String defaultClassifier) {
        _defaultClassifier = defaultClassifier;
    }

    public void setShortCode(String shortCode) {
        _shortCode = shortCode;
    }
    
}