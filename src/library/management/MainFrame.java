package library.management;

import java.awt.*;
import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JDayChooser;

public class MainFrame extends JFrame{
	
//	BookModify bookModify = new BookModify();
//	ReaderModify readerModify = new ReaderModify();
//	LoanModify loanModify = new LoanModify();
//	PunishmentModify punishmentModify = new PunishmentModify();
//	StatisticModify statisticModify = new StatisticModify();
	Client client = new Client();
	private Connection conn;
	private Container cont;
	private ButtonGroup gr;
	private DefaultComboBoxModel cbbModel;
	private DefaultTableModel tblModel;
	private JPasswordField pfPassword;
	private JTextField tfSearchBook, tfBookName, tfPageNo, tfLanguage, tfPrice, tfAmount, tfAuthor, 
						tfPublisher, tfReaderName, tfIdentityCard, tfPhoneNumber, tfSurname, tfReaderId,
						tfBookId, tfSearchLoan, tfDay, tfMonth, tfYear, tfSearchReader, tfDateTime;
	
	private JButton btnSearchBook, btnAddBook, btnResetBook, btnUpdateBook, btnDeleteBook,
						btnCheckReaderId, btnCheckBookId, btnLoanBook, btnReturnBook, btnSearchLoan,
						btnResetLoan, btnPunish, btnAddReader, btnUpdateReader, btnDeleteReader, btnResetReader, btnSearchReader;
	private JLabel lblBookName, lblPageNo, lblPrice, lblAmount, lblPublishYear,
					lblType, lblAuthor, lblPublisher, lblLanguage, lblReaderName, lblIdentityCard, 
					lblPhoneNumber, lblPosition, lblSurname, lblReaderId, lblBookId, lblReturnAppointmentDate, 
					lblOutputReader, lblOutputBook, lblStatisticTotalBook, lblStatisticLoan, lblStatisticPunish, lblStatisticTotalLoan;
	private JScrollPane scrollPane;
	private JComboBox cbbFindBy, cbbPublishYear, cbbSort, cbbType, cbbLoanNo;
	private JTable table;
	private JPanel pnlBookManagement, pnlReaderManagement, pnlLoan, pnlStatistical; 
	private JTabbedPane tabbedPane;
	private JRadioButton rdoLecturer, rdoStudent;
	private JDateChooser dc;
	private DateFormat df;

	public MainFrame() {
		cont = this.getContentPane();
		cont.setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 720);
		
		//connect client-socket
		client.connect();
		
