/**
 * This file contains all the form validation related to user.jsp
 */

$().ready(function() {

	$('#trainingStartTime').timepicker({timeFormat: 'H:i'});
	$('#trainingEndTime').timepicker({timeFormat: 'H:i'});
	
	$("#trainingDate").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat : "dd/mm/yy",
		minDate : '+0D',
		maxDate : '+1Y',
	});

	// validate trainingForm form on keyup and submit
	$("#trainingForm").validate({
		rules : {
			courseName : {
				required : true,
				minlength : 10,
				maxlength : 200
			},
			trainerName : {
				required : true
			},
			trainingDate : {
				required : true
			},
			trainingStartTime : {
				required : true
			},
			trainingEndTime : {
				required : true
			}
		},
		messages : {
			courseName : {
				required : courseName_required,
				minlength : courseName_minlength,
				maxlength : courseName_maxlength
			},
			trainerName : {
				required : trainerName_required
			},
			trainingDate : {
				required : trainingDate_required
			},
			trainingStartTime : {
				required : trainingStartTime_required
			},
			trainingEndTime : {
				required : trainingEndTime_required
			}
		}
	});

	if (readOnly == 'true') {
		$("#trainingForm :input").attr("disabled", true);
		$("#trainingForm :input[type=submit]").removeAttr("disabled");
		$("#trainingForm :input[type=button]").removeAttr("disabled");
		$("#trainingForm :input[type=hidden]").removeAttr("disabled");
	}

	if (action == 'update') {
		$('#trainingName').attr('readonly', true);
	}
	
	
	$('#trainerName').autocomplete({
		serviceUrl: contextPathJs + '/training/allTrainers',
		paramName: "trainername",
		delimiter: ",",
	   transformResult: function(response) {
		   
		return {      	
		  //must convert json to javascript object before process
		  suggestions: $.map($.parseJSON(response), function(item) {
 
		      return { 
		    	  value: item.userName,
		    	  data: item.trainerCode
		    	  };
		   	})
		 };
       }
	 });

});

function getAllTrainings() {
	window.location.href = contextPathJs + "/training/allTrainings";
}
