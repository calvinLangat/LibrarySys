package groupwork;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;

import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ImageIcon;


public class Lib_system_menu extends JFrame{
	Connection cn;
	Statement st;
	ResultSet rs;
	
	public Lib_system_menu() {
		getContentPane().setLayout(null);
		setSize(967,494);
		setLocation(500,300);
		
		JLabel lblAppTitle = new JLabel("Welcome to Crux Library System");
		lblAppTitle.setFont(new Font("Bahnschrift", Font.BOLD, 29));
		lblAppTitle.setBounds(235, 13, 482, 56);
		getContentPane().add(lblAppTitle);
		
		JButton btnCustomer = new JButton("Customer Table");					//Navigates to Customers table
		btnCustomer.setFont(new Font("Arial", Font.BOLD, 15));
		btnCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Customers cm = new Customers();
				cm.setVisible(true);
				dispose();
						
			}
		});
		btnCustomer.setBounds(384, 120, 176, 69);
		getContentPane().add(btnCustomer);
		
		JButton btnBooks = new JButton("Books Table");							//Navigates to Books table
		btnBooks.setFont(new Font("Arial", Font.BOLD, 15));
		btnBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Books bk = new Books();
				bk.setVisible(true);
				dispose();
			}
		});
		btnBooks.setBounds(384, 202, 176, 56);
		getContentPane().add(btnBooks);
		
		JButton btnBorrowed = new JButton("Borrowed Books Table");				//Navigates to Borrowed books table
		btnBorrowed.setFont(new Font("Arial", Font.BOLD, 15));
		btnBorrowed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Borrowed bw =new Borrowed();
				bw.setVisible(true);
				dispose();
			}
		});
		btnBorrowed.setBounds(345, 286, 261, 44);
		getContentPane().add(btnBorrowed);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.setFont(new Font("Arial", Font.BOLD, 15));
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login lg = new Login();
				lg.setVisible(true);
				dispose();
			}
		});
		btnLogOut.setBounds(384, 378, 176, 56);
		getContentPane().add(btnLogOut);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\eclipseNeon\\eclipse\\Saves\\Groupwork\\src\\groupwork\\book.jpg"));
		lblNewLabel.setBounds(0, -59, 964, 578);
		getContentPane().add(lblNewLabel);
	}
	
	
	private void myconnection()
	{
		try
		{
			cn=DriverManager.getConnection("jdbc:mysql://localhost/lib_sys_db","root",null);
			st=cn.createStatement();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
