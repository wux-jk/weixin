<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/18
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<!-- 表工具栏 -->
<div>

  <div class="btn-group">
    <button type="button" class="btn btn-success" onclick="show_add_dialog()">新增</button>
  </div>
  <div class="btn-group">
    <button type="button" class="btn btn-danger" onclick="delete_all_user()">删除</button>
  </div>
  <div class="btn-group">
    <button type="button" class="btn btn-danger" onclick="export_user()">导出用户</button>
  </div>
</div>


<!-- datagrid -->
<table id="userList"></table>

<script type="text/javascript">

  //初始化数据表格
  $('#userList').bootstrapTable({
    url:"<%=request.getContextPath() %>user/selectUserList.jhtml",
    dataType:"json",
    //请求方式
    method:"post",
    //必须的，！！！！不然会造成中文乱码
    contentType: "application/x-www-form-urlencoded",
    //斑马线
    striped:true,

    queryParamsType:"",
    //工具条
    /* toolbar:"#book_tb", */
    //设置后台分页
    //sidePagination:"server",
    //开启搜索框
    /* search:true, */
    //显示刷新按钮
    showRefresh:true,
    columns: [
      {
      checkbox:true
      },
      {
        field: 'userID',
        title: 'id'
      },
      {
        field: 'userName',
        title: '账号'
      }



    ]
  });

</script>
</body>
</html>
