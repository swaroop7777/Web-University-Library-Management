package com.abc.librarymgmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class LibraryDBUtil {
	static int stock;
	private DataSource dataSource;
	public LibraryDBUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void addLibrarian(DetailsInitializer di) {
		try {
			Connection con = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			// Establish connection
			con = dataSource.getConnection();
			st = con.prepareStatement("insert into librarian(id,first_name,last_name,email) values(?,?,?,?)");
			st.setInt(1, di.getId());
			st.setString(2, di.getFirst_name());
			st.setString(3, di.getLast_name());
			st.setString(4, di.getEmail());
			st.executeUpdate();
			close(st, rs, con);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void deleteLibrarian(int theLibId) {
		try {
			Connection con = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			// Establish connection
			con = dataSource.getConnection();
			st = con.prepareStatement("delete from librarian where id=?");
			st.setInt(1, theLibId);
			st.executeUpdate();
			close(st, rs, con);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public List<DetailsInitializer> libDisplay() {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		ArrayList<DetailsInitializer> theLib = new ArrayList<DetailsInitializer>();
		try {
			// Establish connection
			con = dataSource.getConnection();
			st = con.prepareStatement("select * from librarian");
			rs = st.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				String email = rs.getString("email");
				DetailsInitializer di = new DetailsInitializer(id, first_name, last_name, email);
				theLib.add(di);

			}

			close(st, rs, con);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return theLib;
	}
	private static void close(Statement st, ResultSet rs, Connection con) {
		try {
			if (rs != null) {
				rs.close();
			}

			if (st != null) {
				st.close();
			}

			if (con != null) {
				con.close(); // doesn't really close it ... just puts back in connection pool
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void addBook(DetailsInitializer di) throws SQLIntegrityConstraintViolationException {
		
			Connection con = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			Statement stmt=null;
			// Establish connection
			try {
			con = dataSource.getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery("select * from books_stock where book_id="+di.getBid());
			if(rs.next()) 
			{
			if(rs.getInt(1)==di.getId()) {
				stmt=null;
				throw new SQLIntegrityConstraintViolationException();
			   }
			}
			else {
			st=con.prepareStatement("insert into books_stock(book_id,instock) values(?,?)");
			st.setInt(1, di.getId());
			st.setInt(2, di.getStock());
			
			st.execute();
			st = con.prepareStatement("insert into books(bid,b_name,author) values(?,?,?)");
			st.setInt(1, di.getId());
			st.setString(2, di.getFirst_name());//get first name here gets book name
			st.setString(3, di.getLast_name());//get last name gets author name
			st.execute();
			close(st, rs, con);
			 }
			}
			catch(SQLIntegrityConstraintViolationException e) {
				throw new SQLIntegrityConstraintViolationException();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		
	}

	public List<DetailsInitializer> bookDisplay() {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		ArrayList<DetailsInitializer> books = new ArrayList<DetailsInitializer>();
		try {
			// Establish connection
			con = dataSource.getConnection();
			st = con.prepareStatement("select * from books");
			rs = st.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("bid");
				String book_name = rs.getString("b_name");
				String author = rs.getString("author");
				DetailsInitializer di = new DetailsInitializer(id, book_name, author, 0);
				books.add(di);

			}

			close(st, rs, con);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}

	public void deleteBook(int id) {
		try {
			Connection con = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			// Establish connection
			con = dataSource.getConnection();
			st = con.prepareStatement("delete from books_stock where book_id=?");
			st.setInt(1, id);
			st.executeUpdate();
			close(st, rs, con);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean validateIds(DetailsInitializer di) {
		try {
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			// Establish connection
			con = dataSource.getConnection();
			st=con.createStatement();
			String sql1="select * from books where bid="+di.getBid();
			String sql2="select * from student where id="+di.getId();
			String sql3="select * from librarian where id="+di.getLid();
			if((st.executeQuery(sql1).next()) && (st.executeQuery(sql2).next()) && (st.executeQuery(sql3).next()) )
			{
			  close(st, rs, con);	
			}
			else {
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public void addBookBorrowed(int sid, int bid, String borrow_date, String return_date, int lid) throws SQLIntegrityConstraintViolationException{

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			
			// Establish connection
			con = dataSource.getConnection();
			st=con.createStatement();
			rs=st.executeQuery("select * from borrowed_books where book_id="+ bid + " and sid=" +sid);
			if(rs.next()) {
				if(rs.getInt(1)==sid && rs.getInt(2)==bid)
				{
				close(st,rs,con);
				throw new SQLIntegrityConstraintViolationException();
				}
				close(st,rs,con);
			}
			else {
			con = null;
			PreparedStatement st1 = null;
			con= dataSource.getConnection();
			//insertion into borrowed_books table
			st1 = con.prepareStatement("insert into borrowed_books(sid,book_id,borrow_date,return_date,lib_id) values(?,?,?,?,?)");
			st1.setInt(1, sid);
			st1.setInt(2,bid);
			st1.setString(3,borrow_date);
			st1.setString(4,return_date);
			st1.setInt(5,lid);
			st1.executeUpdate();
			close(st, rs, con);
			//step-3:decrement the instock value
		    decrementStock(bid);
			}
		}
		catch(SQLIntegrityConstraintViolationException e) {
			throw new SQLIntegrityConstraintViolationException();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean getStock(int bid){
		
		try {
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			// Establish connection
			con = dataSource.getConnection();
			String sql="select * from books_stock where book_id="+bid;
			st = con.createStatement();
			rs=st.executeQuery(sql);
			if(rs.next())
			{
			int instock=rs.getInt("instock");
			System.out.println(instock);
			if(instock>0)
			{
				stock=instock;
				close(st, rs, con);
				return true;
			}
			}
			close(st,rs,con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void addStudentBorrowed(int sid,int bid,String borrow_date,String return_date,int lid) throws SQLIntegrityConstraintViolationException{
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs=null;
		Statement stmt=null;
		String first_name;
		String last_name;
		String email;
		try {
			// get db connection
			con = dataSource.getConnection();
			
			//fetch the student details
			String sql="select * from borrowed_students where id="+"(select id from student where id="+sid+")";
			stmt = con.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				if(rs.getInt(1)==sid)
				{
           /*if id is matched , check if book id also matches , if matches then its a redundant
            * entry so throw SQLIntegrityEx. if BookID is different , it means same student
            * is trying to take another book which i must allow.*/
				addBookBorrowed(sid,bid,borrow_date,return_date,lid);
				close(stmt,rs,con);
				}
				close(stmt,rs,con);	
			}
			else {
			con = dataSource.getConnection();
			//create sql statement for selecting first_name,last_name,email.. 
			String sqlSelect="select * from student where id="+sid;
			stmt = con.createStatement();
			rs=stmt.executeQuery(sqlSelect);
			rs.absolute(1);
			first_name=rs.getString("first_name");
			last_name=rs.getString("last_name");
			email=rs.getString("email");
			stmt=null;
			
			// create sql statement for insert
			sql = "insert into borrowed_students "
					   + "(id,first_name, last_name, email) "
					   + "values (?, ?, ?, ?)";
			// set the param values for the student into borrowed_students table
			st = con.prepareStatement(sql);
			st.setInt(1, sid);
			st.setString(2,first_name);
			st.setString(3, last_name);
			st.setString(4,email);
			// execute sql insert
			st.execute();
			close(st, rs, con);
			addBookBorrowed(sid,bid,borrow_date,return_date,lid);
		  }
		} 
		catch(SQLIntegrityConstraintViolationException e) {
			throw new SQLIntegrityConstraintViolationException();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<DetailsInitializer> borrowedBookDisplay() {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		ArrayList<DetailsInitializer> booksDetails = new ArrayList<DetailsInitializer>();
		try {
			// Establish connection
			con = dataSource.getConnection();
			st = con.prepareStatement("select * from borrowed_books");
			rs = st.executeQuery();
			while (rs.next()) {
				int sid = rs.getInt("sid");
				int book_id = rs.getInt("book_id");
				String borrow_date = rs.getString("borrow_date");
				String return_date = rs.getString("return_date");
				int lid=rs.getInt("lib_id");
				DetailsInitializer di = new DetailsInitializer(sid, book_id, borrow_date, return_date,lid);
				//above constructor values refers to id,first_name,last_name,email in constructor fields
				booksDetails.add(di);
			}

			close(st, rs, con);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return booksDetails;
		
	}

	public List<DetailsInitializer> borrowedStudDisplay() {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		ArrayList<DetailsInitializer> studDetails = new ArrayList<DetailsInitializer>();
		try {
			// Establish connection
			con = dataSource.getConnection();
			st = con.prepareStatement("select * from borrowed_students");
			rs = st.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				String email = rs.getString("email");
				DetailsInitializer di = new DetailsInitializer(id, first_name, last_name, email);
				//above constructor values refers to id,first_name,last_name,email in constructor fields
				studDetails.add(di);
			}

			close(st, rs, con);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studDetails;
	}

	public void decrementStock(int bid) {
		try {
			Connection con = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			// Establish connection
			con = dataSource.getConnection();
			st = con.prepareStatement("update books_stock set instock=? where book_id=?");
			st.setInt(1,stock-1);
			st.setInt(2,bid);
			st.executeUpdate();
			close(st, rs, con);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	public boolean validateStudentBorrowed(int sid,int bid) {
		try {
			Connection con = null;
			Statement st = null;
			ResultSet rs = null;
			// Establish connection
			con = dataSource.getConnection();
			st=con.createStatement();
			String sql="select * from borrowed_books where sid="+sid;
			rs=st.executeQuery(sql);
			if(rs.next())
			{
			  //atleast an entry is there in borrowed_books table for student sid.
			  close(st,rs,con);
			  return true;
			}
			else {
				//no row is there for the entered sid in 'borrowed_books table'.
				//Check if he is in 'borrowed_students table' and delete him from there.
				bid=0;
				deleteStudentAndBook(sid,bid);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public DetailsInitializer getDueDetails(int sid, int bid) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		// Establish connection
		try {
		con = dataSource.getConnection();
		st=con.createStatement();
		String sql="select * from borrowed_books where sid="+sid+" and book_id="+bid;
		rs=st.executeQuery(sql);
			if(rs.next())
			{
			//an entry is there in borrowed_books table for student sid and book_id bid.
			  String bor_date=rs.getString("borrow_date");
			  String ret_date=rs.getString("return_date");
	          DetailsInitializer di=new DetailsInitializer(bor_date,ret_date,null);
			  close(st,rs,con);
			  deleteStudentAndBook(sid,bid);
			  incrementBookStock(bid);
			  return di;
			}
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void incrementBookStock(int bid) {
		try {
			Connection con = null;
			PreparedStatement st = null;
			ResultSet rs = null;
			// Establish connection
			con = dataSource.getConnection();
			String sql="select * from books_stock where book_id="+bid;
			st = con.prepareStatement(sql);
			rs=st.executeQuery();
			if(rs.next())
			{
			stock=rs.getInt("instock");
			stock=stock+2;//Incrementing by 2 and calling decrementStock(bid) will result in effective
			//increment of stock by 1.
			decrementStock(bid);
			}
		
	}
		catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }

	private void deleteStudentAndBook(int sid, int bid) {
		try {
			//deletion related connections if he borrowed any book lately.
			Connection con_del = null;
			PreparedStatement st_del = null;
			ResultSet rs_del = null;
			// Establish connection for deletion from borrowed_books table
			con_del = dataSource.getConnection();
			if(bid!=0)
			{
			st_del = con_del.prepareStatement("delete from borrowed_books where sid="+sid+" and book_id="+bid);
			st_del.executeUpdate();
			close(st_del, rs_del, con_del);
			}
			else {
			/*bid is set to 0 in validateStudentBorrowed(sid,bid) function in else loop 
			because it entered else loop meant that there is no sid in borrowed_books table
			if its there in borrowed_students then its not feasible thing to keep it. 
			instead, just delete it if present. 'executeUpdate()' will return 0 if there is no sid
			related to that in borrowed_students table.count of deleted rows if present.*/
			st_del = con_del.prepareStatement("delete from borrowed_students where id="+sid);
			st_del.executeUpdate();
			close(st_del, rs_del, con_del);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}				
}

