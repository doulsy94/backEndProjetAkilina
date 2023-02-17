package com.sy.backEndApiAkilina.security.services;

import com.sy.backEndApiAkilina.models.Idee;
import com.sy.backEndApiAkilina.models.Ministere;
import com.sy.backEndApiAkilina.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {

    //methode permettant de lire utilisateur
    List<User> read();

    //methode permettant de modifier un utilisateur
    //String update(User user);

    //methode permettant de supprimer un utilisateur
    String delete(Long id_user);

    Optional<User> trouverParemailOrNumeroAndPass(String emailOrNumero, String password);

    User getById(Long id_user);
    User getByEmail(String email);

    public void resetPassword(User user);

    public void updateUserPassword(User user, String newPassword);

    User findByEmail(String userEmail);

    User ajouter(User user);

    String saveUserImage(MultipartFile multipartFile, Long userImageId);

    Optional<User> trouverUserParID(long id_user);

    String update(Long id_user, User user);

}
