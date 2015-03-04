<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp" %>

<script type="text/javascript">
var readOnly = "${readOnly}";
var action = "${action}";
</script>

<spring:url var="formAction" value="/training" ></spring:url>

<body>
    <!-- This is the main body of screen where Training form is displayed - Start -->
     <div class="form_settings">
     <form:form name='trainingForm' id='trainingForm' commandName="trainingDto" action="${formAction}" method="post">
     <fieldset>
  		<legend><b><spring:message code="training" /><spring:message code="form" /></b></legend>
  			<form:errors path="*" cssClass="errorblock" element="div" />
  			<c:if test="${not empty errorMessages}">
				<div class="errorblock"><spring:message code="${errorMessages}" /></div>
			</c:if>
  			<c:if test="${not empty successMsg}">
        		<div class="successblock"><spring:message code="${successMsg}" /></div>
    		</c:if>
    		<!-- Set all training type and locations in hidden field - start -->
    		<form:hidden path="trainingCode"/>
    		<form:hidden path="trainerCode" />
    		<c:forEach items="${trainingDto.lstTrainingType}" varStatus="vs">
		        <form:hidden path="lstTrainingType[${vs.index}].lookupCode" />
		        <form:hidden path="lstTrainingType[${vs.index}].lookupDesc" />
    		</c:forEach>
    		<c:forEach items="${trainingDto.lstLocations}" varStatus="vs">
		        <form:hidden path="lstLocations[${vs.index}].locationCode" />
		        <form:hidden path="lstLocations[${vs.index}].locationName" />
    		</c:forEach>
  			<!-- Set all training type and locations in hidden field - end -->
  			<c:if test="${not empty action and (action == 'view' or action == 'update')}">
  				<p><span class="mandatory"><spring:message code="training" /><spring:message code="name" /></span><form:input type="text" name="trainingName" id="trainingName" path="trainingName" maxlength="20"  /></p>
  			</c:if>
  			<p><span class="mandatory"><spring:message code="course" /><spring:message code="name" /></span><form:input type="text" name="courseName" id="courseName" path="courseName" maxlength="200"  /></p>
            <p><span class="mandatory"><spring:message code="trainer" /><spring:message code="name" /></span><form:input type="text" name="trainerName" id="trainerName" path="trainerName" maxlength="30" /></p>
            <p><span><spring:message code="departmentProject" /></span><form:input type="text" name="departmentProject" id="departmentProject" path="departmentProject" maxlength="100"  /></p>
            <p><span class="mandatory"><spring:message code="trainingDate" /></span><form:input type="date" name="trainingDate" id="trainingDate" path="trainingDate" maxlength="10"  /></p>
            <p><span class="mandatory"><spring:message code="trainingStartTime" /></span><form:input type="text" name="trainingStartTime" id="trainingStartTime" path="trainingStartTime" maxlength="4"  /></p>
            <p><span class="mandatory"><spring:message code="trainingEndTime" /></span><form:input type="text" name="trainingEndTime" id="trainingEndTime" path="trainingEndTime" maxlength="4"  /></p>
            <p><span class="mandatory"><spring:message code="trainingType" /></span>
            	<form:select id="trainingType" path="trainingType" items="${trainingDto.lstTrainingType}" itemValue="lookupCode" itemLabel="lookupDesc" /> 
			</p>
			<p><span class="mandatory"><spring:message code="location" /><spring:message code="name" /></span>
            	<form:select id="locationCode" path="locationCode" items="${trainingDto.lstLocations}" itemValue="locationCode" itemLabel="locationName" /> 
			</p>
			<br /><br />
            <c:if test="${not empty action and (action == 'view' or action == 'update')}">
            <p><span><spring:message code="training" /><spring:message code="status" /></span>
	            <form:select name="activateFlag" id="activateFlag" path="activateFlag" >
	            	<form:option value="true" ><spring:message code="active" /></form:option>
	            	<form:option value="false" ><spring:message code="inactive" /></form:option>
	         	</form:select>
         	</p>
         	</c:if>
         		
     	</fieldset>
     
            <p style="padding-top: 15px"><span>&nbsp;</span>
            	<c:if test="${not empty action and action != 'view'}">
            		<c:if test="${not empty action and action == 'create'}">
            			<form:button class="submit" id="saveTraining" name="saveTraining" ><spring:message code="save" /></form:button>
					</c:if>
					<c:if test="${not empty action and action == 'update'}">
						<form:button class="submit" id="updateTraining" name="updateTraining" ><spring:message code="update" /></form:button>
					</c:if>
            		<input class="submit" type="reset" name="reset" id="reset" value="<spring:message code="reset" />" onclick="resetForm(trainingForm)" />
            	</c:if>
            	
            	<input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="getAllTrainings()" />
            </p>
            
	</form:form>
    </div>
        
</body>
</html>