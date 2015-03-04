/**
 * This file contains all the form validation related to changePassword.jsp
 */

$().ready(function() {
	
	$('#updateSetting').click(function(){
		
		 if($("input:checked").length == 0)
		 {
			 alert(required);
		 }
		 else
		 {
			 $('#securitySettingForm').attr("action", contextPathJs+"/securitySetting/update");
			 $('#securitySettingForm').attr("method", "POST");
			 $('#securitySettingForm').submit();
		 }
	});
});
