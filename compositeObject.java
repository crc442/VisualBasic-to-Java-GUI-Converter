import java.util.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;

  // This class act as parent class for form,picturebox,frame,and tabbedpane
  // All above mentioned classes are composite.i.e;Objects of this class can contain other objects in them.

class compositeObject extends control
{
  // This variable stores the number of radio butttons embedded in the composite object
	int rbcnt;
	
  // This vector stores all those objects that are embedded in the composite object
    Vector v;
    
  // This string contains the path of the directory where source file is present
	protected String srcDir;
	
  // This string contains the path of directory where reulting java file is to be created
	protected String destDir;
	
  // This string contains the name of object
	protected String objName;
	private String report="";
	JMenuBar mBar;
	boolean menuFlag=true;
	static String shapeCode;//required for storing all the shape codes embedded in composite objects together
	static boolean shFlag;//For taking decision about adding or removing the extra shape code required
	int tabIndex;//For storing the tabIndex of container objects contained in a Tabbed Pane
				 //required in case a shape in contained inside a container embedded in a Tabbed Pane
	compositeObject()
	{
		rbcnt=0;
		v=new Vector(10,5);
		objName="";
		shapeCode=new String();
		tabIndex=-1;
	}
  // This function returns the Declaration of all the objects embedded in the composite object  

	public String getDeclnOfEmbeddedObj()
	{
		String str="",name;
		Integer val;
		HashTable h=new HashTable();
		int counter=0;
		Object o;
		while(counter<v.size())
		{
			o=v.get(counter);		// (return the string in the vector at position counter)
			counter++;				
			val=(Integer)h.hs.get(o.getClass().toString());	//(getclass fetches the class name and finds its value from the hash table)
			switch(val.intValue())
			{
			
				case 78://scrollbar
						scrollbar sc=new scrollbar();
						sc=(scrollbar)o;
						str+=sc.getDeclaration();
						break;
				case 80://picturebox
						picturebox pic;
						pic=(picturebox)o;
						str+=pic.getDeclaration();
						break;
						
				case 104://imagebox
						imagebox pict;
						pict=(imagebox)o;
						str+=pict.getDeclaration();
						break;
						
				case 77: //Jlist
						list Jl;
						Jl=(list)o;
						str+=Jl.getDeclaration();
						break;
				case 60://JLabel
						label lbl = new label();
						lbl = (label)o;
						str+= lbl.getDeclaration();
						break;
				case 61://JCheckBox
						checkbox ck;	
						ck=(checkbox)o;
						str+=ck.getDeclaration();
						break;
						
				case 105://radiobutton
						radiobutton jrb=new radiobutton();	
						jrb=(radiobutton)o;
						str+=jrb.getDeclaration();
						break;
			
				case 62://JButton
						cmdbutton button = new cmdbutton();
						button = (cmdbutton)o;
						str+=button.getDeclaration();
						break;
				case 65://JTextField
						JTextField jt=new JTextField();	
						jt=(JTextField)o;
						name=jt.getName();
						str+="JTextField "+name+";\n\t";
						break;
				
						
				case 63://JTextArea
					
						textarea ta=new textarea(srcDir);
						ta=(textarea)o;
						str+=ta.getDeclaration();
						break;
						
				case 64://JComboBox
						combobox co=new combobox();	
						co=(combobox)o;
						str+=co.getDeclaration();
						break;
				case 70://Frame
						frame fr;
						fr=(frame)o;
						str+=fr.getDecln();
						break;
				case 89://tabbedpane
						tabbedpane tb;
						tb=(tabbedpane)o;
						str+=tb.getDeclaration();
						break;
				
				case 75://Shape 
						shape sh;
						sh=(shape)o;
						str+=sh.getDeclaration();
						break;
				case 95://Line
						line l=(line)o;
						str+=l.getDeclaration();
						break;
				
				case 83://JMenuBar
						str+="javax.swing.JMenuBar mBar;\n\t";
						break;
			}
		  }
		  if(rbcnt>=1)
		  str+="ButtonGroup "+objName+"_rbg;\n\t";
		  return(str);
	}
	
