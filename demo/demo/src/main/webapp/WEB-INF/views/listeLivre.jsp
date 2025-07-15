<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.entities.Livre" %>
<%@ page import="com.example.demo.entities.Utilisateur" %>
<%@ page import="com.example.demo.entities.Exemplaire" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Livres</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
        }

        body {
            background: linear-gradient(135deg, #e6ecf3 0%, #d1d9e6 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 1.5rem;
        }

        .container {
            background: #ffffff;
            padding: 2rem;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
            max-width: 900px;
            width: 100%;
            text-align: center;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .container:hover {
            transform: translateY(-3px);
            box-shadow: 0 12px 36px rgba(0, 0, 0, 0.1);
        }

        h2 {
            color: #1a202c;
            font-size: 2rem;
            font-weight: 700;
            margin-bottom: 2.5rem;
            letter-spacing: -0.02em;
        }

        .book-list {
            list-style: none;
            display: flex;
            flex-direction: column;
            gap: 1.75rem;
        }

        .book-item {
            background: #f8fafc;
            padding: 1.75rem;
            border-radius: 12px;
            text-align: left;
            border: 1px solid #e2e8f0;
            transition: background 0.3s ease, border-color 0.3s ease;
        }

        .book-item:hover {
            background: #f1f5f9;
            border-color: #cbd5e0;
        }

        .book-title {
            color: #1a202c;
            font-size: 1.4rem;
            font-weight: 600;
            margin-bottom: 1.25rem;
        }

        .form-group {
            display: flex;
            align-items: center;
            gap: 1.25rem;
            margin-bottom: 1.25rem;
            flex-wrap: wrap;
        }

        label {
            color: #2d3748;
            font-size: 1rem;
            font-weight: 500;
        }

        input[type="date"] {
            padding: 0.75rem;
            border: 1px solid #d1d9e6;
            border-radius: 10px;
            font-size: 1rem;
            color: #1a202c;
            background: #ffffff;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }

        input[type="date"]:focus {
            outline: none;
            border-color: #2b6cb0;
            box-shadow: 0 0 0 4px rgba(43, 108, 176, 0.15);
        }

        input[type="submit"] {
            background: #2b6cb0;
            color: #ffffff;
            padding: 0.75rem 1.5rem;
            border: none;
            border-radius: 10px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.3s ease, transform 0.2s ease, box-shadow 0.3s ease;
        }

        input[type="submit"]:hover {
            background: #2c5282;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(44, 82, 130, 0.2);
        }

        .no-books {
            color: #2d3748;
            font-size: 1.1rem;
            font-weight: 500;
            background: #fef5f5;
            padding: 1.25rem;
            border-radius: 12px;
            margin-bottom: 1.75rem;
            border: 1px solid #fed7d7;
        }

        .return-link {
            margin-top: 2rem;
            font-size: 1rem;
        }

        .return-link a {
            color: #2b6cb0;
            text-decoration: none;
            font-weight: 600;
            padding: 0.75rem 2rem;
            background: #f1f5f9;
            border-radius: 10px;
            transition: background 0.3s ease, transform 0.2s ease, color 0.3s ease;
        }

        .return-link a:hover {
            color: #2c5282;
            background: #e2e8f0;
            transform: translateY(-2px);
        }

        @media (max-width: 480px) {
            .container {
                padding: 1.25rem;
            }

            h2 {
                font-size: 1.75rem;
            }

            .book-item {
                padding: 1.25rem;
            }

            .form-group {
                flex-direction: column;
                align-items: flex-start;
                gap: 0.75rem;
            }

            input[type="submit"] {
                width: 100%;
                text-align: center;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Liste des Livres</h2>
        <ul class="book-list">
            <%
                List<Livre> Livres = (List<Livre>) request.getAttribute("livres");
                if (Livres != null && !Livres.isEmpty()) {
                    for (Livre Livre : Livres) {
            %>
                <li class="book-item">
                    <div class="book-title"><%= Livre.getTitre() %></div>
                    <%
                        Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
                        if (user != null) {
                    %>
                    <% if (user.getStatut() != null) { %>
                        <div class="details">

                            <a href="${pageContext.request.contextPath}/DetailExemplaire/detailsPage?id=<%= Livre.getIdLivre() %>">
                                <button type="button">Voir les détails</button> 
                            </a>
                        </div>
                    <% } %>
                    <% }%>
                    <div class="detail"></div>
                    <form action="${pageContext.request.contextPath}/Reservations/reserver" method="post">
                        <div class="form-group">
                            <label for="dt-reserve-<%= Livre.getIdLivre() %>">À réserver pour le :</label>
                            <input type="hidden" name="id_livre" value="<%= Livre.getIdLivre() %>">
                            <input type="date" id="dt-reserve-<%= Livre.getIdLivre() %>" name="dt" required>
                            <label for="dt-reserveAction-<%= Livre.getIdLivre() %>">Date de réservation :</label>
                            <input type="date" id="dt-reserveAction-<%= Livre.getIdLivre() %>" name="dtAction" required>
                            <input type="submit" value="Réserver">
                        </div>
                    </form>
                    <form action="${pageContext.request.contextPath}/Exemplaires/EmprunterMaison" method="post">
                        <div class="form-group">
                            <label for="dt-home-<%= Livre.getIdLivre() %>">Date d'emprunt :</label>
                            <input type="hidden" name="id_livre" value="<%= Livre.getIdLivre() %>">
                            <input type="date" id="dt-home-<%= Livre.getIdLivre() %>" name="dt" required>
                            <input type="submit" value="Emprunter à domicile">
                        </div>
                    </form>
                    <form action="${pageContext.request.contextPath}/Exemplaires/EmprunterPlace" method="post">
                        <div class="form-group">
                            <label for="dt-place-<%= Livre.getIdLivre() %>">Date d'emprunt :</label>
                            <input type="hidden" name="id_livre" value="<%= Livre.getIdLivre() %>">
                            <input type="date" id="dt-place-<%= Livre.getIdLivre() %>" name="dt" required>
                            <input type="submit" value="Emprunter sur place">
                        </div>
                    </form>
                </li>
            <%
                    }
                } else {
            %>
                <li class="no-books">Aucun livre trouvé.</li>
            <%
                }
            %>
        </ul>
        <p class="return-link">
            <a href="${pageContext.request.contextPath}/Livres/retourLivre">Retour accueil</a>
        </p>
    </div>
</body>
</html>