/**
 * This file contains all the form validation related to TrainerSkill.jsp
 */


$(document).ready(function() {
	
	$('#updateTrainerSkill').click(function(e){
		
		 if($("input:checked").length == 0)
		 {
			 alert(required);
			 e.preventDefault();
		 }
	});
	
	if(readOnly == 'true')
	{
		$("#trainerSkillForm :input").attr("disabled", true);
		$("#trainerSkillForm :input[type=submit]").removeAttr("disabled");
		$("#trainerSkillForm :input[type=button]").removeAttr("disabled");
		$("#trainerSkillForm :input[type=hidden]").removeAttr("disabled");
	}
	
});


function getAllTrainerSkills()
{
	window.location.href= contextPathJs+"/trainerSkill/allTrainerSkills";
}