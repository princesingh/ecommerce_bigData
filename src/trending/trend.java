package trending;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

/**
 * Servlet implementation class trend
 */
@WebServlet("/trend")
public class trend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public trend() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		
		Cluster cluster = Cluster.builder().addContactPoint("localhost").build();
		Session session = cluster.connect("ecommerce");
		
		String loc = request.getParameter("location");
		int location = Integer.parseInt(loc);
		
		String trend_product = "select * from product_history where location = "+ location+" limit 14";
		
		ResultSet result = session.execute(trend_product);
		
		while(!result.isExhausted()){
			Row trend = result.one();
			
			int id = trend.getInt("product_id");
			
			String each_product = "select id,title,img,price,discount,dfs_list,bfs_list,location from product where id= "+id;
			
			ResultSet result2 = session.execute(each_product);
			
			Row data = result2.one();
			
			String img = data.getString("img");
			int price = data.getInt("price");
			int old_price = price+ data.getInt("discount")/100*price;
			String title = data.getString("title");
			out.println("<div onclick=item_popup("+id+") style='background-color:#F7F7F7;width:252px;height:282px;float:left;box-shadow: 0 2px 2px 0 rgba(0,0,0,0.16);margin-left:12px;margin-top:10px;position:relative;cursor:pointer'><div style='padding:4px;'><div style='width:100%;height:auto;float:left'><div style='width:100%;text-align:center;height:200px'><img src='"+img+"' style='max-width:210px;max-height:220px' /></div><div style='font-size:14px;color:#333333;border-bottom:1px solid green;width:100%;float:left;margin-top:21px'>"+title+"</div><div style='width:100%;float:left'><div style='width:40%;float:left'>&#8377;"+price+"</div><div style='width:40%;float:left'><span style='text-decoration:line-through'>&#8377;"+old_price+"</span></div></div></div></div></div></a>");
		}
	}

}
