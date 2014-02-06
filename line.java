import java.util.*;

// The line class comes into picture when there is VB.Line in source form file.
class line extends control
{
 
	private javax.swing.JLabel l;
	
  //used to store the x co-ordinate of first point of the line

	private int X1;
  
  //used to store the y co-ordinate of the first point of the line
	private int Y1;
	
  //used to store the x co-ordinate of the second point of the line
	private int X2;
  
	//used to store the y co-ordinate of second point of the the line
	private int Y2;
	
  //used to store the border style property of the line.
  	private int borderStyle;

  //used to store the visibility property of the line.
	boolean visible;
	
  //used to store the border color of the line.
	boolean bcolor;

	boolean eflag;
	
	//storing error report
 	private String errReport;


	public line()
	{
		l=new javax.swing.JLabel();
		borderStyle=1;
		visible=true;
		bcolor=false;
	}
	
	
//identifying the properties
	public void setProperties(StringTokenizer s)
	{
		
		HashTable h= new HashTable();
		
	
		int height=0;		// used to store height of the control object.
		
	    int width=0;		//	used to store width of the control object.
	    
	   	int left=0;			 //used to store left of the control object.
		
		int top=0;			//used to store top of the control object.
	
	
		Integer val,val1;
		
		String curToken=new String();
		curToken=getCaption(s);
		l.setName(curToken);

		
		while(curToken.compareTo("End")!=0)
		{
			
			curToken=getCaption(s);
		
			val=(Integer)h.hs.get(curToken);
			if(val==null)
			{
				errReport+="Couldnot map Property in Line "+getName()+" :Property: "+curToken;
				curToken=getCaption(s);

				errReport+=": Value : "+curToken+"\n";
				curToken="";
				continue;
			}
			switch(val.intValue())
			{
			
				case 2://End
					System.out.println("Handled All Properties of line: "+getName());
					break;
					
				case 28://X1
				curToken=getCaption(s);
				try{
					X1=Integer.parseInt(curToken);
					if(X1<0)
					X1+=75000;
					X1=X1/15;
				}
				catch(NumberFormatException Ne){
					errReport+="Number Format Exception in Line:"+getName()+":X1 property: Value :"+curToken;
					X1=0;
					eflag=true;
				}
				break;
			
			case 29://Y1
				curToken=getCaption(s);
				try{
					Y1=Integer.parseInt(curToken);
					Y1=Y1/15;
					Y1=Y1+23; 
				}
				catch(NumberFormatException Ne){
					errReport+="Number Format Exception in Line:"+getName()+":Y1 property: Value: "+curToken;
					Y1=0;
					eflag=true;
				}
				break;
			
			case 30://X2
				curToken=getCaption(s);
				try{
					X2=Integer.parseInt(curToken);
					if(X2<0)
					X2+=75000;
					X2=X2/15;
				}
				catch(NumberFormatException Ne){
					X2=0;
					errReport+="Number Format Exception in Line:"+getName()+":Y1 property: Value: "+curToken+"\n";
					eflag=true;
				}
				break;
			
			case 31://Y2
				curToken=getCaption(s);
				try{
					Y2=Integer.parseInt(curToken);
					Y2=Y2/15;
					Y2=Y2+23;
				}
				catch(NumberFormatException Ne){
					Y2=0;
					errReport+="Number Format Exception in Line:"+getName()+":Y2 property\n";
					eflag=true;
				}
				break;
			
			case 65://BorderStyle
				curToken=getCaption(s);
				try{
					borderStyle=Integer.parseInt(curToken);
				}
				catch(NumberFormatException Ne){
					borderStyle=1;//Default is solid
					eflag=true;
					errReport+="Number Format Exception in Line:"+getName()+":BorderStyle property: Value: "+curToken+"\n";
				}

				break;
			
			case 93://BorderColor
				curToken=getCaption(s);
				curToken=curToken.substring(2,10);
				try{
					// If System color
					if(Integer.parseInt(curToken.substring(0,2),10)==80)
					setForecolor(l,Integer.parseInt(curToken.substring(2,8),16),true);
					// Else RGB color	
					else
					{
						int Fb,Fg,Fr;
						try{
							Fb=Integer.parseInt(curToken.substring(2,4),16);
						}
						catch(NumberFormatException Ne){

							Fb=0;
							eflag=true;
						}
						try{
							Fg=Integer.parseInt(curToken.substring(4,6),16);
						}
						catch(NumberFormatException Ne){
							Fg=0;
							eflag=true;
						}
						try{
							Fr=Integer.parseInt(curToken.substring(6,8),16);
						}
						catch(NumberFormatException Ne){

							Fr=0;
							eflag=true;
						}
						l.setForeground(new java.awt.Color(Fr,Fg,Fb));
					}
				}
				catch(NumberFormatException Ne){
					setForecolor(l,8,true);//ie;windowtext
				}
				bcolor=true;		
				break;
				
			case 57://Visible
				curToken=getCaption(s);
				curToken=getCaption(s);
				visible=false;
				break;
				
			default:
				curToken=getCaption(s);
				break;
			}		
		}
	}

 
 //get name of the line object
	public String getName()
	{
		return(l.getName());	
	}
	


// generating code for declaration of the control
	public String getDeclaration()
	{
		return("javax.swing.JLabel "+l.getName()+";\n\t");	
	}
	


