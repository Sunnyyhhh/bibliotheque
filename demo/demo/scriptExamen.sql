--table ferie
delete from ferie;
INSERT INTO ferie(date) VALUES 
    ('2025-07-26'),
    ('2025-07-19');


--gestion des livres 
--table theme
INSERT INTO theme (libelle) VALUES
    ('Philosophie'),
    ('Littérature classique'),
    ('Jeunesse / Fantastique');

--table categorie_livre
INSERT INTO categorie_livre (age_minimum, nom_categorie) VALUES
    (0, 'Jeunesse'),
    (12, 'Adolescent'),
    (18, 'Adulte'),
    (16, 'Young Adult'),
    (0, 'Tout public');

--table livre
-- Les Misérables : catégorie = 3 (Adulte), thème = 2 (Littérature classique)
INSERT INTO livre (titre, id_categorie_livre, id_theme, auteur) VALUES
    ('Les Misérables', 3, 2, 'Victor Hugo');

-- L'Étranger : catégorie = 3 (Adulte), thème = 1 (Philosophie)
INSERT INTO livre (titre, id_categorie_livre, id_theme, auteur) VALUES
    ('L Etranger', 3, 1, 'Albert Camus');

-- Harry Potter à l'école des sorciers : catégorie = 1 (Jeunesse), thème = 3 (Jeunesse / Fantastique)
INSERT INTO livre (titre, id_categorie_livre, id_theme, auteur) VALUES
    ('Harry Potter à l''école des sorciers', 1, 3, 'J.K. Rowling');

-- Les Misérables (id_livre = 1)
UPDATE livre
SET isbn = '9782070409189'
WHERE id_livre = 1;

-- Harry Potter à l'école des sorciers (id_livre = 2)
UPDATE livre
SET isbn = '9782070643026'
WHERE id_livre = 2;

-- L'Étranger (id_livre = 3)
UPDATE livre
SET isbn = '9782070360022'
WHERE id_livre = 3;

--langue
update livre 
set langue='Francais';

--table detail exemplaire
-- Les Misérables (id_livre = 1)
INSERT INTO detail_exemplaire (ref, statut, id_livre) VALUES
    ('MIS001', 'Disponible', 1),
    ('MIS002', 'Disponible', 1),
    ('MIS003', 'Disponible', 1);

-- L'Étranger (id_livre = 3)
INSERT INTO detail_exemplaire (ref, statut, id_livre) VALUES
    ('ETR001', 'Disponible', 3),
    ('ETR002', 'Disponible', 3);

-- Harry Potter à l'école des sorciers (id_livre = 2)
INSERT INTO detail_exemplaire (ref, statut, id_livre) VALUES
    ('HAR001', 'Disponible', 2);

--table exemplaire
INSERT INTO exemplaire (nb, stock, id_livre) VALUES
    (3, 3, 1),   
    (2, 2, 3),   
    (1, 1, 2);  

--table adherent
INSERT INTO adherent (nb_jour_emprunt, nom_adherent, quota, quota_maison,nb_penalite) VALUES
    (7, 'Etudiant', 2, 2,10),       
    (9, 'Enseignant', 3, 3,9),     
    (12, 'Professionnel', 4, 4,8);

update adherent set nb_penalite=10 where nom_adherent='Etudiant';
update adherent set nb_penalite=9 where nom_adherent='Enseignant';
update adherent set nb_penalite=8 where nom_adherent='Professionnel'; 

--table parametre emprunt
-- Étudiant (id_adherent = 1)
INSERT INTO parametre_emprunt (mode_emprunt, nb_jour, id_adherent, take_home, id_livre, nb_prolongement) VALUES
('DOMICILE', 7, 1, TRUE, 1, 3),
('DOMICILE', 7, 1, TRUE, 2, 3),
('DOMICILE', 7, 1, TRUE, 3, 3);

-- Enseignant (id_adherent = 2)
INSERT INTO parametre_emprunt (mode_emprunt, nb_jour, id_adherent, take_home, id_livre, nb_prolongement) VALUES
('DOMICILE', 9, 2, TRUE, 1, 5),
('DOMICILE', 9, 2, TRUE, 2, 5),
('DOMICILE', 9, 2, TRUE, 3, 5);

