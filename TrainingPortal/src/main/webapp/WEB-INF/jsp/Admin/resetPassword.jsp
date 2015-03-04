<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp" %>

<spring:url var="formAction" value="/resetPassword/reset" ></spring:url>

<body onload='document.resetPasswordForm.username.focus();'>

    <!-- This is the main body of screen where reset Password form is displayed - Start -->
     <div class="form_settings">
     <form:form name='resetPasswordForm' id='resetPasswordForm' action="${formAction}" method="post" commandName="userDto">
     <fieldset>
  		<legend><b><spring:message code="reset_password" /><spring:message code="form" /></b></legend>
  			<c:if test="${not empty errorMessages}">
				<div class="errorblock"><spring:message code="${errorMessages}" /></div>
			</c:if>
			<c:if test="${not empty resetPasswordSuccessMessage}">
        		<div class="successblock"><spring:message code="resetPasswordSuccessMessage" /> ${username}</div>
    		</c:if>
            <p><span><spring:message code="username" /></span><form:input type="text" path="userName" id="userName" maxlength="20" onkeypress="return onlyAlphaNumeric(event)" ></form:input></p>
            <p style="padding-top: 15px"><span>&nbsp;</span>
            	<form:button class="submit" id="resetPassword" name="resetPassword" ><spring:message code="reset_password" /></form:button>
            	<input class="submit" type="button" name="close" id="close" value="<spring:message code="close" />" onclick="goToHomePage()" />
            </p>
     	</fieldset>
     </form:form>
     </div>
        
    <!-- This is the main body of screen where reset Password form is displayed - End -->
      
    <!--  MAIN CONTENT/BODY : END-->
</body>
</html>