import java.io.*;

// The checkbox class comes into picture when there is VB.CheckBox in source form file.
 
class checkbox extends control
{
  	// This variable is used during parsing to store properties of VB.CheckBox.
	
	private javax.swing.JCheckBox jc;
	
   	// It is  used to store source directory.
     	
	private String srcdir;
   	// It is  used to store destination directory.
    
	private String destdir;
	
  	// This Boolean variable is to check whether Background colour has been modified
	
	private boolean bflag;
	
  	// This Boolean variable is to check whether Foreground colour has been modified
	
	private boolean fflag;
  
	// This Boolean variable is to check whether Alignment property of checkbox has been changed from default value
	
	private boolean alignment;
	
  	// This Character variable is used to store the Mnemonic Character 
	
	private char mnemonic;
	
  	// This boolean variable is used to store information about appearance of control i.e; 3D or Flat 
	
	private boolean appearance;
	
  	// This boolean variable is used to store information about style of control i.e; checkbox or togglebutton 
	
	private boolean style;
	
 	// This boolean variable is used to store information about Exception occuring while parsing 
	
	private boolean eflag;
	
  	// This boolean variable is stores information about Exception occuring while parsing for displaying to the user
	
 	private String errReport;
 	
   	// This is a default null argument constructor

	public checkbox()
	{
		mnemonic='\0';
		errReport=new String();
	}

   	// This constructor takes source and destination directory.It is used in parsing phase. 	
    public checkbox(String srcdirectory, String destdirectory)
    {
    	srcdir = srcdirectory;
		destdir = destdirectory;
		//Initialization
		jc=new javax.swing.JCheckBox();
		errReport=new String();
	}	
  
    // This method is called whenever VB.CheckBox appears in the frm file.
   
