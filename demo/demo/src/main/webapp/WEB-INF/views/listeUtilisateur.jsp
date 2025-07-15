<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.entities.Utilisateur" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Utilisateurs</title>
    <style>
        /* Copié de ta page précédente */
        body {
            background: linear-gradient(135deg, #e6ecf3 0%, #d1d9e6 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 1.5rem;
            font-family: 'Inter', sans-serif;
        }

        .container {
            background: #fff;
            padding: 2rem;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
            max-width: 900px;
            width: 100%;
            text-align: center;
        }

        h2 {
            font-size: 2rem;
            margin-bottom: 2rem;
        }

        .user-list {
            list-style: none;
            padding: 0;
        }

        .user-item {
            background: #f8fafc;
            border: 1px solid #e2e8f0;
            border-radius: 12px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
            text-align: left;
            transition: 0.3s;
        }

        .user-item:hover {
            background: #f1f5f9;
            border-color: #cbd5e0;
        }

        .user-name {
            font-size: 1.4rem;
            font-weight: 600;
            color: #1a202c;
        }

        .form-group {
            margin-top: 1rem;
        }

        input[type="submit"] {
            background: #2b6cb0;
            color: white;
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 10px;
            font-weight: 600;
            cursor: pointer;
            transition: 0.3s;
        }

        input[type="submit"]:hover {
            background: #2c5282;
        }

        .return-link {
            margin-top: 2rem;
        }

        .return-link a {
            color: #2b6cb0;
            text-decoration: none;
            font-weight: bold;
        }

        .no-users {
            background: #fff5f5;
            padding: 1rem;
            border: 1px solid #fed7d7;
            border-radius: 10px;
            color: #c53030;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Liste des Utilisateurs</h2>
        <ul class="user-list">
            <%
                List<Utilisateur> utilisateurs = (List<Utilisateur>) request.getAttribute("utilisateurs");
                if (utilisateurs != null && !utilisateurs.isEmpty()) {
                    for (Utilisateur u : utilisateurs) {
            %>
            <li class="user-item">
                <div class="user-name"><%= u.getNom() %></div>
                <p>Statut : <%= u.getStatut() != null ? u.getStatut() : "Non défini" %></p>
                <p>Date de naissance : <%= u.getDtn() != null ? u.getDtn() : "Inconnue" %></p>
                <div class="form-group">
                   <form action="${pageContext.request.contextPath}/utilisateurs/detailsPage" method="get">
                        <input type="hidden" name="id" value="<%= u.getIdUtilisateur() %>">
                        <input type="submit" value="Voir les détails">
                    </form>
                </div>
            </li>
            <%
                    }
                } else {
            %>
            <li class="no-users">Aucun utilisateur trouvé.</li>
            <%
                }
            %>
        </ul>
        <div class="return-link">
            <a href="${pageContext.request.contextPath}/Livres/retourLivre">Retour accueil</a>
        </div>
    </div>
</body>
</html>
