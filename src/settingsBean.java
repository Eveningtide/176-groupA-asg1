import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

@ManagedBean
@SessionScoped
public class settingsBean {

	private BigDecimal food,entertainment, clothing;
	private Calendar start, end;
	private DataSource ds;
	
	public BigDecimal getFood() {
		return food;
	}
	public void setFood(BigDecimal food) {
		this.food = food;
	}
	public BigDecimal getEntertainment() {
		return entertainment;
	}
	public void setEntertainment(BigDecimal entertainment) {
		this.entertainment = entertainment;
	}
	public BigDecimal getClothing() {
		return clothing;
	}
	public void setClothing(BigDecimal clothing) {
		this.clothing = clothing;
	}
	public Calendar getStart() {
		return start;
	}
	public void setStart(Calendar start) {
		this.start = start;
	}
	public Calendar getEnd() {
		return end;
	}
	public void setEnd(Calendar end) {
		this.end = end;
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
 
			//if (username.equals(dataset.getString("MODEL")) && password.equals(dataset.getString("FOOT_TYPE"))) 
				result = "Welcome";
		}
		
		return result;
	}
	
}
