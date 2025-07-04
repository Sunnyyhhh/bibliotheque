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
            max-width: 800px;
            width: 100%;
            text-align: center;
            transition: transform 0.3s ease;
        }

        .container:hover {
            transform: translateY(-5px);
        }

        h2 {
            color: #2d3748;
            font-size: 1.8rem;
            font-weight: 700;
            margin-bottom: 2rem;
            letter-spacing: -0.025em;
        }

        .book-list {
            list-style: none;
            display: flex;
            flex-direction: column;
            gap: 1.5rem;
        }

        .book-item {
            background: #f7fafc;
            padding: 1.5rem;
            border-radius: 8px;
            text-align: left;
            transition: background 0.3s ease;
        }

        .book-item:hover {
            background: #edf2f7;
        }

        .book-title {
            color: #2d3748;
            font-size: 1.2rem;
            font-weight: 600;
            margin-bottom: 1rem;
        }

        .form-group {
            display: flex;
            align-items: center;
            gap: 1rem;
            margin-bottom: 1rem;
            flex-wrap: wrap;
        }

        label {
            color: #4a5568;
            font-size: 0.9rem;
            font-weight: 500;
        }

        input[type="date"] {
            padding: 0.75rem;
            border: 1px solid #e2e8f0;
            border-radius: 8px;
            font-size: 1rem;
            color: #2d3748;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }

        input[type="date"]:focus {
            outline: none;
            border-color: #3182ce;
            box-shadow: 0 0 0 3px rgba(49, 130, 206, 0.1);
        }

        input[type="submit"] {
            background: #3182ce;
            color: white;
            padding: 0.75rem 1rem;
            border: none;
            border-radius: 8px;
            font-size: 0.9rem;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.3s ease, transform 0.2s ease;
        }

        input[type="submit"]:hover {
            background: #2b6cb0;
            transform: translateY(-2px);
        }

        .no-books {
            color: #4a5568;
            font-size: 1rem;
            font-weight: 500;
            background: #fff5f5;
            padding: 1rem;
            border-radius: 8px;
            margin-bottom: 1.5rem;
        }

        .return-link {
            margin-top: 1.5rem;
            font-size: 0.9rem;
        }

        .return-link a {
            color: #3182ce;
            text-decoration: none;
            font-weight: 500;
            padding: 0.75rem 1.5rem;
            background: #edf2f7;
            border-radius: 8px;
            transition: background 0.3s ease, transform 0.2s ease, color 0.3s ease;
        }

        .return-link a:hover {
            color: #2b6cb0;
            background: #e2e8f0;
            transform: translateY(-2px);
        }

        @media (max-width: 480px) {
            .container {
                padding: 1.5rem;
            }

            h2 {
                font-size: 1.5rem;
            }

            .form-group {
                flex-direction: column;
                align-items: flex-start;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Liste des Livres</h2>
        <ul class="book-list">
            <%
                List<Livre> livres = (List<Livre>) request.getAttribute("livres");
                if (livres != null && !livres.isEmpty()) {
                    for (Livre book : livres) {
            %>
                <li class="book-item">
                    <div class="book-title"><%= book.getTitre() %></div>
                    <form action="${pageContext.request.contextPath}/Livres/rendre" method="post">
                        <div class="form-group">
                            <label for="dt-return-<%= book.getIdLivre() %>">Date de retour :</label>
                            <input type="hidden" name="idLivre" value="<%= book.getIdLivre() %>">
                            <input type="date" id="dt-return-<%= book.getIdLivre() %>" name="dt" required>
                            <input type="submit" value="Rendre">
                        </div>
                    </form>
                </li>
            <%
                    }
                } else {
            %>
                <li class="no-books">Aucun livre n'est actuellement emprunté.</li>
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