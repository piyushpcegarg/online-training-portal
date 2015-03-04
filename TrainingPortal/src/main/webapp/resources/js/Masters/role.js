/**
 * This file contains all the form validation related to role.jsp
 */

$().ready(function() {

	pramukhIME.enable('hinRoleName');
	pramukhIME.enable('hinRoleShortName');
	pramukhIME.enable('hinRoleDesc');

	$('#hindiHelpImage').hide();

	// validate roleForm form on keyup and submit
	$("#roleForm").validate({
		rules : {
			engRoleName : {
				required : true,
				minlength : 4,
				maxlength : 120
			},
			hinRoleName : {
				required : true,
				minlength : 4,
				maxlength : 120
			},
			engRoleShortName : {
				required : true,
				minlength : 4,
				maxlength : 60
			},
			hinRoleShortName : {
				required : true,
				minlength : 4,
				maxlength : 60
			},
			engRoleDesc : {
				required : true,
				minlength : 4,
				maxlength : 200
			},
			hinRoleDesc : {
				required : true,
				minlength : 4,
				maxlength : 200
			}
		},
		messages : {
			engRoleName : {
				required : engRoleName_required,
				minlength : engRoleName_minlength,
				maxlength : engRoleName_maxlength
			},
			hinRoleName : {
				required : hinRoleName_required,
				minlength : hinRoleName_minlength,
				maxlength : hinRoleName_maxlength
			},
			engRoleShortName : {
				required : engRoleShortName_required,
				minlength : engRoleShortName_minlength,
				maxlength : engRoleShortName_maxlength
			},
			hinRoleShortName : {
				required : hinRoleShortName_required,
				minlength : hinRoleShortName_minlength,
				maxlength : hinRoleShortName_maxlength
			},
			engRoleDesc : {
				required : engRoleDesc_required,
				minlength : engRoleDesc_minlength,
				maxlength : engRoleDesc_maxlength
			},
			hinRoleDesc : {
				required : hinRoleDesc_required,
				minlength : hinRoleDesc_minlength,
				maxlength : hinRoleDesc_maxlength
			}
		}
	});
	
	if (readOnly == 'true') {
		$("#roleForm :input").attr("disabled", true);
		$("#roleForm :input[type=submit]").removeAttr("disabled");
		$("#roleForm :input[type=button]").removeAttr("disabled");
		$("#roleForm :input[type=hidden]").removeAttr("disabled");
	}
});

function getAllRoles() {
	window.location.href = contextPathJs + "/role/allRoles";
}