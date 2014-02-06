import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

//The frame class comes into picture when there VB.Frame in source form file

class frame	extends compositeObject
{
	//used during parsing to set the Title of frame
	String title;
	
	//used during parsing
	private JPanel jc;
	
	private JLabel jl;
	
	//used when an imageicon is set for Frame
	private boolean picture;
	
	
    //stores source directory	
	private String srcdir;
   
   //stores destination directory.
   private String destdir;
	
	//used to check whether Background colour has been modified
	private boolean bflag;
  
  	//to check whether Foreground colour has been modified
	private boolean fflag;
	
  	//to check whether Alignment property of checkbox has been changed from default value
	private boolean alignment;
	
  //used to store the Mnemonic Character 
	private char mnemonic;
  
	//used to store information about appearance of control i.e; 3D or Flat 
	private boolean appearance;
	
  	//used to store information about style of control i.e; checkbox or togglebutton 
	private boolean style;
  
	//used to store information about Exception occuring while parsing 
	private boolean eflag;
	
 //constructor
	public frame(String srcDirs,String destDirs)
	{
		jc=new JPanel();
		jl=new JLabel();
		rbcnt=0;
		picture=false;
		v=new Vector();
		jc.setLayout(null);
		srcDir=srcDirs;
		destDir=destDirs;
		jc.setBorder(BorderFactory.createTitledBorder(""));
	}
	
	
	public void setProperties(StringTokenizer s, int tabHeight)
	{
		
		
		String str="";
		title="";
	
		int height=0,width=0,left=0,top;
		Integer val,val1;
		str=s.nextToken();

		jc.setName(str);
		objName=str;

		HashTable h=new HashTable();


		while(str.compareTo("End")!=0)
		{
	
			str=s.nextToken();
			val=(Integer)h.hs.get(str);
		
			if(val==null)
			continue;
			switch(val.intValue())
			{

				case(1)://Begin
				addEmbeddedObj(s,0);
				break;	
				case 2://End
				System.out.println("Handled All Properties of frame: "+getName());
				break;		
	
				case 4://Caption
			
				str=s.nextToken();
				while(str.indexOf("\"",2)==-1)
				str+=" "+s.nextToken();
				title=str;
				jc.setBorder(BorderFactory.createTitledBorder(title));
				break;
				
			case 11://Height
			
				str=s.nextToken();
				try{
				height=Integer.parseInt(str);
				height=height/15;
			}
			catch(NumberFormatException Ne){
					height=0;
					eflag=true;		
				}
				break;
			
			case 14://Width
			
				
				str=s.nextToken();
				try{
					width=Integer.parseInt(str);
					width=width/15;
				}
				catch(NumberFormatException Ne){
				width=0;//Default value of width is taken as zero
				eflag=true;		
				}
				jc.setSize(width,height);
			break;							
			
			
			case 12://Left
			
				str=s.nextToken();
				try{
				Integer temp = new Integer(str);
				// If Tabbed pane object with -ve coordinates
				if(temp.longValue() < 0)
					left = (int)(temp.longValue() + 75000);
				// else object with normal coordinates
				else 
					left = temp.intValue();						
				left=left/15;	
			}
			catch(NumberFormatException Ne){
					left=0;
					eflag=true;		
				}
				break;
			
			
			case 13://Top
			
				str=s.nextToken();
				try{
				top=Integer.parseInt(str);
				top=top/15;
				}
			catch(NumberFormatException Ne){
					top=0;
					eflag=true;		
				}
				jc.setLocation(left,top-tabHeight);
				
				break;
			
			case 22://Index
				str=s.nextToken();
	            String nm=jc.getName();
	            nm+="_"+str;
	            jc.setName(nm); 		
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
				
			case(48)://BackColor
	        	bflag=true;
				str=s.nextToken();
				str=str.substring(2,10);
				try{
					// If System color
					if(Integer.parseInt(str.substring(0,2),10)==80)
					{
						setForecolor(jc,Integer.parseInt(str.substring(2,8),16),false);
						sysBkColor=Integer.parseInt(str.substring(2,8),16);
					}
					// Else RGB color	
					else
					{	
						int Br,Bb,Bg;
						try{
							Bb=Integer.parseInt(str.substring(2,4),16);
						}
						catch(NumberFormatException Ne){
							Bb=0;
							eflag=true;				
						}
						try{
							Bg=Integer.parseInt(str.substring(4,6),16);
						}
						catch(NumberFormatException Ne){
							Bg=0;
							eflag=true;				
						}
						try{
							Br=Integer.parseInt(str.substring(6,8),16);
						}
						catch(NumberFormatException Ne){
							Br=0;
							eflag=true;				
						}	
						jc.setBackground(new java.awt.Color(Br,Bg,Bb));
					}
				}
				catch(NumberFormatException Ne){//Catch For outer IF 
					setForecolor(jc,0,false);
					sysBkColor=0;
					eflag=true;				
				}
			break;	
			
			case(59)://ForeColor
				fflag=true;
				str=s.nextToken();
				str=str.substring(2,10);
				// If System color
				try{
					if(Integer.parseInt(str.substring(0,2),10)==80)
					{
						setForecolor(jc,Integer.parseInt(str.substring(2,8),16),true);
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
							Bb=0;
							eflag=true;				
						}	
						try{
							Bg=Integer.parseInt(str.substring(4,6),16);
						}
						catch(NumberFormatException Ne){
							Bg=0;
							eflag=true;				
						}	
						try{						
							Br=Integer.parseInt(str.substring(6,8),16);
						}
						catch(NumberFormatException Ne){				
							Br=0;//Setting default to Minimum red
							eflag=true;				
						}	
						jc.setForeground(new java.awt.Color(Br,Bg,Bb));
					}
				}
				catch(NumberFormatException Ne){//Catch For Outer IF
					setForecolor(jc,15,true);
					sysFrColor=15;
					eflag=true;				
				}						
				break;
			case (53)://Style
				style=true;
				str=s.nextToken();
				break;
				
			case (81)://Picture
				str=s.nextToken();
		   		try
				{
					javax.swing.ImageIcon imgi=makeImage(str,0);
					jl.setIcon(imgi);
					picture=true;
				}
				catch(FileNotFoundException fe)
				{

				}
				catch(IOException ie)
				{

				}						
				break;
				
		
				
			case 55://ToolTipText
				str=getCaption(s);
	            jc.setToolTipText(str); 		
	            break;
	        
	        case 57://Visible
	        	str=s.nextToken();
	        	jc.setVisible(false);
	        	break;
	        	
	        case 50://Enabled
	        	str=s.nextToken();
	        	jc.setEnabled(false);
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
			}
		}
	}
	

	public String getName()
	{
		return(jc.getName());
	}
	
	

