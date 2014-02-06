import java.awt.*;
import javax.swing.*;
import java.util.*;


// The tabbedpane class comes into picture when there is VB.TabDlg.SSTab in source form file.

class tabbedpane extends compositeObject
{
	//This variable is used during parsing to store properties of VB.TabDlg.SSTab.
    	private JTabbedPane jTp;
	
	
	//It is  used to store Number Of Tabs present in Tabbed Pane
     	private int numTabs;
   
	//This Boolean variable is to check whether Foreground colour has been modified
	private boolean fflag;
     	
     
	// This Boolean variable is to check whether Background colour has been modified
	private boolean bflag;
  
	
	// This boolean variable is used to store information about Exception occuring while parsing 
	private boolean eflag;
	

	
     // It is  used to store Tab Number of Control in which it is present.
	private int tabNum;
	
	
    //This is used to store the Names of the tab in the Tabbed Pane.
   	private String[] jp;
	
	// This vector is used to store the Names of controls in the Tabbed Pane.
    private Vector ctrls;
	
	// This vector is used to store the Index of the Tab(i.e;Tab Number) of each control.
    private Vector tabIndexOfCtrl;
	
	
     // This Variable acts as an index for control into ctrls vector and tabIndexOfCtrl Vectors.
    private int ctrlindex;	
	
	
	
     // This Variable is used to store the tab height of the Tabbed Pane.
	private int tabHeight;
	
	
     // This Variable is used to store the Tab Number of currently enabled tab in  the Tabbed Pane.
	private int tab;
	
	
    //  This Variable is used to store the font size of the text displayed at top of the tab in  the Tabbed Pane.
    private double tabFontSize;
    
    
	// This is a constructor that initializes the tabbedpane object when it is created with default values.

	public tabbedpane(String srcDirs,String destDirs)
	{
		srcDir = srcDirs;
		destDir=destDirs;
		ctrlindex=0;
		numTabs=3;
		jp=new String[numTabs];
		ctrls=new Vector(5);
		tabIndexOfCtrl=new Vector(5);
		bflag=false;
		fflag=false;
		sysBkColor=15;
		sysFrColor=18;
	}
	

