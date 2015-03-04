/**
 * This file contains all the form validation related to displayskill.jsp
 */


$(document).ready(function() {
	
	$('#updateSkillPage').click(function(e){
		
		 if($("input:checked").length == 0)
		 {
			 alert(required);
			 e.preventDefault();
		 }
	});
	
	$('#viewSkillPage').click(function(e){
		
		 if($("input:checked").length == 0)
		 {
			 alert(required);
			 e.preventDefault();
		 }
	});
	
});