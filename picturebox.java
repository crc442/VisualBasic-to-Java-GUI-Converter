import java.util.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;

// The picturebox class comes into picture when there is VB.PictureBox in source form file.
 class picturebox extends compositeObject
{
  
  //used to store UnMapped Properties
    String Report;
  
  // This variable is used during parsing
	JPanel jp;
  
  
	//used during parsing
	JLabel picture;
	
  // used for detecting whether the Default Background Colour has been changed or not.
	boolean bflag;
	
	
	// used for detecting whether the Default Foreground Colour has been changed or not.
	boolean fflag;
	
 
 //constructor
	public picturebox(String srcDirs,String destDirs)
	{
		rbcnt=0;
		v=new Vector();
		jp=new JPanel();
		jp.setLayout(null);
		jp.setBorder(BorderFactory.createBevelBorder(1));
		picture=new JLabel();
		srcDir=srcDirs;
		destDir=destDirs;
		Report="";
	}
	
  
  //return the name of the JPanel object 
	public String getName()
	{
		return(jp.getName());
	}
	
	
	public String getCode(String tabName)
	{
		String name,str,str1="hello";
		int counter;
		name=jp.getName();
		str="\t"+name+"=new javax.swing.JPanel();\n\t\t";
		str+=name+".setLocation("+jp.getX()+","+jp.getY()+");\n\t\t";
		str+=name+".setSize("+jp.getWidth()+","+jp.getHeight()+");\n\t\t";
	    if(jp.getBorder().getClass().toString().compareTo("class javax.swing.border.BevelBorder")==0)
	  	str+=name+".setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));\n\t\t";
	    if(jp.getBorder().getClass().toString().compareTo("class javax.swing.border.LineBorder")==0)
	  	str+=name+".setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));\n\t\t";
	  	str+=name+".setLayout(null);\n\t\t";
	    if(jp.isEnabled()==false)
	    str+=name+".setEnabled("+jp.isEnabled()+");\n\t\t";
	    if(jp.isVisible()==false)
	    str+=name+".setVisible("+jp.isVisible()+");\n\t\t";
	    str+=getCodeOfEmbeddedObj(null,tabIndex,tabName);
		if(bflag==true)
		{
			String color = jp.getBackground().toString();			
			str+=name+".setBackground("+getRGB(color,false)+");\n\t\t";
		}
		if(fflag==true)
		{		
			str+=name+".setForeground("+getRGB(picture.getForeground().toString(),true)+");\n\t\t";
		}

	    if(picture.getIcon()!=null)
	    {
	    	ImageIcon imgi=(ImageIcon)picture.getIcon();
			str1=imgi.toString();
			str1=str1.replace('\\','/');
	    	str+=picture.getName()+"=new javax.swing.JLabel(\"Image cannot be displayed\");\n\t\t";
	    	str+=picture.getName()+".setSize("+picture.getWidth()+","+picture.getHeight()+");\n\t\t";
	    	if(jp.getBorder().getClass().toString().compareTo("class javax.swing.border.BevelBorder")==0)
	  		str+=picture.getName()+".setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));\n\t\t";
	        if(jp.getBorder().getClass().toString().compareTo("class javax.swing.border.LineBorder")==0)
	  	    str+=picture.getName()+".setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));\n\t\t";
	  	    str+="ImageIcon "+name+"icon=new ImageIcon(\""+str1+"\");\n\t\t";
	    	str+="\n\t\t"+picture.getName()+".setIcon("+name+"icon);\n\t\t";
	    	str+="\n\t\t"+picture.getName()+".setHorizontalAlignment("+picture.getName()+".LEFT);\n\t\t";
	    	str+="\n\t\t"+picture.getName()+".setVerticalAlignment("+picture.getName()+".TOP);\n\t\t";
			str+=jp.getName()+".add("+picture.getName()+");\n\t\t";		
			if(bflag==true)
	    	{
				String color = jp.getBackground().toString();			
				str+=name+".setBackground("+getRGB(color,false)+");\n\t\t";
			}
			if(fflag==true)
			{		
				str+=name+".setForeground("+getRGB(picture.getForeground().toString(),true)+");\n\t\t";
			}

		}
		return(str);
	}
	
	
	
	public String getDeclaration()
	{
		String str;
		Object o;
		str="javax.swing.JPanel "+jp.getName()+";\n\t";
		if(picture.getIcon()!=null)
		str+="javax.swing.JLabel "+picture.getName()+";\n\t";
		//str+=getDeclnOfEmbeddedObj();
		return(str);
	}
	

