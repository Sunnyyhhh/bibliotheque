select *
from detail_exemplaire 
where id_livre = 1
and statut = 'Disponible' 
and id_detail_exemplaire =
(select min(id_detail_exemplaire)
from detail_exemplaire
where id_livre=1
and statut='Disponible');