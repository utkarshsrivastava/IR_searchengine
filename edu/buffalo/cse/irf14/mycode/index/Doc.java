package edu.buffalo.cse.irf14.index;

import java.util.Comparator;

public class Doc implements Comparable{
	
	public static final String DELIMIT="_";
	public static final String DELIMIT_doc="_;";
	public static final String  DELIMIT_docID = "_-";

	private String extrainfo=null;
	
	Doc(String file,String term,int termfreq,int posindex,String extrainfo) {
			this.term=term;	
			this.posindex = posindex;
			this.extrainfo = extrainfo;
			this.termfreq = termfreq;
			if(file!=null)this.docId=file;	
	}
	Doc()
	{
		
	}
	public String getExtrainfo() {
		return extrainfo;
	}


	public void setExtrainfo(String extrainfo) {
		this.extrainfo = extrainfo;
	}


	public int getPosindex() {
		return posindex;
	}


	public void setPosindex(int posindex) {
		this.posindex = posindex;
	}


	public int getDoclen() {
		return doclen;
	}


	public void setDoclen(int doclen) {
		this.doclen = doclen;
	}


	public int getTermfreq() {
		return termfreq;
	}


	public void setTermfreq(int termfreq) {
		this.termfreq = termfreq;
	}


	public String getDocId() {
		return docId;
	}


	public void setDocId(String docId) {
		this.docId = docId;
	}


	private int posindex=0;
	private int doclen=0;
	private int termfreq=1;
	private String docId=null;
	private String term=null;
	
	
	String docprop_toString()
{
return docId+DELIMIT_docID+term+DELIMIT+termfreq+DELIMIT+posindex+DELIMIT+extrainfo+DELIMIT_doc;
/*String extrainfo_le=null;
String posInd=null;
String doclength=null;
String termfreq=null;
String ID=null;
*/

}
	


	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if (o instanceof String)
		{
			if (this.docId.equals(extractDocId(o))) return 1;
		}
		if (this.docId==((Doc)o).getDocId())return 1;
		return 0;
	}
	private String extractDocId(Object o) {
		// TODO Auto-generated method stub
		String str=(String) o;String dId=null;
		dId=str.split(Doc.DELIMIT_docID)[0];
		return (dId!=null?dId:"");
		
	}
	public String getTerm() {
		// TODO Auto-generated method stub
		return this.term;
	}
	public String getDocID() {
		// TODO Auto-generated method stub
		return this.docId;
	}
	public void setTerm(String term) {
		// TODO Auto-generated method stub
		this.term=term;
	}



}
