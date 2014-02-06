
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import javax.swing.border.*;


//The label class comes into picture when there is VB.Label in source form file

class label extends control
{
	JLabel jl;
      
  
	private boolean appearance;		//appearance 3D or flat
	
	private boolean mnemonic;
	

	private boolean border;

	private boolean eflag;

	//to check if background color is set
	private boolean bflag;
 
 	//to check if foreground color is set
	private boolean fflag;
	
	
   
  
	public label()
	{
		jl = new JLabel();
		jl.setOpaque(true);
		jl.setBackground(java.awt.SystemColor.control);
		mnemonic = true; 
		appearance=true; // 3D		
		bflag=false;
		fflag=false;
		sysBkColor=15;
		sysFrColor=18;
	} 
	
	
  
	public void setProperties(StringTokenizer s, int tabHeight)
	{
		
		HashTable h= new HashTable();
		
		String curToken="";
		
		int height=0;
		
	    int width=0;
	 
	 	int left=0;
	
		int top=0;
	
		Integer val,val1;
		
		f=new font();
		
		jl.setName(s.nextToken());
	
		while(curToken.compareTo("End")!=0)
		{
			curToken=s.nextToken();
			val=(Integer)h.hs.get(curToken);
			if(val==null)
		     	continue;
		    switch(val.intValue())
			{				
					case(4):// Caption 
						String caption; 
						caption = getCaption(s);
						jl.setText(caption);						
						break;
					
				
					case(11):// Height
						curToken=s.nextToken();
						height=Integer.parseInt(curToken);
						height=height/15;
						break;
						
					case(14)://Width
						curToken=s.nextToken();
						width=Integer.parseInt(curToken);
						width=width/15;
						jl.setSize(width,height);
						break;
						
					case(12)://Left
						curToken=s.nextToken();
						Integer temp = new Integer(curToken);
						
						// If Tabbed pane object with -ve coordinates
						if(temp.longValue() < 0)
							left = (int)(temp.longValue() + 75000);
						// else object with normal coordinates
						else 
							left = temp.intValue();						
						left=left/15;				
						break;
						
					case(13)://Top
						curToken=s.nextToken();
						top=Integer.parseInt(curToken);
						top=top/15;
						jl.setLocation(left,top-tabHeight);
						break;
						
					case 22://Index
							curToken=s.nextToken();
				            String nm=jl.getName();
				            nm+="_"+curToken;
	        			    jl.setName(nm); 		
			   			 break;
			   			 
			   			 
					case(48)://BackColor
							bflag=true;
							curToken=s.nextToken();
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
									Bb=0;
									eflag=true;				
								}
								try{
									Bg=Integer.parseInt(curToken.substring(4,6),16);
								}
								catch(NumberFormatException Ne){
									Bg=0;
									eflag=true;				
								}
								try{
									Br=Integer.parseInt(curToken.substring(6,8),16);
								}
								catch(NumberFormatException Ne){
									Br=0;
									eflag=true;				
									}	
								jl.setBackground(new java.awt.Color(Br,Bg,Bb));
								}
							}
						catch(NumberFormatException Ne){//Catch For outer IF 
								setForecolor(jl,15,false);//Setting default to Scrollbar color
								sysBkColor=15;
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
										Bb=0;
										eflag=true;				
									}	
									try{
										Bg=Integer.parseInt(curToken.substring(4,6),16);
									}
									catch(NumberFormatException Ne){
										Bg=0;
										eflag=true;				
									}	
									try{						
										Br=Integer.parseInt(curToken.substring(6,8),16);
									}
									catch(NumberFormatException Ne){
										Br=0;
										eflag=true;				
									}	
									jl.setForeground(new java.awt.Color(Br,Bg,Bb));
								}
							}
							catch(NumberFormatException Ne){//Catch For Outer IF
								setForecolor(jl,18,true);
								sysFrColor=18;
								eflag=true;				
							}						
							break;				
					
					
					case 52:// Mouse Pointer
							curToken=s.nextToken();
							try{
								mptype=Integer.parseInt(curToken);
							}
							catch(NumberFormatException Ne){
								mptype=1;//Value to be used in case of Exception
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
						
					case(47):// Appearance
						curToken=s.nextToken(); 						
						appearance=false;						
						break;
						
					case(16):// TabIndex
						curToken=s.nextToken(); 
						break;
						
					case(50):// Enabled
						curToken=s.nextToken();
						jl.setEnabled(false);
						break;
						
					case(55):// ToolTipText
						curToken=getCaption(s);
						jl.setToolTipText(curToken);
						break;
						
					case(57):// Visible
						jl.setVisible(false);
						break;	
						
					case(65):// BorderStyle
						jl.setBorder(new LineBorder(new java.awt.Color(0,0,0)));
		   				break;	
		   									
					case(90):// Alignment
						curToken=s.nextToken();
						if(curToken.compareTo("1")==0)
							jl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
						else if(curToken.compareTo("2")==0)
							jl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
						break;	
						
					case(92):// BackStyle
						curToken=s.nextToken();
						if(curToken.compareTo("0")==0)
							jl.setOpaque(false); // Transparent
						break;	
							
					case(97):// UseMnemonic
						curToken=s.nextToken();
						if(curToken.compareTo("0")==0)
							mnemonic=false; // UseMnemonic = False
						
						break;		
			}		
		}	
		System.out.println("Handled label");
	}
	
 
 	public String getDeclaration()
	{
		return("javax.swing.JLabel "+jl.getName()+";\n\t");
	}
	

