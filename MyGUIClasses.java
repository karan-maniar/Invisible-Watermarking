import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

// My own implementation of Graphical controls

class MyJButton extends JButton
{
	public MyJButton(ImageIcon icon)
	{
		super(icon);
		setBackground(Color.white);
		setContentAreaFilled(false);
		setBorderPainted(false);
	}

	public MyJButton(ImageIcon icon, ImageIcon rollOverIcon)
	{
		super(icon);
		setRolloverIcon(rollOverIcon);
		setPressedIcon(icon);
		setBackground(Color.white);
		setContentAreaFilled(false);
		setBorderPainted(false);
	}

	public MyJButton(String icon)
	{
		super(new ImageIcon(icon));
		setBackground(Color.white);
		setContentAreaFilled(false);
		setBorderPainted(false);
	}

	public MyJButton(String icon, String rollOverIcon)
	{
		super(new ImageIcon(icon));
		setRolloverIcon(new ImageIcon(rollOverIcon));
		setPressedIcon(new ImageIcon(icon));
		setBackground(Color.white);
		setContentAreaFilled(false);
		setBorderPainted(false);
	}

	public MyJButton(String caption, Color foreground, Color background)
	{
		super(caption);
		setForeground(foreground);
		setBackground(background);
	}
}

class MyJLabel extends JLabel
{
	public MyJLabel(String caption, Color foreground, Color background)
	{
		super(caption);
		setForeground(foreground);
		setBackground(background);
	}
	
	public MyJLabel(String caption, Font font, Color foreground, Color background)
	{
		super(caption);
		setForeground(foreground);
		setBackground(background);
		setFont(font);
	}
}

class MyJTextField extends JTextField
{
	public MyJTextField(String caption, int size, Color foreground, Color background)
	{
		super(caption, size);
		setForeground(foreground);
		setBackground(background);
	}
}

class MyJMenu extends JMenu
{
	public MyJMenu(String caption, int mnemonicIndex, int mnemonic)
	{
		super(caption);
		setDisplayedMnemonicIndex(mnemonicIndex);
		setMnemonic(mnemonic);
	}
}

class MyJMenuItem extends JMenuItem
{
	public MyJMenuItem(String caption, int mnemonicIndex, int mnemonic)
	{
		super(caption);
		setDisplayedMnemonicIndex(mnemonicIndex);
		setMnemonic(mnemonic);
	}
}

class MyJRadioButtonMenuItem extends JRadioButtonMenuItem
{
	public MyJRadioButtonMenuItem(String caption, int mnemonicIndex, int mnemonic)
	{
		super(caption);
		setDisplayedMnemonicIndex(mnemonicIndex);
		setMnemonic(mnemonic);
	}
}

// Class to perform utility operations
class UtilityOperations
{	
	public static JPanel createBorderedPanel(JPanel panel, String title, int hPad, int vPad)
	{
		int i;
		char chars[]= new char[hPad];
		for(i=0; i<chars.length; i++)	chars[i]= ' ';
		String hString= new String(chars);
		chars= new char[vPad];
		for(i=0; i<chars.length; i++)	chars[i]= ' ';
		String vString= new String(chars);

		JPanel newPanel= new JPanel();
		newPanel.setLayout(new BorderLayout());
		newPanel.add(panel, BorderLayout.CENTER);
		newPanel.add(new JLabel(vString), BorderLayout.NORTH);
		newPanel.add(new JLabel(vString), BorderLayout.SOUTH);
		newPanel.add(new JLabel(hString), BorderLayout.EAST);
		newPanel.add(new JLabel(hString), BorderLayout.WEST);
		newPanel.setBorder(new TitledBorder(title));
		newPanel.setBackground(panel.getBackground());
		newPanel.setForeground(panel.getForeground());
		return newPanel;
	}

	public static JPanel createBorderedPanel(JPanel panel, int hPad, int vPad)
	{
		int i;
		char chars[]= new char[hPad];
		for(i=0; i<chars.length; i++)	chars[i]= ' ';
		String hString= new String(chars);
		chars= new char[vPad];
		for(i=0; i<chars.length; i++)	chars[i]= ' ';
		String vString= new String(chars);

		JPanel newPanel= new JPanel();
		newPanel.setLayout(new BorderLayout());
		newPanel.add(panel, BorderLayout.CENTER);
		newPanel.add(new JLabel(vString), BorderLayout.NORTH);
		newPanel.add(new JLabel(vString), BorderLayout.SOUTH);
		newPanel.add(new JLabel(hString), BorderLayout.EAST);
		newPanel.add(new JLabel(hString), BorderLayout.WEST);
		return newPanel;
	}
}