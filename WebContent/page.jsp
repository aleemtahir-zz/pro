<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="style.css">
<title>Insert title here</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="dist/progressbar.min.js"></script>


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
            }
        }
    });
}

var GetChartData = function () {
    $.ajax({
        url: 'testing?method=makegraph',
        data:{input : $("input[name=players]:checked").val()},
        method: 'GET',
        dataType: 'json',
        async:false,
        success: function (d) {
           console.log(d);
           
           chartData1 = {
                   labels: player, 
                   datasets: [
                       {
                           data: d.xAxis,
                           //hoverBackgroundColor: 'rgba(255, 206, 86, 1)',
                           label: "My First dataset",
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
               respondCanvas();
        },
        error:function(){
	           alert('Chart1 error');
	         }
    });
};
var GetChartData2 = function () {
    $.ajax({
        url: 'testing?method=makegraph',
        data:{input : $("input[name=team]:checked").val()},
        method: 'GET',
        dataType: 'json',
        async:false,
        success: function (d) {
        	console.log(d);
           chartData2 = {
                   labels: team, 
                   datasets: [
                       {
                           data: d.xAxis,
                           //hoverBackgroundColor: 'rgba(255, 206, 86, 1)',
                           label: "My First dataset",
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
function GetProgressBarData(){
	$.ajax({
        url: 'testing?method=makebar',
        data:{bar : playername}, 
        method: 'GET',
        dataType: 'json',
        async:false,
        success: function (data) {
        	console.log(data);
        	makeScores(data);
        },
        error:function(){
	           alert('Progress Bar error');
	         }
    });
}

var team;
var player;

function GetYAxisC1(){
	$.ajax({
        url: 'testing?method=makeYAxisC1',
        method: 'GET',
        dataType: 'json',
        async:false,
        success: function (data) {
        	console.log(data);
        	player = data.Y1;
        },
        error:function(){
	           alert('YAxisC1 error');
	         }
    });
}

function GetYAxisC2(){
	$.ajax({
        url: 'testing?method=makeYAxisC2',
        method: 'GET',
        dataType: 'json',
        async:false,
        success: function (data) {
        	console.log(data);
        	team = data.Y2;
        },
        error:function(){
	           alert('YAxisC2 error');
	         }
    });
}

function makeScores(data){
	// Docs: http://progressbarjs.readthedocs.org/en/1.0.0/
	var container = document.getElementById("scoreContainer1");
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
	
	GetYAxisC1();
	GetYAxisC2();
	GetChartData();
	GetChartData2();
	GetProgressBarData();
	
});
</script>
<style>
		
	</style>
</head>
<body>
        <div id="full_page">
			<div id="header">
				<div id="header_top">
					<h1>${playerName}'s Record</h1>
				</div>
				<div id="header_bottom">
					<div id="biodata">
					</div>
					<div id="pic">
					</div>
					<div id="career">
						<div id="row">
							<div id="runsContainer">
							<div id="scoreContainer1"></div>
							<div id="textid">
								<span>Runs</span>
							</div>
						</div>
						<div id="runsContainer">
							<div id="scoreContainer2"></div>
							<div id="textid">
								<span>Average</span>
							</div>
						</div>
						</div>
						<div id="row">
							<div id="runsContainer">
							<div id="scoreContainer2"></div>
							<div id="textid">
								<span>Wickets</span>
							</div>
						</div>
						<div id="runsContainer">
							<div id="scoreContainer2"></div>
							<div id="textid">
								<span>Out</span>
							</div>
						</div>
						</div>
					</div>
				</div>
			</div>
			<div id="first_type_graphs">
				<div id="graph1">
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
				<div id="graph2">
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
			</div>
			<div id="second_type_graph">
				<div id="graph1">
				</div>
				<div id="graph2">
				</div>
			</div>
		</div>
    </body>
</html>