  //It is required because we need to determine the Tab Index for a control embedded in a Tabbed Pane.
	public String getCodeOfEmbeddedObj(compositeObject co,int tindex,String tabName)
	{
		Object o;
		String str="";
		int counter=0;
		String name;
		HashTable h=new HashTable();
		Integer val;
		Font f;
		if(rbcnt>=1)
			str+=objName+"_rbg=new ButtonGroup();\n\t\t";
		while(counter<v.size())
		  {
			o=v.get(counter);
			counter++;
			val=(Integer)h.hs.get(o.getClass().toString());
			switch(val.intValue())
			{
				case 78://scrollbar
						scrollbar sc=new scrollbar();
						sc=(scrollbar)o;
					  	str=str+sc.getCode();
					  	if(this.getClass().toString().compareTo("class form")==0)
							str+="getContentPane().add("+sc.getName()+");\n\n\t";
						else if(this.getClass().toString().compareTo("class tabbedpane")!=0)
							str+=objName+".add("+sc.getName()+");\n\n\t";
						break;
				case 80://Picturebox
						picturebox pic=(picturebox)o;
						pic.tabIndex=tabIndex;
						name=new String(pic.getName());
						if(co!=null)
						{
							tabbedpane tp=(tabbedpane)co;
							tabName = new String(tp.getName());
							int i;
							for(i=0;i<tp.getCtrlIndex();i++)
							{
								if(((String)tp.getCtrls().get(i)).compareTo(name)==0)
								break;	
							}
							if(i<=tp.getCtrlIndex())
							{
								Integer tNum=(Integer)tp.getTabIndexOfCtrl().get(i);
								pic.tabIndex=tNum.intValue();
							}
						}
						str+=pic.getCode(tabName);
						if(this.getClass().toString().compareTo("class form")==0)
							str+="getContentPane().add("+pic.getName()+");\n\n\t";
						else if(this.getClass().toString().compareTo("class tabbedpane")!=0)
							str+=objName+".add("+pic.getName()+");\n\n\t";
						break;
						
				case 104://Imagebox
						imagebox pict=(imagebox)o;
						
						name=new String(pict.getName());
						str+=pict.getCode();//tabName);
						
						if(this.getClass().toString().compareTo("class form")==0)
							str+="getContentPane().add("+pict.getName()+");\n\n\t";
						else if(this.getClass().toString().compareTo("class tabbedpane")!=0)
							str+=objName+".add("+pict.getName()+");\n\n\t";
						break;
				case 77:// list
						String str1=new String();
						list Jl1=new list("");	
						Jl1=(list)o;
						str1=Jl1.getCode();
						str+=str1;
						if(this.getClass().toString().compareTo("class form")==0)
							str+="getContentPane().add("+Jl1.getName()+"ScrollPane);\n\n\t";
						else if(this.getClass().toString().compareTo("class tabbedpane")!=0)
							str+=objName+".add("+Jl1.getName()+"ScrollPane);\n\n\t";
						break;
					
					
				case 60: // class label
						label lbl = (label)o;
						str+= lbl.getCode();
						if(this.getClass().toString().compareTo("class form")==0)
						str+="getContentPane().add("+lbl.getName()+");\n\n\t";
						else if(this.getClass().toString().compareTo("class tabbedpane")!=0)
						str+=objName+".add("+lbl.getName()+");\n\n\t";
						break;				
				case 61://JCheckBox
						checkbox cb=(checkbox)o;
						str+=cb.getCode();
						if(this.getClass().toString().compareTo("class form")==0)
						str+="getContentPane().add("+cb.getName()+");\n\n\t\t";
						else
						str+=objName+".add("+cb.getName()+");\n\n\t\t";
						break;
				
		
				case 105://radiobutton
						radiobutton rb=new radiobutton();
						rb=(radiobutton)o;
						str+=rb.getCode();
						//System.out.println(this.getClass().toString());
						
						
						if(this.getClass().toString().compareTo("class form")==0)
							str+="getContentPane().add("+rb.getName()+");\n\t\t";
						else if(this.getClass().toString().compareTo("class tabbedpane")!=0)
							str+=objName+".add("+rb.getName()+");\n\t\t";
						
						str+=objName+"_rbg.add("+rb.getName()+");\n\n\t";
						break;
				
				
			
				case 62://JButton
						cmdbutton button = new cmdbutton();
						button = (cmdbutton)o;
						str+= button.getCode();
						if(this.getClass().toString().compareTo("class form")==0)
						str+="getContentPane().add("+button.getName()+");\n\n\t";
						else if(this.getClass().toString().compareTo("class tabbedpane")!=0)
						str+=objName+".add("+button.getName()+");\n\n\t";
						break;
				case 83://JMenuBar
						menu m = new menu();
						str+=m.getCode((JMenuBar)o);
						str+="\n\t";
						break;
				
						
				case 63://JTextArea
						
						textarea ta=new textarea(srcDir);
						ta=(textarea)o;
						str+=ta.getCode();
						name=ta.getName();
						
						if(this.getClass().toString().compareTo("class form")==0)
							str+="getContentPane().add("+name+"_Pane);\n\n\t";
						else if(this.getClass().toString().compareTo("class tabbedpane")!=0)
							str+=objName+".add("+name+"_Pane);\n\n\t";
						
						break;
			
				case 65://JTextField
						JTextField jt=new JTextField();	
						jt=(JTextField)o;
						name=jt.getName();
						str+="\t"+name+"=new JTextField();\n\t\t";
						if(!jt.getText().equals(""))
						str+=name+".setText("+jt.getText()+");\n\t\t";
						str+=name+".setLocation("+jt.getX()+","+jt.getY()+");\n\t\t";
						str+=name+".setSize("+(jt.getWidth()+3)+","+jt.getHeight()+");\n\t\t";
						f=jt.getFont();
						str+=name+".setFont(new Font("+f.getName()+","+f.getStyle()+","+f.getSize()+"));\n\t\t";
						break;
			
				case 64://JComboBox
						
						combobox jco=(combobox)o;
						name=jco.getName();
						str+=jco.getCode();	
						if(this.getClass().toString().compareTo("class form")==0)
							str+="getContentPane().add("+jco.getName()+");\n\n\t\t";
						else if(this.getClass().toString().compareTo("class tabbedpane")!=0)
							str+=objName+".add("+jco.getName()+");\n\n\t\t";				
						break;
				
				case 70://Frame
						frame fr;
						fr=(frame)o;
						name = new String(fr.getName());
						fr.tabIndex=tabIndex;
						if(co!=null)
						{
							tabbedpane tp=(tabbedpane)co;
							tabName = new String(tp.getName());
							int i;
							for(i=0;i<tp.getCtrlIndex();i++)
							{
								if(((String)tp.getCtrls().get(i)).compareTo(name)==0)
								break;	
							}
							if(i<=tp.getCtrlIndex())
							{
								Integer tNum=(Integer)tp.getTabIndexOfCtrl().get(i);
								fr.tabIndex=tNum.intValue();
							}
						}
						str+=fr.getCode(tabName);
						if(this.getClass().toString().compareTo("class form")==0)
							str+="getContentPane().add("+fr.getName()+");\n\n\t";
						else if(this.getClass().toString().compareTo("class tabbedpane")!=0)
							str+=objName+".add("+fr.getName()+");\n\n\t";				
						break;
				
				case 75://Shape
						shape sh=(shape)o;
						str+=sh.getCode();
						shapeCode+=sh.getShapeCode(this,tabIndex,tabName);
						if(this.getClass().toString().compareTo("class form")==0)
							str+="getContentPane().add("+sh.getName()+");\n\n\t";
						else if(this.getClass().toString().compareTo("class tabbedpane")!=0)
							str+=objName+".add("+sh.getName()+");\n\n\t";				
						break;
				
				case 95://line
						line l=(line)o;
						str+=l.getCode();
						shapeCode+=l.getLineCode(this,tabIndex,tabName);
						if(this.getClass().toString().compareTo("class form")==0)
							str+="getContentPane().add("+l.getName()+");\n\n\t";
						else if(this.getClass().toString().compareTo("class tabbedpane")!=0)
							str+=objName+".add("+l.getName()+");\n\n\t";				
						break;	
				
				case 89://TabbbedPane
						tabbedpane tb = (tabbedpane)o;
						tb.tabIndex=tabIndex;
						name=new String(tb.getName());
						if(co!=null)
						{
							tabbedpane tp=(tabbedpane)co;
							tabName = new String(tp.getName());
							int i;
							for(i=0;i<tp.getCtrlIndex();i++)
							{
								if(((String)tp.getCtrls().get(i)).compareTo(name)==0)
								break;	
							}
							if(i<=tp.getCtrlIndex())
							{
								Integer tNum=(Integer)tp.getTabIndexOfCtrl().get(i);
								tb.tabIndex=tNum.intValue();
							}
						}
						str+=tb.getCode(tabName);
						if(this.getClass().toString().compareTo("class form")==0)
							str+="getContentPane().add("+tb.getName()+");\n\n\t";
						else if(this.getClass().toString().compareTo("class tabbedpane")!=0)
							str+=objName+".add("+tb.getName()+");\n\n\t";				
						break;
				
				}
		  }
		  return(str);
	}
	
