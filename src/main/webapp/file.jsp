
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath }/works/photoUpload" method="post" enctype="multipart/form-data">
        <h2>文件上传</h2>
        文件:<input type="file" name="file"/><br/><br/>
        <input type="submit" value="上传"/>
    </form>
</body>
</html>
