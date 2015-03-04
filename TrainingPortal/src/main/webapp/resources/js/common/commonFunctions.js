/**
 * This file is included in all the jsp's
 */

function onlyAlphabets(el) {
	var key = el.charCode; // to support all browsers
	if ((key < 65 || key > 90) && (key < 97 || key > 122) && key != 32
			&& key != 0) {
		return false;
	}
}

function onlyNumeric(el) {
	var key = el.charCode; // to support all browsers
	if ((key < 48 || key > 57) && key != 0) {
		return false;
	}
}

function onlyAlphaNumeric(el) {
	var key = el.charCode; // to support all browsers
	if ((key < 48 || key > 57) && (key < 65 || key > 90)
			&& (key < 97 || key > 122) && key != 32 && key != 0) {
		return false;
	}
}

function goToHomePage() {
	window.location.href = contextPathJs + "/home";
}

loadInternationalization = function(localeFromSession) {
	jQuery.i18n.properties({
		name : [ 'JsAlertMsgs', 'TrainingPortalJsAlertMsgs' ],
		language : localeFromSession,
		path : contextPathJs + '/resources/js_properties/',
		mode : 'both',
		cache : true,
		encoding : 'UTF-8'
	});
};

function resetForm(formId) {
	$(formId).trigger('reset');
}

function languageConversion(sourceElement, targetElement) {
	var inputString = sourceElement.value;
	targetElement.value = pramukhIME.convert(inputString, 'english', 'hindi');
}

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

jQuery.validator.addMethod("pwcheck", function(value) {
	return /^[A-Za-z0-9\d=!\-@._*]*$/.test(value) // consists of only these
			&& /[A-Z]/.test(value) // has a uppercase letter
			&& /[a-z]/.test(value) // has a lowercase letter
			&& /\d/.test(value) // has a digit
			&& /[=!\-@._*]/.test(value); // has a special character.
}, "Please enter proper password.");

function getHelpImageFile() {
	$('#hindiHelpImage').bPopup();
}
