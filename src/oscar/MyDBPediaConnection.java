package oscar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import oscar.model.Movie;
import oscar.model.Nominee;
import oscar.model.PersonHistory;

public class MyDBPediaConnection {



	public List<String> restorePersonAllInformation(String personName){ //for test

		ArrayList<String> list = new ArrayList<String>();

		String service  = "http://dbpedia.org/sparql";


		String query=
				"PREFIX dbo:<http://dbpedia.org/property/> "+
						"PREFIX dbpedia: <http://dbpedia.org/resource/>"+
						"PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>"+
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
						"PREFIX foaf:  <http://xmlns.com/foaf/0.1/>"+

					"SELECT ?fullname ?name ?partnername ?thumb ?birthdate ?birthplace ?occupation ?abstract "+
					" WHERE {"+
					" dbpedia:" + personName + " rdfs:label ?name. "+
					" FILTER (langMatches(lang(?name),\"en\")) " +					
					" OPTIONAL {dbpedia:" + personName + " dbpedia-owl:thumbnail ?thumb. } " +
					" OPTIONAL {dbpedia:" + personName + " dbo:dateOfBirth ?birthdate. } " +
					" OPTIONAL {dbpedia:" + personName + " dbo:birthPlace ?birthplace.}" +
					" OPTIONAL {dbpedia:" + personName + " dbpedia-owl:occupation ?oc. ?oc dbpedia-owl:title ?occupation. FILTER (langMatches(lang(?oc),\"en\")).}" +
					" OPTIONAL {dbpedia:" + personName + " dbpedia-owl:birthName ?fullname. FILTER (langMatches(lang(?fullname),\"en\")).}" +
					" OPTIONAL {dbpedia:" + personName + " dbpedia-owl:abstract ?abstract. FILTER (langMatches(lang(?abstract),\"en\")).}" +
					" OPTIONAL {dbpedia:" + personName + " dbpedia-owl:partner ?partner. ?partner rdfs:label ?partnername. FILTER (langMatches(lang(?partnername),\"en\")).}} ";
		 


		System.out.println(query);

		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		try{
			ResultSet results = qe.execSelect();   
			while (results.hasNext()){
				QuerySolution soln = results.nextSolution();
				String fullName = soln.getLiteral("fullname") != null ? soln.getLiteral("fullname").getString() : "";
				String Name = soln.getLiteral("name") != null ? soln.getLiteral("name").getString() : "";
				String partnerName = soln.getLiteral("partnername") != null ? soln.getLiteral("partnername").getString() : "";
				String image = soln.getResource("thumb") !=null ? soln.getResource("thumb").getURI().toString() : "";
				String BirthDate = soln.getLiteral("birthdate") !=null ? soln.getLiteral("birthdate").getString() : "";
				String BirthPlace = soln.getLiteral("birthplace") != null ? soln.getLiteral("birthplace").getString() : "";
				String occupation = soln.getLiteral("oc") !=null ? soln.getLiteral("oc").getString() : "" ;
				String shortAbstract = soln.getLiteral("abstract") !=null ? soln.getLiteral("abstract").getString() : "";

				list.add(Name);
				list.add(fullName);
				list.add(partnerName);
				list.add(image);
				list.add (BirthDate);
				list.add (BirthPlace);
				list.add(occupation);
				list.add(shortAbstract);
			}
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			qe.close();
		}



		return list; 
	}
	
