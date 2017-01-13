package org.gs4tr.globallink.adaptors.aem.connector.core.util;

import java.util.Calendar;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.gs4tr.globallink.adaptors.aem.connector.core.util.search.TranslationJobPredicateGroup;
import org.gs4tr.projectdirector.ws.dto.Date;

import com.adobe.granite.translation.api.TranslationConstants.TranslationStatus;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;

public class TranslationJobUtil {
    
    public static Resource findResource(String translationJobId,
	    ResourceResolver resourceResolver) throws RepositoryException {
	
	if (translationJobId != null && !translationJobId.isEmpty()) {
	    
	    QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
	    if (queryBuilder != null) {
		
		Query query = queryBuilder.createQuery(
			new TranslationJobPredicateGroup().create(translationJobId),
			resourceResolver.adaptTo(Session.class));
		
		List<Hit> queryResult = query.getResult().getHits();
		
		if (queryResult != null && queryResult.size() == 1) {
		    
		    return queryResult.get(0).getResource();
		}
	    }
	}
	
	return null;
    }
    
    public static Date getDueDate(String translationJobId, ResourceResolver resolver)
	    throws RepositoryException {
	
	Resource resource = findResource(translationJobId, resolver);
	if (resource != null) {
	    
	    ValueMap properties = resource.adaptTo(ValueMap.class);
	    
	    if (properties.containsKey(TranslationContraints.JOB_DUE_DATE)) {
		
		return new Date(properties
			.get(TranslationContraints.JOB_DUE_DATE, Calendar.class)
			.getTimeInMillis());
	    }
	}
	
	return null;
    }
    
    public static TranslationStatus getStatus(String translationJobId, ResourceResolver resolver)
	    throws RepositoryException {
	
	Resource resource = findResource(translationJobId, resolver);
	if (resource != null) {
	    
	    ValueMap properties = resource.adaptTo(ValueMap.class);
	    
	    if (properties.containsKey(TranslationContraints.TRANSLATION_STATUS)) {
		
		String translationJobStatus = properties.get(
			TranslationContraints.TRANSLATION_STATUS,
			String.class);
		
		return TranslationStatus.valueOf(translationJobStatus);
	    }
	}
		
	return TranslationStatus.UNKNOWN_STATE;
    }
    
}