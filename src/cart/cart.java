package cart;

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
 * Servlet implementation class cart
 */
@WebServlet("/cart")
public class cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final int V=18;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public cart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();

		HttpSession ses = request.getSession(true);
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
		String location[] = new String[]{"Sri Nagar","Shimla","Delhi","JaiPur","Gandhi Nagar","Mumbai","Bhopal","Raipur","Patna","Itanager","Imphal","Ranchi","Kolkata","Bhuvneshwar","Hyderabad","Banglore","Chennai","Kochi"};
		Row row1 = result1.one();
		int total_cost=0;
		int saving=0;
		int delivery = 0;
		long num = row1.getLong("count");
		int[] srcLocation = new int[100];
		int iota =0;
		if(num > 0){
out.println("<html>"
		+"<head>"
		+"<title>Welcome To Delta Sell</title>"
			+"<link rel='stylesheet' href='index.css' type='text/css' />"
			+"<script src='index.js' type='text/javascript' ></script>"
		    +"<script type='text/javascript' src='https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js'></script>"
		    +"<script type='text/javascript' src='jquery.flexslider-min.js'></script>"
		    +"<meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no'/>"
		    + "<style> #display_image{ width:60%;float:left;margin-top:100px;} </style>"
		
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
											+"</div>"
										+"</div>"
									+"</div>"
								  +"</div>"
								+"</div>"
							+"</header>"
							+ "<div id='display_result' style='width:55%;margin-left:33px;border:4px solid lightgrey; padding:4px;position:relative;height:auto;float:left;margin-top:75px'>");
			while(!result2.isExhausted()){
				Row row2 = result2.one();
				int id = row2.getInt("product_id");
				String query = "select * from product where id="+id;
				ResultSet result = session.execute(query);
				
				Row row = result.one();
				
				String title = row.getString("title");
				int price = row.getInt("price");
				total_cost = total_cost+price;
				int discount = price*100/(100 - row.getInt("discount"));
				saving = saving+discount;
				
				String img = row.getString("img");
				srcLocation[iota] = row.getInt("location");
				int loc = srcLocation[iota];
				
				//Here Id Dijkstra's Algorithm    1  2  3  4  5      6    7   8    9     0    1    2     3    4   5
			    /* Let us create the example graph discussed above */
			       int graph[][] = new int[][]{{0,15,20,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000},
												{15,0,10,1000,1000,1000,1000,40,35,1000,1000,1000,1000,1000,1000,1000,1000,1000},
												{20,10,0,10,1000,17,18,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000},
												{1000,1000,10,0,7,1000,7,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000},
												{1000,1000,1000,7,0,8,17,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000},
												{1000,1000,17,1000,8,0,17,1000,1000,1000,1000,1000,1000,1000,1000,13,1000,13},
												{1000,1000,18,7,17,17,0,1000,17,1000,1000,1000,1000,1000,10,23,1000,1000},
												{1000,40,1000,1000,1000,1000,1000,0,1000,1000,1000,7,1000,9,15,1000,1000,1000},
												{1000,35,1000,1000,1000,1000,17,1000,0,25,1000,6,7,1000,1000,1000,1000,1000},
												{1000,1000,1000,1000,1000,1000,1000,1000,25,0,5,1000,18,1000,1000,1000,1000,1000},
												{1000,1000,1000,1000,1000,1000,1000,1000,1000,5,0,1000,1000,1000,1000,1000,1000,1000},
												{1000,1000,1000,1000,1000,1000,1000,7,6,1000,1000,0,1000,5,1000,1000,1000,1000},
												{1000,1000,1000,1000,1000,1000,1000,1000,7,18,1000,1000,0,8,1000,1000,1000,1000},
												{1000,1000,1000,1000,1000,1000,1000,9,1000,1000,1000,5,8,0,1000,15,18,1000},
												{1000,1000,1000,1000,1000,1000,10,15,1000,1000,1000,1000,1000,1000,0,1000,15,1000},
												{1000,1000,1000,1000,1000,13,23,1000,1000,1000,1000,1000,1000,15,1000,0,4,1000},
												{1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,18,15,4,0,5},
												{1000,1000,1000,1000,1000,13,1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,5,0}};
			        int src =loc-1;
			        int dist[] = new int[V]; // The output array. dist[i] will hold
			                                 // the shortest distance from src to i
			        
			        int[] visited_Array = new int[V];
			        
			        // sptSet[i] will true if vertex i is included in shortest
			        // path tree or shortest distance from src to i is finalized
			        Boolean sptSet[] = new Boolean[V];
			 
			        // Initialize all distances as INFINITE and stpSet[] as false
			        for (int i = 0; i < V; i++)
			        {
			            dist[i] = Integer.MAX_VALUE;
			            sptSet[i] = false;
			        }
			 
			        // Distance of source vertex from itself is always 0
			        dist[src] = 0;
			 
			        // Find shortest path for all vertices
			        for (int count = 0; count < V-1; count++)
			        {
			            // Pick the minimum distance vertex from the set of vertices
			            // not yet processed. u is always equal to src in first
			            // iteration.
			            int min = Integer.MAX_VALUE, min_index=-1;
			 
				        for (int v = 0; v < V; v++)
				            if (sptSet[v] == false && dist[v] <= min)
				            {
				                min = dist[v];
				                min_index = v;
				            }
				 
			            int u = min_index;
			            visited_Array[count] = u+1;
			            // Mark the picked vertex as processremoveed
			            sptSet[u] = true;
			 
			            // Update dist value of the adjacent vertices of the
			            // picked vertex.
			            for (int v = 0; v < V; v++)
			 
			                // Update dist[v] only if is not in sptSet, there is an
			                // edge from u to v, and total weight of path from src to
			                // v through u is smaller than current value of dist[v]
			                if (!sptSet[v] && graph[u][v]!=0 &&
			                        dist[u] != Integer.MAX_VALUE &&
			                        dist[u]+graph[u][v] < dist[v])
			                    dist[v] = dist[u] + graph[u][v];
			        }
			        delivery = delivery+ dist[17];
			        out.println("<div id='remove"+id+"' style='border:1px solid grey;margin-top:5px;padding:3px;border-radius:3px;width:99%;float:left'><div style='width:100%;float:left'><div style='float:left;width:22%'><div style='float:left;width:100%;'><img src='"+img+"' style='width:160px;height:auto;border-radius:3px;' /></div></div><div style='float:left;width:65%;'><div style='width:100%;float:left;font-size:24px;color:grey'>"+title+"</div><div style='width:100%;font-size:18px;margin-top:50px'><strong> &#8377; "+price +"  </strong>+ &#8377; "+dist[17] +"  </div><div style='width:100%;font-size:18px;margin-top:10px'><table><tr><td>Available At:</td><td>"+location[loc-1]+" Store</td></tr><tr><td>Delivery To:</td><td>"+location[17]+"</td></tr></table></div><div style=';margin-top:50px'><a style='width:100%;color:blue;cursor:pointer;font-size:16px' onclick=delete_me1("+id+")>Remove</a></div></div></div></div>");
					
				iota++;
			}
					
					
					
					out.println("</div>");
					
					out.println("<div style='width:30%;right:40px;position:fixed;margin-top:75px;border:4px solid grey; padding:4px;'>"
							+"<div style='width:100%;float:left;font-size:24px;font-family: Roboto,Arial,sans-serif:letter-spacing:0'>"
							+"<div style='width:100%;float:left;'>Price Detail</div><hr>"
							+ "<table border='1' style='border:1px solid lightgrey;width:100%;float:left;'>"
							+ "<tr><td style='padding:8px'>Price ("+num+" items)</td><td style='padding:8px'>&#8377; "+(total_cost+saving)+"</td></tr>"
							+ "<tr><td style='padding:8px'>Delivery Charge</td><td style='padding:8px'>&#8377; "+delivery+"</td></tr>"
							+ "<tr><td style='padding:8px'>Total Saving</td><td style='padding:8px'>&#8377; "+saving+"</td></tr>"
							+ "<tr><td style='padding:8px'>Amount Payable</td><td style='padding:8px'>&#8377; "+total_cost+"</td></tr>"
							
							+ "</table>"
							+"<div style='width:100%;float:left;font-size:18px;margin-top:20px;font-family:tahoma;'>"
							+ "<div style='padding:4px;float:right;cursor:pointer;border-radius:3px;border:1px solid lightgrey;margin-left:20px;'><a href='http://localhost:8080/ecommerce/cart'>Proceed To Checkout</a></div><div style='cursor:pointer;padding:4px;float:right;border-radius:3px;border:1px solid lightgrey'>Continue Shopping</div>"
							+ "</div>"
							+ "</div>"
							
							+ "</div>"
							+ "</div>"
							+"<div style='width:30%;right:40px;position:fixed;margin-top:75px;border:4px solid grey; padding:4px;top:270px;'><img src='images/Thanks.png' style='width:100%;height:auto;' /></div>"
					+"</div>"
				+"</div>"
			+"</body>"
		+"</html>");
		}else{
			response.sendRedirect("http://localhost:8080/ecommerce/index.jsp");
		}
		
		
	}
	
	

}