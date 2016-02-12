import javax.annotation.Resource;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class LoginBean {
 
	private String username;
	private String password;
	private DataSource ds; 
 

	public String getUsername() {
		return username;
	}
 
	public void setUsername(String username) {
		this.username = username;
	}
 

	public String getPassword() {
		return password;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}
 
	public String authenticate() throws SQLException, ServletException {

		String result = null; 
	
		try {
			Context context = (Context) new InitialContext().lookup("java:comp/env");
			ds = (DataSource) context.lookup("jdbc/MyDB3"); 
			if (ds == null) throw new ServletException("'datasource' is null"); 
		} catch (NamingException e) {
			e.printStackTrace();
		}

		Connection con = ds.getConnection();
 
		if(con==null)
			throw new SQLException("Can't get database connection");
 
		PreparedStatement ps = con.prepareStatement("select MODEL, FOOT_TYPE from Sport_Shoes"); 
 
		//get customer data from database
		ResultSet dataset =  ps.executeQuery();
		
		result = "Error";
		while(dataset.next()){
 
			if (username.equals(dataset.getString("MODEL")) && password.equals(dataset.getString("FOOT_TYPE"))) 
				result = "Welcome";
		}
		
		return result;
	}
	
	public String go_settings()
	{
		return "Settings";

	}
 
}