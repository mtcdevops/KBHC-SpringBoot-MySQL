<script type="text/javascript">
function executeAjax(){

$.ajax({
		url : "/getAjax",
		type : "get",
		data : '',
		dataType : "json",
		success : function(data) {
			
			var innerHtml = "";
			innerHtml  += "<table>";
			
			for(var i=0; i<data.length; i++){
				innerHtml  += "<tr>";
				innerHtml  += "<td>"+ data[i].name;
				innerHtml  += "</td>";
				innerHtml  += "<td>"+ data[i].sal;
				innerHtml  += "</td>";
				innerHtml  += "</tr>";
			}
			
			innerHtml  += "</table>";
			
			$("#ajaxResult").html(innerHtml);
		},
		error : function(request) {
			alert("실패");
		}
	});
}
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="js/scripts.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
<script src="assets/demo/chart-area-demo.js"></script>
<script src="assets/demo/chart-bar-demo.js"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
<script src="js/datatables-simple-demo.js"></script>