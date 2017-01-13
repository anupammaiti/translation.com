package org.gs4tr.globallink.adaptors.aem.connector.model.adapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

public abstract class AbstractConfigAdapter {
        
    protected abstract Log getLogger();
    
    protected String[] getMappingsFromFile(String path, ResourceResolver resolver) {
	
	Set<String> mappings = new HashSet<String>();
	
	try {
	
	    Reader reader = getReader(path, resolver);
	    
	    BufferedReader bufferedReader = new BufferedReader(reader);
	    
	    String lineFromFile;
	    
	    while ((lineFromFile = bufferedReader.readLine()) != null) {
		
		String lineFromFileTrimmed = lineFromFile.trim();
		
		if (lineFromFileTrimmed != null && !lineFromFileTrimmed.isEmpty()) {
		
		    mappings.add(lineFromFile.trim());
		}
	    }
	    
	    bufferedReader.close();
	    
	} catch (FileNotFoundException exception) {
	    getLogger().error(exception.getMessage(), exception);
	} catch (IOException exception) {
	    getLogger().error(exception.getMessage(), exception);
	}
	
	return mappings.toArray(new String[mappings.size()]);
    }
    
    private Reader getReader(String path, ResourceResolver resolver) throws FileNotFoundException {

	Resource fileResource = resolver.getResource(path);
	
	if (fileResource != null) {
	    
	    return getResourceReader(path, resolver);
	    
	} else {
	    
	    return getFileReader(path);
	}
    }
    
    private Reader getResourceReader(String path, ResourceResolver resolver) {
		
	Resource fileResource = resolver.getResource(path);
	
	if (fileResource != null) {
	    
	    return new InputStreamReader(fileResource.adaptTo(InputStream.class));
	}
	
	return null;
    }

    private Reader getFileReader(String path) throws FileNotFoundException {
	
	return new FileReader(new File(path));
    }
    
}