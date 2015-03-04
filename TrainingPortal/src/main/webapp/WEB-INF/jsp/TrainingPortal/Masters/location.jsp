<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp" %>

<script type="text/javascript">
var readOnly = "${readOnly}";
</script>

<spring:url var="formAction" value="/location" ></spring:url>

<body>

    <!-- This is the main body of screen where Location form is displayed - Start -->
     <div class="form_settings">
     <form:form name='locationForm' id='locationForm' commandName="locationDto" action="${formAction}" method="post">
     <fieldset>
  		<legend><b><spring:message code="location" /><spring:message code="form" /></b></legend>
  			<form:errors path="*" cssClass="errorblock" element="div" />
  			<c:if test="${not empty errorMessages}">
				<div class="errorblock"><spring:message code="${errorMessages}" /></div>
			</c:if>
  			<c:if test="${not empty successMsg}">
        		<div class="successblock"><spring:message code="${successMsg}" /></div>
    		</c:if>
  			
  			<form:hidden path="locationCode"/>
            <p><span class="mandatory"><spring:message code="location" /><spring:message code="name" /></span><form:input type="text" name="locationName" id="locationName" path="locationName" maxlength="100" /></p>
            <c:if test="${not empty action and action != 'create'}">
            <p><span><spring:message code="location" /><spring:message code="status" /></span>
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
            			<form:button class="submit" id="saveLocation" name="saveLocation" ><spring:message code="save" /></form:button>
					</c:if>
					<c:if test="${not empty action and action == 'update'}">
						<form:button class="submit" id="updateLocation" name="updateLocation" ><spring:message code="update" /></form:button>
					</c:if>
            		<input class="submit" type="reset" name="reset" id="reset" value="<spring:message code="reset" />" onclick="resetForm(locationForm)" />
            	</c:if>			
            	<input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="getAllLocations()" />
            </p>
	</form:form>
     </div>
        
    <!-- This is the main body of screen where Location form is displayed - End -->
      
    <!--  MAIN CONTENT/BODY : END-->
</body>
</html>