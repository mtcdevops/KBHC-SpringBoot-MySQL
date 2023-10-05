<%@page import="com.kbhc.blackcode.VO.DataInfoVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.kbhc.blackcode.VO.DataVO"%>
<% ArrayList<DataVO> list = (ArrayList)request.getAttribute("list"); %>
<% DataInfoVO dataInfo = (DataInfoVO)request.getAttribute("count");%>

<main>
	<div class="container-fluid px-4">
		<h1 class="mt-4">MySQL READ/WRITE TEST</h1>
		<ol class="breadcrumb mb-4">
			<li class="breadcrumb-item active"><%= dataInfo.getTotal() %></li>
		</ol>
		
		<div class="row">
			<div class="col-xl-3 col-md-6">
			    <div class="card bg-primary text-white mb-4">
			        <div class="card-body">LOCAL READ</div>
			        <div class="card-footer d-flex align-items-center justify-content-between">
			            <a class="small text-white stretched-link" href="#"><%= dataInfo.getLOCAL_READ() %></a>
			            <div class="small text-white"><i class="fas fa-angle-right"></i></div>
			        </div>
			    </div>
			</div>
			<div class="col-xl-3 col-md-6">
			    <div class="card bg-warning text-white mb-4">
			        <div class="card-body">LOCAL WRITE</div>
			        <div class="card-footer d-flex align-items-center justify-content-between">
			            <a class="small text-white stretched-link" href="#"><%= dataInfo.getLOCAL_WRITE() %></a>
			            <div class="small text-white"><i class="fas fa-angle-right"></i></div>
			        </div>
			    </div>
			</div>
			<div class="col-xl-3 col-md-6">
			    <div class="card bg-success text-white mb-4">
			        <div class="card-body">WebApp READ</div>
			        <div class="card-footer d-flex align-items-center justify-content-between">
			            <a class="small text-white stretched-link" href="#"><%= dataInfo.getAS_READ()%></a>
			            <div class="small text-white"><i class="fas fa-angle-right"></i></div>
			        </div>
			    </div>
			</div>
			<div class="col-xl-3 col-md-6">
			    <div class="card bg-danger text-white mb-4">
			        <div class="card-body">WebApp Write</div>
			        <div class="card-footer d-flex align-items-center justify-content-between">
			            <a class="small text-white stretched-link" href="#"><%= dataInfo.getLOCAL_WRITE() %></a>
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
					<div class="card-body"><canvas id="myAreaChart" width="100%" height="40"></canvas></div>
				</div>
			</div>
			<div class="col-xl-6">
				<div class="card mb-4">
					<div class="card-header">
						<i class="fas fa-chart-bar me-1"></i>
						Bar Chart Example
					</div>
					<div class="card-body"><canvas id="myBarChart" width="100%" height="40"></canvas></div>
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
			<div class="card-body">
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
		async : true,            // 비동기화 여부 (default : true)
		/* headers : {              // Http header
		      "Content-Type" : "application/json",
		      "X-HTTP-Method-Override" : "POST"
		    }, */
		contentType: 'application/json', // 데이터 타입을 JSON으로 설정
		dataType : 'text',       // 데이터 타입 (html, xml, json, text 등등)
		data : JSON.stringify({  // 보낼 데이터 (Object , String, Array)
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
		async : true,            // 비동기화 여부 (default : true)
		/* headers : {              // Http header
		      "Content-Type" : "application/json",
		      "X-HTTP-Method-Override" : "POST"
		    }, */
		contentType: 'application/json', // 데이터 타입을 JSON으로 설정
		dataType : 'text',       // 데이터 타입 (html, xml, json, text 등등)
		data : JSON.stringify({  // 보낼 데이터 (Object , String, Array)
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
</script>