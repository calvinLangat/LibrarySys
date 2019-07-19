package groupwork;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class AddCustomer extends JFrame {

	private JPanel contentPane;
	private JTextField txtFirstname;
	private JLabel lblLastname;
	private JTextField txtLastname;
	private JLabel lblGender;
	private JLabel lblIdnumber;
	private JTextField txtIdnumber;
	private JLabel lblPhonenumber;
	private JTextField txtPhonenumber;
	private JLabel lblAddress;
	private JTextField txtAddress;
	private JButton btnAdd;
	
	Connection cn;
	Statement st;
	ResultSet rs;
	PreparedStatement prst;
	
	private JButton btnClear;
	private JButton btnBack;
	private JLabel lbloutput;
	private JLabel label;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmBack;
	private JMenuItem mntmClear;
	private JMenu mnEdit;
	private JMenuItem mntmAdd;
	private JMenuItem mntmExit;
	JRadioButton rdbtnMale = new JRadioButton("Male");
	JRadioButton rdbtnFemale = new JRadioButton("Female");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddCustomer frame = new AddCustomer();
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
	public AddCustomer() {
		
		myconnection();														//Establish Connection
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmBack = new JMenuItem("Back");
		mntmBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Customers cs = new Customers();
				cs.setVisible(true);
				dispose();
			}
		});
		mnFile.add(mntmBack);
		
		mntmClear = new JMenuItem("Clear");
		mntmClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		mnFile.add(mntmClear);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		mntmAdd = new JMenuItem("Add");
		mntmAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				add();
			}
		});
		mnEdit.add(mntmAdd);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setSize(1320,627);													//Window size
		
		JLabel lblFirstname = new JLabel("First Name :");
		lblFirstname.setFont(new Font("Arial", Font.PLAIN, 15));
		lblFirstname.setBounds(96, 45, 194, 16);
		contentPane.add(lblFirstname);
		
		txtFirstname = new JTextField();
		txtFirstname.setFont(new Font("Arial", Font.PLAIN, 15));
		txtFirstname.setBounds(285, 51, 325, 22);
		contentPane.add(txtFirstname);
		txtFirstname.setColumns(10);
		
		lblLastname = new JLabel("Last Name :");
		lblLastname.setFont(new Font("Arial", Font.PLAIN, 15));
		lblLastname.setBounds(96, 76, 194, 16);
		contentPane.add(lblLastname);
		
		txtLastname = new JTextField();
		txtLastname.setFont(new Font("Arial", Font.PLAIN, 15));
		txtLastname.setBounds(285, 82, 325, 22);
		contentPane.add(txtLastname);
		txtLastname.setColumns(10);
		
		lblGender = new JLabel("Gender :");
		lblGender.setFont(new Font("Arial", Font.PLAIN, 15));
		lblGender.setBounds(96, 111, 194, 16);
		contentPane.add(lblGender);
		
	
		//JRadioButton rdbtnMale = new JRadioButton("Male");			//Creating the radio buttons
		rdbtnMale.setBackground(new Color(255, 228, 196));
		rdbtnMale.setFont(new Font("Arial", Font.PLAIN, 15));
		rdbtnMale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
			}
		});
		rdbtnMale.setBounds(274, 116, 336, 25);
		rdbtnMale.setSelected(true);
		contentPane.add(rdbtnMale);
		
		
		//JRadioButton rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setBackground(new Color(255, 228, 196));
		rdbtnFemale.setFont(new Font("Arial", Font.PLAIN, 15));
		rdbtnFemale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		rdbtnFemale.setBounds(274, 146, 336, 25);
		contentPane.add(rdbtnFemale);
		
		ButtonGroup group = new ButtonGroup();					//Grouping the radio buttons to prevent selecting more than one at a time.
		group.add(rdbtnFemale);
		group.add(rdbtnMale);
		
		
		
		lblIdnumber = new JLabel("ID Number :");
		lblIdnumber.setFont(new Font("Arial", Font.PLAIN, 15));
		lblIdnumber.setBounds(96, 176, 194, 16);
		contentPane.add(lblIdnumber);
		
		txtIdnumber = new JTextField();
		txtIdnumber.setFont(new Font("Arial", Font.PLAIN, 15));
		txtIdnumber.setBounds(285, 180, 325, 22);
		contentPane.add(txtIdnumber);
		txtIdnumber.setColumns(10);
		
		lblPhonenumber = new JLabel("Phone Number :");
		lblPhonenumber.setFont(new Font("Arial", Font.PLAIN, 15));
		lblPhonenumber.setBounds(96, 221, 194, 16);
		contentPane.add(lblPhonenumber);
		
		txtPhonenumber = new JTextField();
		txtPhonenumber.setFont(new Font("Arial", Font.PLAIN, 15));
		txtPhonenumber.setBounds(285, 227, 325, 22);
		contentPane.add(txtPhonenumber);
		txtPhonenumber.setColumns(10);
		
		lblAddress = new JLabel("Address :");
		lblAddress.setFont(new Font("Arial", Font.PLAIN, 15));
		lblAddress.setBounds(96, 270, 194, 16);
		contentPane.add(lblAddress);
		
		txtAddress = new JTextField();
		txtAddress.setFont(new Font("Arial", Font.PLAIN, 15));
		txtAddress.setBounds(285, 276, 325, 22);
		contentPane.add(txtAddress);
		txtAddress.setColumns(10);
		
		btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 17));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ie) {
				add();
				
			}
		});
		btnAdd.setBounds(483, 322, 127, 36);
		contentPane.add(btnAdd);
		
		btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 17));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int YesOrNo = JOptionPane.showConfirmDialog(null,"Do you want to clear all fields?","Confirm", JOptionPane.YES_NO_OPTION);		//Confirmation Box
				if (YesOrNo==0)
				{
						clear();
				}
				else
				{
					
				}
					
			}
		});
		btnClear.setBounds(719, 166, 148, 36);
		contentPane.add(btnClear);
		
		btnBack = new JButton("Back");
		btnBack.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 17));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Customers cs = new Customers();
				cs.setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(35, 460, 201, 62);
		contentPane.add(btnBack);
		
		lbloutput = new JLabel();
		lbloutput.setBounds(12, 419, 56, 16);
		contentPane.add(lbloutput);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\eclipseNeon\\eclipse\\Saves\\Groupwork\\src\\groupwork\\book7.jpg"));
		label.setBounds(0, 0, 1302, 580);
		contentPane.add(label);
	
	
	}
	
	private void myconnection()
	{
		try
		{
			cn=DriverManager.getConnection("jdbc:mysql://localhost/lib_sys_db","root",null);	//Contains the link to database
			st=cn.createStatement();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void clear()																		//Clears fields
	{
		txtFirstname.setText(null);
		txtLastname.setText(null);
		txtIdnumber.setText(null);
		txtPhonenumber.setText(null);
		txtAddress.setText(null);
	}
	private void add()
	{
		int ID;
		ID=Integer.parseInt(txtIdnumber.getText());
		String gender = null;											//Where the gender value will be stored
		if(rdbtnMale.isSelected())										//If Male radio button is selected it is stored in 'Gender'
			gender="Male";										
		if(rdbtnFemale.isSelected())									//If Female radio button is selected it is stored in 'Gender'
			gender="Female";
		
		String sql = "insert into customers values(?,?,?,?,?,?)";
		
		try{
			
			
			prst=cn.prepareStatement(sql);
			
			prst.setString(1, txtFirstname.getText());
			prst.setString(2, txtLastname.getText());
			prst.setString(3, gender);
			prst.setInt(4, ID);
			prst.setString(5,txtPhonenumber.getText());
			prst.setString(6, txtAddress.getText());
			
			prst.executeUpdate();
			JOptionPane.showMessageDialog(null,"Customer successfully added");
			
			
			myconnection();												//Restart connection														
			clear();													//All fields are cleared.
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
