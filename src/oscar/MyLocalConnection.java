package oscar;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oscar.model.Award;
import oscar.model.Nominee;
import oscar.model.Person;
import oscar.model.Year;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.sparql.engine.http.QueryExceptionHTTP;
import com.hp.hpl.jena.util.FileManager;

public class MyLocalConnection {
	
	public HashMap<String, HashMap<String, Nominee>> restoreCategoryNominees(String awardIdparam){

		HashMap<String, HashMap<String, Nominee>> list = new HashMap<String, HashMap<String,Nominee>>();
		
		
		FileManager.get().addLocatorClassLoader(MyLocalConnection.class.getClassLoader());
		Model model = FileManager.get().loadModel("C:/Users/Abbas/workspace-WEB/Oscar/src/oscar-6.owl");

		String queryString = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
						"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
						"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " + 
						"PREFIX oscar: <http://www.semanticweb.org/abbas/ontologies/2015/2/oscar#> " +
						"SELECT ?awardLabel ?movieTitle ?personName ?award ?movie ?person ?year ?won " +
						"WHERE { ?award a oscar:" + awardIdparam + "." +
						"?award oscar:isAnounced ?oscar. " +
						"?oscar oscar:hasYear ?year." +
						"?award oscar:hasLabel ?awardLabel." +						
						"?cast oscar:hasNominatedFor ?award." +
						"?roll rdfs:subPropertyOf oscar:isCrewOf." +
						"?cast ?roll ?movie." +
						"?person oscar:featuredAs ?cast." +
						"?person oscar:hasFullName ?personName." +
						"?movie oscar:hasTitle ?movieTitle." +
						"BIND(EXISTS{?cast oscar:hasWon ?award} AS ?won)" +
						"} ORDER BY ?awardLabel ?movieTitle";

		System.out.println(queryString );
		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);

		try{
			ResultSet results = qe.execSelect();

			while (results.hasNext()){
				QuerySolution soln = results.nextSolution();

				Resource awardId = soln.getResource("award");
				Literal awardLabel = soln.getLiteral("awardLabel");

				Resource movieId = soln.getResource("movie");
				Literal movieTitle = soln.getLiteral("movieTitle");

				Resource personId = soln.getResource("person");
				Literal personName = soln.getLiteral("personName");
				
				int year = soln.getLiteral("year").getInt();
				
				Boolean won = soln.getLiteral("won").getBoolean();
				
				if ((list.containsKey(awardId.getLocalName()))){ ///if category exists
					if (list.get(awardId.getLocalName()).containsKey(movieId.getLocalName())){ ///if movie exists
						
						Nominee n = list.get(awardId.getLocalName()).get(movieId.getLocalName());
						Person p = new Person(personId.getLocalName(),personName.getString());
						n.getPersons().add(p);
						
					}
					else{ ///if movie is new
						
						Person p = new Person(personId.getLocalName(),personName.getString());
						List<Person> personList = new ArrayList<Person>();
						personList.add(p);
						Year y = new Year("", String.valueOf(year));
						Nominee n = new Nominee(awardId.getLocalName(), awardLabel.getString(), movieId.getLocalName()
								, movieTitle.getString(), personList, y, won);
						
						list.get(awardId.getLocalName()).put(movieId.getLocalName(),n );
						
					}
						
				}
				else{ ///if category is new
					
					Person p = new Person(personId.getLocalName(),personName.getString());
					List<Person> personList = new ArrayList<Person>();
					personList.add(p);
					Year y = new Year("", String.valueOf(year));
					//Boolean won = soln.getLiteral("won").getBoolean();
					
					Nominee n = new Nominee(awardId.getLocalName(), awardLabel.getString(), movieId.getLocalName()
							, movieTitle.getString(), personList,y,won);
					
					HashMap<String, Nominee> tempList = new HashMap<String, Nominee>();
					tempList.put(movieId.getLocalName(), n);
					
					list.put(awardId.getLocalName(), tempList );
				}

			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			qe.close();
		}

		return list;
	}
	