	public List<PersonHistory> restorePersonAllMovies(String personName){ //for test
//System.out.println("here");
		ArrayList<PersonHistory> list = new ArrayList<PersonHistory>();

		String service  = "http://dbpedia.org/sparql";
		
		String query=
				"PREFIX dbo:     <http://dbpedia.org/property/>"+
						"PREFIX dbpedia: <http://dbpedia.org/resource/>"+
						"PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>"+
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
						"PREFIX foaf:  <http://xmlns.com/foaf/0.1/>"+

					"SELECT distinct ?caster ?filmtitle "+
					"WHERE {"+
					" ?film a dbpedia-owl:Film."+
					" ?film ?cast dbpedia:" + personName + "; "+
					" foaf:name ?filmtitle."+
					" ?cast rdfs:label ?caster."+
					" FILTER (langMatches(lang(?caster),\"en\"))  "+
					" OPTIONAL {?film dbo:released ?year.}}" +
					" ORDER BY DESC(?year)";


		System.out.println(query);

		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		try{
			ResultSet results = qe.execSelect(); 
		
			while (results.hasNext()){
				QuerySolution soln = results.nextSolution();
				
				String role = soln.getLiteral("caster").getString();
				String filmTitle = soln.getLiteral("filmtitle").getString();
				String year = soln.getLiteral("year") != null ? soln.getLiteral("year").getString() : "" ;
				
				PersonHistory p = new PersonHistory(role, filmTitle, year);
				list.add(p);
				
			}
			
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			qe.close();
		}

		System.out.println(list.size());

		return list; 
	}
	

	
	public List<String> restoreMovieInformation(String movieName, String yearr){ //for test
		
		String Creteria = exploreMovie(movieName, yearr);
		

		ArrayList<String> list = new ArrayList<String>();

		String service  = "http://dbpedia.org/sparql";
		
		String movieResource = "<http://dbpedia.org/resource/" + movieName + Creteria + ">";
		String query=
				"PREFIX dbo:<http://dbpedia.org/property/>"+
						"PREFIX dbpedia: <http://dbpedia.org/resource/>"+
						"PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>"+
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
						"PREFIX foaf:  <http://xmlns.com/foaf/0.1/>"+
						"SELECT ?labelName ?name ?comment ?abstract  ?country ?budget ?time" +
						" WHERE {" +
						" OPTIONAL {" + movieResource + " <http://dbpedia.org/ontology/Work/runtime> ?time.}" +
						" OPTIONAL {" + movieResource + " rdfs:label ?labelName.}" +
						" OPTIONAL {" + movieResource + " dbo:name ?name. }" +
						" OPTIONAL {" + movieResource + " rdfs:comment ?comment.}" +
						" OPTIONAL {" + movieResource + " dbo:budget ?budget.}" +
						" OPTIONAL {" + movieResource + " dbpedia-owl:abstract ?abstract.}" +
						" OPTIONAL {" + movieResource + " dbo:country ?country.}" +
						" FILTER LANGMATCHES(LANG(?labelName),  \"EN\") FILTER LANGMATCHES(LANG(?name),  \"EN\") FILTER LANGMATCHES(LANG(?abstract),  \"EN\") FILTER LANGMATCHES(LANG(?comment),  \"EN\")" +						
						"}";
					


		System.out.println(query);

		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		try{
			ResultSet results = qe.execSelect();   
			while (results.hasNext()){
				QuerySolution soln = results.nextSolution();
				//"?budget "
				String Name = soln.getLiteral("name") != null ? soln.getLiteral("name").getString() : "";
				String Country = soln.getLiteral("country") != null ? soln.getLiteral("country").getString() : "";
				String Comment = soln.getLiteral("comment") !=null ? soln.getLiteral("comment").getString() : "";
				String shortAbstract = soln.getLiteral("abstract") !=null ? soln.getLiteral("abstract").getString() : "";
				String labelName = soln.getLiteral("labelName") !=null ? soln.getLiteral("labelName").getString() : "";
				String Budget = soln.getLiteral("budget") !=null ? String.valueOf(soln.getLiteral("budget").getLexicalForm()) : "";
				String Time = soln.getLiteral("time") !=null ? soln.getLiteral("time").getString() : "";
				
				list.add(Name);
				list.add(Country);
				list.add(Comment);
				list.add(shortAbstract);
				list.add(labelName);
				list.add(Budget);
				list.add(Time);
			}
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			qe.close();
		}



		return list; 
	}
	
	public List<Movie> restoreMovieAllCasts(String movieName, String yearr){ //for test

		String Creteria = exploreMovie(movieName, yearr);
		
		
		ArrayList<Movie> list = new ArrayList<Movie>();

		String service  = "http://dbpedia.org/sparql";
		
		String query=
				"PREFIX dbo:<http://dbpedia.org/property/>"+
						"PREFIX dbpedia: <http://dbpedia.org/resource/>"+
						"PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>"+
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
						"PREFIX foaf:  <http://xmlns.com/foaf/0.1/>"+

					"SELECT DISTINCT ?name ?castLabel ?fullname "+
					"WHERE {"+
					" <http://dbpedia.org/resource/" + movieName + Creteria + "> dbo:name ?name; "+
					" ?cast ?person."+
					" ?cast rdfs:isDefinedBy <http://dbpedia.org/ontology/>."+
					" ?cast rdfs:label ?castLabel."+
					" ?person rdfs:label ?fullname."+
					" FILTER LANGMATCHES(LANG(?fullname),  \"EN\")"+
					" FILTER LANGMATCHES(LANG(?castLabel),  \"EN\")"+
					" FILTER LANGMATCHES(LANG(?name),  \"EN\")"+
					" } ";


		System.out.println(query);

		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		try{
			ResultSet results = qe.execSelect();   
//			System.out.println(results.hasNext());
			while (results.hasNext()){
				QuerySolution soln = results.nextSolution();
				
				String roleName = soln.getLiteral("castLabel").getString();
				String personName = soln.getLiteral("fullname").getString();
				System.out.println(roleName + " " + personName + " -- ");
				Movie m = new Movie(movieName, roleName, personName);
					
				list.add(m);
			}
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			qe.close();
		}



		return list; 
	}
	
	public String exploreMovie(String movieName, String year){
		String retVal="";
		
		String service  = "http://dbpedia.org/sparql";
		
		String askQuery = "PREFIX dbo:<http://dbpedia.org/property/> ASK {<http://dbpedia.org/resource/" + movieName + "> dbo:name ?name;  a <http://dbpedia.org/ontology/Film>. }";
		String askQuery_film = "PREFIX dbo:<http://dbpedia.org/property/> ASK {<http://dbpedia.org/resource/" + movieName + "_(film)> dbo:name ?name;  a <http://dbpedia.org/ontology/Film>.}";
		String askQuery_year = "PREFIX dbo:<http://dbpedia.org/property/> ASK {<http://dbpedia.org/resource/" + movieName + "_(" + year + "_film)> dbo:name ?name;  a <http://dbpedia.org/ontology/Film>.}";
		
		QueryExecution qe = QueryExecutionFactory.sparqlService(service, askQuery);
		try{
		
			if (qe.execAsk()){
				retVal="";
				
			}
			else
			{
				qe = QueryExecutionFactory.sparqlService(service, askQuery_film);
				   
				if (qe.execAsk()){
					retVal="_(film)";
				}
				else{
					qe = QueryExecutionFactory.sparqlService(service, askQuery_year);
					
					if (qe.execAsk()){
						retVal= "_(" + year + "_film)";
					}
					else{
						retVal="";
						
					}
				}
					
			}
			
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			qe.close();
		}

//		System.out.println(retVal);
		return retVal;
	}

}


