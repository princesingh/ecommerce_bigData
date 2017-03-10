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

		HttpSession ses = request.getSession(false);
		String user_name;
		int header_set = 0;
		if(null != ses.getAttribute("user-login-email")){
			user_name = ses.getAttribute("user-login-email").toString();
			header_set = 1;
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
		int[] srcLocation = new int[100];
		int iota =0;
		if(num > 0){
			while(!result2.isExhausted()){
				Row row2 = result2.one();
				int id = row2.getInt("product_id");
				String query = "select * from product where id="+id;
				ResultSet result = session.execute(query);
				
				Row row = result.one();
				
				String title = row.getString("title");
				int price = row.getInt("price");
				String img = row.getString("img");
				srcLocation[iota] = row.getInt("location");
				int loc = srcLocation[iota];
				out.println("<div id='remove"+id+"' style='border:1px solid grey;margin-top:5px;padding:3px;border-radius:3px;'><table><tr><td rowspan='2'><img src='"+img+"' style='width:46px;height:auto;'</td><td colspan='2'  style='font-size:12px'>"+title.substring(0, 20)+"...</td</tr><tr><td  style='font-size:12px'>"+price+"</td><td><a style='color:blue;cursor:pointer' onclick=delete_me("+id+")>Remove</a></td></tr></table></div>");
				
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
			            // Mark the picked vertex as processed
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
			 
			        // print the constructed distance array
			        for (int i = 0; i < V; i++)
			        	out.println("\t "+dist[i]);
			    
				iota++;
			}
			
		}
		
		
	}
	
	

}