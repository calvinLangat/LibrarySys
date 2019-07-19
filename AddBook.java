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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class AddBook extends JFrame {

	private JPanel contentPane;
	private JTextField txtTitle;
	private JTextField txtAuthor;
	private JTextField txtPublishDate;
	private JTextField txtSerialNo;
	
	Connection cn;
	Statement st;
	ResultSet rs;
	PreparedStatement prst;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddBook frame = new AddBook();
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
	public AddBook() {
		myconnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmBack = new JMenuItem("Back");
		mntmBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Books bk = new Books();
				bk.setVisible(true);
				dispose();
				
			}
		});
		mnFile.add(mntmBack);
		
		JMenuItem mntmClear = new JMenuItem("Clear");
		mntmClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
		});
		mnFile.add(mntmClear);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmAddBook = new JMenuItem("Add Book");
		mntmAddBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insert();
			}
		});
		mnEdit.add(mntmAddBook);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setSize(1272,674);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Title :");
		lblTitle.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 17));
		lblTitle.setBounds(107, 80, 136, 16);
		contentPane.add(lblTitle);
		
		JLabel lblAuthor = new JLabel("Author :");
		lblAuthor.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 17));
		lblAuthor.setBounds(107, 128, 136, 16);
		contentPane.add(lblAuthor);
		
		JLabel lblPublishDate = new JLabel("Publish Date :");
		lblPublishDate.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 17));
		lblPublishDate.setBounds(107, 230, 136, 16);
		contentPane.add(lblPublishDate);
		
		JLabel lblSerialNumber = new JLabel("Serial No :");
		lblSerialNumber.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 17));
		lblSerialNumber.setBounds(107, 177, 136, 16);
		contentPane.add(lblSerialNumber);
		
		txtTitle = new JTextField();
		txtTitle.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTitle.setBounds(367, 80, 225, 22);
		contentPane.add(txtTitle);
		txtTitle.setColumns(10);
		
		txtAuthor = new JTextField();
		txtAuthor.setFont(new Font("Arial", Font.PLAIN, 14));
		txtAuthor.setBounds(367, 128, 225, 22);
		contentPane.add(txtAuthor);
		txtAuthor.setColumns(10);
		
		txtPublishDate = new JTextField();
		txtPublishDate.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPublishDate.setBounds(367, 233, 225, 22);
		contentPane.add(txtPublishDate);
		txtPublishDate.setColumns(10);
		
		txtSerialNo = new JTextField();
		txtSerialNo.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSerialNo.setBounds(367, 177, 225, 22);
		contentPane.add(txtSerialNo);
		txtSerialNo.setColumns(10);
		
		JButton btnAdd = new JButton("Add Book");
		btnAdd.setFont(new Font("Arial", Font.BOLD, 17));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ie) {
				
				insert();
			}
			
		});
		btnAdd.setBounds(837, 108, 178, 56);
		contentPane.add(btnAdd);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Arial", Font.BOLD, 17));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		btnClear.setBounds(865, 487, 166, 56);
		contentPane.add(btnClear);
		
		JButton btnBack = new JButton("Back");						//Navigates back
		btnBack.setFont(new Font("Arial", Font.BOLD, 17));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Books bk = new Books();
				bk.setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(77, 487, 166, 56);
		contentPane.add(btnBack);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\eclipseNeon\\eclipse\\Saves\\Groupwork\\src\\groupwork\\addbooks#.jpg"));
		label.setBounds(0, 0, 1254, 627);
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
		txtTitle.setText(null);
		txtAuthor.setText(null);
		txtPublishDate.setText(null);
		txtSerialNo.setText(null);
		
	}
	private void insert()
	{

		
		
		String sql = "insert into books values(?,?,?,?)";

		
		try{
								
			prst=cn.prepareStatement(sql);
			
			prst.setString(1, txtTitle.getText());
			prst.setString(2, txtAuthor.getText());
			prst.setObject(3, txtSerialNo.getText());
			prst.setObject(4, txtPublishDate.getText());
			
			prst.executeUpdate();
			JOptionPane.showMessageDialog(null,"Book successfully added");
			
			myconnection();
			clear();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
