package library.management;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class Server {
	private ServerSocket serSoc;
//	private Socket soc;
//	private DataInputStream dis;
//	private ObjectInputStream ois;
//	private ObjectOutputStream oos;
	
	
	public Server()
	{
		try {
			serSoc = new ServerSocket(7749);
			System.out.println("Server is waiting...");
			while(true)
			{
				Socket soc = serSoc.accept();
				System.out.println("Connected new client!!!");
				ThreadHandler th = new ThreadHandler(soc);
				th.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args)
	{
		new Server();
	}
}

class ThreadHandler extends Thread
{
	BookModify bookModify = new BookModify();
	ReaderModify readerModify = new ReaderModify();
	LoanModify loanModify = new LoanModify();
	StatisticModify statisticModify = new StatisticModify();
	PunishmentModify punishmentModify = new PunishmentModify();
	private Book bookIn = null;
	private Book bookOut = null;
	private Reader readerIn = null;
	private Reader readerOut = null;
	private Loan loanIn = null;
	private Loan loanOut = null;
	private Punishment punishmentIn = null;
	private Punishment punishmentOut = null;
	
	private Vector<Book> bookListIn = null;
	private Vector<Reader> readerListIn = null;
	private Vector<Loan> loanListIn = null;
	private Vector<Book> bookListOut = null;
	private Vector<Reader> readerListOut = null;
	private Vector<Loan> loanListOut = null;
	private Vector<Punishment> punishmentListIn = null;
	private Vector<Punishment> punishmentListOut = null;
	
	private Socket soc;
	private DataInputStream dis;
	private ObjectInputStream ois;
	private DataOutputStream dos;
	private ObjectOutputStream oos;
	public ThreadHandler(Socket soc)
	{
		try {
			this.soc = soc;
			dis = new DataInputStream(soc.getInputStream());
			ois = new ObjectInputStream(soc.getInputStream());
			oos = new ObjectOutputStream(soc.getOutputStream()); 
			dos = new DataOutputStream(soc.getOutputStream());
		} catch (Exception e) {
			
		}
	}

	@Override
	public void run() {
		try {
			while(true)
			{
				String request = dis.readUTF();
						switch(request)
						{
						case "addBook":
							bookIn = (Book) ois.readObject();
							bookModify.addBook(bookIn);
							break;
						case "deleteBook":
							bookIn = (Book) ois.readObject();
							bookModify.deleteBook(bookIn.getBookId());
							break;
						case "updateBook":
							bookIn = (Book) ois.readObject();
							bookModify.updateBook(bookIn);
							break;
						case "findAllBook":
							bookListOut = bookModify.getBookList();
							oos.writeObject(bookListOut);
							oos.flush();
							break;
						case "addReader":
							readerIn = (Reader) ois.readObject();
							readerModify.addReader(readerIn);
							break;
						case "findAllReader":
							readerListOut = readerModify.getReaderList();
							oos.writeObject(readerListOut);
							oos.flush();
							break;
						case "findAllLoan":
							loanListOut = loanModify.getLoanlist();
							oos.writeObject(loanListOut);
							oos.flush();
							break;
						case "getStatisticTotalBook":
							int getStatisticTotalBook = statisticModify.getStatisticTotalBook();
							dos.write(getStatisticTotalBook);
							dos.flush();
							break;
						case "getStatisticTotalLoan":
							int getStatisticTotalLoan = statisticModify.getStatisticTotalLoan();
							dos.write(getStatisticTotalLoan);
							dos.flush();
							break;
						case "getStatisticLoan":
							int getStatisticLoan = statisticModify.getStatisticLoan();
							dos.write(getStatisticLoan);
							dos.flush();
							break;
						case "getStatisticPunish":
							int getStatisticPunish = statisticModify.getStatisticPunish();
							dos.write(getStatisticPunish);
							dos.flush();
							break;
						case "sortAZPageNo":
							bookListOut = bookModify.sortAZPageNo("call sp_sortAZPageNo");
							oos.writeObject(bookListOut);
							oos.flush();
							break;
						case "sortZAPageNo":
							bookListOut = bookModify.sortAZPageNo("call sp_sortZAPageNo");
							oos.writeObject(bookListOut);
							oos.flush();
							break;
						case "updateReader":
							readerIn = (Reader) ois.readObject();
							readerModify.updateReader(readerIn);
							break;
						case "deleteReader":
							readerIn = (Reader) ois.readObject();
							readerModify.deleteReader(readerIn.getReaderId());
							break;
						case "testReaderId":
							readerIn = (Reader) ois.readObject();
							readerOut = loanModify.testReaderId(readerIn.getReaderId());
							oos.writeObject(readerOut);
							oos.flush();
							break;
						case "testBookId":
							bookIn = (Book) ois.readObject();
							bookOut = loanModify.testBookId(bookIn.getBookId());
							oos.writeObject(bookOut);
							oos.flush();
							break;
						case "checkTimeSpace":
							readerIn = (Reader) ois.readObject();
							String result = loanModify.checkTimeSpace(String.valueOf(readerIn.getReaderId()));
							dos.writeUTF(result);
							dos.flush();
							break;
						case "addLoan":
							loanIn = (Loan) ois.readObject();
							String rs = String.valueOf(loanModify.addLoan(loanIn));
							dos.writeUTF(rs);
							dos.flush();
							break;		
						case "returnBook":
							loanIn = (Loan) ois.readObject();
							loanModify.returnBook(loanIn.getLoanId());
							break;
						case "getPunishmentList":
							punishmentListOut = punishmentModify.getPunishmentList();
							oos.writeObject(punishmentListOut);
							oos.flush();
							break;
						case "getPunishmentReturnYet":
							punishmentListOut = punishmentModify.getPunishmentListReturnYet();
							oos.writeObject(punishmentListOut);
							oos.flush();
							break;
						case "findLoanByAll":
							loanIn = (Loan) ois.readObject();
							loanListOut = loanModify.findLoanByAll(loanIn.getStatus());
							oos.writeObject(loanListOut);
							oos.flush();
							break;
						case "findReaderByAll":
							readerIn = (Reader) ois.readObject();
							readerListOut = readerModify.findReaderByAll(readerIn.getName());
							oos.writeObject(readerListOut);
							oos.flush();
							break;
						case "findBookByName":
							bookIn = (Book) ois.readObject();
							String sql = "call sp_findBookByName(?)";
							bookListOut = bookModify.findBookBy(sql, bookIn.getBookName());
							oos.writeObject(bookListOut);
							oos.flush();
							break;
						case "findBookByAuthor":
							bookIn = (Book) ois.readObject();
							String sql1 = "call sp_findBookByAuthor(?)";
							bookListOut = bookModify.findBookBy(sql1, bookIn.getAuthor());
							oos.writeObject(bookListOut);
							oos.flush();
							break;
						case "findBookByLanguage":
							bookIn = (Book) ois.readObject();
							String sql2 = "call sp_findBookByLanguage(?)";
							bookListOut = bookModify.findBookBy(sql2, bookIn.getLanguage());
							oos.writeObject(bookListOut);
							oos.flush();
							break;
						case "findBookByPublishYear":
							bookIn = (Book) ois.readObject();
							String sql3 = "call sp_findBookByPublishYear(?)";
							bookListOut = bookModify.findBookBy(sql3, bookIn.getPublishYear());
							oos.writeObject(bookListOut);
							oos.flush();
							break;
						case "findBookByType":
							bookIn = (Book) ois.readObject();
							String sql4 = "call sp_findBookByType(?)";
							bookListOut = bookModify.findBookBy(sql4, bookIn.getType());
							oos.writeObject(bookListOut);
							oos.flush();
							break;
						case "findBookByPublisher":
							bookIn = (Book) ois.readObject();
							String sql5 = "call sp_findBookByPublisher(?)";
							bookListOut = bookModify.findBookBy(sql5, bookIn.getPublisher());
							oos.writeObject(bookListOut);
							oos.flush();
							break;
						case "findByAllBook":
							bookIn = (Book) ois.readObject();
							String sql6 = "call sp_findByAllBook(?)";
							bookListOut = bookModify.findBookBy(sql6, bookIn.getBookName());
							oos.writeObject(bookListOut);
							oos.flush();
							break;
						}
						
					}
					
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
