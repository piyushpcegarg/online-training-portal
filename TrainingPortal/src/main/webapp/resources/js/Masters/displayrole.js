/**
 * This file contains all the form validation related to displayrole.jsp
 */


$(document).ready(function() {
	
	$('#updateRolePage').click(function(e){
		 if($("input:checked").length == 0)
		 {
			 alert(required);
			 e.preventDefault();
		 }
	});
	
	$('#viewRolePage').click(function(e){
		
		 if($("input:checked").length == 0)
		 {
			 alert(required);
			 e.preventDefault();
		 }
	});
	
});