<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp" %>

<body onload='document.changePasswordForm.username.focus();'>

    <!-- This is the main body of screen where change Password form is displayed - Start -->
    <c:if test="${not empty error and error == 'credentialExpired'}">
		<div class="errorblock">
			<spring:message code="login_failed_heading" /><br /> <spring:message code="caused" />
			${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
		</div>
	</c:if>
	
	<!-- Decide the form action - start -->
	<c:choose>
		<c:when test="${not empty beforeLogin and beforeLogin == 'true'}">
			<c:set var="formAction" value="changePassword/save"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="formAction" value="passwordChange/save"></c:set>
		</c:otherwise>
	</c:choose>
	<!-- Decide the form action - end -->
	
     <div class="form_settings">
     <form:form name='changePasswordForm' id='changePasswordForm' commandName="changePasswordDto" action="${contextPath}/${formAction}" >
     <fieldset>
  		<legend><b><spring:message code="change_password" /><spring:message code="form" /></b></legend>
  			<br />
  			<label style="color: #ff0000;"><b><spring:message code="passwordPolicy" /></b></label>
  			<br /><br />
  			<form:errors path="*" cssClass="errorblock" element="div" />
  			<c:if test="${not empty errorMessages}">
				<div class="errorblock">${errorMessages}</div>
			</c:if>
  			<!-- Set all security settings and security questions in hidden field - start -->
  			<form:hidden path="beforeLogin"/>
  			<form:hidden path="dojRequired"/>
  			<form:hidden path="dobRequired"/>
  			<form:hidden path="secQuesRequired"/>
  			<form:hidden path="otpRequired"/>
  			<form:hidden path="locale"/>
  			<c:forEach items="${changePasswordDto.lstSecurityQuestionsLookUp}" varStatus="vs">
		        <form:hidden path="lstSecurityQuestionsLookUp[${vs.index}].lookupCode" />
		        <form:hidden path="lstSecurityQuestionsLookUp[${vs.index}].lookupDesc" />
    		</c:forEach>
  			<!-- Set all security settings and security questions in hidden field - end -->
            <p><span><spring:message code="username" /></span><form:input type="text" name="username" id="username" path="username" maxlength="30" readonly="true" /></p>
	        <p><span><spring:message code="old" /> <spring:message code="password" /></span><form:input type="password" name="oldPassword" id="oldPassword" path="oldPassword" maxlength="20" /></p>
            <p><span><spring:message code="new" /> <spring:message code="password" /></span><form:input type="password" name="newPassword" id="newPassword" path="newPassword" maxlength="20" /></p>
            <p><span><spring:message code="confirm" /> <spring:message code="new" /> <spring:message code="password" /></span>
            	<form:input type="password" name="confirmNewPassword" id="confirmNewPassword" path="confirmNewPassword" maxlength="20" />
			</p>
            <c:if test="${not empty changePasswordDto.dojRequired and changePasswordDto.dojRequired == 'true'}">
            	<p><span><spring:message code="doj" /></span><form:input type="text" name="txtUserDoj" id="txtUserDoj" path="txtUserDoj" maxlength="10"/></p>
            </c:if>
            <c:if test="${not empty changePasswordDto.dobRequired and changePasswordDto.dobRequired == 'true'}">
            	<p><span><spring:message code="dob" /></span><form:input type="text" name="txtUserDob" id="txtUserDob" path="txtUserDob" maxlength="10" /></p>
            </c:if>
            <c:if test="${not empty changePasswordDto.secQuesRequired and changePasswordDto.secQuesRequired == 'true'}">
            <p><span><spring:message code="secQuestion" /></span>
            	<form:select id="secretQueCode" path="secretQueCode" items="${changePasswordDto.lstSecurityQuestionsLookUp}" itemValue="lookupCode" itemLabel="lookupDesc" /> 
			</p>
            <p><span><spring:message code="secAnswer" /></span><form:input type="password" name="userSecAnswer" id="userSecAnswer" path="userSecAnswer" maxlength="20"/></p>
            </c:if>
            <c:if test="${not empty changePasswordDto.otpRequired and changePasswordDto.otpRequired == 'true'}">
            	<p><span><spring:message code="otp" /></span><form:input type="text" name="userOtp" id="userOtp" path="userOtp" maxlength="4" onkeypress="return onlyNumeric(event)" /></p>
            </c:if>
            <p style="padding-top: 15px"><span>&nbsp;</span>
					<c:choose>
						<c:when test="${not empty changePasswordDto.beforeLogin and changePasswordDto.beforeLogin == 'true'}">		<!-- Below button is displayed if user is not logged in -->
							<input class="submit" type="button" id="beforeLoginChangePassword" name="beforeLoginChangePassword" style="width: 170px;" value="<spring:message code="change_password" />" />
						</c:when>
						<c:otherwise>
							<input class="submit" type="button" id="afterLoginChangePassword" name="afterLoginChangePassword" style="width: 170px;" value="<spring:message code="change_password" />" />
						</c:otherwise>
					</c:choose>
            	<input class="submit" type="reset" name="reset" id="reset" value="<spring:message code="reset" />" onclick="resetForm(changePasswordForm)" />
            	<input class="submit" type="button" name="close" id="close" value="<spring:message code="close" />" onclick="goToHomePage()" />
            </p>
     	</fieldset>
     </form:form>
     </div>
        
    <!-- This is the main body of screen where change Password form is displayed - End -->
      
    <!--  MAIN CONTENT/BODY : END-->
</body>