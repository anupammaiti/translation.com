package org.gs4tr.globallink.adaptors.aem.connector.model.adapter.impl;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.gs4tr.globallink.adaptors.aem.connector.model.AemSubmissionTarget;

import com.adobe.granite.translation.api.TranslationConstants.TranslationStatus;

public class AemSubmissionTargetAdapter {
    
    public AemSubmissionTarget adaptFromResource(Resource resource) {
	
	if (resource != null) {
	    
	    AemSubmissionTarget target = new AemSubmissionTarget();
	    
	    ValueMap properties = resource.adaptTo(ValueMap.class);
	    
	    if (properties.containsKey(AemSubmissionTarget.AEM_TARGET_LANGUAGE)) {
		
		target.setAemTargetLanguage(properties
			.get(AemSubmissionTarget.AEM_TARGET_LANGUAGE, String.class));
	    }
	    
	    if (properties.containsKey(AemSubmissionTarget.AEM_TRANSLATION_STATUS)) {
		
		String translationStatus = properties
			.get(AemSubmissionTarget.AEM_TRANSLATION_STATUS, String.class);
		
		target.setAemTranslationStatus(TranslationStatus.valueOf(translationStatus));
	    }
	    
	    if (properties.containsKey(AemSubmissionTarget.PD_TARGET_LANGUAGE)) {
		
		target.setPdTargetLanguage(properties
			.get(AemSubmissionTarget.PD_TARGET_LANGUAGE, String.class));
	    }
	    
	    return target;
	}
	
	return null;
    }
    
}