	public HashMap<String, HashMap<String, Nominee>> restoreYearCategoryNominees(String yearId, String awardIdparam){

		HashMap<String, HashMap<String, Nominee>> list = new HashMap<String, HashMap<String,Nominee>>();
		
		
		FileManager.get().addLocatorClassLoader(MyLocalConnection.class.getClassLoader());
		Model model = FileManager.get().loadModel("C:/Users/Abbas/workspace-WEB/Oscar/src/oscar-6.owl");

		String queryString = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
						"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
						"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " + 
						"PREFIX oscar: <http://www.semanticweb.org/abbas/ontologies/2015/2/oscar#> " +
						"SELECT ?awardLabel ?movieTitle ?personName ?award ?movie ?person ?won " +
						"WHERE { ?award a oscar:" + awardIdparam + "." +
						"?award oscar:isAnounced ?oscar. " +
						"?award oscar:hasLabel ?awardLabel." +
						"?oscar oscar:hasYear \"" + yearId +  "\"^^<http://www.w3.org/2001/XMLSchema#int>. " +
						"?cast oscar:hasNominatedFor ?award." +
						"?roll rdfs:subPropertyOf oscar:isCrewOf." +
						"?cast ?roll ?movie." +
						"?person oscar:featuredAs ?cast." +
						"?person oscar:hasFullName ?personName." +
						"?movie oscar:hasTitle ?movieTitle." +
						"BIND(EXISTS{?cast oscar:hasWon ?award} AS ?won)" +
						"} ORDER BY ?awardLabel ?movieTitle";

		System.out.println(queryString );
		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);

		try{
			ResultSet results = qe.execSelect();

			while (results.hasNext()){
				QuerySolution soln = results.nextSolution();

				Resource awardId = soln.getResource("award");
				Literal awardLabel = soln.getLiteral("awardLabel");

				Resource movieId = soln.getResource("movie");
				Literal movieTitle = soln.getLiteral("movieTitle");

				Resource personId = soln.getResource("person");
				Literal personName = soln.getLiteral("personName");
				
				int year = Integer.valueOf(yearId);
				
				Boolean won = soln.getLiteral("won").getBoolean();
				
				if ((list.containsKey(awardId.getLocalName()))){ ///if category exists
					if (list.get(awardId.getLocalName()).containsKey(movieId.getLocalName())){ ///if movie exists
						
						Nominee n = list.get(awardId.getLocalName()).get(movieId.getLocalName());
						Person p = new Person(personId.getLocalName(),personName.getString());
						n.getPersons().add(p);
						
					}
					else{ ///if movie is new
						
						Person p = new Person(personId.getLocalName(),personName.getString());
						List<Person> personList = new ArrayList<Person>();
						personList.add(p);
						Year y = new Year("",String.valueOf(year));
						Nominee n = new Nominee(awardId.getLocalName(), awardLabel.getString(), movieId.getLocalName()
								, movieTitle.getString(), personList,y, won);
						
						list.get(awardId.getLocalName()).put(movieId.getLocalName(),n );
						
					}
						
				}
				else{ ///if category is new
					
					Person p = new Person(personId.getLocalName(),personName.getString());
					List<Person> personList = new ArrayList<Person>();
					personList.add(p);
					Year y = new Year("",String.valueOf(year));
					Nominee n = new Nominee(awardId.getLocalName(), awardLabel.getString(), movieId.getLocalName()
							, movieTitle.getString(), personList,y, won);
					
					HashMap<String, Nominee> tempList = new HashMap<String, Nominee>();
					tempList.put(movieId.getLocalName(), n);
					
					list.put(awardId.getLocalName(), tempList );
				}

			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			qe.close();
		}

		return list;
	}

	public List<Year> restoreAllYear(){

		ArrayList<Year> list = new ArrayList<Year>();

		FileManager.get().addLocatorClassLoader(MyLocalConnection.class.getClassLoader());
		Model model = FileManager.get().loadModel("C:/Users/Abbas/workspace-WEB/Oscar/src/oscar-6.owl");


		String queryString = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
						"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
						"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " + 
						"PREFIX oscar: <http://www.semanticweb.org/abbas/ontologies/2015/2/oscar#> " +
						"SELECT ?oscar ?year " +
						"WHERE { ?oscar a  oscar:Oscar. " +
						"?oscar oscar:hasYear ?year	 " +
						"} ORDER BY DESC (?year )";

		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);

		try{
			ResultSet results = qe.execSelect();
			while (results.hasNext()){
				QuerySolution soln = results.nextSolution();
				Resource id = soln.getResource("oscar");
				Literal number = soln.getLiteral("year");
				//				Literal name = soln.getLiteral ("awardLabel");
				Year year = new Year(id.getLocalName(), number.getString());
				list.add(year);

			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			qe.close();
		}

		return list;

	}

