<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.demo.entities.Livre" %>
<%@ page import="com.example.demo.entities.Utilisateur" %>
<%@ page import="com.example.demo.entities.Exemplaire" %>
<!DOCTYPE html>
<html>
<head>
    <title>Page d'accueil</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
        }

        body {
            background: linear-gradient(135deg, #f5f7fa 0%, #e4e7eb 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .container {
            background: white;
            padding: 2.5rem;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            width: 100%;
            text-align: center;
            transition: transform 0.3s ease;
        }

        .container:hover {
            transform: translateY(-5px);
        }

        h1 {
            color: #2d3748;
            font-size: 1.8rem;
            font-weight: 700;
            margin-bottom: 2rem;
            letter-spacing: -0.025em;
        }

        p {
            color: #4a5568;
            font-size: 1rem;
            margin-bottom: 1.5rem;
            line-height: 1.6;
        }

        .error {
            color: #e53e3e;
            font-weight: 500;
            background: #fff5f5;
            padding: 1rem;
            border-radius: 8px;
            margin-bottom: 1.5rem;
        }

        a {
            color: #3182ce;
            text-decoration: none;
            font-weight: 500;
            transition: color 0.3s ease;
        }

        a:hover {
            color: #2b6cb0;
            text-decoration: underline;
        }

        .link-group {
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }

        .link-group a {
            display: inline-block;
            padding: 0.75rem;
            background: #edf2f7;
            border-radius: 8px;
            transition: background 0.3s ease, transform 0.2s ease;
        }

        .link-group a:hover {
            background: #e2e8f0;
            transform: translateY(-2px);
        }

        @media (max-width: 480px) {
            .container {
                padding: 1.5rem;
            }

            h1 {
                font-size: 1.5rem;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Bienvenue sur la bibliothèque</h1>
        <%
            Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
            if (user != null) {
        %>
                <p>Bienvenue, <%= user.getNom() %> !</p>
                <% if (user.getStatut() != null) { %>
                    <p>Statut : <%= user.getStatut() %></p>
                <% } %>
                <div class="link-group">
                    <a href="${pageContext.request.contextPath}/Livres/liste">Liste des livres</a>
                    <a href="${pageContext.request.contextPath}/Livres/listeEmprunte">Liste des livres empruntés</a>
                    <%
                        if (user.getStatut() != null && user.getStatut().equals("admin")) {
                    %>
                        <a href="${pageContext.request.contextPath}/Reservations/listeReservation">Liste des réservations</a>
                    <%
                        }
                    %>
                </div>
        <%
            } else {
        %>
                <p class="error">Vous n'êtes pas connecté. Veuillez vous connecter pour accéder à la bibliothèque.</p>
                <a href="${pageContext.request.contextPath}/login">Se connecter</a>
        <%
            }
        %>
    </div>
</body>
</html>