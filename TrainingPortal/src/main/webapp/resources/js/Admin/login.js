/**
 * This file contains all the form validation related to login.jsp
 */

$().ready(function() {

	$('#login').click(function() {
		$('#loginForm').attr("action", contextPathJs + "/authenticationCheck");
		$('#loginForm').attr("method", "POST");
		$('#loginForm').submit();
	});

	// validate login form on keyup and submit
	$("#loginForm").validate({
		rules : {
			username : {
				required : true,
				minlength : 8,
				pattern : /^[a-zA-Z0-9.@]*$/
			},
			password : {
				required : true,
				minlength : 8,
				pwcheck : true
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
				pattern : username_pattern
			},
			password : {
				required : password_required,
				minlength : password_minlength,
				pwcheck : password_pwcheck
			},
			userOtp : {
				required : userOtp_required,
				minlength : userOtp_minlength
			}
		}
	});

});