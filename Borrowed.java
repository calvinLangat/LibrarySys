package groupwork;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Borrowed extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField txtSearch;
	JComboBox<Object> cbIDNumber = new JComboBox<Object>();					//Combo box to retrieve ID number from database 
	JComboBox<Object> cbSerialNo = new JComboBox<Object>();					//Combo box to retrieve Serial No from database
	JComboBox<String> cbSearch = new JComboBox<String>();					//Combo box to provide search selection
	Object choice1;															//Value of first combo box is stored here
	Object choice2;															//Value of second combo box is stored here
	Object tableChoice;														//Value of third combo box is stored here
	Object searchItem;
	
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
					Borrowed frame = new Borrowed();
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
	public Borrowed() {
		myconnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1287, 666);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmDisplayAll = new JMenuItem("Display All");
		mntmDisplayAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayall();
			}
		});
		mnFile.add(mntmDisplayAll);
		
		JMenuItem mntmBack = new JMenuItem("Back");
		mntmBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Lib_system_menu lsm= new Lib_system_menu();
				lsm.setVisible(true);
				dispose();
				
			}
		});
		mnFile.add(mntmBack);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmDelete = new JMenuItem("Delete");
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				delete();
			}
		});
		mnEdit.add(mntmDelete);
		
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
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Arial", Font.BOLD, 17));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Lib_system_menu lsm= new Lib_system_menu();
				lsm.setVisible(true);
				dispose();
			}
		});
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
				myconnection();
			}
		});
		btnDelete.setFont(new Font("Arial", Font.BOLD, 14));
		btnDelete.setBounds(854, 189, 146, 34);
		contentPane.add(btnDelete);
		btnBack.setBounds(12, 499, 216, 79);
		contentPane.add(btnBack);
		
		JButton btnAddRecord = new JButton("Add Record");
		btnAddRecord.setFont(new Font("Arial", Font.BOLD, 17));
		btnAddRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddBorrowed ad= new AddBorrowed();
				ad.setVisible(true);
				dispose();
			}
		});
		btnAddRecord.setBounds(1024, 499, 216, 79);
		contentPane.add(btnAddRecord);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column"
			}
		));
		table.setBounds(47, 55, 670, 288);
		contentPane.add(table);
		
		txtSearch = new JTextField();
		txtSearch.setBounds(854, 75, 312, 29);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);
		cbSearch.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 15));
		cbSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableChoice=cbSearch.getSelectedItem();
			}
		});
		

		cbSearch.setBounds(1039, 130, 127, 22);
		contentPane.add(cbSearch);
		cbSearch.addItem("customers");												//Add options to the combo box
		cbSearch.addItem("books");
		cbSearch.addItem("Borrowed");
		
		JButton btnSearch = new JButton("search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search();
				myconnection();
			}
		});
		btnSearch.setFont(new Font("Arial", Font.BOLD, 14));
		btnSearch.setBounds(1012, 189, 154, 34);
		contentPane.add(btnSearch);
		
		JButton btnDisplayAll = new JButton("Display All");
		btnDisplayAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayall();
				myconnection();
			}
		});
		btnDisplayAll.setFont(new Font("Arial", Font.BOLD, 14));
		btnDisplayAll.setBounds(287, 370, 138, 34);
		contentPane.add(btnDisplayAll);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\eclipseNeon\\eclipse\\Saves\\Groupwork\\src\\groupwork\\book5.jpg"));
		label.setBounds(0, 0, 1269, 619);
		contentPane.add(label);
		
		
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
	public void clearTable()																//Function to clear table
	{
		DefaultTableModel model =(DefaultTableModel) table.getModel();
				if(model.getRowCount()>0)
				model.setRowCount(0);
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
	private void delete()
	{
		if(tableChoice=="Borrowed")
			try
			{
				String sql="delete from borrow where BorrowID='"+searchItem+"'";
				prst=cn.prepareStatement(sql);
				prst.executeUpdate();
				JOptionPane.showMessageDialog(null,"Record successfully deleted");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}
}
