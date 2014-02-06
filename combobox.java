import java.util.*;
import java.io.*;

// The combobox class comes into picture when there is VB.ComboBox in source form file.
  
class combobox extends control
{
	// This variable is used during parsing to store properties of VB.ComboBox
	private javax.swing.JComboBox jco;
	
	// This String variable is used to store the name of the source directory Containing the FRX file
	private String dir;
	
	// This String variable is used for storing the Location containing the number of elements of FRX
	private String numItemsLoc;
	
	// This String variable is used for storing the Location of the elements of Combobox
	private String ItemsLoc;
	
    // This Data element is used for storing the elements extracted from the FRX file
    private Vector comboItems;
    
    // This Data element is used for storing the number of items 
    private int numItems;
    
 	//used to check if backgorund color has been changed
	private boolean bflag;
	
 	//used to check if foreground color has been changed
	private boolean fflag;

	// This boolean Data element is used for detecting whether any Exception occurs during parsing
	private boolean eflag;
	
	// This boolean variable is stores information about Exception occuring while parsing for displaying to the user
	 private String errReport;
	 


	//constructor
	
	public combobox()
	{
	}
	
	public combobox(String directory)
	{
		dir=directory;
		errReport=new String();
	}
	
	
	// This method parses the input form file containing VB.ComboBox 
		public void setProperties(StringTokenizer s,int tabHeight)
	{
		
		 // This is used to store string Tokens and corresponding integer constants. 
		 HashTable h= new HashTable();
		
		// store height of the object
		int height=0;
		
		//store width of the object
		 int width=0;
	    
	    // store left of the object
		int left=0;
		
		//store top of the object
		 int top=0;
		
		// Temporary variable
		Integer val,val1;
		
		jco=new javax.swing.JComboBox();
		curToken=getCaption(s);

		jco.setName(curToken);
		
		//Default style for combobox allows editing
		jco.setEditable(true);
		
		//Handle the other properties of ComboBox
		while(curToken.compareTo("End")!=0)
		{
			
			curToken=s.nextToken();
			val=(Integer)h.hs.get(curToken);
			if(val==null)
			{
				//Handling Properties not present in HashTable for ComboBox
				errReport+="Couldnot map Property in ComboBox "+getName()+" :Property: "+curToken;
				curToken=getCaption(s);
				//Handling Properties not present in HashTable for ComboBox
				errReport+=": Value : "+curToken+"\n";
				curToken="";
				continue;
			}
			switch(val.intValue())
			{
				case 2://End
					System.out.println("Handled All Properties of ComboBox: "+getName());
					break;
				case 4://Caption
			
					curToken=getCaption(s);
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
						errReport+="Number Format Exception in ComboBox "+getName()+":Height property Value :"+curToken;		
						errReport+="Value: "+curToken+". Setting Height to 0.\n";
					}
					break;
			
				case 14://Width
					curToken=getCaption(s);
					try{
						width=Integer.parseInt(curToken);
						width=width/15;
					}
					catch(NumberFormatException Ne){
						width=0;//Default value of width is taken as zero
						eflag=true;		
						errReport+="Number Format Exception in ComboBox "+getName()+":Width property Value :"+curToken;		
						errReport+="Value: "+curToken+". Setting Width to 0.\n";
					}
					jco.setSize(width,height);//Default value of width is taken as zero
					break;
				
				case 12://Left
					curToken=getCaption(s);
					try{
						left=Integer.parseInt(curToken);
						// If Tabbed pane object with -ve coordinates
						if(left<0)
						left+=75000;
						//else
						left=left/15;
					}
				catch(NumberFormatException Ne){
					left=0;//Default value of left is taken as zero
					eflag=true;		
					errReport+="Number Format Exception in ComboBox "+getName()+":Left property Value :"+curToken;		
 					errReport+="Value: "+curToken+". Setting Width to 0.\n";
				}
				break;
			
				case 13://Top
					curToken=getCaption(s);
					try{
						top=Integer.parseInt(curToken);
						top=top/15;
						jco.setLocation(left,top-tabHeight);
					}
					catch(NumberFormatException Ne){
						top=0;//Default Value of Top in case of Exception is taken as zero
						jco.setLocation(left,top);
						eflag=true;				
						errReport+="Number Format Exception in ComboBox "+getName()+":Top property Value :"+curToken;		
						errReport+="Value: "+curToken+". Setting Top to 0.\n";
					}
					break;
				
				//This property points to the location where the Number of elements in the combobox can be found out
				case 82://ItemData
					curToken = getCaption(s);
        			numItemsLoc = new String(curToken);
        			break;
        		
        		case 55://ToolTipText
					curToken=getCaption(s);
	            	jco.setToolTipText(curToken); 		
	            	break;
	        	
	        	case 57://Visible
	        		curToken=getCaption(s);
	        		jco.setVisible(false);
	        		break;
	        
	        	case 52://MousePointer
	        		curToken=getCaption(s);
	        		try{
	        			mptype=Integer.parseInt(curToken);
	        		}
	        		catch(NumberFormatException Ne){
						mptype=1;//Value to be used in case of Exception
						eflag=true;				
						errReport+="Number Format Exception in ComboBox "+getName()+": MousePointer property Value :"+curToken;		
						errReport+="Value: "+curToken+". Setting Width to 0.\n";
					}
	        		break;
	       	
	       		case 58://List
					curToken = getCaption(s);
        			ItemsLoc = new String(curToken);
        			try	
        			{
            			comboItems = extractElementsFromFRX();
            		
        			}
        			catch(FileNotFoundException Fe)
        			{
	               		errReport+="Image file couldnot be found in ComboBox "+getName()+":List property";
        	    	}
        	    	catch(IOException Fe)
        			{
	               		errReport+="Image file couldnot be found in ComboBox "+getName()+":Picture property";
        	    	}
					break;
			
				case(48)://BackColor
					bflag=true;
					curToken=getCaption(s);
					curToken=curToken.substring(2,10);
					// If //System color
					try{
						if(Integer.parseInt(curToken.substring(0,2),10)==80)
						setForecolor(jco,Integer.parseInt(curToken.substring(2,8),16),false);
						// Else RGB color	
						else
						{
							int Br,Bb,Bg;
							try{
								Bb=Integer.parseInt(curToken.substring(2,4),16);
							}
							catch(NumberFormatException Ne)
							{
								Bb=0;//Setting default to Minimum Blue
								eflag=true;				
								errReport+="Number Format Exception in ComboBox "+getName()+":BackColor(Blue Component): Value :"+curToken+"\n";		
								errReport+="Setting to 0.\n";
							}
							try{
								Bg=Integer.parseInt(curToken.substring(4,6),16);
							}
							catch(NumberFormatException Ne)
							{
								Bg=0;//Setting default to Minimum green
								eflag=true;				
								errReport+="Number Format Exception in ComboBox "+getName()+": BackColor(Green Component) property: Value :"+curToken+"\n";			
								errReport+="Setting to 0.\n";
							}							
							try{
								Br=Integer.parseInt(curToken.substring(6,8),16);
							}
							catch(NumberFormatException Ne)
							{							
								Br=0;//Setting default to Minimum red
								eflag=true;				
								errReport+="Number Format Exception in ComboBox "+getName()+":BackColor(Red Component) property Value :"+curToken+"\n";			
								errReport+="Value: "+curToken+". Setting Width to 0.\n";
							}	
							jco.setBackground(new java.awt.Color(Br,Bg,Bb));
						}
					}
					catch(NumberFormatException Ne){//Catch For outer IF 
						setForecolor(jco,0,false);
						eflag=true;				
						errReport+="Number Format Exception in ComboBox "+getName()+" BackColor(//System Color) property: Value :"+curToken+"\n";		
						errReport+="Setting BackColor to ScrollBar Color.\n";
					}
					break;	
				case(59)://ForeColor
					fflag=true;
					curToken=getCaption(s);
					curToken=curToken.substring(2,10);
					try{
						// If //System color
						if(Integer.parseInt(curToken.substring(0,2),10)==80)
						setForecolor(jco,Integer.parseInt(curToken.substring(2,8),16),true);
						// Else RGB color	
						else
						{
							int Br,Bb,Bg;
							try{
								Bb=Integer.parseInt(curToken.substring(2,4),16);
							}
							catch(NumberFormatException Ne)
							{
								Bb=0;
								eflag=true;				
								errReport+="Number Format Exception in ComboBox "+getName()+":ForeColor(Blue Component) property Value :"+curToken+"\n";		
								errReport+="Setting Red Value to 0.\n";
							}	
							try{
								Bg=Integer.parseInt(curToken.substring(4,6),16);
							}
							catch(NumberFormatException Ne)
							{							
								Bg=0;
								eflag=true;				
								errReport+="Number Format Exception in ComboBox "+getName()+":ForeColor(Green Component) property Value :"+curToken+"\n";		
								errReport+="Setting Green Value to 0.\n";
							}
							try{
								Br=Integer.parseInt(curToken.substring(6,8),16);
							}
							catch(NumberFormatException Ne)
							{
				
								Br=0;
								eflag=true;				
								errReport+="Number Format Exception in ComboBox "+getName()+":ForeColor(Red Component) property Value :"+curToken+"\n";		
								errReport+="Setting Red Value to 0.\n";
							}
							jco.setForeground(new java.awt.Color(Br,Bg,Bb));
						}
					}
					catch(NumberFormatException Ne)
					{						
						setForecolor(jco,8,true);
						eflag=true;				
						errReport+="Number Format Exception in ComboBox "+getName()+":ForeColor(//System Color) property Value :"+curToken;		
						errReport+="Setting ForeColor to windowText Color.";
					}							
					break;
				
				case 53://Style
					curToken=getCaption(s);
					try{
						if(Integer.parseInt(curToken)==2)
						jco.setEditable(false);
						if(Integer.parseInt(curToken)==1)
						System.out.println("ComboBox:"+jco.getName()+":Style:"+curToken+" not supported");
					}
					catch(NumberFormatException Ne)
					{
						errReport+="Number Format Exception in ComboBox "+getName()+":style property Value :"+curToken+"\n";		
						errReport+="Setting Style to 0.\n";
						jco.setEditable(true);
						eflag=true;
					}
					break;
			
				case 50://Enabled
					curToken=getCaption(s);
					jco.setEnabled(false);
					break;
			
				case 37://BeginProperty
					curToken=getCaption(s);
					val1=(Integer)h.hs.get(curToken);
					if(val==null)
					{
						errReport+="Couldnot map Property in ComboBox "+getName()+" :Inside BeginProperty: :Property: "+curToken;
						curToken=getCaption(s);
						errReport+=": Value : "+curToken+"\n";
						curToken="";
						while(curToken.compareTo("EndProperty")!=0)
						{
							curToken=getCaption(s);
						}
						continue;
					}
					switch(val1.intValue())
					{
						case 39://Font
						f.setFontProperty(s);		
						break;	
					}
					break;
			default:
				errReport+="Couldnot map Property in ComboBox "+getName()+" : :Property: "+curToken;
				curToken=getCaption(s);
				errReport+=": Value :"+curToken+"\n";
				break;
			}
		}
	}
	

	public String getDeclaration()
	{
		return("javax.swing.JComboBox "+jco.getName()+";\n\t");
	}
	

	public String getName()
	{
		return(jco.getName());	
	}
	

