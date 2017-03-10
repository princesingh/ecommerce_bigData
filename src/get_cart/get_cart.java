package get_cart;

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
 * Servlet implementation class get_cart
 */
@WebServlet("/get_cart")
public class get_cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public get_cart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		String data = request.getParameter("data").toString();
		HttpSession ses = request.getSession(false);
		String user_name;
		if(null != ses.getAttribute("user-login-email")){
			user_name = ses.getAttribute("user-login-email").toString();
		}else{
			String ipAddress =  request.getRemoteAddr();
			user_name = "user"+ipAddress.replace(':', '-');
		}
		String query1 = "select count(*) from cart where user_email='"+user_name+"'";
		String query2 = "select * from cart where user_email='"+user_name+"'";
		Cluster cluster = Cluster.builder().addContactPoint("localhost").build();
		Session session = cluster.connect("ecommerce");
		ResultSet result1 = session.execute(query1);
		ResultSet result2 = session.execute(query2);
		
		Row row1 = result1.one();
		
		long num = row1.getLong("count");
		if(data.equalsIgnoreCase("num")){
			out.println(num);
		}else{
			if(num>0){
				while(!result2.isExhausted()){
					Row row2 = result2.one();
					int id = row2.getInt("product_id");
					String query = "select * from product where id="+id;
					ResultSet result = session.execute(query);
					
					Row row = result.one();
					
					String title = row.getString("title");
					int price = row.getInt("price");
					String img = row.getString("img");
					
					out.println("<div id='remove"+id+"' style='border:1px solid grey;margin-top:5px;padding:3px;border-radius:3px;'><table><tr><td rowspan='2'><img src='"+img+"' style='width:46px;height:auto;'</td><td colspan='2'  style='font-size:12px'>"+title.substring(0, 20)+"...</td</tr><tr><td  style='font-size:12px'>"+price+"</td><td><a style='color:blue;cursor:pointer' onclick=delete_me("+id+")>Remove</a></td></tr></table></div>");
				}
				out.println("<div style='color:blue'>Go To Your Cart</div>");
			}
		}
		
	}

}
