import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;

//The list class comes into picture when there is VB.ListBox in source form file.

class list extends control
{
			

			private boolean eflag;
		 
		 //stores report
			private String Report;
			
			private JList jl;
			
	
			private String dir;
			
		  //used for checking bg color
			private boolean bflag;
		  
		  //used ofr checking fg color	
			private boolean fflag;
		 

		//constructor
			public list(String path)
			{
				jl=new JList();
				jl.setSelectionMode(0);
				dir=path;
				eflag=false;
				Report="";
			}
			
		  //returns the name of the JList object 
			
			public String getName()
			{
				return(jl.getName());
			}
			
			
			
			
			// This function is used during the code generation process.It returns 'Declaration' for the JListBox object.
   
			public String getDeclaration()
			{
				String Declaration="";
				Declaration+="javax.swing.JList "+jl.getName()+";\n\t";
				Declaration+="javax.swing.JScrollPane "+jl.getName()+"ScrollPane;\n\t";
				return(Declaration);
			}
			
			
			// returns the report generated
			public String getReport()
			{
				return(Report);
			}
  		  

			//generate code
			public String getCode()
			{
				int size,i;
				Object o1;
				String name=new String();
				String Code=new String();
				String string=new String();	
				name=jl.getName();
				Code+="\t"+name+"=new javax.swing.JList();\n\t\t";
				ListModel l1;
				l1=jl.getModel();
				Code+="java.util.Vector "+name+"v=new java.util.Vector(5,1);\n\t\t";
				size=l1.getSize();
				for(i=0;i<size;i++)
				{
					o1=l1.getElementAt(i);
					string=(String)o1;
					Code+=name+"v.add(\""+string+"\");\n\t\t";	
				}
				String tooltip=jl.getToolTipText();
	            if(jl.isEnabled()==false)
	            Code+=name+".setEnabled("+jl.isEnabled()+");\n\t\t";
			    if(jl.isVisible()==false)
	            Code+=name+".setVisible("+jl.isVisible()+");\n\t\t";
				if(tooltip!=null)
				Code+=name+".setToolTipText("+tooltip+");\n\t\t";
				if(jl.getSelectionMode()!=2)
				Code+=name+".setSelectionMode("+jl.getSelectionMode()+");\n\t\t";
				Code+=name+".setListData("+name+"v);\n\t\t";					
				if(mptype!=0)
				{
					String mp=new String();
					mp=getMousePointer(mptype);
					if(mp!=null)
					Code+=name+".setCursor(new Cursor("+mp+"));\n\t\t";
				}
				if(bflag==true)
				{
					String color = jl.getBackground().toString();			
					Code+=name+".setBackground("+getRGB(color,false)+");\n\t\t";
				}
				if(fflag==true)
				{		
					
					Code+=name+".setForeground("+getRGB(jl.getForeground().toString(),true)+");\n\t\t";
				}
				Code+=name+".setFont("+f.getFontCode()+");\n\t\t";
				Code+=name+"ScrollPane=new JScrollPane("+name+",JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);\n\t\t";
				Code+=name+"ScrollPane.setLocation("+jl.getX()+","+jl.getY()+");\n\t\t";
				Code+=name+"ScrollPane.setSize("+jl.getWidth()+","+jl.getHeight()+");\n\t\t";
				if(jl.getBorder()!=null)
				Code+=name+"ScrollPane.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));\n\t\t";
				if(jl.getBorder()!=null)
				Code+=name+"ScrollPane.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));\n\t\t";
				return(Code);
			}
			
			
			
