/**
 * This file contains all the form validation related to displayrole.jsp
 */


$(document).ready(function() {
	
	$('#viewRoleElementPage').click(function(e){
		
		 if($("input:checked").length == 0)
		 {
			 alert(required);
			 e.preventDefault();
		 }
	});
	
	
	$('#updateRoleElementPage').click(function(e){
		
		 if($("input:checked").length == 0)
		 {
			 alert(required);
			 e.preventDefault();
		 }
	});
	
	
});