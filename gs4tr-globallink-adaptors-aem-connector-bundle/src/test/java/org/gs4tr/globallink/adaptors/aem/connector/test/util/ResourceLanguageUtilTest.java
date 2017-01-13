package org.gs4tr.globallink.adaptors.aem.connector.test.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;

import org.gs4tr.globallink.adaptors.aem.connector.util.ResourceLanguageUtil;
import org.gs4tr.globallink.adaptors.aem.connector.util.constraints.StringConstraints;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResourceLanguageUtilTest {

    private Resource root;
    
    private Resource content;
    
    private Resource sitePage;
    
    private Resource sitePageContent;
    
    Map<String, Object> sitePageContentMap = new HashMap<String, Object>();
    
    private Resource languagePage;
    
    private Resource languagePageContent;
    
    Map<String, Object> languagePageContentMap = new HashMap<String, Object>();
    
    private Resource childPage;
    
    private Resource childPageContent;
    
    Map<String, Object> childPageContentMap = new HashMap<String, Object>();
    
    @Before
    public void initialize() {
	
	root = mock(Resource.class);
	
	content = mock(Resource.class);
	
	sitePage = mock(Resource.class);
	sitePageContent = mock(Resource.class);
    
	languagePage = mock(Resource.class);
	languagePageContent = mock(Resource.class);
	
	childPage = mock(Resource.class);
	childPageContent = mock(Resource.class);
		
	initializeContent();
	
	initializeSitePage();
	initializeSitePageContent();
	
	initializeLanguagePage();
	initializeLanguagePageContent();
	
	initializeChildPage();
	initializeChildPageContent();
    }
    
    private void initializeContent() {
	
	when(content.getParent()).thenReturn(root);
	
	when(content.getPath()).thenReturn("/content");
    }
    
    private void initializeSitePage() {
	
	when(sitePage.getChild(JcrConstants.JCR_CONTENT)).thenReturn(sitePageContent);
	
	when(sitePage.getParent()).thenReturn(content);
	
	when(sitePage.getPath()).thenReturn("/content/site");
    }
    
    private void initializeSitePageContent() {
	
	when(sitePageContent.adaptTo(ValueMap.class)).thenReturn(
		new ValueMapDecorator(sitePageContentMap));
	
	when(sitePageContent.getParent()).thenReturn(sitePage);
	
	when(sitePageContent.getPath()).thenReturn("/content/site"
		.concat(StringConstraints.JCR_CONTENT_SUFFIX));
    }
    
    private void initializeLanguagePage() {
	
	when(languagePage.getChild(JcrConstants.JCR_CONTENT)).thenReturn(languagePageContent);
	
	when(languagePage.getParent()).thenReturn(sitePage);
	
	when(languagePage.getPath()).thenReturn("/content/site/language");
    }
    
    private void initializeLanguagePageContent() {
	
	when(languagePageContent.adaptTo(ValueMap.class)).thenReturn(
		new ValueMapDecorator(languagePageContentMap));
	
	when(languagePageContent.getParent()).thenReturn(sitePage);
	
	when(languagePageContent.getPath()).thenReturn("/content/site/language"
		.concat(StringConstraints.JCR_CONTENT_SUFFIX));
    }
    
    private void initializeChildPage() {
	
	when(childPage.getChild(JcrConstants.JCR_CONTENT)).thenReturn(childPageContent);
	
	when(childPage.getParent()).thenReturn(languagePage);
	
	when(childPage.getPath()).thenReturn("/content/site/language/page");
    }
    
    private void initializeChildPageContent() {
	
	when(childPageContent.adaptTo(ValueMap.class)).thenReturn(
		new ValueMapDecorator(languagePageContentMap));
	
	when(childPageContent.getParent()).thenReturn(childPage);
	
	when(childPageContent.getPath()).thenReturn("/content/site/language/page"
		.concat(StringConstraints.JCR_CONTENT_SUFFIX));
    }
    
    @Test
    public void findResourceLanguageFromCurrentResourceTest() {
	
	languagePageContentMap.put(JcrConstants.JCR_LANGUAGE, "fr");
	
	String language = ResourceLanguageUtil.findResourceLanguage(languagePage);
	
	Assert.assertEquals("fr", language);
    }
    
    @Test
    public void findResourceLanguageFromChildResourceTest() {
	
	languagePageContentMap.put(JcrConstants.JCR_LANGUAGE, "fr");
	
	String language = ResourceLanguageUtil.findResourceLanguage(childPage);
	
	Assert.assertEquals("fr", language);
    }
    
    
    @Test
    public void findResourceLanguageWhenLanguageNotSet() {
	
	String language = ResourceLanguageUtil.findResourceLanguage(childPage);
	
	Assert.assertEquals(StringConstraints.EN, language);
    }
    
    @Test(expected = NullPointerException.class)
    public void findResourceLanguageNullPointerExceptionTest() {
	
	ResourceLanguageUtil.findResourceLanguage(null);
    }
    
}