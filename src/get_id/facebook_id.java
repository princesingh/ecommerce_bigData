package get_id;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * Servlet implementation class facebook_id
 */
@WebServlet("/get_facebook_id")
public class facebook_id extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public facebook_id() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String s_value = request.getParameter("facebook_id");
		
		Cluster cluster = Cluster.builder().addContactPoint("localhost").build();
		Session session = cluster.connect("ecommerce");
		
		HttpSession ses = request.getSession(true);
		String user_name;
		user_name = ses.getAttribute("user-login-email").toString();
		
		String query = "insert into social_connect (email,id) values('"+user_name+"','"+s_value+"')";
		
		session.execute(query);
		
		out.println("Suceeded");
		
	}

}