	public HashMap<String, HashMap<String, Nominee>> restoreYearallNominees(String yearId){
		//ArrayList<Nominee> list = new ArrayList<Nominee>();
		//HashMap<String, Nominee> list = new HashMap<String, Nominee>();
		HashMap<String, HashMap<String, Nominee>> list = new HashMap<String, HashMap<String,Nominee>>();
		
		
		FileManager.get().addLocatorClassLoader(MyLocalConnection.class.getClassLoader());
		Model model = FileManager.get().loadModel("C:/Users/Abbas/workspace-WEB/Oscar/src/oscar-6.owl");

		String queryString = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
						"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
						"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " + 
						"PREFIX oscar: <http://www.semanticweb.org/abbas/ontologies/2015/2/oscar#> " +
						"SELECT ?awardLabel ?movieTitle ?personName ?award ?movie ?person ?year ?won " +
						"WHERE { 	?award oscar:isAnounced oscar:" + yearId + ". " +
						"oscar:" + yearId + " oscar:hasYear ?year." +
						"?award oscar:hasLabel ?awardLabel." +
						"?cast oscar:hasNominatedFor ?award." +
						"?roll rdfs:subPropertyOf oscar:isCrewOf." +
						"?cast ?roll ?movie." +
						"?person oscar:featuredAs ?cast." +
						"?person oscar:hasFullName ?personName." +
						"?movie oscar:hasTitle ?movieTitle." +
						"BIND(EXISTS{?cast oscar:hasWon ?award} AS ?won)" +
						"} ORDER BY ?awardLabel ?movieTitle";

		System.out.println(queryString );
		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);

		try{
			ResultSet results = qe.execSelect();

			while (results.hasNext()){
				QuerySolution soln = results.nextSolution();

				Resource awardId = soln.getResource("award");
				Literal awardLabel = soln.getLiteral("awardLabel");

				Resource movieId = soln.getResource("movie");
				Literal movieTitle = soln.getLiteral("movieTitle");

				Resource personId = soln.getResource("person");
				Literal personName = soln.getLiteral("personName");
				
				int year = soln.getLiteral("year").getInt();
				
				Boolean won = soln.getLiteral("won").getBoolean();
				
				if ((list.containsKey(awardId.getLocalName()))){ ///if category exists
					if (list.get(awardId.getLocalName()).containsKey(movieId.getLocalName())){ ///if movie exists
						
						Nominee n = list.get(awardId.getLocalName()).get(movieId.getLocalName());
						Person p = new Person(personId.getLocalName(),personName.getString());
						n.getPersons().add(p);
						
					}
					else{ ///if movie is new
						
						Person p = new Person(personId.getLocalName(),personName.getString());
						List<Person> personList = new ArrayList<Person>();
						personList.add(p);
						Year y = new Year(yearId,String.valueOf(year));
						
						Nominee n = new Nominee(awardId.getLocalName(), awardLabel.getString(), movieId.getLocalName()
								, movieTitle.getString(), personList,y, won);
						
						list.get(awardId.getLocalName()).put(movieId.getLocalName(),n );
						
					}
						
				}
				else{ ///if category is new
					
					Person p = new Person(personId.getLocalName(),personName.getString());
					List<Person> personList = new ArrayList<Person>();
					personList.add(p);
					Year y = new Year(yearId,String.valueOf(year));
					
					Nominee n = new Nominee(awardId.getLocalName(), awardLabel.getString(), movieId.getLocalName()
							, movieTitle.getString(), personList,y, won);
					
					HashMap<String, Nominee> tempList = new HashMap<String, Nominee>();
					tempList.put(movieId.getLocalName(), n);
					
					list.put(awardId.getLocalName(), tempList );
				}
					

//////////////////////////////////////////////////////////////////////
//				String key = awardLabel.getString() + movieTitle.getString();
//
//				if (list.containsKey(key)){
//
//				Nominee n =  list.get(key);
//				Person p = new Person(personId.getLocalName(), personName.getString());
//				n.getPersons().add(p);
//				
//				}
//				else{
////					System.out.println(personId.getLocalName());
////					System.out.println(personName.getString());
//					Person p = new Person(personId.getLocalName(), personName.getString()); 
//					List<Person> personList = new ArrayList<Person>();
//					personList.add(p);
//					Nominee nominee = new Nominee(awardId.getLocalName(), awardLabel.getString() , 
//							movieId.getLocalName(), movieTitle.getString(), personList);
//					list.put(key, nominee);
//				}


			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			qe.close();
		}

		return list;
	}

	public List<Award> restoreAllCategory(){

		ArrayList<Award> list = new ArrayList<Award>();

		FileManager.get().addLocatorClassLoader(MyLocalConnection.class.getClassLoader());
		Model model = FileManager.get().loadModel("C:/Users/Abbas/workspace-WEB/Oscar/src/oscar-6.owl");

		String queryString = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
						"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
						"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " + 
						"PREFIX oscar: <http://www.semanticweb.org/abbas/ontologies/2015/2/oscar#> " +
						"SELECT ?award ?awardLabel " +
						"WHERE { ?award rdfs:subClassOf* oscar:Award. " +
						"?award rdfs:label ?awardLabel. " +
						"}";
		System.out.println(queryString);
		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);

