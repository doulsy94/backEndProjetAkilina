package com.sy.backEndApiAkilina.serviceImpl;

import com.sy.backEndApiAkilina.models.*;
import com.sy.backEndApiAkilina.repository.FichierVocalRepository;
import com.sy.backEndApiAkilina.repository.MinistereRepository;
import com.sy.backEndApiAkilina.repository.UserRepository;
import com.sy.backEndApiAkilina.security.services.FichierVocalService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FichierVocalServiceImpl implements FichierVocalService {
    @Autowired
    private FichierVocalRepository fichierVocalRepository;
    @Autowired
    private final UserRepository userRepository;
    private final MinistereRepository ministereRepository;

    public void add(FichierVocal fichierVocal, Long id_user, Long id_ministere) {
      Optional<User> user =   userRepository.findById(id_user);
        Optional<Ministere> ministere =   ministereRepository.findById(id_ministere);

        Date date = new Date();
        Notification notification = new Notification();
        notification.setCreateur(user.get().getUsername());
        notification.setMinistere(ministere.get().getLibelle());
        notification.setDatenotif(date);
        notification.setFichierVocal(fichierVocal);

       /* if (fichierVocal.getUser().getId_user() == null) {
            return "L'utilisateur ne peut pas être vide";
        }*/
        fichierVocal.setNotification(notification);
        fichierVocal.setDateCreation(date);
        fichierVocal.setUser(user.get());
        fichierVocal.setMinistere(ministere.get());
        fichierVocalRepository.save(fichierVocal);

        //return "Vocal enregistrer avec succès";
    }

    @Override
    public List<FichierVocal> read() {
        return fichierVocalRepository.findAll();
    }


    @Override
    public String delete(Long id) {
        fichierVocalRepository.deleteById(id);
        return "Vocal supprimé avec succès";
    }

   /* @Override
    public FichierVocal trouverFichierVocalParId(Long id) {
        return null;
    }*/

    @Override
    public List<FichierVocal> AfficherVocalParLibelleMinistere(String libelle) {

        List<FichierVocal> fichierVocals =fichierVocalRepository.findAll();
        List<FichierVocal> fichierVocals1 = new ArrayList<>();
        for(FichierVocal fich:fichierVocals1){
            if(fich.getMinistere().getLibelle().equals(libelle)){
                fichierVocals1.add(fich);
            }
        }
        return  fichierVocals1;
    }

    @Override
    public List<FichierVocal> AfficherVocalParIdMinistere(Ministere ministere) {
        return fichierVocalRepository.findByMinistere(ministere);
    }

}