	public void setProperties(java.util.StringTokenizer s,int tabHeight)
	{
		
		 /** 
		 * This is used to store string Tokens and corresponding integer constants.
		 * These constants are used during parsing phase in the switch case. 
		 */
		HashTable h= new HashTable();
		
		// It is used to store height of the control object.		 
		int height=0;
		
		// It is used to store width of the control object.		 
	    int width=0;
	    
	    // It is used to store left of the control object.
		int left=0;
		
		// It is used to store top of the control object.
		int top=0;
		
		// Temporary variable
		Integer val,val1;
		curToken=getCaption(s);
		
		// Set the name of the checkbox
		jc.setName(curToken);
		
		//Handle the other properties
		while(curToken.compareTo("End")!=0)
		{
			curToken=getCaption(s);
			val=(Integer)h.hs.get(curToken);
			if(val==null)
			{
				errReport+="Couldnot map Property in CheckBox "+getName()+" :Property: "+curToken;
				curToken=getCaption(s);
				errReport+=": Value : "+curToken+"\n";
				curToken="";
				continue;
			}
			switch(val.intValue())
			{
				case 2://End
					System.out.println("Handled All Properties of CheckBox: "+getName());
					break;
				case 4://Caption
				curToken=getCaption(s);
				//Following Code is for extracting Mnemonic From the Caption
				if(curToken.lastIndexOf("&")!=-1)
				{
					mnemonic = curToken.charAt(curToken.lastIndexOf("&") + 1);
					String caption=new String();
					while(curToken.indexOf("&")!=-1)
					{
						curToken=curToken.substring(0,curToken.indexOf("&"))+curToken.substring(curToken.indexOf("&")+1,curToken.length());
					}
				}
				jc.setText(curToken);
				break;
				// For Height,Width,Left,Top values are converted from twips to pixel by dividing them by 15.
			
			case 11://Height
				curToken=getCaption(s);
				try{
					height=Integer.parseInt(curToken);
					height=height/15;
				}
				catch(NumberFormatException Ne){
					height=0;
					eflag=true;
					errReport+="Number Format Exception in CheckBox "+getName()+":Height property: Value: "+curToken+"\n";		
					errReport+="Setting Height to 0.\n";
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
					errReport+="Number Format Exception in CheckBox "+getName()+":Width property: Value: "+curToken+"\n";		
					errReport+="Setting Width to 0.\n";
				}
				jc.setSize(width,height);
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
					errReport+="Number Format Exception in CheckBox "+getName()+":Left property: Value :"+curToken+"\n";		
 					errReport+="Setting Left to 0.\n";
				}
				break;
			
			case 13://Top
				curToken=getCaption(s);
				try{
					top=Integer.parseInt(curToken);
					top=top/15;
					jc.setLocation(left,top-tabHeight);
				}
				catch(NumberFormatException Ne){
					top=0;//Default Value of Top in case of Exception is taken as zero
					jc.setLocation(left,top);
					eflag=true;				
					errReport+="Number Format Exception in CheckBox "+getName()+":Top property: Value : "+curToken+"\n";		
					errReport+="Setting Top to 0.\n";

				}
				break;
				
				//Index Appears in the case of Array of controls
							
			case 22://Index
				curToken=getCaption(s);
	            String nm=jc.getName();
	            nm+="_"+curToken;
	            jc.setName(nm); 		
			    break;
			
			case 47://Appearance i.e; 3D or Flat
				curToken=getCaption(s);
				//curToken=s.nextToken()
				appearance=true;
				break;
			
			case 52://MousePointer
				curToken=getCaption(s);
				try{
					mptype=Integer.parseInt(curToken);
				}
				catch(NumberFormatException Ne){
					mptype=1;//Value to be used in case of Exception
					eflag=true;				
					errReport+="Number Format Exception in CheckBox "+getName()+": MousePointer property: Value :"+curToken+"\n";		
					errReport+="Setting mousepointer to 1.\n";

				}
				break;
			
			case 55://ToolTipText
				curToken=getCaption(s);
	            jc.setToolTipText(curToken); 		
	            break;
	        
	        case 57://Visible
	        	curToken=getCaption(s);
	        	jc.setVisible(false);
	        	break;
	        
	        case 90://Alignment
	        	curToken=getCaption(s);
	        	alignment=true;
	        	break;
	        
	        case 91://Value
	        	curToken=getCaption(s);
	        	jc.setSelected(true);
	        	break;
	        
	        case 50://Enabled
	        	curToken=getCaption(s);
	        	jc.setEnabled(false);
	        	break;
	        
	        case(48)://BackColor
	        	bflag=true;
				curToken=getCaption(s);
				curToken=curToken.substring(2,10);
				try{
					// If //System color
					if(Integer.parseInt(curToken.substring(0,2),10)==80)
					setForecolor(jc,Integer.parseInt(curToken.substring(2,8),16),false);
					// Else RGB color	
					else
					{	
						int Br,Bb,Bg;
						try{
							Bb=Integer.parseInt(curToken.substring(2,4),16);
						}
						catch(NumberFormatException Ne){
							Bb=0;//Setting default to Minimum Blue
							eflag=true;				
							errReport+="Number Format Exception in CheckBox "+getName()+":BackColor property Value :"+curToken+"\n";		 
							errReport+="Setting Blue to 0.\n";
						}
						try{
							Bg=Integer.parseInt(curToken.substring(4,6),16);
						}
						catch(NumberFormatException Ne){
							Bg=0;//Setting default to Minimum green
							eflag=true;				
							errReport+="Number Format Exception in CheckBox "+getName()+": BackColor(Green Component) property Value :"+curToken+"\n";		 
							errReport+="Setting Green Value to 0.\n";
						}
						try{
							Br=Integer.parseInt(curToken.substring(6,8),16);
						}
						catch(NumberFormatException Ne){
							Br=0;//Setting default to Minimum red
							eflag=true;				
							errReport+="Number Format Exception in CheckBox "+getName()+":BackColor(Red Component) property Value :"+curToken+"\n";		 
							errReport+="Setting Red value to 0.\n";
						}	
						jc.setBackground(new java.awt.Color(Br,Bg,Bb));
					}
				}
				catch(NumberFormatException Ne){//Catch For outer IF 
					setForecolor(jc,0,false);//Setting default to Scrollbar color
					eflag=true;				
					errReport+="Number Format Exception in CheckBox "+getName()+" BackColor(//System Color) property : Value :"+curToken+"\n";		 
					errReport+="Setting BackColor to ScrollBar Color.\n";
				}
			break;	
			
			case(59)://ForeColor
				fflag=true;
				curToken=getCaption(s);
				curToken=curToken.substring(2,10);
				// If //System color
				try{
					if(Integer.parseInt(curToken.substring(0,2),10)==80)
					setForecolor(jc,Integer.parseInt(curToken.substring(2,8),16),true);
					// Else RGB color	
					else
					{
						int Br,Bb,Bg;
						try{
							Bb=Integer.parseInt(curToken.substring(2,4),16);
						}
						catch(NumberFormatException Ne){
							Bb=0;//Setting default to Minimum red
							eflag=true;				
							errReport+="Number Format Exception in CheckBox "+getName()+":ForeColor(Blue Component) property: Value :"+curToken+"\n";		
							errReport+="Setting Blue to 0.\n";
						}	
						try{
							Bg=Integer.parseInt(curToken.substring(4,6),16);
						}
						catch(NumberFormatException Ne){
							Bg=0;//Setting default to Minimum Green
							eflag=true;				
							errReport+="Number Format Exception in CheckBox "+getName()+":ForeColor(Green Component) property: Value :"+curToken+"\n";		
							errReport+="Setting Green Value to 0.\n";
						}	
						try{						
							Br=Integer.parseInt(curToken.substring(6,8),16);
						}
						catch(NumberFormatException Ne){
							Br=0;//Setting default to Minimum red
							eflag=true;				
							errReport+="Number Format Exception in CheckBox "+getName()+":ForeColor(Red Component) property: Value :"+curToken+"\n";		
							errReport+="Setting Red value to 0.\n";
						}	
						jc.setForeground(new java.awt.Color(Br,Bg,Bb));
					}
				}
				catch(NumberFormatException Ne){//Catch For Outer IF
					setForecolor(jc,8,true);//Setting default to windowtext i.e; Black color
					eflag=true;				
					errReport+="Number Format Exception in CheckBox "+getName()+":ForeColor(//System Color) propertyValue :"+curToken;		
					errReport+="Value: "+curToken+". Setting Width to 0.\n";
				}						
				break;
			case (53)://Style
				style=true;
				curToken=getCaption(s);
				break;
			case (81)://Picture
				curToken=getCaption(s);
		   		try
				{
					javax.swing.ImageIcon imgi=makeImage(curToken,0);
					jc.setIcon(imgi);
				}
				catch(FileNotFoundException fe)
				{
					errReport+="Image file couldnot be found in Checkbox "+getName()+":Picture property";
				}
				catch(IOException ie)
				{
					errReport+="Image file couldnot be found in Checkbox "+getName()+":Picture property";
				}						
				break;
			
			case (100)://DisabledPicture
				curToken=s.nextToken();
		   		try
				{
					javax.swing.ImageIcon imgi=makeImage(curToken,1);
					jc.setDisabledIcon(imgi);
				}
				catch(FileNotFoundException fe)
				{
					errReport+="Image file couldnot be found in Checkbox "+getName()+":Picture property";
				}
				catch(IOException ie)
				{
					errReport+="Image file couldnot be found in Checkbox "+getName()+":Picture property";
				}						
				break;
			
			case (101)://DownPicture
				curToken=s.nextToken();
		   		try
				{
					javax.swing.ImageIcon imgi=makeImage(curToken,2);
					jc.setSelectedIcon(imgi);
				}
				catch(FileNotFoundException fe)
				{
					errReport+="Image file couldnot be found in Checkbox "+getName()+":Picture property";
				}
				catch(IOException ie)
				{
					errReport+="Image file couldnot be found in Checkbox "+getName()+":Picture property";
				}						
				break;
			
			case 37://BeginProperty
				curToken=s.nextToken();
				val1=(Integer)h.hs.get(curToken);
				if(val==null)
				{
					errReport+="Couldnot map Property in CheckBox "+getName()+" :Inside BeginProperty: :Property: "+curToken;
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
			default://This case is for handling those properties that are not defined in HashTable
				curToken=s.nextToken();
				break;
			}
		}
	}
  // This function is used during the code generation process
	