//generate code
	public String getCode()
	{
		
		// Temporary String to store name  
		String name = new String();
		name=jl.getName();
		
		// String to store code
		String str=new String();
				
		// Initialisation code
		str+="\t"+name+" = new javax.swing.JLabel();\n\t\t";
		
		// Caption
		if(!jl.getText().equals(""))
		{
			
			// If caption can contain '&' and is not meant for mnemonic
			if(!mnemonic)
				str+=name+".setText("+jl.getText()+");\n\t\t";
			
			// Else separate it from caption
			else
			{
				String caption = jl.getText();
				// Extract Mnemonic from caption
				int index=caption.indexOf("&");
				
				if(index!=-1)
				{
					// Remove mnemonic indicator '&' from actual caption
					String str1=new String();;
					str1=caption.substring(0,index);
					str1+=caption.substring(index+1,caption.length());
					caption=str1;
				}
				str+=name+".setText("+caption+");\n\t\t";
				if(index!=-1)
					str+=name+".setDisplayedMnemonicIndex("+(index-1)+");\n\t\t";					
			}
		}
		
		// Set Cursor
		if(mptype!=0)
		{
			String mp=new String();
			mp=getMousePointer(mptype);
			if(mp!=null)
				str+=name+".setCursor(new Cursor("+mp+"));\n\t\t";
		}
		// Set Border 
		if(jl.getBorder()!=null)
		{
			// If appearance is 3D then set bevel border.
			if(appearance)
				str+=name+".setBorder( new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));\n\t\t";		
			
			// Else appearance is 1D hence set line border.
			else
				str+=name+".setBorder(new LineBorder(new java.awt.Color(0,0,0)));\n\t\t";		
		}
		
		// Set position & size
		str+=name+".setBounds("+jl.getX()+","+jl.getY()+","+jl.getWidth()+","+jl.getHeight()+");\n\t\t";
		
		if(jl.isOpaque())
			str+=name+".setOpaque(true);\n\t\t";			
				

		if(jl.isOpaque())
			if(bflag)
				str+=name+".setBackground("+getRGB(jl.getBackground().toString(),false)+");\n\t\t";
			
		if(fflag)
			str+=name+".setForeground("+getRGB(jl.getForeground().toString(),true)+");\n\t\t";
		
		str+=name+".setFont("+f.getFontCode()+");\n\t\t";
		
		// If not enabled
		if(!jl.isEnabled())
			str+=name+".setEnabled(false);\n\t\t";
			
		// Set the alignment i.e. Right or Center. By default it is left
		if(jl.getHorizontalAlignment()!=javax.swing.SwingConstants.LEFT)
		{
			if(jl.getHorizontalAlignment()==javax.swing.SwingConstants.RIGHT)
				str+=name+".setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);\n\t\t";
			else if(jl.getHorizontalAlignment()==javax.swing.SwingConstants.CENTER)
				str+=name+".setHorizontalAlignment(javax.swing.SwingConstants.CENTER);\n\t\t";
		}
		

		str+=name+".setVerticalAlignment(javax.swing.SwingConstants.TOP);\n\t\t";
		
		// Set the tooltip text if any
		if(!(jl.getToolTipText()==null))
			str+=name+".setToolTipText("+jl.getToolTipText()+");\n\t\t";
		
		// If not visible
		if(jl.isVisible()==false)
			str+=name+".setVisible(false);\n\t\t";			
		
		return(str);
	}
	

	public String getName()
	{
		return(jl.getName());
		
	} 
}