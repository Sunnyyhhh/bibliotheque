<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.example.demo.entities.Reservation" %>
<%@ page import="com.example.demo.entities.Utilisateur" %>
<%@ page import="com.example.demo.entities.Exemplaire" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Réservations</title>
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

        .reservation-list {
            list-style: none;
            display: flex;
            flex-direction: column;
            gap: 1.5rem;
        }

        .reservation-item {
            background: #f7fafc;
            padding: 1.5rem;
            border-radius: 8px;
            text-align: left;
            transition: background 0.3s ease;
        }

        .reservation-item:hover {
            background: #edf2f7;
        }

        .reservation-details {
            color: #2d3748;
            font-size: 1rem;
            margin-bottom: 1rem;
            line-height: 1.6;
        }

        .reservation-details strong {
            font-weight: 600;
        }

        .form-group {
            display: flex;
            align-items: center;
            gap: 1rem;
            flex-wrap: wrap;
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

        .no-reservations {
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
        <h2>Liste des Réservations</h2>
        <ul class="reservation-list">
            <%
                List<Reservation> Reservations = (List<Reservation>) request.getAttribute("reservations");
                if (Reservations != null && !Reservations.isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    for (int i=0;i<Reservations.size();i++) {
            %>
                <% Reservation resa=Reservations.get(i);%>
                <li class="reservation-item">
                        <div class="reservation-details">
                            <strong><%= resa.getUtilisateur().getNom() %></strong><br>
                            <p>
                                Reservation effectuee le : <%= resa.getDateAction() %>
                            </p>
                        </div>
                </li>
            <%
                    }
                } else {
            %>
                <li class="no-reservations">Aucune réservation n'a actuellement été trouvee</li>
            <%
                }
            %>
        </ul>
        <p class="return-link">
            <a href="${pageContext.request.contextPath}/Reservations/listeReservation">Retour</a>
        </p>
    </div>
</body>
</html>