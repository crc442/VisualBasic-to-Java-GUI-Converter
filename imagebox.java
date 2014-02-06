import java.util.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;

//The imagebox class comes into picture when there is VB.Image in source form file.
  
class imagebox extends control
{

	JLabel jl;
	
	
	String Report;

	int BORDER_STYLE;
	
	String srcdir, destdir;
	

	public imagebox(String srcDir,String destDir)
	{
		jl=new JLabel();
		srcdir=srcDir;
		destdir=destDir;
		Report="";
	}
	
	
	public String getName()
	{
		return(jl.getName());
	}

	
	public String getDeclaration()
	{
		String str=new String();
	//	Object o;
		str+="JLabel "+jl.getName()+";\n\t";
		str+="ImageIcon "+jl.getName()+"icon;\n\t";
		return(str);
	}


	
	public String getCode()
	{

		String name,str,str1;
		name=jl.getName();
		str="\t\t"+name+"=new JLabel();\n\t\t";
		str+=name+".setLocation("+jl.getX()+","+jl.getY()+");\n\t\t";
		str+=name+".setSize("+jl.getWidth()+","+jl.getHeight()+");\n\t\t";
	    str+=name+".setEnabled("+jl.isEnabled()+");\n\t\t";
	    str+=name+".setVisible("+jl.isVisible()+");\n\t\t";
	    
	    if(jl.getIcon()!=null)
	    {
	    	ImageIcon imgi=(ImageIcon)jl.getIcon();
			str1=imgi.toString();
			str1=str1.replace('\\','/');
	   		str+="ImageIcon "+name+"icon=new ImageIcon(\""+str1+"\");";
	   		
	    str+="\n\t\tImage I"+jl.getName()+";";
	    str+="\n\t\tI"+jl.getName()+"="+name+"icon.getImage();";
	    str+="\n\t\tI"+jl.getName()+"=I"+getName()+".getScaledInstance("+jl.getWidth()+","+jl.getHeight()+",I"+jl.getName()+".SCALE_SMOOTH);";
	    str+="\n\t\t"+name+"icon.setImage(I"+jl.getName()+");";
	    str+="\n\t\t"+name+".setIcon("+name+"icon);\n\t\t";
	    str+="\n\t\t"+name+".setHorizontalAlignment("+name+".LEFT);\n\t\t";
	    str+="\n\t\t"+name+".setVerticalAlignment("+name+".TOP);\n\t\t";
	 	}
	    if(BORDER_STYLE==1)
	    {
	    	str+="\n\t\tBorder "+name+"Border=BorderFactory.createBevelBorder(BevelBorder.LOWERED);";
	    	str+="\n\t\t"+name+".setBorder("+name+"Border);";	
	    }
	    return(str);
	}



	public void setProperties(StringTokenizer s,int tabHeight)
	{
		Integer tid;
		int i,j,height=0,x=0,y=0,width=0,temp;
		String str;
		HashTable h;
		str=s.nextToken();
		jl.setName(str);
		str=s.nextToken();
		h=new HashTable();
		tid=(Integer)h.hs.get(str);
		while(tid==null)
		{
				str=s.nextToken();
				tid=(Integer)h.hs.get(str);
		}
		j=tid.intValue();
		do
		{
				
			switch(j)
			{
						
						
								
						case 11://Height
								 str=s.nextToken();
								 height=Integer.parseInt(str);
								 height=height/15;
								 break;
						
						case 12://left
						
								str=s.nextToken(); 
								x=Integer.parseInt(str);
								if(x<0)
								x+=75000;
								x=x/15;
								break;
						
						case 13://top
						
								str=s.nextToken(); 
								y=Integer.parseInt(str);
								y=y/15;
								jl.setLocation(x,y-tabHeight);
								break;
						
						case 14://Width 
						
								str=s.nextToken();
								 width=Integer.parseInt(str);
								 width=width/15;
								 jl.setSize(width,height);
								 break;	
						
						case 50://Enabled
						
								str=s.nextToken();
								temp=str.compareTo("0");
								if(temp==1);
								{
									jl.setEnabled(false);
								}
								break;
						case 57://Visible
						
								str=s.nextToken();
								temp=str.compareTo("0");
								if(temp==1);
								{
									jl.setVisible(false);
								}
								break;
						case 81://Picture
								str=s.nextToken();
							
								try
								{
								ImageIcon imgi=makeImage(str);
								jl.setIcon(imgi);
								}
								catch(FileNotFoundException fe)
								{

								}
									catch(IOException ie)
								{

								}
								break;
								
						
						case 22://Index
							str=s.nextToken();
	    	        		String nm=jl.getName();
		    	   		    nm+="_"+str;
		        		    jl.setName(nm); 		
				    		break;
						case 71://BorderStyle
								str=s.nextToken(); 
								BORDER_STYLE=1;
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
			while(tid==null)
		    {
				str=s.nextToken();
				tid=(Integer)h.hs.get(str);
		    }
		    j=tid.intValue();
		}
		while(j!=2);
		System.out.println("Handled image box");
	}



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
 
	
	//This method creates the image from the .frx file.
 
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

		//Get the absolute location of the frxFile and open it.
		StringTokenizer si=new StringTokenizer(str,":,\"");
		frxFile = srcdir+"/"+si.nextToken();
		fis=new FileInputStream(frxFile);

		// Get the starting address of the image
		str=si.nextToken();
		startl=Integer.parseInt(str,16);

		//Remove source directory name and'.frx' extension from frxFile name
		int index = frxFile.lastIndexOf('/');
		frxFile=frxFile.substring(index+1);
		frxFile=frxFile.substring(0,frxFile.length()-4);

		// Create an output file in the 'Images' folder in the destination directory.
		fos=new FileOutputStream(destdir+"/Images/"+frxFile+"_"+jl.getName()+".gif");


		//Read the frxFile bytes in a byte[] array
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
		imgi=new ImageIcon(destdir+"/Images/"+frxFile+"_"+jl.getName()+".gif");
		
		// Return the Image Icon.
		return(imgi);
	}


//int to hex conversion	
	public String getStr(int num)
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