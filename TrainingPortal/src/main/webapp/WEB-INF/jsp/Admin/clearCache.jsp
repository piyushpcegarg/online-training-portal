<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp" %>

<spring:url var="formAction" value="/clearCache/clear" ></spring:url>

<body>

    <!-- This is the main body of screen where reset Password form is displayed - Start -->
     <div class="form_settings">
     <form:form name='clearCacheForm' id='clearCacheForm' action="${formAction}" method="post" commandName="clearCache">
     <fieldset>
  		<legend><b><spring:message code="clear_cache" /><spring:message code="form" /></b></legend>
			<c:if test="${not empty clearCacheSuccessMessage}">
        		<div class="successblock"><spring:message code="clearCacheSuccessMessage" /></div>
    		</c:if>
    		<br />
    		<label style="color: #ff0000;"><b><spring:message code="clearCacheWarningMessage" /></b></label>
            <p style="padding-top: 15px"><span>&nbsp;</span>
            	<form:button class="submit" id="clearCache" name="clearCache" ><spring:message code="clear_cache" /></form:button>
            	<input class="submit" type="button" name="close" id="close" value="<spring:message code="close" />" onclick="goToHomePage()" />
            </p>
     	</fieldset>
     </form:form>
     </div>
        
    <!-- This is the main body of screen where reset Password form is displayed - End -->
      
    <!--  MAIN CONTENT/BODY : END-->
</body>
</html>