/**
 * This file contains all the form validation related to displayrole.jsp
 */


$(document).ready(function() {
	
	$('#updateRoleElement').click(function(e){
		
		 if($("input:checked").length == 0)
		 {
			 alert(required);
			 e.preventDefault();
		 }
	});
	
	if(readOnly == 'true')
	{
		$("#roleElementForm :input").attr("disabled", true);
		$("#roleElementForm :input[type=submit]").removeAttr("disabled");
		$("#roleElementForm :input[type=button]").removeAttr("disabled");
		$("#roleElementForm :input[type=hidden]").removeAttr("disabled");
	}
	
});


function getAllRoleElements()
{
	window.location.href= contextPathJs+"/roleElement/allRoleElements";
}