//gives declaration
	public String getDecln()
	{
		String str=new String();
		str+="JPanel "+jc.getName()+";\n\t";
		if(picture==true)
			str+="javax.swing.JLabel "+jc.getName()+"_icon;\n\t";
		str+=getDeclnOfEmbeddedObj();	
		return(str);
	}


//int to hex conversion
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
			return(str);
		}
	

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

		//Get the absolute location of the frxFile and open it.
		java.util.StringTokenizer si=new java.util.StringTokenizer(str,":,\"");
		frxFile = srcdir+"/"+si.nextToken();
	//	System.out.println(frxFile);
		fis=new FileInputStream(frxFile);

		//Get the starting address of the image
		str=si.nextToken();
		startl=Integer.parseInt(str,16);

		//Remove source directory name and '.frx' extension from frxFile name
		int index = frxFile.lastIndexOf('/');
		frxFile=frxFile.substring(index+1);
		frxFile=frxFile.substring(0,frxFile.length()-4);

		//Create an output file in the 'Images' folder in the destination directory.
		if(icontype==0)
			fos=new FileOutputStream(destdir+"/Images/"+frxFile+"_"+jc.getName()+".gif");
		else if(icontype==1)
			fos=new FileOutputStream(destdir+"/Images/"+frxFile+"_"+jc.getName()+"DisabledIcon.gif");
		else
			fos=new FileOutputStream(destdir+"/Images/"+frxFile+"_"+jc.getName()+"SelectedIcon.gif");
			
		//Read the frxFile bytes in a byte[] array
		n=fis.available();
		b=new byte[n];
		fis.read(b);
		fis.close();
		
		// Read the no.of bytes to be read from the current position to where the image ends.
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
		
		//Make a byte array of image size and read each byte from source byte array to destination byte array.
		t=new byte[num];
		for(i=startl+12;i<startl+num+4;i++)
		{
			t[i-startl-12]=b[i];
		}
		
		//Write Image bytes to the image file.
		fos.write(t);
		fos.close();
		
		// Create Image Icon of the generated Image
		if(icontype==0)
			imgi=new javax.swing.ImageIcon(destdir+"/Images/"+frxFile+"_"+jc.getName()+".gif");
		else if(icontype==1)
			imgi=new javax.swing.ImageIcon(destdir+"/Images/"+frxFile+"_"+jc.getName()+"DisabledIcon.gif");
		else
			imgi=new javax.swing.ImageIcon(destdir+"/Images/"+frxFile+"_"+jc.getName()+"SelectedIcon.gif");
			
		//Return the Image Icon.
		return(imgi);
	}
  

