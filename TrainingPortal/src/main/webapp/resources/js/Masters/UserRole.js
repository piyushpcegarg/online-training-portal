/**
 * This file contains all the form validation related to displayrole.jsp
 */


$(document).ready(function() {
	
	$('#updateUserRole').click(function(e){
		
		 if($("input:checked").length == 0)
		 {
			 alert(required);
			 e.preventDefault();
		 }
	});
	
	if(readOnly == 'true')
	{
		$("#userRoleForm :input").attr("disabled", true);
		$("#userRoleForm :input[type=submit]").removeAttr("disabled");
		$("#userRoleForm :input[type=button]").removeAttr("disabled");
		$("#userRoleForm :input[type=hidden]").removeAttr("disabled");
	}
	
});


function getAllUserRoles()
{
	window.location.href= contextPathJs+"/userRole/allUserRoles";
}