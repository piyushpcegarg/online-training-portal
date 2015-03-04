/**
 * This file contains all the form validation related to skill.jsp
 */

$().ready(function() {

	// validate skillForm form on keyup and submit
	$("#skillForm").validate({
		rules : {
			skillName : {
				required : true,
				minlength : 3,
				maxlength : 30
			}
		},
		messages : {
			skillName : {
				required : skillName_required,
				minlength : skillName_minlength,
				maxlength : skillName_maxlength
			}
		}
	});

	if (readOnly == 'true') {
		$("#skillForm :input").attr("disabled", true);
		$("#skillForm :input[type=submit]").removeAttr("disabled");
		$("#skillForm :input[type=button]").removeAttr("disabled");
		$("#skillForm :input[type=hidden]").removeAttr("disabled");
	}
});

function getAllSkills() {
	window.location.href = contextPathJs + "/skill/allSkills";
}