<html>
    <head>
        <script type="text/javascript" src="/js/jquery.min.js"></script>
    </head>
    <body>
    <input type="button" onclick="addUser()" value="增加">

        <table border="1px">
            <tr>
                <td>id</td>
                <td>用户名</td>
            </tr>
            <#list map.list as item>
                <tr
                    <#if item.userID % 2 ==0>
                        style="background-color: aqua"
                    <#else >
                        style="background-color: bisque "
                    </#if>>
                    <td>${item.userID}</td>
                    <td>${item.userName}</td>
                    <td><input type="button" onclick="updateUser(${item.userID })" value="修改"></td>
                    <td><input type="button" onclick="deleteUserById(${item.userID })" value="删除"></td>
                </tr>

            </#list>

        </table>

    <script>
        function addUser(){
            location.href="toAddPage.jhtml"
        }

        function deleteUserById(userId){
            alert(userId);
            location.href="deleteUser.jhtml?userID="+userId;
        }

    </script>
    </body>
</html>