<%@page contentType="text/html" session="false" pageEncoding="utf-8"
    import="javax.jcr.Node, java.util.Iterator,
    	com.day.cq.wcm.webservicesupport.Configuration,
		com.day.cq.wcm.webservicesupport.Service,
		org.apache.commons.lang.StringEscapeUtils,
        org.apache.sling.api.resource.Resource"%>

<%@include file="/libs/foundation/global.jsp"%>
<%@include file="/libs/cq/cloudserviceconfigs/components/configpage/init.jsp"%>

<%
    String currentPageName = currentPage.getName();

    String resourcePath = resource.getPath();
    String resourceType = resource.getResourceType();

    String dialogPath = resource.getResourceResolver().getResource(resourceType).getPath() + "/dialog";
%>

<body style="padding-right: 50px !important;">
    <div>
    	<cq:include path="trail" resourceType="cq/cloudserviceconfigs/components/trail"/>
	</div>
	<p class="cq-clear-for-ie7"></p>
    <h1 style="padding-top: 15px;"><%= xssAPI.encodeForHTML(properties.get("jcr:title", currentPageName)) %></h1>
	<div>
    	<script type="text/javascript">
        CQ.WCM.edit({
            "path":"<%= resourcePath %>",
            "dialog":"<%= dialogPath %>",
            "type":"<%= resourceType %>",
            "editConfig":{
                "xtype":"editbar",
                "listeners":{
                    "afteredit":"REFRESH_PAGE"
                },
                "inlineEditing":CQ.wcm.EditBase.INLINE_MODE_NEVER,
                "disableTargeting": true,
                "actions":[
                    CQ.I18n.getMessage("Connection"),
                    { "xtype": "tbseparator" },
                    CQ.wcm.EditBase.EDIT,
                    { "xtype": "tbseparator" },
                    {
                        "xtype": "tbtext",
                        "text": "<a href='http://adobe.ly/1PGYSZ7' target='_blank' style='color: #15428B; cursor: pointer;'>"
                        		+ CQ.I18n.getMessage("Getting Started") + "</a>"
                    }
                ]
            }
        });
    	</script>
	</div>
    <cq:include script="content.jsp" />
    <%
        if (properties.get("pdWebServicesURL", "").isEmpty()
            	|| properties.get("pdUserName", "").isEmpty()
            	|| properties.get("pdUserPassword", "").isEmpty()
            	|| properties.get("pdProjectShortCode", "").isEmpty()) {
    %>
    <cq:include script="opendialog.jsp" />
    <%
    	}
	%>
</body>