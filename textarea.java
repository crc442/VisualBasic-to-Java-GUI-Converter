import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;


class textarea extends control
{

	// This variable is used during parsing

	JTextArea jta;
	
	String Report;
   
   // These are used to store source and destination directories.
     String srcdir, destdir;
   
   
   // This is used as a flag for generator.
	private boolean style;
	
	
	// This Boolean variable is to check whether BorderStyle property of TextArea has been changed from default value*/
	private boolean borderstyle;
	
	
	// This Boolean variable is to check whether Multiline property of TextArea has been changed from default value
	private boolean multiline;
	

	

	//This Boolean variable checks whether Alignment property of TextArea has been changed from default value
	public boolean alignment;
	
	
	 //This is used to store tabIndex associated with TextArea object
	private int tabIndex;
	
	// This boolean variable is used to store information about Exception occuring while parsing 
	private boolean eflag;
	
	
	// This Boolean variable is to check whether Background colour has been modified
	private boolean bflag;
	
  
	//This Boolean variable is to check whether Foreground colour has been modified
	private boolean fflag;
	
 
	// This boolean variable is used to store information about appearance of control i.e; 3D or Flat 
	private boolean appearance;
	
  
  //constructor
	public textarea(String path)
	{
		jta = new JTextArea();
		multiline=false;
		alignment=false;
		borderstyle=true;
		srcdir=path;
		Report="";
	}
  
  
 //constructor	
	public textarea(String srcdirectory, String destdirectory)
	{
		srcdir = srcdirectory;
		destdir = destdirectory;
		jta = new JTextArea();
		style = false;
		multiline=false;
		alignment=false;
		borderstyle=true;
	}
	
	
	public void setProperties(StringTokenizer s,int tabHeight)
	{
		//Initialization
		curToken="";
		HashTable h = new HashTable();
		f=new font();
			
		// Set the name of the button
		String str = s.nextToken();
	
		jta.setName(str);
	
		//It is used to store height of the control object.
		int height=0;
		
		// It is used to store width of the control object.
		int width=0;
	    
	    // It is used to store left of the control object.
		int left=0;
		
		
		// It is used to store top of the control object
		int top=0;
		
	
		Integer val,val1;			//Temporary variable
		
		
	
	
		while(curToken.compareTo("End")!=0)
		{
			curToken=s.nextToken();
			
			val=(Integer)h.hs.get(curToken);
			if(val==null)
		     	continue;
		    switch(val.intValue())
			{				
			
					case 2://End
					System.out.println("Handled All Properties of Textarea: "+getName());
					break;
								
					case(11):// Height
						curToken=s.nextToken();
						
						try{
						//System.out.println(curToken);
						height=Integer.parseInt(curToken);
						height=height/15;
						}
						catch(NumberFormatException Ne)	{
					
							height=0;//Default value of left is taken as zero
							eflag=true;		
					
						}
						break;
						
					case(14)://Width
						curToken=s.nextToken();
						try{
							
						width=Integer.parseInt(curToken);
						width=width/15;
							}
						catch(NumberFormatException Ne){
					
						width=0;//Default value of left is taken as zero
						eflag=true;		
						}
						jta.setSize(width,height);
						break;
						
					case(12)://Left
						curToken=s.nextToken();
						try{
						Integer temp = new Integer(curToken);
						
						// If Tabbed pane object with -ve coordinates
						if(temp.longValue() < 0)
							left = (int)(temp.longValue() + 75000);
						// else object with normal coordinates
						else 
							left = temp.intValue();						
							left=left/15;				
						}
						
						catch(NumberFormatException Ne){
						left=0;//Default value of left is taken as zero
						eflag=true;		
						}
						break;
						
					case(13)://Top
						curToken=s.nextToken();
						
						try{
						top=Integer.parseInt(curToken);
						top=top/15;
						}
						catch(NumberFormatException Ne){
						top=0;//Default value of left is taken as zero
						eflag=true;		
						}
						
						jta.setLocation(left,top-tabHeight);
						break;
			
				case 22://Index
					str=s.nextToken();
		            String nm=jta.getName();
	         	   	nm+="_"+str;
	         	  	 jta.setName(nm); 		
			 	  	 break;		
			 	  	 	
			 case(48)://BackColor
	        	bflag=true;
				curToken=s.nextToken();
				curToken=curToken.substring(2,10);
				
				try{
					// If System color
					if(Integer.parseInt(curToken.substring(0,2),10)==80)
					{
						setForecolor(jta,Integer.parseInt(curToken.substring(2,8),16),false);
						sysBkColor=Integer.parseInt(curToken.substring(2,8),16);
					}
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
						}
						try{
							Bg=Integer.parseInt(curToken.substring(4,6),16);
						}
						catch(NumberFormatException Ne){
							Bg=0;//Setting default to Minimum green
							eflag=true;				
						}
						try{
							Br=Integer.parseInt(curToken.substring(6,8),16);
						}
						catch(NumberFormatException Ne){
							
							Br=0;//Setting default to Minimum red
							eflag=true;				
						}	
						jta.setBackground(new java.awt.Color(Br,Bg,Bb));
					}
				}
				
				catch(NumberFormatException Ne){

					setForecolor(jta,0,false);//Setting default to Scrollbar color
					sysBkColor=0;
					eflag=true;				
				}
				break;	
			
			case(59)://ForeColor
				fflag=true;
				curToken=s.nextToken();
				curToken=curToken.substring(2,10);
				// If System color
				try{
					if(Integer.parseInt(curToken.substring(0,2),10)==80)
					{
						setForecolor(jta,Integer.parseInt(curToken.substring(2,8),16),true);
						sysFrColor=Integer.parseInt(curToken.substring(2,8),16);
					}
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
						}
							
						try{
							Bg=Integer.parseInt(curToken.substring(4,6),16);
						}
						catch(NumberFormatException Ne){
						
							Bg=0;//Setting default to Minimum Green
							eflag=true;				
						}
							
						try{						
							Br=Integer.parseInt(curToken.substring(6,8),16);
						}
						catch(NumberFormatException Ne){

							Br=0;//Setting default to Minimum red
							eflag=true;				
						}	
						
						jta.setForeground(new java.awt.Color(Br,Bg,Bb));
					}
				}
				
				catch(NumberFormatException Ne){//Catch For Outer IF
				
					setForecolor(jta,15,true);//Setting default to control i.e; Black color
					sysFrColor=15;
					eflag=true;				
					}						
				break;	
				
				
					case(37):////"BeginProperty")==0
						curToken=s.nextToken();
				
						val=(Integer)h.hs.get(curToken);
		     			if(val==null)
		     				continue;
		     			switch(val.intValue())
						{//4
							case(39)://"Font"
							
									f.setFontProperty(s);		
									break;
						
						}//4
			
						break;
						
						
					case(50):// Enabled
						curToken=s.nextToken();
						jta.setEnabled(false);
						break;
					
					case 47://Appearance i.e; 3D or Flat
						str=s.nextToken();
						appearance=true;
						break;
				
				case 52://MousePointer
					str=s.nextToken();
					try{
						mptype=Integer.parseInt(str);
					}
					catch(NumberFormatException Ne){
				
						mptype=1;//Value to be used in case of Exception
						eflag=true;				
					}
				
					break;	
					
					case(53):// Style
						s.nextToken();
		   				s.nextToken();
		   				style = true;
		   				break;
		   				
		   				
					case(55):// ToolTipText
						curToken=s.nextToken();
		   				while(!curToken.endsWith("\""))
							curToken+=" "+s.nextToken();
						jta.setToolTipText(curToken);
						break;
						
					case(57):// Visible
						s.nextToken();
						s.nextToken();
		   				jta.setVisible(false);
		   				break;
					
					case(94):// BorderStyle
						s.nextToken();
						s.nextToken();
		   				borderstyle=false;
		   				break;
		   				
					case(66):// LOCKED
						s.nextToken();
						s.nextToken();
						jta.setEditable(false);
						break;
					
					case 90://Alignment
	        			curToken=s.nextToken();
	        			curToken=s.nextToken();
	        			alignment=true;
	        			break;
	        			
					case(102)://Text
						
						String caption=new String(); 
						
						if(multiline!=true)
							{		
								
								caption = getCaption(s);
								jta.setText(caption);
							}
						else
						{
							str=s.nextToken();
							try
							{
								jta.setText("\""+getMultiLineText(str)+"\"");
							}
							catch(Exception ie)
							{
							}	
							
						}

					break;
				
					case(103)://MultiLine
							
							multiline=true;
							
							break;
					default://Unidentified Property
						Report+="\nUnMapped Property:\t"+str;
						str=s.nextToken();
						if(str.startsWith("\""))
						removeString(s,str);
						break;
				
			}		
		}	
	}
	

