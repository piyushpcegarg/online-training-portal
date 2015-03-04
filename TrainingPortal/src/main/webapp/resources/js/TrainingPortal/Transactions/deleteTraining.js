/**
 * This file contains all the form validation related to displayuser.jsp
 */


$(document).ready(function() {
	
	$('#deleteTrainee').click(function(e){
		
		 if($("input:checked").length == 0)
		 {
			 alert(required);
			 e.preventDefault();
		 }
	});
	
});

function getAllTrainingsForTrainees() {
	window.location.href = contextPathJs + "/trainee/allTrainings";
}