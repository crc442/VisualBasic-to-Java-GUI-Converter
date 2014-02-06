
import javax.swing.*;
import java.awt.*;
import java.util.*;

// The control class is base class for all control classes
 
public class control
{
	// It is used to store the font name, size and style for each object.
	protected font f;
	
	// It is used to store current token to be processed. 
	protected String curToken;
	
	// It is used to store the mouse pointer type
	protected int mptype;
	
	// It is used to store system color code of background.
	protected int sysBkColor;
	
	// It is used to store system color code of foreground.
	protected int sysFrColor;
	
	// Initiates font to default font.
	public control()
	{
		f=new font();
		curToken="";
	}
	
	// This method returns the mouse pointer type for each object derived from control
	protected String getMousePointer(int type)
	{
	//Inside getMousePointer");
		String mp=new String();
		switch(type)
		{
			case 1:
				System.out.println("Cursor Not Supported.");
				mp = null;
			break;
			case 2:
				mp="Cursor.CROSSHAIR_CURSOR";
			break;
			case 3:
				mp="Cursor.TEXT_CURSOR";
			break;
			case 4:
				System.out.println("Cursor Not Supported.");
				mp = null;
			break;
			case 5:
				mp="Cursor.MOVE_CURSOR";
			break;
			case 6:
				mp="Cursor.NE_RESIZE_CURSOR";
			break;
			case 7:
				mp="Cursor.N_RESIZE_CURSOR";
			break;
			case 8:
				mp="Cursor.NW_RESIZE_CURSOR";
			break;
			case 9:
				mp="Cursor.E_RESIZE_CURSOR";
			break;
			case 10:
				System.out.println("Cursor Not Supported.");
				mp=null;
			break;
			case 11:
				mp="Cursor.WAIT_CURSOR";
			break;
			case 12:
				System.out.println("Cursor Not Supported.");
				mp=null;
			break;
			case 13:
				System.out.println("Cursor Not Supported.");
				mp=null;
			break;
			case 14:
				System.out.println("Cursor Not Supported.");
				mp=null;
			break;
			case 15:
				mp="Cursor.MOVE_CURSOR";
			break;
			case 99:
				System.out.println("Cursor Not Supported.");
				mp=null;
			break;
		}
		return(mp);
	}
	
	// This method sets the color of the object. This is used only for system colors.
	
