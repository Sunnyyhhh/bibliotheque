<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String erreur = (String) request.getAttribute("erreur");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Erreur</title>
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

        .error-message {
            color: #e53e3e;
            font-size: 1rem;
            font-weight: 500;
            background: #fff5f5;
            padding: 1rem;
            border-radius: 8px;
            margin-bottom: 1.5rem;
            line-height: 1.6;
        }

        a {
            color: #3182ce;
            text-decoration: none;
            font-weight: 500;
            display: inline-block;
            padding: 0.75rem 1.5rem;
            background: #edf2f7;
            border-radius: 8px;
            transition: background 0.3s ease, transform 0.2s ease, color 0.3s ease;
        }

        a:hover {
            color: #2b6cb0;
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
        <h1>Une erreur est survenue</h1>
        <p class="error-message">
            <%= erreur %>
        </p>
        <% if ("Nom ou mot de passe incorrect".equals(erreur)) { %>
            <p><a href="<%= request.getContextPath() %>/">Réessayer</a></p>
        <% } else if ("Aucun exemplaire trouvé pour ce livre".equals(erreur) ||
            "Date de naissance non définie pour l'utilisateur".equals(erreur)) { %>
            <p><a href="<%= request.getContextPath() %>/Livres/liste">Retour à la liste</a></p>
        <% } else { %>
            <p><a href="<%= request.getContextPath() %>/">Accueil</a></p>
        <% } %>
    </div>
</body>
</html>