    public void setProperties(StringTokenizer s,int tabHeight1)
	{
		 //this is used to store string Tokens and corresponding integer constants. 
		HashTable h= new HashTable();
		
		//It is used to store height of the control object.
		int height=0;
		
		//It is used to store width of the control object.
	    int width=0;
	    
	    // It is used to store left of the control object.
		int left=0;
		
		
		// It is used to store top of the control object.
		int top=0;
		

		Integer val,val1; //	Temporary variable
		jTp=new JTabbedPane();
		System.out.println("Inside TabbedPane");
		curToken=s.nextToken();
		objName=curToken;
		jTp.setName(curToken);
		
		while(curToken.compareTo("End")!=0)
		{
			curToken=s.nextToken();
			System.out.println("Inside while "+curToken);
			val=(Integer)h.hs.get(curToken);
			if(val==null)
			{
				if(curToken.compareTo("'True")==0);
				
				else if(curToken.compareTo("'False")==0);
				
				else if(curToken.startsWith("TabCaption"))
				{
					tabNum=Integer.parseInt(curToken.substring(curToken.indexOf('(')+1,curToken.indexOf(')')));
					System.out.println("TabNumber is "+tabNum);
					jp[tabNum] = getCaption(s);
				}
				else if(curToken.startsWith("TabPicture"))
				{
					curToken=s.nextToken();
					System.out.println("This is TabPicture of "+curToken);
				}
				else if(curToken.startsWith("Tab"))
			    {
			    	System.out.println("Inside block for Tab");
			    	if(curToken.length()==3)
			    	{
			    		curToken=s.nextToken();	
			    		continue;
			    	}
			    	tabNum=Integer.parseInt(curToken.substring(curToken.indexOf('(',2)+1,curToken.indexOf(')')));
			    	
			    	if(curToken.endsWith("ControlCount"))
			    	{
			    		curToken=s.nextToken();
			    		System.out.println("This is inside of Tab control count"+curToken);
			    		
			    	}
			    	else if(curToken.endsWith("ControlEnabled"))
			    	{
			    		curToken=s.nextToken();
			    		System.out.println("This is inside of Tab control Enabled"+curToken);
			    	}
			    	else if(curToken.indexOf("Control")!=-1)
			    	{
			    		String par=new String("(");
			    		if(curToken.endsWith("Enabled")==false&&par.compareTo(String.valueOf(curToken.charAt(curToken.indexOf("Control")+7)))==0)
			    		{
			    			System.out.println("Adding "+Integer.valueOf(String.valueOf(curToken.charAt(curToken.indexOf("Tab")+4)))+" to Tab Number Of control");
			    			tabIndexOfCtrl.add(Integer.valueOf(curToken.substring(curToken.indexOf('(',curToken.indexOf("Tab"))+1,curToken.indexOf(')'))));
			    			curToken=s.nextToken();
			    			curToken=curToken.substring(1,curToken.length()-1);
			    			System.out.println("Adding "+curToken+" to ctrls");
			    			ctrls.add(curToken);
			    			ctrlindex++;
			    			System.out.println("Ctrl index is "+ctrlindex);
			    		}
			    		else
			    		{
			    			curToken=s.nextToken();
			    			System.out.println("Inside control else"+curToken);
			    		}
			   		 }
			   	   	else
			    	{
			    		curToken=s.nextToken();
			    		System.out.println("This is inside else else else of "+curToken);
			    	}		    	
			    	
			    }
			    else
			    {
			   	    	System.out.println("test "+curToken);
			    }
			}
			else
			{
				switch(val.intValue())
				{
					case 1:addEmbeddedObj(s,tabHeight);
						   break;
						   
					case 86://("Tabs")==0)
				
						curToken=s.nextToken();
						numTabs=Integer.parseInt(curToken);
						System.out.println("Number Of Tabs is "+numTabs);
						jp=new String[numTabs];
					break;
				
				    case 11://(curToken.compareTo("Height")==0)
				
						curToken=s.nextToken();
						height=Integer.parseInt(curToken);
						height=height/15;
						System.out.println("Height is "+height);
					break;
				
					case 14://(curToken.compareTo("Width")==0)
				
						curToken=s.nextToken();
						width=Integer.parseInt(curToken);
						width=width/15;
						jTp.setSize(width,height);
						System.out.println("width is "+width);
					break;
				
				
					case 12://(curToken.compareTo("Left")==0)
						curToken=s.nextToken();
						Integer temp = new Integer(curToken);
						
						// If Tabbed pane object with -ve coordinates
						if(temp.longValue() < 0)
							left = (int)(temp.longValue() + 75000);
						// else object with normal coordinates
						else 
							left = temp.intValue();						
						left=left/15;				
						break;
				
						
				
					case 13://(curToken.compareTo("Top")==0)
				
						curToken=s.nextToken();
						top=Integer.parseInt(curToken);
						top=top/15;
						jTp.setLocation(left,top-tabHeight1);
						break;
					
					case 99://(curToken.compareTo("Tab")==0)
						curToken=s.nextToken();
						tab=Integer.parseInt(curToken);
					break;
					
					case 59://(curToken.compareTo("ForeColor")==0)

							fflag=true;
							curToken=s.nextToken();
							curToken=curToken.substring(2,10);
							// If System color
							try{
								if(Integer.parseInt(curToken.substring(0,2),10)==80)
								{
									setForecolor(jTp,Integer.parseInt(curToken.substring(2,8),16),true);
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
									jTp.setForeground(new java.awt.Color(Br,Bg,Bb));
								}
							}
							catch(NumberFormatException Ne){
								setForecolor(jTp,18,true);//Setting default to control i.e; Black color
								sysFrColor=18;
								eflag=true;				
							}						
							break;				
					
					case 87://(curToken.compareTo("TabHeight")==0)
					     curToken=s.nextToken();
					     tabHeight = Integer.parseInt(curToken)/26;
					     System.out.println("Tab Height "+curToken);
					     tabFontSize=Math.ceil(((tabHeight*13/30))*1.40);
	            	 break;
	            	 
	            	 case 88://(curToken.compareTo("TabPerRow")==0)
					     curToken=s.nextToken();
					     System.out.println("TabsPerRow "+curToken);
	            	 break;
	            	 
	            	case 16:
	            		curToken=s.nextToken();
	            		System.out.println("TabIndex is "+curToken);	            		
	            	break;
	            	
	            	case 48://(curToken.compareTo("BackColor")==0)
	           
	            			bflag=true;
							curToken=s.nextToken();
							curToken=curToken.substring(2,10);
							try{
									// If System color
								if(Integer.parseInt(curToken.substring(0,2),10)==80)
								{
									setForecolor(jTp,Integer.parseInt(curToken.substring(2,8),16),false);
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
									jTp.setBackground(new java.awt.Color(Br,Bg,Bb));
								}
							}
							catch(NumberFormatException Ne){//Catch For outer IF 
								setForecolor(jTp,15,false);//Setting default to Scrollbar color
								sysBkColor=15;
								eflag=true;				
							}
						break;	
						
	          
	            	case 96://(curToken.compareTo("TabOrientation")==0)
						curToken = s.nextToken();
						switch(Integer.parseInt(curToken))
						{
							case 1:jTp.setTabPlacement(JTabbedPane.BOTTOM);
									break;
							case 2:jTp.setTabPlacement(JTabbedPane.LEFT);
									break;
							case 3:jTp.setTabPlacement(JTabbedPane.RIGHT);
									break;
						}						
						break;

			    
					case 37://(curToken.compareTo("BeginProperty")==0)
				
						curToken=s.nextToken();
						val1=(Integer)h.hs.get(curToken);
						if(val==null)
						{
			
							curToken=getCaption(s);
							curToken="";
							while(curToken.compareTo("EndProperty")!=0)
							{
								curToken=getCaption(s);
							}
							continue;
						}
						switch(val1.intValue())
						{
						
							case 39://(curToken.compareTo("Font")==0)
					
								while(curToken.compareTo("EndProperty")!=0)
								{
									curToken=s.nextToken();
									val1=(Integer)h.hs.get(curToken);
									if(val1==null)
									continue;
									switch(val1.intValue())
									{
										case 39://Font
											f.setFontProperty(s);		
											break;	
									}
					
								}
							}
						}
					}
				}
		}
	
