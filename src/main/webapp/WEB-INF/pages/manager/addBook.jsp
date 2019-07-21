<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
    <title>添加图书</title>
  </head>
  
  <body>
  <form action="addBook" method="post" enctype="multipart/form-data">
    <table frame="border" width="50%">
    	<tr>
    		<td>图书名称</td>
    		<td>
    			<input type="text" name="name">
    		</td>
    	</tr>
    	<tr>
    		<td>作者</td>
    		<td>
    			<input type="text" name="author">
    		</td>
    	</tr>
    	<tr>
    		<td>售价</td>
    		<td>
    			<input type="text" name="price">
    		</td>
    	</tr>
    	<tr>
    		<td>图片</td>
    		<td>
    			<input type="file" name="file">
    		</td>
    	</tr>
    	<tr>
    		<td>图书描述</td>
    		<td>
    			<input type="text"  name="description"></input>
    		</td>
    	</tr>
    	<tr>
    		<td>所属分类</td>
    		<td>
    			<select name="category_id">
    				<c:forEach var="c" items="${categories }">
    					<option value="${c.id }">${c.name }</option>
    				</c:forEach>
    			</select>
    		</td>
    	</tr>
    	<tr>
    		<td>
    			<input type="reset" value="清空">
    		</td>
    		<td>
    			<input type="submit" value="提交">
    		</td>
    	</tr>
    </table>
    </form>
  </body>
</html>
