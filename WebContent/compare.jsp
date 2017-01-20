<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta charset="UTF-8">
        <title>Players Comparison</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="css/circle.css" rel="stylesheet" type="text/css"/>
        <link href="css/spin.css" rel="stylesheet" type="text/css"/>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>  
        <link rel="stylesheet" href="css/bootstrap-submenu.min.css">
        <link href="css/font.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" type="text/css" href="css/normalize.css" />
		<link rel="stylesheet" type="text/css" href="css/component.css" />
		
        <style>
			.container-fluid{
				padding-top: 100px;
				padding-left: 100px;
				padding-right: 100px;
			}
			.row.content{
				height:auto;
			}
			.dropdown.size{
				padding-top: 20px;
      			height: 100%;
				
			}
			.progress_size{
				padding-top: 35px;
				padding-left: 0px;
				padding-right: 0px;
      			
			}
			.attribute{
				padding-left: 0px;
				padding-right: 0px;
			}
			
			
			//Sub-menu toggle
			
			.dropdown-submenu {
			    position: relative;
			}
			
			.dropdown-submenu>.dropdown-menu {
			    top: 0;
			    left: 100%;
			    margin-top: -6px;
			    margin-left: -1px;
			    -webkit-border-radius: 0 6px 6px 6px;
			    -moz-border-radius: 0 6px 6px;
			    border-radius: 0 6px 6px 6px;
			}
			
			.dropdown-submenu:hover>.dropdown-menu {
			    display: block;
			}
			
			.dropdown-submenu>a:after {
			    display: block;
			    content: " ";
			    float: right;
			    width: 0;
			    height: 0;
			    border-color: transparent;
			    border-style: solid;
			    border-width: 5px 0 5px 5px;
			    border-left-color: #ccc;
			    margin-top: 5px;
			    margin-right: -10px;
			}
			
			.dropdown-submenu:hover>a:after {
			    border-left-color: #fff;
			}
			
			.dropdown-submenu.pull-left {
			    float: none;
			}
			
			.dropdown-submenu.pull-left>.dropdown-menu {
			    left: -100%;
			    margin-left: 10px;
			    -webkit-border-radius: 6px 0 6px 6px;
			    -moz-border-radius: 6px 0 6px 6px;
			    border-radius: 6px 0 6px 6px;
			}
			
			
		</style>
		    </head>
    <body>
        
        <div class="navbar navbar-inverse navbar-fixed-top">
		  <div class="container">
		    <div class="navbar-header">
		      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		      </button>
		      <a class="navbar-brand" href="http://localhost:8080/Pro/">Cricmantic</a>
		    </div>
		    <div class="collapse navbar-collapse">
		      <ul class="nav navbar-nav">
		        <li class="active"><a href="http://localhost:8080/Pro/">Home</a></li>
		        <li><a href="#about">Player</a></li>
		        <li><a href="http://localhost:8080/Pro/teamServlet">Team</a></li>
		        <li><a href="http://localhost:8080/Pro/comparisonServlet">Comaprison</a></li>
		      </ul>
		    </div><!--/.nav-collapse -->
		  </div>
		</div>
        
        <div class="container-fluid text-center">
				<div class="row content">           
					<div class="dropdown size col-sm-4" >
						<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Select Player
						<span class="caret"></span></button>
						<ul class="dropdown-menu">
							<c:forEach items="${playerList}" var="list">
							<li class="dropdown-submenu"><a class="test" id="dropdownList2" role="menuitem" tabindex="-1" href="#">${list}</a>
								<ul class="dropdown-menu">
						          <li><a tabindex="-1" href="#">2nd level dropdown</a></li>
						          <li><a tabindex="-1" href="#">2nd level dropdown</a></li>
						        </ul>
							</li>
							</c:forEach>
						</ul>
					</div>
					
					<div class="col-sm-4">
						<h1>Career Statistics</h1>
					</div>

					<div class="dropdown size col-sm-4" >
						<button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Select Player
						<span class="caret"></span></button>
						<ul class="dropdown-menu">
							<c:forEach items="${playerList}" var="list">
							<li class="dropdown-submenu"><a class="test" id="dropdownList2" role="menuitem" tabindex="-1" href="#">${list}</a>
								<ul class="dropdown-menu">
						          <li><a tabindex="-1" href="#">2nd level dropdown</a></li>
						          <li><a tabindex="-1" href="#">2nd level dropdown</a></li>
						        </ul>
							</li>
							</c:forEach>
						</ul>
					</div>
					</div>

			
				<br>
				
				<div class="row content">           
					<div class="progress_size col-sm-5">
						<strong><h2 id="player1"></h2></strong>	
					</div>
					
					<div class="attribute col-sm-2">
						      
					</div>
					<div class="progress_size col-sm-5">
						<strong><h2 id="player2"></h2></strong>
					</div>
					</div>
				<br>
				
				<div class="row content">           
					<div class="progress_size col-sm-5">
						<div class="spincrement" id="runs1">0</div>
					</div>
					
					<div class="attribute col-sm-2">
						      <div class="circle">Runs</div>
					</div>
					<div class="progress_size col-sm-5">
						<div class="spincrement" id="runs2">0</div>
					</div>
					</div>
				<br>
				<div class="row content">           
					<div class="progress_size col-sm-5">
						<div class="spincrement" id="sixes1">0</div>
					</div>
					
					<div class="attribute col-sm-2">
						  <div class="circle">Sixes</div>
					</div>
					<div class="progress_size col-sm-5">
						<div class="spincrement" id="sixes2">0</div>
					</div>
					</div>
				<br>
				<div class="row content">           
					<div class="progress_size col-sm-5">
						<div class="spincrement" id="fours1">0</div>
					</div>
					
					<div class="attribute col-sm-2">
						  <div class="circle">Fours</div>
						
					</div>
					<div class="progress_size col-sm-5">
						<div class="spincrement" id="fours1">0</div>
					</div>
					</div>
				<br>	
				<div class="row content">           
				<div class="progress_size col-sm-5">
					<div class="spincrement" id="wickets1">0</div>
				</div>
				
				<div class="attribute col-sm-2">
					
					  <div class="circle">Wickets</div>
					
				</div>
				<div class="progress_size col-sm-5">
					<div class="spincrement" id="wickets2">0</div>
				</div>
				</div>
				<br>
				<div class="row content">           
				<div class="progress_size col-sm-5">
					<div class="spincrement" id="bt_avg1">0</div>
				</div>
				
				<div class="attribute col-sm-2">
					<div class="circle">Batting Average</div>
					
				</div>
				<div class="progress_size col-sm-5">
					<div class="spincrement" id="bt_avg2">0</div>
				</div>
				</div>
				<br>
				<div class="row content">           
				<div class="progress_size col-sm-5">
					<div class="spincrement" id="bl_avg1">0</div>
				</div>
				
				<div class="attribute col-sm-2">
					<div class="circle">Balling Average</div>
					
				</div>
				<div class="progress_size col-sm-5">
					<div class="spincrement" id="bl_avg2">0</div>
				</div>
				</div>
				<br>
             
        </div> <!-- Container ends -->
        <script src="js/jquery-3.1.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/bootstrap-submenu.min.js" defer></script>
        <script src="js/jquery.spincrement.min.js"></script>
        <script src="js/modernizr.custom.js"></script>
        <script>
		
		function setValues1(record){
			$("#runs1").html(record[0]);
			$("#sixes1").html(record[1]);
			$("#fours1").html(record[2]);
			$("#wickets1").html(record[3]);
		}
		
		function setValues2(record){
			$("#runs2").html(record[0]);
			$("#sixes2").html(record[1]);
			$("#fours2").html(record[2]);
			$("#wickets2").html(record[3]);
		}
		
		function onClickDropdown(param1, param2){
			$.ajax({
			    url: 'comparisonServlet', // the url you want to send it to
			    data: {
			    	name: param1,
			    	dropdown: param2
			    },
			    method: 'GET',
		        dataType: 'json',
		        async:false,
			    success: function(data) {
			    	console.log(data);
			    	if(data.flag == "first"){
			    		setValues1(data.playerRecord);
			    	}
			    	else{
			    		setValues2(data.playerRecord);
			    	}
			    }
			  });
		};
		
		$(document).ready(function () {
			
			
			$(".dropdown-menu li a#dropdownList1").on('click', function(e) {
				var playerName = $(this).html();
				  console.log(playerName);
				  $("#player1").html(playerName);
				  onClickDropdown(playerName, "first");
				});
			$(".dropdown-menu li a#dropdownList2").on('click', function(e) {
				var playerName = $(this).html();
				  console.log(playerName);
				  $("#player2").html(playerName);
				  onClickDropdown(playerName, "second");
				});
			//ScoreBar
			$('.spincrement').spincrement({
				from: 0,
				decimalPlaces: 0,
				duration: 2000,
			});
			
			
			$(".dropdown-toggle").dropdown();
			/* $(".dropdown-menu li a#dropdownList1").mouseover(function() {
				   console.log($(this).text());
				}); */
			//$("#heading").html("Hello World");

		});
		</script>
        
    </body>
</html>