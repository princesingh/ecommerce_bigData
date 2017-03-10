package com.ecommerce.profile;

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
 * Servlet implementation class profile
 */
@WebServlet("/profile")
public class profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public profile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		Cluster cluster = Cluster.builder().addContactPoint("localhost").build();
		Session session = cluster.connect("ecommerce");
		
		HttpSession ses = request.getSession(false);
		if(null != ses.getAttribute("user-login-email")){
			String mail = ses.getAttribute("user-login-email").toString();
			
			String res_data = "select * from user where email ='"+mail+"'";

			ResultSet result = session.execute(res_data);
			
			Row one_row = result.one();
			
			String name = one_row.getString("name");
			int location = one_row.getInt("location");
			String bfs_dfs_result = "select  count(*) from user_action where user_email='"+mail+"' limit 10";
			
			ResultSet bfs_dfs = session.execute(bfs_dfs_result);
			
			Row fs = bfs_dfs.one();
			long count = fs.getLong("count");
			int bfs_count = 0;

			String location_trend = "";
			String dfs_pro = "";
			String bfs_pro = "";
			String prev_amount = "";
			
			if(count>0){
				String prev_prod = "select product_id from user_action where user_email='"+mail+"' limit 10";
				ResultSet prev = session.execute(prev_prod);
				
				while(!prev.isExhausted()){
					Row d = prev.one();
					int id_all = d.getInt("product_id");
					String query = "select * from product where id="+id_all;
					ResultSet new_result = session.execute(query);
					Row new_row = new_result.one();
					
					int price = new_row.getInt("price");
					int old_price = price+price*new_row.getInt("discount")/100;
					String product = new_row.getString("title");
					int id = new_row.getInt("id");
					String img_loc = new_row.getString("img");
					
					prev_amount += "<div  onclick=item_popup("+id+") style='background-color:#F7F7F7;width:249px;height:282px;float:left;box-shadow: 0 2px 2px 0 rgba(0,0,0,0.16);margin-left:12px;margin-top:10px;position:relative;cursor:pointer'><div style='padding:4px;'><div style='width:100%;height:auto;float:left'><div style='width:100%;text-align:center;height:200px'><img src='"+img_loc+"' style='max-width:210px;max-height:220px' /></div><div style='font-size:14px;color:#333333;border-bottom:1px solid green;width:100%;float:left;margin-top:21px'>"+product+"</div><div style='width:100%;float:left'><div style='width:40%;float:left'>&#8377;"+price+"</div><div style='width:40%;float:left'><span style='text-decoration:line-through'>&#8377;"+old_price+"</span></div></div></div></div></div>";
				
					
					int dfs = new_row.getInt("dfs_list");
					
					int bfs = new_row.getInt("bfs_list");
					
					String gender = new_row.getString("gender");
					
					String dfs_query = "select * from product where gender='"+gender+"' and dfs_list ="+dfs+" limit 3 allow filtering";
					
					String bfs_query = "select * from product where gender='"+gender+"' and bfs_list ="+bfs+" limit 5 allow filtering";
					
					ResultSet dfs_product = session.execute(dfs_query);
					ResultSet bfs_product = session.execute(bfs_query);
					
					while(!bfs_product.isExhausted() && bfs_count < 25){
						
						Row bfs_each = bfs_product.one();
						int b_price = bfs_each.getInt("price");
						String b_title = bfs_each.getString("title");
						String b_img = bfs_each.getString("img");
						int b_discount = b_price+b_price*bfs_each.getInt("discount")/100;
						
						int b_id = bfs_each.getInt("id");
						
						
						if(b_id != id){
							bfs_pro +="<div onclick=item_popup("+b_id+") style='background-color:#F7F7F7;width:249px;height:282px;float:left;box-shadow: 0 2px 2px 0 rgba(0,0,0,0.16);margin-left:12px;margin-top:10px;position:relative;cursor:pointer'><div style='padding:4px;'><div style='width:100%;height:auto;float:left'><div style='width:100%;text-align:center;height:200px'><img src='"+b_img+"' style='max-width:210px;max-height:220px' /></div><div style='font-size:14px;color:#333333;border-bottom:1px solid green;width:100%;float:left;margin-top:21px'>"+b_title+"</div><div style='width:100%;float:left'><div style='width:40%;float:left'>&#8377;"+b_price+"</div><div style='width:40%;float:left'><span style='text-decoration:line-through'>&#8377;"+b_discount+"</span></div></div></div></div></div>";
							bfs_count++;
						}
					}
					while(!dfs_product.isExhausted() && bfs_count < 25){
						
						Row dfs_each = dfs_product.one();
						int d_price = dfs_each.getInt("price");
						String d_title = dfs_each.getString("title");
						String d_img = dfs_each.getString("img");
						int d_discount = d_price+d_price*dfs_each.getInt("discount")/100;
						
						int d_id = dfs_each.getInt("id");
						
						
						if(d_id != id){
							dfs_pro +="<div onclick=item_popup("+d_id+") style='background-color:#F7F7F7;width:249px;height:282px;float:left;box-shadow: 0 2px 2px 0 rgba(0,0,0,0.16);margin-left:12px;margin-top:10px;position:relative;cursor:pointer'><div style='padding:4px;'><div style='width:100%;height:auto;float:left'><div style='width:100%;text-align:center;height:200px'><img src='"+d_img+"' style='max-width:210px;max-height:220px' /></div><div style='font-size:14px;color:#333333;border-bottom:1px solid green;width:100%;float:left;margin-top:21px'>"+d_title+"</div><div style='width:100%;float:left'><div style='width:40%;float:left'>&#8377;"+d_price+"</div><div style='width:40%;float:left'><span style='text-decoration:line-through'>&#8377;"+d_discount+"</span></div></div></div></div></div>";
							bfs_count++;
						}
					}
				}
				
			}
			//display popular product;
			String trend_product = "select * from product_history where location = "+ location+" limit 30";
			
			ResultSet result_trend = session.execute(trend_product);
			
			while(!result_trend.isExhausted()){
				Row trend = result_trend.one();
				
				int new_ids = trend.getInt("product_id");
				
				String each_product = "select id,title,img,price,discount,dfs_list,bfs_list,location from product where id= "+new_ids;
				
				ResultSet result2 = session.execute(each_product);
				
				Row data = result2.one();
				
				String img_new = data.getString("img");
				int price_new = data.getInt("price");
				int old_price_trend = price_new+ data.getInt("discount")/100*price_new;
				String title_trend = data.getString("title");
				location_trend += "<div onclick=item_popup("+new_ids+")  style='background-color:#F7F7F7;width:249px;height:282px;float:left;box-shadow: 0 2px 2px 0 rgba(0,0,0,0.16);margin-left:12px;margin-top:10px;position:relative;cursor:pointer'><div style='padding:4px;'><div style='width:100%;height:auto;float:left'><div style='width:100%;text-align:center;height:200px'><img src='"+img_new+"' style='max-width:210px;max-height:220px' /></div><div style='font-size:14px;color:#333333;border-bottom:1px solid green;width:100%;float:left;margin-top:21px'>"+title_trend+"</div><div style='width:100%;float:left'><div style='width:40%;float:left'>&#8377;"+price_new+"</div><div style='width:40%;float:left'><span style='text-decoration:line-through'>&#8377;"+old_price_trend+"</span></div></div></div></div></div>";
			}
			
			
		out.println("<html>"
					+"<head>"
					+"<title>Welcome To Delta Sell</title>"
						+"<link rel='stylesheet' href='index.css' type='text/css' />"
						+"<script src='index.js' type='text/javascript' ></script>"
					    +"<script type='text/javascript' src='https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js'></script>"
					    +"<script type='text/javascript' src='jquery.flexslider-min.js'></script>"
					    +"<meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no'/>"
					
					+"</head>"
					+"<body style='padding:0px;margin:0px;width:100%;float:left;min-width:600px' onload=get_num()>"
						+"<div style='width:100%;float:left;'>"
							+"<div style='width:100%;float:left;'>"
								+"<div style='width:100%;float:left;'>"
									+"<header style='width:100%;float:left;min-width:600px;position:fixed;z-index:100'>"
										+"<div style='width:100%;float:left;background-color:rgb(228,10,70);height:60px;'>"
											+"<div style='width:95%;margin:0 auto'>"
												+"<div style='width:100%;float:left;height:60px'>"
													+"<div style='width:100%;float:left'>"
														+"<div style='width:15%;float:left;'>"
															+"<div style='Width:auto;height:60px;'>"
																+"<a href='index.jsp'><img src='shopav.png' style='width:auto;height:60px;'/></a>"
															+"</div>"
														+"</div>"
														+"<div style='Width:60%;float:left;margin-top:2px;'>"
															+"<div style='width:90%;float:left;padding-top:10px'>"
																+"<div style='width:100%;float:left;'>"
																	+"<div style='width:75%;float:left'>"
																		+"<input type='text' id='q' style='width:100%;height:37px;float:left;padding:6px' placeholder='Search Over 10,000 Products Here' />"
																	+"</div>"
																		+"<div onclick=get_search_data() style='cursor:pointer;min-width:15%;text-align:center;float:left;background:none repeat scroll 0% 0% rgb(51, 51, 51);color:white;border-radius:0px 3px 3px 0px !important;padding:10px;margin-top:1px;margin-left:-3px;'>"
																			+"<span style='font-size:12px;'>Search</span>"
																		+"</div>"
																	+"</div>"
																+"</div>"
															+"</div>"
														+"<div style='Width:auto;float:right;;margin-top:4px;'>"
															+"<div onclick=show_id_cart() id='_click' class='_click' style='width:auto;float:left;position:relative' >"
																+"<div style='text-align:center'>"
																	+"<img src='cart.png' style='width:32px;height:32px;' />"
																+"</div>"
																+"<div style='text-align:center' >"
																	+"Cart   (<span id='n_cart' > 0 </span>)"
																+"</div>"
																+"<div style='position:absolute;display:none;' id='show_cart'>"
																	+"<div style='padding:4px;width:200px;height:auto;background-color:rgb(228,247,247);margin-left:-152px' id='display_cart'></div>"
																+"</div>"
															+"</div>"
															+"<div id='_clcik1' class='_click' style='width:auto;float:left;margin-left:20px;cursor:pointer;position:relative' onmouseover=show_id('logout_display') onmouseout=hide_id('logout_display')>"
																+"<div style='text-align:center'>"
																	+"<img src='/ecommerce/images/bird.ico' style='width:32px;height:32px;' />"
																+"</div>"
																+"<div style='text-align:center'>"
																	+name.substring(0, 6)
																+"</div>"
																+"<div id='logout_display' style='display:none'>"
																+"<div style='position:absolute;background-color:rgb(228,247,247);padding:6px;margin-left:-16px;margin-top:2px'>"
																	+ "LogOut"
																+ "</div>"
																+ "</div>"
																
															+"</div>"
														+"</div>"
													+"</div>"
												+"</div>"
											  +"</div>"
											+"</div>"
										+"</header>"
									
									
									+"<article style='width:100%;flat:left;z-index:10'>"
										+"<div style='width:100%;float:left;background-color:white;'>"
											+"<div style='width:95%;margin:0 auto'>"
												+"<div style='width:100%;float:left;position:relative;margin-top:70px;'>"
													+"<div style='width:100%;float:left'>"
														+"<div style='width:15%;float:left;position:fixed;margin-top:'>"
															+"<div style='width:95%;float:left;'>"
																+"<table style='width:90%;padding:3px;background-color:rgb(237, 237, 237);box-shadow:0 2px 2px 0 rgba(0,0,0,0.16)'>"
																	+"<tr style=';cursor:pointer'>"
																		+"<td style='padding:9px;width:36px;'><img src='women.jpeg' style='width:30px;height:30px;border-radius:50%' /></td>"
																		+"<td style='padding:5px 5px 0px 0px;font-family:tahoma'>Women</td>"
																	+"</tr>"
																	+"<tr style='cursor:pointer;'>"
																		+"<td style='padding:9px;width:36px;'><img src='men.png' style='width:30px;height:30px;border-radius:50%' /></td>"
																		+"<td style='padding:5px 5px 0px 0px;font-family:tahoma'>Men</td>"
																	+"</tr>"
																	+"<tr style='cursor:pointer;margin-top:3px'>"
																		+"<td style='padding:9px;width:36px;'><img src='book.png' style='width:30px;height:30px;border-radius:50%' /></td>"
																		+"<td style='padding:5px 5px 0px 0px;font-family:tahoma'>Books</td>"
																	+"</tr>"
																	+"<tr style='cursor:pointer;margin-top:3px'>"
																		+"<td style='padding:9px;width:36px;'><img src='sports.png' style='width:30px;height:30px;border-radius:50%' /></td>"
																		+"<td style='padding:5px 5px 0px 0px;font-family:tahoma'>Sports</td>"
																	+"</tr>"
																+"</table>"
															+"</div>"
															+"<div style='width:95%;float:left;height:auto;position:relative'>"
																+"<h3>Today's Offer</h3>"
																+"<div style='width:90%;background-color:rgb(237, 237, 237);box-shadow:0 2px 2px 0 rgba(0,0,0,0.16);margin-top:3px;height:244px'>"
																	
																+"</div>"
															+"</div>"
															+"<div style='width:95%;float:left;height:auto;position:relative'>"
																+"<h3>More Categories</h3>"
																+"<div style='width:90%;background-color:rgb(237, 237, 237);margin-top:3px;height:244px'>"
																	
																+"</div>"
															+"</div>"
														+"</div>"
														+"<div style='width:85%;float:left;margin-left:15%;' id='display_result'>");
														if(prev_amount != ""){
														out.print("<h1>Previously Watched</h1>"
															+ "<div style='width:100%;float:left;margin-top:20px;border:10px solid rgb(237,237,237);padding-bottom:10px'>"
															+prev_amount
															+"</div>"
															+"</div><div style='margin-left:15%;margin-top:-2px;'><img src='/ecommerce/images/shadow_bottom.png' style='width:100%;' /></div>");
														}
														
														out.println("<div style='width:85%;float:left;margin-left:15%;' id='display_result'>");
														if(dfs_pro != "" || bfs_pro != ""){
														out.print("<h1>Related To Previous Experience</h1>"
															+ "<div style='width:100%;float:left;margin-top:20px;border:10px solid rgb(237,237,237);padding-bottom:10px'>"
															+bfs_pro+dfs_pro
															+"</div>"
															+"</div><div style='margin-left:15%;margin-top:-2px;'><img src='/ecommerce/images/shadow_bottom.png' style='width:100%;' /></div>");
														
														}
														
														out.print("<div style='width:85%;float:left;margin-left:15%;' id='display_result'>");
														if(location_trend != null){
															out.print("<h1>Based On Your Location Trending</h1>"
															+ "<div style='width:100%;float:left;margin-top:20px;border:10px solid rgb(237,237,237);padding-bottom:10px'>"
															+location_trend
															+"</div><div style='margin-left:15%;margin-top:-2px;'><img src='/ecommerce/images/shadow_bottom.png' style='width:100%;' /></div>");
														}
														out.print("</div>"
														+"</div>"
													+"</div>"
												+"</div>"
											+"</div>"
										+"</article>"
									+"<div  id='get_active' style='width:100%;height:100%;position:fixed;z-index:2000;background-color:rgba(247,247,247,0.65);display:none;''></div>"
									+"<div style='width:100%;height:100%;position:fixed;z-index:2500;margin-top:100px;display:none' id='product_display'>"
										+"<div style='width:60%;margin:0 auto;'>"
											+"<div style='width:100%;float:left;background-color:rgb(247,247,247);border:4px solid lightgrey'>"
												+"<div id='product_box'></div>"
											+"</div>"
										+"</div>"
									+"</div>"
									+"<footer></footer>"
									+"</div>"
								+"</div>"
							+"</div>"
						+"</body>"
					+"</html>");
		}else{
			response.sendRedirect("http://localhost:8080/ecommerce/index.jsp");
		}
		
	}

}
