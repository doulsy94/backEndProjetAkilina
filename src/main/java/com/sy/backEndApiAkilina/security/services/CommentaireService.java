package com.sy.backEndApiAkilina.security.services;

import com.sy.backEndApiAkilina.configuration.ResponseMessage;
import com.sy.backEndApiAkilina.models.Commentaire;
import com.sy.backEndApiAkilina.models.Idee;
import com.sy.backEndApiAkilina.models.Ministere;
import com.sy.backEndApiAkilina.models.User;

import java.util.List;
import java.util.Optional;

public interface CommentaireService {

    //methode permettant d'ajouter un commentaire en fonction d'un idée
    String add(Commentaire commentaire, User user, Idee idee);

    //methode permettant de lire commentaire
    List<Commentaire> read();

    //methode permettant de modifier un commentaire
    String update(Long id_commentaire, Commentaire commentaire);
    //methode permettant de supprimer un commentaire
    String delete(Long id_commentaire);

   // List<Commentaire> AfficherCommentaireParIdee(Long id_idee);

    List<Commentaire> AfficherCommentaireParIdIdee(Idee idee);

    Optional<Commentaire> trouverCommentaireParID(long id_commentaire);

    //String SetEtat(Long id_commentaire);
}
