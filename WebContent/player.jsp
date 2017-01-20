<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/style.css">
<title>Cricmantic</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

<style>
		
	</style>
</head>
<body>
<div class="container playerPage">
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
	<br/><br/>
        <div class="container" id="full_page">
			<div class="row headerDiv">
				<div class="header">
					<div class="heading">
						<h1>Player</h1>
					</div>
				</div>
			</div>
			<br/>
			<div class="row profileDiv">
				<div class="profile col-md-4">
					<div class="info">
						<strong>Name: ${playerName} </strong> 
						<br/>
						<hr/>
						<strong>Team: ${playerTeam}</strong>
						<br/>
						<hr/>
					</div>
				</div>
				<div id="pic" class="profile col-md-4">
					<img src="pics/${playerName}.jpeg" alt="${playerName}">
					
				</div>
				<div class="profile col-md-4">
					<div id="row">
						<div id="runsContainer">
						<div id="scoreContainer1" class="scoreContainer"></div>
						<div id="textid">
							<span>Runs</span>
						</div>
					</div>
					<div id="runsContainer">
						<div id="scoreContainer2" class="scoreContainer"></div>
						<div id="textid">
							<span>Average</span>
						</div>
					</div>
					</div>
					<div id="row">
						<div id="runsContainer">
						<div id="scoreContainer3" class="scoreContainer"></div>
						<div id="textid">
							<span>Fours</span>
						</div>
					</div>
					<div id="runsContainer">
						<div id="scoreContainer4" class="scoreContainer"></div>
						<div id="textid">
							<span>Sixess</span>
						</div>
					</div>
					</div>
				</div>
			</div>
			<br/>
			<div class="row graphDiv">
				<div class="graph col-md-6">
					<div>
						<h2>Runs Against Player</h2>
					</div>
					<div >
						<input id="RunsP" type="radio" name="players" value="RunsP" >Runs
						<input id="WicketsP" type="radio" name="players" value="WicketsP">Wickets
					</div>		
					<div id="canvas">
					 	<canvas id="myChart1"></canvas>
					</div>
				</div>
				<div class="graph col-md-6">
					<div>
						<h2>Runs Against Team</h2>
					</div>
					<div >
						<input id="RunsT" type="radio" name="team" value="RunsT" >Runs
						<input id="WicketsT" type="radio" name="team" value="WicketsT">Wickets
					</div>		
					<div id="canvas">
					 	<canvas id="myChart2"></canvas>
					</div>
				</div>
				<br/>
			</div>

		</div>
		</div>
		
		<script src="js/jquery-3.1.1.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/progressbar.min.js"></script>
		<script src="js/Chart.min.js"></script>
		
		<script type="text/javascript">
		var chartData1 = {};
		var chartData2 = {};
		
		function respondCanvas(ctx, chartData) {             
		    //Call a function to redraw other content (texts, images etc)
		    var ctx1 = document.getElementById("myChart1").getContext("2d");
		    var myChart1 = new Chart(ctx1, {
		    	
		        type: 'horizontalBar',
		        data: chartData1,
		        options: {
		            scales: {
		                yAxes: [{
		                    ticks: {
		                        beginAtZero:true
		                    }
		                }]
		            },
		            legend: {
		                display: false
		            },
		            tooltips: {
		                callbacks: {
		                   label: function(tooltipItem) {
		                          return tooltipItem.yLabel;
		                   }
		                }
		            }
		        }
		    });
		}
		
		function respondCanvas2() {             
		    //Call a function to redraw other content (texts, images etc)
		    var ctx2 = document.getElementById("myChart2").getContext("2d");
		    var myChart1 = new Chart(ctx2, {
		    	
		        type: 'horizontalBar',
		        data: chartData2,
		        options: {
		            scales: {
		                yAxes: [{
		                    ticks: {
		                        beginAtZero:true
		                    }
		                }]
		            },
		            legend: {
		                display: false
		            },
		            tooltips: {
		                callbacks: {
		                   label: function(tooltipItem) {
		                          return tooltipItem.yLabel;
		                   }
		                }
		            }
		        }
		    });
		}
		
		var GetChartData = function () {
		    $.ajax({
		        url: 'playerServlet?method=makegraph',
		        data:{
		        	input : $("input[name=players]:checked").val()
		        	},
		        method: 'GET',
		        dataType: 'json',
		        async:false,
		        success: function (d) {
		           console.log(d);
		           
		           chartData1 = {
		                   labels: d.yAxis, 
		                   datasets: [
		                       {
		                           data: d.xAxis,
		                           //hoverBackgroundColor: 'rgba(255, 206, 86, 1)',
		                           //label: "My First dataset",
		                           fill: false,
		                           lineTension: 0.1,
		                           backgroundColor: [
		                        	   'rgba(255,99,132,1)',
		                               'rgba(54, 162, 235, 1)',
		                               'rgba(255, 206, 86, 1)',
		                               'rgba(75, 192, 192, 1)',
		                               'rgba(153, 102, 255, 1)',
		                               'rgba(255,99,132,1)',
		                               'rgba(54, 162, 235, 1)',
		                               'rgba(255, 206, 86, 1)',
		                               'rgba(75, 192, 192, 1)',
		                               'rgba(153, 102, 255, 1)',
		                               'rgba(255, 159, 64, 1)'
		                           ],
		                           borderColor: [
		                               'rgba(255,99,132,1)',
		                               'rgba(54, 162, 235, 1)',
		                               'rgba(255, 206, 86, 1)',
		                               'rgba(75, 192, 192, 1)',
		                               'rgba(153, 102, 255, 1)',
		                               'rgba(255,99,132,1)',
		                               'rgba(54, 162, 235, 1)',
		                               'rgba(255, 206, 86, 1)',
		                               'rgba(75, 192, 192, 1)',
		                               'rgba(153, 102, 255, 1)',	
		                               'rgba(255, 159, 64, 1)'
		                           ],
		                           borderWidth: 1
		                       }
		                   ]
		               };
		               respondCanvas();
		        },
		        error:function(){
			           alert('Chart1 error');
			         }
		    });
		};
		var GetChartData2 = function () {
		    $.ajax({
		        url: 'playerServlet?method=makegraph',
		        data:{input : $("input[name=team]:checked").val()},
		        method: 'GET',
		        dataType: 'json',
		        async:false,
		        success: function (d) {
		        	console.log(d);
		           chartData2 = {
		                   labels: d.yAxis, 
		                   datasets: [
		                       {
		                           data: d.xAxis,
		                           //hoverBackgroundColor: 'rgba(255, 206, 86, 1)',
		                           //label: false,
		                           fill: false,
		                           lineTension: 0.1,
		                           backgroundColor: [
		                        	   'rgba(255,99,132,1)',
		                               'rgba(54, 162, 235, 1)',
		                               'rgba(255, 206, 86, 1)',
		                               'rgba(75, 192, 192, 1)',
		                               'rgba(153, 102, 255, 1)',
		                               'rgba(255, 159, 64, 1)'
		                           ],
		                           borderColor: [
		                               'rgba(255,99,132,1)',
		                               'rgba(54, 162, 235, 1)',
		                               'rgba(255, 206, 86, 1)',
		                               'rgba(75, 192, 192, 1)',
		                               'rgba(153, 102, 255, 1)',
		                               'rgba(255, 159, 64, 1)'
		                           ],
		                           borderWidth: 1
		                       }
		                   ]
		               };
		               respondCanvas2();
		        },
		        error:function(){
			           alert('Chart2 error');
			         }
		    });
		};
		
		var playername = "${playerName}";
		function GetProgressBarData(event, elementId){
			$.ajax({
		        url: 'playerServlet?method=makebar',
		        data:{
		        	bar : playername,
		        	param : event
		        	},	
		        method: 'GET',
		        dataType: 'json',
		        async:false,
		        success: function (data) {
		        	console.log(data);
		        	makeScores(elementId,data);
		        },
		        error:function(){
			           alert('Progress Bar error');
			         }
		    });
		}
		
		function makeScores(ID,data){
			// Docs: http://progressbarjs.readthedocs.org/en/1.0.0/
			var container = document.getElementById(ID);
			var bar = new ProgressBar.Circle(container, {
			  color: '#0095B6',
			  // This has to be the same size as the maximum width to
			  // prevent clipping
			  strokeWidth: 4,
			  trailWidth: 1,
			  easing: 'easeInOut',
			  duration: 1400,
			  text: {
			    autoStyleContainer: false
			  },
			  from: { color: '#aaa', width: 1 },
			  to: { color: '#0095B6', width: 4 },
			  // Set default step function for all animate calls
			  step: function(state, circle) {
			    circle.path.setAttribute('stroke', state.color);
			    circle.path.setAttribute('stroke-width', state.width);
		
			    var value = Math.round(circle.value() * data);
			    if (value === 0) {
			      circle.setText('');
			    } else {
			      circle.setText(value);
			    }
		
			  }
			});
			bar.text.style.fontFamily = '"Raleway", Helvetica, sans-serif';
			bar.text.style.fontSize = '2rem';
		
			bar.animate(1.0);  // Number from 0.0 to 1.0
		}
		
		function insertPic(){
		    var img = document.getElementById("picID");
		    img.src = "pics/"+playername+".jpeg";
		
		    //src.appendChild(img);
		}
		
		
		
		$(document).ready(function () {
		
			$("input[id='RunsP']").prop('checked', true);
			$("input[id='RunsT']").prop('checked', true);
			 if( $("input[name=players]").change(function (){
				 $("input[name=players]").prop('checked', false);
				 $(this).prop('checked', true);
				 GetChartData();
			})
			);
			 if( $("input[name=team]").change(function (){
				 $("input[name=team]").prop('checked', false);
				 $(this).prop('checked', true);
				 GetChartData2();
			})
			); 
			
		
			GetChartData();
			GetChartData2();
			GetProgressBarData("runs","scoreContainer1");
			GetProgressBarData("runs","scoreContainer2");
			GetProgressBarData("4s", "scoreContainer3");
			GetProgressBarData("6s", "scoreContainer4");
			
		});
		</script>
    </body>
</html>