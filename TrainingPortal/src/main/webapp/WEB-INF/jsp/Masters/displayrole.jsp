<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp"%>

<spring:url value="/role" var="formAction"></spring:url>
	
	<h2><spring:message code="different" /><spring:message code="role" /></h2>
	
	<form:form name='displayRoleForm' id='displayRoleForm' action="${formAction}" method="post" commandName="orgRoleMstDto" >
     <fieldset>
  		<legend><b><spring:message code="display" /><spring:message code="role" /><spring:message code="form" /></b></legend>
  		
        <display:table id="role" name="roleList" 
                    requestURI="${contextPath}/role/allRoles" pagesize="5"  style="width:100%;" >
            <display:column>
            	<form:radiobutton path="roleCode" value="${role.roleCode}" />
        	</display:column>
            <display:column property="roleName" titleKey="roleName" />
            <display:column property="roleShortName" titleKey="roleShortName" />
            <display:column property="roleDesc" titleKey="roleDesc" />
            <display:column titleKey="status" >
            	<c:if test="${role.activateFlag == 'true'}"><spring:message code="active" /></c:if>
            	<c:if test="${role.activateFlag == 'false'}"><spring:message code="inactive" /></c:if>
            </display:column>
        </display:table>

		<div class="form_settings">
		<p style="padding-top: 15px"><span>&nbsp;</span>
		<form:button class="submit" id="createRolePage" name="createRolePage" ><spring:message code="create" /></form:button>
		<form:button class="submit" id="updateRolePage" name="updateRolePage" ><spring:message code="update" /></form:button>
		<form:button class="submit" id="viewRolePage" name="viewRolePage" ><spring:message code="view" /></form:button>
		<input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="goToHomePage()" />
		</p>
		</div>

	</fieldset>
	</form:form>
