package edu.buffalo.cse.irf14;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.buffalo.cse.irf14.document.Parser;
import edu.buffalo.cse.irf14.document.ParserException;
import edu.buffalo.cse.irf14.index.IndexerException;
import edu.buffalo.cse.irf14.query.Corpus;
import edu.buffalo.cse.irf14.query.Query;
import edu.buffalo.cse.irf14.query.QueryParser;

/**
 * Main class to run the searcher.
 * As before implement all TODO methods unless marked for bonus
 * @author u2
 *
 */
public class SearchRunner {
	public enum ScoringModel {TFIDF, OKAPI};
	public ScoringModel sm;
	public static String indexDirec=null;
	public static String corpusDirec=null;
	public static char mode=0;
	public PrintStream ps=null;
	
	/**
	 * Default (and only public) constuctor
	 * @param indexDir : The directory where the index resides
	 * @param corpusDir : Directory where the (flattened) corpus resides
	 * @param mode : Mode, one of Q or E
	 * @param stream: Stream to write output to
	 */
	public SearchRunner(String indexDir, String corpusDir, 
			char mode, PrintStream stream) {
		//TODO: IMPLEMENT THIS METHOD
		indexDirec=indexDir;
		corpusDirec=corpusDir;
		this.mode=mode;
		ps=stream;
		
	}
	
	/**
	 * Method to execute given query in the Q mode
	 * @param userQuery : Query to be parsed and executed
	 * @param model : Scoring Model to use for ranking results
	 */
	public void query(String userQuery, ScoringModel model) {
		//TODO: IMPLEMENT THIS METHOD
		Query q=new Query(userQuery);
		this.sm=model;
	}
	
	/**
	 * Method to execute queries in E mode
	 * @param queryFile : The file from which queries are to be read and executed
	 */
	public void query(File queryFile) {
		//TODO: IMPLEMENT THIS METHOD
		try{
			Query q;
			//QueryParser qp=new Quer
		 BufferedReader br=new BufferedReader(new FileReader(queryFile));
		 String line1=br.readLine();line1=line1.trim();
		 while (!line1.startsWith("numQueries=")){line1=br.readLine();line1=line1.trim();}
		 String[] abc=line1.split("=");
		 String sval=abc[1];
		 short val=Short.parseShort(sval);
		 short tmppi=0;
		 String[] Equery=new String[val];
		 while(tmppi<val)
		 {
			 Equery[tmppi]=br.readLine();
			 q=QueryParser.parse(Equery[tmppi],QueryParser.OR_);
		 }
		 
		}catch(Exception fe){}
	}
	
	/**
	 * General cleanup method
	 */
	public void close() {
		//TODO : IMPLEMENT THIS METHOD
		
	}
	
	/**
	 * Method to indicate if wildcard queries are supported
	 * @return true if supported, false otherwise
	 */
	public static boolean wildcardSupported() {
		//TODO: CHANGE THIS TO TRUE ONLY IF WILDCARD BONUS ATTEMPTED
		return false;
		/*String querystr=null;
		if (querystr.contains("*"))
		{
			String[] quer_ar=querystr.split("*");
			ArrayList<String> query_arr=new ArrayList<String>();
			int len=quer_ar.length;short k=0;
			while(k<len)
			{
				String[] q_ar=quer_ar[k].split("?");
				int ttl=0;
				while(ttl<q_ar.length){
				query_arr.add(q_ar[ttl]);ttl++;
				}
			}
		}
		q_ar
		*/
	}
	
	/**
	 * Method to get substituted query terms for a given term with wildcards
	 * @return A Map containing the original query term as key and list of
	 * possible expansions as values if exist, null otherwise
	 */
	public Map<String, List<String>> getQueryTerms() {
		//TODO:IMPLEMENT THIS METHOD IFF WILDCARD BONUS ATTEMPTED
		
		return null;
		
		
	}
	
	/**
	 * Method to indicate if speel correct queries are supported
	 * @return true if supported, false otherwise
	 */
	public static boolean spellCorrectSupported() {
		//TODO: CHANGE THIS TO TRUE ONLY IF SPELLCHECK BONUS ATTEMPTED
		return true;
	}
	
	/**
	 * Method to get ordered "full query" substitutions for a given misspelt query
	 * @return : Ordered list of full corrections (null if none present) for the given query
	 */
	public List<String> getCorrections() {
		//TODO: IMPLEMENT THIS METHOD IFF SPELLCHECK EXECUTED
		return null;
	}
	
	}
