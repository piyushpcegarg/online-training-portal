/**
 * This file contains all the form validation related to resetPassword.jsp
 */

$().ready(function() {
	
	$("#resetPasswordForm").validate({
		rules: {
			username : {
				required: true,
				minlength: 8,
				maxlength: 20
			}
		},
		messages: {
			username: 
			{
				required: username_required,
				minlength: username_minlength,
				maxlength: username_maxlength
			}
		}
	});
	
});