		try{
			ResultSet results = qe.execSelect();
			while (results.hasNext()){
				QuerySolution soln = results.nextSolution();
				
				Resource awardId = soln.getResource("award");
				Literal awardLabel = soln.getLiteral ("awardLabel");
				
				Award award = new Award(awardId.getLocalName(), awardLabel.getString());
				list.add(award);

			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			qe.close();
		}

		return list;

	}

	public List<String> restoreRemote(){ //for test

		ArrayList<String> list = new ArrayList<String>();

//		String service = "http://data.linkedmdb.org/sparql";	
		String service  = "http://dbpedia.org/sparql";
		
		
		String query= "PREFIX dbont: <http://dbpedia.org/ontology/> "+
			    "PREFIX dbpedia-owl: <http://dbpedia.org/property/>"+			        
			    "SELECT DISTINCT ?actor ?film ?birthdate ?occupation"+
			    "WHERE {  "+
			    "?film dbpedia-owl:starring ?actor ." +
			    "FILTER regex( str(?actor),\"Nicole_Kidman\") ." +
			    "}";

//		Good 04302015
//		String query = "PREFIX movie: <http://data.linkedmdb.org/resource/movie/> " +
//				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
//				"PREFIX dc: <http://purl.org/dc/terms/> " +
//				"SELECT ?film ?id { ?film dc:title \"Boyhood\". " + 
//				" ?film movie:filmid ?id } limit 10";		

		// Goood		
		//		String query = "PREFIX movie: <http://data.linkedmdb.org/resource/movie/> " +
		//				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
		//				"SELECT ?actor ?id { ?actor movie:actor_name \"Angelina Jolie\". " + 
		//				" ?actor movie:actor_actorid ?id } limit 10";

		//		String query = "PREFIX movie: <http://data.linkedmdb.org/resource/movie/> " +
		//						"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +	
		//						"SELECT DISTINCT ?p WHERE { ?a rdf:type movie:film. ?a  ?p ?o} limit 50 ";


		//		String query = "PREFIX actor: <http://data.linkedmdb.org/resource/actor/> " +
		//				"SELECT DISTINCT ?p WHERE{<http://data.linkedmdb.org/resource/film/2014> ?p ?o } limit 10";

		//		String query = "PREFIX movie: <http://data.linkedmdb.org/> " +
		//				"SELECT ?s ?o WHERE{ ?s a ?o } limit 10";


		QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		try{
			ResultSet results = qe.execSelect();   
			System.out.println(results.hasNext());
			while (results.hasNext()){
				QuerySolution soln = results.nextSolution();
				String uri = soln.getResource("film").getLocalName();
				System.out.println(uri);
//				String uri = soln.getResource("film").toString();
//				Literal id = soln.getLiteral("id");
//				//				Literal name = soln.getLiteral ("id");
//				System.out.println(id.getInt() + " --> " + uri );			
//				//				System.out.println( uri );
				list.add(uri.toString());
			}
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			qe.close();
		}


		//		String query = "ASK {?s a ?o}";
		//        QueryExecution qe = QueryExecutionFactory.sparqlService(service, query);
		//        try {
		//            if (qe.execAsk()) {
		//                System.out.println(service + " is UP");
		//            } // end if
		//        } catch (QueryExceptionHTTP e) {
		//            System.out.println(service + " is DOWN");
		//        } finally {
		//            qe.close();
		//        } // end try/catch/finally

		return list; 
	}

	public List<String>  restoreTest() {

		ArrayList<String> list = new ArrayList<String>();

		FileManager.get().addLocatorClassLoader(MyLocalConnection.class.getClassLoader());
		Model model = FileManager.get().loadModel("C:/Users/Abbas/workspace-WEB/Oscar/src/oscar.owl");


		String queryString = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
						"PREFIX owl: <http://www.w3.org/2002/07/owl#> " +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
						"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " + 
						"PREFIX oscar: <http://www.semanticweb.org/abbas/ontologies/2015/2/oscar#> " +
						"SELECT ?movieTitle " +
						"WHERE { 	?movie rdf:type oscar:Movie; " +
						"oscar:hasTitle ?movieTitle; " +
						"oscar:participatedIn oscar:oscar87 " +
						"} ORDER BY ?movieTitle";

		Query query = QueryFactory.create(queryString);

		// Execute the query and obtain results
		QueryExecution qe = QueryExecutionFactory.create(query, model);

		try{
			ResultSet results = qe.execSelect();
			while (results.hasNext()){
				QuerySolution soln = results.nextSolution();
				Literal name = soln.getLiteral ("movieTitle");				 
				list.add(name.toString());

			}
		}
		finally{
			qe.close();
		}

		// Output query results	
		//ResultSetFormatter.out(System.out, results, query);

		return list;

	}

}
