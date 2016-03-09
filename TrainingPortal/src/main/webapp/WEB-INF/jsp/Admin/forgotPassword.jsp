<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp" %>

<body onload='document.searchUserForm.searchUsername.focus();'>

    <!-- This is the main body of screen where forgot Password form is displayed - Start -->
	 <div class="form_settings">
     <form name='searchUserForm' id='searchUserForm' >
     <fieldset>
  		<legend><b><spring:message code="user" /><spring:message code="search" /><spring:message code="form" /></b></legend>
  			<p><span><spring:message code="username" /></span><input type="text" name="searchUsername" id="searchUsername" maxlength="30" onchange="checkAvailability()"  /> <label id="username_availability_result"></label></p>
  			<p><span><spring:message code="language"/></span>
  				<spring:message code="english"/><input type="radio" name="locale" value="en" checked="checked">
            	<spring:message code="hindi"/><input type="radio" name="locale" value="hi" >
            </p> 
            <p style="padding-top: 15px"><span>&nbsp;</span>
            <input class="submit" type="button" name="search" id="search" value="<spring:message code="search" />" />
            <input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="goToHomePage()" /></p>
  	</fieldset>
  	</form>
  	</div>
	
	<c:if test="${not empty displayForgotPasswordForm and displayForgotPasswordForm == 'true'}">
     <div class="form_settings">
     <form:form name='forgotPasswordForm' id='forgotPasswordForm' commandName="forgotPasswordDto" >
     <fieldset>
  		<legend><b><spring:message code="forgot_password" /><spring:message code="form" /></b></legend>
  			<br />
  			<label style="color: #ff0000;"><b><spring:message code="passwordPolicy" /></b></label>
  			<br /><br />
  			<form:errors path="*" cssClass="errorblock" element="div" />
  			<c:if test="${not empty errorMessages}">
				<div class="errorblock">${errorMessages}</div>
			</c:if>
  			<!-- Set all security settings and security questions in hidden field - start -->
  			<form:hidden path="dojRequired"/>
  			<form:hidden path="dobRequired"/>
  			<form:hidden path="secQuesRequired"/>
  			<form:hidden path="otpRequired"/>
  			<form:hidden path="locale"/>
  			<c:forEach items="${forgotPasswordDto.lstSecurityQuestionsLookUp}" varStatus="vs">
		        <form:hidden path="lstSecurityQuestionsLookUp[${vs.index}].lookupCode" />
		        <form:hidden path="lstSecurityQuestionsLookUp[${vs.index}].lookupDesc" />
    		</c:forEach>
  			<!-- Set all security settings and security questions in hidden field - end -->
            <p><span><spring:message code="username" /></span><form:input type="text" name="username" id="username" path="username" maxlength="20" readonly="true" onkeypress="return onlyAlphaNumeric(event)"  /></p>
            <p><span><spring:message code="new" /> <spring:message code="password" /></span><form:input type="password" name="newPassword" id="newPassword" path="newPassword" maxlength="20" /></p>
            <p><span><spring:message code="confirm" /> <spring:message code="new" /> <spring:message code="password" /></span>
            	<form:input type="password" name="confirmNewPassword" id="confirmNewPassword" path="confirmNewPassword" maxlength="20" />
			</p>
            <c:if test="${not empty forgotPasswordDto.dojRequired and forgotPasswordDto.dojRequired == 'true'}">
            	<p><span><spring:message code="doj" /></span><form:input type="text" name="txtUserDoj" id="txtUserDoj" path="txtUserDoj" maxlength="10"/></p>
            </c:if>
            <c:if test="${not empty forgotPasswordDto.dobRequired and forgotPasswordDto.dobRequired == 'true'}">
            	<p><span><spring:message code="dob" /></span><form:input type="text" name="txtUserDob" id="txtUserDob" path="txtUserDob" maxlength="10" /></p>
            </c:if>
            <c:if test="${not empty forgotPasswordDto.secQuesRequired and forgotPasswordDto.secQuesRequired == 'true'}">
            <p><span><spring:message code="secQuestion" /></span>
            	<form:select id="secretQueCode" path="secretQueCode" items="${forgotPasswordDto.lstSecurityQuestionsLookUp}" itemValue="lookupCode" itemLabel="lookupDesc" /> 
			</p>
            <p><span><spring:message code="secAnswer" /></span><form:input type="password" name="userSecAnswer" id="userSecAnswer" path="userSecAnswer" maxlength="20"/></p>
            </c:if>
            <c:if test="${not empty forgotPasswordDto.otpRequired and forgotPasswordDto.otpRequired == 'true'}">
            	<p><span><spring:message code="otp" /></span><form:input type="text" name="userOtp" id="userOtp" path="userOtp" maxlength="4" onkeypress="return onlyNumeric(event)" /></p>
            </c:if>
            <p style="padding-top: 15px"><span>&nbsp;</span>
				<input class="submit" type="button" id="forgotPassword" name="forgotPassword" style="width: 170px;" value="<spring:message code="save" />" />
            	<input class="submit" type="reset" name="reset" id="reset" value="<spring:message code="reset" />" onclick="resetForm(forgotPasswordForm)" />
            	<input class="submit" type="button" name="close" id="close" value="<spring:message code="close" />" onclick="goToLoginPage()" />
            </p>
     	</fieldset>
     </form:form>
     </div>
     
     </c:if>
        
    <!-- This is the main body of screen where forgot Password form is displayed - End -->
      
    <!--  MAIN CONTENT/BODY : END-->
</body>