<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp"%>

<spring:url value="/roleElement" var="formAction"></spring:url>

<script type="text/javascript">
var readOnly = "${readOnly}";
</script>

	<h2><spring:message code="elementMapWithRole" /> ${roleName}</h2>
	
	<form:form name='roleElementForm' id='roleElementForm' action="${formAction}" method="post" commandName="roleDto" >
     <fieldset>
  		<legend><b><spring:message code="display" /><spring:message code="role" /><spring:message code="element" /><spring:message code="form" /></b></legend>
  		
  		<c:if test="${not empty errorMessages}">
			<div class="errorblock"><spring:message code="${errorMessages}" /></div>
		</c:if>
 			<c:if test="${not empty successMsg}">
       		<div class="successblock"><spring:message code="${successMsg}" /></div>
   		</c:if>
  		
  		<form:hidden path="roleCode"/>
  		<form:hidden path="engRoleName"/>
  		
  		<display:table id="roleElementDto" name="lstRoleElementDto" style="width:100%;" >
            <display:column>
            	<form:checkbox path="lstElementCodes" value="${roleElementDto.elementCode}" />
        	</display:column>
            <display:column property="elementName" titleKey="elementName" />
            <display:column property="elementDesc" titleKey="elementDesc" />
            <display:column titleKey="elementType" >
            	<c:if test="${roleElementDto.elementType == '6'}"><spring:message code="module" /></c:if>
            	<c:if test="${roleElementDto.elementType == '7'}"><spring:message code="screen" /></c:if>
            </display:column>
            <display:column property="elementUrl" titleKey="elementUrl" />
            <display:column property="elementOrder" titleKey="elementOrder" />
            <display:column titleKey="status" >
            	<c:if test="${roleElementDto.activateFlag == 'true'}"><spring:message code="active" /></c:if>
            	<c:if test="${roleElementDto.activateFlag == 'false'}"><spring:message code="inactive" /></c:if>
            </display:column>
        </display:table>

	<div class="form_settings">
		<p style="padding-top: 15px"><span>&nbsp;</span>
		<c:if test="${not empty action and action == 'update'}">
			<form:button class="submit" id="updateRoleElement" name="updateRoleElement" ><spring:message code="update" /></form:button>
		</c:if>
		<input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="getAllRoleElements()" /></p>
	</div>
	
	</fieldset>
	</form:form>