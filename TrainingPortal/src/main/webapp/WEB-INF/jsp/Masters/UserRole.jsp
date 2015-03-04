<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp"%>

<spring:url value="/userRole" var="formAction"></spring:url>

<script type="text/javascript">
var readOnly = "${readOnly}";
</script>

	<h2><spring:message code="roleMapWithUsername" /> ${userName}</h2>
	
	<form:form name='userRoleForm' id='userRoleForm' action="${formAction}" method="post" commandName="userDto" >
     <fieldset>
  		<legend><b><spring:message code="display" /><spring:message code="user" /><spring:message code="role" /><spring:message code="form" /></b></legend>
  		
  		<c:if test="${not empty errorMessages}">
			<div class="errorblock"><spring:message code="${errorMessages}" /></div>
		</c:if>
 		<c:if test="${not empty successMsg}">
       		<div class="successblock"><spring:message code="${successMsg}" /></div>
   		</c:if>
  		
  		<form:hidden path="userId"/>
  		<form:hidden path="userName"/>
  		
  		<display:table id="userRoleDto" name="lstUserRoleDto" style="width:100%;" >
            <display:column>
            	<form:checkbox path="lstRoleCodes" value="${userRoleDto.roleCode}" /> 
        	</display:column>
            <display:column property="roleName" titleKey="roleName" />
            <display:column property="roleShortName" titleKey="roleShortName" />
            <display:column property="roleDesc" titleKey="roleDesc" />
            <display:column titleKey="status" >
            	<c:if test="${userRoleDto.activateFlag == 'true'}"><spring:message code="active" /></c:if>
            	<c:if test="${userRoleDto.activateFlag == 'false'}"><spring:message code="inactive" /></c:if>
            </display:column>
        </display:table>

	<div class="form_settings">
		<p style="padding-top: 15px"><span>&nbsp;</span>
		<c:if test="${not empty action and action == 'update'}">
			<form:button class="submit" id="updateUserRole" name="updateUserRole" ><spring:message code="update" /></form:button>
		</c:if>
		<input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="getAllUserRoles()" /></p>
	</div>
	
	</fieldset>
	</form:form>