// This Function is used to send complete 	report of UnMapped Properties 
  
	public String getReport()
	{
		return(Report);
	}
	
	private void removeString(StringTokenizer s,String str)
	{
		int i=0;
		int index;
		while(true)
		{
					
			index=str.indexOf('"');
			while(index!=-1)
			{
				i++;
				index=str.indexOf('"',index+1);
			}
			if(i%2==0)
			{
				break;
			}
			else
			str=s.nextToken();
		}
	}

  	// This method returns 'Declaration' for the JTextArea object.
 	public String getDeclaration()
	{
		String str=new String("javax.swing.JTextArea "+jta.getName()+";\n\t javax.swing.JScrollPane "+jta.getName()+"_Pane"+";\n\t");
		return(str);
	}
	
  // This mehod generates the code for JButton object.
  	public String getCode()
	{
		
		String name = new String();
		name=jta.getName();
		String str=new String();

		
		str+="\t"+name+" = new javax.swing.JTextArea();\n\t\t";
			
		str+="\t"+name+"_Pane=new javax.swing.JScrollPane("+name+","+name+"_Pane.VERTICAL_SCROLLBAR_AS_NEEDED,"+name+"_Pane.HORIZONTAL_SCROLLBAR_AS_NEEDED);\n\t\t";
		
		if(!jta.getText().equals(""))
		str+=name+".setText("+jta.getText()+");\n\t\t";
		
		
		str+=name+"_Pane.setLocation("+jta.getX()+","+jta.getY()+");\n\t\t";
		str+=name+"_Pane.setSize("+(jta.getWidth()+3)+","+jta.getHeight()+");\n\t\t";
			
		
	
		if(bflag==true)
			str+=name+".setBackground("+getRGB(jta.getBackground().toString(),false)+");\n\t\t";
		
		if(fflag==true)
			str+=name+".setForeground("+getRGB(jta.getForeground().toString(), true)+");\n\t\t";
		
		
		str+=name+".setFont("+f.getFontCode()+");\n\t\t";

		
		if(jta.isEnabled()==false)
			str+=name+".setEnabled(false);\n\t\t";
		
		if(jta.isEditable()==false)
			str+=name+".setEditable(false);\n\t\t";
		
		if(!(jta.getToolTipText()==null))
			str+=name+".setToolTipText("+jta.getToolTipText()+");\n\t\t";
		
		if(jta.isVisible()==false)
			str+=name+".setVisible(false);\n\t\t";		
			
		if(borderstyle==true)
		{	
				if(appearance==true)
	  	  		str+=name+".setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));\n\t\t";			
				else	
	  	  		str+=name+".setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));\n\t\t";
	  	}
		
				
	    return(str);
	}
	
