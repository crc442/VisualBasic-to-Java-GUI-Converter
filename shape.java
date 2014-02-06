import java.util.*;

//The checkbox class comes into picture when there is VB.Shape in source form file.
class shape extends control
{
	
	//This variable is used to store the type of shape of the VB.Shape.
	private int shape;
	
	//This variable is used to store the Fill Style of the Shape control
	private int FillStyle;
	
	//This variable is used to store the Location and Size properties of the Shape control
	private javax.swing.JLabel sh;
	
	
	//This variable is used to store the Border properties of the Shape control
	private javax.swing.JLabel shBorder;
			

	//This variable is used to store the Border Style information of the Shape control
	private int borderStyle;
	
	//This variable is used to store the Visibility information of the Shape control
	private boolean visible;
	
	//This variable is used to store the BackStyle information of the Shape control
	private boolean bkstyle;	
	

	// This boolean Data element is used for detecting whether any Exception occurs during parsing.
	private boolean eflag;
	

	//This boolean variable is stores information about Exception occuring while parsing for displaying to the user
	private String errReport;


	//This is a default Null argument constructor
	public shape()
	{
		borderStyle=1;
		sh=new javax.swing.JLabel();
		shBorder=new javax.swing.JLabel();
		shBorder.setForeground(java.awt.SystemColor.windowText);//Default Border Color
		FillStyle=1;
		visible=true;
	}
	
	
    public void setProperties(StringTokenizer s,int tabHeight)
	{
		String curToken=new String();
		curToken=getCaption(s);
		sh.setName(curToken);
		
		 
		// This is used to store string Tokens and corresponding integer constants. 
		HashTable h= new HashTable();
		

		Integer val,val1;		//temperory variables
		int height=0,width=0,top=0,left=0;
		
		
		while(curToken.compareTo("End")!=0)
		{
			curToken=getCaption(s);
			val=(Integer)h.hs.get(curToken);
			if(val==null)
			{
				errReport+="Couldnot map Property in Shape "+getName()+" :Property: "+curToken;
				curToken=getCaption(s);

				errReport+=": Value : "+curToken+"\n";
				curToken="";
				continue;
			}
			switch(val.intValue())
			{
			
				case 2://End
					System.out.println("Handled All Properties of Shape: "+getName());
					break;
					
				case 11://Height
					curToken=getCaption(s);
					try{
						height=Integer.parseInt(curToken);
						height=height/15;
					}
					catch(NumberFormatException Ne){
						height=0;
						eflag=true;
						errReport+="Number Format Exception in Shape "+getName()+":Height property Value :"+curToken;		
						errReport+="Value: "+curToken+". Setting Height to 0.\n";
					}
					break;
			
				case 14://Width
					curToken=getCaption(s);
					try{
						width=Integer.parseInt(curToken);
						width=width/15;
						sh.setSize(width+1,height+1);
					}
					catch(NumberFormatException Ne){
						width=0;//Default value of width is taken as zero
						eflag=true;		
						errReport+="Number Format Exception in Shape "+getName()+":Width property Value :"+curToken;		
						errReport+="Value: "+curToken+". Setting Width to 0.\n";
					}
					break;
			
				case 12://Left
					curToken=getCaption(s);
					try{
						left=Integer.parseInt(curToken);
						if(left<0)
						left+=75000;
						left=left/15;
					}
					catch(NumberFormatException Ne){
					left=0;//Default value of left is taken as zero
					eflag=true;		
					errReport+="Number Format Exception in Shape "+getName()+":Left property Value :"+curToken;		
 					errReport+="Value: "+curToken+". Setting Width to 0.\n";
				}
					break;
			
			
				case 13://Top
					curToken=getCaption(s);
					try{
						top=Integer.parseInt(curToken);
						top=top/15;
						//top+=22;//to take care of title height
						sh.setLocation(left,top-tabHeight);
					}
					catch(NumberFormatException Ne){
						
						top=0;//Default Value of Top in case of Exception is taken as zero
						sh.setLocation(left,top);
						eflag=true;				
						errReport+="Number Format Exception in Shape "+getName()+":Top property Value :"+curToken;		
						errReport+="Value: "+curToken+". Setting Top to 0.\n";
					}
					break;
				
				
				
				case 74://Shape
					curToken=getCaption(s);
					try{
						shape=Integer.parseInt(curToken);
					
					}
					catch(NumberFormatException Ne){
						shape=0;
						eflag=true;
					}
					break;
			
				case 73://FillStyle
					curToken=getCaption(s);
					try{
						FillStyle=Integer.parseInt(curToken);
					}
					catch(NumberFormatException Ne){
						System.out.println("Number Format Exception in Shape "+getName()+":FillStyle property");	
						System.out.println("Setting FillStyle to transparent.");
						FillStyle=1;
						eflag=true;
					}
					break;
			
				case 92://Backstyle
					curToken=getCaption(s);
					bkstyle=true;
					break;
				
				case 57://Visible
					curToken=getCaption(s);
					visible=false;
					break;
				
				case 72://FillColor
					curToken=getCaption(s);
					curToken=curToken.substring(2,10);
					try{
						// If System color
						if(Integer.parseInt(curToken.substring(0,2),10)==80)
						setForecolor(sh,Integer.parseInt(curToken.substring(2,8),16),true);
						// Else RGB color	
						else
						{
							int Fb,Fg,Fr;
							try{
								Fb=Integer.parseInt(curToken.substring(2,4),16);
							}
							catch(NumberFormatException Ne){
								Fb=0;//Setting default to Minimum red
								eflag=true;				
							}	
							try{
								Fg=Integer.parseInt(curToken.substring(4,6),16);
							}
							catch(NumberFormatException Ne){
								Fg=0;//Setting default to Minimum Green
								eflag=true;				
							}
							try{
								Fr=Integer.parseInt(curToken.substring(6,8),16);
							}
							catch(NumberFormatException Ne){
								Fr=0;//Setting default to Minimum red
								eflag=true;				
							}
							sh.setForeground(new java.awt.Color(Fr,Fg,Fb));
						}
					}
					catch(NumberFormatException Ne){//Catch For Outer IF
						System.out.println("Setting FillColor to ScrollBar Color.");
						setForecolor(sh,15,true);//Setting default to control i.e; Black color
						eflag=true;				
					}		
					break;
			
				case 93://BorderColor
					curToken=getCaption(s);
					curToken=curToken.substring(2,10);
					try{
						// If System color
						if(Integer.parseInt(curToken.substring(0,2),10)==80)
						setForecolor(shBorder,Integer.parseInt(curToken.substring(2,8),16),true);
						// Else RGB color	
						else
						{
							int Fb,Fg,Fr;
							try{
								Fb=Integer.parseInt(curToken.substring(2,4),16);
							}
							catch(NumberFormatException Ne){
								System.out.println("Number Format Exception in Shape "+getName()+":BorderColor(Blue Component) property");
								System.out.println("Setting Blue value to 0");
								Fb=0;//Setting default to Minimum Blue
								eflag=true;				
							}
							try{
								Fg=Integer.parseInt(curToken.substring(4,6),16);
							}
							catch(NumberFormatException Ne){
								System.out.println("Number Format Exception in Shape "+getName()+":BorderColor(Green Component) property");
								System.out.println("Setting Green value to 0");
								Fg=0;//Setting default to Minimum Green
								eflag=true;				
							}
							try{
								Fr=Integer.parseInt(curToken.substring(6,8),16);
							}
							catch(NumberFormatException Ne){
								System.out.println("Number Format Exception in Shape "+getName()+":BorderColor(Red Component) property");
								System.out.println("Setting Red value to 0");
								Fr=0;//Setting default to Minimum red
								eflag=true;				
							}
							shBorder.setForeground(new java.awt.Color(Fr,Fg,Fb));
						}
					}
					catch(NumberFormatException Ne){//Catch For Outer IF
						System.out.println("Number Format Exception in Shape "+getName()+":BorderColor(System Color) property");
						System.out.println("Setting BorderColor to Black Color.");
						setForecolor(sh,8,true);//Setting default to windowtext i.e; Black color
						eflag=true;				
					}		
					break;
					
			
				case 65://Borderstyle
					curToken=getCaption(s);
					try{
						borderStyle=Integer.parseInt(curToken);
					}
					catch(NumberFormatException Ne){
						System.out.println("Number Format Exception in Shape "+getName()+":BorderStyle property");
						System.out.println("Setting BorderStyle to 1");
						borderStyle=1;
						eflag=true;				
					}
					break;
					
					
				case 48://BackColor
					curToken=getCaption(s);
					System.out.println("BackColor: "+curToken);
					curToken=curToken.substring(2,10);
					System.out.println("Color: "+curToken);
					try{
						// If System color
						if(Integer.parseInt(curToken.substring(0,2),10)==80)
						setForecolor(sh,Integer.parseInt(curToken.substring(2,8),16),false);
						// Else RGB color	
						else
						{
							int Br,Bb,Bg;
							try{
								Bb=Integer.parseInt(curToken.substring(2,4),16);
							}
							catch(NumberFormatException Ne){
								System.out.println("Number Format Exception in Shape "+getName()+":BackColor(Blue Component) property");
								Bb=0;//Setting default to Minimum Blue
								eflag=true;				
							}
							try{
								Bg=Integer.parseInt(curToken.substring(4,6),16);
							}
							catch(NumberFormatException Ne){
								System.out.println("Number Format Exception in Shape "+getName()+":BackColor(Green Component) property");
								Bg=0;//Setting default to Minimum green
								eflag=true;				
							}
							try{
								Br=Integer.parseInt(curToken.substring(6,8),16);
							}
							catch(NumberFormatException Ne){
								System.out.println("Number Format Exception in Shape "+getName()+":BackColor(Red Component) property");
								Br=0;//Setting default to Minimum red
								eflag=true;				
							}	
							sh.setBackground(new java.awt.Color(Br,Bg,Bb));
						}
					}
					catch(NumberFormatException Ne){//Catch For outer IF 
						System.out.println("Number Format Exception in Shape "+getName()+":BackColor(System Color) property");
						System.out.println("Setting BackColor to ScrollBar Color.");
						setForecolor(sh,0,false);//Setting default to Scrollbar color
						eflag=true;				
					}
					break;
					
			default://This case is for handling those properties that are not defined in HashTable
				System.out.println("Inside Default case in Shape :"+getName()+": Property: "+curToken);
				curToken=getCaption(s);
				System.out.print("Inside Default case in Shape :"+getName()+" : Value: "+curToken);
				break;
			}
		}
	}
	
