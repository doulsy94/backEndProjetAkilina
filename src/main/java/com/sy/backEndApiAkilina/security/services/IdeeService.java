package com.sy.backEndApiAkilina.security.services;

import com.sy.backEndApiAkilina.models.Idee;
import com.sy.backEndApiAkilina.models.Ministere;
import com.sy.backEndApiAkilina.models.User;

import java.util.HashMap;
import java.util.List;

public interface IdeeService {

    //methode permettant d'ajouter une idéé en fonction d'un ministere
    String add(Idee idee, User user, Ministere ministere);

    //String add(Idee idee, HashMap<String, String> request, User user, Ministere ministere);

    //methode permettant de lire les idees
    List<Idee> read();

    //methode permettant de modifier une idee
    String update(Long id_idee, Idee idee);
    //methode permettant de supprimer une idee
    String delete(Long id_idee);
    //methode permettant d'afficher les idées par libellé de ministere
    List<Idee> AfficherIdeeParLibelleMinistere(String libelle);

    //methode permettant d'afficher les idées par id de ministere
    List<Idee> AfficherIdeeParIdMinistere(Ministere ministere);

    //methode permettant de trouver idee par id
    Idee trouverIdeeParID(long id_idee);

    //ResponseMessage SetEtat(Idee idee, Long id_idee);
}