		  //function for identifying the propeties of the listbox
    	public void setProperties(StringTokenizer s1,int tabHeight)
			{
				Vector v1;
				int j,x=0,y,height=0,width;
				HashTable h=new HashTable();
				Integer tid;
				f=new font();
				String curToken=s1.nextToken();
				jl.setName(curToken);
				curToken=s1.nextToken();
				tid=(Integer)h.hs.get(curToken);
				if(tid==null)
				j=0;
				else
				j=tid.intValue();
				while(j!=2)
				{
					switch(j)
					{
						case 47://Appearance=Flat 
								 s1.nextToken();
								 jl.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
								 break;
								 
						case 48://Backcolor
								bflag=true;
								curToken=s1.nextToken();
								curToken=curToken.substring(2,10);

								try{
									// If System color
									if(Integer.parseInt(curToken.substring(0,2),10)==80)
									{
										setForecolor(jl,Integer.parseInt(curToken.substring(2,8),16),false);
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
									jl.setBackground(new java.awt.Color(Br,Bg,Bb));
									}
								}
								
							catch(NumberFormatException Ne){	//Catch For outer IF 

									setForecolor(jl,15,false);//Setting default to Scrollbar color
									sysBkColor=15;
									eflag=true;				
								}
							break;	
							
						case 55://ToolTipText
								jl.setToolTipText(getCaption(s1));
								break;
								
						case 22://Index
								 curToken=s1.nextToken();
	            				 String nm=jl.getName();
	            				 nm+="_"+curToken;
	            				 jl.setName(nm);
	            				 break;
	            				 
						case 59://ForeColor
								fflag=true;
								curToken=s1.nextToken();
								curToken=curToken.substring(2,10);
								// If System color
								try{
									if(Integer.parseInt(curToken.substring(0,2),10)==80)
									{
										setForecolor(jl,Integer.parseInt(curToken.substring(2,8),16),true);
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
										jl.setForeground(new java.awt.Color(Br,Bg,Bb));
									}
								}
								catch(NumberFormatException Ne){		//Catch For Outer IF
									setForecolor(jl,18,true);//Setting default color
									sysFrColor=18;
									eflag=true;				
								}							
								break;	
												
						case 11: curToken=s1.nextToken();
								 try
								 {
								 	height=Integer.parseInt(curToken);
								 	height=height/15;
								 }
								 catch(Exception e)
								 {
								 	eflag=true;
								 }
								 break;
						
						case 12:curToken=s1.nextToken();
								try
								{
									Integer temp = new Integer(curToken);
									// If Tabbed pane object with -ve coordinates
									if(temp.longValue() < 0)
									x = (int)(temp.longValue() + 75000);
									// else object with normal coordinates
									else 
									x = temp.intValue();						
									x=x/15;	
								}
								catch(Exception e)
								{
									eflag=true;
								}
								break;
						
						case 13:curToken=s1.nextToken(); 
								try
								{
									y=Integer.parseInt(curToken);
									y=y/15;
									jl.setLocation(x,y-tabHeight);
								}
								catch(Exception e)
								{
									eflag=true;
								}
								break;
						
						case 14: curToken=s1.nextToken();
								 try
								 {
								 	width=Integer.parseInt(curToken);
								 	width=width/15;
								 	jl.setSize(width,height);
								 }
								 catch(Exception e)
								 {
								 	eflag=true;
								 }
								 break;
								 
								 
						case 110://Multiselect
								 s1.nextToken();
								 jl.setSelectionMode(2);
								 break;
								 
						case 52://MousePointer
	        					  curToken=s1.nextToken();
	        					  mptype=Integer.parseInt(curToken);
	        					  curToken=s1.nextToken();
	        					  break;
	        					  
						case 58://List
							   	 curToken=s1.nextToken();
								 try
								 {
								 	v1=new Vector(10,5);
								 	extractListElements(v1,curToken);
								 	jl.setListData(v1);
								 }
								 catch(Exception ie)
								 {

								 }
								 break;
								 
						case 50://Enable
								curToken=s1.nextToken();
							    if(curToken.compareTo("0")==0);
								{
									jl.setEnabled(false);
								}
								break;
						case 57://Visible
								curToken=s1.nextToken();
								if(curToken.compareTo("0")==0);
								{
									jl.setVisible(false);
								}
								break;
						
						case 37: 		
								 //Beginproperty
								 Integer tid1;
								 curToken=s1.nextToken();
								 tid1=(Integer)h.hs.get(curToken);
								 if(tid1==null)
								 continue;
								 switch(tid1.intValue())
								 {
									case 39:
									
											f.setFontProperty(s1);		
											break;	
								  }
								  break;
					
						default://Unidentified Property
								Report+="\nUnidentified Property:\t"+curToken;
								curToken=s1.nextToken();
								if(curToken.startsWith("\""))
								removeString(s1,curToken);
								break;
						
					}
				
					curToken=s1.nextToken();
					tid=(Integer)h.hs.get(curToken);
					if(tid==null)
		    		j=0;
					else
		    		j=tid.intValue();
				}
				jl.setFont(new Font(f.name,f.style,(int)f.size));
				System.out.println("Handled list");
				
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
			
			
		  // This method extracts elements from the FRX file for the list object
    			private void extractListElements(Vector v1,String loc) throws FileNotFoundException,IOException
			{
				FileInputStream fis;  
				StringTokenizer sl;
				Byte b1,b2;
				byte t[];
				String name,hex,Litem,actualpath;
				int addr,num,temp,i;
				sl = new StringTokenizer(loc,":\n\t\"");
				name=sl.nextToken();
				dir=dir+"/"+name;

				hex=sl.nextToken();

				addr = Integer.parseInt(hex,16);
				System.out.println(" "+addr+" ");
				int n,flag=0,item;
				fis=new FileInputStream(dir);
				n=fis.available();
				byte b[],b3[];
				b=new byte[n];
				fis.read(b);
				num=b[addr];
				b1=new Byte(b[addr+1]);
				b2=new Byte(b[addr]);
				String str=new String();
				String str1=new String();
				num=b1.intValue();
				str1+=getstr(num);
				num=b2.intValue();
				str1+=getstr(num);
				num=Integer.parseInt(str1,16);

				addr+=4;
				for(item=0;item<num;item++)
				{
					b1=new Byte(b[addr+1]);
					b2=new Byte(b[addr]);
					str1=new String();
					temp=b1.intValue();
					str1+=getstr(temp);
					temp=b2.intValue();
					str1+=getstr(temp);

					temp=Integer.parseInt(str1,16);
					t=new byte[temp];
					addr=addr+2;
					for(i=0;i<temp;i++)
					{
						t[i]=b[addr];
						addr++;
					}
					Litem=new String(t);
					v1.addElement(Litem);

				}
			fis.close();	
			
	
		}
		
	
	//get hexadecimal equivalent if the integer
		private String getstr(int num)
		{
		//	System.out.println(num);
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
}