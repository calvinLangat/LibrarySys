package groupwork;

import java.awt.BorderLayout;
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
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Books extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField txtSearch;
	Connection cn;
	Statement st;
	PreparedStatement prst;
	ResultSet rs;
	Object SearchItem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Books frame = new Books();
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
	public Books() {
		myconnection();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1219, 678);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmDisplay = new JMenuItem("Display All");
		mntmDisplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DisplayAll();
			}
		});
		mnFile.add(mntmDisplay);
		
		JMenuItem mntmBack = new JMenuItem("Back");
		mntmBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Lib_system_menu lsm = new Lib_system_menu();
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
		btnBack.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Lib_system_menu lsm = new Lib_system_menu();
				lsm.setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(36, 519, 220, 68);
		contentPane.add(btnBack);
		
		JButton btnAddBook = new JButton("Add Book");
		btnAddBook.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		btnAddBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddBook ab = new AddBook();
				ab.setVisible(true);
				dispose();
			}
		});
		btnAddBook.setBounds(937, 519, 166, 68);
		contentPane.add(btnAddBook);
		
		table = new JTable();
		table.setFont(new Font("Arial", Font.ITALIC, 14));
		table.setBackground(UIManager.getColor("Button.light"));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Title", "Author", "New column", "Date Published"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(171);
		table.getColumnModel().getColumn(1).setPreferredWidth(174);
		table.getColumnModel().getColumn(2).setPreferredWidth(139);
		table.getColumnModel().getColumn(3).setPreferredWidth(156);
		table.setBounds(72, 75, 806, 158);
		contentPane.add(table);
		
		JButton btnDisplayAll = new JButton("Display All");
		btnDisplayAll.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		btnDisplayAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DisplayAll();
				myconnection();
			}
		});
		btnDisplayAll.setBounds(419, 268, 144, 49);
		contentPane.add(btnDisplayAll);
		
		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		txtSearch.setBounds(952, 93, 219, 22);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				search();
				myconnection();
			}
		});
		btnSearch.setBounds(955, 149, 107, 35);
		contentPane.add(btnSearch);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				delete();
				myconnection();
			}
		});
		btnDelete.setBounds(1064, 149, 107, 35);
		contentPane.add(btnDelete);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\eclipseNeon\\eclipse\\Saves\\Groupwork\\src\\groupwork\\books2.jpg"));
		label.setBounds(0, 0, 1201, 631);
		contentPane.add(label);
	}
	
	private void DisplayAll()
	{
		DefaultTableModel model =(DefaultTableModel) table.getModel();
		
		try 
		{
			String sql = "Select * from books";
			prst=cn.prepareStatement(sql);
			rs=prst.executeQuery();
			try
			{
				if (model.getRowCount()>0)
					model.setRowCount(0);
				while(rs.next())
					model.addRow(new Object []
							{
								rs.getString(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4)
								
							});
						
				
			}
			catch(Exception ie)
			{
				ie.printStackTrace();
			}
		}
		catch(Exception ie)
		{
			ie.printStackTrace();
		}
	}
	
	private void myconnection()
	{
		try
		{
			cn=DriverManager.getConnection("jdbc:mysql://localhost/lib_sys_db","root",null);
		}
		catch(Exception ie)
		{
			ie.printStackTrace();
		}
	}
	private void search()
	{
		DefaultTableModel model =(DefaultTableModel) table.getModel();
		SearchItem=txtSearch.getText();
		try 
		{
			String sql="select * from books where SerialNo='"+SearchItem+"'";
			prst=cn.prepareStatement(sql);												//Prepared statement
			rs=prst.executeQuery();
			
			
			try{
				if(model.getRowCount()>=0)												//Checks if there are existing rows
					model.setRowCount(0);												//Clears any present rows
				
				while(rs.next())
					model.addRow(new Object []
							{
								rs.getString(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4)
								
							});
			}
			catch(Exception ie){
				ie.printStackTrace();
			}
		}
		catch(Exception ie)
		{
			ie.printStackTrace();
		}
	}
	private void delete()
	{
		try
		{
		String sql="delete from books where SerialNo='"+SearchItem+"'";
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
