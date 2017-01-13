package org.gs4tr.globallink.adaptors.aem.connector.util;

import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.StringConstraints;

public class ResourceLanguageUtil {
    
    public static String findResourceLanguage(Resource resource) {
	
	if (resource != null) {
	    
	    String language = findResourceLanguageInternal(resource);
	    
	    if (language != null && !language.isEmpty()) {
		
		return language;
		
	    } else {
		
		return StringConstraints.EN;
	    }
	}
		
	throw new NullPointerException();
    }
    
    private static String findResourceLanguageInternal(Resource resource) {
	
	if (resource.getPath() != null) {
	    
	    Resource content = resource.getChild(JcrConstants.JCR_CONTENT);
	    
	    if (content != null) {
		
		ValueMap contentProperties = content.adaptTo(ValueMap.class);
		
		if (contentProperties.containsKey(JcrConstants.JCR_LANGUAGE)) {
		    
		    String language = contentProperties
			    .get(JcrConstants.JCR_LANGUAGE, String.class);
		    
		    if (!language.isEmpty()) {
			
			return language;
		    }
		}
	    }
	
	    return findResourceLanguageInternal(resource.getParent());
	}
	
	return null;
    }
    
}