
import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

// The form class comes into picture when there is VB.Form in source form file.
class form extends compositeObject
{
		public font f;
		JFrame frm;
		HashTable h;
		MenuBar mBar;
		boolean menuFlag=true;
		
		//used to store information about Exception occuring while parsing 
		private boolean eflag;
		
		// check whether Background colour has been modified
		private boolean bflag;
		
		 //whether Foreground colour has been modified
		private boolean fflag;
	
	
	// This mehod generates the code for From object.

		public String getCode(String fname) 
		{
			String str=new String();
			if(shFlag==true)
			str+="import java.awt.event.*;\n";
			str+="class "+fname+" extends JFrame";
			if(shFlag==true)
			str+=" implements MouseListener"; 
			str+="\n{\n\t";
			str+=getDeclnOfEmbeddedObj();		//fetch the code for the declaration part for all embedded objects
		  	str+="\n\tpublic "+fname+"()\n\t{\n\t\tgetContentPane().setLayout(null);\n\n\t";
			str+=getCodeOfEmbeddedObj(null,tabIndex,null);	// get the code for adding the control to te form or tabbed pane		
			
			str+="\taddWindowListener(new java.awt.event.WindowAdapter() {\n\t";
			str+="\t\tpublic void windowClosing(java.awt.event.WindowEvent e) {\n\t";
			str+="\t\t\tdispose();\n\t\t\t\tSystem.exit(0);\n\t\t\t}\n\t\t});\n\t";
			str+="}\n\t";
			if(shFlag==true)
			{
				str+="\n\tpublic void mouseClicked(java.awt.event.MouseEvent me)\n\t";
				str+="{\n\t\tSystem.out.println(\"Invoking Paint\");";
				str+="\n\t\tpaint(this.getGraphics());\n\t}";
				str+="\n\tpublic void mouseEntered(java.awt.event.MouseEvent me){}";
				str+="\n\tpublic void mouseExited(java.awt.event.MouseEvent me){}";
				str+="\n\tpublic void mousePressed(java.awt.event.MouseEvent me){}";
				str+="\n\tpublic void mouseReleased(java.awt.event.MouseEvent me){}";
				str+="\n\n\tpublic void paint(Graphics g)\n\t";
				str+="{\n\t\tpaintComponents(g);\n\t";
				str+=shapeCode;			
				str+="\n}";
			}
			
			str+="\n\tpublic static void main(String args[])\n\t{\n\t\ttry\n\t\t{\n\t\t\tUIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());\n\t\t}\n\t\tcatch (Exception exc)\n\t\t{\n\t\t}\n\t\t"+fname+" f=new "+fname+"();\n\t\t";
			str+="f.setLocation("+frm.getX()+","+frm.getY()+");\n\t\t";
			str+="f.setSize("+frm.getWidth()+","+frm.getHeight()+");\n\t\t";
			
			if(bflag)
				str+="f.setBackground("+getRGB(frm.getBackground().toString(),false)+");\n\t\t";
						
			if(fflag)
				str+="f.setForeground("+getRGB(frm.getForeground().toString(),true)+");\n\t\t";
					
			// Set Cursor
			if(mptype!=0)
			{
				String mp=new String();
				mp=getMousePointer(mptype);
				if(mp!=null)
					str+="setCursor(new Cursor("+mp+"));\n\t\t";
			}
			if(!frm.getTitle().equals(""))
				str+="f.setTitle("+frm.getTitle()+");\n\t\t";
			if(frm.getExtendedState()==Frame.MAXIMIZED_BOTH)
				str+="f.setExtendedState(Frame.MAXIMIZED_BOTH);\n\t\t";
			
			str+="f.setVisible(true);\n\t}\n}";
			
			shapeCode="";
			shFlag=false;
			return(str);
		}
			                                                    


