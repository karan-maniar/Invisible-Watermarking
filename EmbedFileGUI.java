import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Hashtable;

public class EmbedFileGUI extends JFrame implements ActionListener, ItemListener
{
	BackEndHandler client;

	private JLabel lblMaster, lblOutput, lblData, lblMasterSize, lblOutputSize, lblMessage;;
	private JLabel lblMasterFileSize, lblOutputFileSize, lblStatus;
	private JLabel lblDataSize, lblDataFileSize;
	private JLabel lblCompression;
	private JCheckBox chkCompress;
	private JSlider	sliderCompression;
	private JTextField txtMasterFile, txtOutputFile, txtDataFile;
	private JScrollPane scrollPane;
	private JButton btnGo, btnHelp, btnCancel;
	private JButton btnChangeMasterFile, btnChangeOutputFile, btnChangeDataFile;

	public EmbedFileGUI(BackEndHandler client)
	{
		super("Embedding file - Watermarking");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.client= client;

		Font arialFont= new Font("Arial", Font.PLAIN, 11);
		lblMaster= new JLabel("Master file");
		lblOutput= new JLabel("Output file");
		lblData= 	new JLabel("Data file");
		lblMasterSize= new JLabel("Size: ");
		lblOutputSize= new JLabel("Size: ");
		lblDataSize=  new JLabel("Size: ");
		txtMasterFile= new MyJTextField(client.getMasterFile().getName(), 13, Color.blue, Color.lightGray);
		txtMasterFile.setEditable(false);
		lblMasterFileSize= new MyJLabel(""+ client.getMasterFile().length()/1024+ " Kb", arialFont, Color.red, Color.gray);

		txtOutputFile= new MyJTextField(client.getOutputFile().getName(), 13, Color.blue, Color.lightGray);
		txtOutputFile.setEditable(false);
		lblOutputFileSize= new MyJLabel(lblMasterFileSize.getText(), arialFont, Color.red, Color.gray);

		txtDataFile= new MyJTextField(client.getDataFile().getName(), 13, Color.blue, Color.lightGray);
		txtDataFile.setEditable(false);
		lblDataFileSize= new MyJLabel(""+ client.getDataFile().length()/1024+ " Kb", arialFont, Color.red, Color.gray);

		btnChangeMasterFile= new JButton("Change");
		btnChangeOutputFile= new JButton("Change");
		btnChangeDataFile=	 new JButton("Change");
		btnGo= new JButton("    Go    ");
		btnHelp= new JButton("   Help   ");
		btnCancel= new JButton("  Close  ");

		lblCompression= new MyJLabel("Compression level", arialFont, Color.black, Color.lightGray);
		chkCompress= new JCheckBox("Compress");

		sliderCompression= new JSlider(0, 9, 5);
		sliderCompression.setForeground(Color.blue);
		sliderCompression.setPaintTicks(true);
		sliderCompression.setPaintLabels(true);
		sliderCompression.setSnapToTicks(true);
		sliderCompression.setMajorTickSpacing(1);
		Hashtable h= new Hashtable();
		h.put(new Integer(0), new JLabel("0".toString(), JLabel.CENTER));
		h.put(new Integer(5), new JLabel("5".toString(), JLabel.CENTER));
		h.put(new Integer(9), new JLabel("9".toString(), JLabel.CENTER));
		sliderCompression.setLabelTable(h);

		lblCompression.setEnabled(false);
		sliderCompression.setEnabled(false);		

		// Setup panel file1
		JPanel file1= new JPanel();
		new BoxLayout(file1, BoxLayout.X_AXIS);
		file1.add(lblMaster);
		file1.add(txtMasterFile);
		file1.add(lblMasterSize);
		file1.add(lblMasterFileSize);
		file1.add(btnChangeMasterFile);

		// Setup panel file2
		JPanel file2= new JPanel();
		new BoxLayout(file2, BoxLayout.X_AXIS);
		file2.add(lblOutput);
		file2.add(txtOutputFile);
		file2.add(lblOutputSize);
		file2.add(lblOutputFileSize);
		file2.add(btnChangeOutputFile);

		// Setup panel file3
		JPanel file3= new JPanel();
		new BoxLayout(file3, BoxLayout.X_AXIS);
		file3.add(lblData);
		file3.add(txtDataFile);
		file3.add(lblDataSize);
		file3.add(lblDataFileSize);
		file3.add(btnChangeDataFile);

		JPanel panelFiles= new JPanel();
		GridBagLayout gbl= new GridBagLayout();
		GridBagConstraints gbc= new GridBagConstraints();
		panelFiles.setLayout(gbl);
		JLabel lblFiller;
		gbc.anchor= gbc.WEST;	gbc.gridx=1; gbc.gridy= 1;
		gbl.setConstraints(file1, gbc);
		panelFiles.add(file1);
		lblFiller= new JLabel(" ");
		gbc.gridy= 2;		gbl.setConstraints(lblFiller, gbc);
		panelFiles.add(lblFiller);
		gbc.gridy= 3;		gbl.setConstraints(file2, gbc);
		panelFiles.add(file2);
		lblFiller= new JLabel(" ");
		gbc.gridy= 4;		gbl.setConstraints(lblFiller, gbc);
		panelFiles.add(lblFiller);
		gbc.gridy= 5;		gbl.setConstraints(file3, gbc);
		panelFiles.add(file3);
		panelFiles= UtilityOperations.createBorderedPanel(panelFiles, "Files", 19, 3);

		// Setup features panel 1
		JPanel panelFeatures1a= new JPanel();
		new BoxLayout(panelFeatures1a, BoxLayout.X_AXIS);
		panelFeatures1a.add(chkCompress);
		panelFeatures1a.add(lblCompression);

		JPanel panelFeatures1b= new JPanel();
		new BoxLayout(panelFeatures1b, BoxLayout.X_AXIS);
		panelFeatures1b.add(new MyJLabel("Low", arialFont, Color.blue, Color.lightGray));
		panelFeatures1b.add(sliderCompression);
		panelFeatures1b.add(new MyJLabel("High", arialFont, Color.blue, Color.lightGray));

		JPanel panelFeatures1= new JPanel();
		gbl= new GridBagLayout();
		panelFeatures1.setLayout(gbl);
		gbc.gridx= 1;	gbc.gridy= 1;	gbl.setConstraints(panelFeatures1a, gbc);
		panelFeatures1.add(panelFeatures1a);
		gbc.gridy= 2;	gbl.setConstraints(panelFeatures1b, gbc);
		panelFeatures1.add(panelFeatures1b);
		panelFeatures1= UtilityOperations.createBorderedPanel(panelFeatures1, "Compression", 10, 3);


		JPanel panelFeatures= new JPanel();
		gbl= new GridBagLayout();
		panelFeatures.setLayout(gbl);
		gbc.anchor= gbc.WEST;	gbc.gridx=1; gbc.gridy= 1;
		gbl.setConstraints(panelFeatures1, gbc);
		//panelFeatures.add(panelFeatures1);
		lblFiller= new JLabel(" ");
		gbc.gridy= 2;		gbl.setConstraints(lblFiller, gbc);
		panelFeatures.add(lblFiller);

		// Setup the buttons panel
		JPanel panelButtons= new JPanel();
		gbl= new GridBagLayout();
		panelButtons.setLayout(gbl);
		gbc.anchor= gbc.WEST;	gbc.gridx=1; gbc.gridy= 1;
		gbl.setConstraints(btnGo, gbc);
		panelButtons.add(btnGo);
		lblFiller= new JLabel(" ");
		gbc.gridy= 2;		gbl.setConstraints(lblFiller, gbc);
		panelButtons.add(lblFiller);
		gbc.gridy= 3;		gbl.setConstraints(btnHelp, gbc);
		panelButtons.add(btnHelp);
		lblFiller= new JLabel(" ");
		gbc.gridy= 4;		gbl.setConstraints(lblFiller, gbc);
		panelButtons.add(lblFiller);
		gbc.gridy= 5;		gbl.setConstraints(btnCancel, gbc);
		panelButtons.add(btnCancel);

		JPanel mainPanel= new JPanel();
		new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.add(panelFiles);
		mainPanel.add(new JLabel(" "));
		mainPanel.add(panelFeatures);
		mainPanel.add(new JLabel(" "));
		mainPanel.add(panelButtons);
		mainPanel= UtilityOperations.createBorderedPanel(mainPanel, 3, 3);
		setContentPane(mainPanel);

		btnChangeMasterFile.addActionListener(this);
		btnChangeOutputFile.addActionListener(this);
		btnChangeDataFile.addActionListener(this);
		btnHelp.addActionListener(this);
		btnGo.addActionListener(this);
		btnCancel.addActionListener(this);		

		Dimension d= Toolkit.getDefaultToolkit().getScreenSize();
		int width= (int)(0.7* d.width);
		int height= (int)(0.85* d.height);
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

		if(source== btnChangeDataFile)
		{
			client.chooseDataFile();
			txtDataFile.setText(client.getDataFile().getName());
			lblDataFileSize.setText(""+ client.getDataFile().length()/1024+ "Kb");
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
			int compression= sliderCompression.getValue();
			String password= null;

			if(client.getOutputFile().exists())
			{
				int result2= JOptionPane.showConfirmDialog(null, "File "+ client.getOutputFile().getName()+ " already exists!\nWould you like to OVERWRITE it?", "File already exists!", JOptionPane.YES_NO_OPTION);
				if(!(result2== JOptionPane.YES_OPTION))
				if(!client.chooseOutputFile())
					return;
			}

			if(Watermark.embedFile(client.getMasterFile(), client.getOutputFile(), client.getDataFile(), compression, password))
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