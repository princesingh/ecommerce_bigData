package sign_in_up;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

/**
 * Servlet implementation class sign
 */
@WebServlet("/sign")
public class sign extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sign() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		
		//connecting to cluster
		Cluster cluster = Cluster.builder().addContactPoint("localhost").build();
		Session session = cluster.connect("ecommerce");
		
		//setting up session variable
		HttpSession ses = request.getSession(true);
		
		String verify = request.getParameter("verify");
		if(verify.equals("login")){
			
			String user_name = request.getParameter("user_name");
			String user_pass = request.getParameter("user_pass");
			if(user_name.length() >= 6 && user_pass.length() >= 6){
				
				String login = "select password from user where email='"+user_name+"'";
				ResultSet result = session.execute(login);
				if(result.one().getString("password").equals(user_pass)){
					
					ses.setAttribute("user-login-email", user_name);
					out.println("Successful");
					
				}else{
					out.println("ERROR: 102 Invalid Credential");
				}
			}else{
				out.println("ERROR: 101 Invalid Credential");
			}
		}else if(verify.equals("signup")){
			
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			
			String passcode = request.getParameter("passcode");
			
			String year = request.getParameter("year");
			String date = request.getParameter("date");
			String month = request.getParameter("month");
			
			String b_date = year+"-"+month+"-"+date;
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date1 = sdf.format(new Date());
			
			
			String gender = request.getParameter("gender");
			
			String user_query = "insert into user(name,email,gender,b_date,city,date_of_joining,password) values ('"+name+"','"+email+"','"+gender+"','"+b_date+"','kochi','"+date1+"','"+passcode+"')";
			
			session.execute(user_query);
			ses.setAttribute("user-login-email", email);
			out.println("Successful");
		}else{
			out.println("ERROR: 100 Invalid Credential");
		}
	}

}
