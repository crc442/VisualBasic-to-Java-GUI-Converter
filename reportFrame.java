
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

//This class provides the report in HTML format.It can be either read or save the report in HTML format.

public class reportFrame implements ActionListener
{
	
	
	// Dialog box used to display report.
	    JDialog box;

    //Button to close
    JButton btnClose;

     //Button to save
    JButton save;

     // Textarea to display report
    JTextArea report;

     // String to store report 
    String dataReport;
    
     // String to store title
    String title;
    
    
     //JFrame object to display report dialog    
    JFrame parent;
    

    public reportFrame(JFrame owner, String Title, String s1)
        throws FileNotFoundException, IOException
    {
    	title=Title;
        parent = owner;
        dataReport = s1;
        box = new JDialog(parent, Title, false);
        box.setLocation(50, 50);
        box.setSize(600, 500);
        box.getContentPane().setLayout(null);
        
        JLabel jlabel = new JLabel("Details :");
        jlabel.setBounds(20, 0, 50, 50);
        box.getContentPane().add(jlabel);
        
        report = new JTextArea(dataReport);
        JScrollPane jscrollpane = new JScrollPane(report, 20, 30);
        jscrollpane.setLocation(20, 40);
        jscrollpane.setSize(550, 380);
        report.setFont(new Font("Arial", 0, 12));
        box.getContentPane().add(jscrollpane);
        save = new JButton("Save As");
        save.setBounds(40, 430, 80, 30);
        box.getContentPane().add(save);
        save.addActionListener(this);
        btnClose = new JButton("Close");
        btnClose.setBounds(150, 430, 80, 30);
        box.getContentPane().add(btnClose);
        btnClose.addActionListener(this);
        box.setVisible(true);
    }

    public void actionPerformed(ActionEvent actionevent)
    {
    	FileOutputStream fos;
        if(actionevent.getSource() == btnClose)
            box.setVisible(false);
        if(actionevent.getSource() == save)
        {
            JFileChooser jfilechooser = new JFileChooser();
            JFrame temp = new JFrame();
            temp.setBounds(50, 50, 600, 350);
            ExampleFileFilter examplefilefilter = new ExampleFileFilter(new String[] {"html","htm"}, "HTM, HTML File");
            jfilechooser.addChoosableFileFilter(examplefilefilter);
            if(jfilechooser.showSaveDialog(temp) == 0)
            {
            	File file;
            	String filename = jfilechooser.getSelectedFile().toString().trim();
            	if(filename.endsWith(".htm")||filename.endsWith(".html"))
            		file = new File(filename);
            	else
            		file = new File(filename+".htm");
                if(file != null)
                    try
                    {
                    	int index=dataReport.indexOf("\n");
                    	while(index!=-1)
                    	{
                    		dataReport= dataReport.substring(0,index)+"<BR>"+dataReport.substring(index+1);
                    		index=dataReport.indexOf("\n");
                    	}
                    	dataReport="<HTML><TITLE>"+title+"</TITLE><BODY>"+dataReport+"</BODY></HTML>";
                        fos = new FileOutputStream(file);
                        fos.write(dataReport.getBytes());
                        fos.close();
                    }
                    catch(Exception exception)
                    {
                        new msgBox(parent, "Error", "Error while saving");
                    }
            }
        }
    }
	

}
