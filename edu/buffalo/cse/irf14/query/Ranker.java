package edu.buffalo.cse.irf14.query;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.buffalo.cse.irf14.SearchRunner;
import edu.buffalo.cse.irf14.index.Doc;
import edu.buffalo.cse.irf14.index.IndexWriter;

public class Ranker {
	double score=0;
	public double calculateOkapi(Doc[] doc)
{int i=0;
double IDF=0;
int postingsize=0;
try {
	postingsize = IndexWriter.getPostingsListSize(doc[i].getTerm());
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
return 0;
}

		while(i<doc.length){
			IDF=Math.log10((IndexWriter.getTotDocs()-postingsize+0.5)/(postingsize+0.5));
		score=score+IDF*(doc[i].getTermfreq()*2.2/(doc[i].getTermfreq()+1.2*(.25+.75*(doc[i].getDoclen()/(IndexWriter.getAvgDocLength())))));
		}
		return score;
}

public  double calculatetfIdf(Doc[] doc)
{int i=0;
double IDF=0;

	double tf=0;
	while(i<doc.length){
		
	tf+=doc[i].getTermfreq();
	try {
		IDF+=Math.log10((IndexWriter.getTotDocs()/IndexWriter.getPostingsListSize(doc[i].getTerm())));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return 0;
	}
}
	return tf*IDF;
}

}
