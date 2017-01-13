package org.gs4tr.globallink.adaptors.aem.connector.model.adapter.impl;

import java.util.Calendar;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemSubmission;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemSubmissionInfo;

import com.adobe.granite.translation.api.TranslationConstants.TranslationStatus;

public class AemSubmissionAdapter {
    
    public AemSubmission adaptFromResource(Resource resource) {
	
	if (resource != null) {
	
	    AemSubmission submission = new AemSubmission();
	    
	    ValueMap properties = resource.adaptTo(ValueMap.class);
	    
	    if (properties.containsKey(AemSubmission.NAME)) {
		
		submission.setName(properties
			.get(AemSubmission.NAME, String.class));
	    }
	    
	    if (properties.containsKey(AemSubmission.DATE_CREATED)) {
		
		Calendar dateCreated = properties
			.get(AemSubmission.DATE_CREATED, Calendar.class);
		
		submission.setDateCreated(dateCreated.getTime());
	    }
	    
	    if (properties.containsKey(AemSubmission.DATE_REQUESTED)) {
		
		Calendar dateRequested = properties
			.get(AemSubmission.DATE_REQUESTED, Calendar.class);
		
		submission.setDateRequested(dateRequested.getTime());
	    }
	    
	    if (properties.containsKey(AemSubmission.CONNECTION_PATH)) {
		
		submission.setConnectionPath(properties
			.get(AemSubmission.CONNECTION_PATH, String.class));
	    }
	    
	    if (properties.containsKey(AemSubmission.REPOSITORY_PATH)) {
		
		submission.setRepositoryPath(properties
			.get(AemSubmission.REPOSITORY_PATH, String.class));
	    }
	    
	    submission.setInfo(getSubmissionInfo(properties));
	    
	    return submission;
	}
	
	return null;
    }
    
    private AemSubmissionInfo getSubmissionInfo(ValueMap properties) {
	
	AemSubmissionInfo info = new AemSubmissionInfo();
	
	if (properties.containsKey(AemSubmissionInfo.AEM_SOURCE_LANGUAGE)) {
	    
	    info.setAemSourceLanguage(properties
		    .get(AemSubmissionInfo.AEM_SOURCE_LANGUAGE, String.class));
	}
	
	if (properties.containsKey(AemSubmissionInfo.AEM_TRANSLATION_STATUS)) {
	    
	    String translationStatus = properties
		    .get(AemSubmissionInfo.AEM_TRANSLATION_STATUS, String.class);
	    
	    if (translationStatus != null && !translationStatus.isEmpty()) {
		
		info.setAemTranslationStatus(TranslationStatus.valueOf(translationStatus));
	    }
	}
	
	if (properties.containsKey(AemSubmissionInfo.PD_SOURCE_LANGUAGE)) {
	    
	    info.setPdSourceLanguage(properties
		    .get(AemSubmissionInfo.PD_SOURCE_LANGUAGE, String.class));
	}
	
	if (properties.containsKey(AemSubmissionInfo.PD_TICKET)) {
	    
	    info.setPdTicket(properties
		    .get(AemSubmissionInfo.PD_TICKET, String.class));
	}
	
	return info;
    }
    
}