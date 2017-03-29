<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <html xmlns="http://www.w3.org/1999/xhtml"
    xmlns:f="http://java.sun.com/jsf/core"
    xmlns:h="http://java.sun.com/jsf/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Cricmantic</title>
<link href="css/style.css" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link href="css/searchResult.css" rel="stylesheet" type="text/css" />
<link href="css/font.css" rel="stylesheet" type="text/css">
<link href="css/font.css" rel="stylesheet" type="text/css">
<link href="css/normalize.css" rel="stylesheet" type="text/css" />
<link href="css/component.css" rel="stylesheet" type="text/css" />
</script>
<style>
body{
	  background: linear-gradient( rgba(0,0,0,.5), rgba(0,0,0,.5) ), 
	  url('pics/c.jpg') no-repeat center center fixed; 
	  -webkit-background-size: cover;
	  -moz-background-size: cover;
	  -o-background-size: cover;
	  background-size: cover;
	  height: 100%;
	  background-color: #000000;
	  
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
        <li><a href="http://localhost:8080/Pro/matchServlet">Match</a></li>
        <li><a href="http://localhost:8080/Pro/teamServlet">Team</a></li>
        <li><a href="http://localhost:8080/Pro/comparisonServlet">Comaprison</a></li>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</div>
<br/><br/><br/>
<div class="container">
	<div class="row">
        <div class="col-sm-6 ">
            <form action="NLPservlet" method="POST"> 
                <div class="input-group stylish-input-group">
                    <input type="text" class="form-control" value='${query}'  placeholder="Search" >
                    <span class="input-group-addon">
                        <button type="submit">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>  
                    </span>
                </div>
            </form>
        </div>
	</div>
</div>
<br/><br/>        
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css" rel='stylesheet' type='text/css'>

<div class="container">
  <div class="row">
    <div class="col-md-12">

        <div class="panel panel-default panel-table">
          <div class="panel-heading">
            <div class="row">
              <div class="col col-xs-6">
                <h3 class="panel-title">Results</h3>
              </div>
            </div>
          </div>
          <div class="panel-body">
            <table class="table table-striped table-bordered table-list">
              <thead>
			      <tr class="fixed-cell-width table-bordered">
			       <c:forEach  items="${field}" var="field">
						<th><c:out value="${field}" /></th>
					</c:forEach>
			      </tr>
			    </thead>
              <tbody>
				<c:forEach items="${resultList1}" var="list1">
				<tr>
					<c:forEach begin="0" end="${fn:length(list1.field) - 1}"
						var="index">
						<td><c:out value="${list1.field[index]}" /></td>
					</c:forEach>
				</tr>
				</c:forEach>
				</tbody>
            </table>
        
          </div>
          <div class="panel-footer">
            <div class="row">
              <div class="col col-xs-4">Page 1 of 5
              </div>
              <div class="col col-xs-8">
                <ul class="pagination hidden-xs pull-right">
                  <li><a href="#">1</a></li>
                  <li><a href="#">2</a></li>
                  <li><a href="#">3</a></li>
                  <li><a href="#">4</a></li>
                  <li><a href="#">5</a></li>
                </ul>
                <ul class="pagination visible-xs pull-right">
                    <li><a href="#">«</a></li>
                    <li><a href="#">»</a></li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        </div>
</div></div>
  <br><br>
  <div class="container">
  	<div class="row">
    	<div class="col-md-12">
    		<div  style="height:300px; width:300px; float:left">
		      <canvas id="myChart1"></canvas>
		    </div>
		    <div  style="height:300px; width:300px; float:right">
		      <canvas id="myChart2"></canvas>
		    </div>
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
	var ctx1 = document.getElementById("myChart1").getContext("2d");
	var ctx2 = document.getElementById("myChart2").getContext("2d");
	
	
	function respondCanvas(ctx, chartData){
		
		var myPieChart = new Chart(ctx,{
		    type: 'doughnut',
		    data: chartData,
		    options: {
		        animation:{
		            animateScale:true,
		            animateRotate:true
		        }
		    }
		});
	}
	var GetChartData = function (label, dataList, ctx) {
		var data = {
    		    labels: label,
    		    datasets: [
    		        {
    		            data: dataList,
    		            backgroundColor: [
    		                "#FF6384",
    		                "#36A2EB",
    		                "#FFCE56"
    		            ],
    		            hoverBackgroundColor: [
    		                "#FF6384",
    		                "#36A2EB",
    		                "#FFCE56"
    		            ]
    		        }]
    		};
           respondCanvas(ctx, data);
	}
	
	$(document).ready(function () {
		
		/* $.get("NLPservlet", function(data){
	        console.log(data);
	    }); */
	    $.ajax({
	        url: 'NLPservlet',
	        method: 'GET',
	        dataType: 'json',
	        async:false,
	        success: function (d) {
	           console.log(d);
	           GetChartData(d.labels, d.list1, ctx1);
	   		   GetChartData(d.labels, d.list2, ctx2);
	        },
	        
	    });
		
	}); 
	</script>
</body>
</html>