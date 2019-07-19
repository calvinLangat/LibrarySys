package groupwork;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Point;
import java.awt.Font;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JLabel lblPassword;
	private JTextField txtPassword;
	private JButton btnLogin;
	private JLabel lblNewLabel;
	private Object username;
	private Object password;

	
	Connection cn;
	Statement st;
	PreparedStatement prst;
	ResultSet rs;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	

	/**
	 * Create the frame.
	 */
	public Login() {
		myconnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 536, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(500,300);
		
		
		
		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setFont(new Font("Arial", Font.BOLD, 16));
		lblUsername.setBounds(47, 39, 135, 38);
		contentPane.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
		txtUsername.setBounds(206, 42, 164, 34);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		lblPassword = new JLabel("Password :");
		lblPassword.setFont(new Font("Arial", Font.BOLD, 16));
		lblPassword.setBounds(47, 111, 135, 26);
		contentPane.add(lblPassword);
		
		txtPassword = new JTextField();
		txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPassword.setLocation(new Point(500, 300));
		txtPassword.setBounds(206, 103, 164, 34);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);
		
		
		
		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
		btnLogin.setBounds(67, 193, 123, 47);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				passwordGet();
				
				String uname=txtUsername.getText();
				String pwd=txtPassword.getText();
				
				if (uname.equals(username) && pwd.equals(password)){
					clear();
					JOptionPane.showMessageDialog(null,"Successful login");
					Lib_system_menu lsm = new Lib_system_menu();
					lsm.setVisible(true);
					dispose();
				}
				
				else
					JOptionPane.showMessageDialog(null,"Unsuccessful login");
				clear();
			}
		});
		contentPane.add(btnLogin);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Arial", Font.BOLD, 16));
		btnExit.setBounds(247, 193, 123, 47);
		contentPane.add(btnExit);
		
		lblNewLabel = new JLabel();
		lblNewLabel.setIcon(new ImageIcon("C:\\eclipseNeon\\eclipse\\Saves\\Groupwork\\src\\groupwork\\menu1.jpg"));
		lblNewLabel.setBounds(0, 0, 525, 350);
		contentPane.add(lblNewLabel);
	}
	
	private void clear()
	{
		txtPassword.setText(null);
		txtUsername.setText(null);
	}
	
	private void myconnection()
	{
		try {
			cn=DriverManager.getConnection("jdbc:mysql://localhost/lib_sys_db","root",null);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void passwordGet()
	{
		String sql = "select * from users_passwords";
		
		try {
			prst=cn.prepareStatement(sql);
			rs=prst.executeQuery();
			try {
				while(rs.next()) {
				username=rs.getObject(1);
				password=rs.getObject(2);
				}
				}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
