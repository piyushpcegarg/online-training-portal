<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp"%>
<c:if test="${not empty passwordExpiryDaysRemaining}">
	<h3 align="center" style="color: #ff0000;"><spring:message code="passwordExpiryMessage1" />${passwordExpiryDaysRemaining}<spring:message code="passwordExpiryMessage2" /></h3>
</c:if>
	
<div style="width: 500px; height: 250px;" align="center">
	<img src="${contextPath}/resources/images/logo.png"
		alt="piyush_image" width="500" height="250" style="padding: 30px 300px 30px 300px ;" />
</div>

