<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp" %>

<script type="text/javascript">
var readOnly = "${readOnly}";
var action = "${action}";
</script>

<spring:url var="formAction" value="/trainer" ></spring:url>

<body>
    <!-- This is the main body of screen where User search form is displayed - Start -->
    <c:if test="${not empty action and action == 'create'}">
     <div class="form_settings">
     <form:form name='searchUserForm' id='searchUserForm' commandName="trainerDto" action="${formAction}" method="post">
     <fieldset>
  		<legend><b><spring:message code="user" /><spring:message code="search" /><spring:message code="form" /></b></legend>
  		<c:if test="${not empty errorMessages}">
			<div class="errorblock"><spring:message code="${errorMessages}" /></div>
		</c:if>
  		<p><span class="mandatory"><spring:message code="username" /></span><form:input type="text" name="userName" id="userName" path="userName" maxlength="30"  /></p>
            <p style="padding-top: 15px"><span>&nbsp;</span>
            <form:button class="submit" id="searchUser" name="searchUser" ><spring:message code="search" /></form:button>
            <input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="getAllTrainers()" />
  	</fieldset>
  	</form:form>
    </div>
    </c:if>
    <!-- This is the main body of screen where User search form is displayed - End -->
    
    <!-- This is the main body of screen where User form is displayed - Start -->
    <c:if test="${not empty displayUserForm and displayUserForm == 'true'}">
     <div class="form_settings">
     <form:form name='userForm' id='userForm' commandName="trainerDto" action="${formAction}" method="post">
     <fieldset>
  		<legend><b><spring:message code="user" /><spring:message code="form" /></b></legend>
  			<form:errors path="*" cssClass="errorblock" element="div" />
  			<c:if test="${not empty errorMessages}">
				<div class="errorblock"><spring:message code="${errorMessages}" /></div>
			</c:if>
  			<c:if test="${not empty successMsg}">
        		<div class="successblock"><spring:message code="${successMsg}" /></div>
    		</c:if>
    		<form:hidden path="userId"/>
    		<form:hidden path="trainerCode"/>
  			<!-- Button that triggers the popup -->
  			<p><span class="mandatory"><spring:message code="username" /></span><form:input type="text" name="userName" id="userName" path="userName" maxlength="30" onchange="checkAvailability()"  /> <label id="username_availability_result"></label></p>
            <p><span class="mandatory"><spring:message code="first" /><spring:message code="engName" /></span><form:input type="text" name="engEmpFname" id="engEmpFname" path="engEmpFname" maxlength="30" onblur="languageConversion(engEmpFname , hinEmpFname)" onkeypress="return onlyAlphabets(event)" /></p>
            <p><span class="mandatory"><spring:message code="first" /><spring:message code="hinName" /></span><form:input type="text" name="hinEmpFname" id="hinEmpFname" path="hinEmpFname" maxlength="30" /></p>
            <p><span><spring:message code="middle" /><spring:message code="engName" /></span><form:input type="text" name="engEmpMname" id="engEmpMname" path="engEmpMname" maxlength="30" onblur="languageConversion(engEmpMname , hinEmpMname)" onkeypress="return onlyAlphabets(event)" /></p>
            <p><span><spring:message code="middle" /><spring:message code="hinName" /></span><form:input type="text" name="hinEmpMname" id="hinEmpMname" path="hinEmpMname" maxlength="30" /></p>
            <p><span class="mandatory"><spring:message code="last" /><spring:message code="engName" /></span><form:input type="text" name="engEmpLname" id="engEmpLname" path="engEmpLname" maxlength="30" onblur="languageConversion(engEmpLname , hinEmpLname)" onkeypress="return onlyAlphabets(event)" /></p>
            <p><span class="mandatory"><spring:message code="last" /><spring:message code="hinName" /></span><form:input type="text" name="hinEmpLname" id="hinEmpLname" path="hinEmpLname" maxlength="30" /></p>
            <p><span class="mandatory"><spring:message code="gender" /></span>
            	<spring:message code="male" /><form:radiobutton name="empGender" id="empGender" path="empGender" value="M" /> 
				<spring:message code="female" /><form:radiobutton name="empGender" id="empGender" path="empGender" value="F" />
         	</p>
            <p><span class="mandatory"><spring:message code="doj" /></span><form:input type="date" name="empDoj" id="empDoj" path="empDoj" maxlength="10"  /></p>
            <p><span class="mandatory"><spring:message code="dob" /></span><form:input type="date" name="empDob" id="empDob" path="empDob" maxlength="10"  /></p>
            <p><span class="mandatory"><spring:message code="email" /></span><form:input type="text" name="email" id="email" path="email" maxlength="40"  /></p>
            <p><span class="mandatory"><spring:message code="mobile" /></span><form:input type="text" name="regMobileNo" id="regMobileNo" path="regMobileNo" maxlength="10" onkeypress="return onlyNumeric(event)"  onchange="checkMobileAvailability()" /><label id="mobile_availability_result"></label></p>
            <p><span class="mandatory"><spring:message code="otpEnabled" /></span>
            	<spring:message code="yes" /><form:radiobutton name="otpEnabled" id="otpEnabled" path="otpEnabled" value="true" /> 
				<spring:message code="no" /><form:radiobutton name="otpEnabled" id="otpEnabled" path="otpEnabled" value="false" />
         	</p>
			<p><span><spring:message code="userImage" /></span><img src="data:image/jpeg;base64,${trainerDto.encodedImageString}" alt="user_image" width="100" height="100" style="padding: 10px;" /></p>
			<br /><br />
			<c:if test="${not empty action and action != 'create'}">
            <p><span><spring:message code="trainer" /><spring:message code="status" /></span>
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
            			<form:button class="submit" id="saveTrainer" name="saveTrainer" ><spring:message code="save" /></form:button>
					</c:if>
					<c:if test="${not empty action and action == 'update'}">
						<form:button class="submit" id="updateTrainer" name="updateTrainer" ><spring:message code="update" /></form:button>
					</c:if>
            		<input class="submit" type="reset" name="reset" id="reset" value="<spring:message code="reset" />" onclick="resetForm(userForm)" />
            	</c:if>
            				
	            <input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="getAllTrainers()" />
            </p>
            
	</form:form>
    </div>
    </c:if>
        
    <!-- This is the main body of screen where User form is displayed - End -->
        
</body>
</html>