package delete_me;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

/**
 * Servlet implementation class delete
 */
@WebServlet("/delete")
public class delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public delete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String s_value = request.getParameter("value");
		int value = Integer.parseInt(s_value);
		
		Cluster cluster = Cluster.builder().addContactPoint("localhost").build();
		Session session = cluster.connect("ecommerce");
		
		String user_name;
		HttpSession ses = request.getSession(false);
		
		if(null != ses.getAttribute("user-login-email")){
			user_name = ses.getAttribute("user-login-email").toString();
		}else{
			String ipAddress =  request.getRemoteAddr();
			user_name = "user"+ipAddress.replace(':', '-');
			
		}
		
		String check_query = "select count(*) from cart where user_email = '"+user_name+"' and product_id="+value;
		
		ResultSet result = session.execute(check_query);
		Row row1 = result.one();
		Long data = row1.getLong("count");
		String insert_query;
		long delta =0;
		if(data > 0){
			insert_query = "delete from cart where user_email='"+user_name+"' and product_id="+value;
			session.execute(insert_query);
			delta++;
		}else{
		}
		out.println(data-delta+1);
	}

}
