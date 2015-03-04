/**
 * This file contains all the form validation related to user.jsp
 */

$().ready(function() {

	if (readOnly == 'true') {
		$("#trainingTraineeForm :input").attr("disabled", true);
		$("#trainingTraineeForm :input[type=submit]").removeAttr("disabled");
		$("#trainingTraineeForm :input[type=button]").removeAttr("disabled");
		$("#trainingTraineeForm :input[type=hidden]").removeAttr("disabled");
	}

});

function getAllTrainingsForTrainees() {
	window.location.href = contextPathJs + "/trainee/allTrainings";
}