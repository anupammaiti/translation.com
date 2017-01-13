package org.gs4tr.globallink.adaptors.aem.connector.model;

import java.util.Set;

import com.adobe.granite.translation.api.TranslationConstants.TranslationStatus;

public class AemSubmissionInfo {
    
    public static final String AEM_SOURCE_LANGUAGE = "aemSourceLanguage";

    public static final String AEM_TRANSLATION_STATUS = "aemTranslationStatus";
        
    public static final String PD_SOURCE_LANGUAGE = "pdSourceLanguage";

    public static final String PD_TICKET = "pdTicket";
    
    private String _aemSourceLanguage;
    
    private TranslationStatus _aemTranslationStatus;
    
    private String _pdSourceLanguage;

    private String _pdTicket;
    
    private Set<AemSubmissionTarget> _targets;

    public String getAemSourceLanguage() {
        return _aemSourceLanguage;
    }

    public TranslationStatus getAemTranslationStatus() {
        return _aemTranslationStatus;
    }

    public String getPdSourceLanguage() {
        return _pdSourceLanguage;
    }

    public String getPdTicket() {
        return _pdTicket;
    }
    
    public Set<AemSubmissionTarget> getTargets() {
        return _targets;
    }

    public void setAemSourceLanguage(String aemSourceLanguage) {
        _aemSourceLanguage = aemSourceLanguage;
    }

    public void setAemTranslationStatus(TranslationStatus aemTranslationStatus) {
        _aemTranslationStatus = aemTranslationStatus;
    }

    public void setPdSourceLanguage(String pdSourceLanguage) {
        _pdSourceLanguage = pdSourceLanguage;
    }

    public void setPdTicket(String pdTicket) {
        _pdTicket = pdTicket;
    }
    
    public void setTargets(Set<AemSubmissionTarget> targets) {
        _targets = targets;
    }
    
}