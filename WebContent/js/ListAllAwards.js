/**
 * 
 */
$(document).ready(function(){
	
	$.ajax({
		dataType: "json",
		type: "GET",
		url: "rest/award",
	})
	.done(function( data ) {
		$.each(data.data, function(key, item) {
			strSelectOptions = "";
			strSelectOptions += '<option value="' + item.awardId +'">' + item.awardLabel + '</option>';
			$("#selectAward").append(strSelectOptions);
		});
	});

});
