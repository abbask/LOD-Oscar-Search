
$(document).ready(function(){
	//alert(GetURLParameter("name"));

	personName = GetURLParameter("name");
	yearr = GetURLParameter("year");
	var CorrectFormat = personName;
	CorrectFormat = CorrectFormat.split('%20').join('_')
	CorrectFormat = CorrectFormat.replace(/^[a-z]/, function(letter) {
		return letter.toUpperCase();
	});

	$.ajax({
		dataType: "json",
		type: "GET",
		url: "http://localhost:8080/Oscar/rest/movie/" + CorrectFormat + "/year/" + yearr,
	})
	.done(function( data ) {


		item = data.data;
		console.log(item);
		if(item.length != 0){

			Name = item[0];
			Country = item[1];
			dbpediaLabel = item[4];
			Budget = item[5];
			timme = item[6];
			
			Comment = item[2];		
			shortAbstract = item[3];
			
			strName = "<h1><label>" +  Name+ " </label></h1>";
			strTime = "Time: <b>" + timme + " minutes</b>"
			strCountry = "<br /><br />Country: <b>" + Country + "</b>"
			strLabel = "<br /><br />DBPedia Label: <b>" + dbpediaLabel + "</b>";
			strBudget = "<br /><br />Budget: <b>" + Budget + "</b>"; 

			strAbstract = '<br /><br /><div id="abstractDiv" style="width:700px" width="200px"><i>' + shortAbstract + '</i></div>';
			strComment = '<br />Comment: <br /><div id="abstractDiv" style="width:700px" width="200px"><i>' + Comment + '</i></div>';

			strContentGeneralInfo = strName + strTime + strCountry + strLabel + strBudget + strAbstract + strComment;

			
			$("#movieInfoDiv").append(strContentGeneralInfo);
		}	
		else{
			$("#movieInfoDiv").append("No Data found");
		}
		

	}); //ajax done

	$.ajax({
		dataType: "json",
		type: "GET",
		url: "http://localhost:8080/Oscar/rest/movie/" + CorrectFormat + "/year/" + yearr + "/person",
	})
	.done(function( data ) {
		item = data.data;
		strRows = "";
		strTable = '<table><th><div style="float:left;">Role</div></th><th><div style="float:left;">Person</div></th>';
		strRows = "<tr>";
		
		if (item.length == 0)
			return;
		
		$.each(item, function(key, i) {
			//console.log(i);
			role = i.roleName;
			personName = i.personName;
			year = i.year;


			strRole = "<td>" + role + "</td>";
			strPersonName = "<td>" + personName + "</td>";

			strRows = strRows + "</tr>";
			strRows = strRows + strRole + strPersonName ;

		});

		console.log(strRows);

		strTable = strTable + strRows + "</table>";

		$('#specificInfo').append(strTable);
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