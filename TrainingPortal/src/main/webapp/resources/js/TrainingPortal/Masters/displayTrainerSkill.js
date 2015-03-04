/**
 * This file contains all the form validation related to displayrole.jsp
 */


$(document).ready(function() {
	
	$('#viewTrainerSkillPage').click(function(e){
		
		 if($("input:checked").length == 0)
		 {
			 alert(required);
			 e.preventDefault();
		 }
	});
	
	
	$('#updateTrainerSkillPage').click(function(e){
		
		 if($("input:checked").length == 0)
		 {
			 alert(required);
			 e.preventDefault();
		 }
	});
	
	
});