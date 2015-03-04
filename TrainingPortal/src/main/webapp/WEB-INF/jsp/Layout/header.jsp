<%@page import="java.util.ArrayList"%>
<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="com.gargorg.Admin.valueObject.UserElements"%>

<c:set var="contextPath" scope="application" >${pageContext.request.contextPath}</c:set>

<sec:authorize access="authenticated" var="authenticated"/>
<c:url var="logoutUrl" value="/logout"/>
<script type="text/javascript">
var contextPathJs = "${contextPath}";
</script>

<!-- START HEADER -->
<header>
	<div id="logo">
		<div id="logo_text">
			<!-- class="logo_colour", allows you to change the colour of the text -->
			<h1>
				<a href="#"><spring:message code="Online" /><span class="logo_colour"><spring:message code="training_portal" /></span></a>
			</h1>
			<h2><spring:message code="logo_text" /></h2>
		</div>
	</div>
	<c:if test="${authenticated}">
		<div class="sf-menu" id="profile"
			style="color: #0000FF; width: 400px; height: 20px; float: right; color: black;">
			<p>
				&nbsp;&nbsp;<a href="<spring:url value='/home' />" style="color: black; font-weight: bold;"><spring:message code="home" /></a>&nbsp;&nbsp;|
				&nbsp;&nbsp;<a href="<spring:url value='/myProfile' />" style="color: black; font-weight: bold;"><spring:message code="my_profile" /></a>&nbsp;&nbsp;|
				&nbsp;&nbsp;<a href="<spring:url value='/passwordChange' />" style="color: black; font-weight: bold;"><spring:message code="change_password" /></a>&nbsp;&nbsp;|
				&nbsp;&nbsp;<a href="${logoutUrl}" style="color: black;font-weight: bold;"><spring:message code="logout" /></a>&nbsp;&nbsp;
			</p>
		</div>
		<div id="profile" style="width: 400px; height: 100px; float: right;">
			<div style="width: 100px; height: 100px; float: left;">
			<img src="data:image/jpeg;base64,<sec:authentication property="principal.userImage.encodedImageString" />" alt="user_image" width="80" height="80" style="padding: 10px;" />
			
			</div>
			<div style="width: 300px; height: 100px; padding: 20px; color: black;">
				<p style="padding: 0 0 10px 0; line-height: 1em;"><spring:message code="welcome" />&nbsp;<sec:authentication property="principal.employee.empFname" />&nbsp;<sec:authentication property="principal.employee.empLname" />
					</p>
					<sec:authentication property="principal.authorities" var="setAuthorities" />
					<c:forEach items="${setAuthorities}" var="roleName">
    					<p style="padding: 0 0 10px 0; line-height: 1em;">${roleName}</p>
					</c:forEach>
			</div>
		</div>
		<nav>
	<%
	UserElements userElements= (UserElements)session.getAttribute("navigationUserElements");
    if(userElements!=null)
    {
    	String contextPathUrl = (String)request.getContextPath();
		List<Integer> firstLevelModuleIndex = userElements.getRootChildElements();
 		
 		StringBuffer finalUlLiString = new StringBuffer();
 		Integer elementIndex;
		
		finalUlLiString.append("<ul class=\"sf-menu\" id=\"nav\">");
		for(int k=0;k< firstLevelModuleIndex.size();k++)
		{
			elementIndex = (Integer)firstLevelModuleIndex.get(k);
			finalUlLiString.append(userElements.getUlLiStringForElement(elementIndex,contextPathUrl));
		}										
		finalUlLiString.append("</ul>");
		out.println(finalUlLiString);
	}	
	%>
	</nav>
	</c:if>
</header>
<!-- END HEADER -->