<%@page session="false" contentType="text/html" pageEncoding="utf-8"
    import="com.day.cq.i18n.I18n,
		org.apache.sling.commons.json.JSONArray,
		org.apache.sling.commons.json.JSONObject"%>

<%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %>

<cq:defineObjects/>

<%@include file="/libs/cq/cloudserviceconfigs/components/configpage/hideeditok.jsp" %>
<%@include file="/libs/cq/cloudserviceconfigs/components/configpage/init.jsp" %>

<cq:includeClientLib categories="gs4tr-translation" />

<%
    I18n i18n = new I18n(request);
    String resourcePath = resource.getPath().replace("/jcr:content", "");

	String webServicesURL = properties.get("pdWebServicesURL", "");
	String userName = properties.get("pdUserName", "");
	String userPassword = properties.get("pdUserPassword", "").replaceAll(".", "*");

	String projectShortCode = properties.get("pdProjectShortCode", "");
	String projectDefaultClassifier = properties.get("pdProjectDefaultClassifier", "");


%>


<div>
    <h3><%= i18n.get("GlobalLink Connect: Cloud Service Configuration") %></h3>
    <img src="<%= xssAPI.encodeForHTMLAttr(thumbnailPath) %>" alt="<%= xssAPI.encodeForHTMLAttr(serviceName) %>" style="float: left;" />
    <ul style="float: left; margin: 0px 0px 0px 0px;">
        <li>
            <div class="li-bullet">
                <strong><%= i18n.get("Web Services URL") %>:</strong>
                <%= xssAPI.encodeForHTML(webServicesURL) %>
            </div>
        </li>
        <li>
            <div class="li-bullet">
                <strong><%= i18n.get("Username") %>:</strong>
                <%= xssAPI.encodeForHTML(userName) %>
            </div>
        </li>
        <li>
            <div class="li-bullet">
                <strong><%= i18n.get("Password") %>:</strong>
                <%= xssAPI.encodeForHTML(userPassword) %>
            </div>
        </li>
        <li>
            <h3 style="margin-bottom: 10px"><%= i18n.get("Project Configuration") %></h3>
        </li>
        <li>
            <div class="li-bullet">
                <strong><%= i18n.get("Short Code") %>:</strong>
                <%= xssAPI.encodeForHTML(projectShortCode) %>
            </div>
        </li>
        <li>
            <div class="li-bullet">
                <strong><%= i18n.get("Classifier") %>:</strong>
                <%= xssAPI.encodeForHTML(projectDefaultClassifier) %>
            </div>
        </li>
        <li>
            <h3 style="margin-bottom: 10px"><%= i18n.get("Cost Configuration") %></h3>
        </li>

 <li>

    <table border = "0">
        <tr><td><b>Language</b></td> <td><b>Language Code</b></td> <td><b>New Words</b></td> <td><b>Fuzzy Words</b></td> <td><b>100% Words</b></td> <td><b>Repetitions</b></td> <td><b>PM Cost Value</b></td></tr>


        <%

        Iterator<Resource> items = null;

        try{

			items = resource.getChild("/etc/cloudservices/gs4tr-translation/language-cost").listChildren();
        }
catch (Exception e){}

			if(items!=null)
            while (items.hasNext()) {

    			Resource property = items.next().getChild("jcr:content");

                if (property == null) {
        			continue;
    			}
    			else {

        		Node node = property.adaptTo(Node.class);
        		String languageName = "";
        		String languageCode = "";
				String fuzzyWords= "";
        		String newWords= "";
        		String hundMatches= "";
        		String repsWords= "";
        		String pmCost= "";
        		String pmCostSelection= "";

                if(node.hasProperty("langName"))
                languageName = node.getProperty("langName").getValue().getString();

                if(node.hasProperty("langCode"))
        		languageCode = node.getProperty("langCode").getValue().getString();

                if(node.hasProperty("fuzzyWords"))
				fuzzyWords= node.getProperty("fuzzyWords").getValue().getString();

                if(node.hasProperty("newWords"))
        		newWords= node.getProperty("newWords").getValue().getString();

                if(node.hasProperty("hundMatches"))
        		hundMatches= node.getProperty("hundMatches").getValue().getString();

                if(node.hasProperty("repsWords"))
        		repsWords= node.getProperty("repsWords").getValue().getString();
        		
        		 if(node.hasProperty("pmCost"))
        		pmCost= node.getProperty("pmCost").getValue().getString();
        		
        		 if(node.hasProperty("pmCostSelection"))
        		pmCostSelection= node.getProperty("pmCostSelection").getValue().getString();


         %>
                <tr><td><%= languageName %></td> <td><%= languageCode %></td> <td><%= newWords %></td><td><%= fuzzyWords %></td> <td><%= hundMatches %></td> <td><%= repsWords %></td> <td><%= pmCost %></td></tr>
        <%

    }
%>

<% } %>



</table>

        </li>
</ul>

</div>