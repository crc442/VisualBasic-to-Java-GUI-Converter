
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

// The cmdbutton class comes into picture when there is VB.CommandButton in source form file.
  
public class cmdbutton extends control
{
	//This variable is used during parsing to set all the properties and is put into the vector for generation phase.
	JButton jb;
	
   //These are used to store source and destination directories.
	String srcdir, destdir;
	
   // This is used as a flag for generator. Since VB uses style
	private boolean style;
	
	// This Boolean variable is to check whether Background colour has been modified
	private boolean bflag;
  
	//This boolean variable is used to store information about Exception occuring while parsing 

	private boolean eflag;
	
	//This is used to store tabIndex associated with Button object
	private int tabIndex;
   
   //This constructor does not any parameters.It is used during generation phase.
	public cmdbutton()
	{
		jb = new JButton();
	}
  
 
 //consrtuctor	
	public cmdbutton(String srcdirectory, String destdirectory)
	{
		srcdir = srcdirectory;
		destdir = destdirectory;
		jb = new JButton();
		style = false;
		bflag=false;
		sysBkColor=15;
	}
	

	public void setProperties(StringTokenizer s, int tabHeight)
	{
		//Initialization
		String curToken="";
		HashTable h = new HashTable();;
		f=new font();
		String str = s.nextToken();
		// Set the name of the button
		jb.setName(str);
		
		int height=0;
		int width=0;
		int left=0;
		int top=0;
		
		// Handle the properties
		while(curToken.compareTo("End")!=0)
		{
							
			curToken=s.nextToken();
			Integer val,val1;
			val=(Integer)h.hs.get(curToken);
			if(val==null)
		     	continue;
		    switch(val.intValue())
			{				
				case 2://End
					System.out.println("Handled All Properties of Command Button: "+getName());
					break;
				case(4):// Caption 
						String caption; 
						caption = getCaption(s);
						int index=caption.indexOf("&");
						if(index!=-1)
						{
							jb.setMnemonic(caption.charAt(index+1));	
							String str1=new String();;
							str1=caption.substring(0,index);
							str1+=caption.substring(index+1,caption.length());
							caption=str1;
						}
						jb.setText(caption);
						break;
					
					case(11):// Height
						curToken=s.nextToken();
						try{
							height=Integer.parseInt(curToken);
							height=height/15;
							}
						catch(NumberFormatException Ne){
							height=0;
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
							width=0;//Default value of width is taken as zero
							eflag=true;		
						}
						jb.setSize(width,height);
						break;
					case(12)://Left
						curToken=s.nextToken();
						try{
							Integer temp = new Integer(curToken);
							left=Integer.parseInt(curToken);
							// If Tabbed pane object with -ve coordinates
							if(temp.longValue() < 0)
							left = (int)(temp.longValue() + 75000);
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
							jb.setLocation(left,top-tabHeight);
						}
						catch(NumberFormatException Ne){
							top=0;//Default Value of Top in case of Exception is taken as zero
							jb.setLocation(left,top);
							eflag=true;				
						}
						break;
						
					case 22://Index
							curToken=s.nextToken();
				            String nm=jb.getName();
				            nm+="_"+curToken;
	        			    jb.setName(nm); 		
			   			 break;
						
					case(48)://BackColor
							bflag=true;
							curToken=s.nextToken();
							curToken=curToken.substring(2,10);
							try{
									// If //System color
								if(Integer.parseInt(curToken.substring(0,2),10)==80)
								{
									setForecolor(jb,Integer.parseInt(curToken.substring(2,8),16),false);
									sysBkColor=Integer.parseInt(curToken.substring(2,8),16);
								}
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
									}
									try{
										Bg=Integer.parseInt(curToken.substring(4,6),16);
									}
									catch(NumberFormatException Ne)
									{
										Bg=0;//Setting default to Minimum green
										eflag=true;				
									}
									try{
										Br=Integer.parseInt(curToken.substring(6,8),16);
									}
									catch(NumberFormatException Ne)
									{									
										Br=0;//Setting default to Minimum red
										eflag=true;				
									}	
									jb.setBackground(new java.awt.Color(Br,Bg,Bb));
								}
							}
							catch(NumberFormatException Ne){
								setForecolor(jb,15,false);//Setting default to Scrollbar color
								sysBkColor=15;
								eflag=true;				
							}
						break;	
						
					
					case 37://BeginProperty
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
						break;
				case(50):// Enabled
						curToken=s.nextToken();
						jb.setEnabled(false);
						break;
					case(52):// MousePointer
						curToken=s.nextToken();
						try{
							mptype=Integer.parseInt(curToken);
						}
						catch(NumberFormatException Ne){
							mptype=1;//Value to be used in case of Exception
							eflag=true;				
						}
						break;
					case(53):// Style
					    s.nextToken();
		   				style = true;
		   				break;
					case(55):// ToolTipText
						curToken=getCaption(s);
						jb.setToolTipText(curToken);
						break;
					case(57):// Visible
						s.nextToken();
						jb.setVisible(false);
						break;
					case(81):// Picture
						curToken=s.nextToken();
		   				try
						{
							ImageIcon imgi=makeImage(curToken);
							jb.setIcon(imgi);
						}
						catch(FileNotFoundException fe)
						{
						}
						catch(IOException ie)
						{
						}						
						break;
				
			}		
		}	
	}
	
  // This method returns 'Declaration' for the JButton object.
     
	public String getDeclaration()
	{
		return("javax.swing.JButton "+jb.getName()+";\n\t");
	}
	
  // This method generates the code for JButton object.
  
	public String getCode()
	{
		// Temporary String to store name  
		String name = new String();
		name=jb.getName();
		
		// String to store code
		String code=new String();
		Font f;
		
		// Initialisation code
		code+="\t"+name+" = new javax.swing.JButton();\n\t\t";
		
		// Caption
		if(!jb.getText().equals(""))
			code+=name+".setText("+jb.getText()+");\n\t\t";
		
		// Set position & size
		code+=name+".setBounds("+jb.getX()+","+jb.getY()+","+jb.getWidth()+","+jb.getHeight()+");\n\t\t";
		
		// Fill background color
		if(style)
			if(bflag)
				code+=name+".setBackground("+getRGB(jb.getBackground().toString(),false)+");\n\t\t";
		
		// Set the font for the button
		f=jb.getFont();
		if(f.getName().endsWith("\""))
			code+=name+".setFont(new Font("+f.getName()+","+f.getStyle()+","+f.getSize()+"));\n\t\t";
		else
			code+=name+".setFont(new Font(\""+f.getName()+"\","+f.getStyle()+","+f.getSize()+"));\n\t\t";
		
		// Set Mnemonic
		if(jb.getMnemonic()!=0)
			code+=name+".setMnemonic("+jb.getMnemonic()+");\n\t\t";
		
		// If not enabled
		if(jb.isEnabled()==false)
			code+=name+".setEnabled(false);\n\t\t";
			
		// Set the tooltip text if any
		if(!(jb.getToolTipText()==null))
			code+=name+".setToolTipText("+jb.getToolTipText()+");\n\t\t";
		
		// If not visible
		if(jb.isVisible()==false)
			code+=name+".setVisible(false);\n\t\t";			
		
		if(mptype!=0)
		{
			String mp=new String();
			mp=getMousePointer(mptype);
			if(mp!=null)
			code+=name+".setCursor(new Cursor("+mp+"));\n\t\t";
		}
		
		// Set Icon if any & style is true
		if(!(jb.getIcon()==null) && style)
		   code+=name+".setIcon(new javax.swing.ImageIcon(\""+jb.getIcon()+"\"));\n\t\t";
	    
		return(code);
	}
	
 
	
  // This method creates the image from the .frx file.
   
	public ImageIcon makeImage(String str) throws FileNotFoundException,IOException
	{
		int n,num,i,startl;
		Byte b1,b2,b3,b4;		
		FileInputStream fis;
		FileOutputStream fos;
		StringTokenizer s;
		String str1 = new String();
		String frxFile;
		ImageIcon imgi;
		byte b[],t[];

		// Get the absolute location of the frxFile and open it.
		StringTokenizer si=new StringTokenizer(str,":,\"");
		frxFile = srcdir+"/"+si.nextToken();
		fis=new FileInputStream(frxFile);

		//Get the starting address of the image
		str=si.nextToken();
		startl=Integer.parseInt(str,16);

		// 3. Remove source directory name and '.frx' extension from frxFile name
		int index = frxFile.lastIndexOf('/');
		frxFile=frxFile.substring(index+1);
		frxFile=frxFile.substring(0,frxFile.length()-4);

		// Create an output file in the 'Images' folder in the destination directory.
		
		fos=new FileOutputStream(destdir+"/Images/"+frxFile+"_"+jb.getName()+".gif");

		// Read the frxFile bytes in a byte[] array
		n=fis.available();
		b=new byte[n];
		fis.read(b);
		fis.close();
		
		//Read the no.of bytes to be read from the current position to where the image ends.
		b1=new Byte(b[3+startl]); // MSB
		b2=new Byte(b[2+startl]);
		b3=new Byte(b[1+startl]);
		b4=new Byte(b[0+startl]); // LSB
		num=b1.intValue();
		str1+= getStr(num);
		num=b2.intValue();
		str1+=getStr(num);
		num=b3.intValue();
		str1+= getStr(num);
		num=b4.intValue();
		str1+= getStr(num);
		num=Integer.parseInt(str1,16);
		
		// Make a byte array of image size and read each byte from source byte array to destination
		// byte array.
		t=new byte[num];
		for(i=startl+12;i<startl+num+4;i++)
		{
			t[i-startl-12]=b[i];
		}
		
		//Write Image bytes to the image file.
		fos.write(t);
		fos.close();
		
		//Create Image Icon of the generated Image
		imgi=new ImageIcon(destdir+"/Images/"+frxFile+"_"+jb.getName()+".gif");
		
		//Return the Image Icon.
		return(imgi);
	}

  //This method takes integer value and returns hexadecimal equivalent of it. 
    
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
		
 // This mehod gives the name of the Button.
   
	public String getName()
	{
		return(jb.getName());
		
	}

}