	public String getDeclaration()
	{
		if(style==false)
			return("javax.swing.JCheckBox "+jc.getName()+";\n\t");
		else
			return("javax.swing.JToggleButton "+jc.getName()+";\n\t");
	}
	
  // This function is used during the code generation process

	public String getName()
	{
		return(jc.getName());	
	}
  
  // This method takes integer value and returns hexadecimal equivalent of it. Actually a byte is converted to int and passed.
   
	public String getStr(int num)
	{
			String str=new String();
			int i,quo,rem;
			if(num<0)
			{
				num=128+(128+num);
			}
			for(i=0;i<2;i++)
			{
				quo=num/16;
				rem=num%16;
				switch(rem)
				{
					case 0: str="0"+str;
							break;
					case 1: str="1"+str;
							break;
					case 2: str="2"+str;
							break;
					case 3: str="3"+str;
							break;
					case 4: str="4"+str;
							break;
					case 5: str="5"+str;
							break;
					case 6: str="6"+str;
							break;
					case 7: str="7"+str;
							break;
					case 8: str="8"+str;
							break;
					case 9: str="9"+str;
							break;
					case 10: str="a"+str;
							break;
					case 11: str="b"+str;
							break;
					case 12: str="c"+str;
							break;
					case 13: str="d"+str;
							break;
					case 14: str="e"+str;
							break;
					case 15: str="f"+str;
							break;	
				
				}
				num=quo;
			}
			return(str);
		}
	