	// This function adds the detected object in to the composite object
	public void addEmbeddedObj(StringTokenizer s,int tabHeight)
	{
			
			String str;
			Integer temp;
			int j;
			HashTable h=new HashTable();
			str=s.nextToken();
			temp=(Integer)h.hs.get(str);
			if(temp==null)
			{
				int cnt=1;
				while(cnt!=0)
				{
					str=s.nextToken();
					if(str.compareTo("End")==0)
					cnt--;
					if(str.compareTo("Begin")==0)
					cnt++;
				}
			}
			else
			{
				j=temp.intValue();
			
				switch(j)					
				{				
				
							
								case(34)://"VB.VScrollBar"
												
										scrollbar sc=new scrollbar();
										sc.setProperties(s,1,tabHeight);
										report+="\n* Vertical Scrollbar name = "+sc.getName();
										v.addElement(sc);
										break;
								case(35)://"VB.HScrollBar"
										scrollbar sc1=new scrollbar();
										sc1.setProperties(s,0,tabHeight);
										report+="\n* Horizontal Scrollbar name = "+sc1.getName();
										v.addElement(sc1);
										break;
								case(24)://"VBCommandButton"
										cmdbutton btn=new cmdbutton(srcDir,destDir);
										btn.setProperties(s,tabHeight);
										report+="\n* Command Button name = "+btn.getName();
										v.addElement(btn);
										break;
						
								case(33)://"VB.Label
										label lbl = new label();
										lbl.setProperties(s,tabHeight);
										report+="\n* Label name = "+lbl.getName();
										v.addElement(lbl);
										break;								
								case(10)://"VB.CheckBox")==0
										checkbox ck=new checkbox(srcDir,destDir);
										ck.setProperties(s,tabHeight);
										report+="\n* CheckBox name = "+ck.getName();
										v.addElement(ck);
										break;
								
								case(21)://"VB.ComboBox")==0
										combobox cb=new combobox(srcDir);
										cb.setProperties(s,tabHeight);
										report+="\n* ComboBox name = "+cb.getName();
										v.addElement(cb);
										break;
									
															
								 case(23)://"VB.OptionButton")==0
										rbcnt++;
										radiobutton rb;
										rb=new radiobutton();
										rb.setProperties(s,tabHeight);
										report+="\n* RadioButton name = "+rb.getName();
										report+=rb.getReport();
										v.addElement(rb);
										break;	
										
								case(25)://"VB.TextBox")==0
								 		textarea t;
										t=new textarea(srcDir);
										t.setProperties(s,tabHeight);
										report+="\n* TextArea name = "+t.getName();
										report+=t.getReport();
										v.addElement(t);
										break;
										
								case (20)://VB.ListBox
										list Jl;
										Jl=new list(srcDir);
										Jl.setProperties(s,tabHeight);	
										report+="\n* ListBox name = "+Jl.getName();
										v.addElement(Jl);
										break;
								
								case (15)://VB.PictureBox
										picturebox pic=new picturebox(srcDir,destDir);
										pic.setProperties(s,tabHeight);
										report+="\n* PictureBox name = "+pic.getName();
										report+=pic.getReport();
										v.addElement(pic);
										break;
										
								case (26)://VB.Image
										imagebox pict=new imagebox(srcDir,destDir);
										pict.setProperties(s,tabHeight);
										report+="\n* ImageBox name = "+pict.getName();
										report+=pict.getReport();
										v.addElement(pict);
										break;
										
								case (32)://VB.Shape
										shFlag=true;
										shape sh=new shape();
										sh.setProperties(s,tabHeight);
										//report+=
										v.addElement(sh);
										break;
								case (69)://VB.Frame								
										frame fr=new frame(srcDir,destDir);
										fr.setProperties(s,tabHeight);
										report+="\n* Frame name = "+fr.getName();
										report+=fr.getReport();
										v.addElement(fr);
										break;
								case (84):// TabDlg.SSTab / Tabbedpane
										tabbedpane tb=new tabbedpane(srcDir,destDir);
										tb.setProperties(s,tabHeight);
										report+="\n* TabbedPane name = "+tb.getName();
										report+=tb.getReport();
										v.addElement(tb);
										break;
								
								case (27)://VB.Line
										shFlag=true;
										line l=new line();
										l.setProperties(s);
										//report+=
										v.addElement(l);
										break;
								
								case(85)://VB.Menu
										menu m;
										if(menuFlag)
										{
											mBar = new JMenuBar();
										  	v.addElement(mBar);
										  	menuFlag = false;
										  	// Extra to be added for each object
											report+="\n* MenuBar";
										}										  
										m=new menu();
										m.setProperties(s,mBar);
								 		break;
				}
			}				
	}
	
	public String getReport()
	{
		return(report);
	}
}