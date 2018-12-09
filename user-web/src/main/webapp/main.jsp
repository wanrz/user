<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
    function getkey(a) {
        var pms = document.getElementById("sql").value;
 	 	a.href = '<%=request.getContextPath()%>/user/updateData.do?sql=' + pms;
  	}

	function saveImg(a) {
        var fileUrl = document.getElementById("fileUrl").value;
 	 	a.href = '<%=request.getContextPath()%>/user/saveImg.do?fileUrl=' + fileUrl;
  	}
</script>
<body>
	欢迎：${currentUser.userName }
	<details> <summary>显示人员</summary> <a
		href="<%=request.getContextPath()%>/user/1/showUser.do">显示人员</a> </details>
	<details> <summary>显示html标签</summary> <a
		href="<%=request.getContextPath()%>/user/showHtmlLab.do">显示html标签</a>
	</details>
	<details> <summary>保存图片</summary>
		<input type="text" id="fileUrl" name="fileUrl" style="width: 550px;"/>
		 <a href=""  onclick="saveImg(this)">保存图像</a><br> 
		 <span style="color: red;">${error}</span>
	</details>
	<details> <summary>显示图像标签</summary> <a
		href="<%=request.getContextPath()%>/user/showImg.do">显示图像标签</a>
	</details>
	<details> <summary>更新数据</summary> 
		<a href=""  onclick="getkey(this)">更新数据</a><br>
		<textarea id="sql" rows="10" cols="60">
			
		</textarea><br>
		<label for="sql">SQL语句</label>
		<span style="color: red;">${error}</span>
<!-- 		<input type="text" id="sql" name="sql"/> -->
	</details>
</body>
</html>