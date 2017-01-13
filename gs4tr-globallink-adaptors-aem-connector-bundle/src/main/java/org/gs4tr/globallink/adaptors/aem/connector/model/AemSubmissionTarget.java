package org.gs4tr.globallink.adaptors.aem.connector.model;

import com.adobe.granite.translation.api.TranslationConstants.TranslationStatus;

public class AemSubmissionTarget {
    
    public static final String AEM_TARGET_LANGUAGE = "aemTargetLanguage";;
    
    public static final String AEM_TRANSLATION_STATUS = "aemTranslationStatus";
    
    public static final String PD_TARGET_LANGUAGE = "pdTargetLanguage";
    
    private String _aemTargetLanguage;
    
    private TranslationStatus _aemTranslationStatus;
    
    private String _pdTargetLanguage;

    public String getAemTargetLanguage() {
        return _aemTargetLanguage;
    }

    public TranslationStatus getAemTranslationStatus() {
        return _aemTranslationStatus;
    }

    public String getPdTargetLanguage() {
        return _pdTargetLanguage;
    }

    public void setAemTargetLanguage(String aemTargetLanguage) {
        _aemTargetLanguage = aemTargetLanguage;
    }
    
    public void setAemTranslationStatus(TranslationStatus aemTranslationStatus) {
        _aemTranslationStatus = aemTranslationStatus;
    }
    
    public void setPdTargetLanguage(String pdTargetLanguage) {
        _pdTargetLanguage = pdTargetLanguage;
    }
    
}