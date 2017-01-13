package org.gs4tr.globallink.adaptors.aem.connector.util;

import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.StringConstraints;

public class RepositoryUtil {
    
    public static Resource findRepository(Resource resource) {
	
	if (resource != null) {
	
	    String path = findRepositoryPath(resource);
	
	    if (path != null && !path.isEmpty() && path.startsWith(
		    StringConstraints.CQ_CLOUD_SERVICE_CONFIG_PATH)) {
	    
		ResourceResolver resolver = resource.getResourceResolver();
		
		return resolver.getResource(path.concat(StringConstraints.JCR_CONTENT_SUFFIX));
	    }
	}
	
	return null;
    }
    
    private static String findRepositoryPath(Resource resource) {
	
	// Check that is not root resource
	if (resource.getPath() != null) {
	    
	    Resource content = resource.getChild(JcrConstants.JCR_CONTENT);
	    if (content != null) {
	    
		ValueMap properties = content.adaptTo(ValueMap.class);		
		if (properties.containsKey(StringConstraints.CQ_CLOUD_SERVICE_CONFIGS)) {
		    
		    Object repositoryPath = properties.get(StringConstraints.CQ_CLOUD_SERVICE_CONFIGS);
		    
		    if (repositoryPath instanceof String) {
			
			return (String) repositoryPath;
		    
		    } else if (repositoryPath instanceof String[]) {
			
			String[] repositoryPaths = (String[]) repositoryPath;
			for (String repositoryTempPath : repositoryPaths) {
			    
			    if (repositoryTempPath.startsWith(
				    StringConstraints.CQ_CLOUD_SERVICE_CONFIG_PATH)) {
				
				return repositoryTempPath;
			    }
			}
		    }
		}
	    }
	    
	    return findRepositoryPath(resource.getParent());
	}
	
	return null;
    }
    
}