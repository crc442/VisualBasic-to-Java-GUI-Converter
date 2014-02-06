import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
public class generator
{
	public generator(form frm,String srcFile,String destDir) throws FileNotFoundException,IOException
	{
		String str;
		FileOutputStream fos;
		str="import java.awt.*;\nimport javax.swing.*;\nimport javax.swing.border.*;\n";

		

		int index = srcFile.lastIndexOf('/');
		srcFile=srcFile.substring(index+1);
		srcFile=srcFile.substring(0,srcFile.length()-4);
		srcFile=srcFile.replace(' ','_');
		fos=new FileOutputStream(destDir+"/"+srcFile+".java");

		str+=frm.getCode(srcFile);		
		try
		{
				fos.write(str.getBytes());
				fos.close();
		}
		catch(FileNotFoundException e)
		{
			new msgBox(new JFrame(),"Error",destDir+"/"+srcFile+".java File can not be opened");
		}
		catch(IOException e)
		{
			System.out.println("Cannot carry forward ioexception\n");
		}
	}
}