-- Professionnel (id_adherent = 3)
INSERT INTO parametre_emprunt (mode_emprunt, nb_jour, id_adherent, take_home, id_livre, nb_prolongement) VALUES
('DOMICILE', 12, 3, TRUE, 1, 7),
('DOMICILE', 12, 3, TRUE, 2, 7),
('DOMICILE', 12, 3, TRUE, 3, 7);

--sur place
-- Étudiant (id_adherent = 1)
INSERT INTO parametre_emprunt (mode_emprunt, nb_jour, id_adherent, take_home, id_livre, nb_prolongement)
VALUES ('SUR_PLACE', 7, 1, TRUE, NULL, 3);

-- Enseignant (id_adherent = 2)
INSERT INTO parametre_emprunt (mode_emprunt, nb_jour, id_adherent, take_home, id_livre, nb_prolongement)
VALUES ('SUR_PLACE', 9, 2, TRUE, NULL, 5);

-- Professionnel (id_adherent = 3)
INSERT INTO parametre_emprunt (mode_emprunt, nb_jour, id_adherent, take_home, id_livre, nb_prolongement)
VALUES ('SUR_PLACE', 12, 3, TRUE, NULL, 7);

--table utilisateur
INSERT INTO utilisateur (quota_perso, quota_perso_maison, statut, dtn, mot_de_passe, nb_prolongement_perso, nom, id_adherent) VALUES
  (2, 2, NULL, '2000-01-01 00:00:00', 'mdp', 3, 'Amine Bensaïd', 1),
  (2, 2, NULL, '2000-01-01 00:00:00', 'mdp', 3, 'Sarah El Khattabi', 1),
  (2, 2, NULL, '2000-01-01 00:00:00', 'mdp', 3, 'Youssef Moujahid', 1),
  (3, 3, NULL, '2000-01-01 00:00:00', 'mdp', 5, 'Nadia Benali', 2),
  (3, 3, NULL, '2000-01-01 00:00:00', 'mdp', 5, 'Karim Haddadi', 2),
  (3, 3, NULL, '2000-01-01 00:00:00', 'mdp', 5, 'Salima Touhami', 2),
  (4, 4, NULL, '2000-01-01 00:00:00', 'mdp', 7, 'Rachid El Mansouri', 3),
  (4, 4, NULL, '2000-01-01 00:00:00', 'mdp', 7, 'Amina Zerouali', 3);

--admin
INSERT INTO utilisateur (quota_perso, quota_perso_maison, statut, dtn, mot_de_passe, nb_prolongement_perso, nom, id_adherent) VALUES
(4, 4, NULL, '2000-01-01 00:00:00', 'mdpadmin', 7, 'AdminUser', 3);

--alter noms 
UPDATE utilisateur SET nom = 'AmineBensaid' WHERE id_utilisateur = 1;
UPDATE utilisateur SET nom = 'SarahElKhattabi' WHERE id_utilisateur = 2;
UPDATE utilisateur SET nom = 'YoussefMoujahid' WHERE id_utilisateur = 3;
UPDATE utilisateur SET nom = 'NadiaBenali' WHERE id_utilisateur = 4;
UPDATE utilisateur SET nom = 'KarimHaddadi' WHERE id_utilisateur = 5;
UPDATE utilisateur SET nom = 'SalimaTouhami' WHERE id_utilisateur = 6;
UPDATE utilisateur SET nom = 'RachidElMansouri' WHERE id_utilisateur = 7;
UPDATE utilisateur SET nom = 'AminaZerouali' WHERE id_utilisateur = 8;



  --update les num_adherent
  UPDATE utilisateur SET num_adherent = CASE id_utilisateur
    WHEN 1 THEN 'ETU001'
    WHEN 2 THEN 'ETU002'
    WHEN 3 THEN 'ETU003'
    WHEN 4 THEN 'ENS001'
    WHEN 5 THEN 'ENS002'
    WHEN 6 THEN 'ENS003'
    WHEN 7 THEN 'PROF001'
    WHEN 8 THEN 'PROF002'
    WHEN 9 THEN 'AD001'
    END
    WHERE id_utilisateur BETWEEN 1 AND 9;

