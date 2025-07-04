<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.demo.entities.Livre" %>
<%@ page import="com.example.demo.entities.Utilisateur" %>
<%@ page import="com.example.demo.entities.Exemplaire" %>
<!DOCTYPE html>
<html>
<head>
    <title>Page details exemplaire</title>
</head>
<body>
    <h1>Bienvenue sur details exemplaires</h1>
    <% Integer ex=(Integer) request.getAttribute("stock") ;%>

    <p>
        <% if (ex!=null) {%>
            <%= ex%>
        <% } else {%>
            Aucun detail trouve
        <% }%>
    </p>

    <% String stat=(String) request.getAttribute("statutFinal") ;%>

    <p>
        <% if (stat!=null) {%>
            <%= stat%>
        <% } else {%>
            Statut impossible a verifier
        <% }%>
    </p>
    <a href="${pageContext.request.contextPath}/Livres/liste">Retour a la liste</a>
</body>
</html>
