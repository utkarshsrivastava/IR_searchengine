package edu.buffalo.cse.irf14.query;

/**
 * Class that represents a parsed query
 * @author nikhillo
 *
 */
public class Query {
	/**
	 * Method to convert given parsed query into string
	 */
	String query=null;
	Query(){
		
	}
public Query(String query){
		this.query=query;
	}
	
	public String toString() {
		//TODO: YOU MUST IMPLEMENT THIS
		return query;
	}
}