		//scrollpane
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 321, 1166, 362);
			//table
		table = new JTable();
		table.setCellSelectionEnabled(true);
		scrollPane.add(table);
		scrollPane.setViewportView(table);
		cont.add(scrollPane);
		
		//tabbedpane
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 1166, 301);
		
			//quan li sach
		pnlBookManagement = new JPanel();
		pnlBookManagement.setBounds(10, 41, 1166, 270);
		pnlBookManagement.setLayout(null);

		lblBookName = new JLabel("T??n s??ch:");
		lblPageNo = new JLabel("S??? trang:");
		lblLanguage = new JLabel("Ng??n ng???:");
		lblPrice = new JLabel("Gi?? tr???:");
		lblAmount = new JLabel("S??? l?????ng:");
		lblPublishYear = new JLabel("N??m xu???t b???n:");
		lblType = new JLabel("Th??? lo???i:");
		lblAuthor = new JLabel("T??c gi???:");
		lblPublisher = new JLabel("Nh?? xu???t b???n:");
	
		tfBookName = new JTextField(null);
		tfPageNo = new JTextField(null);
		tfLanguage = new JTextField(null);
		tfPrice = new JTextField();
		tfPrice.setText("0");
		tfAmount = new JTextField(null);
		tfSearchBook = new JTextField();

		cbbPublishYear = new JComboBox();
		cbbType = new JComboBox();
		cbbSort = new JComboBox();
		cbbSort.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sortAZPageNo();
				
			}
		});
		cbbFindBy = new JComboBox();
		
		tfAuthor = new JTextField(null);
		tfPublisher = new JTextField(null);
		
		btnAddBook = new JButton("Th??m s??ch");
		btnDeleteBook = new JButton("X??a s??ch");
		btnUpdateBook = new JButton("C???p nh???t");
		btnResetBook = new JButton("Nh???p l???i");
		btnSearchBook = new JButton("T??m ki???m");
		
		
		lblBookName.setBounds(10, 17, 73, 26);
		lblPageNo.setBounds(10, 53, 73, 26);
		lblLanguage.setBounds(10, 89, 73, 26);
		lblPrice.setBounds(10, 125, 81, 26);
		lblAmount.setBounds(10, 161, 73, 19);
		lblPublishYear.setBounds(245, 21, 81, 19);
		lblType.setBounds(245, 53, 81, 26);
		lblAuthor.setBounds(245, 89, 86, 26);
		lblPublisher.setBounds(245, 125, 86, 26);
		tfBookName.setBounds(70, 21, 123, 19);
		tfPageNo.setBounds(70, 57, 123, 19);
		tfLanguage.setBounds(70, 93, 123, 19);
		tfPrice.setBounds(70, 129, 123, 19);
		tfAmount.setBounds(70, 161, 123, 19);
		tfAuthor.setBounds(336, 93, 123, 19);
		tfPublisher.setBounds(336, 129, 123, 19);
		btnAddBook.setBounds(10, 215, 103, 26);
		cbbType.setBounds(336, 55, 123, 23);
		btnDeleteBook.setBounds(136, 215, 96, 26);
		btnUpdateBook.setBounds(252, 215, 99, 26);
		cbbSort.setBounds(570, 155, 191, 30);
		cbbFindBy.setBounds(570, 195, 113, 30);
		tfSearchBook.setBounds(570, 235, 470, 30);
		btnSearchBook.setBounds(1048, 234, 103, 30);
		btnResetBook.setBounds(373, 215, 86, 26);
		cbbPublishYear.setBounds(336, 20, 123, 20);
		
		cbbFindBy.setMaximumRowCount(7);
		cbbPublishYear.setMaximumRowCount(13);
		
		cbbPublishYear.setModel(new DefaultComboBoxModel(new String[] {"2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009"}));
		cbbType.setModel(new DefaultComboBoxModel(new String[] {"Gi??o tr??nh", "T??i li???u tham kh???o", "Lu???n ??n", "Lu???n v??n", "S??ch b??i t???p"}));
		cbbSort.setModel(new DefaultComboBoxModel(new String[] {"S???p x???p", "T??ng d???n theo s??? trang", "Gi???m d???n theo s??? trang"}));
		cbbFindBy.setModel(new DefaultComboBoxModel(new String[] {"T???t c???", "T??n s??ch", "T??c gi???", "Ng??n ng???", "N??m xu???t b???n", "Th??? lo???i", "NXB"}));

		pnlBookManagement.add(lblBookName);
		pnlBookManagement.add(lblPageNo);
		pnlBookManagement.add(lblLanguage);
		pnlBookManagement.add(lblPrice);
		pnlBookManagement.add(lblAmount);
		pnlBookManagement.add(lblPublishYear);
		pnlBookManagement.add(lblType);
		pnlBookManagement.add(lblAuthor);
		pnlBookManagement.add(lblPublisher);
		pnlBookManagement.add(tfBookName);
		pnlBookManagement.add(tfPageNo);
		pnlBookManagement.add(tfLanguage);		
		pnlBookManagement.add(tfAmount);
		pnlBookManagement.add(tfPrice);
		pnlBookManagement.add(cbbPublishYear);
		pnlBookManagement.add(tfAuthor);
		pnlBookManagement.add(tfPublisher);
		pnlBookManagement.add(btnAddBook);
		pnlBookManagement.add(cbbType);
		pnlBookManagement.add(btnDeleteBook);
		pnlBookManagement.add(btnUpdateBook);
		pnlBookManagement.add(cbbSort);
		pnlBookManagement.add(cbbFindBy);
		pnlBookManagement.add(tfSearchBook);
		pnlBookManagement.add(btnSearchBook);
		pnlBookManagement.add(btnResetBook);
		findAllBook();
		tabbedPane.addTab("Qu???n l?? s??ch", pnlBookManagement);
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				switch(tabbedPane.getSelectedIndex())
				{
					case 0: 
						findAllBook();
						break;
					case 1:
						findAllReader();
						break;
					case 2:
						findAllLoan();
						break;
					case 3:
						tblModel = new DefaultTableModel();
						table.setModel(tblModel);
						getStatistic();
						break;
				}
			}
		});
		
			//quan ly doc gia
		pnlReaderManagement = new JPanel();
		pnlReaderManagement.setBounds(9, 40, 1166, 270);
		pnlReaderManagement.setLayout(null);
		
		lblReaderName = new JLabel("T??n:");
		lblIdentityCard = new JLabel("CMND:");
		lblPhoneNumber = new JLabel("S??? ??i???n tho???i:");
		lblPosition = new JLabel("Ch???c v???:");
		lblSurname = new JLabel("H??? ?????m: ");
		
		rdoLecturer = new JRadioButton("Gi???ng vi??n");
		rdoStudent = new JRadioButton("Sinh vi??n");
		
		tfIdentityCard = new JTextField();
		tfReaderName = new JTextField();
		tfPhoneNumber = new JTextField();
		tfSurname = new JTextField();
		tfSearchReader = new JTextField();
		
		btnAddReader = new JButton("Th??m ?????c gi???");
		btnSearchReader = new JButton("T??m ki???m");
		
		btnUpdateReader = new JButton("C???p nh???t");
		btnUpdateReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt)
			{
				btnUpdateReaderActionPerformed(evt);
			}
		});
		btnDeleteReader = new JButton("X??a ?????c gi???");
		btnDeleteReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnDeleteReaderActionPerformed(evt);
			}
		});
		btnResetReader = new JButton("Nh???p l???i");
		btnResetReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnResetReaderActionPerformed(evt);
			}
		});
		
		lblReaderName.setBounds(64, 55, 80, 26);
		lblIdentityCard.setBounds(64, 88, 80, 26);
		lblPhoneNumber.setBounds(64, 124, 80, 29);
		lblPosition.setBounds(64, 163, 80, 13);
		lblSurname.setBounds(64, 19, 80, 26);
		rdoLecturer.setBounds(170, 159, 90, 21);
		rdoStudent.setBounds(290, 159, 90, 21);
		tfReaderName.setBounds(170, 55, 246, 26);
		tfIdentityCard.setBounds(170, 91, 246, 26);
		tfPhoneNumber.setBounds(170, 127, 246, 26);
		tfSearchReader.setBounds(570, 233, 470, 31);
		tfSurname.setBounds(170, 19, 246, 26);
		btnAddReader.setBounds(64, 196, 153, 26);
		btnUpdateReader.setBounds(272, 196, 141, 26);
		btnDeleteReader.setBounds(64, 238, 153, 26);
		btnResetReader.setBounds(272, 238, 140, 26);
		rdoStudent.setSelected(true);
		
		gr = new ButtonGroup();
		gr.add(rdoStudent);
		gr.add(rdoLecturer);
		
		pnlReaderManagement.add(lblReaderName);
		pnlReaderManagement.add(lblIdentityCard);
		pnlReaderManagement.add(lblPhoneNumber);
		pnlReaderManagement.add(lblPosition);
		
		
		
		pnlReaderManagement.add(lblSurname);
		pnlReaderManagement.add(rdoLecturer);
		pnlReaderManagement.add(rdoStudent);
		pnlReaderManagement.add(tfReaderName);
		pnlReaderManagement.add(tfIdentityCard);
		pnlReaderManagement.add(tfPhoneNumber);
		pnlReaderManagement.add(tfSurname);
		
		
		
		pnlReaderManagement.add(tfSearchReader);
		pnlReaderManagement.add(btnAddReader);
		pnlReaderManagement.add(btnUpdateReader);
		pnlReaderManagement.add(btnDeleteReader);
		pnlReaderManagement.add(btnResetReader);
		
		tabbedPane.addTab("Qu???n l?? ?????c gi???", pnlReaderManagement);
		
		
		btnSearchReader.setBounds(1050, 233, 102, 31);
		pnlReaderManagement.add(btnSearchReader);
		
		
		//muon tra sach
		pnlLoan = new JPanel();
		pnlLoan.setLayout(null);
		tabbedPane.addTab("M?????n tr??? s??ch", pnlLoan);
		
		
		lblReaderId = new JLabel("M?? ?????c gi???:");
		lblBookId = new JLabel("M?? s??ch:");
		lblReturnAppointmentDate = new JLabel("Ng??y h???n tr???:");
		lblOutputReader = new JLabel();
		lblOutputBook = new JLabel();
		tfReaderId = new JTextField();
		tfBookId = new JTextField();
		tfSearchLoan = new JTextField();
		
		tfDay = new JTextField();
		tfMonth = new JTextField();
		tfYear = new JTextField();
		tfDateTime = new JTextField();
		
		
		
		dc = new JDateChooser();
		pnlLoan.add(dc);
		dc.setBounds(175, 130, 148, 20);
		dc.setDateFormatString("yyyy-MM-dd");	
		df = new SimpleDateFormat("yyyy-MM-dd");
		
		btnCheckReaderId = new JButton("Ki???m tra");
		btnCheckReaderId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnTestReaderIdActionPerformed(evt);
				
			}
		});
		btnCheckBookId = new JButton("Ki???m tra");
		btnCheckBookId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnTestBookIdActionPerformed(evt);
			}
		});
		btnLoanBook = new JButton("M?????n s??ch");
		btnLoanBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) 
			{
					btnAddLoanActionPerformed(evt);
			}
		});
		btnReturnBook = new JButton("Tr??? s??ch");
		btnReturnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnReturnBookActionPerformed(evt);
			}
		});
		btnSearchLoan = new JButton("T??m ki???m");
		btnSearchLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnSearchLoanActionPerformed(evt);
			}
		});
		btnResetLoan = new JButton("Nh???p l???i");
		btnResetLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnResetLoanActionPerformed(evt);
			}
		});
		
		btnPunish = new JButton("DS Ph???t");
		btnPunish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt)
			{
				btnPunishActionPerformed(evt);
			}
		});
		cbbLoanNo = new JComboBox();
		
		lblReaderId.setBounds(65, 22, 100, 20);
		lblBookId.setBounds(65, 75, 100, 20);
		lblReturnAppointmentDate.setBounds(65, 130, 148, 20);
		lblOutputReader.setBounds(175, 46, 221, 20);
		lblOutputBook.setBounds(175, 100, 221, 20);
		tfReaderId.setBounds(175, 23, 138, 20);
		tfBookId.setBounds(175, 76, 138, 20);
		tfDateTime.setBounds(217,127,55,28);
		btnCheckReaderId.setBounds(323, 22, 90, 21);
		btnCheckBookId.setBounds(323, 75, 90, 20);
		btnLoanBook.setBounds(65, 183, 100, 28);
		btnReturnBook.setBounds(200, 183, 87, 28);
		tfSearchLoan.setBounds(570, 237, 470, 28);
		btnSearchLoan.setBounds(1051, 236, 100, 28);
		tfDay.setBounds(217,127,55,28);
		tfMonth.setBounds(282,127,55,28);
		tfYear.setBounds(347,127,49,28);
		btnResetLoan.setBounds(319,183,94,28);
		btnPunish.setBounds(200,223,87,28);
		
		
		

		
		pnlLoan.add(lblReaderId);
		pnlLoan.add(lblBookId);
		pnlLoan.add(lblReturnAppointmentDate);
		pnlLoan.add(tfReaderId);
		pnlLoan.add(tfBookId);
		pnlLoan.add(btnCheckReaderId);
		pnlLoan.add(btnCheckBookId);
		pnlLoan.add(btnLoanBook);
		pnlLoan.add(btnReturnBook);

		pnlLoan.add(tfSearchLoan);
		pnlLoan.add(btnSearchLoan);

		pnlLoan.add(lblOutputReader);
		pnlLoan.add(lblOutputBook);
		pnlLoan.add(btnResetLoan);
		pnlLoan.add(btnPunish);

		
		
		//thong ke
		
		
		pnlStatistical = new JPanel();
		pnlStatistical.setLayout(null);
		tabbedPane.addTab("Th???ng k??", pnlStatistical);
		cont.add(tabbedPane);
		
		lblStatisticTotalBook = new JLabel();
		lblStatisticTotalLoan = new JLabel();
		lblStatisticLoan = new JLabel();
		lblStatisticPunish = new JLabel(); 
		
		lblStatisticTotalBook.setBounds(389,10,200,28);
		lblStatisticTotalLoan.setBounds(389,51,200,28);
		lblStatisticLoan.setBounds(389,89,200,28);
		lblStatisticPunish.setBounds(389,127,200,28);
		
		pnlStatistical.add(lblStatisticTotalBook);
		pnlStatistical.add(lblStatisticTotalLoan);
		pnlStatistical.add(lblStatisticLoan);
		pnlStatistical.add(lblStatisticPunish);
		
		
		
		btnResetBook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				
				btnResetBookActionPerformed(evt);
			}
		});
		
		btnSearchBook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) 
			{
				btnSearchBookActionPerformed(evt);
			}});
		btnDeleteBook.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent evt) 
			{
				btnDeleteBookActionPerformed(evt);
			}
		});
		
		btnUpdateBook.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent evt) 
			{
				btnUpdateBookActionPerformed(evt);
			}
		});
		
		btnAddBook.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent evt) 
			{
				btnAddBookActionPerformed(evt);
			}
		});
		
		btnSearchReader.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				findAllReader();
				
			}
		});
		
		btnAddReader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnAddReaderActionPerformed(evt);
			}
		});

		setVisible(true);

	}


	
	

	
	private void btnSearchBookActionPerformed(ActionEvent evt)
	{
		String request;
		String parameter;
		if(tfSearchBook.getText().equals(""))
		{
			findAllBook();
		} else
		{
			if(cbbFindBy.getSelectedItem().equals("T??n s??ch"))
			{
				request = "findBookByName";
				parameter = tfSearchBook.getText();
				Book book = new Book();
				book.setBookName(parameter);
				findBookBy(book,request);
			} 
			else if(cbbFindBy.getSelectedItem().equals("T??c gi???"))
			{
				request = "findBookByAuthor";
				parameter = tfSearchBook.getText();
				Book book = new Book();
				book.setAuthor(parameter);
				findBookBy(book,request);
			} 
			else if(cbbFindBy.getSelectedItem().equals("Ng??n ng???"))
			{
				request = "findBookByLanguage";
				parameter = tfSearchBook.getText();
				Book book = new Book();
				book.setLanguage(parameter);
				findBookBy(book,request);
			} 
			else if(cbbFindBy.getSelectedItem().equals("N??m xu???t b???n"))
			{
				request = "findBookByPublishYear";
				parameter = tfSearchBook.getText();
				Book book = new Book();
				book.setPublishYear(parameter);
				findBookBy(book,request);
			} 
			else if(cbbFindBy.getSelectedItem().equals("Th??? lo???i"))
			{
				request = "findBookByType";
				parameter = tfSearchBook.getText();
				Book book = new Book();
				book.setType(parameter);
				findBookBy(book,request);
			} 
			else if(cbbFindBy.getSelectedItem().equals("NXB"))
			{
				request = "findBookByPublisher";
				parameter = tfSearchBook.getText();
				Book book = new Book();
				book.setPublisher(parameter);
				findBookBy(book,request);
			} 
			else if(cbbFindBy.getSelectedItem().equals("T???t c???"))
			{
				request = "findByAllBook";
				parameter = tfSearchBook.getText();
				Book book = new Book();
				book.setBookName(parameter);
				findBookBy(book,request);
			}
		}	
	}
	
	private void btnResetBookActionPerformed(ActionEvent evt)
	{
		tfBookName.setText(null);
		tfPageNo.setText(null);
		tfLanguage.setText(null);
		tfPrice.setText("0");
		tfAmount.setText(null);
		cbbPublishYear.setSelectedIndex(0);
		cbbType.setSelectedIndex(0);
		tfAuthor.setText(null);
		tfPublisher.setText(null);
	}
	
	private void btnDeleteBookActionPerformed(ActionEvent evt)
	{
		try
		{
			Book book = new Book();
			book.setBookId(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
			client.requestBook(book, "deleteBook");
			findAllBook();
		} catch(IndexOutOfBoundsException e)
		{
			JOptionPane.showInternalMessageDialog(cont,"Vui l??ng ch???n h??ng c???n x??a");
		}
	}
	
	private void btnUpdateBookActionPerformed(ActionEvent evt)
	{
		Book book = new Book();
		if(tfBookName.getText().length()==0  || tfAmount.getText().length()==0)
		{
			JOptionPane.showInternalMessageDialog(cont,"T??n s??ch v?? s??? l?????ng s??ch kh??ng ???????c ????? tr???ng");
		} 
		else
		{
			book.setBookName(tfBookName.getText());
			if(tfPageNo.getText().length()==0)
			{
				book.setPageNo(null);
			}
			else
			{
				book.setPageNo(tfPageNo.getText());
			}
			
			if(tfLanguage.getText().length()==0)
			{
				book.setLanguage(null);
			}
			else
			{
				book.setLanguage(tfLanguage.getText());
			}
			book.setPrice(Integer.parseInt(tfPrice.getText()));
			book.setAmount(Integer.parseInt(tfAmount.getText()));
			book.setPublishYear(String.valueOf(cbbPublishYear.getSelectedItem()));
			book.setType(String.valueOf(cbbType.getSelectedItem()));
			if(tfAuthor.getText().length()==0)
			{
				book.setAuthor(null);
			}
			else
			{
				book.setAuthor(tfAuthor.getText());
			}
			
			if(tfPublisher.getText().length()==0)
			{
				book.setPublisher(null);
			}
			else
			{
				book.setPublisher(tfPublisher.getText());
			}
			book.setBookId(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
			client.requestBook(book, "updateBook");
			findAllBook();
		}
		
		
	}
	
	private void btnAddBookActionPerformed(ActionEvent evt)
	{
		Book book = new Book();
		if(tfBookName.getText().length()==0  || tfAmount.getText().length()==0)
		{
			JOptionPane.showInternalMessageDialog(cont,"T??n s??ch v?? s??? l?????ng s??ch kh??ng ???????c ????? tr???ng");
		} 
		else
		{
			book.setBookName(tfBookName.getText());
			if(tfPageNo.getText().length()==0)
			{
				book.setPageNo(null);
			}
			else
			{
				book.setPageNo(tfPageNo.getText());
			}
			
			if(tfLanguage.getText().length()==0)
			{
				book.setLanguage(null);
			}
			else
			{
				book.setLanguage(tfLanguage.getText());
			}
			book.setPrice(Integer.parseInt(tfPrice.getText()));
			book.setAmount(Integer.parseInt(tfAmount.getText()));
			book.setPublishYear(String.valueOf(cbbPublishYear.getSelectedItem()));
			book.setType(String.valueOf(cbbType.getSelectedItem()));
			if(tfAuthor.getText().length()==0)
			{
				book.setAuthor(null);
			}
			else
			{
				book.setAuthor(tfAuthor.getText());
			}
			
			if(tfPublisher.getText().length()==0)
			{
				book.setPublisher(null);
			}
			else
			{
				book.setPublisher(tfPublisher.getText());
			}
			client.requestBook(book, "addBook");
			findAllBook();
		}
	}
	
	


	
	

	
	
	private void btnAddReaderActionPerformed(ActionEvent evt)
	{
		Reader reader = new Reader();
		if(tfReaderName.getText().length()==0  || tfIdentityCard.getText().length()==0 || tfPhoneNumber.getText().length()==0)
		{
			JOptionPane.showInternalMessageDialog(cont,"Vui l??ng ??i???n ?????y ????? th??ng tin");
		} 
		else
		{
			if(tfSurname.getText().length()==0)
			{
				reader.setSurname(null);
			}
			else
			{
				reader.setSurname(tfSurname.getText());
			}
			reader.setName(tfReaderName.getText());
			reader.setIdentityCard(tfIdentityCard.getText());
			reader.setPhoneNo(tfPhoneNumber.getText());
			if(rdoLecturer.isSelected())
			{
				reader.setJob(rdoLecturer.getText());
			} else if(rdoStudent.isSelected())
			{
				reader.setJob(rdoStudent.getText());
			}
			client.requestReader(reader, "addReader");
			findAllReader();
		}

	}
	
	private void btnUpdateReaderActionPerformed(ActionEvent evt)
	{
		Reader reader = new Reader();
		if(tfReaderName.getText().length()==0  || tfIdentityCard.getText().length()==0 || tfPhoneNumber.getText().length()==0)
		{
			JOptionPane.showInternalMessageDialog(cont,"Vui l??ng ??i???n ?????y ????? th??ng tin");
		} 
		else
		{
			if(tfSurname.getText().length()==0)
			{
				reader.setSurname(null);
			}
			else
			{
				reader.setSurname(tfSurname.getText());
			}
			reader.setName(tfReaderName.getText());
			reader.setIdentityCard(tfIdentityCard.getText());
			reader.setPhoneNo(tfPhoneNumber.getText());
			if(rdoLecturer.isSelected())
			{
				reader.setJob(rdoLecturer.getText());
			} else if(rdoStudent.isSelected())
			{
				reader.setJob(rdoStudent.getText());
			}
			reader.setReaderId(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
			client.requestReader(reader, "updateReader");
			findAllReader();
		}
		
	}
	
	private void btnDeleteReaderActionPerformed(ActionEvent evt)
	{
		try
		{
			Reader reader = new Reader();
			reader.setReaderId(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
			client.requestReader(reader, "deleteReader");
			findAllReader();
		} catch(IndexOutOfBoundsException e)
		{
			JOptionPane.showInternalMessageDialog(cont,"Vui l??ng ch???n h??ng c???n x??a");
		}
	}
	
	private void btnResetReaderActionPerformed(ActionEvent evt)
	{
		tfSurname.setText(null);
		tfReaderName.setText(null);
		tfIdentityCard.setText(null);
		tfPhoneNumber.setText(null);
		rdoStudent.setSelected(true);
		
	}
	
	private void btnSearchLoanActionPerformed(ActionEvent evt)
	{
		findAllLoan();
	}
	
	private void btnAddLoanActionPerformed(ActionEvent evt)
	{
		Loan loan = new Loan();
		try
		{
			loan.setReaderId(Integer.parseInt(tfReaderId.getText()));
			loan.setBookId(Integer.parseInt(tfBookId.getText()));

			try
			{

				loan.setBookReturnAppointmentDate(df.format(dc.getDate()));
				if(lblOutputBook.getText().equals("Kh??ng t??m th???y s??ch") || lblOutputReader.getText().equals("Kh??ng t??m th???y ?????c gi???"))
				{
					JOptionPane.showInternalMessageDialog(cont, "M?????n s??ch th???t b???i\nVui l??ng ki???m tra m?? ?????c gi??? v?? m?? s??ch");
				} else
				{

				Reader reader = new Reader();
				reader.setReaderId(Integer.parseInt(tfReaderId.getText()));
				String checkTimeSpace = client.requestString(reader, "checkTimeSpace");
				if(checkTimeSpace == null || Integer.parseInt(checkTimeSpace)>=7)
				{
					int addLoan = Integer.parseInt(client.requestString(loan, "addLoan"));
					if(addLoan ==0 )
					{
						JOptionPane.showMessageDialog(this, "S??ch n??y ???? h???t trong kho");
					}
				} 
				else
				{
					JOptionPane.showInternalMessageDialog(cont, "M???i ?????c gi??? ch??? ???????c m?????n t???i ??a 3 quy???n s??ch trong 1 tu???n");
				}
				}
				
				findAllLoan();
			} catch(Exception e)
			{
				JOptionPane.showInternalMessageDialog(cont, "Ng??y h???n tr??? kh??ng h???p l???");
			}



		} catch(Exception e)
		{
			JOptionPane.showInternalMessageDialog(cont, "Vui l??ng nh???p ?????y ????? th??ng tin");
		}
	}
	
	private void btnReturnBookActionPerformed(ActionEvent evt)
	{
		try
		{
			Loan loan = new Loan();
			loan.setLoanId(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
			client.requestLoan(loan, "returnBook");
			findAllLoan();
		} catch(Exception e)
		{
			JOptionPane.showInternalMessageDialog(cont, "Vui l??ng ch???n m?? m?????n c???a s??ch c???n tr???");
		}
	}
	
	private void btnTestReaderIdActionPerformed(ActionEvent evt)
	{
		try
		{
			Reader reader = new Reader();
			reader.setReaderId(Integer.parseInt(tfReaderId.getText()));
			Reader rd = client.requestReaderObject(reader, "testReaderId");
//			Reader reader = loanModify.testReaderId(Integer.parseInt(tfReaderId.getText()));
			if(rd.getName().equals(""))
			{
				lblOutputReader.setText("Kh??ng t??m th???y ?????c gi???");
				lblOutputReader.setForeground(Color.RED);
			}
			else
			{
				lblOutputReader.setText(rd.getName());
				lblOutputReader.setForeground(Color.GREEN);
			}
		} catch(Exception e)
		{
			lblOutputReader.setText("Kh??ng t??m th???y ?????c gi???");
			lblOutputReader.setForeground(Color.RED);
		}
	}
	
	private void btnTestBookIdActionPerformed(ActionEvent evt)
	{
		try
		{
			Book book = new Book();
			book.setBookId(Integer.parseInt(tfBookId.getText()));
			Book bok = client.requestBookObject(book, "testBookId");
			if(bok.getBookName().equals(""))
			{
				lblOutputBook.setText("Kh??ng t??m th???y s??ch");
				lblOutputBook.setForeground(Color.GREEN);
			}
			lblOutputBook.setText(bok.getBookName());
			lblOutputBook.setForeground(Color.GREEN);
		} catch(Exception e)
		{
			lblOutputBook.setText("Kh??ng t??m th???y s??ch");
			lblOutputBook.setForeground(Color.RED);
		}
	}
	
	
	private void btnResetLoanActionPerformed(ActionEvent evt)
	{
		tfReaderId.setText(null);
		tfBookId.setText(null);

		lblOutputReader.setText(null);
		lblOutputBook.setText(null);
		dc.setCalendar(null);
	}
	
	private void btnPunishActionPerformed(ActionEvent evt)
	{
		punish();
	}
	
	
	public void sortAZPageNo()
	{
		Vector<Book> bookList = new Vector();
		if(cbbSort.getSelectedItem().equals("T??ng d???n theo s??? trang"))
		{
			bookList = client.requestBook("sortAZPageNo");
		}
		else
		if(cbbSort.getSelectedItem().equals("Gi???m d???n theo s??? trang"))
		{
			bookList = client.requestBook("sortZAPageNo");
		}
		String[] columnNames = {"M?? s??ch","T??n s??ch","S??? trang","Ng??n ng???","Gi?? tr???","S??? l?????ng c??n","N??m xu???t b???n","Th??? lo???i","T??c gi???","Nh?? xu???t b???n"};
		tblModel = new DefaultTableModel();
		tblModel.setColumnIdentifiers(columnNames);
		table.setModel(tblModel);
		for(Book book: bookList)
		{
			tblModel.addRow(new Object[] {book.getBookId(),book.getBookName(),book.getPageNo(),book.getLanguage(),book.getPrice(),
					book.getAmount(),book.getPublishYear(),book.getType(),book.getAuthor(),book.getPublisher()});
		}
		
	}
	//find information
	
	public void findAllBook()
	{
		Vector<Book> bookList = client.requestBook("findAllBook");
		String[] columnNames = {"M?? s??ch", "T??n s??ch", "S??? trang", "Ng??n ng???", "Gi?? tr???", "S??? l?????ng c??n", "N??m xu???t b???n", "Th??? lo???i", "T??c gi???", "Nh?? xu???t b???n"}; 
		DefaultTableModel tblModel = new DefaultTableModel();
		tblModel.setColumnIdentifiers(columnNames);
		table.setModel(tblModel);	
		for(Book book: bookList)
		{
			tblModel.addRow(new Object[] {book.getBookId(),book.getBookName(),book.getPageNo(),book.getLanguage(),book.getPrice(),
											book.getAmount(),book.getPublishYear(),book.getType(),book.getAuthor(),book.getPublisher()});
		}
	}
	
	
	public void findBookBy(Book book, String request)
	{
		Vector<Book> bookList = client.requestBookList(book, request);
		String[] columnNames = {"M?? s??ch", "T??n s??ch", "S??? trang", "Ng??n ng???", "Gi?? tr???", "S??? l?????ng c??n", "N??m xu???t b???n", "Th??? lo???i", " T??c gi???", " Nh?? xu???t b???n"}; 
		tblModel = new DefaultTableModel();
		tblModel.setColumnIdentifiers(columnNames);
		table.setModel(tblModel);
		
		for(Book book1: bookList)
		{
			tblModel.addRow(new Object[] {book1.getBookId(),book1.getBookName(),book1.getPageNo(),book1.getLanguage(),book1.getPrice(),
											book1.getAmount(),book1.getPublishYear(),book1.getType(),book1.getAuthor(),book1.getPublisher()});
		}
	}
	
	public void findAllReader()
	{
		Vector<Reader> readerList = null;
		if(btnSearchReader.getText().equals(""))
		{
			readerList = client.requestReader("findAllReader");
		} else
		{
			Reader rd = new Reader();
			rd.setName(tfSearchReader.getText());
			readerList = client.requestReaderList(rd, "findReaderByAll");
		}
		String[] columnNames = {"M?? ?????c gi???", "H??? ?????m", 
				"T??n", "CMND", "S??T", "Ng??y c???p th???", "Ch???c v???"};
		tblModel = new DefaultTableModel();
		tblModel.setColumnIdentifiers(columnNames);
		table.setModel(tblModel);
		for(Reader reader: readerList)
		{
			tblModel.addRow(new Object[] {reader.getReaderId(), reader.getSurname(), reader.getName(), reader.getIdentityCard(), reader.getPhoneNo(), reader.getCardIssueDate(), reader.getJob()});
		}
		
		
	}
	
	public void findAllLoan()
	{
		Vector<Loan> loanList = null;
		
		if(tfSearchLoan.getText().equals(""))
		{
			loanList = client.requestLoan("findAllLoan");
		} else
		{
			Loan ln = new Loan();
			ln.setStatus(tfSearchLoan.getText());
			loanList = client.requestLoanList(ln, "findLoanByAll");
		}
		String[] columnNames = {"M?? m?????n","M?? ?????c gi???","M?? s??ch", "S??? l?????ng m?????n", "Ng??y m?????n", "Ng??y h???n tr???", "Ng??y tr???", "Tr???ng th??i"};
		tblModel = new DefaultTableModel();
		tblModel.setColumnIdentifiers(columnNames);
		table.setModel(tblModel);
		for(Loan loan: loanList)
		{
			tblModel.addRow(new Object[] {loan.getLoanId(), loan.getReaderId(), loan.getBookId(), loan.getLoanNo(), loan.getLoanDate(), loan.getBookReturnAppointmentDate(), loan.getBookReturnDate(), loan.getStatus()});
		}
	}
	
	public void punish()
	{
		Vector<Punishment> punishmentList1 = client.requestPunishment("getPunishmentList");
		Vector<Punishment> punishmentList2 = client.requestPunishment("getPunishmentReturnYet");
		String[] columnNames = {"M?? m?????n","M?? ?????c gi???","T??n ?????c gi???","M?? s??ch","T??n s??ch","S??? l?????ng m?????n","Qu?? h???n (ng??y)","Th??nh ti???n (?????ng)", "Tr???ng th??i s??ch"};
		tblModel = new DefaultTableModel();
		tblModel.setColumnIdentifiers(columnNames);
		table.setModel(tblModel);
		for(Punishment punishment : punishmentList1)
		{
			tblModel.addRow(new Object[] {punishment.getLoanId(),punishment.getReaderId(),punishment.getFullname(),punishment.getBookId(),punishment.getBook(),punishment.getLoanNo(),punishment.getDaysLate(),punishment.getTotal(),punishment.getStatus()});
		}
		
		for(Punishment punishment: punishmentList2)
		{
			tblModel.addRow(new Object[] {punishment.getLoanId(),punishment.getReaderId(),punishment.getFullname(),punishment.getBookId(),punishment.getBook(),punishment.getLoanNo(),punishment.getDaysLate(),punishment.getTotal(),punishment.getStatus()});
		}
	}
	
	public void getStatistic()
	{
		lblStatisticTotalBook.setText("T???ng s??? s??ch: "+client.requestStatistic("getStatisticTotalBook"));
		lblStatisticTotalLoan.setText("T???ng s??? phi???u m?????n s??ch: "+client.requestStatistic("getStatisticTotalLoan"));
		lblStatisticLoan.setText("S??ch ??ang cho m?????n: "+client.requestStatistic("getStatisticLoan"));
		lblStatisticPunish.setText("S??ch b??? tr??? h???n tr???: "+client.requestStatistic("getStatisticPunish"));
	}
	

	public static void main(String[] args)
	{
		new MainFrame();
	}
}
