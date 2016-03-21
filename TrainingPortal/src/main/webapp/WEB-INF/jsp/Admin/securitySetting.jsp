<%@ include file="/WEB-INF/jsp/Layout/taglibs.jsp"%>

<spring:url var="formAction" value="/securitySetting/update" ></spring:url>

	<form:form name='securitySettingForm' id='securitySettingForm' method="post" action="${formAction}" commandName="cmnLookupMstDto" >
     <fieldset>
  		<legend><b><spring:message code="securitySetting" /><spring:message code="form" /></b></legend>
  		
  		<c:if test="${not empty settingSuccessMessage}">
        	<div class="successblock"><spring:message code="settingSuccessMessage" /></div>
   		</c:if>
   		<c:if test="${not empty settingErrorMessage}">
			<div class="errorblock"><spring:message code="settingErrorMessage" /></div>
		</c:if>
  		
        <display:table id="cmnLookupMst" name="lstSecuritySettings"
                    requestURI="${contextPath}/securitySetting" pagesize="10" style="width:100%;" >
                    
            <display:column>
				<input type="checkbox" name="chkbox" value="${cmnLookupMst.lookupCode}~${cmnLookupMst.activateFlag}" />
			</display:column>
            <display:column property="lookupName" titleKey="name"  />
            <display:column property="lookupShortName" titleKey="shortName"  />
            <display:column property="lookupDesc" titleKey="desc"  />
            <display:column titleKey="status" >
            	<select name="lookupCode_${cmnLookupMst.lookupCode}">
            		<option value="true"<c:if test="${cmnLookupMst.activateFlag == 'true'}"> selected</c:if>><spring:message code="active" /></option>
            		<option value="false"<c:if test="${cmnLookupMst.activateFlag == 'false'}"> selected</c:if>><spring:message code="inactive" /></option>
         		</select>
            </display:column>
        </display:table>

	<div class="form_settings">
		<p style="padding-top: 15px"><span>&nbsp;</span>
		<input class="submit" type="button" id="updateSetting" name="updateSetting" value="<spring:message code="updateSetting" />" />
		<input class="submit" type="button" name="close" value="<spring:message code="close" />" onclick="goToHomePage()" /></p>
	</div>
	</fieldset>
	</form:form>
