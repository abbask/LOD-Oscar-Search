/**
 * 
 */
$(document).ready(function(){ 
	$(function() {
//		$( "input[type=submit],a.button, button" )
		$( "#searchButton" )
		.button()
		.click(function( event ) {
			event.preventDefault();
			
			selectedYear = "";
			selectedYearInt = "";
			selectedAward = "";
			
			selectedYear = $("#selectYear").val();
			selectedYearInt = $("#selectYear option:selected").text();
			selectedAward = $("#selectAward").val();
			
			strUrl="";
			
			if (selectedYear == "all" && selectedAward == "all"){
				
			}
			
			if (selectedYear != "all" && selectedAward == "all") {
				strUrl="rest/year/" + selectedYear + "/nominees";
			}
			
			if (selectedYear == "all" && selectedAward != "all") {
				strUrl="rest/award/" + selectedAward+ "/nominees";
			}
			
			if (selectedYear != "all" && selectedAward != "all") {
				strUrl="rest/year/" + selectedYearInt + "/award/" + selectedAward + "/nominees";
			}
			$("#content").empty();

			$.ajax({
				dataType: "json",
				type: "GET",
				url: strUrl,
			})
			.done(function( data ) {
				
				$.each(data.data, function(key, items) {
					strSection = "";
					strRow = "";
					divEachRow = "";
					$.each(items,function (k,i){
						
						strPersons = "";
						$.each(i.persons, function(jp, person){

//							strPersons = strPersons + person.personFullName + ", ";
							strPersons = strPersons + '<a href="person.html?name=' + person.personFullName + '">' + person.personFullName + '</a>, ';
						});
						
						strMovie = '<a href="movie.html?name=' + i.movie + '&year=' + i.year.year + '">  (' + i.movie + ')</a>';
						yearHeader = i.year.year;
						awardHeader = i.award;
						
						strRow = strPersons + strMovie + "<br />";
						
						strWon = "";
						console.log(i.won);
						if (i.won == true) strWon = "<b>   *Won</b>"
						
						
						divEachRow = divEachRow + '<div class="rowNominee"><span class="rowPersons">' + strPersons + '</span><span class="rowMovie"><i>' + strMovie + '</i></span>' + strWon + '</div>'	;
						
					});
//									
					strYearHeader="<h3>" + yearHeader + "</h3>";
					strAwardHeader="<h4>" + awardHeader +  "</h4>";
					strSection  = strSection + strYearHeader +  strAwardHeader +  divEachRow + "<br />";
					
//					
					$("#content").append(strSection);
				});//for each


				
			}); //ajax done



		});


	})	
});
