import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class BackEndHandler extends Thread
{
	public static final short EMBED_MESSAGE= 	0;
	public static final short EMBED_FILE= 		1;
	public static final short RETRIEVE_MESSAGE= 	2;
	public static final short RETRIEVE_FILE= 	3;

	private short operation;
	private WindowAdapter client;

	private JFileChooser fileChooser;
	private MyFileView	  fileView;
	private File masterFile, dataFile, outputFile;

	private int result, result2;

	public BackEndHandler(WindowAdapter client, short operation)
	{
		this.client= client;
		this.operation= operation;

		// Setup file chooser
		fileChooser= new JFileChooser("./");
		fileChooser.setFileSelectionMode(fileChooser.FILES_ONLY);
		fileChooser.setDialogType(fileChooser.CUSTOM_DIALOG);
		//MyFileView fileView = new MyFileView();
		//fileView.putIcon("jpg", new ImageIcon("Images/image.jpg"));
		//fileView.putIcon("gif", new ImageIcon("Images/image.jpg"));
		//fileView.putIcon("bmp", new ImageIcon("Images/image.jpg"));
		//fileChooser.setFileView(fileView); 
		fileChooser.setAccessory(new FilePreviewer(fileChooser));

		// Create and set the file filter
		MyFileFilter filter1= new MyFileFilter(new String[]{"bmp", "jpg", "gif", "tif"}, "Picture files");
		MyFileFilter filter2= new MyFileFilter(new String[]{"mp3", "wav"}, "Audio files");
		MyFileFilter filter3= new MyFileFilter(new String[]{"avi", "mpg", "dat"}, "Video files");
		fileChooser.addChoosableFileFilter(filter1);
		fileChooser.addChoosableFileFilter(filter2);
		fileChooser.addChoosableFileFilter(filter3);
	}

	public void run()
	{			
		if(!chooseMasterFile()) return;
		
		if(operation== EMBED_MESSAGE || operation== EMBED_FILE)
			if(!chooseOutputFile())	return;

		if(operation== EMBED_FILE)
			if(!chooseDataFile()) return;

		WMInformation steg;
		switch(operation)
		{
			case EMBED_MESSAGE: new EmbedMessageGUI(this); break;
			case EMBED_FILE:	 new EmbedFileGUI(this);	 break;
			case RETRIEVE_MESSAGE:
					steg= new WMInformation(masterFile);
					if(steg.isEster())
						showEster(steg);
					else
						if(!steg.isValid())
							JOptionPane.showMessageDialog(null, "File '"+ masterFile.getName()+ 
								"' does not contain any message or file", "Invalid Watermark file!", JOptionPane.WARNING_MESSAGE);
						else
							new PreRetrieveGUI(steg, PreRetrieveGUI.RETRIEVE_MESSAGE);
					break;
			case RETRIEVE_FILE:
					steg= new WMInformation(masterFile);
					if(steg.isEster())
						showEster(steg);
					else
						if(!steg.isValid())
							JOptionPane.showMessageDialog(null, "File '"+ masterFile.getName()+ 
								"' does not contain any message or file", "Invalid Watermark file!", JOptionPane.WARNING_MESSAGE);
						else
							new PreRetrieveGUI(steg, PreRetrieveGUI.RETRIEVE_FILE);					
		}
	}

	// Method for choosing input file
	public boolean chooseMasterFile()
	{
		int result;
		do
		{				
			result= fileChooser.showDialog(null, "Select Master file");
			if(result== fileChooser.APPROVE_OPTION)
			{
				masterFile= fileChooser.getSelectedFile();
				if(!checkFileExistency(masterFile))
					continue;
				else
					break;
			}
		} while(result!= fileChooser.CANCEL_OPTION);

		if(result== fileChooser.CANCEL_OPTION)	return false;
		else									return true;
	}

	// Method for choosing output file
	public boolean chooseOutputFile()
	{
		int result;
		do
		{
			File previousFile= fileChooser.getSelectedFile();
			result= fileChooser.showDialog(null, "Select Output file");
			if(result== fileChooser.APPROVE_OPTION)
			{
				outputFile= fileChooser.getSelectedFile();
				if(outputFile.exists())
				{
					result2= JOptionPane.showConfirmDialog(null, "File "+ outputFile.getName()+ " already exists!\nWould you like to OVERWRITE it?", "File already exists!", JOptionPane.YES_NO_OPTION);
					if(result2== JOptionPane.NO_OPTION)
					{
						if(previousFile!= null)
							fileChooser.setSelectedFile(previousFile);
						continue;
					}
				}
				break;
			}
		} while(result!= fileChooser.CANCEL_OPTION);

		if(result== fileChooser.CANCEL_OPTION)	return false;
		else									return true;
	}

	// Method for choosing data file
	public boolean chooseDataFile()
	{
		do
		{
			result= fileChooser.showDialog(null, "Select Data file");
			if(result== fileChooser.APPROVE_OPTION)
			{
				dataFile= fileChooser.getSelectedFile();
				if(!checkFileExistency(dataFile))
						continue;
				else
						break;
			}
		} while(result!= fileChooser.CANCEL_OPTION);

		if(result== fileChooser.CANCEL_OPTION)	return false;
		else									return true;
	}


	// Accessor methods
	public File getMasterFile()	{ return masterFile; }
	public File getOutputFile()	{ return outputFile; }
	public File getDataFile()		{ return dataFile; }

	// Mutator methods
	public void setMasterFile(File file)	{ masterFile= file; }
	public void setOutputFile(File file)	{ outputFile= file; }
	public void setDataFile(File file)		{ dataFile= file; }

	// Checks whether given file actually exists
	private boolean checkFileExistency(File file)
	{
		if(!file.exists())
		{
			JOptionPane.showMessageDialog(null, "File "+ file.getName()+ " does not exist!", "Inexistent file!", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}

	private void showMessage(String message, String title)
	{
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
	}

	private void showEster(WMInformation steg)
	{
		Object message[]= new Object[3];
		message[0]= new MyJLabel("This is an encrypted zone.", Color.red, Color.gray);
		message[1]= new JLabel("Please enter password to continue.");
		JPasswordField pass= new JPasswordField(10);
		message[2]= pass;

		String options[]= {"Retrieve now", "Cancel"};
		int result= JOptionPane.showOptionDialog(null, message, "Encrypted zone"
			  , JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

		if(result== 1)	return;

		String password= new String(pass.getPassword());
		if(password.length()<8)
			JOptionPane.showMessageDialog(null, "This was not the right password!", "Invalid password", JOptionPane.OK_OPTION);
		else
		{
			int fileSize= (int) steg.getFile().length();
			byte[] byteArray= new byte[fileSize];
			try
			{
				DataInputStream in= new DataInputStream(new FileInputStream(steg.getFile()));
				in.read(byteArray, 0, fileSize);
				in.close();

				Cipher cipher= Cipher.getInstance("DES");
				cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(password.substring(0, 8).getBytes(), "DES"));

				byteArray= cipher.doFinal(byteArray);
			}
			catch(Exception e)	{ return; }

			JFrame frame= new JFrame("Encrypt Zone...");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.getContentPane().add(new JScrollPane(new JLabel(new ImageIcon(byteArray))));
			frame.setBackground(Color.white);

			Dimension d= Toolkit.getDefaultToolkit().getScreenSize();
			frame.setSize(d.width, d.height/2);
			frame.setVisible(true);
		}
	}
}