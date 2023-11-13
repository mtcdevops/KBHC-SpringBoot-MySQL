<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html lang="en">
<%-- <%@ include file="/WEB-INF/views/include/common/head.jsp" %> --%>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
	<meta name="description" content="" />
	<meta name="author" content="" />
	<title>DB-Duplication</title>
	<link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
	<link href="css/styles.css" rel="stylesheet" />
	<script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>
	
	<body class="sb-nav-fixed">
	
		<%@ include file="/WEB-INF/views/include/common/sb-topnav.jsp" %>

		<div id="layoutSidenav">
		
			<%@ include file="/WEB-INF/views/include/common/layoutSidenav_nav.jsp" %>
			
			<div id="layoutSidenav_content">
				
				<%@ include file="/WEB-INF/views/index/main.jsp" %>
				
				<%@ include file="/WEB-INF/views/include/common/footer.jsp" %>
			</div>
		</div>
		<%@ include file="/WEB-INF/views/include/common/script.jsp" %>
	</body>
</html>
