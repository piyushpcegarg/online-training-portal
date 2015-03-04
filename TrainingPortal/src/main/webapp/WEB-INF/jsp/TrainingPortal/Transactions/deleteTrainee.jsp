<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp"%>

<spring:url value="/trainee" var="formAction"></spring:url>

<script type="text/javascript">
var readOnly = "${readOnly}";
</script>

	<h2><spring:message code="userMapWithTraining" /> ${trainingName}</h2>
	
	<form:form name='deleteTrainingTraineeForm' id='deleteTrainingTraineeForm' action="${formAction}" method="post" commandName="traineeDto" >
     <fieldset>
  		<legend><b><spring:message code="display" /><spring:message code="training" /><spring:message code="trainee" /><spring:message code="form" /></b></legend>
  		
  		<c:if test="${not empty errorMessages}">
			<div class="errorblock"><spring:message code="${errorMessages}" /></div>
		</c:if>
 			<c:if test="${not empty successMsg}">
       		<div class="successblock"><spring:message code="${successMsg}" /></div>
   		</c:if>
   		
   		<form:hidden path="trainingCode"/>
   		<form:label path="trainingName"></form:label>
   		<form:label path="courseName"></form:label>
   		<form:label path="trainingDate"></form:label>
  		
  		<display:table id="traineeDto" name="lstTraineeDto" style="width:100%;" >
            <display:column>
            	<form:checkbox path="lstTraineeId" value="${traineeDto.userId}" />
        	</display:column>
            <display:column property="userName" titleKey="username" />
            <display:column property="empFname" titleKey="empFname" />
            <display:column property="empLname" titleKey="empLname" />
            <display:column property="regMobileNo" titleKey="mobile" />
            <display:column titleKey="status" >
            	<c:if test="${traineeDto.activateFlag == 'true'}"><spring:message code="active" /></c:if>
            	<c:if test="${traineeDto.activateFlag == 'false'}"><spring:message code="inactive" /></c:if>
            </display:column>
        </display:table>

	<div class="form_settings">
		<p style="padding-top: 15px"><span>&nbsp;</span>
		<form:button class="submit" id="deleteTrainee" name="deleteTrainee" ><spring:message code="delete" /></form:button>
		<input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="getAllTrainingsForTrainees()" /></p>
	</div>
	
	</fieldset>
	</form:form>