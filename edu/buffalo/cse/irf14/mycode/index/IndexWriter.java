/**
 * 
 */
package edu.buffalo.cse.irf14.index;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import edu.buffalo.cse.irf14.analysis.Analyzer;
import edu.buffalo.cse.irf14.analysis.AnalyzerFactory;
import edu.buffalo.cse.irf14.analysis.Token;
import edu.buffalo.cse.irf14.analysis.TokenStream;
import edu.buffalo.cse.irf14.analysis.Tokenizer;
import edu.buffalo.cse.irf14.analysis.TokenizerException;
import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.FieldNames;

/**
 * @author u2
 * Class responsible for writing indexes to disk
 */

public class IndexWriter {
	
	
	int docprops[]=new int[5];
	
	private static AnalyzerFactory factory;
	private static Tokenizer tizer;
	
	private static String idxDir;
	private HashMap<String, List<Doc>> termIdx;
	private HashMap<String, List<Doc>> authorIdx;
	private HashMap<String, List<Doc>> categoryIdx;
	private HashMap<String, List<Doc>> placeIdx;
	private HashSet<Integer> seenDocs;
	private String authorOrg;
	private static int numofdocs=0;
	/**
	 * Default constructor
	 * @param indexDir : The root directory to be sued for indexing
	 */public static long doclength=0;
	public IndexWriter(String indexDir) {
		
		if (factory == null) {
			factory = AnalyzerFactory.getInstance();
			tizer = new Tokenizer();
		}
		
		termIdx = new HashMap<String, List<Doc>>(50000);
		authorIdx = new HashMap<String, List<Doc>>(1000);
		categoryIdx = new HashMap<String, List<Doc>>(100);
		placeIdx = new HashMap<String, List<Doc>>(1000);
		idxDir = indexDir;
		seenDocs = new HashSet<Integer>();
	}
	
	
	
	/**
	 * Method to add the given Document to the index
	 * This method should take care of reading the filed values, passing
	 * them through corresponding analyzers and then indexing the results
	 * for each indexable field within the document. 
	 * @param d : The Document to be added
	 * @throws IndexerException : In case any error occurs
	 */
	private Doc doc=new Doc();

	private static int totdoclength;
	public static int getTotDocs()
	{
	return numofdocs;	
	}
	public void addDocument(Document d) throws IndexerException {
		String[] values;
		TokenStream stream;
		Analyzer analyzer;
		String fileId = d.getField(FieldNames.FILEID)[0];
		int fid = Integer.parseInt(fileId);
		numofdocs++;
		authorOrg = null;
		try {
			if (seenDocs.contains(fid)) {
				stream = tizer.consume(d.getField(FieldNames.CATEGORY)[0]);
				addToIndex(stream, FieldNames.CATEGORY, fileId); //only add to category idx
			doc.setDocId(fileId);
			} else {
				for (FieldNames fn : FieldNames.values()) {
					if (fn == FieldNames.FILEID) {
						continue;
					} else if (fn == FieldNames.AUTHOR) {
						values = d.getField(FieldNames.AUTHORORG);
						if (values != null) {
							authorOrg = values[0];
						}
					}
					
					values = d.getField(fn);
					
					if (values != null) {
						for (String v : values) {
							stream = tizer.consume(v);
							analyzer = factory.getAnalyzerForField(fn, stream);
							
							if (analyzer != null) {
								while (analyzer.increment()) {
									
								}			
								stream = analyzer.getStream();
							}
							
							addToIndex(stream, fn, fileId);
						}
					}
				}
				
				seenDocs.add(fid);
			}
		} catch (TokenizerException e) {
			
		}
	}
	
	private void addToIndex(TokenStream stream, FieldNames fn, String fileId) {
		HashMap<String, List<Doc>> idx = getIndex(fn);
		boolean appendOrg = (fn == FieldNames.AUTHOR && authorOrg != null);
		doc.setDoclen(stream.size());
		totdoclength+=stream.size();
		if (idx != null) {
			Map<String, Integer> streamMap = getAsMap(stream);
			String key;
			List<Doc> postings;
			for (Entry<String, Integer> etr : streamMap.entrySet()) {
				key = etr.getKey();
				doc.setTermfreq(etr.getValue());
				
				if (appendOrg)
					key += "|" + authorOrg;
				doc.setTerm(key);
				
				if(idx.containsKey(key))
				{
					Iterator itr=(postings=idx.get(key)).iterator();
					Doc tmpdoc;
					while(itr.hasNext())
					{
						tmpdoc=(Doc)itr.next();
						if (tmpdoc.equals(doc))
						{
							itr.remove();break;
						}
					}
				}
				else
				{
					postings = new ArrayList<Doc>();
				}
				postings.add(doc);
				idx.put(key, postings);
			}
		}
		
	}

