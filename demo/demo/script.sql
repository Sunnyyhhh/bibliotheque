-- Creation de la base
CREATE DATABASE bibliotheque;
\c bibliotheque

-- Table des categories d'adherents
CREATE TABLE adherent (
    id_adherent SERIAL PRIMARY KEY,
    nom_adherent VARCHAR(50) NOT NULL
);

-- Table des utilisateurs lies à un adherent
CREATE TABLE utilisateur (
    id_utilisateur SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL,
    id_adherent INT UNIQUE, 
    dtn TIMESTAMP,  -- PostgreSQL : datetime → timestamp
    CONSTRAINT fk_adherent FOREIGN KEY (id_adherent) REFERENCES adherent(id_adherent)
);

-- Table des categories de livres
CREATE TABLE categorie_livre (
    id_categorie_livre SERIAL PRIMARY KEY,
    age_minimum INT NOT NULL CHECK (age_minimum >= 0),
    nom_categorie VARCHAR(50) NOT NULL
);

-- Table des thèmes
CREATE TABLE theme (
    id_theme SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL
);

-- Table des livres
CREATE TABLE livre (
    id_livre SERIAL PRIMARY KEY,
    titre VARCHAR(100) NOT NULL,
    id_categorie_livre INT,
    id_theme INT,
    CONSTRAINT fk_categorie FOREIGN KEY (id_categorie_livre) REFERENCES categorie_livre(id_categorie_livre),
    CONSTRAINT fk_theme FOREIGN KEY (id_theme) REFERENCES theme(id_theme)
);

-- Table des exemplaires (copies physiques)
CREATE TABLE exemplaire (
    id_exemplaire SERIAL PRIMARY KEY,
    id_livre INT NOT NULL,
    nb INT NOT NULL CHECK (nb >= 0),
    stock int,
    CONSTRAINT fk_livre FOREIGN KEY (id_livre) REFERENCES livre(id_livre)
);

-- Table des types d'emprunts
CREATE TABLE type_emprunt (
    id_type_emprunt SERIAL PRIMARY KEY,
    nom_emprunt VARCHAR(50) NOT NULL
);

-- -----------------------
-- Donnees d'exemple
-- -----------------------

-- Types d'adherents
INSERT INTO adherent (nom_adherent) VALUES
('Professeur'),
('Particulier'),
('Professionnel'),
('Etudiant'),
('Anonyme');

-- Utilisateurs
INSERT INTO utilisateur (nom, mot_de_passe, id_adherent) VALUES
('martin.prof', 'prf', 1),
('marie.dubois', 'mdb', 2),
('tech.pro', 'tch', 3),
('lucie.etud', 'let', 4),
('anonyme001', 'anm', 5);

-- Categories de livres
INSERT INTO categorie_livre (age_minimum, nom_categorie) VALUES
(0, 'Jeunesse'),
(12, 'Adolescent'),
(18, 'Adulte'),
(16, 'Young Adult'),
(0, 'Tout public');

-- Thèmes
INSERT INTO theme (libelle) VALUES 
('Horreur'),
('Comedie'),
('Fantastique');

-- Livres (on suppose ici que les IDs de categorie et de thème existent dejà)
INSERT INTO livre (titre, id_categorie_livre, id_theme) VALUES
('Le Petit Prince', 1, 3),
('1984', 3, 1),
('Harry Potter à l ecole des sorciers', 2, 3),
('Les Miserables', 3, 2),
('L etranger', 3, 1);

-- Exemplaires
INSERT INTO exemplaire (nb, stock, id_livre) VALUES
(3, 2, 1),  -- Le Petit Prince : 3 exemplaires, 2 disponibles
(1, 1, 2),  -- 1984 : 1 exemplaire, tous dispo
(5, 4, 3),  -- Harry Potter : 5 exemplaires, 4 dispo
(2, 0, 4),  -- Les Miserables : tous empruntés
(4, 3, 5);  -- L'Étranger : 4 exemplaires, 3 dispo

-- Types d'emprunt
INSERT INTO type_emprunt (nom_emprunt) VALUES
('À domicile'),
('Sur place');


-- MODE SUR_PLACE : tout le monde peut emprunter sur place pour 2 jours

INSERT INTO parametre_emprunt (mode_emprunt, nb_jour, id_adherent, livre_id) VALUES
('SUR_PLACE', 2, 1, 1), ('SUR_PLACE', 2, 1, 2), ('SUR_PLACE', 2, 1, 3), ('SUR_PLACE', 2, 1, 4), ('SUR_PLACE', 2, 1, 5),
('SUR_PLACE', 2, 2, 1), ('SUR_PLACE', 2, 2, 2), ('SUR_PLACE', 2, 2, 3), ('SUR_PLACE', 2, 2, 4), ('SUR_PLACE', 2, 2, 5),
('SUR_PLACE', 2, 3, 1), ('SUR_PLACE', 2, 3, 2), ('SUR_PLACE', 2, 3, 3), ('SUR_PLACE', 2, 3, 4), ('SUR_PLACE', 2, 3, 5),
('SUR_PLACE', 2, 4, 1), ('SUR_PLACE', 2, 4, 2), ('SUR_PLACE', 2, 4, 3), ('SUR_PLACE', 2, 4, 4), ('SUR_PLACE', 2, 4, 5),
('SUR_PLACE', 2, 5, 1), ('SUR_PLACE', 2, 5, 2), ('SUR_PLACE', 2, 5, 3), ('SUR_PLACE', 2, 5, 4), ('SUR_PLACE', 2, 5, 5);

-- MODE DOMICILE : varie selon le quota_maison

INSERT INTO parametre_emprunt (mode_emprunt, nb_jour, id_adherent, livre_id) VALUES
('DOMICILE', 30, 1, 1), ('DOMICILE', 30, 1, 2), ('DOMICILE', 30, 1, 3), ('DOMICILE', 30, 1, 4), ('DOMICILE', 30, 1, 5),
('DOMICILE', 14, 2, 1), ('DOMICILE', 14, 2, 2), ('DOMICILE', 14, 2, 3), ('DOMICILE', 14, 2, 4), ('DOMICILE', 14, 2, 5),
('DOMICILE', 21, 3, 1), ('DOMICILE', 21, 3, 2), ('DOMICILE', 21, 3, 3), ('DOMICILE', 21, 3, 4), ('DOMICILE', 21, 3, 5),
('DOMICILE', 10, 4, 1), ('DOMICILE', 10, 4, 2), ('DOMICILE', 10, 4, 3), ('DOMICILE', 10, 4, 4), ('DOMICILE', 10, 4, 5),
('DOMICILE', 0, 5, 1), ('DOMICILE', 0, 5, 2), ('DOMICILE', 0, 5, 3), ('DOMICILE', 0, 5, 4), ('DOMICILE', 0, 5, 5);
