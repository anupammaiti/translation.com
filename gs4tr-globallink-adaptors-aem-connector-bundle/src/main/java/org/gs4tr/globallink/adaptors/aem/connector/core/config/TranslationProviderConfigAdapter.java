package org.gs4tr.globallink.adaptors.aem.connector.core.config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.StringConstraints;

import com.day.cq.wcm.api.Page;

public class TranslationProviderConfigAdapter {
    
    public TranslationProviderConfig adaptFromResource(Resource resource) {
	
	if (resource != null) {
	    
	    if (resource.isResourceType(StringConstraints.CQ_PAGE)) {
		
		Page resourcePage = resource.adaptTo(Page.class);
		
		return adaptFromContentResource(resourcePage.getContentResource());
	    }
	    
	    return adaptFromContentResource(resource);
	}
	
	return null;
    }
    
    private TranslationProviderConfig adaptFromContentResource(Resource resource) {
	
	try {
		
	    TranslationProviderConfig config = new TranslationProviderConfig();
	    
	    ValueMap properties = resource.adaptTo(ValueMap.class);
	    
	    if (properties.containsKey(TranslationProviderConfig.WEB_SERVICES_URL)) {
		    
		config.setWebServicesURL(new URL(properties.get(
			TranslationProviderConfig.WEB_SERVICES_URL,
			String.class)));
	    }
		
	    if (properties.containsKey(TranslationProviderConfig.USER_NAME)) {
		
		config.setUserName(properties.get(
			TranslationProviderConfig.USER_NAME,
			String.class));
	    }
		
	    if (properties.containsKey(TranslationProviderConfig.USER_PASSWORD)) {
		    
		config.setUserPassword(properties.get(
			TranslationProviderConfig.USER_PASSWORD,
			String.class));
	    }
	    
	    if (properties.containsKey(TranslationProviderConfig.PROJECT_SHORT_CODE)) {
		
		config.setProjectShortCode(properties.get(
			TranslationProviderConfig.PROJECT_SHORT_CODE,
			String.class));
	    }
		
	    if (properties.containsKey(TranslationProviderConfig.PROJECT_DEFAULT_CLASSIFIER)) {
		
		config.setProjectDefaultClassifier(properties.get(
			TranslationProviderConfig.PROJECT_DEFAULT_CLASSIFIER,
			String.class));
	    }
	    
	    if (properties.containsKey(TranslationProviderConfig.PROJECT_CUSTOM_CLASSIFIER)) {
			
	    	String[] customClassifiers = properties.get(TranslationProviderConfig.PROJECT_CUSTOM_CLASSIFIER,
					String[].class);
	    	Map<String,String> classifiersList =  new HashMap<String,String>();
	    	if(customClassifiers!=null){
	    		for(String classifier:customClassifiers){
	    			if(classifier.length()>0){
	    				String[] mimeClassifier=classifier.split("/");
	    				classifiersList.put(mimeClassifier[0], mimeClassifier[1]);
	    			}
	    		}
	    	}
			config.setProjectCustomClassifier(classifiersList);
		    }
	    
	    return config;

	} catch (MalformedURLException exception) {
	    exception.printStackTrace();
	}
	
	return null;
    }
    
}