import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Hashtable;

public class EmbedMessageGUI extends JFrame implements ActionListener, ItemListener
{
	BackEndHandler client;

	private JLabel lblMaster, lblOutput, lblMasterSize, lblOutputSize, lblMessage;;
	private JLabel lblMasterFileSize, lblOutputFileSize, lblStatus, lblFiller[];
	private JLabel lblCompression;
	private JCheckBox chkCompress;
	private JSlider   sliderCompression;
	private JTextField txtMasterFile, txtOutputFile;
	private JTextArea txtMessage;
	private JScrollPane scrollPane;
	private JButton btnGo, btnHelp, btnCancel, btnChangeMasterFile, btnChangeOutputFile;

	public EmbedMessageGUI(BackEndHandler client)
	{
		super("Embedding message - Watermarking");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.client= client;
		Font arialFont= new Font("Arial", Font.PLAIN, 11);

		// Create filler labels
		lblFiller= new JLabel[11];
		for(int i=0; i<11; i++)
			lblFiller[i]= new JLabel(" ");

		lblMaster= new MyJLabel("Master file", arialFont, Color.black, Color.lightGray);
		lblOutput= new MyJLabel("Output file", arialFont, Color.black, Color.lightGray);
		lblMasterSize= new MyJLabel("Size: ", arialFont, Color.black, Color.lightGray);
		lblOutputSize= new MyJLabel("Size: ", arialFont, Color.black, Color.lightGray);
		txtMasterFile= new MyJTextField(client.getMasterFile().getName(), 13, Color.blue, Color.lightGray);
		txtMasterFile.setEditable(false);
		lblMasterFileSize= new MyJLabel(""+ client.getMasterFile().length()/1024+ " Kb", arialFont, Color.red, Color.gray);

		txtOutputFile= new MyJTextField(client.getOutputFile().getName(), 13, Color.blue, Color.lightGray);
		txtOutputFile.setEditable(false);
		lblOutputFileSize= new MyJLabel(lblMasterFileSize.getText(), arialFont, Color.red, Color.gray);
		lblMessage= new MyJLabel("Message:", arialFont, Color.black, Color.lightGray);
		lblStatus= new MyJLabel("Minimum required size of the Master file: 32Kb", arialFont, Color.black, Color.lightGray);
		txtMessage= new JTextArea(12, 64);
		scrollPane= new JScrollPane(txtMessage);

		btnChangeMasterFile= new JButton("Change");
		btnChangeOutputFile= new JButton("Change");
		btnGo= new JButton("   Go   ");
		btnHelp= new JButton("  Help  ");
		btnCancel= new JButton(" Close ");

		// Setup panelFiles
		JPanel panelFiles= new JPanel();
		GridBagLayout gbl= new GridBagLayout();
		GridBagConstraints gbc= new GridBagConstraints();
		panelFiles.setLayout(gbl);

		gbc.insets= new Insets(2,2,2,2);
		gbc.gridx= 1;	gbc.gridy= 1;	gbl.setConstraints(lblMaster, gbc);
		panelFiles.add(lblMaster);
		gbc.gridx= 2;	gbl.setConstraints(txtMasterFile, gbc);
		panelFiles.add(txtMasterFile);
		gbc.gridx= 3;	gbl.setConstraints(lblMasterSize, gbc);
		panelFiles.add(lblMasterSize);
		gbc.gridx= 4;	gbl.setConstraints(lblMasterFileSize, gbc);
		panelFiles.add(lblMasterFileSize);
		gbc.gridx= 5;	gbl.setConstraints(lblFiller[0], gbc);
		panelFiles.add(lblFiller[0]);
		gbc.gridx= 6;	gbl.setConstraints(lblOutput, gbc);
		panelFiles.add(lblOutput);
		gbc.gridx= 7;	gbl.setConstraints(txtOutputFile, gbc);
		panelFiles.add(txtOutputFile);
		gbc.gridx= 8;	gbl.setConstraints(lblOutputSize, gbc);
		panelFiles.add(lblOutputSize);
		gbc.gridx= 9;	gbl.setConstraints(lblOutputFileSize, gbc);
		panelFiles.add(lblOutputFileSize);

		gbc.gridx= 2;	gbc.gridy= 2;	gbl.setConstraints(btnChangeMasterFile, gbc);
		panelFiles.add(btnChangeMasterFile);
		gbc.gridx= 7;	gbl.setConstraints(btnChangeOutputFile, gbc);
		panelFiles.add(btnChangeOutputFile);
		panelFiles=  UtilityOperations.createBorderedPanel(panelFiles, "Files", 19, 3);

		// Setup panelFeatures
		lblCompression= new JLabel("Compression level");
		chkCompress= new JCheckBox("Compress");
		sliderCompression= new JSlider(0, 9, 5);
		sliderCompression.setPaintTicks(true);
		sliderCompression.setPaintLabels(true);
		sliderCompression.setSnapToTicks(true);
		sliderCompression.setMajorTickSpacing(1);
		sliderCompression.setForeground(Color.blue);
		Hashtable h= new Hashtable();
		h.put(new Integer(0), new JLabel("0", JLabel.CENTER));
		h.put(new Integer(5), new JLabel("5", JLabel.CENTER));
		h.put(new Integer(9), new JLabel("9", JLabel.CENTER));
		sliderCompression.setLabelTable(h); 

		chkCompress.addItemListener(this);
		lblCompression.setEnabled(false);
		sliderCompression.setEnabled(false);

		JPanel panelCompression1= new JPanel();
		new BoxLayout(panelCompression1, BoxLayout.X_AXIS);
		panelCompression1.add(chkCompress);
		panelCompression1.add(lblCompression);

		JPanel panelCompression2= new JPanel();
		new BoxLayout(panelCompression2, BoxLayout.X_AXIS);
		panelCompression2.add(new MyJLabel("Low", arialFont, Color.blue, Color.lightGray));
		panelCompression2.add(sliderCompression);
		panelCompression2.add(new MyJLabel("High", arialFont, Color.blue, Color.lightGray));

		JPanel panelCompression= new JPanel();
		gbl= new GridBagLayout();
		panelCompression.setLayout(gbl);
		gbc.gridx= 1; gbc.gridy=1; gbl.setConstraints(panelCompression1, gbc);
		panelCompression.add(panelCompression1);
		gbc.gridy= 2; gbl.setConstraints(panelCompression2, gbc);
		panelCompression.add(panelCompression2);
		panelCompression= UtilityOperations.createBorderedPanel(panelCompression, "Compression", 3, 3);

		JPanel panelFeatures= new JPanel();
		new BoxLayout(panelFeatures, BoxLayout.X_AXIS);
		panelFeatures.add(panelCompression);
		
		// Setup panelText
		JPanel panelText= new JPanel();
		gbl= new GridBagLayout();		
		panelText.setLayout(gbl);
		gbc.gridy= 2;	gbc.anchor= gbc.WEST;	gbl.setConstraints(lblMessage, gbc);
		panelText.add(lblMessage);
		gbc.gridy= 3;	gbc.anchor= gbc.CENTER;	gbl.setConstraints(scrollPane, gbc);
		panelText.add(scrollPane);
		gbc.gridy= 4;	gbl.setConstraints(lblStatus, gbc);
		panelText.add(lblStatus);

		// Setup panelButtons
		JPanel panelButtons= new JPanel();
		gbl= new GridBagLayout();
		panelButtons.setLayout(gbl);
		gbc.anchor= gbc.CENTER;	gbc.gridx= 1; gbc.gridy= 1;	gbl.setConstraints(btnGo, gbc);
		panelButtons.add(btnGo);
		gbc.gridy= 2;gbl.setConstraints(lblFiller[3], gbc);
		panelButtons.add(lblFiller[3]);
		gbc.gridy= 3;gbl.setConstraints(btnHelp, gbc);
		panelButtons.add(btnHelp);
		gbc.gridy= 4;gbl.setConstraints(lblFiller[4], gbc);
		panelButtons.add(lblFiller[4]);
		gbc.gridy= 5;gbl.setConstraints(btnCancel, gbc);
		panelButtons.add(btnCancel);

		JPanel panelLower= new JPanel();
		new BoxLayout(panelLower, BoxLayout.X_AXIS);
		panelLower.add(panelText);
		panelLower.add(panelButtons);		

		JPanel mainPanel= new JPanel();
		new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.add(panelFiles);
		mainPanel.add(lblFiller[10]);
		mainPanel.add(panelFeatures);
		mainPanel.add(panelLower);

		mainPanel= UtilityOperations.createBorderedPanel(mainPanel, 3, 3);
		setContentPane(mainPanel);

		btnChangeMasterFile.addActionListener(this);
		btnChangeOutputFile.addActionListener(this);
		btnHelp.addActionListener(this);
		btnGo.addActionListener(this);
		btnCancel.addActionListener(this);

		Dimension d= Toolkit.getDefaultToolkit().getScreenSize();
		int width= (int)(0.91* d.width);
		int height= (int)(0.935* d.height);
		setSize(width, height);
		setLocation((d.width- width)/2, (d.height- height)/2);
		//setResizable(false);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source= e.getSource();

		if(source== btnChangeMasterFile)
		{
			client.chooseMasterFile();
			txtMasterFile.setText(client.getMasterFile().getName());
			lblMasterFileSize.setText(""+ client.getMasterFile().length()/1024+ "Kb");
			lblOutputFileSize.setText(lblMasterFileSize.getText());
		}

		if(source== btnChangeOutputFile)
		{
			client.chooseOutputFile();
			txtOutputFile.setText(client.getOutputFile().getName());
		}

		if(source== btnHelp)
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

		if(source== btnCancel)
			dispose();

		if(source== btnGo)
		{
			int compression= -1;
			String password= null;

			//---------------------------------------------
				String om = txtMessage.getText();
				String com = "";
				char aom[] = om.toCharArray();
				int oml = om.length();
				for(int ci=0;ci<oml;ci++)
				{
					com = com + (31 + (int)aom[ci]) + "-";
				}

			//---------------------------------------------

			if(txtMessage.getText().length()<1)
			{
				JOptionPane.showMessageDialog(this, "Please enter the message\nYou can also paste the message on clipboard using\nCtrl+V combination.", "Empty message!", JOptionPane.WARNING_MESSAGE);
				return;
			}

			if(chkCompress.isSelected())
				compression= sliderCompression.getValue();


			if(client.getOutputFile().exists())
			{
				int result2= JOptionPane.showConfirmDialog(null, "File "+ client.getOutputFile().getName()+ " already exists!\nWould you like to OVERWRITE it?", "File already exists!", JOptionPane.YES_NO_OPTION);
				if(!(result2== JOptionPane.YES_OPTION))
				if(!client.chooseOutputFile())
					return;
			}

			if(Watermark.embedMessage(client.getMasterFile(), client.getOutputFile(), com, compression, password))
				JOptionPane.showMessageDialog(this, Watermark.getMessage(), "Operation Successful", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(this, Watermark.getMessage(), "Operation Unsuccessful", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void itemStateChanged(ItemEvent e)
	{
		if(e.getSource()== chkCompress)
		{
			if(chkCompress.isSelected())
			{
				lblCompression.setEnabled(true);
				sliderCompression.setEnabled(true);
			}
			else
			{
				lblCompression.setEnabled(false);
				sliderCompression.setEnabled(false);
			}
		}
	}
}