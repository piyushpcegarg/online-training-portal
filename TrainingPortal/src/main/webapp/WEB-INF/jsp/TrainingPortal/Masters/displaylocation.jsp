<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp"%>

<spring:url value="/location" var="formAction"></spring:url>
	
	<h2><spring:message code="different" /><spring:message code="location" /></h2>
	
	<form:form name='displayLocationForm' id='displayLocationForm'  action="${formAction}" method="post" commandName="locationDto" >
     <fieldset>
  		<legend><b><spring:message code="display" /><spring:message code="location" /><spring:message code="form" /></b></legend>
  		
        <display:table id="location" name="locationList"
                    requestURI="${contextPath}/location/allLocations" pagesize="5"  style="width:100%;" >
            <display:column>
            	<form:radiobutton path="locationCode" value="${location.locationCode}" />
        	</display:column>
            <display:column property="locationName" titleKey="locationName" />
            <display:column titleKey="status" >
            	<c:if test="${location.activateFlag == 'true'}"><spring:message code="active" /></c:if>
            	<c:if test="${location.activateFlag == 'false'}"><spring:message code="inactive" /></c:if>
            </display:column>
        </display:table>

		<div class="form_settings">
		<p style="padding-top: 15px"><span>&nbsp;</span>
		<form:button class="submit" id="createLocationPage" name="createLocationPage" ><spring:message code="create" /></form:button>
		<form:button class="submit" id="updateLocationPage" name="updateLocationPage" ><spring:message code="update" /></form:button>
		<form:button class="submit" id="viewLocationPage" name="viewLocationPage" ><spring:message code="view" /></form:button>
		<input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="goToHomePage()" /></p>
		</div>

	</fieldset>
	</form:form>
