import javax.annotation.Resource;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import com.mysql.jdbc.Statement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
		
		/* [IMPORTANT] you can't use the same connection string twice, every time you change the statement the string name has to change too.
		 * So you can't use jdbcUrl, jdbcUrl2 with altered content, you will have to use something new eg: jdbcURL4 , 5, 6, etc. !!!!!!!!!!!! --> check if true
		 * Using command Alt+Shift+X, R to run server gives an error / right click to start server is fine 
		 */
		

		String result = null; 
		Connection con = null;
		
		// Read RDS connection information from the environment
		/*String dbName = "shouldibuyRDB";
		String userName = "eveningtideRDB";
		String dbpassword = "eveningtideRDB1";
		String hostname = "shouldibuyRDB.cxrttqsvigc5.us-west-2.rds.amazonaws.com";
		String port = "3306";
		String jdbcUrl = "jdbc:mysql://" + hostname + ":" +
		    port + "/" + dbName + "?user=" + userName + "&password=" + dbpassword;*/ 
		
		//Databases on Amazon
		//String jdbcUrl2 = "jdbc:mysql://shouldibuyRDB2.cxrttqsvigc5.us-west-2.rds.amazonaws.com:3306/shouldibuy?user=eveningtideRDB2&password=eveningtideRDB21"; //has no data
		String jdbcUrl = "jdbc:mysql://shouldibuyRDB.cxrttqsvigc5.us-west-2.rds.amazonaws.com:3306/shouldibuyRDB?user=eveningtideRDB&password=eveningtideRDB1"; // has data
	
		
		// Load the JDBC driver
		try {
			System.out.println("Loading driver...");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Cannot find the driver in the classpath!", e);
		}

		
	    try {
	        con = DriverManager.getConnection(jdbcUrl);
	        System.out.println("connection made");
	        
	        //PreparedStatement ps = con.prepareStatement("SELECT user_name, u_password FROM users");
	        PreparedStatement ps = con.prepareStatement("SELECT user_name, u_password FROM users");
	        
	        //get customer data from database
			ResultSet dataset =  ps.executeQuery();
			
			result = "Error";  //incorrect login info entered 
			while(dataset.next()){
				
				System.out.println(dataset.getString("user_name")+ dataset.getString("u_password"));
				System.out.println("--------------------------------------------");
				System.out.println(username + password);
				if (username.equals(dataset.getString("user_name")) && password.equals(dataset.getString("u_password"))) 
					result = "Welcome"; //correct login info entered 
			}
			

	      } catch (SQLException ex) {
	        // Handle any errors
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	      } finally {
	           System.out.println("Closing the connection.");
	          if (con != null) try { con.close(); } catch (SQLException ignore) {}
	      }
	    
	    System.out.println(result);
	    return result;
	}
	

}
