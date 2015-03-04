/**
 * This file contains all the form validation related to displayuser.jsp
 */


$(document).ready(function() {
	
	$('#updateTrainingPage').click(function(e){
		
		 if($("input:checked").length == 0)
		 {
			 alert(required);
			 e.preventDefault();
		 }
	});
	
	$('#viewTrainingPage').click(function(e){
		
		 if($("input:checked").length == 0)
		 {
			 alert(required);
			 e.preventDefault();
		 }
	});
	
});