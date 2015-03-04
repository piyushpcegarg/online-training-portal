<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp" %>

<script type="text/javascript">
var readOnly = "${readOnly}";
var action = "${action}";
</script>

<spring:url var="formAction" value="/myProfile" ></spring:url>

<body>
    <!-- This is the main body of screen where User form is displayed - Start -->
     <div class="form_settings">
     <form:form name='myProfileForm' id='myProfileForm' commandName="userDto" enctype="multipart/form-data" action="${formAction}" method="post">
     <fieldset>
  		<legend><b><spring:message code="user" /><spring:message code="form" /></b></legend>
  			<form:errors path="*" cssClass="errorblock" element="div" />
  			<c:if test="${not empty errorMessages}">
				<div class="errorblock"><spring:message code="${errorMessages}" /></div>
			</c:if>
  			<c:if test="${not empty successMsg}">
        		<div class="successblock"><spring:message code="${successMsg}" /></div>
    		</c:if>
    		<!-- Set all security settings and roles in hidden field - start -->
    		<form:hidden path="userId"/>
    		<c:forEach items="${userDto.lstSecurityQuestionsLookUp}" varStatus="vs">
		        <form:hidden path="lstSecurityQuestionsLookUp[${vs.index}].lookupCode" />
		        <form:hidden path="lstSecurityQuestionsLookUp[${vs.index}].lookupDesc" />
    		</c:forEach>
    		<c:forEach items="${userDto.lstRoles}" varStatus="vs">
		        <form:hidden path="lstRoles[${vs.index}].roleCode" />
		        <form:hidden path="lstRoles[${vs.index}].roleName" />
    		</c:forEach>
  			<!-- Set all security settings and roles in hidden field - End -->
  			<!-- Button that triggers the popup -->
  			<p><span class="mandatory"><spring:message code="username" /></span><form:input type="text" name="userName" id="userName" path="userName" maxlength="30" onchange="checkAvailability()"  /> <label id="username_availability_result"></label></p>
            <p><span class="mandatory"><spring:message code="first" /><spring:message code="engName" /></span><form:input type="text" name="engEmpFname" id="engEmpFname" path="engEmpFname" maxlength="30" onblur="languageConversion(engEmpFname , hinEmpFname)" onkeypress="return onlyAlphabets(event)" /></p>
            <p><span class="mandatory"><spring:message code="first" /><spring:message code="hinName" /></span><form:input type="text" name="hinEmpFname" id="hinEmpFname" path="hinEmpFname" maxlength="30" />
            	<img id="hindiImage" src="resources/images/help.png" alt="help" height="15px" width="15px" onclick="getHelpImageFile();"/>
            </p>
            <p><span><spring:message code="middle" /><spring:message code="engName" /></span><form:input type="text" name="engEmpMname" id="engEmpMname" path="engEmpMname" maxlength="30" onblur="languageConversion(engEmpMname , hinEmpMname)" onkeypress="return onlyAlphabets(event)" /></p>
            <p><span><spring:message code="middle" /><spring:message code="hinName" /></span><form:input type="text" name="hinEmpMname" id="hinEmpMname" path="hinEmpMname" maxlength="30" />
            	<img id="hindiImage" src="resources/images/help.png" alt="help" height="15px" width="15px" onclick="getHelpImageFile();"/>
            </p>
            <p><span class="mandatory"><spring:message code="last" /><spring:message code="engName" /></span><form:input type="text" name="engEmpLname" id="engEmpLname" path="engEmpLname" maxlength="30" onblur="languageConversion(engEmpLname , hinEmpLname)" onkeypress="return onlyAlphabets(event)" /></p>
            <p><span class="mandatory"><spring:message code="last" /><spring:message code="hinName" /></span><form:input type="text" name="hinEmpLname" id="hinEmpLname" path="hinEmpLname" maxlength="30" />
            	<img id="hindiImage" src="resources/images/help.png" alt="help" height="15px" width="15px" onclick="getHelpImageFile();"/>
            </p>
            <p><span class="mandatory"><spring:message code="gender" /></span>
            	<spring:message code="male" /><form:radiobutton name="empGender" id="empGender" path="empGender" value="M" /> 
				<spring:message code="female" /><form:radiobutton name="empGender" id="empGender" path="empGender" value="F" />
         	</p>
            <p><span class="mandatory"><spring:message code="doj" /></span><form:input type="date" name="empDoj" id="empDoj" path="empDoj" maxlength="10"  /></p>
            <p><span class="mandatory"><spring:message code="dob" /></span><form:input type="date" name="empDob" id="empDob" path="empDob" maxlength="10"  /></p>
            <p><span class="mandatory"><spring:message code="email" /></span><form:input type="text" name="email" id="email" path="email" maxlength="40"  /></p>
            <p><span class="mandatory"><spring:message code="mobile" /></span><form:input type="text" name="regMobileNo" id="regMobileNo" path="regMobileNo" maxlength="10" onkeypress="return onlyNumeric(event)"  onchange="checkMobileAvailability()" /><label id="mobile_availability_result"></label></p>
            <c:if test="${not empty action and action != 'myProfile'}">
            <p><span class="mandatory"><spring:message code="otpEnabled" /></span>
            	<spring:message code="yes" /><form:radiobutton name="otpEnabled" id="otpEnabled" path="otpEnabled" value="true" /> 
				<spring:message code="no" /><form:radiobutton name="otpEnabled" id="otpEnabled" path="otpEnabled" value="false" />
         	</p>
         	</c:if>
            <p><span class="mandatory"><spring:message code="secQuestion" /></span>
            	<form:select id="secretQueCode" path="secretQueCode" items="${userDto.lstSecurityQuestionsLookUp}" itemValue="lookupCode" itemLabel="lookupDesc" /> 
			</p>
            <p><span class="mandatory"><spring:message code="secAnswer" /></span><form:input type="password" name="secretAnswer" id="secretAnswer" path="secretAnswer" maxlength="20"/></p>
            <c:if test="${not empty action and action != 'view'}">
				<p><span class="mandatory"><spring:message code="selectImage" /></span><input type="file" name="userImage" id="userImage" /></p>
			</c:if>
			<c:if test="${not empty action and action != 'create'}">
				<form:hidden path="encodedImageString"/>
				<p><span><spring:message code="userImage" /></span><img src="data:image/jpeg;base64,${userDto.encodedImageString}" alt="user_image" width="100" height="100" style="padding: 10px;" /></p>
			</c:if>
			<c:if test="${not empty action and not empty userDto.lstRoles and action != 'update' and action != 'myProfile'}">
				<p><span class="mandatory"><spring:message code="role" /><spring:message code="name" /></span>
		            <form:checkboxes id="lstRoleCodes" name="lstRoleCodes" path="lstRoleCodes" items="${userDto.lstRoles}" itemValue="roleCode" itemLabel="roleName"  />
				</p>
			</c:if>
			<br /><br />
            <c:if test="${not empty action and (action == 'view' or action == 'update')}">
            <p><span><spring:message code="user" /><spring:message code="status" /></span>
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
            			<form:button class="submit" id="saveUser" name="saveUser" ><spring:message code="save" /></form:button>
					</c:if>
					<c:if test="${not empty action and action == 'update'}">
						<form:button class="submit" id="updateUser" name="updateUser" ><spring:message code="update" /></form:button>
					</c:if>
            		<input class="submit" type="reset" name="reset" id="reset" value="<spring:message code="reset" />" onclick="resetForm(userForm)" />
            	</c:if>
            				
            	<!-- In case of update , after clicking on close button it should redirect to home page , because it is also using in my profile page . -->
            	<!-- For My profile Page section - start -->
            	<c:if test="${not empty action and action == 'myProfile'}">
            		<c:if test="${readOnly == 'false'}">
            			<form:button class="submit" id="updateMyProfile" name="updateMyProfile" ><spring:message code="update" /></form:button>
            		</c:if>
            		<input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="goToHomePage()" />
            	</c:if>
            	<!-- For My profile Page section - end -->
            	<c:if test="${not empty action and action != 'myProfile'}">
	            	<input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="getAllUsers()" />
            	</c:if>
            </p>
            
	</form:form>
    </div>
        
    <!-- This is the main body of screen where User form is displayed - End -->
      <div id="hindiHelpImage" ><img alt="helpImage" src="resources/images/pramukhindic-hindi.png"></div>	<!-- Hindi Typing Help Image -->
    <!--  MAIN CONTENT/BODY : END-->
</body>
</html>