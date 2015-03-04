<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp" %>

<body onload='document.loginForm.username.focus();'>
    <!-- This is the main body of screen where login form is displayed - Start -->
    <c:if test="${not empty error}">
		<div class="errorblock">
			<spring:message code="login_failed_heading" /><br /> <spring:message code="caused" />
			 ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}  
		</div>
	</c:if>
	<c:if test="${not empty successLogoutMessage}">
        <div class="successblock">
        	<spring:message code="successLogoutMessage" />
        </div>
    </c:if>
    <c:if test="${not empty changePasswordSuccessMsg}">
        <div class="successblock">
        	<spring:message code="changePasswordSuccessMsg" />
        </div>
    </c:if>
    
    
    <!-- Set UserName and password if user is otp enabled - Start -->
    <c:set var="userNameOnJsp" value=""/>
    <c:set var="passwordOnJsp" value=""/>
    <c:if test="${username != null}"><c:set var="userNameOnJsp" value="${username}"/></c:if>
    <c:if test="${password != null}"><c:set var="passwordOnJsp" value="${password}"/></c:if>
    <!-- Set UserName and password if user is otp enabled - End -->
    
	
     <div class="form_settings" >
     <form name='loginForm' id='loginForm' >
     <fieldset>
     	<legend><b><spring:message code="login" /><spring:message code="form" /></b></legend>
  			<br />
     
            <p><span><spring:message code="username"/></span><input type="text" name="username" id="username" value="${userNameOnJsp}" maxlength="30"  /></p>
            <p><span><spring:message code="password"/></span><input type="password" name="password" id="password" value="${passwordOnJsp}" maxlength="20" /></p>
            <c:if test="${not empty OTP_REQUIRED and OTP_REQUIRED == 'true'}">
            	<p><span><spring:message code="otp"/></span><input type="number" name="userOtp" id="userOtp" maxlength="4" onkeypress="return onlyNumeric(event)" /></p>
            </c:if>
            <p><span><spring:message code="language"/></span>
            	<spring:message code="english"/><input type="radio" name="locale" value="en" checked="checked">
            	<spring:message code="hindi"/><input type="radio" name="locale" value="hi" >
            	<a href="${contextPath}/forgotPassword"><spring:message code="forgot_password"/></a>
            </p>  
            <p style="padding-top: 15px"><span>&nbsp;</span>
            <input class="submit" type="button" name="login" id="login" value="<spring:message code="login" />" />
            <input class="submit" type="reset" name="reset" id="reset" value="<spring:message code="reset" />" /></p>
     </fieldset>
     </form>
     </div>
        
    <!-- This is the main body of screen where login form is displayed - End -->
      
    <!--  MAIN CONTENT/BODY : END-->
</body>
