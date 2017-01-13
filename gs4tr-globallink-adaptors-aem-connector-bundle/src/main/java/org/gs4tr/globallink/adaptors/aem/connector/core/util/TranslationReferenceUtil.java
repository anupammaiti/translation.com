package org.gs4tr.globallink.adaptors.aem.connector.core.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.gs4tr.globallink.adaptors.aem.connector.core.util.search.TranslationReferencePredicateGroup;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.StringConstraints;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;

public class TranslationReferenceUtil {
    
    public static String create(String srcLang, String tgtLang,
	    ResourceResolver resolver) throws PersistenceException {
		    
	Resource submissions = resolver.getResource(StringConstraints.SUBMISSIONS_PATH);
	
	String jobId = String.valueOf(new Date().getTime());
	
	Map<String, Object> properties = new HashMap<String, Object>();

	properties.put(JcrConstants.JCR_PRIMARYTYPE, JcrConstants.NT_UNSTRUCTURED);
	
	if (srcLang != null && !srcLang.isEmpty()) {
	    properties.put(TranslationContraints.SOURCE_LANGUAGE, srcLang);
	}
	
	if (tgtLang != null && !tgtLang.isEmpty()) {
	    properties.put(TranslationContraints.DESTINATION_LANGUAGE, tgtLang);
	}
	
	properties.put(TranslationContraints.TRANSLATION_OBJECT_ID, jobId);

	resolver.create(submissions, jobId, properties);
	
	resolver.commit();
				
	return jobId;
    }
        
    public static Resource findByExternalJobId(String jobExternalId,
	    ResourceResolver resolver) throws RepositoryException {
	
	QueryBuilder queryBuilder = resolver.adaptTo(QueryBuilder.class);
	if (queryBuilder != null) {
	
	    Query query = queryBuilder.createQuery(
		    new TranslationReferencePredicateGroup().create(
			    TranslationContraints.TRANSLATION_OBJECT_EXTERNAL_ID,
			    jobExternalId),
		    resolver.adaptTo(Session.class));
	    
	    List<Hit> queryResult = query.getResult().getHits();

	    if (queryResult != null && queryResult.size() == 1) {
		return queryResult.get(0).getResource();
	    }
	}
	
	return null;
    }
    
    public static Resource findByJobId(String jobId, ResourceResolver resolver)
	    throws RepositoryException {
	
	QueryBuilder queryBuilder = resolver.adaptTo(QueryBuilder.class);
	if (queryBuilder != null) {
	
	    Query query = queryBuilder.createQuery(
		    new TranslationReferencePredicateGroup().create(
			    TranslationContraints.TRANSLATION_OBJECT_ID,
			    jobId), resolver.adaptTo(Session.class));
		
	    List<Hit> queryResult = query.getResult().getHits();
	    if (queryResult != null && queryResult.size() == 1) {
		
		return queryResult.get(0).getResource();
	    }
	}
	
	return null;
    }
    
    public static String getExternalJobId(String jobId, ResourceResolver resolver)
	    throws RepositoryException {
	
	Resource resource = findByJobId(jobId, resolver);
	if (resource != null) {
	    
	    ValueMap properties = resource.adaptTo(ValueMap.class);
	    
	    if (properties.containsKey(TranslationContraints.TRANSLATION_OBJECT_EXTERNAL_ID)) {
		
		return properties.get(
			TranslationContraints.TRANSLATION_OBJECT_EXTERNAL_ID,
			String.class);
	    }
	}
	
	return null;
    }
    
    private static String getSourceLanguage(Resource resource) {
	
	ValueMap properties = resource.adaptTo(ValueMap.class);
	
	if (properties.containsKey(TranslationContraints.SOURCE_LANGUAGE)) {
	    
	    return properties.get(
		    TranslationContraints.SOURCE_LANGUAGE,
		    String.class);
	}
	
	return null;
    }
    
    public static String getSourceLanguageByExternalJobId(String jobExternalId,
	    ResourceResolver resolver) throws RepositoryException {
	
	Resource resource = findByExternalJobId(jobExternalId, resolver);
	
	if (resource != null) {
	    return getSourceLanguage(resource);
	}
	
	return null;
    }
    
    public static String getSourceLanguageByJobId(String jobId,
	    ResourceResolver resolver) throws RepositoryException {
	
	Resource resource = findByJobId(jobId, resolver);
	
	if (resource != null) {
	    return getSourceLanguage(resource);
	}
	
	return null;
    }
    
    private static String getTargetLanguage(Resource resource) {
	
	ValueMap properties = resource.adaptTo(ValueMap.class);
	
	if (properties.containsKey(TranslationContraints.DESTINATION_LANGUAGE)) {
	    
	    return properties.get(
		    TranslationContraints.DESTINATION_LANGUAGE,
		    String.class);
	}
	
	return null;
    }
    
    public static String getTargetLanguageByExternalJobId(String jobExternalId,
	    ResourceResolver resolver) throws RepositoryException {
	
	Resource resource = findByExternalJobId(jobExternalId, resolver);
	
	if (resource != null) {	    
	    return getTargetLanguage(resource);
	}
	
	return null;
    }
    
    public static String getTargetLanguageByJobId(String jobId,
	    ResourceResolver resolver) throws RepositoryException {
	
	Resource resource = findByJobId(jobId, resolver);
	
	if (resource != null) {	    
	    return getTargetLanguage(resource);
	}
	
	return null;
    }
    
    public static void setExternalJobId(String jobId, String jobExternalId,
	    ResourceResolver resolver) throws PersistenceException, RepositoryException {
	
	Resource resource = findByJobId(jobId, resolver);
	if (resource != null) {
	    
	    ModifiableValueMap properties = resource.adaptTo(ModifiableValueMap.class);
	    
	    properties.put(TranslationContraints.TRANSLATION_OBJECT_EXTERNAL_ID, jobExternalId);
	    
	    resolver.commit();
	}
    }
    
}