  // This method creates the image from the .frx file.The.frx file contains bytes of the image and the start address of the image in the .frx file.
    
	public javax.swing.ImageIcon makeImage(String str,int icontype) throws FileNotFoundException,IOException
	{
		int n,num,i,startl;
		Byte b1,b2,b3,b4;		
		FileInputStream fis;
		FileOutputStream fos;
		java.util.StringTokenizer s;
		String str1 = new String();
		String frxFile;
		javax.swing.ImageIcon imgi;
		byte b[],t[];

		// Get the absolute location of the frxFile and open it
		java.util.StringTokenizer si=new java.util.StringTokenizer(str,":,\"");
		frxFile = srcdir+"/"+si.nextToken();
		fis=new FileInputStream(frxFile);

		//  Get the starting address of the image
		str=si.nextToken();
		startl=Integer.parseInt(str,16);

		// Remove source directory name and '.frx' extension from frxFile name
		int index = frxFile.lastIndexOf('/');
		frxFile=frxFile.substring(index+1);
		frxFile=frxFile.substring(0,frxFile.length()-4);

		// Create an output file in the 'Images' folder in the destination directory.

		if(icontype==0)
			fos=new FileOutputStream(destdir+"/Images/"+frxFile+"_"+jc.getName()+".gif");
		else if(icontype==1)
			fos=new FileOutputStream(destdir+"/Images/"+frxFile+"_"+jc.getName()+"DisabledIcon.gif");
		else
			fos=new FileOutputStream(destdir+"/Images/"+frxFile+"_"+jc.getName()+"SelectedIcon.gif");

		// Read the frxFile bytes in a byte[] array
		n=fis.available();
		b=new byte[n];
		fis.read(b);
		fis.close();
		
		//Read the no.of bytes to be read from the current position to where the image ends.
		b1=new Byte(b[3+startl]); // MSB
		b2=new Byte(b[2+startl]);
		b3=new Byte(b[1+startl]);
		b4=new Byte(b[0+startl]); // LSB*/
		num=b1.intValue();
		str1+= getStr(num);
		num=b2.intValue();
		str1+=getStr(num);
		num=b3.intValue();
		str1+= getStr(num);
		num=b4.intValue();
		str1+= getStr(num);
		num=Integer.parseInt(str1,16);
		
		// Make a byte array of image size and read each byte from source byte array to destination byte array.
		t=new byte[num];
		for(i=startl+12;i<startl+num+4;i++)
		{
			t[i-startl-12]=b[i];
		}
		
		// Write Image bytes to the image file.
		fos.write(t);
		fos.close();
		
		// Create Image Icon of the generated Image
		if(icontype==0)
			imgi=new javax.swing.ImageIcon(destdir+"/Images/"+frxFile+"_"+jc.getName()+".gif");
		else if(icontype==1)
			imgi=new javax.swing.ImageIcon(destdir+"/Images/"+frxFile+"_"+jc.getName()+"DisabledIcon.gif");
		else
			imgi=new javax.swing.ImageIcon(destdir+"/Images/"+frxFile+"_"+jc.getName()+"SelectedIcon.gif");
		// Return the Image Icon.
		return(imgi);
	}
  