--table abonnement
INSERT INTO abonnement (debut, fin, id_utilisateur) VALUES
  ('2025-02-01 00:00:00', '2025-07-24 00:00:00', 1),  
  ('2025-02-01 00:00:00', '2025-07-01 00:00:00', 2), 
  ('2025-04-01 00:00:00', '2025-12-01 00:00:00', 3),  
  ('2025-07-01 00:00:00', '2026-07-01 00:00:00', 4),
  ('2025-08-01 00:00:00', '2026-05-01 00:00:00', 5),  
  ('2025-07-01 00:00:00', '2026-06-01 00:00:00', 6),  
  ('2025-06-01 00:00:00', '2025-12-01 00:00:00', 7), 
  ('2024-10-01 00:00:00', '2025-06-01 00:00:00', 8);  



--BACKUP 
bibliotheque=# select *from parametre_emprunt;
 id_parametre_emprunt | mode_emprunt | nb_jour | id_adherent | take_home | id_livre | nb_prolongement
----------------------+--------------+---------+-------------+-----------+----------+-----------------
                   82 | SUR_PLACE    |       0 |           1 | t         |          |               3
                   88 | DOMICILE     |      15 |           1 | t         |        1 |               3
                   89 | DOMICILE     |      15 |           1 | t         |        2 |               3
                   90 | DOMICILE     |      15 |           1 | t         |        3 |               3
                   91 | DOMICILE     |      15 |           1 | t         |        4 |               3
                   92 | DOMICILE     |      15 |           1 | t         |        5 |               3
                   83 | SUR_PLACE    |       0 |           2 | t         |          |               3
                   93 | DOMICILE     |      12 |           2 | t         |        1 |               3
                   94 | DOMICILE     |      12 |           2 | t         |        2 |               3
                   95 | DOMICILE     |      12 |           2 | t         |        3 |               3
                   96 | DOMICILE     |      12 |           2 | t         |        4 |               3
                   97 | DOMICILE     |      12 |           2 | t         |        5 |               3
                   84 | SUR_PLACE    |       0 |           3 | t         |          |               2
                   98 | DOMICILE     |      10 |           3 | t         |        1 |               2
                   99 | DOMICILE     |      10 |           3 | t         |        2 |               2
                  100 | DOMICILE     |      10 |           3 | t         |        3 |               2
                  101 | DOMICILE     |      10 |           3 | t         |        4 |               2
                  102 | DOMICILE     |      10 |           3 | t         |        5 |               2
                   85 | SUR_PLACE    |       0 |           4 | t         |          |               1
                  103 | DOMICILE     |       7 |           4 | t         |        1 |               1
                  104 | DOMICILE     |       7 |           4 | t         |        2 |               1
                  105 | DOMICILE     |       7 |           4 | t         |        3 |               1
                  106 | DOMICILE     |       7 |           4 | t         |        4 |               1
                  107 | DOMICILE     |       7 |           4 | t         |        5 |               1
                   86 | SUR_PLACE    |       0 |           5 | t         |          |               0
                  108 | DOMICILE     |       5 |           5 | t         |        1 |               0
                  109 | DOMICILE     |       5 |           5 | t         |        2 |               0
                  110 | DOMICILE     |       5 |           5 | t         |        3 |               0
                  111 | DOMICILE     |       5 |           5 | t         |        4 |               0
                  112 | DOMICILE     |       5 |           5 | t         |        5 |               0
                   87 | SUR_PLACE    |       0 |           6 | t         |          |              20
                  113 | DOMICILE     |      20 |           6 | t         |        1 |              20
                  114 | DOMICILE     |      20 |           6 | t         |        2 |              20
                  115 | DOMICILE     |      20 |           6 | t         |        3 |              20
                  116 | DOMICILE     |      20 |           6 | t         |        4 |              20
                  117 | DOMICILE     |      20 |           6 | t         |        5 |              20
(36 rows)