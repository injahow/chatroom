<%--
  Created by IntelliJ IDEA.
  User: injah
  Date: 2020/12/26
  Time: 21:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <title>online list</title>
</head>
<body>
<table>
    <tr>
        <td>在线名单(共${fn:length(onlineList)}人)</td>
    </tr>
    <c:forEach items="${onlineList}" var="i">
        <tr>
            <td>
                <a href="javascript:void(0)" onclick="add_user('${i.getAccount()}','${i.getName()}')">
                    ${i.getName()}(${i.getAccount()})
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>