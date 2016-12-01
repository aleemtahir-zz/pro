<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("input").click(function() {
		$.ajax({
	         url:'testing',
	         data:{input : $("input[type=radio]:checked").val()},
	         type:'get',
	         success:function(data){
	            alert(data); 
	         },
	         error:function(){
	           alert('error');
	         }
	      });
	});
});

</script>

</head>
<body>
<div><h1>${playerName} Record</h1></div>
<div>
<div style="margin-left:100px;">
<input id="check" type="radio" name="records" value="Runs">Runs
<input id="check" type="radio" name="records" value="Highest">Highest
<input id="check" type="radio" name="records" value="100s">100s
<input id="check" type="radio" name="records" value="50s">50s
<input id="check" type="radio" name="records" value="Wickets">Wickets

</div>
<div style=" top:60px; left:10px; width:500px; height:500px;">
 	<canvas id="myChart"></canvas>
    <script>
    var ctx = document.getElementById("myChart").getContext("2d");
    var myChart = new Chart(ctx, {
    	
        type: 'horizontalBar',
        data: {
            labels: ["India", "Pakistan", "Australia", "England", "WestIndies", "SriLanka","NewZealand"],
            datasets: [{
                label: 'Runs Against Team',
                data: [${results}, , 7, 1, 8, 13],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)',
                    'rgba(255, 206, 86, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(153, 102, 255, 0.2)',
                    'rgba(255, 159, 64, 0.2)'
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
            }]
        },
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
    </script>
    </div>
    </div>
    <div>
	<%-- 	<table>
			<c:forEach items="${results}" var="rowObject">
				<tr>
		            <td>${rowObject.getSubject()}</td>
		            <td>${rowObject.getObject()}</td> 
		    	</tr>
			</c:forEach>
		</table> --%>
	</div>
</body>
</html>