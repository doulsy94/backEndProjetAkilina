package com.sy.backEndApiAkilina.security.services;

import com.sy.backEndApiAkilina.models.Ministere;

import java.util.List;
import java.util.Optional;

public interface MinistereService {

    //methode permettant de creer un ministère
    Ministere add(Ministere ministere);

    //methode permettant de lire ministère
    List<Ministere> read();

    //methode permettant de modifier un ministere
    Ministere update(Long id_ministere, Ministere ministere);
    //methode permettant de supprimer un ministere
    String delete(Long id_ministere);
    //methode permettant de trouver ministere par libelle
    Ministere trouverMinistereParLibelle(String libelle);

    //methode permettant de trouver ministere par id
    Optional<Ministere> trouverMinistereParID(long id_ministere);
}
