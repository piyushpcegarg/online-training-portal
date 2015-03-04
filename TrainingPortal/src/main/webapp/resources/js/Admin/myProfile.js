/**
 * This file contains all the form validation related to user.jsp
 */

$().ready(function() {

	pramukhIME.enable('hinEmpFname');
	pramukhIME.enable('hinEmpMname');
	pramukhIME.enable('hinEmpLname');

	$('#hindiHelpImage').hide();

	$("#empDoj").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : "dd/mm/yy",
		minDate : '-60Y',
		maxDate : '+0D',
	});

	$("#empDob").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : "dd/mm/yy",
		minDate : '-60Y',
		maxDate : '-18Y',
	});

	// validate userForm form on keyup and submit
	$("#myProfileForm1").validate({
		rules : {
			username : {
				required : true,
				minlength : 8,
				maxlength : 30,
				pattern : /^[a-zA-Z0-9.@]*$/
			},
			engEmpFname : {
				required : true,
				minlength : 3,
				maxlength : 30
			},
			hinEmpFname : {
				required : true,
				minlength : 3,
				maxlength : 30
			},
			engEmpLname : {
				required : true,
				minlength : 3,
				maxlength : 30
			},
			hinEmpLname : {
				required : true,
				minlength : 3,
				maxlength : 30
			},
			empDob : {
				required : true
			},
			empDoj : {
				required : true
			},
			otpEnabled : {
				required : true
			},
			secretAnswer : {
				required : true,
				minlength : 3
			},
			regMobileNo : {
				required : true,
				minlength : 10
			},
			email : {
				required : true,
				minlength : 10,
				maxlength : 40,
				email : true
			},
			empGender : {
				required : true
			},
			lstRoleCodes : {
				required : true
			},
			userImage : {
				required : false,
				extension : "png|jpe?g|gif"
			}
		},
		messages : {
			username : {
				required : username_required,
				minlength : username_minlength,
				maxlength : username_maxlength,
				pattern : username_pattern
			},
			engEmpFname : {
				required : engEmpFname_required,
				minlength : engEmpFname_minlength,
				maxlength : engEmpFname_maxlength
			},
			hinEmpFname : {
				required : hinEmpFname_required,
				minlength : hinEmpFname_minlength,
				maxlength : hinEmpFname_maxlength
			},
			engEmpLname : {
				required : engEmpLname_required,
				minlength : engEmpLname_minlength,
				maxlength : engEmpLname_maxlength
			},
			hinEmpLname : {
				required : hinEmpLname_required,
				minlength : hinEmpLname_minlength,
				maxlength : hinEmpLname_maxlength
			},
			empDoj : {
				required : userDoj_required
			},
			empDob : {
				required : userDob_required
			},
			secretAnswer : {
				required : userSecAnswer_required,
				minlength : userSecAnswer_minlength
			},
			regMobileNo : {
				required : regMobileNo_required,
				minlength : regMobileNo_minlength
			},
			email : {
				required : email_required,
				minlength : email_minlength,
				maxlength : email_maxlength,
				email : email_valid
			},
			empGender : {
				required : required
			},
			lstRoleCodes : {
				required : required
			},
			otpEnabled : {
				required : required
			},
			userImage : {
				required : required,
				extension : userImage_extension
			}
		}
	});

	if (readOnly == 'true') {
		$("#myProfileForm :input").attr("disabled", true);
		$("#myProfileForm :input[type=submit]").removeAttr("disabled");
		$("#myProfileForm :input[type=button]").removeAttr("disabled");
		$("#myProfileForm :input[type=hidden]").removeAttr("disabled");
	}

	if (action == 'myProfile') {
		$('#username').attr('readonly', true);
	}

});

function getAllUsers() {
	window.location.href = contextPathJs + "/user/allUsers";
}

// function to check mobile no availability
function checkMobileAvailability() {
	var mobileNo = $("#regMobileNo").val();
	if (mobileNo != '') {
		$.ajax({
			url : contextPathJs + '/myProfile/checkMobilenoAvailability',
			data : 'mobileNo=' + mobileNo,
			type : "GET",
			success : function(result) {
				// if the result is 1
				if (result != 1) {
					// show that the mobile is aleady registered
					$("#mobile_availability_result").addClass("error");
					$("#mobile_availability_result")
							.html(mobileNo + registered);
					$("#regMobileNo").val("");
					$("#regMobileNo").focus();
				} else {
					// show that the mobile is not aleady registered
					$("#mobile_availability_result").addClass("success");
					$("#mobile_availability_result").html(
							mobileNo + notRegistered);
				}
			}
		});
	}
}