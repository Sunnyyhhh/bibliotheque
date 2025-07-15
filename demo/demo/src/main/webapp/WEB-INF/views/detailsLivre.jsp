<!DOCTYPE html>
<html>
<head>
    <title>Details du Livre</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f7fa;
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

        .meta {
            font-style: italic;
            margin-bottom: 1.5rem;
            color: #4a5568;
        }

        .info {
            margin-bottom: 1rem;
        }

        .info strong {
            color: #2b6cb0;
        }

        ul {
            list-style: none;
            padding-left: 0;
        }

        ul li {
            padding: 0.5rem;
            border-bottom: 1px solid #e2e8f0;
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
    <div id="livre-details">
        <div class="loader"></div>
        <p>Chargement des details...</p>
    </div>
</div>

<%
    Integer idLivre = (Integer) request.getAttribute("idLivre");
    int id = idLivre != null ? idLivre : 0;
%>

<script>
    console.log("Debut du script JavaScript, idLivre: <%= id %>");

    if (<%= id %> === 0) {
        document.getElementById('livre-details').innerHTML = '<p class="error">Erreur : ID du livre non fourni.</p>';
        throw new Error("ID du livre non fourni");
    }

    fetch(`${window.location.origin}/DetailExemplaire/Detail`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `id_livre=<%= id %>`
    })
    .then(response => {
        console.log("Reponse reçue, statut: " + response.status);
        if (!response.ok) {
            throw new Error("Erreur HTTP: " + response.status);
        }
        return response.json();
    })
    .then(data => {
        console.log("Donnees JSON reçues: ", data);
        
        // Tests specifiques pour chaque propriete
        /*console.log("data.titre:", data.titre);
        console.log("data.auteur:", data.auteur);
        console.log("data.categorie:", data.categorie);
        console.log("data.theme:", data.theme);
        console.log("data.nb:", data.nb);
        console.log("data.stock:", data.stock);
        console.log("data.details:", data.details);*/

        const container = document.getElementById('livre-details');

        // Extraction simple des valeurs avec verification
        const titre = String(data.titre || "Inconnu");
        const auteur = String(data.auteur || "Inconnu");
        const categorie = String(data.categorie || "Non specifie");
        const theme = String(data.theme || "Non specifie");
        const nb = String(data.nb !== undefined ? data.nb : 0);
        const stock = String(data.stock !== undefined ? data.stock : 0);

        //console.log("Valeurs extraites:", {titre, auteur, categorie, theme, nb, stock});
        //console.log("Types des valeurs:", typeof titre, typeof auteur, typeof categorie, typeof theme, typeof nb, typeof stock);

        let listeDetails = '';
        if (data.details && Array.isArray(data.details) && data.details.length > 0) {
            data.details.forEach(d => {
                const ref = String(d.ref || "N/A");
                const statut = String(d.statut || "N/A");
                listeDetails += "<li><strong>Ref :</strong> "+ref+" | <strong>Statut :</strong> "+statut+"</li>";
            });
        } else {
            listeDetails = '<li>Aucun detail d\'exemplaire trouve.</li>';
        }

        //console.log("Liste des details generee:", listeDetails);

        const htmlContent = 
            '<h2>' + titre + '</h2>' +
            '<p class="meta">par ' + auteur + '</p>' +
            '<div class="info"><strong>Categorie :</strong> ' + categorie + '</div>' +
            '<div class="info"><strong>Thème :</strong> ' + theme + '</div>' +
            '<div class="info"><strong>Nombre d\'exemplaires :</strong> ' + nb + '</div>' +
            '<div class="info"><strong>Stock disponible :</strong> ' + stock + '</div>' +
            '<h3>Details des exemplaires :</h3>' +
            '<ul>' + listeDetails + '</ul>' +
            '<p><a href="' + window.location.origin + '/Livres/liste">Retour à la liste des livres</a></p>';

        //console.log("HTML genere:", htmlContent);
        container.innerHTML = htmlContent;
        //console.log("HTML assigne au container");
    })
    .catch(err => {
        console.error("Erreur dans fetch: ", err);
        document.getElementById('livre-details').innerHTML = '<p class="error">Une erreur est survenue : ' + err.message + '</p>';
    });
</script>
</body>
</html>