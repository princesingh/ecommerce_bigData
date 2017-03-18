package facebook;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.Post;

/**
 * Servlet implementation class facebook
 */
@WebServlet("/facebook")
public class facebook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public facebook() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("deprecation")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		String accessToken = "EAACEdEose0cBAAiRlAfcPlJxZCzh8sCzY3vMv0SQwLM3SsZAn85O8ZAcRQHZCC42PGZAI0z8qs4QLuPYFbvFZAWEJS9UZA9ZCuTZCriLsASYWAuv9Tb9HMluI1iMrGkRmgzmtl3mX9LxMBOfgOmE8L0yw5DxGOCLb3kyDl7w3PGk1wZB7uiwiDrUZCvxHZALtBko8C4ZD";
		
		FacebookClient fbc = new DefaultFacebookClient(accessToken);
		
		Connection<Post> result = fbc.fetchConnection("me/feed", Post.class);
		
		Cluster cluster = Cluster.builder().addContactPoint("localhost").build();
		
		Session session = cluster.connect("ecommerce");
		

		for(List<Post> page: result){
			
			for(Post apost : page){
				out.println(apost.getId()+ "/likes");

				String postId = apost.getId();
				Connection<NamedFacebookType> commentConnection = fbc.fetchConnection(postId + "/likes", NamedFacebookType.class, Parameter.with("limit", 10));

				int personalLimit = 50;
				out.println("Id: " + postId+ " loading...");
				
				for (List<NamedFacebookType> commentPage : commentConnection) {
					
					for (NamedFacebookType comment : commentPage) {
					
					  	 
						String like_id = comment.getId().toString();
						
					    personalLimit--;
					    
					    // break both loops
					    if (personalLimit == 0) {
					       return;
					    }
					    
					    //check if it is existing
					    String check = "select count(*) from social_like where user_id='"+like_id+"' and media_post_id='"+postId+"'";
					    ResultSet check_count = session.execute(check);
					    Row check_one = check_count.one();
					    long c = check_one.getLong("count");
					    
					    if(c>0){
					    	
					    }else{
					    	String insert_query = "insert into social_like (user_id,media_post_id) values('"+like_id+"','"+postId+"')";
					    	session.execute(insert_query);
					    }
					    
					    out.println("Id: " + like_id + " is successfully imported"); 
				    
					}
				}
				
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
