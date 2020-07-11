package com.abc.librarymgmt;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/LibraryServlet")
public class LibraryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private LibraryDBUtil dbUtil;

	@Resource(name = "jdbc/library")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();

		// create our Library db util ... and pass in the conn pool / datasource
		try {
			dbUtil = new LibraryDBUtil(dataSource);
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String command = request.getParameter("command");

		switch (command) {
		case "VIEW_LIB":
			getLibrarianDetails(request, response);
			break;
		case "DEL_LIB":
			deleteLibrarian(request, response);
			break;
		case "VIEW_ALL_BK":
			getBookDetails(request, response);
			break;
		case "DEL_BK":
			deleteBook(request, response);
			break;
		case "VIEW_BOR_BK":
			getBorrowedBooks(request, response);
			break;
		case "VIEW_BOR_STD":
			getBorrowedStudents(request, response);
			break;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String command = request.getParameter("command");

		switch (command) {
		case "ADD_LIB":
			loadLibrarianDetails(request, response);
			break;
		case "ADD_BK":
			loadBookDetails(request, response);
			break;
		case "BOR_BK":
			validateBorrowDetails(request, response);
			break;
		case "RET_BK":
			loadReturnBookDetails(request, response);
			break;

		}
	}

	private void loadReturnBookDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int bid = Integer.parseInt(request.getParameter("bid"));
		int sid = Integer.parseInt(request.getParameter("sid"));
		String ret_date;
		String bor_date;
		// check if student with sid borrowed a book or not.
		if (dbUtil.validateStudentBorrowed(sid, bid))// if yes(This Student surely did borrow a book)
		{
			// You here means that Student has atleast a row in borrowed_books.
			// Delete the respective entry for sid and bid from borrowed_books by
			// fetching borrow_date calculating due and increment the instock
			//in books_stock.
			DetailsInitializer di = dbUtil.getDueDetails(sid, bid);
			if (di != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				bor_date = di.getFirst_name();// this returns the borrow_date in string format
				LocalDate l=LocalDate.now();
				ret_date=l.toString(); //gets todays date

				try {
					Date return_date = sdf.parse(ret_date);// Parses text from the beginning of the given string to
															// produce a date(util)
					System.out.println(return_date);
					Date borrowed_date = sdf.parse(bor_date);// Parses text from the beginning of the given string to
																// produce a date(util)
					long difference = (return_date.getTime() - borrowed_date.getTime()) / 86400000;
					PrintWriter out = response.getWriter();

					if (difference <= 0) {
						out.println("<h1 class='center-align'>You have no due amount.. ThankYou </h1>");
						out.println("<a href='librarian-operations.jsp'><button>Back To Menu</button></a>");
					} else {
						out.println("<h1 class='center-align'>Due Amount is:" + difference * 2 + " </h1>");
						out.println("<a href='librarian-operations.jsp'><button>Back To Menu</button></a>");
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				dbUtil.validateStudentBorrowed(sid,bid);/*
				 * check if he has more books, if yes, retain his row in borrowed_students table
				 * as is. else it means he has only one borrowed book which is deleted in
				 * step-1. So no need of his entry in borrowed_student table anymore. This takes
				 * care inside validateStudentBorrowed(sid) function.
				 */
			}
			else {
				//people who haven't borrowed anything if clicks to return book , you will come here
				goToBorrowPage(request, response,"borrow");
			}
		}
		else {
			goToBorrowPage(request, response,"return");
		}

	}

	private void getBorrowedStudents(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get Borrowed Students from database (db util)
		List<DetailsInitializer> studDetails = dbUtil.borrowedStudDisplay();

		// place students in the request attribute
		request.setAttribute("BorStudDetails", studDetails);

		// send to jsp page: view-bor-students.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/view-bor-students.jsp");
		dispatcher.forward(request, response);

	}

	private void getBorrowedBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get Borrowed Books from database (db util)
		List<DetailsInitializer> bookDetails = dbUtil.borrowedBookDisplay();

		// place books in the request attribute
		request.setAttribute("BorBookDetails", bookDetails);

		// send to jsp page: view-bor-books.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/view-bor-books.jsp");
		dispatcher.forward(request, response);

	}

	private void validateBorrowDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get all the details bid,sid and lid and validate them from dbUtil
		int bid = Integer.parseInt(request.getParameter("bid"));
		int sid = Integer.parseInt(request.getParameter("sid"));
		int lid = Integer.parseInt(request.getParameter("lid"));
		DetailsInitializer di = new DetailsInitializer(bid, sid, lid);
		boolean result = dbUtil.validateIds(di);
		boolean instock = true;
		// check if stock is not 0.
		instock = dbUtil.getStock(bid);
		// if stock is non-zero and ids entered are valid then execute if statement
		if (result == true && instock == true) {
			try {
				addBorrowDetails(sid, bid, lid, request, response);
				success(response,"lib");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			goToBorrowPage(request, response,"borrow");//redirects to borrow page
		}
	}

	private void addBorrowDetails(int sid, int bid, int lid, HttpServletRequest request, HttpServletResponse response)
			throws ParseException, ServletException, IOException {
		// This method fills Borrowed_Students table and Borrowed_Books table
		// creating borrow_date and return_date columns data to add to table
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// Date Format

		Calendar c = Calendar.getInstance();// get current Date from inbuilt Calendar class
		c.add(Calendar.DAY_OF_MONTH, 21);
		String return_date = sdf.format(c.getTime());
		LocalDate l = LocalDate.now();
		String borrow_date = l.toString();
		System.out.println(return_date);
		/*
		 * Date d1=sdf.parse(return_date);//Parses text from the beginning of the given
		 * string to produce a date Date d2=sdf.parse(borrow_date);//Parses text from
		 * the beginning of the given string to produce a date
		 */

		try {
			// step-1:add-student to Borrowed-Students first.
			dbUtil.addStudentBorrowed(sid, bid, borrow_date, return_date, lid);
			goToBorrowPage(request, response,"borrow");
		} catch (SQLIntegrityConstraintViolationException e) {
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);

		}
	}

	private void getBookDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get Books from database (db util)
		List<DetailsInitializer> books = dbUtil.bookDisplay();

		// place student in the request attribute
		request.setAttribute("BookDetails", books);

		// send to jsp page: view-books.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/view-books.jsp");
		dispatcher.forward(request, response);

	}

	private void loadBookDetails(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		String bname = request.getParameter("book_name");
		String author = request.getParameter("auth_name");
		int stock = Integer.parseInt(request.getParameter("stock"));
		DetailsInitializer di = new DetailsInitializer(id, bname, author, stock);
		try {
			dbUtil.addBook(di);
			success(response,"lib");
		} catch (SQLIntegrityConstraintViolationException e) {
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void deleteBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("BookId"));
		dbUtil.deleteBook(id);
		getBookDetails(request, response);
		success(response,"lib");
	}

	private void deleteLibrarian(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("libId"));
		dbUtil.deleteLibrarian(id);
		getLibrarianDetails(request, response);
		success(response,"admin");
	}

	private void getLibrarianDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get Librarian from database (db util)
		List<DetailsInitializer> theLib = dbUtil.libDisplay();

		// place student in the request attribute
		request.setAttribute("LibDetails", theLib);

		// send to jsp page: view.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/view-admin.jsp");
		dispatcher.forward(request, response);

	}

	private void loadLibrarianDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		String fname = request.getParameter("first_name");
		String lname = request.getParameter("last_name");
		String email = request.getParameter("email");
		DetailsInitializer di = new DetailsInitializer(id, fname, lname, email);
		dbUtil.addLibrarian(di);
		success(response,"admin");
	}
	private void success(HttpServletResponse response,String from) throws IOException
	{
		PrintWriter out = response.getWriter();
		out.println("<h1>Success</h1>");
		if(from.equals("lib"))
		out.println("<a href='librarian-operations.jsp'><button>Back To Menu</button></a>");
		else if(from.equals("admin"))
		out.println("<a href='admin-operations.jsp'><button>Back To Menu</button></a>");
	}
	private void goToBorrowPage(HttpServletRequest request, HttpServletResponse response,String from)
	{
		String page="";
		if(from.equals("borrow"))
		 page = "borrow-book.jsp";
		else if(from.equals("return"))
		 page="return-book.jsp";
		RequestDispatcher rd = request.getRequestDispatcher("/" + page);
		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

}