	public void setProperties(StringTokenizer s,int tabHeight)
	{
		Integer tid;
		boolean eflag=false;
		int i,j,height=0,x=0,y,width,temp,flag=0;
		String str;
		HashTable h;
		str=s.nextToken();
		jp.setName(str);
		objName=str;
		picture.setName(str+"label");
		str=s.nextToken();
		h=new HashTable();
		tid=(Integer)h.hs.get(str);
		if(tid==null)
		j=0;
		else
		j=tid.intValue();
		do
		{
				switch(j)
				{
						case 1: //Begin
								addEmbeddedObj(s,tabHeight);
								break;
								
						case 48://Backcolor
								bflag=true;
								str=s.nextToken();

								str=str.substring(2,10);

								try{
									// If System color
									if(Integer.parseInt(str.substring(0,2),10)==80)
									{
										setForecolor(jp,Integer.parseInt(str.substring(2,8),16),false);
										sysBkColor=Integer.parseInt(str.substring(2,8),16);
									}
									// Else RGB color	
									else
									{	
										int Br,Bb,Bg;
										try
										{
											Bb=Integer.parseInt(str.substring(2,4),16);
										}
										catch(NumberFormatException Ne)
										{
											Bb=0;//Setting default to Minimum Blue
											eflag=true;				
									    }
									    
									    
								    	try
								    	{
										    Bg=Integer.parseInt(str.substring(4,6),16);
										    System.out.println(Bg);
									    }
										catch(NumberFormatException Ne)
										{
											Bg=0;//Setting default to Minimum green
											eflag=true;				
										}
										
										
										try
										{
											Br=Integer.parseInt(str.substring(6,8),16);
											System.out.println(Br);
										}
										catch(NumberFormatException Ne)
										{
											Br=0;//Setting default to Minimum red
											eflag=true;				
										}	
										jp.setBackground(new java.awt.Color(Br,Bg,Bb));
									}
								}
								
							catch(NumberFormatException Ne){

									setForecolor(jp,15,false);//Setting default color
									sysBkColor=15;
									eflag=true;				
								}
							break;
							
								
						case 59://ForeColor
								fflag=true;
								str=s.nextToken();
								str=str.substring(2,10);
								// If System color
								try{
									if(Integer.parseInt(str.substring(0,2),10)==80)
									{
										setForecolor(jp,Integer.parseInt(str.substring(2,8),16),true);
										sysFrColor=Integer.parseInt(str.substring(2,8),16);
									}
								// Else RGB color	
									else
									{
										int Br,Bb,Bg;
										try{
											Bb=Integer.parseInt(str.substring(2,4),16);
										}
										catch(NumberFormatException Ne){
											Bb=0;//Setting default to Minimum red
											eflag=true;				
										}	
										try{
											Bg=Integer.parseInt(str.substring(4,6),16);
										}
										catch(NumberFormatException Ne){

											Bg=0;//Setting default to Minimum Green
											eflag=true;				
										}	
										try{						
											Br=Integer.parseInt(str.substring(6,8),16);
										}
										catch(NumberFormatException Ne){
											Br=0;//Setting default to Minimum red
											eflag=true;				
										}	
										jp.setForeground(new java.awt.Color(Br,Bg,Bb));
									}
								}
								catch(NumberFormatException Ne){//Catch For Outer IF
									setForecolor(jp,18,true);//Setting default to control i.e; Black color
									sysFrColor=18;
									eflag=true;				
								}							
								break;					
						
									
					
						case 11://Height
								 str=s.nextToken();

								 try
								 {
								 height=Integer.parseInt(str);
								 height=height/15;
								 }
								 catch(NumberFormatException ne)
								 {
								 	eflag=true;
								 }
								 break;
						
						case 12://Left
								str=s.nextToken();
								try
								{
									Integer t = new Integer(str);
								
									if(t.longValue() < 0)			// If Tabbed pane object with -ve coordinates
									x = (int)(t.longValue() + 75000);
								
									else 							// else object with normal coordinates
									x = t.intValue();							
									x=x/15;	
								}
								catch(NumberFormatException ne)
								{
									eflag=true;
								}
								break;
						
						case 13://Top
								str=s.nextToken(); 

								try
								{
									y=Integer.parseInt(str);
									y=y/15;
									jp.setLocation(x,y-tabHeight);
									picture.setLocation(x,y-tabHeight);
								}
								catch(NumberFormatException ne)
								{
									eflag=true;
								}
								break;
						
						case 14://Width
								 str=s.nextToken();
								 try
								 {
								 	width=Integer.parseInt(str);
								 	width=width/15;
								 	jp.setSize(width,height);
								 	picture.setSize(width,height);
								 //	System.out.println(width+"??"+height);
								 }
								 catch(NumberFormatException ne)
								 {
								 	eflag=true;
								 }
								 break;	
								 
						case 50://Enabled
								str=s.nextToken();
								temp=str.compareTo("0");
								if(temp==0);
								{
									jp.setEnabled(false);
								}
								break;
								
						case 57://Visible
								str=s.nextToken();
								temp=str.compareTo("0");
								if(temp==0);
								{
									jp.setVisible(false);
								}
								break;
								
						case 65://BorderStyle
								str=s.nextToken();
								temp=str.compareTo("0");
								if(temp==0)
								{
								
									jp.setBorder(BorderFactory.createEmptyBorder());
								}
								
								break;
						case 47://Appearance=Flat 
								 s.nextToken();
								 jp.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
								 jp.setBackground(new Color(255,255,255));
								 bflag=true;
								 break;
								 
						case 37://BeginProperty
								while(str.compareTo("EndProperty")!=0)
								str=s.nextToken();
								break;
								
						case 22://Index
								 str=s.nextToken();
	            				 String nm=jp.getName();
	            				 nm+="_"+str;
	            				 jp.setName(nm);
	            				 picture.setName(nm+"Label"); 		
			    				 break;
					
						case 81://Picture
								str=s.nextToken();
								try
								{
									
									ImageIcon imgi=createImage(str);
									picture.setIcon(imgi);
								}
								catch(FileNotFoundException fe)
								{
									new msgBox(new JFrame(),"Filenot","FNF");
									fe.printStackTrace();
								}
								catch(IOException ie)
								{
									new msgBox(new JFrame(),"IOEce","Image file cannot be opened\n");
								}
								break;
								
						default://Unidentified Property
								Report+="\nUnMapped Property:\t"+str;
								str=s.nextToken();
								if(str.startsWith("\""))
								removeString(s,str);
								break;
						
								
					}
					str=s.nextToken();
					tid=(Integer)h.hs.get(str);
					if(tid==null)
		    		j=0;
					else
		    		j=tid.intValue();

				}
				while(j!=2);

			System.out.println("Handled picturebox");
				
	}
	
//report of ummapped properties
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

