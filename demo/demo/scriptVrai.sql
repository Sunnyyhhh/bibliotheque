-- Insertion des thèmes
INSERT INTO theme (libelle) VALUES
('Horreur'),
('Comedie'),
('Fantastique');

-- Insertion des catégories de livres
INSERT INTO categorie_livre (age_minimum, nom_categorie) VALUES
(0, 'Jeunesse'),
(12, 'Adolescent'),
(18, 'Adulte'),
(16, 'Young Adult'),
(0, 'Tout public');

-- Insertion des adhérents
INSERT INTO adherent (nom_adherent, quota, quota_maison, nb_jour_emprunt) VALUES
('Professeur', 15, 4, 15),
('Professionnel', 10, 4, 12),
('Particulier', 5, 2, 10),
('Etudiant', 5, 2, 7),
('Anonyme', 3, 2, 0),
('Bibliothecaire', 20, 20, 20);

-- Insertion des utilisateurs
INSERT INTO utilisateur (
    mot_de_passe, nom, id_adherent,
    quota_perso, dtn, quota_perso_maison, statut
) VALUES
('prf', 'martin.prof', 1, 15, '1990-05-15 00:00:00', 4, NULL),
('mdb', 'marie.dubois', 2, 10, '1985-08-22 00:00:00', 4, NULL),
('tch', 'tech.pro', 3, 5, '1978-03-10 00:00:00', 2, NULL),
('anm', 'anonyme001', 5, 3, '1995-07-01 00:00:00', 2, NULL),
('saka', 'mimi', 4, 5, '2005-11-10 00:00:00', 2, NULL),
('bbl', 'bibli.admin', 6, 20, '1980-01-01 00:00:00', 20, 'admin'),
('let', 'lucie.etud', 4, 5, '2019-11-10 00:00:00', 2, NULL);

-- Insertion des abonnements (id_utilisateur changé de 6 à 7)
INSERT INTO abonnement (id_abonnement, debut, fin, id_utilisateur) VALUES
(1, '2025-06-01 00:00:00', '2025-08-01 00:00:00', 7);

-- Insertion des livres
INSERT INTO livre (titre, id_categorie_livre, id_theme) VALUES
('Le Petit Prince', 1, 3),
('1984', 3, 1),
('Harry Potter à l''école des sorciers', 2, 3),
('Les Misérables', 3, 2),
('L''Étranger', 3, 1);

-- Insertion des exemplaires
INSERT INTO exemplaire (id_exemplaire, nb, stock, id_livre) VALUES
(1, 1, 1, 1),
(2, 5, 4, 2),
(3, 2, 0, 3),
(4, 4, 3, 4),
(5, 3, 8, 5);

-- Insertion des paramètres d'emprunt
INSERT INTO parametre_emprunt (mode_emprunt, nb_jour, id_adherent,take_home,id_livre) VALUES
-- si adherent 1 prends le livre 1 a la maison
('DOMICILE', 15, 1,true,1),
-- si adherent 1 prends le livre 2 a la maison
('DOMICILE', 15, 1,true,2),
-- si adherent 1 prends le livre 3 a la maison
('DOMICILE', 15, 1,true,3),
-- si adherent 1 prends le livre 4 a la maison
('DOMICILE', 15, 1,true,4),
-- si adherent 1 prends le livre 5 a la maison
('DOMICILE', 15, 1,true,5),
------------fin adherent 1
-- si adherent 2 prends le livre 1 a la maison
('DOMICILE', 12, 2,true,1),
-- si adherent 2 prends le livre 2 a la maison
('DOMICILE', 12, 2,true,2),
-- si adherent 2 prends le livre 3 a la maison
('DOMICILE', 12, 2,true,3),
-- si adherent 2 prends le livre 4 a la maison
('DOMICILE', 12, 2,true,4),
-- si adherent 2 prends le livre 5 a la maison
('DOMICILE', 12, 2,true,5),
-------------fin adherent 2
-- si adherent 3 prends le livre 1 a la maison
('DOMICILE', 10, 3,true,1),
-- si adherent 3 prends le livre 2 a la maison
('DOMICILE', 10, 3,false,2),
-- si adherent 3 prends le livre 3 a la maison
('DOMICILE', 10, 3,true,3),
-- si adherent 3 prends le livre 4 a la maison
('DOMICILE', 10, 3,false,4),
-- si adherent 3 prends le livre 5 a la maison
('DOMICILE', 10, 3,true,5),
-------------fin adherent 3
-- si adherent 4 prends le livre 1 a la maison
('DOMICILE', 7, 4,true,1),
-- si adherent 4 prends le livre 2 a la maison
('DOMICILE', 7, 4,false,2),
-- si adherent 4 prends le livre 3 a la maison
('DOMICILE', 7, 4,true,3),
-- si adherent 4 prends le livre 4 a la maison
('DOMICILE', 7, 4,false,4),
-- si adherent 4 prends le livre 5 a la maison
('DOMICILE', 7, 4,false,5),
---------------fin adherent 4
-- si adherent 5 prends le livre 1 a la maison
('DOMICILE', 5, 5,true,1),
-- si adherent 5 prends le livre 2 a la maison
('DOMICILE', 5, 5,false,2),
-- si adherent 5 prends le livre 3 a la maison
('DOMICILE', 5, 5,false,3),
-- si adherent 5 prends le livre 4 a la maison
('DOMICILE', 5, 5,false,4),
-- si adherent 5 prends le livre 5 a la maison
('DOMICILE', 5, 5,false,5),
--------------fin adherent 5
-- si adherent 6 prends le livre 1 a la maison
('DOMICILE', 20, 6,true,1),
-- si adherent 6 prends le livre 2 a la maison
('DOMICILE', 20, 6,true,2),
-- si adherent 6 prends le livre 3 a la maison
('DOMICILE', 20, 6,true,3),
-- si adherent 6 prends le livre 4 a la maison
('DOMICILE', 20, 6,true,4),
-- si adherent 6 prends le livre 5 a la maison
('DOMICILE', 20, 6,true,5);

INSERT INTO parametre_emprunt (mode_emprunt, nb_jour, id_adherent)
VALUES
('SUR_PLACE', 0, 1),
('SUR_PLACE', 0, 2),
('SUR_PLACE', 0, 3),
('SUR_PLACE', 0, 4),
('SUR_PLACE', 0, 5),
('SUR_PLACE', 0, 6);

-- Insertion des jours fériés
INSERT INTO ferie (date) VALUES
('2025-07-11');

-- Insertion des pénalités (id_utilisateur changé de 6 à 7)
INSERT INTO penalite (dt_debut, dt_fin, id_utilisateur) VALUES
('2025-07-01 00:00:00', '2025-07-10 23:59:59', 7);

-- Insertion des prêts (id_utilisateur changé de 6 à 7)
INSERT INTO pret (
    dt_emprunt,
    dt_retour,
    dt_retour_prevue,
    id_livre,
    id_utilisateur,
    id_parametre_emprunt
) VALUES (
    '2025-06-15 10:00:00',
    NULL,
    '2025-07-15 10:00:00',
    1,
    7,
    1
);

-- Insertion des réservations (id_utilisateur changé de 6 à 7)
INSERT INTO reservation ( date_reservation, id_livre, id_utilisateur) VALUES
( '2025-07-17 00:00:00', 1, 7);