		void setProperties(StringTokenizer s)
		{//1
		
		
		
			int n;
			String curToken;
			curToken=s.nextToken();
			objName=curToken;
			frm.setName(curToken);
			Integer val;
			int top=0,left=0,height=0,width=0;
			h=new HashTable();
			while(curToken.compareTo("End")!=0)
		 	{//2
		    		curToken=s.nextToken();       
		     		val=(Integer)h.hs.get(curToken);
		     		if(val==null)
		     		continue;
		     		switch(val.intValue())
		     		{//3
		     			case 1://"Begin"
		     					addEmbeddedObj(s,0);
		     					break;
		     					
		     			case(4):// Caption 
								frm.setTitle(getCaption(s));							
								break;
								
		     			case 5://"ClientHeight"
								curToken=s.nextToken();
								height=Integer.parseInt(curToken);
								height=height/15+35;
								break;
								
						case 8://"ClientWidth"
								curToken=s.nextToken();
							//	System.out.println(curToken);
								width=Integer.parseInt(curToken);
								width=width/15;
								frm.setSize(width,height);
								break;
								
						case 6://"ClientLeft"
								curToken=s.nextToken();
								left=Integer.parseInt(curToken);
								left=left/15;
								break;
								
						case 7://"ClientTop"
								curToken=s.nextToken();
								top=Integer.parseInt(curToken);
								top=top/15;
								frm.setLocation(left,top);
								break;
					
						case(48)://BackColor
								bflag=true;
								curToken=s.nextToken();
								curToken=curToken.substring(2,10);
								
								try{
									// If System color
									if(Integer.parseInt(curToken.substring(0,2),10)==80)
									{
										setForecolor(frm,Integer.parseInt(curToken.substring(2,8),16),false);
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
									frm.setBackground(new java.awt.Color(Br,Bg,Bb));
									}
								}
								catch(NumberFormatException Ne){//Catch For outer IF 
									setForecolor(frm,15,false);//Setting default to Scrollbar color
									sysBkColor=15;
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
										setForecolor(frm,Integer.parseInt(curToken.substring(2,8),16),true);
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
										frm.setForeground(new java.awt.Color(Br,Bg,Bb));
									}
								}
								catch(NumberFormatException Ne){//Catch For Outer IF
									setForecolor(frm,18,true);//Setting default to control i.e; Black color
									sysFrColor=18;
									eflag=true;				
								}						
								break;				
					
								
						case 52:// MousePointer
								curToken=s.nextToken();
								try{
									mptype=Integer.parseInt(curToken);
									}
								catch(NumberFormatException Ne){
									mptype=1;//Value to be used in case of Exception
									eflag=true;				
									}
								break;
						
						case 98:// WindowState
								curToken=s.nextToken();
								
								if(Integer.parseInt(curToken)==2)
								{
									frm.setExtendedState(Frame.MAXIMIZED_BOTH);
									
								}
																
								break;	
								
					
						case 37://"BeginProperty")==0
								curToken=s.nextToken();
								val=(Integer)h.hs.get(curToken);
		     					if(val==null)
		     					continue;
		     					switch(val.intValue())
								{//4
										case(39)://"Font")==0
												while(curToken.compareTo("EndProperty")!=0)
												{//5
													curToken=s.nextToken();
													val=(Integer)h.hs.get(curToken);
		     										if(val==null)
		     										continue;
													switch(val.intValue())
													{//6
															case(40)://"Name")==0
																curToken=s.nextToken();

																while(curToken.indexOf("\"",2)==-1)
																curToken+=" "+s.nextToken();
																f.name=curToken;

																break;
															case(41)://"Size")==0
														
															
																curToken=s.nextToken();
																f.size=Math.ceil(Double.parseDouble(curToken)*1.33);
																break;
														
															case(44)://"Weight")==0
															
																curToken=s.nextToken();
																if(Integer.parseInt(curToken)>450)
																f.style=f.style|Font.BOLD;	
																break;
															 case(45)://Italic")==0
														
																curToken=s.nextToken();
																if(Integer.parseInt(curToken)==-1)
																f.style=f.style|Font.ITALIC;
																break;
															case (54):
																	  break;
													}//6	
															
												}//5
														
											}//4
											frm.setFont(new Font(f.name,f.style,(int)f.size));
											break;//end of case 3	for VB.Form	
											
					}//3					
		 	}//2
		 
		}//1
		

//constructor
		public form(String srcDirs,String destDirs) 
		{
		//	FileInputStream fis;  
		//	StringTokenizer s;
			rbcnt=0;
			v=new Vector(10,5);
			frm=new JFrame();
			srcDir=srcDirs;
			destDir=destDirs;
			f=new font();
			frm.setTitle("");
		
	}
	

	public String getName()
	{
		return(frm.getName());		
	}
}