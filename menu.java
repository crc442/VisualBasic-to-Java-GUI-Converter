
import java.awt.*;
import javax.swing.*;
import java.util.*;

//The menu class comes into picture when there is VB.Menu in source form file.

public class menu extends control
{

	public menu()
	{
	
	}
	

	public void setProperties(StringTokenizer tokenSet,JMenuBar mBar)
	{
		JMenu mn,stackTop;	
		JMenuItem childMn,topItem;
		
		int count = 1;
		
		// To store parent menus
		Stack stk = new Stack();;

		mn = new JMenu();
		curToken = tokenSet.nextToken();
		
		// Set menu in the Top menubar and store in stack.
		mn.setName(curToken);
		mBar.add(mn);
		stk.push(mn);
		
		// While not end of menu in the Top menubar
		while(count > 0 )
		{
			curToken = tokenSet.nextToken();	

			
			// If submenu of current main menu
			if(curToken.compareTo("Begin")==0)
			{

				
				// If top menu in the stack is JMenuItem then it is changed to JMenu to add submenus
				if(stk.peek().getClass().toString().compareTo("class javax.swing.JMenuItem")==0)
				{
					topItem = (JMenuItem)stk.pop();
					stackTop= new JMenu(topItem.getText());
					stackTop.setName(topItem.getName());
					stackTop.setMnemonic(topItem.getMnemonic());
					((JMenu)stk.peek()).remove(topItem);
					((JMenu)stk.peek()).add(stackTop);
					stk.push(stackTop);		
				}
				
				// Create new JMenuItem and add it to current top parent menu which is in stack				
				curToken=tokenSet.nextToken();
				childMn = new JMenuItem();
				curToken=tokenSet.nextToken();
				childMn.setName(curToken);
				((JMenu)stk.peek()).add(childMn);
				
				// Add new menu in stack an increment count
				stk.push(childMn);
				count++;
			}
			
			
			// If current menu ends remove it from stack top and decrement count
			else if(curToken.compareTo("End")==0)
			{
				stk.pop();
				count--;
			}
			
			else if(curToken.compareTo("Caption")==0)
			{
				curToken=getCaption(tokenSet);

				// Mnemonic handling Start
				int index;
				index = curToken.indexOf('&');
				if(index!=-1)
				{
					char m;
					String name="";
					m=curToken.charAt(index+1);
					((AbstractButton)stk.peek()).setMnemonic(m);
					if(index==0)
						name = curToken.substring(1);
					else if(index>0)
					{
						name = curToken.substring(0,index);
						name = name.concat(curToken.substring(index+1));
					}
					curToken=name;
				}
				// Mnemonic handling end
				((AbstractButton)stk.peek()).setText(curToken);
			} // END : Caption handling
			
			
			else if(curToken.compareTo("Enabled")==0)
			{
				curToken=tokenSet.nextToken();
				if(Integer.parseInt(curToken)==0)
				((AbstractButton)stk.peek()).setEnabled(false);

			} // END : Enable property handling
			
			// START : Visible property handling 
			else if(curToken.compareTo("Visible")==0)
			{
				curToken=tokenSet.nextToken();
				if(Integer.parseInt(curToken)==0)
					((AbstractButton)stk.peek()).setVisible(false);
								
			}// END : Visible property handling 
			
			
			// START : Checked property handling 
			else if(curToken.compareTo("Checked")==0)
			{
				JCheckBoxMenuItem cmn;
				curToken=tokenSet.nextToken();
				if(Integer.parseInt(curToken)==-1)
				{
					topItem = (JMenuItem)stk.pop();
					cmn = new JCheckBoxMenuItem(topItem.getText(),true);
					cmn.setName(topItem.getName());
					cmn.setMnemonic(topItem.getMnemonic());
					((JMenu)stk.peek()).remove(topItem);
					((JMenu)stk.peek()).add(cmn);
					stk.push(cmn);
					
				}
				
			}// END : Checked property handling 
			
			
			else if(curToken.compareTo("Shortcut")==0)
			{
				int mod = 0;
				int key;
				String caption;
				curToken = tokenSet.nextToken();

				mod = getModifier(curToken);
				key = getKey(curToken);
				caption = ((JMenuItem)stk.peek()).getText();
				caption+=" KeyCode{"+key+","+mod;
				((JMenuItem)stk.peek()).setText(caption);

				((JMenuItem)stk.peek()).setAccelerator(KeyStroke.getKeyStroke(key,mod));
			}// END : Shortcut key handling
			
			
		}
		System.out.println("Handled menubar");
	}
	


//generating the code
	public String getCode(JMenuBar mBar)
	{

		String code = "", name;

		code+="\tmBar=new javax.swing.JMenuBar();\n\t\t";
		code+="setJMenuBar(mBar);\n\n\t\t";
		int n;
		n=mBar.getMenuCount();
		for(int i=0;i<n;i++)
			{
				
				JMenu mn = mBar.getMenu(i);
				name = mn.getName();

				code+="javax.swing.JMenu "+name+" = new javax.swing.JMenu("+mn.getText()+");\n\t\t";
				if(mn.getMnemonic() != 0)
					code+=name+".setMnemonic('"+(char)mn.getMnemonic()+"');\n\t\t";
				if(mn.isVisible()==false)
					code+=name+".setVisible(false);\n\t\t";
				if(mn.isEnabled()==false)
					code+=name+".setEnabled(false);\n\t\t";
				code+="mBar.add("+name+");\n\n\t\t";
				code+=submenu(mn,name);
			}
		return(code);
	}

