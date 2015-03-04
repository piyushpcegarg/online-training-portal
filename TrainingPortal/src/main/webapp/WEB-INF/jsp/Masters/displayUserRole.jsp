<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp"%>

<spring:url value="/userRole" var="formAction"></spring:url>
	
	<h2><spring:message code="different" /><spring:message code="user" /><spring:message code="role" /></h2>
	
	<form:form name='displayUserRoleForm' id='displayUserRoleForm' action="${formAction}" method="post" commandName="userDto" >
     <fieldset>
  		<legend><b><spring:message code="display" /><spring:message code="user" /><spring:message code="role" /><spring:message code="form" /></b></legend>
  		
  		<display:table id="user" name="userList"
                    requestURI="${contextPath}/userRole/allUserRoles" pagesize="5"  style="width:100%;" >
            <display:column>
            	<form:radiobutton path="userId" value="${user.userId}" />
        	</display:column>
            <display:column property="engEmpFname" titleKey="empFname" />
            <display:column property="engEmpLname" titleKey="empLname" />
            <display:column property="userName" titleKey="username" />
            <display:column property="email" titleKey="email" />
            <display:column property="regMobileNo" titleKey="mobile" />
            <display:column titleKey="status" >
            	<c:if test="${user.activateFlag == 'true'}"><spring:message code="active" /></c:if>
            	<c:if test="${user.activateFlag == 'false'}"><spring:message code="inactive" /></c:if>
            </display:column>
        </display:table>

		<div class="form_settings">
		<p style="padding-top: 15px"><span>&nbsp;</span>
		<form:button class="submit" id="updateUserRolePage" name="updateUserRolePage" ><spring:message code="update" /></form:button>
		<form:button class="submit" id="viewUserRolePage" name="viewUserRolePage" ><spring:message code="view" /></form:button>
		<input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="goToHomePage()" /></p>
		</div>

	</fieldset>
	</form:form>
