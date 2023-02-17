package com.sy.backEndApiAkilina.serviceImpl;

import com.sy.backEndApiAkilina.configuration.ResponseMessage;
import com.sy.backEndApiAkilina.models.Commentaire;
import com.sy.backEndApiAkilina.models.Idee;
import com.sy.backEndApiAkilina.models.Ministere;
import com.sy.backEndApiAkilina.models.User;
import com.sy.backEndApiAkilina.repository.CommentaireRepository;
import com.sy.backEndApiAkilina.repository.IdeeRepository;
import com.sy.backEndApiAkilina.security.services.CommentaireService;
import com.sy.backEndApiAkilina.security.services.WordFilterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//Annotation permettant de gérer les problèmes de constructeur pour tous les champs
@AllArgsConstructor
@Service

public class CommentaireServiceImpl implements CommentaireService {
    private final CommentaireRepository commentaireRepository;

    private final WordFilterService wordFilterService;
    private final IdeeRepository ideeRepository;

    @Override
    public String add(Commentaire commentaire, User user, Idee idee) {
        Boolean filteredContent = wordFilterService.filterCommentaire(commentaire);
        if (filteredContent)
            return "Veuillez utiliser des mots approprié";
        else {
            Date date= new Date();
            commentaire.setUser(user);
            commentaire.setIdee(idee);
            commentaire.setDate(date);
            commentaireRepository.save(commentaire);
            return "Commentaire ajouté avec succès";
        }
    }

    @Override
    public List<Commentaire> read() {
        return commentaireRepository.findAll();
    }

    @Override
    public String update(Long id_commentaire, Commentaire commentaire) {
        if(!wordFilterService.filterCommentaire(commentaire)){
            return commentaireRepository.findById(id_commentaire)
                            .map(commentaire1 -> {
                            commentaire1.setContenu_commentaire(commentaire.getContenu_commentaire());
                                commentaireRepository.save(commentaire1);
                                return "Commentaire modifier avec succes";
                            }).orElseThrow();
        }else {
            return "Veuillez utiliser des mots appropriés";
        }
    }

    @Override
    public String delete(Long id_commentaire) {
        commentaireRepository.deleteById(id_commentaire);
        return "Commentaire supprimé avec succès";
    }

    @Override
    public List<Commentaire> AfficherCommentaireParIdIdee(Idee idee) {
        return commentaireRepository.findByIdee(idee);
    }

    @Override
    public Optional<Commentaire> trouverCommentaireParID(long id_commentaire) {
        return commentaireRepository.findById(id_commentaire);
    }


    /*  @Override
    public ResponseMessage SetEtat(Commentaire commentaire, Long id_commentaire) {

        Optional<Commentaire> commentaire1 = commentaireRepository.findById(id_commentaire);
        if(commentaire1.isPresent()){
            Commentaire commentaire2 = commentaireRepository.findById(id_commentaire).get();
            commentaire2.setEtat(commentaire.isEtat());
            this.commentaireRepository.save(commentaire2);
            ResponseMessage message = new ResponseMessage("Commentaire modifiée avec succès !", true);
            return message;
        }
        else {
            ResponseMessage message = new ResponseMessage("Commentaire non modifiés !", false);
            return message;


        }
    }*/

}

