<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp"%>

<spring:url value="/element" var="formAction"></spring:url>
	
	<h2><spring:message code="different" /><spring:message code="element" /></h2>
	
	<form:form name='displayElementForm' id='displayElementForm' action="${formAction}" method="post" commandName="orgElementMstDto" >
     <fieldset>
  		<legend><b><spring:message code="display" /><spring:message code="element" /><spring:message code="form" /></b></legend>
  		
        <display:table id="element" name="elementList"
                    requestURI="${contextPath}/element/allElements" pagesize="10"  style="width:100%;" >
            <display:column>
            	<form:radiobutton path="elementCode" value="${element.elementCode}" />
        	</display:column>
            <display:column property="elementName" titleKey="elementName" />
            <display:column property="elementDesc" titleKey="elementDesc" />
            <display:column titleKey="elementType" >
            	<c:if test="${element.elementType == '6'}"><spring:message code="module" /></c:if>
            	<c:if test="${element.elementType == '7'}"><spring:message code="screen" /></c:if>
            </display:column>
            <display:column property="elementUrl" titleKey="elementUrl" />
            <display:column property="elementOrder" titleKey="elementOrder" />
            <display:column titleKey="status" >
            	<c:if test="${element.activateFlag == 'true'}"><spring:message code="active" /></c:if>
            	<c:if test="${element.activateFlag == 'false'}"><spring:message code="inactive" /></c:if>
            </display:column>
        </display:table>

		<div class="form_settings">
		<p style="padding-top: 15px"><span>&nbsp;</span>
		<form:button class="submit" id="createElementPage" name="createElementPage" ><spring:message code="create" /></form:button>
		<form:button class="submit" id="updateElementPage" name="updateElementPage" ><spring:message code="update" /></form:button>
		<form:button class="submit" id="viewElementPage" name="viewElementPage" ><spring:message code="view" /></form:button>
		<input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="goToHomePage()" /></p>
		</div>

	</fieldset>
	</form:form>