	protected void setForecolor(Component jl, int index, boolean foreground)
	{
		if(foreground)
		{
			switch(index)
			{
				case(0):jl.setForeground(java.awt.SystemColor.scrollbar);
					   break;
				case(1):jl.setForeground(java.awt.SystemColor.desktop);
					   break;
				case(2):jl.setForeground(java.awt.SystemColor.activeCaption);
					   break;	
				case(3):jl.setForeground(java.awt.SystemColor.inactiveCaption);
					   break;
				case(4):jl.setForeground(java.awt.SystemColor.menu);
					   break;
				case(5):jl.setForeground(java.awt.SystemColor.window);
					   break;
				case(6):jl.setForeground(java.awt.SystemColor.windowBorder);
					   break;
				case(7):jl.setForeground(java.awt.SystemColor.menuText);
					   break;
				case(8):jl.setForeground(java.awt.SystemColor.windowText);
					   break;
				case(9):jl.setForeground(java.awt.SystemColor.activeCaptionText);
					   break;
				case(10):jl.setForeground(java.awt.SystemColor.activeCaptionBorder);
					   break;
				case(11):jl.setForeground(java.awt.SystemColor.inactiveCaptionBorder);
					   break;
				case(12):jl.setForeground(java.awt.SystemColor.text);
					   break;
				case(13):jl.setForeground(java.awt.SystemColor.textHighlight);
					   break;
				case(14):jl.setForeground(java.awt.SystemColor.textHighlightText);
					   break;
				case(15):jl.setForeground(java.awt.SystemColor.control);
					   break;				   				   				   				   				   				   				   				   				   				   				   				   
				case(16):jl.setForeground(java.awt.SystemColor.controlShadow);
					   break;
				case(17):jl.setForeground(java.awt.SystemColor.textInactiveText);
					   break;
				case(18):jl.setForeground(java.awt.SystemColor.controlText);
					   break;
				case(19):jl.setForeground(java.awt.SystemColor.inactiveCaptionText);
					   break;				   				   				   							   
				case(20):jl.setForeground(java.awt.SystemColor.controlHighlight);
					   break;		
				case(21):jl.setForeground(java.awt.SystemColor.controlDkShadow);
					   break;		
				case(22):jl.setForeground(java.awt.SystemColor.controlLtHighlight);
					   break;
				case(23):jl.setForeground(java.awt.SystemColor.infoText);
					   break;
				case(24):jl.setForeground(java.awt.SystemColor.info);
					   break;				   							
			}		
		}
		
		else // background
		{
			switch(index)
			{
				case(0):jl.setBackground(java.awt.SystemColor.scrollbar);
					   break;
				case(1):jl.setBackground(java.awt.SystemColor.desktop);
					   break;
				case(2):jl.setBackground(java.awt.SystemColor.activeCaption);
					   break;	
				case(3):jl.setBackground(java.awt.SystemColor.inactiveCaption);
					   break;
				case(4):jl.setBackground(java.awt.SystemColor.menu);
					   break;
				case(5):jl.setBackground(java.awt.SystemColor.window);
					   break;
				case(6):jl.setBackground(java.awt.SystemColor.windowBorder);
					   break;
				case(7):jl.setBackground(java.awt.SystemColor.menuText);
					   break;
				case(8):jl.setBackground(java.awt.SystemColor.windowText);
					   break;
				case(9):jl.setBackground(java.awt.SystemColor.activeCaptionText);
					   break;
				case(10):jl.setBackground(java.awt.SystemColor.activeCaptionBorder);
					   break;
				case(11):jl.setBackground(java.awt.SystemColor.inactiveCaptionBorder);
					   break;
				case(12):jl.setBackground(java.awt.SystemColor.text);
					   break;
				case(13):jl.setBackground(java.awt.SystemColor.textHighlight);
					   break;
				case(14):jl.setBackground(java.awt.SystemColor.textHighlightText);
					   break;
				case(15):jl.setBackground(java.awt.SystemColor.control);
					   break;				   				   				   				   				   				   				   				   				   				   				   				   
				case(16):jl.setBackground(java.awt.SystemColor.controlShadow);
					   break;
				case(17):jl.setBackground(java.awt.SystemColor.textInactiveText);
					   break;
				case(18):jl.setBackground(java.awt.SystemColor.controlText);
					   break;
				case(19):jl.setBackground(java.awt.SystemColor.inactiveCaptionText);
					   break;				   				   				   							   
				case(20):jl.setBackground(java.awt.SystemColor.controlHighlight);
					   break;		
				case(21):jl.setBackground(java.awt.SystemColor.controlDkShadow);
					   break;		
				case(22):jl.setBackground(java.awt.SystemColor.controlLtHighlight);
					   break;
				case(23):jl.setBackground(java.awt.SystemColor.infoText);
					   break;
				case(24):jl.setBackground(java.awt.SystemColor.info);
					   break;				   							
			}		
			
		}
	}
	
	// This method returns foreground or background color for each object derived from control.

