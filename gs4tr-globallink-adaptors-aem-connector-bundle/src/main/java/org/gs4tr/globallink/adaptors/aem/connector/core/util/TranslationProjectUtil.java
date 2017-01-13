package org.gs4tr.globallink.adaptors.aem.connector.core.util;

import java.util.Calendar;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.gs4tr.projectdirector.ws.dto.Date;

import com.day.cq.commons.jcr.JcrConstants;

public class TranslationProjectUtil {
        
    public static Resource findProjectResource(String jobId,
	    ResourceResolver resolver) throws RepositoryException {
	
	Resource resource = TranslationJobUtil.findResource(jobId, resolver);
	if (resource != null) {
	    
	    return resource.getParent().getParent().getParent();
	}
	
	return null;
    }
    
    public static String getProjectTitle(String jobId,
	    ResourceResolver resolver) throws RepositoryException {
	
	Resource resource = findProjectResource(jobId, resolver);
	if (resource != null) {
	    
	    ValueMap properties = resource.adaptTo(ValueMap.class);
	    
	    if (properties.containsKey(JcrConstants.JCR_TITLE)) {
		
		return properties.get(JcrConstants.JCR_TITLE, String.class);
	    }
	}
	
	return null;
    }
    
    public static Date getProjectDueDate(String jobId,
	    ResourceResolver resolver) throws RepositoryException {
	
	Resource resource = findProjectResource(jobId, resolver);
	if (resource != null) {
	    
	    ValueMap properties = resource.adaptTo(ValueMap.class);
	    
	    if (properties.containsKey(TranslationContraints.PROJECT_DUE_DATE)) {
				
		return new Date(properties
			.get(TranslationContraints.PROJECT_DUE_DATE, Calendar.class)
			.getTimeInMillis());
	    }
	}
	
	return null;
    }
    
}