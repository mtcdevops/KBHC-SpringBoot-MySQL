<%@page import="com.example.demo.VO.Client"%>
<%@page import="com.example.demo.VO.UserVO"%>
<%@page import="com.example.demo.VO.PCMonitorVO"%>
<%@page import="com.example.demo.VO.DataInfoVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.example.demo.VO.DataVO"%>
<% PCMonitorVO pcMonitorVO = new PCMonitorVO();%>
<% UserVO user = (UserVO)session.getAttribute("user"); %>
<% String clientIP = (String)request.getAttribute("clientIP"); %>
<main>
	<div class="container-fluid px-4">
		<% if(user != null){ %>
		<h1 class="mt-4">SESSION : <%=user.getEmail() %></h1>
		<%}else{ %>
		<h1 class="mt-4">MySQL Duplication Test</h1>
		<%} %>
		<ol class="breadcrumb mb-4">
			<li class="breadcrumb-item active">TOTAL : <%=user.getTotalClient() %></li>
			<li class="breadcrumb-item active">client ip : <%=clientIP %></li>
		</ol>
		
		<button id="deleteSession">Delete Session</button>
		<div class="card mb-4" >
			<div class="card-header">
				<i class="fas fa-table me-1"></i>
				SESSION IP LIST
			</div>
			<div class="card-body" id="ServerExceptionLog">
				<table id="">
					<thead>
						<tr>
							<th>Num  </th>
							<th>|  ip ===============</th>
						</tr>
					</thead>
					<tbody>
						<% for(int i = 0 ; i < user.getClientList().size();i++){ %>
						<tr>
							<th><%=i %></th>
							<th>|<%=user.getClientList().get(i) %></th>
						</tr>
						<% } %>
					</tbody>
				</table>
			</div>
		</div>
		
		
		<div class="row">
			<div class="col-xl-6">
				<div class="card mb-4">
					<div class="card-header">
						<i class="fas fa-chart-area me-1"></i>
						Server Resource Info
					</div>
					<div class="card-body" id="PCMonitor">
						<table id="">
							<thead>
								<tr>
									<th>CPU ======================</th>
									<th>HDD ======================</th>
									<th>Memory ======================</th>
								</tr>
							</thead>
							
							<tbody>
								<tr>
									<td>
										<%= pcMonitorVO.getCPU_Usage()%><br>
										<%= pcMonitorVO.getCPU_Usage_Percent()%><br>
										<%= pcMonitorVO.getCPU_Idle_Percent()%>
									</td>
									<td>
										<%= pcMonitorVO.getHDD_Usage() %><br>
										<%= pcMonitorVO.getHDD_Usage_Percent() %><br>
										<%= pcMonitorVO.getHDD_Idle() %><br>
										<%= pcMonitorVO.getHDD_Idle_Percent() %><br>
										<%= pcMonitorVO.getHDD_Total() %><br>
									</td>
									<td>
										<%=pcMonitorVO.getMemory_Idle_Percent() %><br>
										<%=pcMonitorVO.getMemory_FreePhysicalMemorySize() %><br>
										<%=pcMonitorVO.getMemory_TotalPhysicalMemorySize() %><br>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<div class="col-xl-6">
				<div class="card mb-4">
					<div class="card-header">
						<i class="fas fa-chart-bar me-1"></i>
						Server Resource Chart
					</div>
					<div class="card-body"><canvas id="PCMonitorChart" width="100%" height="40"></canvas></div>
				</div>
			</div>
		</div>
		
	</div>
</main>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
$("#deleteException").click(function(){
	console.log("deleteException")
	$.ajax({
		type: 'post',
		url : '<%=request.getContextPath()%>' + '/deleteException',
		async : true,            // ë¹ëê¸°í ì¬ë¶ (default : true)
		/* headers : {              // Http header
		      "Content-Type" : "application/json",
		      "X-HTTP-Method-Override" : "POST"
		    }, */
		contentType: 'application/json', // ë°ì´í° íìì JSONì¼ë¡ ì¤ì 
		dataType : 'text',       // ë°ì´í° íì (html, xml, json, text ë±ë±)
		data : JSON.stringify({  // ë³´ë¼ ë°ì´í° (Object , String, Array)
		      "data1" : "data1",
		      "data2" : "data2",
		      "data3" : "data3"
		    }),
		error : function(request, status, error) {
			console.log("request : ",request);
			console.log("status : ",status);
			console.log("error : ",error);
		},
		success : function(result) {
			console.log(result);
		}
	});
});

$("#deleteAll").click(function(){
	console.log("deleteAll")
	$.ajax({
		type: 'post',
		url : '<%=request.getContextPath()%>' + '/deleteAllData',
		async : true,            // ë¹ëê¸°í ì¬ë¶ (default : true)
		/* headers : {              // Http header
		      "Content-Type" : "application/json",
		      "X-HTTP-Method-Override" : "POST"
		    }, */
		contentType: 'application/json', // ë°ì´í° íìì JSONì¼ë¡ ì¤ì 
		dataType : 'text',       // ë°ì´í° íì (html, xml, json, text ë±ë±)
		data : JSON.stringify({  // ë³´ë¼ ë°ì´í° (Object , String, Array)
		      "data1" : "data1",
		      "data2" : "data2",
		      "data3" : "data3"
		    }),
		error : function(request, status, error) {
			console.log("request : ",request);
			console.log("status : ",status);
			console.log("error : ",error);
		},
		success : function(result) {
			console.log(result);
		}
	});
});

$("#deleteSession").click(function(){
	console.log("deleteSession")
	$.ajax({
		type: 'post',
		url : '<%=request.getContextPath()%>' + '/deleteSession',
		async : true,            // ë¹ëê¸°í ì¬ë¶ (default : true)
		/* headers : {              // Http header
		      "Content-Type" : "application/json",
		      "X-HTTP-Method-Override" : "POST"
		    }, */
		contentType: 'application/json', // ë°ì´í° íìì JSONì¼ë¡ ì¤ì 
		dataType : 'text',       // ë°ì´í° íì (html, xml, json, text ë±ë±)
		data : {"action":"delete"},
		error : function(request, status, error) {
			console.log("request : ",request);
			console.log("status : ",status);
			console.log("error : ",error);
		},
		success : function(result) {
			console.log(result);
			location.reload();
		}
	});
});

setInterval(reload, 1000);
function reload(){
	//location.reload()
    $("#PCMonitor").load(window.location.href + " #PCMonitor");
    $("#ServerExceptionLog").load(window.location.href + " #ServerExceptionLog");
    chartBar();
}

</script>