  // This method extracts Picture from the FRX file for the picturebox and creates .jpg image.

    private ImageIcon createImage(String str) throws FileNotFoundException,IOException
	{
		int n,num,i,startl;
		Byte b1,b2,b3,b4;
		FileInputStream fis;
		FileOutputStream fos;
		StringTokenizer s;
		String str1=new String();
		ImageIcon imgi;
		StringTokenizer si=new StringTokenizer(str,":\"");
		String filename=si.nextToken();
		str=si.nextToken();

		startl=Integer.parseInt(str,16);
		fis=new FileInputStream(srcDir+"/"+filename);/*opens the corresponding vb file*/
		fos=new FileOutputStream(destDir+"/Images/"+filename.substring(0,filename.length()-4)+"_"+jp.getName()+".gif");
		n=fis.available();/*gets the number of bytes in the file*/
		byte b[],t[];
		b=new byte[n];
		fis.read(b);/*reads the contents of file in b*/
		fis.close();
		b1=new Byte(b[3+startl]);
		b2=new Byte(b[2+startl]);
		b3=new Byte(b[1+startl]);
		b4=new Byte(b[0+startl]);
		num=b1.intValue();
		str1+=getstr(num);
		num=b2.intValue();
		str1+=getstr(num);
		num=b3.intValue();
		str1+=getstr(num);
		num=b4.intValue();
		str1+=getstr(num);
		num=Integer.parseInt(str1,16);

		t=new byte[num];
		for(i=startl+12;i<startl+num+4;i++)
		{
			t[i-startl-12]=b[i];
		}
		
		fos.write(t);
		fos.close();
		imgi=new ImageIcon(destDir+"/Images/"+filename.substring(0,filename.length()-4)+"_"+jp.getName()+".gif");
		return(imgi);
	}
	
	private String getstr(int num)
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
}