package crawl.parsing;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.OWL2;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;
public class parse {
	private static String newuri="http://www.semanticweb.org/tayyab/ontologies/2016/7/untitled-ontology-2#";
	private static String match="";
	private static String matchInfo="";
	private static String matchToss="";
	private static String matchDate="";
	private static String team1="";
	private static String team2="";
	private static String event="";
	private static String team1Innings="";
	private static String team2Innings="";
	private static String umpire="";
	private static String Tvumpire="";
	private static String venue="";
	private static String unregdata="";
	public void parseSum(String data) 
	{
		Model model = ModelFactory.createOntologyModel();
		int j=0;
		model.read("cric.rdf");
		int i;
		for(i=0;i<data.length()-3;i++)
		{
			if (data.charAt(i)=='M'&&data.charAt(i+1)=='A'&&data.charAt(i+2)=='T'&&data.charAt(i+3)=='C'
					&&data.charAt(i+4)=='H'&&data.charAt(i+5)==' '&&data.charAt(i+6)=='I'&&data.charAt(i+7)=='N'
					&&data.charAt(i+8)=='F'&&data.charAt(i+9)=='O')
			{
				
				while(data.charAt(i)!='T'||data.charAt(i+1)!='o'||data.charAt(i+2)!='s')
				{
					i=i+1;
				}
				i=i+5;
				j=0;
				
				int team1in=0;
				int team2in=0;
				
				while(j<10)
				{	
					if(data.charAt(i)==team1.charAt(0))
					{
						team1in=1;
						break;
					}
					else if(data.charAt(i)==team2.charAt(0))
					{
						team2in=1;
						break;
					}
					j=j+1;
					i=i+1;
				}
				j=0;
				while(data.charAt(i)!=')')
				{
					
					j=j+1;
					i=i+1;
					
					if(data.charAt(i)=='F'&&data.charAt(i+1)=='i'&&data.charAt(i+2)=='e'&&data.charAt(i+3)=='l'&&data.charAt(i+4)=='d')
					{
						if (team1in==1)
							{
							team1Innings="Second";
							team2Innings="First";
							}
						else
						{
							team2Innings="Second";
							team1Innings="First";
						}
							
					}
					if(data.charAt(i)=='B'&&data.charAt(i+1)=='a'&&data.charAt(i+2)=='t'&&data.charAt(i+3)=='t')
					{
						if (team1in==1)
							{
							team1Innings="First";
							team2Innings="Second";
							}
						else
						{
							team2Innings="First";
							team1Innings="Second";
						}
							
					}
				}
		
				
			}
		}
	
		Resource Team1=model.getResource(newuri+team1);
		Resource Team2=model.getResource(newuri+team2);
		Property pr1=model.getProperty(newuri+"innings");
		Team1.addLiteral(pr1, team1Innings);
		Team2.addLiteral(pr1, team2Innings);
		
		String fileName = "cric.rdf"; 
		FileWriter out;
		try 
		{ 
			out = new FileWriter(fileName );
			model.write( out, "RDF/XML" ); out.close(); 
			} 
		catch(IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	public void parseFact(String data) 
	{
		Model model = ModelFactory.createOntologyModel();
		model.read("crickmantic.owl");
		int i=0;
		int j=0;
		for(i=0;i<data.length()-30;i++)
		{
			if (data.charAt(i)=='M'&&data.charAt(i+1)=='a'&&data.charAt(i+2)=='t'&&data.charAt(i+3)=='c'
					&&data.charAt(i+4)=='h'&&data.charAt(i+5)==':')
			{
				i=i+7;
				
				while(data.charAt(i)!=' ')
				{
					
				team1=team1+data.charAt(i);
				i=i+1;
			
				}
				
				i=i+4;
				while(data.charAt(i)!=',')
				{
					
				team2=team2+data.charAt(i);
				i=i+1;
				
				}
				
				i=i+2;
				;
				while(data.charAt(i)!='D'||data.charAt(i+1)!='a'||data.charAt(i+2)!='t'||data.charAt(i+3)!='e')
				{
					if(data.charAt(i)!=' '&&data.charAt(i)!=',')
					matchInfo=matchInfo+data.charAt(i);
					j=j+1;
					i=i+1;
				}
				i=i+5;
				while(data.charAt(i)!='T'||data.charAt(i+1)!='o'||data.charAt(i+2)!='s'||data.charAt(i+3)!='s')
				{
					
					matchDate=matchDate+data.charAt(i);
					i=i+1;
				}
				i=i+5;
			
				while(data.charAt(i)!='T'||data.charAt(i+1)!='i'||data.charAt(i+2)!='m'||data.charAt(i+3)!='e')
				{	
					matchToss=matchToss+data.charAt(i);
					
					i=i+1;
				}
				i=i+25;
				
				while(data.charAt(i)!='U'||data.charAt(i+1)!='m'||data.charAt(i+2)!='p'||data.charAt(i+3)!='i')
				{
					venue=venue+data.charAt(i);
					
					i=i+1;	
				}
				i=i+9;
				while(data.charAt(i)!='T'||data.charAt(i+1)!='h'||data.charAt(i+2)!='i'||data.charAt(i+3)!='r')
				{
					if (data.charAt(i)!=' ')
					umpire=umpire+data.charAt(i);
					i=i+1;
				}
				i=i+14;
				while(data.charAt(i)!='M'||data.charAt(i+1)!='a'||data.charAt(i+2)!='t'||data.charAt(i+3)!='c')
				{
					if (data.charAt(i)!=' ')
					Tvumpire=Tvumpire+data.charAt(i);
					i=i+1;
				}
				
			}
		}
		match=team1+team2+matchInfo;
		Resource Match=model.createResource(newuri+match);
		Resource class1=model.getResource(newuri+"Match");
		
		Resource Umpire=model.createResource(newuri+umpire);
		Resource UmpireTv=model.createResource(newuri+Tvumpire);
		
		Resource Team1=model.createResource(newuri+team1);
		Resource Team2=model.createResource(newuri+team2);
		Property pr=model.getProperty(newuri+"hasMatchBetween");
		Property hasMatch=model.getProperty(newuri+"instanceHasMatch");
		Property pr5=model.getProperty(newuri+"matchInfo");
		Property pr2=model.getProperty(newuri+"matchDate");
		Property pr4=model.getProperty(newuri+"matchUmpires");
		Property pr6=model.getProperty(newuri+"matchVenue");
		Property pr7=model.getProperty(newuri+"matchTvUmpire");
		Property pr3=model.createProperty(newuri+"matchBetween");
		Resource class2=model.getResource(newuri+"Team");
		Property pr8=model.getProperty(newuri+"isUmpireOf");
		Resource class3=model.getResource(newuri+"FieldUmpire");
		Resource class4=model.getResource(newuri+"TVUmpire");
		model.add(Match,RDF.type,class1);
		model.add(Team1,RDF.type,class2);
		model.add(Team2,RDF.type,class2);
		model.add(Umpire,RDF.type,class3);
		model.add(UmpireTv,RDF.type,class4);
		model.add(Team1,hasMatch,Match);
		model.add(Team2,hasMatch,Match);
		model.add(Umpire,hasMatch,Match);
		model.add(UmpireTv,hasMatch,Match);
		model.add(Match,pr,Team1);
		model.add(Match,pr,Team2);
		model.add(Team1,pr3,Team2);
		model.add(Team2,pr3,Team1);
		Match.addLiteral(pr2, matchDate);
		Match.addLiteral(pr5, matchInfo);
		Match.addLiteral(pr4, umpire);
		Match.addLiteral(pr6, venue);
		Match.addLiteral(pr7, Tvumpire);
		model.add(Umpire,pr8,Match);
		model.add(UmpireTv,pr8,Match);
		
		String fileName = "cric.rdf"; 
		FileWriter out;
		try 
		{ 
			out = new FileWriter(fileName );
			model.write( out, "RDF/XML" ); out.close(); 
			} 
		catch(IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void parseCommentary(String data,int inn)
	{
		
		Model model = ModelFactory.createOntologyModel();
		model.read("cric.rdf");
		
		String over=null;
		String bowler="";
		String batsman="";
		int flag=0;
		int flag2=0;
		int i=0;
		int j=0;
		float f=0;
		int score=0;
		int escore=-1;
		
			for(i=0;i<data.length()-3;i++)
			{
				if(Character.getNumericValue(data.charAt(i))>=0 && Character.getNumericValue(data.charAt(i))<=9	
						&& Character.getNumericValue(data.charAt(i+1))>=0 && Character.getNumericValue(data.charAt(i+1))<=9
						&& data.charAt(i+2)=='.'&& Character.getNumericValue(data.charAt(i+3))>=0 && Character.getNumericValue(data.charAt(i+3))<=6)
				{
				
					over=Character.toString(data.charAt(i))+Character.toString(data.charAt(i+1))+Character.toString(data.charAt(i+2))+Character.toString(data.charAt(i+3));
					i=i+5;
					flag=1;
				}
				if(Character.getNumericValue(data.charAt(i))>=0 && Character.getNumericValue(data.charAt(i))<10
						&&data.charAt(i+1)=='.' && Character.getNumericValue(data.charAt(i+2))>0&&Character.getNumericValue(data.charAt(i+2))<=6)
				{
					
					over=Character.toString(data.charAt(i))+Character.toString(data.charAt(i+1))+Character.toString(data.charAt(i+2));
					i=i+4;
					flag=1;
				}
				if (flag==1)
				{
					i=i+1;
					while(data.charAt(i)!='t'||data.charAt(i+1)!='o')
					{
						if(data.charAt(i)!=' ')
						bowler=bowler+data.charAt(i);
						i=i+1;
					}
					i=i+3;
					while(data.charAt(i)!=',')
					{
					
						if(data.charAt(i)!=' ')
						batsman=batsman+data.charAt(i);
						i=i+1;
					}
					j=0;
					flag=0;
					while(j<30)
					{
						if(j==29&&flag2!=1)
						{
							unregdata=over;
							flag2=2;
						}
						
						if(data.charAt(i)=='n'&&data.charAt(i+1)=='o'&&data.charAt(i+2)==' '&&data.charAt(i+3)=='b'&&data.charAt(i+4)=='a')
						{
							flag=1;
							event="no ball";
							escore=1;
							flag2=1;
							i=i+5;
						}
						if(data.charAt(i)=='w'&&data.charAt(i+1)=='i'&&data.charAt(i+2)=='d'&&data.charAt(i+3)=='e'&&data.charAt(i+4)=='s')
						{
							flag2=1;
							flag=1;
							escore=Character.getNumericValue(data.charAt(i-2));
							event="wide";
								break;
						}
						if(data.charAt(i)=='w'&&data.charAt(i+1)=='i'&&data.charAt(i+2)=='d'&&data.charAt(i+3)=='e')
						{
							flag2=1;
							flag=1;
							event="wide";
								escore=1;
								break;
						}
						
						if(data.charAt(i)=='r'&&data.charAt(i+1)=='u'&&data.charAt(i+2)=='n')
						{
							flag2=1;
							if(Character.getNumericValue(data.charAt(i-2))>0&&Character.getNumericValue(data.charAt(i-2))<10)
							{
								if(flag==1)
								{	
								escore=Character.getNumericValue(data.charAt(i-2))+escore;
								break;
								}
								else
									{
									event="runs";
									score=Character.getNumericValue(data.charAt(i-2));
									break;
									}
							}
							else
							{
								event="no run";
								break;
							}
						}
						if(data.charAt(i)=='F'&&data.charAt(i+1)=='O'&&data.charAt(i+2)=='U'&&data.charAt(i+3)=='R')
						{
							flag2=1;
							if(flag==1)
								escore=5;
							else
							{
								score=4;
								event="FOUR";
							}
							break;
						}
						if(data.charAt(i)=='S'&&data.charAt(i+1)=='I'&&data.charAt(i+2)=='X')
						{
							flag2=1;
							if(flag==1)
								escore=7;
							else
								{
								event="SIX";
								score=6;
								}
							break;
						}
						if(data.charAt(i)=='o'&&data.charAt(i+1)=='u'&&data.charAt(i+2)=='t')
						{
							flag2=1;
							if(flag==1)
							{
								escore=1;
								event="OUT! Run Out";
								break;
							}
							else
								{
								event="OUT!";
								
									while(j<30)
									{
										if(data.charAt(i)=='r'&&data.charAt(i+1)=='u'&&data.charAt(i+2)=='n')
										{
											if(Character.getNumericValue(data.charAt(i-2))>0&&Character.getNumericValue(data.charAt(i-2))<10)
											{
												score=Character.getNumericValue(data.charAt(i-2));
												break;
											}
										}
										i=i+1;
										j=j+1;
									}
								break;
								}
							
						}
						j=j+1;
						i=i+1;
					}
					
					j=0;
					f = Float.parseFloat(over);
					
									
					Resource Match=model.getResource(newuri+match);
					
					Property ball=model.getProperty(newuri+"ballBowler");
					
					Resource overIns = model.createResource(newuri+over); 
					Property bat=model.getProperty(newuri+"ballBatsman");
					Resource bowllerIns = model.createResource(newuri+bowler);
					Resource batsmanIns = model.createResource(newuri+batsman);
					Property player=model.getProperty(newuri+"hasPlayer");
					Property player10=model.getProperty(newuri+"over");
					Property hasMatch=model.getProperty(newuri+"instanceHasMatch");
					Property Score1=model.getProperty(newuri+"scoreIn1stInnings");
					Property Score2=model.getProperty(newuri+"scoreIn2ndInnings");
					Property TScore1=model.getProperty(newuri+"teamScoreIn1stInnings");
					Property TScore2=model.getProperty(newuri+"teamScoreIn2ndInnings");
					Property EScore1=model.getProperty(newuri+"extraScore1stInnings");
					Property EScore2=model.getProperty(newuri+"extraScore2ndInnings");
					Property Event1=model.getProperty(newuri+"eventIn1stInnings");
					Property Event2=model.getProperty(newuri+"eventIn2ndInnings");
					Property player1=model.getProperty(newuri+"innings");
					Property player2=model.getProperty(newuri+"isPlayerOf");
					Resource Team1=model.getResource(newuri+team1);
					Resource Team2=model.getResource(newuri+team2);
					Property player3=model.getProperty(newuri+"playedBall");
					Property player4=model.getProperty(newuri+"throwBall");
					Resource class2=model.getResource(newuri+"Player");
					Resource class3=model.getResource(newuri+"Overs");
					model.add(overIns,RDF.type,class3);
					model.add(bowllerIns,RDF.type,class2);
					model.add(batsmanIns,RDF.type,class2);
					
					model.add(overIns,hasMatch,Match);
					model.add(bowllerIns,hasMatch,Match);
					model.add(batsmanIns,hasMatch,Match);
					
					overIns.addLiteral(player10, f);
					
					if(flag!=1)
						{
						model.add(overIns,bat,batsmanIns);
						}
					model.add(batsmanIns,player3,overIns);	
					model.add(overIns,ball,bowllerIns);
					
					model.add(bowllerIns,player4,overIns);
					if(flag2==2)
					{
						Property UnrecData=model.getProperty(newuri+"unrecognizedData");
						Match.addLiteral(UnrecData, unregdata);
					}
					flag2=0;
				if(inn==1 && team1Innings=="First")
				{
					if (escore!=-1)
						{
							overIns.addLiteral(EScore1, escore);
							overIns.addLiteral(TScore1, escore);
						}
					else
					{
						overIns.addLiteral(Score1, score);
						overIns.addLiteral(TScore1, score);
					}
					
					overIns.addLiteral(Event1, event);
					
					
					model.add(Team2,player,bowllerIns);
					model.add(Team1,player,batsmanIns);
					
					bowllerIns.addLiteral(player1, team2Innings);
					
					batsmanIns.addLiteral(player1, team1Innings);
					
					bowllerIns.addProperty(player2, Team2);
					batsmanIns.addProperty(player2,Team1);
				}
				else if(inn==1)
				{
					if (escore!=-1)
					{
						overIns.addLiteral(EScore1, escore);
						overIns.addLiteral(TScore1, escore);
					}
					else
						{
						overIns.addLiteral(Score1, score);
						overIns.addLiteral(TScore1, score);
						}
				
					overIns.addLiteral(Event1,  event);
				
					model.add(Team1,player,bowllerIns);
					model.add(Team2,player,batsmanIns);
					bowllerIns.addLiteral(player1, team1Innings);
					batsmanIns.addLiteral(player1, team2Innings);
					bowllerIns.addProperty(player2, Team1);
					batsmanIns.addProperty(player2,Team2);
				}
				else if(inn==2 && team1Innings=="Second")
				{
					if (escore!=-1)
					{
						overIns.addLiteral(EScore2, escore);
						overIns.addLiteral(TScore2, escore);
					}
					else
					{
						overIns.addLiteral(Score2, score);
						overIns.addLiteral(TScore2, score);
					}
					overIns.addLiteral(Event2, event);
					
					model.add(Team2,player,bowllerIns);
					model.add(Team1,player,batsmanIns);
					bowllerIns.addLiteral(player1, team2Innings);
					batsmanIns.addLiteral(player1, team1Innings);
					bowllerIns.addProperty(player2, Team2);
					batsmanIns.addProperty(player2,Team1);
				}
				else
				{
					if (escore!=-1)
					{
						overIns.addLiteral(EScore2, escore);
						overIns.addLiteral(TScore2, escore);
					}
					else
					{
						overIns.addLiteral(Score2, score);
						overIns.addLiteral(TScore2, score);
					}
					overIns.addLiteral(Event1, event);
					
					model.add(Team1,player,bowllerIns);
					model.add(Team2,player,batsmanIns);
					bowllerIns.addLiteral(player1, team1Innings);
					batsmanIns.addLiteral(player1, team2Innings);
					bowllerIns.addProperty(player2, Team1);
					batsmanIns.addProperty(player2,Team2);
				}
					flag=0;		
					flag2=0;
					score=0;
					escore=-1;
					event="";
					batsman="";
					bowler="";
					
				}
			}
			String fileName = "cric.rdf"; 
			FileWriter out;
			try 
			{ 
				out = new FileWriter(fileName );
				model.write( out, "RDF/XML" ); out.close(); 
				} 
			catch(IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
				
	}
	
}

