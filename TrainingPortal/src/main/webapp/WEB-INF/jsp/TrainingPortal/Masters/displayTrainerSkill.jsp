<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp"%>

<spring:url value="/trainerSkill" var="formAction"></spring:url>
	
	<h2><spring:message code="different" /><spring:message code="trainer" /><spring:message code="skill" /></h2>
	
	<form:form name='displayTrainerSkillForm' id='displayTrainerSkillForm' action="${formAction}" method="post" commandName="trainerDto" >
     <fieldset>
  		<legend><b><spring:message code="display" /><spring:message code="trainer" /><spring:message code="skill" /><spring:message code="form" /></b></legend>
  		
        <display:table id="trainer" name="trainerList"
                    requestURI="${contextPath}/trainerSkill/allTrainerSkills" pagesize="5"  style="width:100%;" >
            <display:column>
            	<form:radiobutton path="trainerCode" value="${trainer.trainerCode}" />
        	</display:column>
            <display:column property="username" titleKey="username" />
            <display:column property="engEmpFname" titleKey="empFname" />
            <display:column property="engEmpLname" titleKey="empLname" />
            <display:column property="email" titleKey="email" />
            <display:column property="regMobileNo" titleKey="mobile" />
            <display:column titleKey="status" >
            	<c:if test="${trainer.activateFlag == 'true'}"><spring:message code="active" /></c:if>
            	<c:if test="${trainer.activateFlag == 'false'}"><spring:message code="inactive" /></c:if>
            </display:column>
        </display:table>

		<div class="form_settings">
		<p style="padding-top: 15px"><span>&nbsp;</span>
		<form:button class="submit" id="updateTrainerSkillPage" name="updateTrainerSkillPage" ><spring:message code="update" /></form:button>
		<form:button class="submit" id="viewTrainerSkillPage" name="viewTrainerSkillPage" ><spring:message code="view" /></form:button>
		<input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="goToHomePage()" /></p>
		</div>

	</fieldset>
	</form:form>
