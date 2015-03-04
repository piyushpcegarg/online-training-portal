/**
 * This file contains all the form validation related to forgotPassword.jsp
 */

$(document).ready(
		function() {

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

			$('#search').click(
					function() {
						$('#searchUserForm').attr("action",
								contextPathJs + "/forgotPassword/search");
						$('#searchUserForm').attr("method", "POST");
						$('#searchUserForm').submit();
					});

			$('#forgotPassword').click(
					function() {
						$('#forgotPasswordForm').attr("action",
								contextPathJs + "/forgotPassword/save");
						$('#forgotPasswordForm').attr("method", "POST");
						$('#forgotPasswordForm').submit();
					});

			jQuery.validator.addMethod("notEqualTo", function(value, element,
					param) {
				var notEqual = true;
				value = $.trim(value);
				for (var i = 0; i < param.length; i++) {
					if (value == $.trim($(param[i]).val())) {
						notEqual = false;
					}
				}
				return this.optional(element) || notEqual;
			}, "Please enter a diferent value.");

			$("#searchUserForm").validate({
				rules : {
					searchUsername : {
						required : true,
						minlength : 8,
						maxlength : 30,
						pattern : /^[a-zA-Z0-9.@]*$/
					}
				},
				messages : {
					searchUsername : {
						required : username_required,
						minlength : username_minlength,
						maxlength : username_maxlength,
						pattern : username_pattern
					}
				}
			});

			// validate forgotPassword form on keyup and submit
			$("#forgotPasswordForm").validate({
				rules : {
					username : {
						required : true,
						minlength : 8,
						maxlength : 30,
						pattern : /^[a-zA-Z0-9.@]*$/
					},
					newPassword : {
						required : true,
						minlength : 8,
						maxlength : 20,
						notEqualTo : [ '#username' ],
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

// function to check username availability
function checkAvailability() {
	var username = $("#searchUsername").val();
	if (username != '') {
		$.ajax({
			url : contextPathJs + "/forgotPassword/checkUsernameAvailability",
			data : 'username=' + username,
			type : "POST",
			success : function(result) {
				// if the result is 1
				if (result != 1) {
					// show that the username is available
					$("#username_availability_result").addClass("success");
					$("#username_availability_result").html(
							username + available);
				} else {
					// show that the username is NOT available
					$("#username_availability_result").addClass("error");
					$("#username_availability_result").html(
							username + notAvailable);
					$("#searchUsername").val("");
					$("#searchUsername").focus();
				}
			}
		});
	}
}
