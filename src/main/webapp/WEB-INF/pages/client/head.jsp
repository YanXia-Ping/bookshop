<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>首页头</title>
  </head>
  
  <body style="text-align:center;">
    <h1>网上书店</h1>
    <br/>
    <div>
	    <a href="getAll?&method=getAll" target="body">首页</a>
	    <a href="listcart" target="body">查看购物车</a>
	    <a href="clientListOrderServlet?userid=${user.id}" target="body">查看订单</a>
    </div>
    <div style="float:right;">
    	<c:if test="${user==null}"> 
	    <form action="login" method="post">
	    	用户名：<input type="text" name="username" style="width:60px;">
	    	密码：<input type="password" name="password" style="width:60px;">
	    	<input type="submit" value="登陆">
	    	<input type="button" value="注册" onclick="javascript:window.parent.body.location.href='register'">
	    </form>
	    </c:if>
	    
	    <c:if test="${user!=null}">
	    	欢迎您：${user.username } <a href="loginOutServlet">注销</a>
	    </c:if>
    </div>
  </body>
</html>
