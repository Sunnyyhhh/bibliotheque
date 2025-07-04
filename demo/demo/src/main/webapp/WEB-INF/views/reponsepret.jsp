<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.demo.entities.Livre" %>
<%@ page import="com.example.demo.entities.Utilisateur" %>
<%@ page import="com.example.demo.entities.Exemplaire" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html>
<head>
    <title>Résultat de l'emprunt</title>
    <link rel="stylesheet" href="/css/styleResultPret.css">
</head>
<body>
    <h2>Résultat de l'emprunt</h2>

    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
            if (message.equals("Emprunt effectue avec succes")) {
    %>
                <h3 class="success"><%= message %></h3>
    <%
            } else if (message.equals("Echec de l'emprunt") || message.equals("Echec de la reservation")) {
    %>
                <h3 class="error"><%= message %></h3>
                <ul>
                    <%
                        String erreurDisponibilite = (String) request.getAttribute("messageDisponibilite");
                        String erreurAge = (String) request.getAttribute("messageAge");
                        String erreurQuotaGeneral = (String) request.getAttribute("messageQuotaGeneral");
                        String erreurQuotaMaison = (String) request.getAttribute("messageQuotaMaison");
                        String erreurPermissionMaison = (String) request.getAttribute("messageHome");
                        String erreurActivite = (String) request.getAttribute("messageAdhesion");
                        String erreurPenalisation = (String) request.getAttribute("messagePenalisation");

                        if (erreurDisponibilite != null) {
                    %>
                            <li class="error"><%= erreurDisponibilite %></li>
                    <%
                        }
                        if (erreurAge != null) {
                    %>
                            <li class="error"><%= erreurAge %></li>
                    <%
                        }
                        if (erreurQuotaGeneral != null) {
                    %>
                            <li class="error"><%= erreurQuotaGeneral %></li>
                    <%
                        }
                        if (erreurQuotaMaison != null) {
                    %>
                            <li class="error"><%= erreurQuotaMaison %></li>
                    <%
                        }
                        if (erreurPermissionMaison != null) {
                    %>
                            <li class="error"><%= erreurPermissionMaison %></li>
                    <%
                        }
                        if (erreurActivite != null) {
                    %>
                            <li class="error"><%= erreurActivite %></li>
                    <%
                        }
                        if (erreurPenalisation != null) {
                    %>
                            <li class="error"><%= erreurPenalisation %></li>
                    <%
                        }
                    %>
                </ul>
    <%
            } else {
    %>
                <h3 class="error">Statut de l'emprunt inconnu</h3>
    <%
            }
        }
    %>


    <p><a href="${pageContext.request.contextPath}/Livres/liste">Retour à la liste des livres</a></p>
</body>
</html>