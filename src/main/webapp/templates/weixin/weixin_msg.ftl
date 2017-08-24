<html>
<head>
    <meta charset="UTF-8">
    <title>金科教育</title>
    <script type="text/javascript" src="/js/jquery.min.js"></script>
</head>
<body>
<h1>群发微信公众号图片消息</h1>

<form action="/weixin/weixinSendImageMsg.jhtml", method="post" enctype="multipart/form-data">
    选择文件：<input type="file" name="multipartFile"><br>
    <input type="submit">
</form>

</body>
<script>
    $("h3").each(function(index, value) {
        console.log($(this).html());
    })
</script>
</html>