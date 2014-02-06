
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Main extends JFrame implements ActionListener
{
	

	//Used to display status after convertion.
	private JList statusList;
	
	//Button to start conversion. 	 
	private JButton btnok;
	
	//Button to add input to the input list.
	private JButton btnadd;
	
	//Button to remove any input from the input list.
	private JButton btnremove;
	
	//Button to reset the input.
	private JButton btnreset;
	
	//Button to display more details
	private JButton btnDetail;
	
	//List of destination paths.
	private JList destlist;
	
	// List of source paths.
	private JList srclist;
	
	// Browse button for destination
	private JButton btndest;
	
	//Textarea showing destination path
	private JTextArea txtdest;
	
	//Browse button for source
	private JButton btnsrc;
	
	//Textarea showing source path
	private JTextArea txtsrc;
	
	//Source and destination file chooser.
	private JFileChooser fc;	
	
	//String array to store source files.
	private String srcFiles[];
	
	//String array to store destination files.
	private String destDirs[];
	
	//String array to store status information.
	private String status[];
	
	//Integer indicating number of inputs.
	private int len;
	
	//indicates current directory
	private File curDir = null;
	
	//Vector to store details strings for each input
	private Vector v;



	public Main()
	{
		
	JPanel Frame1;
	JPanel Frame2;
	JPanel Frame3;
	JPanel Frame4;
	JLabel lblsrc;
	JLabel lbldest;	
	JLabel lblsrclist;
	JLabel lbldestlist;
	JScrollPane List1ScrollPane;
	JScrollPane srclistScrollPane;
	JScrollPane destlistScrollPane;
	

		
		getContentPane().setLayout(null);
		setExtendedState(Frame.MAXIMIZED_BOTH);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setTitle("VB to Java GUI Converter");
		Frame1=new JPanel();
		Frame1.setLayout(null);
		Frame1.setLocation(0,0);
		Frame1.setSize(1288,705);
		Frame1.setFont(new Font("MS Sans Serif",0,11));
		Frame1.setBorder(BorderFactory.createTitledBorder(""));
		Frame1.setBackground(new java.awt.Color(255,255,255));
			
		Frame2=new JPanel();
		Frame2.setLayout(null);
		Frame2.setLocation(168,48);
		Frame2.setSize(825,201);
		Frame2.setFont(new Font("Lucida Sans",1,13));
		Frame2.setBorder(BorderFactory.createTitledBorder("Select "));
		Frame2.setBackground(new java.awt.Color(255,255,255));
		
		btnok = new javax.swing.JButton();
		btnok.setText("Convert");
		btnok.setBounds(432,152,129,33);
		btnok.setBackground(new java.awt.Color(255,255,255));
		btnok.setFont(new Font("Tahoma",0,11));
		Frame2.add(btnok);
		btnok.addActionListener(this);

		btnreset = new javax.swing.JButton();
		btnreset.setText("Reset");
		btnreset.setBounds(600,152,129,33);
		btnreset.setBackground(new java.awt.Color(255,255,255));
		btnreset.setFont(new Font("Tahoma",0,11));
		Frame2.add(btnreset);
		btnreset.addActionListener(this);



		txtsrc=new JTextArea();
		txtsrc.setLocation(192,38);
		txtsrc.setSize(428,33);
		txtsrc.setFont(new Font("Arial",0,12));
		txtsrc.setEditable(false);
		txtsrc.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
		Frame2.add(txtsrc);
		
		
		txtdest=new JTextArea();
		txtdest.setLocation(192,94);
		txtdest.setSize(428,33);
		txtdest.setFont(new Font("Arial",0,12));
		txtdest.setEditable(false);
		txtdest.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
		Frame2.add(txtdest);

		btnadd = new javax.swing.JButton();
		btnadd.setText("Add");
		btnadd.setBounds(96,152,129,33);
		btnadd.setBackground(new java.awt.Color(255,255,255));
		btnadd.setFont(new Font("Tahoma",0,11));
		Frame2.add(btnadd);
		btnadd.addActionListener(this);

		btnremove = new javax.swing.JButton();
		btnremove.setText("Remove");
		btnremove.setBounds(264,152,129,33);
		btnremove.setBackground(new java.awt.Color(255,255,255));
		btnremove.setFont(new Font("Tahoma",0,11));
		btnremove.addActionListener(this);		
		Frame2.add(btnremove);

		btnsrc = new javax.swing.JButton();
		btnsrc.setText("Browse...");
		btnsrc.setBounds(632,35,105,33);
		btnsrc.setBackground(new java.awt.Color(255,255,255));
		btnsrc.setFont(new Font("Tahoma",0,11));
		Frame2.add(btnsrc);
		btnsrc.addActionListener(this);
		

		btndest = new javax.swing.JButton();
		btndest.setText("Browse...");
		btndest.setBounds(632,91,105,33);
		btndest.setBackground(new java.awt.Color(255,255,255));
		btndest.setFont(new Font("Tahoma",0,11));
		Frame2.add(btndest);
		btndest.addActionListener(this);

		lblsrc = new javax.swing.JLabel();
		lblsrc.setText("Source Path");
		lblsrc.setBounds(64,40,81,33);
		lblsrc.setOpaque(true);
		lblsrc.setBackground(new java.awt.Color(255,255,255));
		lblsrc.setFont(new Font("Lucida Sans",0,13));
		lblsrc.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		Frame2.add(lblsrc);

		lbldest = new javax.swing.JLabel();
		lbldest.setText("Destination Path");
		lbldest.setBounds(64,96,113,33);
		lbldest.setOpaque(true);
		lbldest.setBackground(new java.awt.Color(255,255,255));
		lbldest.setFont(new Font("Lucida Sans",0,13));
		lbldest.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		Frame2.add(lbldest);

		Frame1.add(Frame2);

		Frame3=new JPanel();
		Frame3.setLayout(null);
		Frame3.setLocation(656,256);
		Frame3.setSize(337,369);
		Frame3.setFont(new Font("Lucida Sans",1,13));
		Frame3.setBorder(BorderFactory.createTitledBorder("Status"));
		Frame3.setBackground(new java.awt.Color(255,255,255));
		
		 statusList=new javax.swing.JList();
		java.util.Vector List1v=new java.util.Vector(5,1);
	 	statusList.setSelectionMode(0);
		 statusList.setListData(List1v);
		 statusList.setFont(new Font("MS Sans Serif",0,11));
		List1ScrollPane=new JScrollPane(statusList,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		List1ScrollPane.setLocation(24,24);
		List1ScrollPane.setSize(289,290);
		Frame3.add(List1ScrollPane);

		btnDetail = new javax.swing.JButton();
		btnDetail.setText("More Information");
		btnDetail.setBounds(184,320,129,33);
		btnDetail.setBackground(new java.awt.Color(255,255,255));
		btnDetail.setFont(new Font("Tahoma",0,11));
		btnDetail.addActionListener(this);
		Frame3.add(btnDetail);

		Frame1.add(Frame3);

		Frame4=new JPanel();
		Frame4.setLayout(null);
		Frame4.setLocation(168,256);
		Frame4.setSize(473,369);
		Frame4.setFont(new Font("Lucida Sans",1,13));
		Frame4.setBorder(BorderFactory.createTitledBorder("List of files"));
		Frame4.setBackground(new java.awt.Color(255,255,255));
		
		srclist=new javax.swing.JList();
		java.util.Vector srclistv=new java.util.Vector(5,1);
		srclist.setSelectionMode(0);
		srclist.setListData(srclistv);
		srclist.setFont(new Font("MS Sans Serif",0,11));
		srclistScrollPane=new JScrollPane(srclist,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		srclistScrollPane.setLocation(16,48);
		srclistScrollPane.setSize(209,303);
		Frame4.add(srclistScrollPane);

		destlist=new javax.swing.JList();
		java.util.Vector destlistv=new java.util.Vector(5,1);
		destlist.setSelectionMode(0);
		destlist.setListData(destlistv);
		destlist.setFont(new Font("MS Sans Serif",0,11));
		destlistScrollPane=new JScrollPane(destlist,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		destlistScrollPane.setLocation(240,48);
		destlistScrollPane.setSize(209,303);
		Frame4.add(destlistScrollPane);

		lblsrclist = new javax.swing.JLabel();
		lblsrclist.setText("Source");
		lblsrclist.setBounds(16,24,105,25);
		lblsrclist.setOpaque(true);
		lblsrclist.setBackground(new java.awt.Color(255,255,255));
		lblsrclist.setFont(new Font("Lucida Sans",0,13));
		lblsrclist.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		Frame4.add(lblsrclist);

		lbldestlist = new javax.swing.JLabel();
		lbldestlist.setText("Destination");
		lbldestlist.setBounds(240,24,113,25);
		lbldestlist.setOpaque(true);
		lbldestlist.setBackground(new java.awt.Color(255,255,255));
		lbldestlist.setFont(new Font("Lucida Sans",0,13));
		lbldestlist.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		Frame4.add(lbldestlist);

		Frame1.add(Frame4);

		getContentPane().add(Frame1);
			
				
		srcFiles=new String[50];
		destDirs=new String[50];
		status=new String[50];
		len = -1;
		

	}
	



	public void actionPerformed(ActionEvent ae)
	{
		// Display input file chooser for frm & vbp files
		if(ae.getSource()==btnsrc)
		{
			fc=new JFileChooser();
			if(curDir != null)
				fc.setCurrentDirectory(curDir);
			ExampleFileFilter bothFilter = new ExampleFileFilter(new String[] {"frm", "vbp"}, "FRM and VBP Image Files");
			fc.addChoosableFileFilter(bothFilter);
			if(fc.showOpenDialog(new JFrame())==JFileChooser.APPROVE_OPTION)
			{
				File f = fc.getSelectedFile();
				if(f!=null)
				{
					txtsrc.setText(f.getAbsolutePath());
					curDir = fc.getCurrentDirectory();					
				}
			}
		}
		
		// Display output file chooser for destination folder selection
		if(ae.getSource()==btndest)
		{
			
			fc=new JFileChooser();
			if(curDir != null)
				fc.setCurrentDirectory(curDir);
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.showDialog(new JFrame(),"Select Folder");
			File f = fc.getSelectedFile();
			if(f!=null)
			{
				txtdest.setText(f.getAbsolutePath());
				curDir = f;
			}
		}
		
		// Add the input and output path to the source and destination list.
		if(ae.getSource()==btnadd)
		{
			if(len>49)
				new msgBox(this,"Limit","More than 50 files can not be selected");
			else
			{
				String spath = new String("");
				String dpath = new String("");
				spath = (txtsrc.getText()).trim().toLowerCase();
				dpath = (txtdest.getText()).trim().toLowerCase();
				if(spath.compareTo("")==0)
					new msgBox(this,"Source","Source path should not be empty.");
				else if(dpath.compareTo("")==0)
					new msgBox(this,"Destination","Destination path should not be empty.");
				else 
				{	
					File f = new File(dpath);
					if(f!=null)
					{
						if(!f.isDirectory())
							new msgBox(this,"Destination Error","Destination path is invalid. It should be directory.");				
						else if(!spath.endsWith(".frm") && !spath.endsWith(".vbp") && !spath.endsWith(".FRM") && !spath.endsWith(".VBP"))						
							new msgBox(this,"Source Error","Source File is not a Form file or VBProject file.");						
						else 
						{	
							len++;					
							srcFiles[len]=spath;
							destDirs[len]=dpath;
							srclist.setListData(srcFiles);
							destlist.setListData(destDirs);
							
							txtsrc.setText("");
							txtdest.setText("");
						}					
					}
				}
			}
		}
		
		
		// Start convertion
		if(ae.getSource()==btnok)
		{
			v = new Vector(5,5);
			
			if(len<0)
				new msgBox(this,"Error","Input is not specified");			
			else
			{
				int index;
				parser p;
				generator g;
				

				for(int i=0;i<=len;i++)
				{
					srcFiles[i]=srcFiles[i].replace('\\','/');
					destDirs[i]=destDirs[i].replace('\\','/');
					try{
						
						// Make Image directory at destination path to store images used in the form
						File f1 = new File(destDirs[i]+"/Images/");
						if(!f1.exists())
							f1.mkdir();
					
						// If input is Form file
						if(srcFiles[i].endsWith(".frm"))
						{
							p = new parser(srcFiles[i],destDirs[i]);
							
							// If convertable objects found
							if(p.frm!=null)
							{
								v.addElement(srcFiles[i]+": Following Objects are processed "+p.getReport()+"\nDestination :"+destDirs[i]+"\n\n");
								g = new generator(p.frm,srcFiles[i],destDirs[i]);
								status[i]=srcFiles[i]+" File processing completed\n";
								statusList.setListData(status);									
							}
						
							// Else dipslay no convertable objects found
							else
							{
								status[i]=srcFiles[i]+" File does not contain convertable objects\n";
								statusList.setListData(status);
								v.addElement(srcFiles[i]+" File does not contain convertable objects\n");
							}								
						}
						
						// If input if VB Porject file
						else if(srcFiles[i].endsWith(".vbp"))
						{
							String report=new String(srcFiles[i]+"\n");
							String vbptoFrm[] = new String[50];
							int count = 0;
							String srcDir = srcFiles[i].substring(0,srcFiles[i].lastIndexOf('/'));	
							FileInputStream fin;
						
							fin=new FileInputStream(srcFiles[i]);
							int numOfBytes=fin.available();
							byte b[];
							b=new byte[numOfBytes];
							fin.read(b);
							String vbpTxt=new String(b);
							fin.close();
							StringTokenizer s=new StringTokenizer(vbpTxt,"= \n\t\r\f");
							
							// Extract form files of the project
							while(s.hasMoreTokens())
							{
								String token = s.nextToken();
								System.out.println(token+" ");
								if(token.compareTo("Form")==0)
								{
									token = s.nextToken();
									token = token.replace('\\','/');
									if(token.indexOf('/')!=-1)
										vbptoFrm[count]= token;
									else
										vbptoFrm[count]= srcDir+"/"+token;
									count++;
									
								}
							}
							for(int k=0;k<count;k++)
							{
								System.out.println("**************************************************************");
								System.out.println(vbptoFrm[k]);
								if(vbptoFrm[k].endsWith(".frm"))
								{
									
									p = new parser(vbptoFrm[k],destDirs[i]);										
									if(p.frm!=null)
									{
										g = new generator(p.frm,vbptoFrm[k],destDirs[i]);
										report+= "\n"+vbptoFrm[k]+": Following Objects are processed "+p.getReport()+"\nDestination :"+destDirs[i]+"\n\n";											
									}
									else
									{
										report+= "\n"+vbptoFrm[k]+" File does not contain convertible objects.\n";
									}									
									
								}									
							}		
							status[i]=srcFiles[i]+" File processing completed\n";
							statusList.setListData(status);
							v.addElement(report);
														
						}
					}
					catch(FileNotFoundException e)
					{
						status[i]=srcFiles[i]+" File cannot be opened\n";
						statusList.setListData(status);
						v.addElement(srcFiles[i]+" File cannot be opened\n");			
						System.out.println("File cannot be opened\n");
					}	
					catch(IOException e)
					{
						System.out.println("Cannot carry forward ioexception\n");
					}
				}						

		    }
		}
		
		
		
		// Remove input from list
		if(ae.getSource()==btnremove)
		{
			// removing selected files from source list
			int index=srclist.getSelectedIndex();
			if(index>len)
				new msgBox(this,"Select","Please Select the file from Source list.");
			if(index==-1)
				new msgBox(this,"Select","Please Select the file from Source list.");
			else
			{
				status=new String[50];
				statusList.setListData(status);
				for(int i=index;i<len;i++)
				{
					srcFiles[i]=srcFiles[i+1];
					destDirs[i]=destDirs[i+1];
				}
				srcFiles[len]="";
				destDirs[len]="";
				srclist.setListData(srcFiles);
				destlist.setListData(destDirs);
				len--;				
			}
		}
		
		// Reset input list		
		if(ae.getSource()==btnreset)
		{

			srcFiles=new String[50];
			destDirs=new String[50];
			status=new String[50];
			statusList.setListData(status);
			srclist.setListData(srcFiles);
			destlist.setListData(destDirs);	
			len=-1;					
		}
		
		// Display details of convertion
		if(ae.getSource()==btnDetail)
		{
			// Display the details of the selected file
			if(v==null)
				;
			else
			{
				int index=statusList.getSelectedIndex();
			
				if(index>=v.size())
					new msgBox(this,"Select","Please Select the file from Status list.");
				if(index==-1)
					new msgBox(this,"Select","Please Select the file from Status list.");
				else
				{				
					try
					{
						new reportFrame(this,srcFiles[index]+" Report",(String)v.get(index));
					}
					catch(Exception e)
					{
						
					}
				}
			}
		}		
	}
	


	public static void main(String args[])
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception exc)
 		{
  		}
		Main f=new Main();
		f.setVisible(true);
	}
}