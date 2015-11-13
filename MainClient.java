/*
Author: Karan Maniar
Email ID: karan.maniar@hotmail.com
*/

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class MainClient extends WindowAdapter implements ActionListener
{
	private JFrame		 mainFrame;
	private JMenuBar	 menuBar;
	private JMenu		 menuFile, menuView, menuHelp, menuLookAndFeel;
	private JMenuItem	 mnuExit, mnuEmbedMessage, mnuEmbedFile, mnuHelp, mnuAbout;
	private JMenuItem	 mnuRetrieveMessage, mnuRetrieveFile;
	private JRadioButtonMenuItem	 mnuMetalFeel, mnuMotifFeel, mnuWindowsFeel;
	private ButtonGroup lookAndFeelButtonGroup;

	private JLabel lblLogo;

	private GridBagLayout gbl;
	private GridBagConstraints gbc;
	private BackEndHandler back;

	public MainClient()
	{
		mainFrame= new JFrame("Invisible Watermarking");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.addWindowListener(this);

		// Setup the menu bar
		mnuExit= new JMenuItem("Exit");
		mnuEmbedMessage= new JMenuItem("Embed Message");
		mnuEmbedFile= new JMenuItem("Embed File");
		mnuRetrieveMessage= new JMenuItem("Retrieve Message");
		mnuRetrieveFile= new JMenuItem("Retrieve File");
		mnuHelp= new JMenuItem("Help");
		mnuAbout= new JMenuItem("About");

		mnuMetalFeel= new JRadioButtonMenuItem("Metal");
		mnuMotifFeel= new JRadioButtonMenuItem("Motif");
		mnuWindowsFeel= new JRadioButtonMenuItem("Windows");

		// Add item listener for Look and feel menu items
		RadioListener radioListener= new RadioListener();

		mnuMetalFeel.addItemListener(radioListener);
		mnuMotifFeel.addItemListener(radioListener);
		mnuWindowsFeel.addItemListener(radioListener);


		lookAndFeelButtonGroup= new ButtonGroup();

		lookAndFeelButtonGroup.add(mnuMetalFeel);
		lookAndFeelButtonGroup.add(mnuMotifFeel);
		lookAndFeelButtonGroup.add(mnuWindowsFeel);

		// Add action listeners for other menu items
		mnuEmbedMessage.addActionListener(this);
		mnuEmbedFile.addActionListener(this);
		mnuRetrieveMessage.addActionListener(this);
		mnuRetrieveFile.addActionListener(this);
		mnuExit.addActionListener(this);
		mnuHelp.addActionListener(this);
		mnuAbout.addActionListener(this);

		menuFile= new JMenu("File");
		menuFile.add(mnuEmbedMessage);
		menuFile.add(mnuEmbedFile);
		menuFile.add(mnuRetrieveMessage);
		menuFile.add(mnuRetrieveFile);
		menuFile.add(mnuExit);

		menuLookAndFeel= new JMenu("Look and Feel...");

		menuLookAndFeel.add(mnuMetalFeel);
		menuLookAndFeel.add(mnuMotifFeel);
		menuLookAndFeel.add(mnuWindowsFeel);
		menuView= new JMenu("View");
		menuView.add(menuLookAndFeel);

		menuHelp= new JMenu("Help");
		menuHelp.add(mnuHelp);
		menuHelp.add(mnuAbout);

		menuBar= new JMenuBar();
		menuBar.add(menuFile);
		menuBar.add(menuView);
		menuBar.add(menuHelp);
		mainFrame.setJMenuBar(menuBar);




		lblLogo= new JLabel(new ImageIcon("Images/Logo.jpg"));

		Dimension d= Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.add(lblLogo);
		mainFrame.setSize(d.width, (int) (d.height-(d.height*.03)));
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
	}

	// Listener methods
	public void actionPerformed(ActionEvent e)
	{
		Object source= e.getSource();

		// Embed message operation
		if(source== mnuEmbedMessage)
		{
			back= new BackEndHandler(this, BackEndHandler.EMBED_MESSAGE);
			back.start();
		}

		// Retrieve message operation
		if(source== mnuRetrieveMessage)
		{
			back= new BackEndHandler(this, BackEndHandler.RETRIEVE_MESSAGE);
			back.start();
		}

		// Embed file operation
		if(source== mnuEmbedFile)
		{
			back= new BackEndHandler(this, BackEndHandler.EMBED_FILE);
			back.start();
		}

		// Retrieve file operation
		if(source== mnuRetrieveFile)
		{
			back= new BackEndHandler(this, BackEndHandler.RETRIEVE_FILE);
			back.start();
		}

		if(source== mnuHelp)
		{
			try
			{
				Runtime.getRuntime().exec("hh.exe Help.html");
			}
			catch(Exception e1)
			{
				System.out.print("\n Error : " + e1);
			}
		}
		if(source== mnuAbout)
		{
			JOptionPane.showMessageDialog(mainFrame,"This software is developed by Karan Maniar, Siddhant Pai and Nikhil Kulkarni.","About",JOptionPane.INFORMATION_MESSAGE);
		}

		if(source== mnuExit)
		{
			int result= JOptionPane.showConfirmDialog(mainFrame, "Are you sure that you want to Exit ?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
			if(result== JOptionPane.YES_OPTION)
			{
				System.exit(0);
			}
		}
	}

	public void windowClosing(WindowEvent w)
	{
		
	}

	// Class for lissoning to Look and feel radio menu events
	private class RadioListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			JMenuItem item= (JMenuItem) e.getSource();
			try
			{
				if(item== mnuMetalFeel && mnuMetalFeel.isSelected())
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

				if(item== mnuMotifFeel && mnuMotifFeel.isSelected())
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

				if(item== mnuWindowsFeel && mnuWindowsFeel.isSelected())
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

				SwingUtilities.updateComponentTreeUI(mainFrame);
				Watermark.updateUserInterface();
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(mainFrame, "Oops!!\n"+ "Unable to load "+ item.getText()+ " Look and feel.", "Warning!", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	// Main method
	/*public static void main(String args[])
	{
		new MainClient();
	}*/
}