
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//  * This class is used to display a message box with one OK button. It displays one Dialog box with specified Title

public class msgBox implements ActionListener
{

	private JDialog box;

	public msgBox(JFrame owner,String title,String msg)
	{
		// JDialog box Initialisation
		box = new JDialog(owner,title,true);
		box.setLocation(100,100);
		box.setSize(350,150);
		box.getContentPane().setLayout(null);
		
		// Message to display
		JLabel lbl = new JLabel(msg);
		lbl.setBounds(30,0,290,50);
		lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		box.getContentPane().add(lbl);
		
		// OK button  
		JButton jb = new JButton("OK");
		jb.setBounds(145,55,60,40);
		box.getContentPane().add(jb);
		jb.addActionListener(this);
		box.setVisible(true);
				
	}
	
	
	public void actionPerformed(ActionEvent ae)
	{
		box.setVisible(false);
			
	}
	

}