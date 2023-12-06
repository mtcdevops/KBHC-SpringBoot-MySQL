<%@page import="com.kbhc.blackcode.VO.DatabaseVO"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.catalina.util.ServerInfo"%>
<%@page import="com.kbhc.blackcode.VO.UserVO"%>
<%@page import="com.kbhc.blackcode.VO.PCMonitorVO"%>
<%@page import="com.kbhc.blackcode.VO.DataInfoVO"%>
<%@page import="com.kbhc.blackcode.VO.DataVO"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<DataVO> list = (ArrayList)request.getAttribute("list"); %>
<% DataInfoVO dataInfo = (DataInfoVO)request.getAttribute("count");%>
<% //PCMonitorVO pcMonitorVO = (PCMonitorVO)request.getAttribute("pcMonitorVO");%>
<% PCMonitorVO pcMonitorVO = new PCMonitorVO();%>
<% UserVO user = (UserVO)session.getAttribute("user"); %>
<% String server_name = (String)request.getAttribute("server_name");%>
<% List<DatabaseVO> dbList = (List<DatabaseVO>)request.getAttribute("dbList"); %>

<main>
	<div class="container-fluid px-4">
		<% if(user != null){ %>
		<h1 class="mt-4">SESSION : <%=user.getEmail() %></h1>
		<%}else{ %>
		<h1 class="mt-4">MySQL Duplication Test</h1>
		<%} %>
		<ol class="breadcrumb mb-4"> 
			<li class="breadcrumb-item active">MySQL Query Current Count</li>
		</ol>
		<button id="deleteSession">Delete Session</button>
		
		<div class="row" id="countData">
			<div class="col-xl-3 col-md-6">
			    <div class="card bg-primary text-white mb-4">
			        <div class="card-body">LOCAL READ [<%=server_name %>]</div>
			        <div class="card-footer d-flex align-items-center justify-content-between" id="LOCAL_READ">
			            <a class="small text-white stretched-link" href="#"><%= dataInfo.getLOCAL_READ() %></a>
			            <div class="small text-white"><i class="fas fa-angle-right"></i></div>
			        </div>
			    </div>
			</div>
			<div class="col-xl-3 col-md-6">
			    <div class="card bg-warning text-white mb-4">
			        <div class="card-body">LOCAL WRITE </div>
			        <div class="card-footer d-flex align-items-center justify-content-between" id="LOCAL_WRITE">
			            <a class="small text-white stretched-link" href="#"><%= dataInfo.getLOCAL_WRITE() %></a>
			            <div class="small text-white"><i class="fas fa-angle-right"></i></div>
			        </div>
			    </div>
			</div>
			<div class="col-xl-3 col-md-6">
			    <div class="card bg-success text-white mb-4">
			        <div class="card-body">Read Replica DB</div>
			        <div class="card-footer d-flex align-items-center justify-content-between" id="WebApp_READ">
			            <a class="small text-white stretched-link" href="#">[<%=server_name %>]</a>
			            <div class="small text-white"><i class="fas fa-angle-right"></i></div>
			        </div>
			    </div>
			</div>
			<div class="col-xl-3 col-md-6">
			    <div class="card bg-danger text-white mb-4">
			        <div class="card-body">ToTal Count</div>
			        <div class="card-footer d-flex align-items-center justify-content-between" id="WebApp_Write">
			            <a class="small text-white stretched-link" href="#"><%= dataInfo.getTotal() %></a>
			            <div class="small text-white"><i class="fas fa-angle-right"></i></div>
			        </div>
			    </div>
			</div>
		</div>

		<div class="row">
			<div class="col-xl-6">
				<div class="card mb-4">
					<div class="card-header">
						<i class="fas fa-chart-area me-1"></i>
						Area Chart Example
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
						Read Replica DB list
					</div>
					<div class="card-body">
						<table>
							<tr>
								<th>| No</th>
								<th>| Database Server id</th>
								<th>| Read replica name</th>
							</tr>
							<% for(int i=0; i<dbList.size(); i++) { %>
							<tr>
								<td>| <%= dbList.get(i).getServer_num() %></td>
								<td>| <%= dbList.get(i).getServer_id() %></td>
								<td>| <%= dbList.get(i).getServer_name() %></td>
							</tr>
							<%} %>
						</table>
					</div>
				</div>
			</div>
		</div>
		
		
		<div class="card mb-4">
			<div class="card-header">
				<i class="fas fa-table me-1"></i>
				Server Exception Log
				<button id="deleteException"> DELETE Exception Data</button>
				<button id="deleteAll"> DELETE ALL Data</button>
			</div>
			<div class="card-body" id="ServerExceptionLog">
				<table id="datatablesSimple">
					<thead>
						<tr>
							<th>Num</th>
							<th>Contents</th>
							<th>Date</th>
							<th>Read/Write</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<th>Num</th>
							<th>Contents</th>
							<th>Date</th>
							<th>Read/Write</th>
						</tr>
					</tfoot>
					<tbody>
					<% for(int i=0; i<list.size(); i++) {%>
						<tr>
							<td><%= list.get(i).getNum() %></td>
							<td><%= list.get(i).getContents() %></td>
							<td><%= list.get(i).getDate() %></td>
							<% if(list.get(i).getRw().equals("RR")) {%>
								<td>READ</td>
							<% }else{%>
								<td>Write</td>
							<% } %>
						</tr>
					<%} %>
					</tbody>
				</table>
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
    $("#LOCAL_READ").load(window.location.href + " #LOCAL_READ");
    $("#LOCAL_WRITE").load(window.location.href + " #LOCAL_WRITE");
    $("#WebApp_READ").load(window.location.href + " #WebApp_READ");
    $("#WebApp_Write").load(window.location.href + " #WebApp_Write");
    $("#PCMonitor").load(window.location.href + " #PCMonitor");
    $("#ServerExceptionLog").load(window.location.href + " #ServerExceptionLog");
}
</script>