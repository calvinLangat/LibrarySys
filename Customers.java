package groupwork;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Customers extends JFrame {

	private JPanel contentPane;
	private JTable tableCustomers;
	
	Connection cn;
	Statement st;
	PreparedStatement prst;
	ResultSet rs;
	private JButton btnDisplayAll;
	private JTextField txtSearch;
	private JButton btnSearch;
	private Object searchItem;
	private JLabel label;
	private JButton btnDelete;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmDisplayAll;
	private JMenuItem mntmBack;
	private JMenuItem mntmExit;
	private JMenu mnEdit;
	private JMenuItem mntmDelete;
	private JMenu mnSearch;
	private JMenuItem mntmSearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Customers frame = new Customers();
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
	public Customers() {
		myconnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmDisplayAll = new JMenuItem("Display All");
		mntmDisplayAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayall();
			}
		});
		mnFile.add(mntmDisplayAll);
		
		mntmBack = new JMenuItem("Back");
		mntmBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			Lib_system_menu lsb = new Lib_system_menu();
			lsb.setVisible(true);
			dispose();
			}
		});
		mnFile.add(mntmBack);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		mntmDelete = new JMenuItem("Delete");
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				delete();
			}
		});
		mnEdit.add(mntmDelete);
		
		mnSearch = new JMenu("Search");
		menuBar.add(mnSearch);
		
		mntmSearch = new JMenuItem("Search");
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
		setSize(1281,618);
		
		JButton btnBack = new JButton("Back");								//Navigates to Lib_system_menu window
		btnBack.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 17));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Lib_system_menu ls = new Lib_system_menu();
				ls.setVisible(true);
				dispose();
			}
		});
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				delete();
				myconnection();
			}
		});
		btnDelete.setBounds(923, 130, 97, 25);
		contentPane.add(btnDelete);
		
		btnBack.setBounds(12, 433, 200, 79);
		contentPane.add(btnBack);
		
		
		JButton btnAddRecord = new JButton("Add Record");					//Navigates to AddCustomer window
		btnAddRecord.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 17));
		btnAddRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddCustomer adc = new AddCustomer();
				adc.setVisible(true);
				dispose();
			}
		});
		btnAddRecord.setBounds(1048, 433, 203, 79);
		contentPane.add(btnAddRecord);
		
		tableCustomers = new JTable();
		tableCustomers.setFont(new Font("Arial", Font.BOLD, 15));
		tableCustomers.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"First Name", "Last Name", "Gender", "ID Number", "Phone Number", "Addres"
			}
		));
		tableCustomers.getColumnModel().getColumn(0).setPreferredWidth(106);
		tableCustomers.getColumnModel().getColumn(1).setPreferredWidth(126);
		tableCustomers.getColumnModel().getColumn(2).setPreferredWidth(80);
		tableCustomers.getColumnModel().getColumn(3).setPreferredWidth(87);
		tableCustomers.getColumnModel().getColumn(4).setPreferredWidth(133);
		tableCustomers.setBounds(12, 26, 808, 231);
		contentPane.add(tableCustomers);
		
		btnDisplayAll = new JButton("Display All");
		btnDisplayAll.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 17));
		btnDisplayAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayall();
			}
		});
		btnDisplayAll.setBounds(333, 284, 182, 55);
		contentPane.add(btnDisplayAll);
		
		txtSearch = new JTextField();
		txtSearch.setBounds(922, 50, 243, 22);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search();
				myconnection();
			}
		});
		btnSearch.setBounds(1068, 130, 97, 25);
		contentPane.add(btnSearch);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\eclipseNeon\\eclipse\\Saves\\Groupwork\\src\\groupwork\\books6.jpg"));
		label.setBounds(0, 0, 1263, 571);
		contentPane.add(label);
		
		
		
	}
	
	protected void displayall()
	{
		DefaultTableModel model =(DefaultTableModel) tableCustomers.getModel();
	
		
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
	
	private void myconnection()
	{
		try
		{
			cn=DriverManager.getConnection("jdbc:mysql://localhost/lib_sys_db","root",null);	//Contains the link to database
			st=cn.createStatement();
			rs=st.executeQuery("select * from customers");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private void search()
	{
			searchItem=txtSearch.getText();
			DefaultTableModel model = (DefaultTableModel) tableCustomers.getModel();
			try
			{
				String sql="select * from customers where IDNUmber='"+searchItem+"' ";
				prst = cn.prepareStatement(sql);
				rs=prst.executeQuery();
				try 
				{
					if(model.getRowCount()>=0)
						model.setRowCount(0);
					while(rs.next())
						model.addRow(new Object[] {
								rs.getString(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4),
								rs.getString(5),
								rs.getString(6)
						});
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		
	}
	private void delete()
	{
		try
		{
		String sql="delete from books where SerialNo='"+searchItem+"'";
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
