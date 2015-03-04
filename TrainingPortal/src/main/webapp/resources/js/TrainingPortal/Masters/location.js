/**
 * This file contains all the form validation related to location.jsp
 */

$().ready(function() {

	// validate locationForm form on keyup and submit
	$("#locationForm").validate({
		rules : {
			locationName : {
				required : true,
				minlength : 10,
				maxlength : 100
			}
		},
		messages : {
			locationName : {
				required : locationName_required,
				minlength : locationName_minlength,
				maxlength : locationName_maxlength
			}
		}
	});

	if (readOnly == 'true') {
		$("#locationForm :input").attr("disabled", true);
		$("#locationForm :input[type=submit]").removeAttr("disabled");
		$("#locationForm :input[type=button]").removeAttr("disabled");
		$("#locationForm :input[type=hidden]").removeAttr("disabled");
	}
});

function getAllLocations() {
	window.location.href = contextPathJs + "/location/allLocations";
}