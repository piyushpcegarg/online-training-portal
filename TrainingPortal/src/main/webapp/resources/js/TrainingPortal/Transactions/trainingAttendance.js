/**
 * This file contains all the form validation related to user.jsp
 */

$().ready(function() {

	if (readOnly == 'true') {
		$("#trainingAttendanceForm :input").attr("disabled", true);
		$("#trainingAttendanceForm :input[type=submit]").removeAttr("disabled");
		$("#trainingAttendanceForm :input[type=button]").removeAttr("disabled");
		$("#trainingAttendanceForm :input[type=hidden]").removeAttr("disabled");
	}

});

function getAllTrainingsForAttendance() {
	window.location.href = contextPathJs + "/attendance/allTrainings";
}