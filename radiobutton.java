import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;


//The radiobutton class comes into picture when there  is VB.RadioButton in source form file.
 class radiobutton extends control
{
	
	//used during Parsing
	JRadioButton jrb;
		
	String Report;
	
   //used to store source and destination directories.
	String srcdir, destdir;
   
   // This is used as a flag for generator for style property. 
	private boolean style;
	
	
	// This variable is to check whether Background colour has been modified	
	private boolean bflag;
	
  //variable is to check whether Foreground colour has been modified
	private boolean fflag;
	
  //This Boolean variable is to check Alignment property of checkbox
	private boolean alignment;
	

  // This variable is used to store the Mnemonic Character 
	private char mnemonic;
	
  //This variable is used to store information about appearance of control i.e; 3D or Flat 
	private boolean appearance;
	
  
  //used to store information about Exception occuring while parsing 
  	private boolean eflag;
	
	private int tabIndex;
   
   
   //constructor
	public radiobutton()
	{
		jrb = new JRadioButton();
	}
  
 	
 	//constructor	
	public radiobutton(String srcdirectory, String destdirectory)
	{
		srcdir = srcdirectory;
		destdir = destdirectory;
		jrb = new JRadioButton();
		style = false;
		Report="";
	}
	
  // This method is called whenever VB.RadioButton appears in the frm file.