	protected String getRGB(String temp,boolean foreground)
	{
		int index1, index2;
		String result = new String("");
		if(temp.indexOf("SystemColor")!=-1 || temp.indexOf("ColorUIResource")!=-1)
		{
			int colorNumber;
			if(foreground)
				colorNumber=sysFrColor;
			else
				colorNumber=sysBkColor;
			switch(colorNumber)
			{
				case(0):result = "java.awt.SystemColor.scrollbar";
					   break;
				case(1):result = "java.awt.SystemColor.desktop";
					   break;
				case(2):result = "java.awt.SystemColor.activeCaption";
					   break;	
				case(3):result = "java.awt.SystemColor.inactiveCaption";
					   break;
				case(4):result = "java.awt.SystemColor.menu";
					   break;
				case(5):result = "java.awt.SystemColor.window";
					   break;
				case(6):result = "java.awt.SystemColor.windowBorder";
					   break;
				case(7):result = "java.awt.SystemColor.menuText";
					   break;
				case(8):result = "java.awt.SystemColor.windowText";
					   break;
				case(9):result = "java.awt.SystemColor.activeCaptionText";
					   break;
				case(10):result = "java.awt.SystemColor.activeCaptionBorder";
					   break;
				case(11):result = "java.awt.SystemColor.inactiveCaptionBorder";
					   break;
				case(12):result = "java.awt.SystemColor.control";
					   break;
				case(13):result = "java.awt.SystemColor.textHighlight";
					   break;
				case(14):result = "java.awt.SystemColor.textHighlightText";
					   break;
				case(15):result = "java.awt.SystemColor.control";
					   break;				   				   				   				   				   				   				   				   				   				   				   				   
				case(16):result = "java.awt.SystemColor.controlShadow";
					   break;
				case(17):result = "java.awt.SystemColor.textInactiveText";
					   break;
				case(18):result = "java.awt.SystemColor.controlText";
					   break;
				case(19):result = "java.awt.SystemColor.inactiveCaptionText";
					   break;				   				   				   							   
				case(20):result = "java.awt.SystemColor.controlLtHighlight";
					   break;		
				case(21):result = "java.awt.SystemColor.controlDkShadow";
					   break;		
				case(22):result = "java.awt.SystemColor.controlHighlight";
					   break;
				case(23):result = "java.awt.SystemColor.infoText";
					   break;
				case(24):result = "java.awt.SystemColor.info";
					   break;				   							
			}
			
				//System.out.println("The background color is in if:" +result);		
						
			
		}
		else if(temp.indexOf("Color")!=-1)
		{
			temp = temp.replace('[','(');
			temp = temp.replace(']',')');
			index1 = temp.indexOf('(');
			index2 = temp.indexOf('g');
			result = "new "+temp.substring(0,index1+1);
			result+= temp.substring(index1+3,index2);
			index1 = temp.indexOf('b');
			result+=temp.substring(index2+2,index1);
			result+=temp.substring(index1+2);
					
		}
	//	System.out.println("The background color is" +result);
		return(result);
	}	
	
 // This retireves the complete caption of the Control Object. 
  
	protected String getCaption(StringTokenizer tokens)
	{
		int ind;
		String caption, str;
		str=tokens.nextToken();
		str= str.substring(1);	
		ind = noOfEndQuotes(str);
		while(even(ind))	
		{
			str+=" "+tokens.nextToken();
			ind = noOfEndQuotes(str);
		}
		str="\""+str;
		
		ind= str.indexOf("\\",1);		
		while(ind > -1)
		{
			str = str.substring(0,ind)+"\\\\"+str.substring(ind+1);
			ind = str.indexOf("\\",ind+2);
			
		}
		
		ind = str.indexOf("\"\"",1);
		while(ind > -1)
		{
			str = str.substring(0,ind)+"\\\""+str.substring(ind+2);
			ind = str.indexOf("\"\"",ind+2);
			
		}
		caption=str;
		
		if(caption.compareTo("\"")==0)
			caption="\" \"";
						
		return(caption);
	}
	
	
	private int noOfEndQuotes(String str)
	{
		int n=0;
		String text=str;
		int endPos = text.length()-1;
		while(endPos>=0 && (text.charAt(endPos)=='\"')) 
		{
			n++;
			endPos--;
		}
		return(n);
		
	}
	
	// This function returns if the number is even or odd.

	private boolean even(int n)
	{
		if(n%2==0)
			return(true);
		return(false);
		
	}
}