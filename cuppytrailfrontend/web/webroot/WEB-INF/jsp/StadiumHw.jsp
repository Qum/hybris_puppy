<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html>
<head>

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script language="JavaScript">

    function delete_stadium(stadiumPk) {
        $.ajax({
            url: "/cuppytrailfrontend/stadiums/remove/"+stadiumPk,
            type: "GET",

            success: function(d) {
                if(d==="isLast") {
                    var forceDelete = confirm("Точно удалить последний стадион?");
                    if (forceDelete){
                        $.ajax({
                            url: "/cuppytrailfrontend/stadiums/remove/"+stadiumPk,
                            method: "GET",
                            data: { accept: "true"},
                            success: function() {
                                document.getElementById("spisok").innerHTML = 'Stadiums not found';
                            }
                        });
                    }
                } else if (d==="trouble"){
                    alert("Ошибка в базе данных!");
                } else if (d==="OK"){
                    var elem = document.getElementById('deleteButt-'+stadiumPk);
                    elem.parentNode.removeChild(elem);
                }
                    },

            error: function() {
                alert( "Sorry, there was a problem!" );
            }
        });
    }

</script>
    </head>
<title>Stadium L1sting</title>
<body>
<h1>Stadium Listing</h1>
<ul id="spisok">
    <c:choose>
        <c:when test="${fn:length(stadiums) gt 0}">
            <c:forEach var="stadium" items="${stadiums}">
                <li id="deleteButt-${stadium.pk}">
                    <a   href="./stadiums/${stadium.name}">${stadium.name} </a> <input type="button" value="Remove" onclick="delete_stadium(${stadium.pk});">
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
              Stadiums not found
        </c:otherwise>
    </c:choose>

</ul>
</body>
</html>