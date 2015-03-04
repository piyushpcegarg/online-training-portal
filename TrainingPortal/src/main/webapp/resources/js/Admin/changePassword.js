/**
 * This file contains all the form validation related to changePassword.jsp
 */

$(document).ready(function() {

	$("#txtUserDoj").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : "dd/mm/yy",
		minDate : '-60Y',
		maxDate : '+0D',
	});

	$("#txtUserDob").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : "dd/mm/yy",
		minDate : '-60Y',
		maxDate : '-18Y',
	});

	/** Below action is fired when user is not logged in */
	$('#beforeLoginChangePassword').click(function() {
		/*
		 * $('#changePasswordForm').attr("action", contextPathJs +
		 * "/changePassword/save");
		 */
		$('#changePasswordForm').attr("method", "POST");
		$('#changePasswordForm').submit();
	});

	/** Below action is fired when user is logged in */
	$('#afterLoginChangePassword').click(function() {
		/*
		 * $('#changePasswordForm').attr("action", contextPathJs +
		 * "/passwordChange/save");
		 */
		$('#changePasswordForm').attr("method", "POST");
		$('#changePasswordForm').submit();
	});

	jQuery.validator.addMethod("notEqualTo", function(value, element, param) {
		var notEqual = true;
		value = $.trim(value);
		for (var i = 0; i < param.length; i++) {
			if (value == $.trim($(param[i]).val())) {
				notEqual = false;
			}
		}
		return this.optional(element) || notEqual;
	}, "Please enter a diferent value.");

	// validate changePassword form on keyup and submit
	$("#changePasswordForm").validate({
		rules : {
			username : {
				required : true,
				minlength : 8,
				maxlength : 30,
				pattern : /^[a-zA-Z0-9.@]*$/
			},
			oldPassword : {
				required : true,
				minlength : 8,
				maxlength : 20,
				notEqualTo : [ "#username" ],
				pwcheck : true
			},
			newPassword : {
				required : true,
				minlength : 8,
				maxlength : 20,
				notEqualTo : [ '#oldPassword', '#username' ],
				pwcheck : true
			},
			confirmNewPassword : {
				required : true,
				minlength : 8,
				maxlength : 20,
				equalTo : "#newPassword"
			},
			txtUserDoj : {
				required : true
			},
			txtUserDob : {
				required : true
			},
			userSecAnswer : {
				required : true,
				minlength : 3
			},
			userOtp : {
				required : true,
				minlength : 4
			}
		},
		messages : {
			username : {
				required : username_required,
				minlength : username_minlength,
				maxlength : username_maxlength,
				pattern : username_pattern
			},
			oldPassword : {
				required : oldPassword_required,
				minlength : oldPassword_minlength,
				maxlength : oldPassword_maxlength,
				notEqualTo : oldPassword_notEqualTo,
				pwcheck : oldPassword_pwcheck
			},
			newPassword : {
				required : newPassword_required,
				minlength : newPassword_minlength,
				maxlength : newPassword_maxlength,
				notEqualTo : newPassword_notEqualTo,
				pwcheck : newPassword_pwcheck
			},
			confirmNewPassword : {
				required : confirmNewPassword_required,
				minlength : confirmNewPassword_minlength,
				maxlength : confirmNewPassword_maxlength,
				equalTo : confirmNewPassword_equalTo
			},
			txtUserDoj : {
				required : userDoj_required
			},
			txtUserDob : {
				required : userDob_required
			},
			userSecAnswer : {
				required : userSecAnswer_required,
				minlength : userSecAnswer_minlength
			},
			userOtp : {
				required : userOtp_required,
				minlength : userOtp_minlength
			}
		}
	});

});
