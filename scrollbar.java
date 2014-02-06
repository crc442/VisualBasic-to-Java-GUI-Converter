import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;


//The scrollbar class comes into picture when there is VB.VScrollBar or VB.HScrollBar in source form file.
class scrollbar extends control
{
	boolean eflag;
  
  	//This String stores the report generated during parsing the object VB.HScrollBar or VB.VScrollBar
	String Report;
	
	private JScrollBar js;


	//constructor
	public scrollbar()
	{
		js=new JScrollBar();
		js.setMaximum(32767);
		js.setMinimum(0);
		js.setBlockIncrement(1);
		js.setUnitIncrement(1);
	}
	
	
     //return the name of the JScrollBar object 
	public String getName()
	{
		return(js.getName());
	}
	
	
	
	// This function is used during the code generation process  It returns 'Declaration' for the JScrillBar object.
    public String getDeclaration()
	{
		return("javax.swing.JScrollBar "+js.getName()+";\n\t");
	}
	

    // This method parses the input form file containing VB.VScrollBar or VB.HScrollBar
   //parameter ori this tells whether JScrollBar is  Vertical or Horizontal.

	public void setProperties(StringTokenizer s,int ori,int tabHeight)
	{
		Integer tid;
		int i,j,height=0,x=0,y,width,temp;
		String curToken;
		HashTable h;
		js.setOrientation(ori);
		curToken=s.nextToken();
		js.setName(curToken);
		curToken=s.nextToken();
		h=new HashTable();
		tid=(Integer)h.hs.get(curToken);
		if(tid==null)
		j=0;
		else
		j=tid.intValue();
		do
		{
				
			switch(j)
			{
						case 106://LargeChange 
								 curToken=s.nextToken();
								 js.setBlockIncrement(Integer.parseInt(curToken));
								 break;
								 
						case 107://SmallChange 
								 curToken=s.nextToken();
								 js.setUnitIncrement(Integer.parseInt(curToken));
								 break;
								 
						case 108: //Max
								 curToken=s.nextToken();
								 try
								 {
								 	js.setMaximum(Integer.parseInt(curToken));
								 }
								 catch(Exception e)
								 {
								 	eflag=true;
								 }
								 break;
								 
						case 109: //Min
								 curToken=s.nextToken();
								 try
								 {
								 	js.setMinimum(Integer.parseInt(curToken));
								 }
								 catch(Exception e)
								 {
								 	eflag=true;
								 }
								 break;
								 
						case 91://Value
								 curToken=s.nextToken();
								 try
								 {
								 	js.setValue(Integer.parseInt(curToken));
								 }
								 catch(Exception e)
								 {
								 	eflag=true;
								 }
								 break;
								 
						case 55://ToolTipText
								js.setToolTipText(getCaption(s));
								break;
						
						case 11: curToken=s.nextToken();
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
						
						case 12:curToken=s.nextToken(); 
								try
								{
									x=Integer.parseInt(curToken);
									// If Tabbed pane object with -ve coordinates
									if(x < 0)
									x = x + 75000;
									// else object with normal coordinates						
									x=x/15;	
								}
								catch(Exception e)
								{
								   eflag=true;
								}
								break;
								
						case 22://Index
								 curToken=s.nextToken();
	            				 String nm=js.getName();
	            				 nm+="_"+curToken;
	            				 js.setName(nm);
	            				 break;
						case 13:curToken=s.nextToken(); 
								try
								{
									y=Integer.parseInt(curToken);
									y=y/15;
									js.setLocation(x,y-tabHeight);
								}
								catch(Exception e)
								{
									eflag=true;
								}
								break;
						
						case 14: curToken=s.nextToken();
								//System.out.println(curToken);
								 width=Integer.parseInt(curToken);
								 width=width/15;
								 js.setSize(width,height);
								 break;	
						case 50://Enable
								curToken=s.nextToken();
								temp=curToken.compareTo("0");
								if(curToken.compareTo("0")==0);
								{
									js.setEnabled(false);
								}
								break;
						case 57://Visible
								curToken=s.nextToken();
								temp=curToken.compareTo("0");
								if(curToken.compareTo("0")==0);
								{
									js.setVisible(false);
								}
								break;
						case 52://MousePointer
	        					 curToken=s.nextToken();
	        					 mptype=Integer.parseInt(curToken);
	        					 curToken=s.nextToken();
	        					 break;
						case 37://BeginProperty
								while(curToken.compareTo("EndProperty")!=0)
								curToken=s.nextToken();
								break;
						default://Unidentified Property
								Report+="\nUnMapped Property:\t"+curToken;
								curToken=s.nextToken();
								if(curToken.startsWith("\""))
								removeString(s,curToken);
								break;
						
			}
			curToken=s.nextToken();
			tid=(Integer)h.hs.get(curToken);
			if(tid==null)
		    j=0;
			else
		    j=tid.intValue();
		}
		while(j!=2);
	System.out.println("Handled scrollbar");
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
   
     // This method generates the code for JScrollBar object.
	public String getCode()
	{
		String name,Code,tooltip;
		name=js.getName();
		Code="\t\t"+name+"=new javax.swing.JScrollBar();\n\t\t";
		Code+=name+".setOrientation("+js.getOrientation()+");\n\t\t";
		Code+=name+".setLocation("+js.getX()+","+js.getY()+");\n\t\t";
		Code+=name+".setSize("+js.getWidth()+","+js.getHeight()+");\n\t\t";
	    if(js.isEnabled()==false)
	    Code+=name+".setEnabled("+js.isEnabled()+");\n\t\t";
	   	tooltip=js.getToolTipText();
		if(tooltip!=null)
		Code+=name+".setToolTipText("+tooltip+");\n\t\t";
		if(mptype!=0)
		{
			String mp=new String();
			mp=getMousePointer(mptype);
			if(mp!=null)
			Code+=name+".setCursor(new Cursor("+mp+"));\n\t\t";
		}
		if(js.getValue()!=0)
		Code+=name+".setValue("+js.getValue()+");\n\t\t";		
	    if(js.isVisible()==false)
	    Code+=name+".setVisible("+js.isVisible()+");\n\t\t";
	    Code+=name+".setMaximum("+js.getMaximum()+");\n\t\t";
	    Code+=name+".setMinimum("+js.getMinimum()+");\n\t\t";
	    Code+=name+".setUnitIncrement("+js.getUnitIncrement()+");\n\t\t";
	    Code+=name+".setBlockIncrement("+js.getBlockIncrement()+");\n\t\t";
	    return(Code);
	}
}