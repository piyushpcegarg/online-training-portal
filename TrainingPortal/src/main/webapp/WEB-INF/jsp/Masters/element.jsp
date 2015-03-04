<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp" %>

<script type="text/javascript">
var readOnly = "${readOnly}";
</script>

<spring:url var="formAction" value="/element"></spring:url>

<body>

    <!-- This is the main body of screen where Element form is displayed - Start -->
     <div class="form_settings">
     <form:form name='elementForm' id='elementForm' commandName="elementDto" action="${formAction}" method="post">
     <fieldset>
  		<legend><b><spring:message code="element" /><spring:message code="form" /></b></legend>
  			<form:errors path="*" cssClass="errorblock" element="div" />
  			<c:if test="${not empty errorMessages}">
				<div class="errorblock"><spring:message code="${errorMessages}" /></div>
			</c:if>
  			<c:if test="${not empty successMsg}">
        		<div class="successblock"><spring:message code="${successMsg}" /></div>
    		</c:if>
  			
  			<!-- Set all Elements and elementCode in hidden field - start -->
    		<form:hidden path="elementCode"/>
    		<c:forEach items="${elementDto.lstOrgElementMstDto}" varStatus="vs">
		        <form:hidden path="lstOrgElementMstDto[${vs.index}].elementCode" />
		        <form:hidden path="lstOrgElementMstDto[${vs.index}].elementName" />
    		</c:forEach>
  			<!-- Set all Elements and elementCode in hidden field - End -->
  			
            <p><span class="mandatory"><spring:message code="element" /><spring:message code="engName" /></span><form:input type="text" name="engElementName" id="engElementName" path="engElementName" maxlength="200" onblur="languageConversion(engElementName , hinElementName)" onkeypress="return onlyAlphabets(event)" /></p>
            <p><span class="mandatory"><spring:message code="element" /><spring:message code="hinName" /></span><form:input type="text" name="hinElementName" id="hinElementName" path="hinElementName" maxlength="200" />
            	<img id="hindiImage" src="resources/images/help.png" alt="help" height="15px" width="15px" onclick="getHelpImageFile();"/>
            </p>
            <p><span class="mandatory"><spring:message code="element" /><spring:message code="engDesc" /></span><form:input type="text" name="engElementDesc" id="engElementDesc" path="engElementDesc" maxlength="200"  onblur="languageConversion(engElementDesc , hinElementDesc)" onkeypress="return onlyAlphabets(event)" /></p>
            <p><span class="mandatory"><spring:message code="element" /><spring:message code="hinDesc" /></span><form:input type="text" name="hinElementDesc" id="hinElementDesc" path="hinElementDesc" maxlength="200" />
            	<img id="hindiImage" src="resources/images/help.png" alt="help" height="15px" width="15px" onclick="getHelpImageFile();"/>
            </p>
            <p><span class="mandatory"><spring:message code="element" /><spring:message code="engToolTip" /></span><form:input type="text" name="engElementToolTip" id="engElementToolTip" path="engElementToolTip" maxlength="200"  onblur="languageConversion(engElementToolTip , hinElementToolTip)" onkeypress="return onlyAlphabets(event)" /></p>
            <p><span class="mandatory"><spring:message code="element" /><spring:message code="hinToolTip" /></span><form:input type="text" name="hinElementToolTip" id="hinElementToolTip" path="hinElementToolTip" maxlength="200" />
            	<img id="hindiImage" src="resources/images/help.png" alt="help" height="15px" width="15px" onclick="getHelpImageFile();"/>
            </p>
            <p><span class="mandatory"><spring:message code="elementType" /></span>
            	<spring:message code="module" /><form:radiobutton name="elementType" id="elementType" path="elementType" value="6" /> 
				<spring:message code="screen" /><form:radiobutton name="elementType" id="elementType" path="elementType" value="7" />
         	</p>
         	<p><span class="mandatory"><spring:message code="editable" /></span>
            	<spring:message code="yes" /><form:radiobutton name="editable" id="editable" path="editable" value="true" /> 
				<spring:message code="no" /><form:radiobutton name="editable" id="editable" path="editable" value="false" />
         	</p>
         	<p><span class="mandatory"><spring:message code="elementUrl" /></span><form:input type="text" name="elementUrl" id="elementUrl" path="elementUrl" maxlength="250" /></p>
         	<p><span class="mandatory"><spring:message code="elementOrder" /></span><form:input type="text" name="elementOrder" id="elementOrder" path="elementOrder" maxlength="4" onkeypress="return onlyNumeric(event)" /></p>
         	<p><span><spring:message code="parent" /><spring:message code="element" /></span>
            	<form:select id="elementParentCode" path="elementParentCode"  >
            		<form:option value="-1" label="--- No Parent Element ---"/>
   					<form:options items="${elementDto.lstOrgElementMstDto}" itemValue="elementCode" itemLabel="elementName" />
   				</form:select> 
			</p>
            <c:if test="${not empty action and action != 'create'}">
            <p><span><spring:message code="element" /><spring:message code="status" /></span>
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
            			<form:button class="submit" id="saveElement" name="saveElement" ><spring:message code="save" /></form:button>
					</c:if>
					<c:if test="${not empty action and action == 'update'}">
						<form:button class="submit" id="updateElement" name="updateElement" ><spring:message code="update" /></form:button>
					</c:if>
            		<input class="submit" type="reset" name="reset" id="reset" value="<spring:message code="reset" />" onclick="resetForm(elementForm)" />
            	</c:if>			
            	<input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="getAllElements()" />
            </p>
	</form:form>
     </div>
        
    <!-- This is the main body of screen where Element form is displayed - End -->
      <div id="hindiHelpImage" ><img alt="helpImage" src="resources/images/pramukhindic-hindi.png"></div>	<!-- Hindi Typing Help Image -->
    <!--  MAIN CONTENT/BODY : END-->
</body>
</html>