//generate code
	public String getCode(String tabName) //throws ArrayIndexOutOfBoundsException
	{
		Integer vl=new Integer(0);
		HashTable h=new HashTable();	
		int count=0,i;
		String name,str="",Fname;
		Fname=jc.getName();

		str+="\t"+Fname+"=new JPanel();\n\t\t";
		str+=Fname+".setLayout(null);\n\t\t";
		str+=Fname+".setLocation("+jc.getX()+","+jc.getY()+");\n\t\t";
		str+=Fname+".setSize("+jc.getWidth()+","+jc.getHeight()+");\n\t\t";
		

		str+=Fname+".setFont("+f.getFontCode()+");\n\t";

		if(jc.getBorder()!=null)
		{
			if(jc.getBorder().getClass().toString().compareTo("class javax.swing.border.TitledBorder")==0)
	  		{
	  			if(appearance==true)
	  			str+=Fname+".setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));\n\t\t";		
				else if(title.compareTo("")==0)
				str+=Fname+".setBorder(BorderFactory.createTitledBorder(\"\"));\n\t\t";			
				else
				str+=Fname+".setBorder(BorderFactory.createTitledBorder("+title+"));\n\t\t";			
			}
		}
		
		if(!(jl.getIcon()==null) && style)
			{
				str+=Fname+"_icon=new JLabel();\n\t\t";
				str+=Fname+"_icon.setIcon(new javax.swing.ImageIcon(\""+jl.getIcon()+"\"));\n\t\t";
			}
		
		if(jc.getToolTipText()!=null)
		str+=Fname+".setToolTipText("+jc.getToolTipText()+");\n\t\t";
		
		if(jc.isVisible()==false)
			str+=Fname+".setVisible(false);\n\t\t";
		
		if(jc.isEnabled()==false)
			str+=Fname+".setEnabled(false);\n\t\t";
		
		
		
		if(bflag==true)
			str+=Fname+".setBackground("+getRGB(jc.getBackground().toString(),false)+");\n\t";
		
		if(fflag==true)
			str+=Fname+".setForeground("+getRGB(jc.getForeground().toString(), true)+");\n\t";
		
		if(mptype!=0)
		{
			String mp=new String();
			mp=getMousePointer(mptype);
			if(mp!=null)
			str+=Fname+".setCursor(new Cursor("+mp+"));\n\t\t";
		}
		
		
		str+=getCodeOfEmbeddedObj(null,tabIndex,tabName);
		
		
		return (str);		
		
	}
		
}