	public String getCode()
	{
		String code=new String();
		String name=new String(l.getName());
		code+=name+"=new JLabel();\n\t\t";
		return(code);
	}
 
 
 //generating code
	public String getLineCode(compositeObject co,int tabIndex,String tabName) 
	{
		String lineCode=new String();
		String parentName=new String();
		String name=new String(l.getName());
		String color = new String();
		if(bcolor==true)
		color = l.getForeground().toString();	
		boolean tflag=false;
		if(co.getClass().toString().compareTo("class tabbedpane")==0)
		{
			tflag=true;
			tabbedpane tp=(tabbedpane)co;
			parentName=tp.getName();
			Y1=Y1-23;//To Take Care Of
			Y2=Y2-23;//Title of JFrame
		}
		else if(co.getClass().toString().compareTo("class frame")==0)
		{
			frame fr=(frame)co;
			parentName=fr.getName();	
			Y1=Y1-23;
			Y2=Y2-23;
		}
		else if(co.getClass().toString().compareTo("class form")==0)
		{
			parentName="this";	
		}
		else
		{
			picturebox pb=(picturebox)co;
			parentName=pb.getName();
			Y1=Y1-23;
			Y2=Y2-23;
		}
		if(visible==false)
		lineCode+="if("+visible+")//Start of Block for Line's Visibility\n\t\t{\n\t\t";
		if(tflag)
		{
			tabbedpane tp=(tabbedpane)co;
			int i;
			for(i=0;i<tp.getCtrlIndex();i++)
			{
				if(((String)tp.getCtrls().get(i)).compareTo(name)==0)
				break;	
			}
			if(i<=tp.getCtrlIndex())
			lineCode+="if("+parentName+".getSelectedIndex()=="+tp.getTabIndexOfCtrl().get(i)+")\n\t\t{\n\t\t";	
		}
		if(tabIndex!=-1)
		lineCode+="\tif("+ tabName +".getSelectedIndex()=="+ tabIndex +")\n\t\t{\n\t";	
		lineCode+="\tg="+parentName+".getGraphics();\n\t";
		if(borderStyle!=0)
		{
			if(bcolor==true)
			lineCode+="\tg.setColor("+getRGB(color,true)+");\n\t";//Border Color
			else
			lineCode+="\tg.setColor(java.awt.SystemColor.windowText);\n\t";
		}
		else
		lineCode+="\tg.setColor(null);\n\t\t";//Border Color
		lineCode+="\tg.drawLine("+X1+","+Y1+","+X2+","+Y2+");\n\t";
		if(tflag)
		lineCode+="\t}\n\t";
		if(visible==false)
		lineCode+="\t}//End of Block for Line's Visibility\n\t\t";
		return(lineCode);
	}
	
 
 //returns error report
	public String getErrorReport()
	{
		return(errReport);
	}
 
 
 
	private StringBuffer replaceSlashByDoubleSlash(StringBuffer text)
	{
		int slpos=-2;
		while(true)
		{
				slpos=text.indexOf("\\",slpos+2);
				if(slpos==-1)
				break;
				text.insert(slpos+1,'\\');
		}	
		return(text);
	}	



	protected String getCaption(StringTokenizer s)
	{
		StringBuffer token=new StringBuffer(s.nextToken());
		int oldpos,newpos;
		oldpos=0;newpos=0;
		char charNxtToQuotes='\0';
		int quotesCounter=0;

		if(new String(token).startsWith("\"")==false)
		return(new String(token));
		else
		{
			quotesCounter++;
			token=replaceSlashByDoubleSlash(token);
			StringBuffer newToken;
			while(true)
			{
				newpos=token.indexOf("\"",newpos+1);//Find occurence of " after the pair of Quotes that has been found
				//True implies ending quote has not been found as yet and hence append the next token
				if(newpos==-1)
				{
					if(quotesCounter%2==0)
					{
						break;
					}
					token=token.append(' ');
					newToken=new StringBuffer(s.nextToken());
					newToken=replaceSlashByDoubleSlash(newToken);
					token=token.append(newToken);
					newpos=oldpos+1;//oldpos is reqd bcoz newpos gets overwritten when Quotes is not present
				
				}
				else
				{
					oldpos=newpos;
					quotesCounter++;
					if(newpos+1!=token.length())//true implies that the ending quotes has been found
					charNxtToQuotes=token.charAt(newpos+1);
					else
					{
						break;
					}

					if(charNxtToQuotes==' ')
					{
						break;
					}
					else if(charNxtToQuotes=='\"')
					{
						//Replacing the first out of the pair by \\,i.e;escape character for printing quotes in Java
						quotesCounter++;
						token.setCharAt(newpos,'\\');
						//Updating the new position so that search begins from the character after the pair of quotes
						newpos+=1;
					}

				}
			}
		}
		return(new String(token));
	}
}