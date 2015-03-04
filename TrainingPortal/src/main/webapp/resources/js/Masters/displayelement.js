/**
 * This file contains all the form validation related to displayelement.jsp
 */


$(document).ready(function() {
	
	$('#updateElementPage').click(function(e){
		
		 if($("input:checked").length == 0)
		 {
			 alert(required);
			 e.preventDefault();
		 }
	});
	
	$('#viewElementPage').click(function(e){
		
		 if($("input:checked").length == 0)
		 {
			 alert(required);
			 e.preventDefault();
		 }
	});
	
});