	private HashMap<String, List<Doc>> getIndex(FieldNames fn) {
		switch (fn) {
		case AUTHOR:
			return authorIdx;
		case CATEGORY:
			return categoryIdx;
		case PLACE:
			return placeIdx;
		case TITLE:
		case CONTENT:
		case NEWSDATE:
			return termIdx;
			
			default:
				return null;
		}
	}

	private Map<String, Integer> getAsMap(TokenStream stream) {
		Token t;
		String s;
		stream.reset();
		Map<String, Integer> map = new HashMap<String, Integer>(stream.size());
		int val;
		while (stream.hasNext()) {
			t = stream.next();
			
			if (t!= null) {
				s = t.toString();
				val = map.containsKey(s) ? map.get(s) : 0;
				map.put(s, ++val);
			}
		}
		
		return map;
	}



	/**
	 * Method that indicates that all open resources must be closed
	 * and cleaned and that the entire indexing operation has been completed.
	 * @throws IndexerException : In case any error occurs
	 */
	public void close() throws IndexerException {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(idxDir+File.separator+"author.idx"), "UTF-8"));
			passivate(writer, authorIdx);
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(idxDir+File.separator+"place.idx"), "UTF-8"));
			passivate(writer, placeIdx);
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(idxDir+File.separator+"category.idx"), "UTF-8"));
			passivate(writer, categoryIdx);
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(idxDir+File.separator+"term.idx"), "UTF-8"));
			passivate(writer, termIdx);
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(idxDir+File.separator+"idx.props"), "UTF-8"));
			writer.write("N="+seenDocs.size());
			writer.flush();
			writer.close();
			seenDocs.clear();
			seenDocs = null;
		} catch (IOException e) {
			throw new IndexerException();
		}
		
		
	}

	private void passivate(BufferedWriter writer,
			HashMap<String, List<Doc>> idx) throws IOException {
		StringBuilder bldr = new StringBuilder();
		int ctr = 0;
		List<Doc> value;
		TreeMap<String, List<Doc>> map = new TreeMap<String, List<Doc>>(idx);
		for (Entry<String, List<Doc>> etr : map.entrySet()) {
			value = etr.getValue();
			bldr.append(etr.getKey()).append("/").append(value.size()).append(":");
			bldr.append(doc.toString());
			bldr.append("#");
			ctr++;
			
			if (ctr % 1000 == 0) {
				writer.write(bldr.toString());
				writer.newLine();
				bldr.delete(0, bldr.length());
				writer.flush();
			}
		}
		
		writer.write(bldr.toString());
		bldr.delete(0, bldr.length());
		writer.newLine();
		writer.flush();
		writer.close();
		idx.clear();
		idx = null;
		map.clear();
		map = null;
		
	}

	private String asString(List<String> value) {
		Collections.sort(value);
		return value.toString();
	}
	public static int getPostingsListSize(String term) throws FileNotFoundException,IOException{
		// TODO Auto-generated method stub
		boolean istermthere=false;
		String tmpstr="0";
		BufferedReader reader= new BufferedReader(new FileReader(idxDir+File.separator+"term.idx"));
		while(!istermthere)
			{
			tmpstr=reader.readLine();
			if (tmpstr.contains(term))
			{
				tmpstr=tmpstr.substring(tmpstr.indexOf('/'), tmpstr.indexOf(":")-1);
				istermthere=true;
			}
			}
		try{
		if (true){Integer.parseInt(tmpstr);
		return new Integer(tmpstr);
		}
		}catch(NumberFormatException nfe)
		{
			
		}
		return 0;
		
	}
	public static double getAvgDocLength() {
		// TODO Auto-generated method stub
		
		return totdoclength/getTotDocs();
	}




}
