package groupwork;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class AddBorrowed extends JFrame {

	private JPanel contentPane;
	private JTextField txtBorrowid;
	private JTextField txtDateborrowed;
	private JTextField txtDatedue;
	JComboBox<Object> cbIDNumber = new JComboBox<Object>();					//Combo box to retrieve ID number from database 
	JComboBox<Object> cbSerialNo = new JComboBox<Object>();					//Combo box to retrieve Serial No from database
	JComboBox<String> cbSearch = new JComboBox<String>();					//Combo box to provide search selection
	Object choice1;															//Value of first combo box is stored here
	Object choice2;															//Value of second combo box is stored here
	Object tableChoice;														//Value of third combo box is stored here
	Object searchItem;														//Input from search text box is stored here
	
	Connection cn;
	Statement st;
	ResultSet rs;
	PreparedStatement prst;
	private JTextField txtFirstname;
	private JTextField txtTitle;
	private JTextField txtSearch;
	private JTable table;
	private JTable table_1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddBorrowed frame = new AddBorrowed();
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	private void combobox1()															//Establish link between combo box and Database
	{
		String sql ="select * from customers";
		try
		{
		prst=cn.prepareStatement(sql);
		rs=prst.executeQuery();
		while(rs.next())
		{
			int ID = rs.getInt("IDnumber");
			cbIDNumber.addItem(ID);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void combobox2()														//Establish link between combo box and Database
	{
		String sql="select * from books";
		try
		{
			prst=cn.prepareStatement(sql);
			rs=prst.executeQuery();
			while(rs.next())
			{
				int ID = rs.getInt("SerialNo");
				cbSerialNo.addItem(ID);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	

	public AddBorrowed() {
		myconnection();															//Start connection
		combobox1();															//Call function for the first combo box
		cbIDNumber.setFont(new Font("Arial", Font.PLAIN, 15));
		
		
		cbIDNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				choice1= cbIDNumber.getSelectedItem();
				retrieveCustomer();
			
			}
			
		});
		
		cbIDNumber.setSelectedItem(null);
		combobox2();															//Call function for the second combo box
		cbSerialNo.setFont(new Font("Arial", Font.PLAIN, 15));
		cbSerialNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choice2= cbSerialNo.getSelectedItem();
				retrieveBook();
			}
		});
		cbSerialNo.setSelectedItem(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmShowAll = new JMenuItem("Show All");
		mntmShowAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayall();
			}
		});
		mnFile.add(mntmShowAll);
		
		JMenuItem mntmClear = new JMenuItem("Clear");
		mntmClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		mnFile.add(mntmClear);
		
		JMenuItem mntmBack = new JMenuItem("Back");
		mntmBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Books b = new Books();
				b.setVisible(true);
				dispose();
			}
		});
		mnFile.add(mntmBack);
		
		JMenuItem mntmPrint = new JMenuItem("Print");
		mntmPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					printing();
				}
				catch(PrinterException ie)
				{
					ie.printStackTrace();
				
			}
			}
		});
		mnFile.add(mntmPrint);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmAdd = new JMenuItem("Add");
		mntmAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insert();
			}
		});
		mnEdit.add(mntmAdd);
		
		JMenu mnSearch = new JMenu("Search");
		menuBar.add(mnSearch);
		
		JMenuItem mntmSearch = new JMenuItem("Search");
		mntmSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				search();
			}
		});
		mnSearch.add(mntmSearch);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setSize(1307,715);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					printing();
				}
				catch(PrinterException ie)
				{
					ie.printStackTrace();
				}
			}
		});
		btnPrint.setBounds(1092, 217, 97, 25);
		contentPane.add(btnPrint);
		
		JLabel lblBorrowId = new JLabel("Borrow ID :");
		lblBorrowId.setForeground(new Color(0, 0, 128));
		lblBorrowId.setBackground(new Color(255, 127, 80));
		lblBorrowId.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 19));
		lblBorrowId.setBounds(85, 125, 150, 16);
		contentPane.add(lblBorrowId);
		
		JLabel lblIdNumber = new JLabel("ID Number :");
		lblIdNumber.setForeground(new Color(0, 0, 128));
		lblIdNumber.setBackground(new Color(255, 127, 80));
		lblIdNumber.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 19));
		lblIdNumber.setBounds(85, 154, 150, 16);
		contentPane.add(lblIdNumber);
		
		JLabel lblSeriaNo = new JLabel("Serial No :");
		lblSeriaNo.setForeground(new Color(0, 0, 128));
		lblSeriaNo.setBackground(new Color(255, 127, 80));
		lblSeriaNo.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 19));
		lblSeriaNo.setBounds(85, 224, 150, 16);
		contentPane.add(lblSeriaNo);
		
		JLabel lblDateBorrowed = new JLabel("Date Borrowed :");
		lblDateBorrowed.setForeground(new Color(0, 0, 128));
		lblDateBorrowed.setBackground(new Color(255, 127, 80));
		lblDateBorrowed.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 19));
		lblDateBorrowed.setBounds(85, 285, 150, 16);
		contentPane.add(lblDateBorrowed);
		
		JLabel lblDateDue = new JLabel("Date Due :");
		lblDateDue.setForeground(new Color(0, 0, 128));
		lblDateDue.setBackground(new Color(255, 127, 80));
		lblDateDue.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 19));
		lblDateDue.setBounds(85, 314, 150, 16);
		contentPane.add(lblDateDue);
		
		txtBorrowid = new JTextField();
		txtBorrowid.setFont(new Font("Arial", Font.PLAIN, 15));
		txtBorrowid.setBounds(276, 122, 227, 22);
		contentPane.add(txtBorrowid);
		txtBorrowid.setColumns(10);
		
		txtDateborrowed = new JTextField();
		txtDateborrowed.setFont(new Font("Arial", Font.PLAIN, 15));
		txtDateborrowed.setBounds(276, 279, 227, 22);
		contentPane.add(txtDateborrowed);
		txtDateborrowed.setColumns(10);
		
		txtDatedue = new JTextField();
		txtDatedue.setFont(new Font("Arial", Font.PLAIN, 15));
		txtDatedue.setBounds(276, 308, 227, 22);
		contentPane.add(txtDatedue);
		txtDatedue.setColumns(10);
		
		
		
		
		cbIDNumber.setEditable(true);
		cbIDNumber.setBounds(276, 151, 227, 22);
		contentPane.add(cbIDNumber);
		
		
		cbSerialNo.setEditable(true);
		cbSerialNo.setBounds(276, 218, 227, 22);
		contentPane.add(cbSerialNo);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Arial", Font.BOLD, 17));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				insert();													//Call function to insert data to DB
				
			}
		});
		btnAdd.setBounds(114, 394, 157, 47);
		contentPane.add(btnAdd);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Arial", Font.BOLD, 17));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();													//Call function to clear fields
				clearTable();												//Call function to clear table
			}
		});
		btnClear.setBounds(364, 394, 150, 47);
		contentPane.add(btnClear);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Arial", Font.BOLD, 17));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Borrowed br= new Borrowed();
				br.setVisible(true);										//Open Borrowed JFrame
				dispose();													//Close this window
			}
		});
		btnBack.setBounds(497, 583, 194, 47);
		contentPane.add(btnBack);
		
		JLabel lblFirstName = new JLabel("First Name :");
		lblFirstName.setForeground(new Color(0, 0, 128));
		lblFirstName.setBackground(new Color(255, 127, 80));
		lblFirstName.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 19));
		lblFirstName.setBounds(85, 195, 150, 16);
		contentPane.add(lblFirstName);
		
		JLabel lblTitle = new JLabel("Title :");
		lblTitle.setForeground(new Color(0, 0, 128));
		lblTitle.setBackground(new Color(255, 127, 80));
		lblTitle.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 19));
		lblTitle.setBounds(85, 256, 150, 16);
		contentPane.add(lblTitle);
		
		txtFirstname = new JTextField();
		txtFirstname.setFont(new Font("Arial", Font.PLAIN, 15));
		txtFirstname.setEditable(false);
		txtFirstname.setBounds(276, 186, 227, 22);
		contentPane.add(txtFirstname);
		txtFirstname.setColumns(10);
		
		txtTitle = new JTextField();
		txtTitle.setFont(new Font("Arial", Font.PLAIN, 15));
		txtTitle.setEditable(false);
		txtTitle.setBounds(276, 253, 227, 22);
		contentPane.add(txtTitle);
		txtTitle.setColumns(10);
		
		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Arial", Font.PLAIN, 15));
		txtSearch.setBounds(735, 155, 150, 22);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);
		cbSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableChoice=cbSearch.getSelectedItem();								//Pass Combo box value to global variable
			}
		});
		
		
		cbSearch.setFont(new Font("Arial", Font.BOLD, 16));
		cbSearch.setBounds(913, 155, 134, 22);
		contentPane.add(cbSearch);
		cbSearch.addItem("customers");												//Add options to the combo box
		cbSearch.addItem("books");
		cbSearch.addItem("Borrowed");
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				search();															//Call search function
				myconnection();														//Re-establish connection
				
				
			}
		});
		
		
		btnSearch.setFont(new Font("Arial", Font.BOLD, 15));
		btnSearch.setBounds(1092, 154, 97, 25);
		contentPane.add(btnSearch);
		
		table = new JTable();
		table.setFont(new Font("Verdana", Font.PLAIN, 14));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(195);
		table.getColumnModel().getColumn(1).setPreferredWidth(114);
		table.getColumnModel().getColumn(2).setPreferredWidth(99);
		table.getColumnModel().getColumn(3).setPreferredWidth(104);
		table.getColumnModel().getColumn(4).setPreferredWidth(107);
		table.getColumnModel().getColumn(5).setPreferredWidth(110);
		table.setBounds(635, 258, 626, 267);
		contentPane.add(table);
		
		JButton btnShowAll = new JButton("Show All");
		btnShowAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayall();													//Call display all function
				myconnection();													//Re-establish connection
			}
		});
		btnShowAll.setFont(new Font("Arial", Font.BOLD, 15));
		btnShowAll.setBounds(915, 206, 97, 25);
		contentPane.add(btnShowAll);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\eclipseNeon\\eclipse\\Saves\\Groupwork\\src\\groupwork\\borrowbackground.jpg"));
		label.setBounds(0, 0, 1289, 655);
		contentPane.add(label);
		
		table_1 = new JTable();
		table_1.setBounds(1260, 227, 1, 1);
		contentPane.add(table_1);
	}
	
	private void myconnection()													//Function to connect to DB
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
	
	private void clear()														//Clear fields function
	{
		txtBorrowid.setText(null);
		cbIDNumber.setSelectedItem(null);
		cbSerialNo.setSelectedItem(null);
		txtDateborrowed.setText(null);
		txtDatedue.setText(null);
	}
	private void insert()														//Insert functions
	{
		String sql="insert into borrow values(?,?,?,?,?)";						//String for prepared statement
		try
		{
			prst=cn.prepareStatement(sql);										//Prepared statement
			
			prst.setObject(1,txtBorrowid.getText());
			prst.setObject(2, choice1);
			prst.setObject(3, choice2);
			prst.setObject(4, txtDateborrowed.getText());
			prst.setObject(5, txtDatedue.getText());
			prst.executeUpdate();
			JOptionPane.showMessageDialog(null,"Record successfully added");
			
			myconnection();														//Re-establish connection
			clear();															//Call clear function
			
			
		}
		catch(Exception ie)
		{
			ie.printStackTrace();
		}
	}
	
	private void retrieveCustomer()												//Function to display name associated with ID Number
	{
		try
		{
			String sql="select * from customers where IDNumber='"+choice1+"' ";
			prst=cn.prepareStatement(sql);
			rs=prst.executeQuery();
			if(rs.next())
			{
			String name=rs.getString(1);
			
			txtFirstname.setText(name);
			}
			
		}
		catch(Exception ie)
		{
			ie.printStackTrace();
		}
	}
	private void retrieveBook()													//Function to display Title associated with Serial number
	{
		try
		{
			String sql="select * from books where SerialNo='"+choice2+"' ";
			prst=cn.prepareStatement(sql);
			rs=prst.executeQuery();
			if(rs.next())
			{
			String title=rs.getString(1);
			
			txtTitle.setText(title);
			}
			
		}
		catch(Exception ie)
		{
			ie.printStackTrace();
		}
	}
	private void search()														//Search Function
	{
		searchItem = txtSearch.getText();
		if(tableChoice=="customers")											//If selected option in combo box is customers
		{
			try
			{
				String sql="select * from customers where IDNumber='"+searchItem+"'";
				prst=cn.prepareStatement(sql);													//Prepared statement
				rs=prst.executeQuery();
				
				
				DefaultTableModel model =(DefaultTableModel) table.getModel();
				
				try{
					if(model.getRowCount()>0)													//Checks if there are existing rows
						model.setRowCount(0);													//Clears any present rows
					
					while(rs.next())
					{
						
							model.addRow(new Object[]
								{
										rs.getString(1),
										rs.getString(2),
										rs.getString(3),
										rs.getString(4),
										rs.getString(5),
										rs.getString(6)
								});
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else if(tableChoice=="books")															//If selected item in combo box id books
		{
			try
			{
				String sql="select * from books where SerialNo='"+searchItem+"'";
				prst=cn.prepareStatement(sql);												//Prepared statement
				rs=prst.executeQuery();
				
				DefaultTableModel model =(DefaultTableModel) table.getModel();
				
				try{
					if(model.getRowCount()>0)												//Checks if there are existing rows
						model.setRowCount(0);												//Clears any present rows
					
					while(rs.next())
					{
						
							model.addRow(new Object[]
								{
										rs.getString(1),
										rs.getString(2),
										rs.getString(3),
										rs.getString(4),
										
								});
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else if(tableChoice=="Borrowed")													//If selected item in combo box is Borrowed
		{
			try 
			{
				String sql="select * from borrow where BorrowID='"+searchItem+"'";
				prst=cn.prepareStatement(sql);												//Prepared statement
				rs=prst.executeQuery();
				
				DefaultTableModel model =(DefaultTableModel) table.getModel();
				
				try{
					if(model.getRowCount()>0)												//Checks if there are existing rows
						model.setRowCount(0);												//Clears any present rows
					
					while(rs.next())
					{
						
							model.addRow(new Object[]
								{
										rs.getString(1),
										rs.getString(2),
										rs.getString(3),
										rs.getString(4),
										rs.getString(5),
										
								});
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	protected void displayall()															//Function to display all rows in DB
	{
		

		DefaultTableModel model =(DefaultTableModel) table.getModel();
		
		try{
			if(tableChoice=="customers")												//Checks if combo box value == customers
			{
				try
				{
					String sql="select * from customers";
					prst=cn.prepareStatement(sql);
					rs=prst.executeQuery();
					
										
					try{
						if(model.getRowCount()>0)
							model.setRowCount(0);
						
						while(rs.next())
						{
							
								model.addRow(new Object[]
									{
											rs.getString(1),
											rs.getString(2),
											rs.getString(3),
											rs.getString(4),
											rs.getString(5),
											rs.getString(6)
									});
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else if(tableChoice=="books")												//Checks if combo box value == books
			{
				try
				{
					String sql="select * from books";
					prst=cn.prepareStatement(sql);
					rs=prst.executeQuery();
					
					
					try{
						if(model.getRowCount()>0)
							model.setRowCount(0);
						
						while(rs.next())
						{
							
								model.addRow(new Object[]
									{
											rs.getString(1),
											rs.getString(2),
											rs.getString(3),
											rs.getString(4),
											
									});
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else if(tableChoice=="Borrowed")										//Checks if combo box value == Borrowed
			{
				try 
				{
					String sql="select * from borrow";
					prst=cn.prepareStatement(sql);												//Prepared statement
					rs=prst.executeQuery();
					
										
					try{
						if(model.getRowCount()>0)												//Checks if there are existing rows
							model.setRowCount(0);												//Clears any present rows
						
						while(rs.next())
						{
							
								model.addRow(new Object[]
									{
											rs.getString(1),
											rs.getString(2),
											rs.getString(3),
											rs.getString(4),
											rs.getString(5),
											
									});
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	private void clearTable()																//Function to clear table
	{
		DefaultTableModel model =(DefaultTableModel) table.getModel();
				if(model.getRowCount()>0)
				model.setRowCount(0);
	}
	
	private void printing() throws PrinterException
	{
		table_1.print(JTable.PrintMode.FIT_WIDTH);
	}
}