 // This method generates the code for JCheckBox object.It retrieves all the properties of the JCheckBox object which are set during parsing.
   
	public String getCode()
	{
		String code="";
		String name,curToken="";
		name=jc.getName();
		
		if(style==false)
		code+=name+"=new JCheckBox();\n\t\t";
		else
		code+=name+"=new JToggleButton();\n\t\t";
		code+=name+".setText("+jc.getText()+");\n\t\t";
		if(mnemonic!='\0')
		code+=name+".setMnemonic(java.awt.event.KeyEvent.VK_"+Character.toUpperCase(mnemonic)+");\n\t\t";
		code+=name+".setLocation("+jc.getX()+","+jc.getY()+");\n\t\t";
		code+=name+".setSize("+(jc.getWidth()+3)+","+jc.getHeight()+");\n\t\t";
		if(style==false)
		code+=name+".setHorizontalAlignment(SwingConstants.LEFT);\n\t\t";
		if(alignment==true&&style==false)
		code+=name+".setHorizontalTextPosition(SwingConstants.LEADING);\n\t\t";
		if(style==true)
		code+=name+".setHorizontalTextPosition(SwingConstants.CENTER);\n\t\t";
		if(bflag==true)
		{
			code+=name+".setBackground("+getRGB(jc.getBackground().toString(),false)+");\n\t\t";
		}
		if(fflag==true)
		{
			code+=name+".setForeground("+getRGB(jc.getForeground().toString(),true)+");\n\t\t";
		}
		code+=name+".setFont("+f.getFontCode()+");\n\t\t";
		if(jc.isSelected()==true)
		code+=name+".setSelected(true);\n\t\t";
		if(jc.getToolTipText()!=null)
		code+=name+".setToolTipText("+jc.getToolTipText()+");\n\t\t";
		if(appearance==true&&style==false)
		code+=name+".setBorderPaintedFlat(true);\n\t\t";
		if(mptype!=0)
		{
			String mp=new String();
			mp=getMousePointer(mptype);
			if(mp!=null)
			code+=name+".setCursor(new Cursor("+mp+"));\n\t\t";
		}
		if(jc.isVisible()==false)
			code+=name+".setVisible(false);\n\t\t";
		
		if(jc.isEnabled()==false)
			code+=name+".setEnabled(false);\n\t\t";
			
		// Set Icon if any & style is true
		if(!(jc.getIcon()==null) && style)
			code+=name+".setIcon(new javax.swing.ImageIcon(\""+jc.getIcon()+"\"));\n\t\t";
			
		if(!(jc.getDisabledIcon()==null) && style)
			code+=name+".setDisabledIcon(new javax.swing.ImageIcon(\""+jc.getDisabledIcon()+"\"));\n\t\t";
		if(!(jc.getSelectedIcon()==null) && style)
			code+=name+".setSelectedIcon(new javax.swing.ImageIcon(\""+jc.getSelectedIcon()+"\"));\n\t\t";
	    return (code);
	}
 // This method returns the string errReport 
   
	public String getErrorReport()
	{
		return(errReport);
	}
	
  // This method replaces every occurence of single backward slash by double backward slash.

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

   // This method is based on the idea that the ending Quotes is present at the end of the token
	
	protected String getCaption(java.util.StringTokenizer s)
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
					if(newpos+1!=token.length())// it implies that the ending quotes has been found
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
						//Replacing the first out of the pair by \\
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
