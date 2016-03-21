/**
 * 
 */
package edu.buffalo.cse.irf14.query;

import edu.buffalo.cse.irf14.index.IndexWriter;

/**
 * @author u2
 * Static parser that converts raw text to Query objects
 */
public class QueryParser {
	/**
	 * MEthod to parse the given user query into a Query object
	 * @param userQuery : The query to parse
	 * @param defaultOperator : The default operator to use, one amongst (AND|OR)
	 * @return Query object if successfully parsed, null otherwise
	 */
	String[] clauses=null;
	String[] q_indexes=null;
	String[] phrases=null;
	public static final String AND_="AND";
	public static final String OR_="OR";
	public static final String NOT_="NOT";
	public static final String COLO=":";
	public static Query parse(String userQuery, String defaultOperator) throws QueryException {
		//TODO: YOU MUST IMPLEMENT THIS METHOD
		String query_id=null;
		String user_query=null;
		int indextmp=userQuery.indexOf(COLO);
		if (indextmp==-1) throw new QueryException("INVALID index Exception !");
		query_id=userQuery.substring(0, indextmp);
		user_query=userQuery.substring(indextmp);
		//:userQuery.substring(userQuery.indexOf("{"));
		user_query=putDefOperator(user_query,defaultOperator);
		user_query=parseUserQuery(user_query);
		
		return new Query(user_query);
	}
	
	private static String putDefOperator(String user_query,
			String defaultOperator)
	{
		user_query=user_query.trim();
		String def_query=null;
		String[] query_idx=user_query.split(COLO);
		String def_prefix="Term";
		if (query_idx.length==1)
		{
			String[] abc=user_query.split(" ");
			int kk=0;
			while(kk<abc.length){
				if (abc[kk].startsWith("\""))
				{
					while(!abc[kk].endsWith("\"")){
					abc[kk]+=abc[++kk];}
					
				}
			def_query+="Term:"+abc[kk];
			if (abc[kk].startsWith("("))
			{
				def_query+="[";
			
				
			}
			if (abc[kk].endsWith(")"))
			{
				def_query+="]";
			}
				if (abc[kk].equals(AND_)
						||abc[kk].equals(OR_)
						||abc[kk].equals(NOT_)
						)
				{
					String tmpstr=null;
					tmpstr=(kk+1<abc.length)?abc[kk+1]:"";
					def_query+=abc[kk]+tmpstr;kk+=2;
				}
				else {
					def_query+=defaultOperator+" ";
					}
				}
					def_query+="Term:"+abc[kk];
				
			
			return def_query;
		}
		
		int qidxct=1;
		String prefix=query_idx[0];
		while(qidxct<query_idx.length)
		{
			String[] abc=query_idx[qidxct].split(" ");
			int kk=0;
			String pref_2=null;
			while(kk<abc.length){
				if (abc[kk].startsWith("\""))
				{
					while(!abc[kk].endsWith("\"")){
					abc[kk]+=abc[++kk];}
					
				}
			def_query+=prefix+":"+abc[kk];
			if (abc[kk].equals(AND_)
					||abc[kk].equals(OR_)
					||abc[kk].equals(NOT_)
					)
			{
				String tmpstr=null;
				tmpstr=(kk+1<abc.length)?abc[kk+1]:"";
				def_query+=abc[kk]+tmpstr;kk+=2;
			}
			if (abc[kk].startsWith("("))
			{
				def_query+="[";
				if (kk==0){pref_2=def_prefix;def_prefix=abc[kk-1].substring(0, abc[kk-1].length()-1);}
				
			}
			if (abc[kk].endsWith(")"))
			{
				def_query+="]";
				if (pref_2!=null) {def_prefix=pref_2;pref_2=null;}
			}
			
			else {
				if (!(abc[kk].equals(AND_)
						||abc[kk].equals(OR_)
						||abc[kk].equals(NOT_)
						))
				{
				def_query+=def_prefix+":"+abc[kk];
				}
				else{
					def_query+=def_prefix+":"+abc[kk];
				}
			}
		int i=1;
		
		String [] tmp_query=query_idx[qidxct].split(" ");
		if (kk==tmp_query.length-1){
		if (tmp_query[kk].equals("Term"))
				{
			prefix="Term";
		}
				if ( tmp_query[kk].equals("Author"))
						{
					prefix="Author";
				}
						if (tmp_query[kk].equals("Place"))
								{
							prefix="Place";
						}
								if ( tmp_query[kk].equals("Category"))
		{
			prefix="Category";
		}
								else
								{
									prefix="Term";
								}
								kk++;
		}
		/*String actual_query=tmp_query[0];
		if (tmp_query.length>1)
		{
			String tmpstr=null;
			while(i<tmp_query.length)
			{
				if ((tmp_query[i].equals(AND_))
						|| (tmp_query[i].equals(OR_))
						|| (tmp_query[i].equals(NOT_)))
				{
					tmpstr=(i+1<tmp_query.length)?tmp_query[i+1]:"";
					actual_query+=tmp_query[i]+tmpstr;
					i+=2;
					continue;
				}
				
				actual_query+=defaultOperator+" "+tmp_query[i];
			}
		}*/
		}
		// TODO Auto-generated method stub
		
	}
		return def_query
				;
	}
	
	private static int num_expr=0;
	
	private static String parseUserQuery(String query) {
		// TODO Auto-generated method stub
		String[] quer=query.split(" ");int i=0;
		while(i<quer.length)
		{
			
		}
		return query;
		
	}
	/*private static int getNumOfResult(String query) {
		// TODO Auto-generated method stub
		String[] quer_arr=query.split(" ");
		int i=0;
		while(i<quer_arr.length)
		quer_arr[i].split(COLO_);
		{
			
		}
		
		return 0;
	}*/
	private static  int getLastBrackets(String query) {
		// TODO Auto-generated method stub
		int i=0;
		int count=0;int las=0;
		while(i<query.length())
		{
			if (query.charAt(i)=='(') {count++;las=i;}
			i++;
		}
		count=num_expr
			;
		return las;
	}
	
public static void main(String[] args) {
		QueryParser qp=new QueryParser();
		qp.parseUserQuery("Q_FS_3529_3:place:washington AND federal treasury");
		
	}
}
