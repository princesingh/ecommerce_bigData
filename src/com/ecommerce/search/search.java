package com.ecommerce.search;

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
 * Servlet implementation class search
 */
@WebServlet("/search")
public class search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String q = request.getParameter("data");
		PrintWriter out = response.getWriter();
		
		String gender;
		if(q.indexOf("women") >=0 || q.indexOf("woman") >=0){
			gender = "women";
		}else{
			gender = "men";
		}
		
		Cluster cluster = Cluster.builder().addContactPoint("localhost").build();
		Session session = cluster.connect("ecommerce");
		
		String query = "select id,title,img,price,discount,dfs_list,bfs_list,location from product where gender ='"+gender+"' allow filtering";
		
		ResultSet result = session.execute(query);

		q = q.replaceAll(gender, "");
		
		q = q.trim();
		q = q.toLowerCase();
		String[] new_data = q.split(" ");
		int new_len = new_data.length;
		for(int j=0;j<new_len;j++){
			out.println(new_data[j]);
		}
		out.println("in <span style='color:red'>"+gender+"</span> category");
		out.println("<hr>");
		int res = 0;
		while((!result.isExhausted()) && (res < 50)){
			//out.println("name:- "+result.one().getString("title"));
			int count = 0;
			int i=0;
			Row row = result.one();
			
			//fetching results
			//int id = row.getInt("id");
			
			String product = row.getString("title");
			
			String img_loc = row.getString("img");
			int price = row.getInt("price");
			int id = row.getInt("id");
			int old_price = price + price * row.getInt("discount")/100;
			
			product = product.toLowerCase();
			while(i<new_len){
				if((product.indexOf(new_data[i])) > 0){
					count ++;
				}
				i++;
			}

			if(count>=2){
				out.print("<div onclick=item_popup("+id+") style='background-color:#F7F7F7;width:245px;height:282px;float:left;box-shadow: 0 2px 2px 0 rgba(0,0,0,0.16);margin-left:12px;margin-top:10px;position:relative;cursor:pointer'><div style='padding:4px;'><div style='width:100%;height:auto;float:left'><div style='width:100%;text-align:center;height:200px'><img src='"+img_loc+"' style='max-width:210px;max-height:220px' /></div><div style='font-size:14px;color:#333333;border-bottom:1px solid green;width:100%;float:left;margin-top:21px'>"+product+"</div><div style='width:100%;float:left'><div style='width:40%;float:left'>&#8377;"+price+"</div><div style='width:40%;float:left'><span style='text-decoration:line-through'>&#8377;"+old_price+"</span></div></div></div></div></div>");
				res++;
			}
		}
        
 
        
	}

}
