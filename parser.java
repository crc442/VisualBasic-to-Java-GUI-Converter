import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

//parser opens the *.frm file and reads the contents of that file
public class parser
{
	  
		//This is initialised when Form object is detected in *.frm file		
		public form frm;
		
	  //stores the path of the source file directory
		String srcDir;
 
	    //stores the path of the Destination file directory
		String destDir;
	  
	  
	  	//stores the report
		private String report="";
		
		
		
	  //This function detects whether Form object is present. If it is present then initialises the form object
		void switchcase(StringTokenizer s)
		{
			String str=new String();
			Integer val;
			HashTable h=new HashTable();
			int counter=0;
			String heading;
			while(s.hasMoreTokens()) 
			{
				str=s.nextToken();

				val=(Integer)h.hs.get(str);
				if(val==null)
				continue;
				switch(val.intValue())
				{
					case(1)://Begin
					{
						str=s.nextToken();

						val=(Integer)h.hs.get(str);
						if(val==null)
						continue;
						switch(val.intValue())
						{
							case(3)://"VB.Form"
								frm=new form(srcDir,destDir);
								frm.setProperties(s);
								report="\n* Form name = "+frm.getName();
								report+=frm.getReport();
						}
					}
				}
			}
		
		}
		
	
	//function to remove comments from the file
		private String removeComments(String fileText)
		{
			StringTokenizer s;
			String target="";
			String str;
			int flag1,flag2;
			System.out.println("Removing comments from form file and creating tokens for parsing...");
			s=new StringTokenizer(fileText,"\n");
			while(s.hasMoreTokens())
			{
				str=s.nextToken();
				flag1=str.indexOf("'");
				flag2=str.indexOf("\"");
				while(flag1!=-1)
				{
					if((flag2==-1)||(flag1<flag2))
					{
						if(flag1!=0)
						target+="\n"+str.substring(0,flag1);
						break;
					}
					else
					{
						if(flag1>flag2)
						{
							int count=1;
							int temp=str.indexOf("\"",flag2+1);
							while(temp!=-1&&temp<flag1)
							{
								count++;
								temp=str.indexOf("\"",temp+1);
							}

							if((count/2)*2-count==0)
							{
								target+="\n"+str.substring(0,flag1);
								break;
							}
							else
							flag1=str.indexOf("'",flag1+1);
						}
						else
						{
							flag1=str.indexOf("'",flag1+1);
						}
					}
				}
				if(flag1==-1)
				target+="\n"+str;
			}

			return(target);
		}


	  //this function creates tokens from the file content
		public parser(String srcFiles, String destDirs) throws FileNotFoundException,IOException	
		{	
			
			FileInputStream fis;  
			StringTokenizer s;
			int numOfBytes;
			String frmTxt;
			int index = srcFiles.lastIndexOf('/');
			srcDir=srcFiles.substring(0,index);			
			destDir=destDirs; 
			try{
				System.out.println("Parsing started...");
				fis=new FileInputStream(srcFiles);
				numOfBytes=fis.available();
				byte b[];
				b=new byte[numOfBytes];
				fis.read(b);
				frmTxt=new String(b);
				fis.close();
				frmTxt=removeComments(frmTxt);
				
			/*	FileOutputStream fout;
				fout=new FileOutputStream("E:/Renu/remcomments.txt");
									try
									{
										fout.write(frmTxt.getBytes());
										fout.close();
										System.out.println("File created");
										
									}
									
									
									catch(FileNotFoundException e)
									{
									System.out.println("File not found\n");
									
									}
									
									catch(IOException e)
									{
										System.out.println("Cannot carry forward ioexception\n");
									}*/				
				
				s=new StringTokenizer(frmTxt,"= \n\t\r\f");
				switchcase(s);
				System.out.println("Parsing completed...");								
			}
			catch(FileNotFoundException fe)
			{
				System.out.println(srcFiles+" not found.");								
			}
			catch(IOException ie)
			{
				System.out.println(srcFiles+" couldnot be opened for reading.");									
			}
		}
	 
	 
	 
	 //returns the report
		public String getReport()
		{
			return(report);			
		}		
}