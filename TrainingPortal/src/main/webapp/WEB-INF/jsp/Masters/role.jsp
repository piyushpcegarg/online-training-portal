<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp" %>

<script type="text/javascript">
var readOnly = "${readOnly}";
</script>

<spring:url var="formAction" value="/role" ></spring:url>

<body>

    <!-- This is the main body of screen where Role form is displayed - Start -->
     <div class="form_settings">
     <form:form name='roleForm' id='roleForm' commandName="roleDto" action="${formAction}" method="post">
     <fieldset>
  		<legend><b><spring:message code="role" /><spring:message code="form" /></b></legend>
  			<form:errors path="*" cssClass="errorblock" element="div" />
  			<c:if test="${not empty errorMessages}">
				<div class="errorblock"><spring:message code="${errorMessages}" /></div>
			</c:if>
  			<c:if test="${not empty successMsg}">
        		<div class="successblock"><spring:message code="${successMsg}" /></div>
    		</c:if>
  			
  			<form:hidden path="roleCode"/>
            <p><span class="mandatory"><spring:message code="role" /><spring:message code="engName" /></span><form:input type="text" name="engRoleName" id="engRoleName" path="engRoleName" maxlength="120" onblur="languageConversion(engRoleName , hinRoleName)" onkeypress="return onlyAlphabets(event)" /></p>
            <p><span class="mandatory"><spring:message code="role" /><spring:message code="hinName" /></span><form:input type="text" name="hinRoleName" id="hinRoleName" path="hinRoleName" maxlength="120" />
            	<img id="hindiImage" src="resources/images/help.png" alt="help" height="15px" width="15px" onclick="getHelpImageFile();"/>
            </p>
            <p><span class="mandatory"><spring:message code="role" /><spring:message code="engShortName" /></span><form:input type="text" name="engRoleShortName" id="engRoleShortName" path="engRoleShortName" maxlength="60"  onblur="languageConversion(engRoleShortName , hinRoleShortName)" onkeypress="return onlyAlphabets(event)" /></p>
            <p><span class="mandatory"><spring:message code="role" /><spring:message code="hinShortName" /></span><form:input type="text" name="hinRoleShortName" id="hinRoleShortName" path="hinRoleShortName" maxlength="60" />
            	<img id="hindiImage" src="resources/images/help.png" alt="help" height="15px" width="15px" onclick="getHelpImageFile();"/>
            </p>
            <p><span class="mandatory"><spring:message code="role" /><spring:message code="engDesc" /></span><form:input type="text" name="engRoleDesc" id="engRoleDesc" path="engRoleDesc" maxlength="200"  onblur="languageConversion(engRoleDesc , hinRoleDesc)" onkeypress="return onlyAlphabets(event)" /></p>
            <p><span class="mandatory"><spring:message code="role" /><spring:message code="hinDesc" /></span><form:input type="text" name="hinRoleDesc" id="hinRoleDesc" path="hinRoleDesc" maxlength="200" />
            	<img id="hindiImage" src="resources/images/help.png" alt="help" height="15px" width="15px" onclick="getHelpImageFile();"/>
            </p>
            <c:if test="${not empty action and action != 'create'}">
            <p><span><spring:message code="role" /><spring:message code="status" /></span>
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
           			<form:button class="submit" id="saveRole" name="saveRole" ><spring:message code="save" /></form:button>
				</c:if>
				<c:if test="${not empty action and action == 'update'}">
					<form:button class="submit" id="updateRole" name="updateRole" ><spring:message code="update" /></form:button>
				</c:if>
           		<input class="submit" type="reset" name="reset" id="reset" value="<spring:message code="reset" />" onclick="resetForm(roleForm)" />
           	</c:if>			
           	<input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="getAllRoles()" />
         </p>
            
     </form:form>
     </div>
        
    <!-- This is the main body of screen where Role form is displayed - End -->
      
      <div id="hindiHelpImage" ><img alt="helpImage" src="resources/images/pramukhindic-hindi.png" ></div>	<!-- Hindi Typing Help Image -->
    <!--  MAIN CONTENT/BODY : END-->
</body>
</html>