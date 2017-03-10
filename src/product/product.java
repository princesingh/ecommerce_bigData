package product;

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
 * Servlet implementation class product
 */
@WebServlet("/product")
public class product extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public product() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		
		
		String s_id = request.getParameter("id");
		int id = Integer.parseInt(s_id);
		
		String query = "select * from product where id="+id;
		Cluster cluster = Cluster.builder().addContactPoint("localhost").build();
		Session session = cluster.connect("ecommerce");
		
		ResultSet result = session.execute(query);
		
		Row row = result.one();
		
		HttpSession ses = request.getSession(false);
		
		String user_name;
		if(null != ses.getAttribute("user-login-email")){  
			user_name = ses.getAttribute("user-login-email").toString();
			//insert the data into his database
		}else{  
			String ipAddress =  request.getRemoteAddr();
			user_name = "user"+ipAddress.replace(':', '-');
		}  
		String check_req = "select * from user_action where product_id= "+id+" and user_email='"+user_name+"'";
		ResultSet check_more = session.execute(check_req);
		Row check = check_more.one();
		
		if(check!= null){
		}else{
			
			Date dNow = new Date( );
		    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		    
			String query2 = "insert into user_action (product_id,user_email,date) values("+id+",'"+user_name+"','"+ft.format(dNow)+"')";
			
			session.execute(query2);
			//String d3 = "select * from product_history where location= 3 and product_id=2"
			//if no 
			//add
			//else skip
		}
		String title = row.getString("title");
		int price = row.getInt("price");
		int discount = price + price*row.getInt("discount")/100;
		
		int bfs_list = row.getInt("bfs_list");
		int dfs_list = row.getInt("dfs_list");
		String gender= row.getString("gender");
		
		String img = row.getString("img");
		
		int location = row.getInt("location");
		location --;
		
		String category = row.getString("cat");

		String[] location_new = new String[10];
		location_new[0] = "Sri Nagar";
		location_new[1] = "Shimla";
		location_new[2] = "Gadhi Nagar";
		location_new[3] = "Bhopal";
		location_new[4] = "Patna";
		location_new[5] = "Imphal";
		location_new[6] = "Mumbai";
		location_new[7] = "Banglore";
		location_new[8] = "Chennai";
		location_new[9] = "Trivendrum";
		
		out.print("<div style='width:100%;float:left;position:relative'><div style='padding:10px'><div style='width:100%;float:left'><div style='width:400px;min-height:400px;float:left'><img src='"+img+"' style='max-width:400px;max-height:400px' /></div> <div style='width:300px;float:left;min-height:400px;'><div style='width:100%;float:left;font-size:20px;color:green'>"+title+"</div><hr><div style='width:100%;float:left;font-size:18px;margin-top:40px;color:#999'><div><div>Availabe At:-"+location_new[location]+" store</div><div>Product Category:-"+category+"</div></div></div><div style='width:100%;float:left;font-size:18px;margin-top:40px'><div style='width:48%;float:left;text-align:center'>New Price: &#8377;"+price+"</div><div style='width:48%;float:left;text-decoration:line-through;text-align:center'>Old Price: &#8377;"+discount+"</div></div><hr><div style='width:100%;float:left;text-align:center;margin-top:140px'><span style='border:3px solid green;padding:8px;cursor:pointer' id='cart_action' onclick=add_to_cart("+id+")>Add To Cart</span></div></div></div></div></div></div><hr><br>");
		
		String bfs_query = "select id,img from product where gender='"+gender+"' and bfs_list="+bfs_list+" and cat='"+category+"' allow filtering";
		
		String dfs_query = "select id,img from product where gender='"+gender+"' and dfs_list="+dfs_list+" and cat='"+category+"' allow filtering";
		
		ResultSet bfs_max = session.execute(bfs_query);
		ResultSet dfs_max = session.execute(dfs_query);
		int count = 0;
		while(!bfs_max.isExhausted() && count <7){
			Row bfs = bfs_max.one();
			int new_id = bfs.getInt("id");
			String image = bfs.getString("img");
			if(new_id != id){
				out.print("<div onclick=get_new_product("+new_id+") style='width:80px;height:100px;float:left;margin-left:10px;margin-top:10px;'>"
						+ "<img src='"+image+"' style='height:100%;width:100%;' />"
					+ "</div>");
				count++;
			}
			
		}
		while(!dfs_max.isExhausted() && count <7){
			Row dfs = dfs_max.one();
			int dnew_id = dfs.getInt("id");
			String dimage = dfs.getString("img");
			if(dnew_id != id){
				out.print("<div onclick=get_new_product("+dnew_id+") style='width:80px;height:100px;float:left;margin-left:10px;margin-top:10px;'>"
						+ "<img src='"+dimage+"' style='height:100%;width:100%;' />"
					+ "</div>");
				count++;
			}
			
			
		}
		
	}

}
