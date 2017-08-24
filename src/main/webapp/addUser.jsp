<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/18
  Time: 20:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form action="<%=request.getContextPath()%>/user/addUser.jhtml" method="post">
 <%-- <input type="hidden" value="${book.bookImg }" name="bookImg" id="bookImg">--%>
  <table border="1px">
    <tr>
      <td>用户名称:</td>
      <td><input type="text" name="userName" value="${user.userName }"></td>
    </tr>
   <%-- <tr>
      <td>书籍价格:</td>
      <td><input type="text" name="bookPrice" value="${book.bookPrice }"></td>
    </tr>
    <tr>
      <td>学生头像:</td>
      <td>
        <img src="<%=request.getContextPath()%>${book.bookImg}"  width="80px"/>
        <input type="file"  name="upImg"/></td>
    </tr>--%>
  </table>
  <input type="submit" value="提交">



</form>
</body>
</html>