	public void setProperties(StringTokenizer s,int tabHeight)
	{
		
		System.out.println("Inside setprop");
		String str="";
		int height=0,width=0,left=0,top=0;
		Integer val,val1;

		
		HashTable h=new HashTable();
		
		String name=new String(s.nextToken());
		jrb.setName(name);
		
		while(str.compareTo("End")!=0)
		{
			str=s.nextToken();
			val=(Integer)h.hs.get(str);
			if(val==null)
			continue;
			
			System.out.println("val="+val);
			switch(val.intValue())
			{
			
				
				case 2://End
					System.out.println("Handled All Properties of radiobutton: "+getName());
					break;
					
				case 4://Caption
				curToken=getCaption(s);
				
				
				//Code is for extracting Mnemonic From the Caption
				if(curToken.lastIndexOf("&")!=-1)
				{
					mnemonic = curToken.charAt(curToken.lastIndexOf("&") + 1);
					String caption=new String();
					while(curToken.indexOf("&")!=-1)
					{
						curToken=curToken.substring(0,curToken.indexOf("&"))+curToken.substring(curToken.indexOf("&")+1,curToken.length());
					}
				}
				jrb.setText(curToken);
				System.out.println("curToken is: "+curToken);
				break;
				
				

			
			case 11://Height
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
			
			case 14://Width
				curToken=s.nextToken();
				try{
					width=Integer.parseInt(curToken);
					width=width/15;
				}
				catch(NumberFormatException Ne){
					width=0;//Default value of width is taken as zero
					eflag=true;		
				}
				
				jrb.setSize(width,height);
				break;
			
			case 12://Left
				curToken=s.nextToken();
				try{
					left=Integer.parseInt(curToken);
				
					if(left<0)				// If Tabbed pane object with -ve coordinates
					left+=75000;
				
					left=left/15;			//else
				}
				catch(NumberFormatException Ne){
					left=0;//Default value of left is taken as zero
					eflag=true;		
				}
				break;
			
			case 13://Top
				curToken=s.nextToken();
				try{
					top=Integer.parseInt(curToken);
					top=top/15;
					jrb.setLocation(left,top-tabHeight);
				}
				
				catch(NumberFormatException Ne){
					jrb.setLocation(left,top);
					eflag=true;				
				}
				break;
				
				
				
				//Index Appears in the case of Array of controls
			case 22://Index
				curToken=s.nextToken();
	            String nm=jrb.getName();
	            nm+="_"+curToken;
	            jrb.setName(nm); 		
			    break;
			
			case 47://Appearance i.e; 3D or Flat
				curToken=s.nextToken();
				appearance=true;
				break;
			
			case 52://MousePointer
				curToken=s.nextToken();
				try{
					mptype=Integer.parseInt(curToken);
				}
				catch(NumberFormatException Ne){
					mptype=1;
					eflag=true;				
				}
				
				break;
			
		  case 90://Alignment
	        	curToken=s.nextToken();
	        	alignment=true;
	        	break;
	        	
	        	
	      case 91://Value
	        	curToken=s.nextToken();
	        	jrb.setSelected(true);
	        	break;
	        	
	     case (81)://Picture
				curToken=s.nextToken();

		   		try
				{
					javax.swing.ImageIcon imgi=makeImage(curToken,0);
					jrb.setIcon(imgi);
				}
				catch(FileNotFoundException fe)
				{

				}
				catch(IOException ie)
				{

				}						
				break;
				
			
			case (100)://DisabledPicture
				curToken=s.nextToken();
				
		   		try
				{
					javax.swing.ImageIcon imgi=makeImage(curToken,1);
					jrb.setDisabledIcon(imgi);
				}
				catch(FileNotFoundException fe)
				{

				}
				catch(IOException ie)
				{

				}						
				break;
				
			
			case (101)://DownPicture
				curToken=s.nextToken();

		   		try
				{
					javax.swing.ImageIcon imgi=makeImage(curToken,2);
					jrb.setSelectedIcon(imgi);
				}
				catch(FileNotFoundException fe)
				{

				}
				catch(IOException ie)
				{

				}						
				break;
			
		 case(48)://BackColor
	        	bflag=true;
				curToken=s.nextToken();

				curToken=curToken.substring(2,10);

				try{
					// If System color
					if(Integer.parseInt(curToken.substring(0,2),10)==80)
					{
						setForecolor(jrb,Integer.parseInt(curToken.substring(2,8),16),false);
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
							//System.out.println("Number Format Exception in CheckBox "+getName()+":BackColor(Green Component) property");
							//System.out.println("Setting Green value to 0");
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
						jrb.setBackground(new java.awt.Color(Br,Bg,Bb));
							System.out.println("bg is set");
					}
				}
				catch(NumberFormatException Ne){

					System.out.println("Setting BackColor to ScrollBar Color.");
					setForecolor(jrb,0,false);  //Setting default color
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
						setForecolor(jrb,Integer.parseInt(curToken.substring(2,8),16),true);
						sysFrColor=Integer.parseInt(curToken.substring(2,8),16);
						System.out.println("ForeColor for radiobutton is set");
					}
					// Else RGB color	
					else
					{
						int Br,Bb,Bg;
						try{
							Bb=Integer.parseInt(curToken.substring(2,4),16);
						}
						catch(NumberFormatException Ne){
							System.out.println("Number Format Exception in CheckBox "+getName()+":ForeColor(Blue Component) property");
							System.out.println("Setting Blue value to 0");
							Bb=0;	//Setting default to Minimum red
							eflag=true;				
						}	
						try{
							Bg=Integer.parseInt(curToken.substring(4,6),16);
						}
						catch(NumberFormatException Ne){
							System.out.println("Number Format Exception in CheckBox "+getName()+":ForeColor(Green Component) property");
							System.out.println("Setting Green value to 0");
							Bg=0;	//Setting default to Minimum Green
							eflag=true;				
						}	
						try{						
							Br=Integer.parseInt(curToken.substring(6,8),16);
						}
						catch(NumberFormatException Ne){
							System.out.println("Number Format Exception in CheckBox "+getName()+":ForeColor(Red Component) property");
							System.out.println("Setting Red value to 0");
							Br=0;	//Setting default to Minimum red
							eflag=true;				
						}	
						jrb.setForeground(new java.awt.Color(Br,Bg,Bb));
						System.out.println("ForeColor for radiobutton is set");
					}
				}
				catch(NumberFormatException Ne){
					System.out.println("Number Format Exception in CheckBox "+getName()+":ForeColor(System Color) property");
					System.out.println("Setting ForeColor to Black Color.");
					setForecolor(jrb,15,true);		//Setting default to control
					sysFrColor=15;
					eflag=true;				
				}						
				break;
				
			case(50):// Enabled
						curToken=s.nextToken();
						System.out.println("Enabled False = "+curToken);
						jrb.setEnabled(false);
						break;
						
			case(53):// Style
						// Since this property is displayed only 
						// if Style is 1. Hence we are not checking 
						// whether it is 1 or not.
						s.nextToken();// Skip 1
		   				s.nextToken();// Skip 'Graphical 
		   				style = true;
		   				break;
		   				
			case(55):// ToolTipText
						curToken=s.nextToken();
		   				while(!curToken.endsWith("\""))
							curToken+=" "+s.nextToken();
						System.out.println("Tool Tip Text = "+curToken);
						jrb.setToolTipText(curToken);
						break;
						
			case(57):// Visible
						s.nextToken();
						s.nextToken();
		   				jrb.setVisible(false);
		   				System.out.println("Visible = false "+curToken);
						break;
						
		
					
			case 37://BeginProperty
			
				str=s.nextToken();
				val1=(Integer)h.hs.get(str);
				if(val1==null)
				continue;
				switch(val1.intValue())
				{
					
					case 39://Font
								f.setFontProperty(s);		
								break;	
				}
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
	
// This Function is used to send report of UnMapped Proterties 
  
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

 // This method returns 'Declaration' for the JRadioButton object.

	public String getDeclaration()
	{
		if(style==true)
		return("javax.swing.JToggleButton "+jrb.getName()+";\n\t");
		else
		return("javax.swing.JRadioButton "+jrb.getName()+";\n\t");
	}
	
	
	
//This method takes integer value and returns hexadecimal equivalent of it. 
	public String getStr(int num)
	{
			System.out.println(num);
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
			System.out.println(str);
			return(str);
		}
 
 //    * This method creates the image from the .frx file.

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

		// 1. Get the absolute location of the frxFile and open it.
		java.util.StringTokenizer si=new java.util.StringTokenizer(str,":,\"");
		frxFile = srcdir+"/"+si.nextToken();
		System.out.println(frxFile);
		fis=new FileInputStream(frxFile);

		// 2. Get the starting address of the image
		str=si.nextToken();
		System.out.println(str+"\n");
		startl=Integer.parseInt(str,16);

		// 3. Remove source directory name and '.frx' extension from frxFile name
		int index = frxFile.lastIndexOf('/');
		frxFile=frxFile.substring(index+1);
		frxFile=frxFile.substring(0,frxFile.length()-4);

		
		// 4. Create an output file in the 'Images' folder in the destination directory.
		if(icontype==0)
			fos=new FileOutputStream(destdir+"/Images/"+frxFile+"_"+jrb.getName()+".gif");
		else if(icontype==1)
			fos=new FileOutputStream(destdir+"/Images/"+frxFile+"_"+jrb.getName()+"DisabledIcon.gif");
		else
			fos=new FileOutputStream(destdir+"/Images/"+frxFile+"_"+jrb.getName()+"SelectedIcon.gif");
		System.out.println("This is destination of the image"+destdir+"/Images/"+frxFile+"_"+jrb.getName()+".gif");

		// 5. Read the frxFile bytes in a byte[] array
		n=fis.available();
		b=new byte[n];
		fis.read(b);
		fis.close();
		
		// 6. Read the no.of bytes to be read from the current position to where the image ends.
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
		System.out.println(str1);
		num=Integer.parseInt(str1,16);
		
		// 7. Make a byte array of image size and read each byte from source byte array to destination byte array.
		t=new byte[num];
		for(i=startl+12;i<startl+num+4;i++)
		{
			t[i-startl-12]=b[i];
		}
		
		// 8. Write Image bytes to the image file.
		fos.write(t);
		fos.close();
		
		// 9. Create Image Icon of the generated Image
		if(icontype==0)
			imgi=new javax.swing.ImageIcon(destdir+"/Images/"+frxFile+"_"+jrb.getName()+".gif");
		else if(icontype==1)
			imgi=new javax.swing.ImageIcon(destdir+"/Images/"+frxFile+"_"+jrb.getName()+"DisabledIcon.gif");
		else
			imgi=new javax.swing.ImageIcon(destdir+"/Images/"+frxFile+"_"+jrb.getName()+"SelectedIcon.gif");
			
		// 10. Return the Image Icon.
		return(imgi);
	}
  
  // This method generates the code for JRadioButton object.
 
	public String getCode()
	{
		String name,str="";
		name=jrb.getName();

		
		if(style==true)
			str+="\t"+name+"=new JToggleButton();\n\t\t";
		else
			str+="\t"+name+"=new JRadioButton();\n\t\t";
		
		str+=name+".setText("+jrb.getText()+");\n\t\t";
		str+=name+".setLocation("+jrb.getX()+","+jrb.getY()+");\n\t\t";
		str+=name+".setSize("+jrb.getWidth()+","+jrb.getHeight()+");\n\t\t";
		
		
		if(bflag)
			str+=name+".setBackground("+getRGB(jrb.getBackground().toString(),false)+");\n\t\t";
			
		if(fflag)
			str+=name+".setForeground("+getRGB(jrb.getForeground().toString(),true)+");\n\t\t";
			
		
		str+=name+".setFont("+f.getFontCode()+");\n\t\t";
		if(jrb.getMnemonic()!=0)
			str+=name+".setMnemonic("+jrb.getMnemonic()+");\n\t\t";
		
		if(jrb.isEnabled()==false)
			str+=name+".setEnabled(false);\n\t\t";
		
		if(!(jrb.getToolTipText()==null))
			str+=name+".setToolTipText("+jrb.getToolTipText()+");\n\t\t";
		
		if(jrb.isVisible()==false)
			str+=name+".setVisible(false);\n\t\t";			
		
	
		if(!(jrb.getIcon()==null) && style)
		   str+=name+".setIcon(new javax.swing.ImageIcon(\""+jrb.getIcon()+"\"));\n\t\t";
		
		return (str);
	}

	
	// This function is used during the code generation process. it gives the tab index
	public int getTabIndex()
	{
		return(tabIndex);
	}
	

 //This function is used during the code generation process
	public String getName()
	{
		return(jrb.getName());
		
	}


}

