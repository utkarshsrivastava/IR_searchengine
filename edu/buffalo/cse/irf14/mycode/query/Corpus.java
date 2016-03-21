package edu.buffalo.cse.irf14.query;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Writer;

import edu.buffalo.cse.irf14.document.ParserException;
import edu.buffalo.cse.irf14.index.IndexerException;

public class Corpus {
	/*private Writer sumfile;
	private File sumfil;

	public boolean setCorpus(String dirloc)
	{
		
		try {
			File fil, corpusfil;
			java.io.BufferedReader br=null;
			java.io.PrintWriter pw=null;
			String readstr=null;
			String tmpjiststr=null;
			String files[];
			File dir
			for (String cat : catDirectories) {
				dir = new File(ipDir+ File.separator+ cat);
				files = dir.list();
				
				if (files == null)
					continue;
				
				for (String f : files) {
					try {
					fil=new File(ipDir+ File.separator+ cat+File.separator+f);
					br=new BufferedReader(new FileReader(f));
					corpusfil=new File(dirloc+File.separator+f);
					sumfil=new File(summarydir+File.separator+f);
					pw=new PrintWriter(corpusfil);
					PrintWriter pw_jist=new PrintWriter(sumfil);
						if (fil.isFile())
						{
						
							readstr=br.readLine();
						if (readstr!=null)
						{
							
							pw_jist.append(readstr);
							pw.append(readstr);
							
						
						readstr=br.readLine();
						if (readstr!=null)
						{
							
							pw_jist.append(readstr);
							pw.append(readstr);
							
						
						readstr=br.readLine();
						if (readstr!=null)
						{
							
							pw_jist.append(readstr);
							pw.append(readstr);
							
						}
						}
						}
						while(readstr!=null)
							{
								
								pw.write(readstr+System.getProperty("line.seperator"));
								readstr=br.readLine();
							}
						}
					} catch (ParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				}
				
			}
			
			writer.close();
		} catch (IndexerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	private void addToSummary(String readstr) {
		// TODO Auto-generated method stub
		
	}
*/
}
