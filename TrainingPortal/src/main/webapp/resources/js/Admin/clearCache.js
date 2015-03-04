/**
 * This file contains all the form validation related to displayelement.jsp
 */


$(document).ready(function() {
	
	$('#clearCache').click(function(e){
		
		var result = confirm("Are you sure to clear the cache of application !");
		if (result == false) {
			e.preventDefault();
		}
	});
	
});