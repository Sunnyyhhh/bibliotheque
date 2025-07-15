<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Détails de l'utilisateur</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f4f8;
            padding: 2rem;
            color: #2d3748;
        }

        .container {
            background: white;
            padding: 2rem;
            border-radius: 12px;
            max-width: 700px;
            margin: auto;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
        }

        h2 {
            font-size: 2rem;
            margin-bottom: 0.5rem;
        }

        h3 {
            margin-top: 2rem;
            font-size: 1.4rem;
            color: #2b6cb0;
        }

        .info {
            margin-bottom: 1rem;
        }

        .info strong {
            color: #2b6cb0;
        }

        .error {
            color: red;
            font-weight: bold;
        }

        .loader {
            border: 4px solid #f3f3f3;
            border-top: 4px solid #2b6cb0;
            border-radius: 50%;
            width: 30px;
            height: 30px;
            animation: spin 1s linear infinite;
            margin: 0 auto;
        }

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    </style>
</head>
<body>
<div class="container">
    <div id="user-details">
        <div class="loader"></div>
        <p>Chargement des détails de l'utilisateur...</p>
    </div>
</div>

<%
    Integer idUser = (Integer) request.getAttribute("id");
    int id = idUser != null ? idUser : 0;
%>

<script>
    //console.log("Début du script JavaScript, idUser: <%= id %>");

    if (<%= id %> === 0) {
        document.getElementById('user-details').innerHTML = '<p class="error">Erreur : ID utilisateur non fourni.</p>';
        throw new Error("ID utilisateur non fourni");
    }

    fetch(`${window.location.origin}<%= request.getContextPath() %>/utilisateurs/Detail`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `id_utilisateur=<%= id %>`
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Erreur HTTP: " + response.status);
        }
        return response.json();
    })
    .then(data => {
        const container = document.getElementById('user-details');

        const nom = String(data.nom || "Inconnu");
        const statut = String(data.statut || "Non défini");
        const dtn = String(data.dtn || "Non précisée");
        const quotaPerso = String(data.quotaPerso != null ? data.quotaPerso : 0);
        const quotaPersoMaison = String(data.quotaPersoMaison != null ? data.quotaPersoMaison : 0);
        const nbProlongement = String(data.nbProlongement != null ? data.nbProlongement : 0);

        let htmlContent = 
            '<h2>' + nom + '</h2>' +
            '<div class="info"><strong>Statut :</strong> ' + statut + '</div>' +
            '<div class="info"><strong>Date de naissance :</strong> ' + dtn + '</div>' +
            '<div class="info"><strong>Quota personnel restant:</strong> ' + quotaPerso + '</div>' +
            '<div class="info"><strong>Quota maison restant :</strong> ' + quotaPersoMaison + '</div>' +
            '<div class="info"><strong>Prolongements restants :</strong> ' + nbProlongement + '</div>';

        if (data.adherent) {
            const nomAdherent = String(data.adherent.nom || "Inconnu");
            const quotaAdherent = String(data.adherent.quota != null ? data.adherent.quota : 0);
            const quotaMaisonAdherent = String(data.adherent.quotaMaison != null ? data.adherent.quotaMaison : 0);
            const nbJourEmprunt = String(data.adherent.nbJourEmprunt != null ? data.adherent.nbJourEmprunt : 0);

            htmlContent +=
                '<h3>Statut adherent</h3>' +
                '<div class="info"><strong>Nom adhérent :</strong> ' + nomAdherent + '</div>' +
                '<div class="info"><strong>Quota :</strong> ' + quotaAdherent + '</div>' +
                '<div class="info"><strong>Quota maison :</strong> ' + quotaMaisonAdherent + '</div>' +
                '<div class="info"><strong>Jours d\'emprunt :</strong> ' + nbJourEmprunt + '</div>';
        }

        htmlContent += '<p><a href="' + window.location.origin + '<%= request.getContextPath() %>/utilisateurs/liste">← Retour à la liste</a></p>';

        container.innerHTML = htmlContent;
    })
    .catch(err => {
        console.error("Erreur dans fetch: ", err);
        document.getElementById('user-details').innerHTML = '<p class="error">Une erreur est survenue : ' + err.message + '</p>';
    });
</script>
</body>
</html>
