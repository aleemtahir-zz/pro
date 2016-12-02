package crawl.parsing;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.riot.thrift.wire.RDF_Literal;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.XSD;
import org.mindswap.pellet.jena.vocabulary.OWL_1_1;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;

public class Cricmantic {
	public static void main(String[] args) throws IOException, InterruptedException
	{
		String sumdata=null;
		String in1data=null;
		String in2data=null;

		String url="http://www.cricbuzz.com/cricket-full-commentary/16344/pak-vs-sl-10th-match-asia-cup-2016";
	//	in1data="12.5 Irfan to Kohli,  1 run, it's another, this time Irfan oversteps, Kohli guides it to thi";
		parse par=new parse();
		
	//	par.parseCommentary(in1data,2);
		scrap s=new scrap();
		
	try {
			
			sumdata=s.facts(url);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		par.parseFact(sumdata);
		
		try {
			in2data=s.second(url);
			sumdata=s.summary(url);
			in1data=s.first(url);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		par.parseSum(sumdata);
		par.parseCommentary(in2data,2);
		par.parseCommentary(in1data,1);
	}

}