	//This function is used during the code generation process
	public String getName()
	{
		return(sh.getName());	
	}

	
	//This function is used during the code generation process.It returns 'Declaration' for the JLabel object containing the Shape.
   	public String getDeclaration()
	{
		return("javax.swing.JLabel "+sh.getName()+";\n\t");	
	}
	
	
	//This method generates the code for JLabel object.
  	public String getCode()
	{
		String code=new String();
		String name=new String(sh.getName());
		code+=name+"=new JLabel();\n\t\t";
		code+=name+".setLocation("+sh.getX()+","+sh.getY()+");\n\t\t";
		code+=name+".setSize("+(sh.getWidth()+3)+","+sh.getHeight()+");\n\t\t";	
		return(code);
	}


   //This method generates the code for shape
  	public String getShapeCode(compositeObject co,int tabIndex,String tabName) 
	{
		String shapeCode=new String();
		String name=new String(sh.getName());
		String color = shBorder.getForeground().toString();	
		boolean tflag=false;
		int height=sh.getHeight();
		int width=sh.getWidth();
		if(co.getClass().toString().compareTo("class tabbedpane")==0)
		tflag=true;
		if(visible==false)
		shapeCode+="if("+visible+")//Start of block for Shape's Visibility\n\t\t{\n\t\t";
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
			shapeCode+="\tif("+tp.getName()+".getSelectedIndex()=="+tp.getTabIndexOfCtrl().get(i)+")\n\t\t{\n\t\t";	
		}
		if(tabIndex!=-1)
		shapeCode+="\tif("+ tabName +".getSelectedIndex()=="+ tabIndex +")\n\t\t{\n\t";	
		shapeCode+="\tg="+name+".getGraphics();\n\t";
		if(borderStyle!=0)
		shapeCode+="\tg.setColor("+getRGB(color,true)+");\n\t\t";//Border Color
		else
		shapeCode+="\tg.setColor(null);\n\t\t";//Border Color
		if(shape==1||shape==3||shape==5)
		{
				System.out.println("Shape: "+shape+" Fillstyle: ");
				if(width<height)
				height=width;
				else
				width=height;
		}
		switch(shape)
		{
			case 0://Rectangle
					if(borderStyle!=0)
					shapeCode+="g.drawRect("+0+","+0+","+width+","+height+");\n\t\t";
					if(bkstyle==true)
					{
						color = sh.getBackground().toString();				
						shapeCode+="g.setColor("+getRGB(color,false)+");\n\t\t";//For FillColor
						shapeCode+="g.fillRect("+0+","+0+","+width+","+height+");\n\n\t";	
					}
					if(FillStyle!=1)//Transparent
					{

						color = sh.getForeground().toString();				
						shapeCode+="g.setColor("+getRGB(color,false)+");\n\t\t";//For FillColor
						shapeCode+="g.fillRect("+0+","+0+","+width+","+height+");\n\n\t";
					}
					break;
					
			case 1://Square
					if(borderStyle!=0)
						shapeCode+="g.drawRect("+0+","+0+","+width+","+height+");\n\t\t";
					if(bkstyle==true)
					{
						color = sh.getBackground().toString();				
						shapeCode+="g.setColor("+getRGB(color,false)+");\n\t\t";//For FillColor
						shapeCode+="g.fillRect("+0+","+0+","+width+","+height+");\n\n\t";	
					}
					if(FillStyle!=1)//Transparent
					{
						color = sh.getForeground().toString();				
						shapeCode+="g.drawRect("+0+","+0+","+width+","+height+");\n\t\t";
						shapeCode+="g.setColor("+getRGB(color,false)+");\n\t\t";
						shapeCode+="g.fillRect("+0+","+0+","+width+","+height+");\n\n\t";
					}
					break;
					
			case 2://Oval
					if(borderStyle!=0)
					shapeCode+="g.drawOval("+0+","+0+","+width+","+height+");\n\t\t";	
					if(bkstyle==true)
					{
						color = sh.getBackground().toString();				
						shapeCode+="g.setColor("+getRGB(color,false)+");\n\t\t";
						shapeCode+="g.fillOval("+0+","+0+","+width+","+height+");\n\n\t";
					}
					if(FillStyle!=1)//Transparent
					{
						shapeCode+="g.drawOval("+0+","+0+","+width+","+height+");\n\t\t";
						color = sh.getForeground().toString();				
						shapeCode+="g.setColor("+getRGB(color,false)+");\n\t\t";
						shapeCode+="g.fillOval("+0+","+0+","+width+","+height+");\n\n\t";
					}
					break;
					
			case 3://Circle
					if(borderStyle!=0)
					shapeCode+="g.drawOval("+0+","+0+","+width+","+height+");\n\t\t";
					if(bkstyle==true)
					{
						color = sh.getBackground().toString();				
						shapeCode+="g.setColor("+getRGB(color,false)+");\n\t\t";
						shapeCode+="g.fillOval("+0+","+0+","+width+","+height+");\n\n\t";
					}
					if(FillStyle!=1)//Transparent
					{
						shapeCode+="g.drawOval("+0+","+0+","+width+","+height+");\n\t\t";
						color = sh.getForeground().toString();				
						shapeCode+="g.setColor("+getRGB(color,false)+");\n\t\t";
						shapeCode+="g.fillOval("+0+","+0+","+width+","+height+");\n\n\t";
					}
					break;
					
			case 4://Rounded Rectangle
					if(borderStyle!=0)
					shapeCode+="g.drawRoundRect("+0+","+0+","+width+","+(height-2)+",20,20);\n\t\t";
					if(bkstyle==true)
					{
						color = sh.getBackground().toString();				
						shapeCode+="g.setColor("+getRGB(color,false)+");\n\t\t";
						shapeCode+="g.fillRoundRect("+0+","+0+","+width+","+(height-4)+",20,20);\n\n\t";
					}
					if(FillStyle!=1)//Transparent
					{
						shapeCode+="g.drawRoundRect("+0+","+0+","+width+","+(height-2)+",20,20);\n\t\t";
						color = sh.getForeground().toString();				
						shapeCode+="g.setColor("+getRGB(color,false)+");\n\t\t";
						shapeCode+="g.fillRoundRect("+0+","+0+","+width+","+(height-4)+",20,20);\n\n\t";
					}
					break;
					
			case 5://Rounded Square
					if(borderStyle!=0)
					shapeCode+="g.drawRoundRect("+0+","+0+","+width+","+height+",20,20);\n\t\t";	
					if(bkstyle==true)
					{
						color = sh.getBackground().toString();				
						shapeCode+="g.setColor("+getRGB(color,false)+");\n\t\t";
						shapeCode+="g.fillRoundRect("+0+","+0+","+width+","+height+",20,20);\n\n\t";
					}
					if(FillStyle!=1)//Transparent
					{
						shapeCode+="g.drawRoundRect("+0+","+0+","+width+","+height+",20,20);\n\t\t";
						color = sh.getForeground().toString();				
						shapeCode+="g.setColor("+getRGB(color,false)+");\n\t\t";
						shapeCode+="g.fillRoundRect("+0+","+0+","+width+","+height+",20,20);\n\n\t";
					}
					break;
			}
			if(tflag==true)
			shapeCode+="\t}\n\t";
			if(tabIndex!=-1)
			shapeCode+="\t}\n\t";
			if(visible==false)
			shapeCode+="\t}//End of block for Shape Visibility\n\t\t";

			return(shapeCode);
	}

 
   // This method returns the string errReport

	public String getErrReport()
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

   
   // This method is based on the idea that the ending Quotes is present at the end of the token.If extra Quotes are Present then they are present in pairs
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

					newpos=oldpos+1;
					
				}
				else
				{
					oldpos=newpos;
					quotesCounter++;
					if(newpos+1!=token.length())//IF newpos+1 == tokenlength, it implies that the ending quotes has been found
					charNxtToQuotes=token.charAt(newpos+1);
					else
					{
						break;
					}

					if(charNxtToQuotes==' ')
					{
					//	System.out.println("Char before breaking loc 1: "+token.charAt(newpos-1));
						break;
					}
					else if(charNxtToQuotes=='\"')
					{
						quotesCounter++;
						token.setCharAt(newpos,'\\');
						newpos+=1;
					}
				}
			}
		}

		
				return(new String(token));
	}	
}
