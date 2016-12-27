<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
     
     	<form action="api/house/secondsoup" method="get">
			<input class="btn btn-success" type="submit" value="二手房数据">
		</form>
		
     	<form action="api/house/insert" method="get">
			<input class="btn btn-success" type="submit" value="插入表格">
		</form>
		<form action="api/house/fangCrawl" method="get">
			<input class="btn btn-success" type="submit" value="新房数据">
		</form>
	-->
	   <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
  </head> 
  <body>
    This is my JSP page. <br>
    <form action="api/house/fangCrawl" method="get">
			<input class="btn btn-success" type="submit" value="新房数据">
		</form>
  </body>
</html>
