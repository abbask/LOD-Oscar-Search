/**
 * 
 */
$(document).ready(function(){
	
	$.ajax({
		dataType: "json",
		type: "GET",
		url: "rest/year",
	})
	.done(function( data ) {
//		console.log(data);
		
		$.each(data.data, function(key, item) {
			strSelectOptions = "";
			strSelectOptions += '<option value="' + item.id +'">' + item.year + '</option>';
			$("#selectYear").append(strSelectOptions);
//			console.log(item.id);
//			console.log(item.year);
		});
		
	});

});