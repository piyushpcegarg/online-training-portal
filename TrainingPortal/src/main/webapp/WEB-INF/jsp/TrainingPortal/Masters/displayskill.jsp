<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp"%>

<spring:url value="/skill" var="formAction"></spring:url>
	
	<h2><spring:message code="different" /><spring:message code="skill" /></h2>
	
	<form:form name='displaySkillForm' id='displaySkillForm' action="${formAction}" method="post" commandName="skillDto" >
     <fieldset>
  		<legend><b><spring:message code="display" /><spring:message code="skill" /><spring:message code="form" /></b></legend>
  		
        <display:table id="skill" name="skillList"
                    requestURI="${contextPath}/skill/allSkills" pagesize="5"  style="width:100%;" >
            <display:column>
            	<form:radiobutton path="skillCode" value="${skill.skillCode}" />
        	</display:column>
            <display:column property="skillName" titleKey="skillName" />
            <display:column property="skillDesc" titleKey="skillDesc" />
            <display:column titleKey="status" >
            	<c:if test="${skill.activateFlag == 'true'}"><spring:message code="active" /></c:if>
            	<c:if test="${skill.activateFlag == 'false'}"><spring:message code="inactive" /></c:if>
            </display:column>
        </display:table>

		<div class="form_settings">
		<p style="padding-top: 15px"><span>&nbsp;</span>
		<form:button class="submit" id="createSkillPage" name="createSkillPage" ><spring:message code="create" /></form:button>
		<form:button class="submit" id="updateSkillPage" name="updateSkillPage" ><spring:message code="update" /></form:button>
		<form:button class="submit" id="viewSkillPage" name="viewSkillPage" ><spring:message code="view" /></form:button>
		<input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="goToHomePage()" /></p>
		</div>

	</fieldset>
	</form:form>
