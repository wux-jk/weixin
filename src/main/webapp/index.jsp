<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/18
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
  </head>
  <body>
  <input type="button" onclick="addUser()" value="增加">
<table border="1px">

  <tr>
    <td >id</td>
    <td>用户名称</td>
  </tr>


    <c:forEach items="${list}" var="user">
        <tr>
          <td>${user.userID }</td>
          <td>${user.userName }</td>
          <td><input type="button" onclick="updateUser(${user.userID })" value="修改"></td>
          <td><input type="button" onclick="deleteUserById(${user.userID })" value="删除"></td>
      </tr>
    </c:forEach>

</table>

<script>
  function addUser(){
    location.href="<%=request.getContextPath()%>/addUser.jsp"
  }

  function updateUser(){
    location.href="<%=request.getContextPath()%>/addUser.jsp"
  }
</script>
  </body>
</html>