  // This method is used by the getCode() method to find submenus.

	public String submenu(JMenu mn,String current)
	{
		int i=0,n;
		String name;
		String temp = "",code="";
		Object o;
		
		n = mn.getItemCount();
		for(i=0;i<n;i++)
		{
			o = mn.getItem(i);

			if((o.getClass().toString()).compareTo("class javax.swing.JMenu")==0)
			{
				name =  ((JMenu)o).getName();
				code+="javax.swing.JMenu "+name+" = new javax.swing.JMenu("+((JMenu)o).getText()+");\n\t\t";
				if(((JMenu)o).getMnemonic() != 0)
					code+=name+".setMnemonic('"+(char)((JMenu)o).getMnemonic()+"');\n\t\t";
				if(((JMenu)o).isVisible()==false)
					code+=name+".setVisible(false);\n\t\t";
				if(((JMenu)o).isEnabled()==false)
					code+=name+".setEnabled(false);\n\t\t";
				code+=current+".add("+name+");\n\n\t\t";
				code+=submenu((JMenu)o,name);
			}
			else if((o.getClass().toString()).compareTo("class javax.swing.JMenuItem")==0)			
			{
				if((((JMenuItem)o).getText()).compareTo("\"-\"")!=0)
				{
					name =  ((JMenuItem)o).getName();
					
					if((((JMenuItem)o).getText()).lastIndexOf("KeyCode{")==-1)  
						code+="javax.swing.JMenuItem "+name+"=new javax.swing.JMenuItem("+((JMenuItem)o).getText()+");\n\t\t";
					else
					{
						int ind;
						String caption;
						caption = ((JMenuItem)o).getText();
						ind = caption.lastIndexOf("KeyCode{");
						code+="javax.swing.JMenuItem "+name+"=new javax.swing.JMenuItem("+caption.substring(0,ind-1)+");\n\t\t";
						ind = caption.indexOf('{',ind);

						code+=name+".setAccelerator(KeyStroke.getKeyStroke("+caption.substring(ind+1)+")); // "+((JMenuItem)o).getAccelerator()+"\n\t\t";
					}
					if(((JMenuItem)o).getMnemonic() != 0)
						code+=name+".setMnemonic('"+(char)((JMenuItem)o).getMnemonic()+"');\n\t\t";
					if(((JMenuItem)o).isVisible()==false)
						code+=name+".setVisible(false);\n\t\t";
					if(((JMenuItem)o).isEnabled()==false)
						code+=name+".setEnabled(false);\n\t\t";
					
					code+=current+".add("+name+");\n\n\t\t";
				}	
				else
				{
					name =  ((JMenuItem)o).getName();
					code+="javax.swing.JSeparator "+name+" = new javax.swing.JSeparator();\n\t\t";
					code+=current+".add("+name+");\n\n\t\t";
					if(((JMenuItem)o).isVisible()==false)
						code+=name+".setVisible(false);\n\t\t";
				}
			}
			else if((o.getClass().toString()).compareTo("class javax.swing.JCheckBoxMenuItem")==0)
			{
				name =  ((JCheckBoxMenuItem)o).getName();
				if((((JCheckBoxMenuItem)o).getText()).lastIndexOf("KeyCode{")==-1)  
					code+="javax.swing.JCheckBoxMenuItem "+name+" = new javax.swing.JCheckBoxMenuItem("+((JCheckBoxMenuItem)o).getText()+",true);\n\t\t";
				else
				{
					int ind;
					String caption;
					caption = ((JCheckBoxMenuItem)o).getText();
					ind = caption.lastIndexOf("KeyCode{");
					code+="javax.swing.JCheckBoxMenuItem "+name+"=new javax.swing.JCheckBoxMenuItem("+caption.substring(0,ind-1)+",true);\n\t\t";
					ind = caption.indexOf('{',ind);

					code+=name+".setAccelerator(KeyStroke.getKeyStroke("+caption.substring(ind+1)+")); // "+((JCheckBoxMenuItem)o).getAccelerator()+"\n\t\t";
					
				}

				if(((JCheckBoxMenuItem)o).getMnemonic() != 0)
					code+=name+".setMnemonic('"+(char)((JCheckBoxMenuItem)o).getMnemonic()+"');\n\t\t";
				if(((JCheckBoxMenuItem)o).isVisible()==false)
					code+=name+".setVisible(false);\n\t\t";
				if(((JCheckBoxMenuItem)o).isEnabled()==false)
					code+=name+".setEnabled(false);\n\t\t";
				
				code+=current+".add("+name+");\n\n\t\t";
			}
		}
		return(code);
	}
	
	
  // This method is used to get modifier for the  shortcut key(like ctrl, shift, alt).
	public int  getModifier(String str)
	{
		int i=0;
		if(str.indexOf('^')!=-1)
			i= i|2;
		if(str.indexOf('+')!=-1)
			i= i|1;
		if(str.indexOf('%')!=-1)
			i= i|8;
		return(i);
		
	}
	
  // This method is used to get key for the shortcut key(like A-Z, F1-F12, INSERT, DEL, BKSP).
	public int getKey(String str)
	{
		int start,end;
		String key;
		keyTable Table = new keyTable() ;
		
		start  = str.indexOf('{');
		end = str.indexOf('}');
		if(start!=-1 | end !=-1) 
			key = str.substring(start+1,end);
		else
		{	
			start = str.indexOf('^');
			key = str.substring(start+1);
		}
		
		return(((Integer)Table.key.get(key)).intValue());
	}	
}