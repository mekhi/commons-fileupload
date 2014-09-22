<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>upload.jsp</title>
<meta http-equiv="keywords" content="mekhi">
</head>

<body>
	<form action="UploadFileServlet" method="post"
		enctype="multipart/form-data">
		文件：<input name="filename" type="file"><br> <input
			name="submit" type="submit" value="上传">
	</form>

</body>
</html>
