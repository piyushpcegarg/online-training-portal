<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp"%>

<spring:url value="/training" var="formAction"></spring:url>
	
	<h2><spring:message code="different" /><spring:message code="training" /></h2>
	
	<form:form name='displayTrainingForm' id='displayTrainingForm' action="${formAction}" method="post" commandName="trainingDto" >
     <fieldset>
  		<legend><b><spring:message code="display" /><spring:message code="training" /><spring:message code="form" /></b></legend>
  		
  		<display:table id="training" name="trainingList"
                    requestURI="${contextPath}/training/allTrainings" pagesize="10"  style="width:100%;" >
            <display:column>
            	<form:radiobutton path="trainingCode" value="${training.trainingCode}" />
        	</display:column>
            <display:column property="trainingName" titleKey="trainingName" />
            <display:column property="courseName" titleKey="courseName" />
            <display:column property="trainingDate" titleKey="trainingDate" format="{0,date,dd-MM-yyyy}" />
            <display:column titleKey="attendanceStatus" >
            	<c:if test="${training.attendanceStatus == '26'}"><spring:message code="notTaken" /></c:if>
            	<c:if test="${training.attendanceStatus == '27'}"><spring:message code="saved" /></c:if>
            	<c:if test="${training.attendanceStatus == '28'}"><spring:message code="submitted" /></c:if>
            </display:column>
            <display:column property="trainingStatus" titleKey="trainingStatus" />
            <display:column titleKey="status" >
            	<c:if test="${training.activateFlag == 'true'}"><spring:message code="active" /></c:if>
            	<c:if test="${training.activateFlag == 'false'}"><spring:message code="inactive" /></c:if>
            </display:column>
        </display:table>
  		
		<div class="form_settings">
		<p style="padding-top: 15px"><span>&nbsp;</span>
		<form:button class="submit" id="createTrainingPage" name="createTrainingPage" ><spring:message code="create" /></form:button>
		<form:button class="submit" id="updateTrainingPage" name="updateTrainingPage" ><spring:message code="update" /></form:button>
		<form:button class="submit" id="viewTrainingPage" name="viewTrainingPage" ><spring:message code="view" /></form:button>
		<input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="goToHomePage()" /></p>
		</div>

	</fieldset>
	</form:form>
