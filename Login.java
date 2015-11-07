import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener
{
	JLabel l1,l2;
	JTextField t1;
	JPasswordField p1;
	JButton b1,b2;
	public Login()
	{
		JPanel jp = (JPanel)getContentPane();
		jp.setLayout(new GridLayout(3,2));
		l1 = new JLabel("Username");
		l2 = new JLabel("Password");
		t1 = new JTextField(20);
		p1 = new JPasswordField(20);
		b1 = new JButton("Login");
		b2 = new JButton("Close");
		b1.addActionListener(this);
		b2.addActionListener(this);
		jp.add(l1);
		jp.add(t1);
		jp.add(l2);
		jp.add(p1);
		jp.add(b1);
		jp.add(b2);
		addWindowListener(new WinClose());
	}
	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getSource() == b1)
		{
			String u,p;
			u = t1.getText();
			p = p1.getText();
			if (u.equals("") == true)
			{
				JOptionPane.showMessageDialog(this,"Please enter Username","Error",JOptionPane.ERROR_MESSAGE);
			}
			else if (p.equals("") == true)
			{
				JOptionPane.showMessageDialog(this,"Please enter Password","Error",JOptionPane.ERROR_MESSAGE);
			}
			else if (u.equals("Admin") == true && p.equals("Admin") == true)
			{
				this.setVisible(false);
				new MainClient();
			}
			else
			{
				JOptionPane.showMessageDialog(this,"Invalid Username / Password","Error",JOptionPane.ERROR_MESSAGE);
				t1.setText("");
				p1.setText("");
			}
			
		}
		else if (ae.getSource() == b2)
		{
			System.exit(0);
		}
	}
	public class WinClose extends WindowAdapter
	{
		public void windowClosing(WindowEvent w)
		{
			System.exit(0);
		}
	}
	public static void main(String args[])
	{
		Login ob = new Login();
		ob.setSize(300,150);
		ob.setVisible(true);
	}
}