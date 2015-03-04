<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp"%>

<spring:url value="/trainerSkill" var="formAction"></spring:url>

<script type="text/javascript">
var readOnly = "${readOnly}";
</script>

	<h2><spring:message code="skillMapWithTrainer" /> ${trainerName}</h2>
	
	<form:form name='trainerSkillForm' id='trainerSkillForm' action="${formAction}" method="post" commandName="trainerDto" >
     <fieldset>
  		<legend><b><spring:message code="display" /><spring:message code="trainer" /><spring:message code="skill" /><spring:message code="form" /></b></legend>
  		
  		<c:if test="${not empty errorMessages}">
			<div class="errorblock"><spring:message code="${errorMessages}" /></div>
		</c:if>
 			<c:if test="${not empty successMsg}">
       		<div class="successblock"><spring:message code="${successMsg}" /></div>
   		</c:if>
  		
  		<form:hidden path="trainerCode"/>
  		<form:hidden path="userName"/>
  		
  		<display:table id="trainerSkillDto" name="lstTrainerSkillDto" style="width:100%;" >
            <display:column>
            	<form:checkbox path="lstSkillCodes" value="${trainerSkillDto.skillCode}" />
        	</display:column>
            <display:column property="skillName" titleKey="skillName" />
            <display:column property="skillDesc" titleKey="skillDesc" />
            <display:column titleKey="status" >
            	<c:if test="${trainerSkillDto.activateFlag == 'true'}"><spring:message code="active" /></c:if>
            	<c:if test="${trainerSkillDto.activateFlag == 'false'}"><spring:message code="inactive" /></c:if>
            </display:column>
        </display:table>

	<div class="form_settings">
		<p style="padding-top: 15px"><span>&nbsp;</span>
		<c:if test="${not empty action and action == 'update'}">
			<form:button class="submit" id="updateTrainerSkill" name="updateTrainerSkill" ><spring:message code="update" /></form:button>
		</c:if>
		<input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="getAllTrainerSkills()" /></p>
	</div>
	
	</fieldset>
	</form:form>