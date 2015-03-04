/**
 * This file contains all the form validation related to user.jsp
 */

$().ready(function() {

	if (readOnly == 'true') {
		$("#userForm :input").attr("disabled", true);
		$("#userForm :input[type=submit]").removeAttr("disabled");
		$("#userForm :input[type=button]").removeAttr("disabled");
		$("#userForm :input[type=hidden]").removeAttr("disabled");
	}
	
	if (action == 'update') {
		$('#activateFlag').removeAttr("disabled");
	}

	
	$("#searchUserForm").validate({
		rules : {
			userName : {
				required : true,
				minlength : 8,
				maxlength : 30,
				pattern : /^[a-zA-Z0-9.@]*$/
			}
		},
		messages : {
			userName : {
				required : username_required,
				minlength : username_minlength,
				maxlength : username_maxlength,
				pattern : username_pattern
			}
		}
	});
	
	$('#userName').autocomplete({
		serviceUrl: contextPathJs + '/trainer/allUsers',
		paramName: "username",
		delimiter: ",",
	   transformResult: function(response) {
		   
		return {      	
		  //must convert json to javascript object before process
		  suggestions: $.map($.parseJSON(response), function(item) {
 
		      return { 
		    	  value: item.userName,
		    	  data: item.userId 
		    	  };
		   	})
		 };
       }
	 });
});

function getAllTrainers() {
	window.location.href = contextPathJs + "/trainer/allTrainers";
}