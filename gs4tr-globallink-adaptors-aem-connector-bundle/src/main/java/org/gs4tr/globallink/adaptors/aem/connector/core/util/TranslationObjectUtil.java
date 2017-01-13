package org.gs4tr.globallink.adaptors.aem.connector.core.util;

import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.gs4tr.globallink.adaptors.aem.connector.core.util.search.TranslationObjectPredicateGroup;

import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;

public class TranslationObjectUtil {
    
    public static Resource findObjectResource(String translationJobId, String translationObjectId,
	    ResourceResolver resolver) throws RepositoryException {
	
	if (translationJobId != null && !translationJobId.isEmpty()
		&& translationObjectId != null && !translationObjectId.isEmpty()) {
	    
	    QueryBuilder queryBuilder = resolver.adaptTo(QueryBuilder.class);
	    if (queryBuilder != null) {
				
		Query query = queryBuilder.createQuery(
			new TranslationObjectPredicateGroup().create(translationJobId, translationObjectId),
			resolver.adaptTo(Session.class));
		
		List<Hit> queryResult = query.getResult().getHits();
		
		if (queryResult != null && queryResult.size() == 1) {
		    
		    return queryResult.get(0).getResource();
		}
	    }
	}
	
	return null;
    }
        
}