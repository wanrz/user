<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<fieldset>
	<legend>我是控件</legend>
	<div>
			<table>
			<form action="<%=request.getContextPath()%>/user/1/showUser.do" name="user_form" method="post"
			 enctype="application/x-www-form-urlencoded">
					<tr>
						<td><input type="hidden" id="hidden" name="hidden" maxlength="5" /></td>
					</tr>
					<tr>
						<details>
					 		<summary>单选框</summary>
							<label for="text">单行文本</label><input type="text" id="text" name="text" maxlength="5" />
							<label for="password">密码</label><input type="password" id="password" name="password" maxlength="5" />
						</details>
					</tr>
			 		<tr>
						<td>
							<details>
				 				<summary>单选框</summary>
								<input type="radio" id="radio" name="radio" value="1" /><span>男</span>
								<input type="radio" id="radio" name="radio" value="2" checked="checked"/><span>女</span>
							</details>
						</td>
			 		</tr>
			 		<tr>
						<td>
						<details>
			 				<summary>复选框</summary>
							<input type="checkbox" id="checkbox" name="checkbox" value="1" /><span>工人</span>
							<input type="checkbox" id="checkbox" name="checkbox" value="2" checked="checked"/><span>军人</span>
						</details>
						</td>
			 		</tr>
			 		<tr>
						<td>
							<details>
			 					<summary>下拉列表</summary>
								<select id="select" name="select" multiple="multiple">
									<option value="1">北京</option>
									<option value="2">上海</option>
									<option value="3">武汉</option>
									<option value="1">北京</option>
									<option value="2">上海</option>
									<option value="3">武汉</option>
								</select>
							</details>
						</td>
			 		</tr>
			 		<tr>
			 			<td>
			 				<details>
			 					<summary>文件下载</summary>
			 					<input type="file" name="file" >
			 				</details>
			 			</td>
			 		</tr>
			 		<tr>
			 			<td>
			 				<details>
			 					<summary>其他标签</summary>
			 					<time datetime="2018-12-06 0:0:0">情人节</time>
			 					<mark>进度条</mark>
			 					<meter min="0" max="50" tile="50%">不支持meter</meter>
			 				</details>
			 			</td>
			 		</tr>
			 		<tr>
			 			<td>
			 				<details>
			 					<summary>用户基本信息</summary>
			 					<p>姓名：zhangsan</p>
			 					<p>性别：男</p>
			 				</details>
			 			</td>
			 		</tr>
			 		<tr>
			 			<td>
			 				<details>
			 					<summary>图片</summary>
			 					<input type="image" src="<%=request.getContextPath()%>/img/20180805_110843_1287.jpg" name="image" >
			 					<input type="image" src="<%=request.getContextPath()%>/img/20180805_110843_1287.jpg" name="image" >
			 					<img src="<%=request.getContextPath()%>/img/20180805_110843_1287.jpg" name="image"/>
			 				</details>
			 			</td>
			 		</tr>
			 		<tr>
						<td><label for="button">按钮</label><input type="button" id="button" name="button" value="按钮" /></td>
			 		</tr>
					<tr>
						<td>
							<input type="submit" id="submit" name="submit" value="提交" />
							<input type="reset" id="reset" name="reset" value="重置" />
						</td>
					</tr>
			</form>
			</table>
	</div>
</fieldset>
<textarae name="name" rows="100" cols="200" value="在这输入内容"> </textarae>
</body>
</html>