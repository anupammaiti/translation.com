package org.gs4tr.globallink.adaptors.aem.connector.core;

import java.util.LinkedHashMap;
import java.util.Map;

import com.adobe.granite.translation.api.TranslationScope;

public class TranslationScopeImpl implements TranslationScope{
	
	private Map<String, String> map;
	
	public TranslationScopeImpl() {
		map = new LinkedHashMap<String, String>();
	}
	
	public Map<String, String> getScopeMap() {
		
		return map;
	} 

	
	public Map<String, String> getFinalScope() {
		
		return map;
	}

	
	public int getImageCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getVideoCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getWordCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