// This method extracts elements from the FRX file for the combobox object for which it is called. 

   private Vector extractElementsFromFRX() throws FileNotFoundException, IOException
    {
        StringTokenizer sl = new StringTokenizer(numItemsLoc,":\n\t");
		String name=sl.nextToken();
		String hexValOfAddr=sl.nextToken();
		int addr = Integer.parseInt(hexValOfAddr,16);
		name=name.substring(1,name.length()-1);
		RandomAccessFile fis=new RandomAccessFile(dir+"/"+name,"r");
		long n=fis.length();
		String binaryNumItemsHi = new String();
		String binaryNumItemsLo = new String();
		String binaryNumItems = new String();
		fis.seek(addr);
		numItems=fis.read();//LSB
		binaryNumItemsLo=Integer.toBinaryString(numItems);
		addr++;
		numItems=fis.read();//MSB
		binaryNumItemsHi=Integer.toBinaryString(numItems);
		binaryNumItems=binaryNumItemsHi + binaryNumItemsLo;
		numItems=Integer.parseInt(binaryNumItems,2);
		int[] itemIndex = new int[numItems];//To Store the Indexes of Items i.e;ItemData
		int numDigitsInIndex;
		String binaryNumDigitsInIndexHi=new String();//Used to Store Binary rep of Hi part of numDigitsInIndex
		String binaryNumDigitsInIndexLo=new String();//Used to Store Binary rep of Lo part of numDigitsInIndex
		String binaryNumDigitsInIndex=new String();//Used to Store Full Binary rep of part of numDigitsInIndex
		addr+=2;
		int j;
		int Index;
		for(int i=0;i<numItems;i++)
		{
			addr++;
			fis.seek(addr);
			numDigitsInIndex=fis.read();//LSB 
			binaryNumDigitsInIndexLo=Integer.toBinaryString(numDigitsInIndex);
			addr++;
			fis.seek(addr);
			numDigitsInIndex=fis.read();//MSB
			binaryNumDigitsInIndexHi=Integer.toBinaryString(numDigitsInIndex);
			binaryNumDigitsInIndex=binaryNumDigitsInIndexHi+binaryNumDigitsInIndexLo;
			numDigitsInIndex=Integer.parseInt(binaryNumDigitsInIndex,2);
			itemIndex[i]=0;
			for(j=0;j<numDigitsInIndex;j++)
			{
				addr++;
				fis.seek(addr);
				Index = fis.read();
				itemIndex[i]+=(Index - 48)*(int)Math.pow(10.0,(double)(numDigitsInIndex - 1 - j));
			}
		}
        //The Indexes for the Items has been obtained
		StringTokenizer s2 = new StringTokenizer(ItemsLoc,":\n\t");
		s2.nextToken();
		hexValOfAddr=s2.nextToken();
		addr=Integer.parseInt(hexValOfAddr,16);
		addr+=3;//The next Two address locations give the number of characters in first memory location
		Vector v=new Vector(numItems);
		int flag=0,numChars=0;//numChars stores the number of characters for each item
		String binaryNumCharsHi=new String();//Used to Store Binary rep of numCharsHi Part
		String binaryNumCharsLo=new String();//Used to Store Binary rep of numCharsLow Part
		String binaryNumChars=new String();//Used to Store Full Binary rep of numChars 
		for(int item=0;item<numItems;item++)
		{
			addr++;
			fis.seek(addr);
			numChars=fis.read();
			binaryNumCharsLo=Integer.toBinaryString(numChars);
			addr++;
			fis.seek(addr);
			numChars=fis.read();
			binaryNumCharsHi=Integer.toBinaryString(numChars);
			binaryNumChars=binaryNumCharsHi+binaryNumCharsLo;
			numChars=Integer.parseInt(binaryNumChars,2);
			byte[] ele=new byte[numChars+1];
			for(int i=0;i<=numChars;i++)
			{
				ele[i]=fis.readByte();
				addr++;
				fis.seek(addr);
			}
			byte[] dele = new byte[numChars+1];
			for(int i=0;i<numChars;i++)
			dele[i]=ele[i+1];
			String comboElement=new String(dele);
			v.add(comboElement.trim());
			addr--;
		}
		fis.close();	
		return v;
	}
	
   // This method generates the code for jcomboBox object
	public String getCode()
	{
		String code="";
		String name="",curToken="";
		name=jco.getName();
		code+=name+"=new JComboBox();\n\t\t";
		code+=name+".setLocation("+jco.getX()+","+jco.getY()+");\n\t\t";
		code+=name+".setSize("+(jco.getWidth()+3)+","+jco.getHeight()+");\n\t\t";
		for(int i = 0; i < numItems; i++)
        code+=name+".addItem(\"" + comboItems.get(i) + "\");\n\t\t";
		if(bflag==true)
		{
			String color = jco.getBackground().toString();			
			code+=name+".setBackground("+getRGB(color,false)+");\n\t\t";
		}
		if(fflag==true)
		{		
			code+=name+".setForeground("+getRGB(jco.getForeground().toString(),true)+");\n\t\t";
		}
	
		code+=name+".setFont("+f.getFontCode()+");\n\t\t";
		if(mptype!=0)
		{
			String mp=new String();
			mp=getMousePointer(mptype);
			if(mp!=null)
			code+=name+".setCursor(new Cursor("+mp+"));\n\t\t";
		}
		if(jco.getToolTipText()!=null)
		code+=name+".setToolTipText("+jco.getToolTipText()+");\n\t\t";
		if(jco.isEditable()==true)
		code+=name+".setEditable(true);\n\t\t";
		if(jco.isVisible()==false)
		code+=name+".setVisible(false);\n\t\t";
		if(jco.isEnabled()==false)
		code+=name+".setEnabled(false);\n\t\t";
		return (code);
	}
	

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
				newpos=token.indexOf("\"",newpos+1);
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
					if(newpos+1!=token.length())
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