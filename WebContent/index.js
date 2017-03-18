function get_search_data(){
	var q = document.getElementById("q").value;
	if(q != ""){
		var se_jx;
		if(window.XMLHttpRequest){
			se_jx = new XMLHttpRequest();
		}else{
			se_jx = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		var reg_exp = /[a-zA-Z0-9 ]+/g;
		q = reg_exp.exec(q);
		
		se_jx.open("GET", "/ecommerce/search?data="+q, true);
		
		se_jx.onreadystatechange = function(){
			
			if(se_jx.status == 200 && se_jx.readyState == 4){
				
				document.getElementById("display_result").innerHTML = se_jx.responseText;
				window.scrollTo(0, 0);
				
			}
		};
		se_jx.send();
	}
}

function display_signin_box(){
	$("#signup_box").hide(500);
	$("#get_active").show(1000);
	$("#login_box").show(1500);
}
function display_signup_box(){
	$("#login_box").hide(500);
	$("#get_active").show(1000);
	$("#signup_box").show(1500);
}

function allow_login(){
	$("#login_error").hide();
	var u = document.getElementById("login_name").value;
	var p = document.getElementById("login_pass").value;
	if(u.length < 6 || p.length < 6){
		$("#login_error").show();
	}else{
		var s_jx;
		if(window.XMLHttpRequest){
			s_jx = new XMLHttpRequest();
		}else{
			s_jx = new ActiveXObject('Microsoft.XMLHTTP');
		}
		param = "user_name="+u+"&user_pass="+p+"&verify=login";
		
		s_jx.open("POST","/ecommerce/sign", true);
		
		s_jx.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		s_jx.setRequestHeader("Contenet-length", param.length);
		s_jx.setRequestHeader("Connetion", "close");
		
		s_jx.onreadystatechange = function(){
			if(s_jx.readyState == 4 && s_jx.status == 200){
				if(s_jx.responseText.trim() == "Successful"){
					window.location = "http://localhost:8080/ecommerce/profile";
				}else{
					$("#login_error").show();
					document.getElementById("login_error").innerHTML = s_jx.responseText;
				}
			}else{
				$("#login_error").show();
				document.getElementById("login_error").innerHTML = s_jx.responseText;
			}
		};
		s_jx.send(param);
	}
	
}

function allow_signup(){
	var name = document.getElementById("user_name").value;
	var mail = document.getElementById("user_mail").value;
	var b_date = document.getElementById("b_date").value;
	
	var pass1 = document.getElementById("user_pass").value;
	var pass2 = document.getElementById("conf_pass").value;
	
	
	var split = b_date.split('-');
	var year = split[0];
	var month = split[1];
	var date = split[2];

	var gender = document.getElementsByName('gender');
    var genValue = false;

    for(var i=0; i<gender.length;i++){
        if(gender[i].checked == true){
            genValue = gender[i].value;  
            break;
        }
    }
    var error_list = "error_list";

	if(name.length >= 6){
	}else{
		error_list = error_list + "\nName length is small";
	}
	
	if( (mail.length >= 6) && (mail.indexOf(".") >= 0) && (mail.indexOf("@") >= 0) ){
	}else{
		error_list = error_list + "\nemail missing parameter";
	}

	if(genValue == 'male' || genValue == 'female' || genValue == 'other'){
	}else{
		error_list = error_list + "\nGender Required";
	}
	
	if(pass1 == pass2){
	}else{
		error_list = error_list + "\nPassword donot match";
	}

	if(error_list.trim() == "error_list"){
		parameter = "name="+name+"&email="+mail+"&passcode="+pass1+"&year="+year+"&date="+date+"&month="+month+"&gender="+genValue+"&verify=signup";
		var sign_jx;

		
		if(window.XMLHttpRequest){
			sign_jx = new XMLHttpRequest();
		}else{
			sign_jx = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		sign_jx.open("POST","/ecommerce/sign",true);
		
		sign_jx.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		sign_jx.setRequestHeader("Content-length", parameter.length);
		sign_jx.setRequestHeader("Connetion", "close");
		

		sign_jx.onreadystatechange = function(){
			if(sign_jx.readyState == 4 && sign_jx.status == 200){
				
				if(sign_jx.responseText.trim() == "Successful"){
					window.location = "http://localhost:8080/ecommerce/profile";
				}else{
					$("#signup_error").show();
					document.getElementById("login_error").innerHTML = sign_jx.responseText;
				}
			}else{
				$("#signup_error").show();
				document.getElementById("signup_error").innerHTML = sign_jx.responseText;
			}
		};
		sign_jx.send(parameter);
	}else{
		$("#signup_error").show();
		document.getElementById("signup_error").innerHTML = error_list;
	}
}

function get_trending(){
	
	var trend_jx;
	if(window.XMLHttpRequest){
		trend_jx = new XMLHttpRequest();
	}else{
		trend_jx = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	trend_jx.open("GET", "/ecommerce/trend?location="+2, true);
	
	trend_jx.onreadystatechange = function(){
		if(trend_jx.status == 200 && trend_jx.readyState == 4){
			
			document.getElementById("display_trending").innerHTML = trend_jx.responseText;
			
		}
	};
	trend_jx.send();
	get_num();
}



function show(id){
	document.getElementById(id).style.display = "block";
	
}
function show_id(id){
	document.getElementById(id).style.display = "block";
	document.getElementById("_clcik1").style.backgroundColor = "rgb(228,247,247)";
}
function hide_id(id){
	document.getElementById(id).style.display = "none";
	document.getElementById("_clcik1").style.backgroundColor = "rgba(228,247,247,0.2)";
}

function item_popup(id){
	var pop_jx;
	if(window.XMLHttpRequest){
		pop_jx = new XMLHttpRequest();
	}else{
		pop_jx = new ActiveXObject('Microsoft.XMLHTTP');
	}
	
	pop_jx.open("GET", "/ecommerce/product?id="+id, false);

	pop_jx.onreadystatechange = function(){
		if(pop_jx.status == 200 && pop_jx.readyState == 4){
			
			$("#get_active").show(500);
			$("#product_display").show(700);

			document.getElementById("product_box").innerHTML = pop_jx.responseText;
		}
	};
	pop_jx.send();
}


function get_new_product(id){
	item_popup(id);
}

function hide_all(){
	alert("I");
}

function add_to_cart(id){
	var add_jx;
	if(window.XMLHttpRequest){
		add_jx = new XMLHttpRequest();
	}else{
		add_jx = new ActiveXObject('Microsoft.XMLHTTP');
	}
	
	add_jx.open("GET", "/ecommerce/add?value="+id, false);
	
	add_jx.onreadystatechange = function(){
		if(add_jx.readyState == 4 && add_jx.status == 200){
			document.getElementById("n_cart").innerHTML = (add_jx.responseText);
		}
	};
	add_jx.send();
	get_num();
}

function get_cart(){
	var cart_jx;
	if(window.XMLHttpRequest){
		cart_jx = new XMLHttpRequest();
	}else{
		cart_jx = new ActiveXObject('Microsoft.XMLHTTP');
	}
	
	cart_jx.open("GET", "/ecommerce/get_cart?data=data", false);
	
	cart_jx.onreadystatechange = function(){
		if(cart_jx.readyState == 4 && cart_jx.status == 200){
			document.getElementById("display_cart").innerHTML = (cart_jx.responseText);
		}
	};
	cart_jx.send();
}
function get_num(){
	var cart_jx1;
	if(window.XMLHttpRequest){
		cart_jx1 = new XMLHttpRequest();
	}else{
		cart_jx1 = new ActiveXObject('Microsoft.XMLHTTP');
	}
	
	cart_jx1.open("GET", "/ecommerce/get_cart?data=num", false);
	
	cart_jx1.onreadystatechange = function(){
		if(cart_jx1.readyState == 4 && cart_jx1.status == 200){
			document.getElementById("n_cart").innerHTML = (cart_jx1.responseText);
		}
	};
	cart_jx1.send();
	get_cart();
}

function delete_me(ids){
	var delete_jx1;
	if(window.XMLHttpRequest){
		delete_jx1 = new XMLHttpRequest();
	}else{
		delete_jx1 = new ActiveXObject('Microsoft.XMLHTTP');
	}
	
	delete_jx1.open("GET", "/ecommerce/delete?value="+ids, false);
	
	delete_jx1.onreadystatechange = function(){
		if(delete_jx1.readyState == 4 && delete_jx1.status == 200){
			document.getElementById("n_cart").innerHTML = (delete_jx1.responseText);
			document.getElementById("remove"+id).style.display = "none";
		}
	};
	delete_jx1.send();
	get_num();
}
function delete_me1(ids){
	var delete_jx1;
	if(window.XMLHttpRequest){
		delete_jx1 = new XMLHttpRequest();
	}else{
		delete_jx1 = new ActiveXObject('Microsoft.XMLHTTP');
	}
	
	delete_jx1.open("GET", "/ecommerce/delete?value="+ids, false);
	
	delete_jx1.onreadystatechange = function(){
		if(delete_jx1.readyState == 4 && delete_jx1.status == 200){
			window.location = "http://localhost:8080/ecommerce/cart";
		}
	};
	delete_jx1.send();
	get_num();
}

function show_id_cart(){
	if(document.getElementById("show_cart").style.display == "none"){
		get_cart();
		document.getElementById("show_cart").style.display = "block";
		document.getElementById("_click").style.backgroundColor = "rgb(228,247,247)";
	}else{
		document.getElementById("show_cart").style.display = "none";
		document.getElementById("_click").style.backgroundColor = "rgba(228,247,247,0.2)";
	}
}

function get_facebook_id(){
	//get_active+show_get_id
	if(document.getElementById("show_get_id").style.display == "none"){
		$("#get_active").show();
		$("#show_get_id").show();
	}else{
		$("#get_active").hide();
		$("#show_get_id").hide();
	}
}

function get_id(){
	var facebook_jx;
	if(window.XMLHttpRequest){
		facebook_jx = new XMLHttpRequest();
	}else{
		facebook_jx = new ActiveXObject('Microsoft.XMLHTTP');
	}
	var id = document.getElementById("facebook_id").value;
	param = "facebook_id="+id;
	
	facebook_jx.open("POST", "/ecommerce/get_facebook_id", false);
	
	facebook_jx.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	facebook_jx.setRequestHeader("Content-length", param.length);
	facebook_jx.setRequestHeader("Connetion", "close");

	facebook_jx.onreadystatechange = function(){
		if(facebook_jx.readyState == 4 && facebook_jx.status == 200){
			if(facebook_jx.responseText.trim() == "Suceeded"){
				window.location = "http://localhost:8080/ecommerce/profile";
			}else{
				alert("Try Again");
			}
		}else{
			alert("Try Again");
		}
	};
	facebook_jx.send(param);
}

function close_it(id){
	$("#get_active").hide(200);
	$("#"+id).hide(300);
}