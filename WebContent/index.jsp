
<%
HttpSession ses = request.getSession(false);
if(null != ses.getAttribute("user-login-email")){
	response.sendRedirect("http://localhost:8080/ecommerce/profile");
}else{
	//stay here
}
%>

<html>
	<head>
		<title>Welcome To Delta Sell</title>
		<link rel="stylesheet" href="index.css" type="text/css" />
		<script src="index.js" type="text/javascript" ></script>
	    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
	    <script type="text/javascript" src="jquery.flexslider-min.js"></script>
	    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"/>
	    <script>
		    var $ = jQuery.noConflict();
		    $(window).load(function() {
		    $('.flexslider').flexslider({
		          animation: "fade"
		    });
	
		    $(function() {
		    	$('.show_menu').click(function(){
		    			$('.menu').fadeIn();
		    			$('.show_menu').fadeOut();
		    			$('.hide_menu').fadeIn();
		    	});
		    	$('.hide_menu').click(function(){
		    			$('.menu').fadeOut();
		    			$('.show_menu').fadeIn();
		    			$('.hide_menu').fadeOut();
		    		});
		    	});
		      });

	</script>
	</head>
	<body style="padding:0px;margin:0px;width:100%;float:left;min-width:600px" onload=get_trending() >
		<div style="width:100%;float:left;">
			<div style="width:100%;float:left;">
				<div style="width:100%;float:left;">
					<!-- Header Start -->
					<header style="width:100%;float:left;min-width:600px;position:fixed;z-index:100">
						<div style="width:100%;float:left;background-color:rgb(228,10,70);height:60px;">
							<div style="width:95%;margin:0 auto">
								<div style="width:100%;float:left;height:60px">
									<div style="width:100%;float:left">
										<div style="width:15%;float:left;">
											<div style="Width:auto;height:60px;">
												<a href="index.jsp"><img src="shopav.png" style="width:auto;height:60px;"/></a>
											</div>
										</div>
										<div style="Width:60%;float:left;margin-top:2px;">
											<div style="width:90%;float:left;padding-top:10px">
												<div style="width:100%;float:left;">
													<div style="width:75%;float:left">
														<input type="text" id="q" style="width:100%;height:37px;float:left;padding:6px" placeholder="Search Over 10,000 Products Here" />
													</div>
													<div onclick=get_search_data() style="cursor:pointer;min-width:15%;text-align:center;float:left;background:none repeat scroll 0% 0% rgb(51, 51, 51);color:white;border-radius:0px 3px 3px 0px !important;padding:10px;margin-top:1px;margin-left:-3px;">
														<span style="font-size:12px;">Search</span>
													</div>
												</div>
											</div>
										</div>
										<div style="Width:auto;float:right;;margin-top:4px;">
										
											<div onclick=show_id_cart() id='_click' class='_click' style="width:auto;float:left;position:relative" > 
												<div style="text-align:center">
													<img src="cart.png" style="width:32px;height:32px;" />
												</div>
												<div style="text-align:center" >
													Cart   (<span id='n_cart' > 0 </span>)
												</div>
												<div style='position:absolute;display:none;' id='show_cart'>
													<div style='padding:4px;width:200px;height:auto;background-color:rgb(228,247,247);margin-left:-153px' id='display_cart'></div>
												</div>
											</div>
											<div class='_click' style="width:auto;float:left;margin-left:20px;cursor:pointer" onclick=display_signin_box()>
												<div style="text-align:center">
													<img src="signin.png" style="width:32px;height:32px;" />
												</div>
												<div style="text-align:center">
													Sign In
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</header>
					
					
					<article style="width:100%;flat:left;z-index:10">
						<div style="width:100%;float:left;background-color:white;">
							<div style="width:95%;margin:0 auto">
								<div style="width:100%;float:left;position:relative;margin-top:70px;">
									<div style="width:100%;float:left">
										<div style="width:15%;float:left;position:fixed;margin-top:">
											<div style="width:95%;float:left;">
												<table style="width:90%;padding:3px;background-color:rgb(237, 237, 237);box-shadow:0 2px 2px 0 rgba(0,0,0,0.16)">
													<tr style=";cursor:pointer">
														<td style="padding:9px;width:36px;"><img src="women.jpeg" style="width:30px;height:30px;border-radius:50%" /></td>
														<td style="padding:5px 5px 0px 0px;font-family:tahoma">Women</td>
													</tr>
													<tr style="cursor:pointer;">
														<td style="padding:9px;width:36px;"><img src="men.png" style="width:30px;height:30px;border-radius:50%" /></td>
														<td style="padding:5px 5px 0px 0px;font-family:tahoma">Men</td>
													</tr>
													<tr style="cursor:pointer;margin-top:3px">
														<td style="padding:9px;width:36px;"><img src="book.png" style="width:30px;height:30px;border-radius:50%" /></td>
														<td style="padding:5px 5px 0px 0px;font-family:tahoma">Books</td>
													</tr>
													<tr style="cursor:pointer;margin-top:3px">
														<td style="padding:9px;width:36px;"><img src="sports.png" style="width:30px;height:30px;border-radius:50%" /></td>
														<td style="padding:5px 5px 0px 0px;font-family:tahoma">Sports</td>
													</tr>
												</table>
											</div>
											<div style="width:95%;float:left;height:auto;position:relative">
												<h3>Today's Offer</h3>
												<div style="width:90%;background-color:rgb(237, 237, 237);box-shadow:0 2px 2px 0 rgba(0,0,0,0.16);margin-top:3px;height:244px">
													
												</div>
											</div>
											<div style="width:95%;float:left;height:auto;position:relative">
												<h3>More Categories</h3>
												<div style="width:90%;background-color:rgb(237, 237, 237);margin-top:3px;height:244px">
													
												</div>
											</div>
										</div>
										<div style="width:85%;float:left;margin-left:15%;" id="display_result">
											<div class="slider_container"style="margin-left:12px;width:99%;">
												<div class="flexslider">
											      <ul class="slides">
											    	<li>
											    		<a><img id="city_trending_img_1" src="images/slider/slide1.jpg" alt="" title="" style="width:100%;height:340px"/></a>
											    		<div class="flex-caption">
										                     <div id="city_trending_data_1"  class="caption_title_line"><h2>Books</h2><p>Get books at compared Price.</p></div>
										                </div>
											    	</li>
											    	<li>
											    		<a><img id="city_trending_img_2"  src="images/slider/slide2.jpg" alt="" title="" style="width:100%;height:340px"/></a>
											    		<div class="flex-caption">
										                     <div id="city_trending_data_2" class="caption_title_line"><h2>Men Fashion</h2><p>The latest trend of dresses</p></div>
										                </div>
											    	</li>
											    	<li>
											    		<a><img id="city_trending_img_3"  src="images/slider/slide3.jpg" alt="" title="" style="width:100%;height:340px"/></a>
											    		<div class="flex-caption">
										                     <div id="city_trending_data_3" class="caption_title_line"><h2>Sports</h2><p>Get sport equipments and gym equipment</p></div>
										                </div>
											    	</li>
											    	<li>
											    		<a><img id="city_trending_img_4"  src="images/slider/slide4.jpg" alt="" title="" style="width:100%;height:340px"/></a>
											    		<div class="flex-caption">
										                     <div id="city_trending_data_4" class="caption_title_line"><h2>Women Fashion</h2><p>Get Latest trend in dressing in women fashion.</p></div>
										                </div>
											    	</li>
											    </ul>
											  </div>
										   </div>
											<h1><strong>Trending</strong></h1>
											<div id="display_trending" style="width:100%;float:left;margin-top:20px;">
												
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- State Wise Recommendation -->
						<!-- All Trending Product Section Wise -->
					</article>
					
					<!-- Active For Every Overlay -->
					<div id="get_active" style="width:100%;height:100%;position:fixed;z-index:2000;background-color:rgba(247,247,247,0.65);display:none;" onclick=hide_all()></div>
					
					<!-- For Login --Send Data Using Given Parameter -->
					<div style="width:100%;height:100%;position:absolute;z-index:2500;display:none;" id='login_box'>
						<div style="width:250px;margin:0 auto">
							<div style="width:100%;float:left;;margin-top:130px;border:1px solid rgba(228,10,70,0.78);padding:10px;background-color:rgb(247,247,247);border-radius:4px;">
								<div style="width:100%;float:left;font-size:24px">
									Login Credential
								</div>
								<div style="width:100%;float:left;font-size:14px;color:brown;cursor:pointer;text-align:right" onclick=display_signup_box()>
									SignUp Here...
								</div>
								<div style="width:100%;float:left;font-size:12px;color:red;display:none" id='login_error'>
									Login Failed
								</div>
								<div style="width:100%;float:left;margin-top:10px;">
									<input type="text" id="login_name" style="width:100%;float:left;padding:9px;outline:none;border:none;border-radius:2px;" placeholder="E-mail/Mobile">
								</div>
								<div style="width:100%;float:left;margin-top:10px;">
									<input type="password" id="login_pass"style="width:100%;float:left;padding:9px;outline:none;border:none;border-radius:2px;" placeholder="PassWord" />
								</div>
								<div style="width:100%;float:left;margin-top:10px">
									<div style="width:50%;margin:0 auto;">
										<div style="width:100%;float:left;" onclick=allow_login()>
											<button style="width:100%;float:left;border:none;padding:9px;cursor:pointer;">Submit</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<!-- For SignUp --Send Data Using Given Parameter -->
					<div style="width:100%;height:100%;position:absolute;display:none;z-index:2500;" id='signup_box'>
						<div style="width:250px;margin:0 auto">
							<div style="width:100%;float:left;;margin-top:80px;border:1px solid rgba(228,10,70,0.78);padding:10px;background-color:rgb(247,247,247);border-radius:4px;">
								<div style="width:100%;float:left;font-size:24px">
									SignUp...
								</div>
								<div style="width:100%;float:left;font-size:14px;text-align:right;color:brown;cursor:pointer" onclick=display_signin_box()>
									Click Here To Login
								</div>
								<div id='signup_error' style="width:100%;float:left;font-size:12px;color:red;display:none">
									Error Message
								</div>
								<div style="width:100%;float:left;margin-top:10px;">
									Name
								</div>
								<div style="width:100%;float:left;">
									<input type="text" name="user_name" id="user_name" style="width:100%;float:left;padding:9px;outline:none;border:none;border-radius:2px;" placeholder="Name" />
								</div>
								<div style="width:100%;float:left;margin-top:10px;">
									Email
								</div>
								<div style="width:100%;float:left;">
									<input type="text" name="email" id="user_mail" style="width:100%;float:left;padding:9px;outline:none;border:none;border-radius:2px;" placeholder="Email" />
								</div>
								<div style="width:100%;float:left;margin-top:10px;">
									Date Of Birth
								</div>
								<div style="width:100%;float:left;">
									<input type="date" id="b_date" style="width:100%;float:left;padding:9px;outline:none;border:none;border-radius:2px;" placeholder="Date Of Birth">
								</div>
								<div style="width:100%;float:left;margin-top:10px;">
									Gender
								</div>
								<div style="width:100%;float:left;">
									<label style="width:25%;float:left;padding:9px;outline:none;border:1px solid rgba(228,10,70,0.5);border-radius:2px;">
										<input type="radio" name="gender" id="gender" value="men" />Male
									</label>
									<label style="width:25%;float:left;padding:9px;outline:none;border:1px solid rgba(228,10,70,0.5);border-radius:2px;">
										<input type="radio" name="gender" id="gender" value="women" />Female
									</label>
									<label style="width:25%;float:left;padding:9px;outline:none;border:1px solid rgba(228,10,70,0.5);border-radius:2px;">
										<input type="radio" name="gender" id="gender" value="other" />Other
									</label>
								</div>
								<div style="width:100%;float:left;margin-top:10px;">
									Password
								</div>
								<div style="width:100%;float:left;">
									<input type="password" name="user_pass" id="user_pass" style="width:100%;float:left;padding:9px;outline:none;border:none;border-radius:2px;" placeholder="PassWord" />
								</div>
								<div style="width:100%;float:left;margin-top:10px;">
									Confirn Password
								</div>
								<div style="width:100%;float:left;">
									<input type="password" name="user_pass" id="conf_pass" style="width:100%;float:left;padding:9px;outline:none;border:none;border-radius:2px;" placeholder="PassWord" />
								</div>
								<div style="width:100%;float:left;margin-top:10px">
									<div style="width:50%;margin:0 auto;">
										<div onclick=allow_signup()  style="width:100%;float:left;">
											<button style="width:100%;float:left;border:none;padding:9px;cursor:pointer;">Submit</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<!-- Display Product -->
					<div style="width:100%;height:100%;position:fixed;z-index:2500;margin-top:100px;display:none" id="product_display">
						<div style="width:60%;margin:0 auto;">
							<div style="width:100%;float:left;height:auto;background-color:rgb(247,247,247);border:4px solid lightgrey">
								<div id="product_box"></div>
							</div>
						</div>
					</div>
					<footer></footer>
				</div>
			</div>
		</div>
	</body>
</html>