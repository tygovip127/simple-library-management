package library.management;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;

public class Client
{
	private Socket soc;
	private ObjectInputStream ois;
	private DataInputStream dis;
	private DataOutputStream dos;
	private ObjectOutputStream oos;
	public Socket connect()
	{
		try {
			soc = new Socket(InetAddress.getLocalHost(), 7749);
			dos = new DataOutputStream(soc.getOutputStream());
			oos = new ObjectOutputStream(soc.getOutputStream());
			ois = new ObjectInputStream(soc.getInputStream());
			dis = new DataInputStream(soc.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return soc;
	}
	
	public String requestString(Reader reader, String request)
	{
		String result = "";
		try {
			dos.writeUTF(request);
			dos.flush();
			oos.writeObject(reader);
			oos.flush();
			result = dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String requestString(Loan loan, String request)
	{
		String result = "";
		try {
			dos.writeUTF(request);
			dos.flush();
			oos.writeObject(loan);
			oos.flush();
			result = dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public void requestBook(Book book, String request)
	{
		try {
			dos.writeUTF(request);
			dos.flush();
			oos.writeObject(book);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Book requestBookObject(Book book, String request)
	{
		Book bok = null;
		try {
			dos.writeUTF(request);
			dos.flush();
			oos.writeObject(book);
			oos.flush();
			bok = (Book) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return bok;
	}
	
	public Vector<Book> requestBookList(Book book, String request) 
	{
		Vector<Book> bookList = null;
		try {
			dos.writeUTF(request);
			dos.flush();
			oos.writeObject(book);
			oos.flush();
			bookList = (Vector<Book>) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return bookList;
	}
	
	public Vector<Book> requestBook(String request) 
	{
		Vector<Book> bookList = null;
		try {
			dos.writeUTF(request);
			dos.flush();
			bookList = (Vector<Book>) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return bookList;
	}
	
	public void requestReader(Reader reader, String request)
	{
		try {
			dos.writeUTF(request);
			dos.flush();
			oos.writeObject(reader);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public Vector<Reader> requestReader(String request) 
	{
		Vector<Reader> readerList = null;
		try {
			dos.writeUTF(request);
			dos.flush();
			readerList = (Vector<Reader>) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return readerList;
	}
	
	public Vector<Reader> requestReaderList(Reader reader, String request)
	{
		Vector<Reader> rd = null;
		try {
			dos.writeUTF(request);
			dos.flush();
			oos.writeObject(reader);
			oos.flush();
			rd = (Vector<Reader>) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return rd;
	}
	
	public Reader requestReaderObject(Reader reader, String request)
	{
		Reader rd = null;
		try {
			dos.writeUTF(request);
			dos.flush();
			oos.writeObject(reader);
			oos.flush();
			rd = (Reader) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return rd;
	}
	
	public void requestLoan(Loan loan, String request)
	{
		try {
			dos.writeUTF(request);
			dos.flush();
			oos.writeObject(loan);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Vector<Loan> requestLoanList(Loan loan, String request)
	{
		Vector<Loan> ln = null;
		try {
			dos.writeUTF(request);
			dos.flush();
			oos.writeObject(loan);
			oos.flush();
			ln = (Vector<Loan>) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ln;
	}
	
	public Vector<Loan> requestLoan(String request) 
	{
		Vector<Loan> loanList = null;
		try {
			dos.writeUTF(request);
			dos.flush();
			loanList = (Vector<Loan>) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return loanList;
	}
	
	public Vector<Punishment> requestPunishment(String request) 
	{
		Vector<Punishment> punishmentList = null;
		try {
			dos.writeUTF(request);
			dos.flush();
			punishmentList = (Vector<Punishment>) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return punishmentList;
	}
	
	public int requestStatistic(String request)
	{
		int getStatistic = 0;
		try {
			dos.writeUTF(request);
			dos.flush();
			getStatistic = dis.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getStatistic;
	}
}
