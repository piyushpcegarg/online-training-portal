/**
 * This file contains all the form validation related to element.jsp
 */

$().ready(function() {

	pramukhIME.enable('hinElementName');
	pramukhIME.enable('hinElementDesc');
	pramukhIME.enable('hinElementToolTip');

	$('#hindiHelpImage').hide();

	$('input[type=radio][name=elementType]').change(function() {

		var elementType = $(this).val();
		if (elementType == '6') {
			$('#elementUrl').attr('readonly', true);
			$("#elementUrl").val("/parent");
		} else {
			$('#elementUrl').attr('readonly', false);
			$("#elementUrl").val("");
		}
	});

	// validate elementForm form on keyup and submit
	$("#elementForm").validate({
		rules : {
			engElementName : {
				required : true,
				minlength : 4,
				maxlength : 200
			},
			hinElementName : {
				required : true,
				minlength : 4,
				maxlength : 200
			},
			engElementToolTip : {
				required : true,
				minlength : 4,
				maxlength : 200
			},
			hinElementToolTip : {
				required : true,
				minlength : 4,
				maxlength : 200
			},
			engElementDesc : {
				required : true,
				minlength : 4,
				maxlength : 200
			},
			hinElementDesc : {
				required : true,
				minlength : 4,
				maxlength : 200
			},
			elementType : {
				required : true
			},
			editable : {
				required : true
			},
			elementUrl : {
				required : true,
				minlength : 2,
				maxlength : 250
			},
			elementOrder : {
				required : true,
				minlength : 1,
				maxlength : 4
			}
		},
		messages : {
			engElementName : {
				required : engElementName_required,
				minlength : engElementName_minlength,
				maxlength : engElementName_maxlength
			},
			hinElementName : {
				required : hinElementName_required,
				minlength : hinElementName_minlength,
				maxlength : hinElementName_maxlength
			},
			engElementToolTip : {
				required : engElementToolTip_required,
				minlength : engElementToolTip_minlength,
				maxlength : engElementToolTip_maxlength
			},
			hinElementToolTip : {
				required : hinElementToolTip_required,
				minlength : hinElementToolTip_minlength,
				maxlength : hinElementToolTip_maxlength
			},
			engElementDesc : {
				required : engElementDesc_required,
				minlength : engElementDesc_minlength,
				maxlength : engElementDesc_maxlength
			},
			hinElementDesc : {
				required : hinElementDesc_required,
				minlength : hinElementDesc_minlength,
				maxlength : hinElementDesc_maxlength
			},
			elementType : {
				required : required
			},
			editable : {
				required : required
			},
			elementUrl : {
				required : elementUrl_required,
				minlength : elementUrl_minlength,
				maxlength : elementUrl_maxlength
			},
			elementOrder : {
				required : elementOrder_required,
				minlength : elementOrder_minlength,
				maxlength : elementOrder_maxlength
			}
		}
	});

	if (readOnly == 'true') {
		$("#elementForm :input").attr("disabled", true);
		$("#elementForm :input[type=submit]").removeAttr("disabled");
		$("#elementForm :input[type=button]").removeAttr("disabled");
		$("#elementForm :input[type=hidden]").removeAttr("disabled");
	}
});

function getAllElements() {
	window.location.href = contextPathJs + "/element/allElements";
}