//This method is used to retrive the Text Stored in FRX file by VB when multiline Property is set of VB.TextArea Control
 private String getMultiLineText(String loc) throws FileNotFoundException,IOException
	{
		FileInputStream fis;  
		StringTokenizer sl;
		String dir;
		Byte b1,b2;
		byte t[];
		String name,hex,Length,result,str;
		int addr,num,temp,i,Size,j,k;
		result="";
		sl=new StringTokenizer(loc,":\n\t\"");
		name=sl.nextToken();
		dir=srcdir+"/"+name;
		
		hex=sl.nextToken();
		addr=Integer.parseInt(hex,16);
		int n,flag=0,item;
		fis=new FileInputStream(dir);
		n=fis.available();
		byte b[],b3[],b4[];
		b=new byte[n];
		fis.read(b);
		fis.close();	
		num=b[addr];
		if(num<0)
		num=128+128+num;
		if(num!=255)
		{
			j=0;
			b3=new byte[n];
			for(i=0;i<num;i++)
			{
				switch(b[addr+i+1])
				{
					case 34: //"
							 b3[j]='\\';
							 b3[j+1]='"';
							 j=j+2;
							 break;
					case 13: //Ctrl+Enter
							 break;
					case 10: //Enter
							 b3[j]='\\';
							 b3[j+1]='n';
				    		 j=j+2;
							 break;
					case 9:  //Tab
							 b3[j]='\\';
							 b3[j+1]='t';
							 j=j+2;
							 break; 
				   case 92:  //"\-Escape" character
				   			 b3[j]='\\';
				   			 b3[j+1]='\\';
				   			 j=j+2;
				   			 break;
				    
				   default: b3[j]=b[addr+i+1];
				    		j++;
				    		break; 
				}
				
		    }
		    b4=new byte[j];
		    for(k=0;k<j;k++)
		    {
		    	b4[k]=b3[k];
		    }
		    result=new String(b4);
 			return(result);
		}
		else
		{
			temp=b[addr+2];
			Length=getStr(temp);
			temp=b[addr+1];
			Length+=getStr(temp);
			Size=Integer.parseInt(Length,16);
			j=0;
			b3=new byte[n];
			for(i=0;i<Size;i++)
			{
				
				switch(b[addr+i+3])
				{
					case 34: //"
							 b3[j]='\\';
							 b3[j+1]='"';
							 j=j+2;
							 break;
										
					case 13://ctrl+Enter
							 break;
					case 10://Enter 
							 b3[j]='\\';
							 b3[j+1]='n';
							 j=j+2;
							 break;
					case 9://Tab  
							 b3[j]='\\';
							 b3[j+1]='t';
							 j=j+2;
							 break;
				   case 92://"\-Escape" character 
				   			 b3[j]='\\';
				   			 b3[j+1]='\\';
				   			 j=j+2;
				   			 break;
				   default:  b3[j]=b[addr+i+3];
				    		 j++;
				    		 break; 
				}
				

			}
		    b4=new byte[j];
		    for(k=0;k<j;k++)
		    {
		    	b4[k]=b3[k];
		    }
		    result=new String(b4);
			return(result);
		}
	}	
   
   
  //This method takes integer value and returns  hexadecimal equivalent of it.
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

	//This function is used during the code generation process	
	public int getTabIndex()
	{
		return(tabIndex);
	}
	
	
	// This function is used during the code generation process.returns the name of JTextArea
	public String getName()
	{
		return(jta.getName());
		
	}

}


