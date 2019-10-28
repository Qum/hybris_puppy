<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
<title>Stadium Listing</title>
<body>
<h1>Stadium Listing</h1>
<ul>
    <c:choose>
        <c:when test="${fn:length(stadiums) gt 0}">
            <c:forEach var="stadium" items="${stadiums}">
                <li>
                    <a href="./stadiums/${stadium.name}">${stadium.name} </a> <a href="./stadiums/remove/${stadium.pk}"
                                                                                 style="color:red">Remove</a>
                </li>
            </c:forEach>
            <br>
            <br>
            <br>
            <br>
            <br>
            <a href="./stadiums/removeall" style="color:red"> Remove All </a>
        </c:when>
        <c:otherwise>
            <br>
            Stadiums not found
            <br/>
        </c:otherwise>
    </c:choose>

</ul>
</body>
</html>