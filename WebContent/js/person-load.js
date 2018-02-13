/**
 * 
 */
$(document).ready(function(){
	//alert(GetURLParameter("name"));

	personName = GetURLParameter("name");
//alert(personName);
//	var nameArray = personName.split("%20");
	var CorrectFormat = personName;

//	CorrectFormat = CorrectFormat.replace("%20","_");	
	CorrectFormat = CorrectFormat.split('%20').join('_')

//	$.each(nameArray, function(index, value) { 
////	alert(index + ': ' + value);
//	CorrectFormat = CorrectFormat + "_" + value;

//	});

//alert(CorrectFormat);
	CorrectFormat = CorrectFormat.replace(/^[a-z]/, function(letter) {
		return letter.toUpperCase();
	});
//alert(CorrectFormat);



	$.ajax({
		dataType: "json",
		type: "GET",
		url: "http://localhost:8080/Oscar/rest/person/" + CorrectFormat,
	})
	.done(function( data ) {


		item = data.data;
		if(item.length != 0){

			Name = item[0];
			BirthName = item[1];
			PartnerName = item[2];
			if (PartnerName == "") {
				PartnerName = "<i>unknown</i>"
			}

			Image = item[3];

			var birthDate = item[4];
			birthPlace = item[5];
			Occupation = item[6];
			shortAbstract = item[7];

			//First name: <input type="text" name="fname"><br>
			strName = "<h1><label>" +  Name+ " </label></h1>";
			strBirthDate = "Birth Date: <b>" + birthDate.substring(0,10) + "</b>"
			strBirthName = "<br /><br />Birth Name: <b>" + BirthName + "</b>";
			strBirthPlace = "<br /><br />Birth Place: <b>" + birthPlace + "</b>"; 
			strPartnerName = "<br /><br />Partner Name: <b>" + PartnerName + "</b>";
			strOccupation = "<br /><br />Occupation: <b>" + Occupation + "</b>";
			strAbstract = '<br /><br /><div id="abstractDiv" style="width:700px" width="200px"><i>' + shortAbstract + '</i></div>';


			strContentGeneralInfo = strName + strBirthDate + strBirthName + strBirthPlace + strPartnerName + strOccupation + strAbstract;

			$("#pictureImg").attr("src", Image);
			$("#personalInfoDiv").append(strContentGeneralInfo);
		}	
		else{
			$("#pictureImg").attr("src","");
			$("#personalInfoDiv").append("No Data found");
		}
		

	}); //ajax done

	$.ajax({
		dataType: "json",
		type: "GET",
		url: "http://localhost:8080/Oscar/rest/person/" + CorrectFormat + "/movies",
	})
	.done(function( data ) {
		item = data.data;
		strRows = "";
		strTable = '<table><th><div style="float:left;">Role</div></th><th><div style="float:left;">Movie</div></th>';
		strRows = "<tr>";
		
		if (item.length == 0)
			return;
		
		$.each(item, function(key, i) {
			//console.log(i);
			role = i.role;
			filmTitle = i.filmTitle;
			year = i.year;


			strRole = "<td>" + role + "</td>";
			strMovie = "<td>" + filmTitle + "</td>";

			strRows = strRows + "</tr>";
			strRows = strRows + strRole + strMovie ;

		});

		console.log(strRows);

		strTable = strTable + strRows + "</table>";

		$('#specificInfo').append(strTable);

//		Name = item[0];
//		BirthName = item[1];
//		PartnerName = item[2];
//		if (PartnerName == "") {
//		PartnerName = "<i>unknown</i>"
//		}

//		Image = item[3];

//		var birthDate = item[4];
//		birthPlace = item[5];
//		Occupation = item[6];
//		shortAbstract = item[7];

//		//First name: <input type="text" name="fname"><br>
//		strName = "<h1><label>" +  Name+ " </label></h1>";
//		strBirthDate = "Birth Date: <b>" + birthDate.substring(0,10) + "</b>"
//		strBirthName = "<br /><br />Birth Name: <b>" + BirthName + "</b>";
//		strBirthPlace = "<br /><br />Birth Place: <b>" + birthPlace + "</b>"; 
//		strPartnerName = "<br /><br />Partner Name: <b>" + PartnerName + "</b>";
//		strOccupation = "<br /><br />Occupation: <b>" + Occupation + "</b>";
//		strAbstract = '<br /><br /><div id="abstractDiv" style="width:700px" width="200px"><i>' + shortAbstract + '</i></div>';


//		strContentGeneralInfo = strName + strBirthDate + strBirthName + strBirthPlace + strPartnerName + strOccupation + strAbstract;

//		$("#pictureImg").attr("src", Image);
//		$("#personalInfoDiv").append(strContentGeneralInfo);

	}); //ajax done
});

function GetURLParameter(sParam)
{
	var sPageURL = window.location.search.substring(1);
	var sURLVariables = sPageURL.split('&');
	for (var i = 0; i < sURLVariables.length; i++)
	{
		var sParameterName = sURLVariables[i].split('=');
		if (sParameterName[0] == sParam)
		{
			return sParameterName[1];
		}
	}
}