<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Basic Maven WebApp</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <script>
    window.addEventListener("load",function(){
        alert("Hello world")
    })
    </script>
</head>
<body>
    <h1 id="test">Welcome to My Basic Maven WebApp using Tomcat 11 and JSP!</h1>
    <p>Current time: <%= new java.util.Date() %></p>
    <a href="hello">Testing js and css</a>
</body>
</html>