    //This method returns the name of the particular tabbedpane which is being processed
	
	public String getName()
	{
		return(jTp.getName());	
	}
	
	
	//This method returns the number of controls present in the tabbedpane object.
	public int getCtrlIndex()
	{
		return(ctrlindex);	
	}
	
	//This method returns the Vector storing names of controls present in the tabbedpane object.
	public Vector getCtrls()
	{
		return(ctrls);	
	}


	//This method returns the Vector storing tab number of control present in the tabbedpane object.
	public Vector getTabIndexOfCtrl()
	{
		return(tabIndexOfCtrl);	
	}
	
	
	//This function is used during the code generation process.It returns 'Declaration' for the tabbedpane object.
	public String getDeclaration()
	{
		String decln = "JTabbedPane "+jTp.getName()+";\n\t";
		decln+=getDeclnOfEmbeddedObj();
		return(decln);		
	}
	
   
   //This method generates the code for tabbedpane object.It retrieves all the properties of the tabbedpane object
	public String getCode(String tabName)
	{
		String name,str="";
		name=jTp.getName();
		Font f;
		str+=name+"=new JTabbedPane();\n\t\t";
		str+=name+".setLocation("+jTp.getX()+","+jTp.getY()+");\n\t\t";
		str+=name+".setSize("+jTp.getWidth()+","+(jTp.getHeight())+");\n\t\t";
		f=jTp.getFont();
		if(f.getName().startsWith("\""))
		str+=name+".setFont(new Font("+f.getName()+","+f.getStyle()+","+(int)tabFontSize+"));\n\t\t";
		else
		str+=name+".setFont(new Font(\""+f.getName()+"\","+f.getStyle()+","+(int)tabFontSize+"));\n\t\t";
		if(fflag)
		str+=name+".setForeground("+getRGB(jTp.getForeground().toString(),false)+");\n\t\t";
		if(bflag)
		str+=name+".setBackground("+getRGB(jTp.getBackground().toString(),false)+");\n\t\t";
		
		int tplace = jTp.getTabPlacement();
		if(tplace !=JTabbedPane.TOP)
		{
			if(tplace ==3)
				str+=name+".setTabPlacement(JTabbedPane.BOTTOM);\n\t\t";
			else if(tplace == 2)
				str+=name+".setTabPlacement(JTabbedPane.LEFT);\n\t\t";
			else if(tplace == 4)
				str+=name+".setTabPlacement(JTabbedPane.RIGHT);\n\t\t";
		}
		
		int i;
		for(i=0;i<numTabs;i++)
		{
			str+="JPanel "+name+"Jtp"+i+" = new JPanel();\n\t\t";
			str+=name+"Jtp"+i+".setLayout(null);\n\t\t";
		}
		str+="\n\t\t";
		for(i=0;i<numTabs;i++)
		str+=name+".addTab("+jp[i]+","+name+"Jtp"+i+");\n\t\t";
		str+="\n\t\t";
		str+=getCodeOfEmbeddedObj(this,tabIndex,tabName);
	/*	for(i=0;i<v.size();i++)
		{
			Object o=v.get(i);
			if((o.getClass().toString().compareTo("class list"))==0)
			{
				for(int j=0;j<ctrlindex;j++)
				if(((list)o).getName().compareTo(ctrls.get(j))==0)
				ctrls.set(j,ctrls.get(j)+"ScrollPane");
			}
			if((o.getClass().toString().compareTo("class textarea"))==0)
			{
				for(int j=0;j<ctrlindex;j++)
				if(((textarea)o).getName().compareTo(ctrls.get(j))==0)
				ctrls.set(j,ctrls.get(j)+"_Pane");
			}
			
		}*/
		for(i=0;i<ctrlindex;i++)
		{
			str+=jTp.getName()+"Jtp"+tabIndexOfCtrl.get(i)+".add("+ctrls.get(i)+");\n\t\t";	
		}
		if(shFlag==true)
		str+=name+".addMouseListener(this);\n\t\t";	
		if(jTp.getSelectedIndex()!=1)
			str+=name+".setSelectedIndex("+tab+");\n\t\t";
					
		return (str);
	}
	
	
	//	* The Method is used for converting the Decimal Parameter to Hexadecimal base.
   	public String intToHex(Integer x)
	{
		String hex = new String();
		long quo, num;
		int rem;
		num = x.longValue();
		while(num > 0)
		{
			quo = num/16;
			rem = (int) num % 16;
			switch(rem)
			{
				case 0: hex="0"+hex;
						break;
				case 1: hex="1"+hex;
						break;
				case 2: hex="2"+hex;
						break;
				case 3: hex="3"+hex;
						break;
				case 4: hex="4"+hex;
						break;
				case 5: hex="5"+hex;
						break;
				case 6: hex="6"+hex;
						break;
				case 7: hex="7"+hex;
						break;
				case 8: hex="8"+hex;
						break;
				case 9: hex="9"+hex;
						break;
				case 10: hex="a"+hex;
						break;
				case 11: hex="b"+hex;
						break;
				case 12: hex="c"+hex;
						break;
				case 13: hex="d"+hex;
						break;
				case 14: hex="e"+hex;
						break;
				case 15: hex="f"+hex;
						break;	
		
			}
			num= quo;
		}
		if(hex.length()<6)
			while(hex.length()<6)
				hex="0"+hex;
		return(hex);
		
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
}