package add;

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
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

/**
 * Servlet implementation class add
 */
@WebServlet("/add")
public class add extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public add() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		}else{

			Date dNow = new Date( );
		    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
			insert_query = "insert into cart (user_email,product_id,date) values('"+user_name+"',"+value+",'"+ft.format(dNow)+"')";
			session.execute(insert_query);
			delta++;
		}
		out.println(data+delta);
	}

}
