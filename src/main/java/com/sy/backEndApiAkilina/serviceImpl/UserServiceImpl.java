package com.sy.backEndApiAkilina.serviceImpl;

import com.sy.backEndApiAkilina.configuration.SaveImage;
import com.sy.backEndApiAkilina.models.Ministere;
import com.sy.backEndApiAkilina.models.User;
import com.sy.backEndApiAkilina.repository.UserRepository;
import com.sy.backEndApiAkilina.security.services.UserService;
import com.sy.backEndApiAkilina.utils.EmailConstructor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;



@AllArgsConstructor
@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    private EmailConstructor emailConstructor;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JavaMailSender mailSender;
    private final UserRepository userRepository;



    @Override
    public List<User> read() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id_user) {
        return userRepository.findById(id_user).get();
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void resetPassword(User user) {
        String password = RandomStringUtils.randomAlphanumeric(10);
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        mailSender.send(emailConstructor.constructResetPasswordEmail(user, password));
    }

    @Override
    public void updateUserPassword(User user, String newPassword) {
        String encryptedPassword = bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(encryptedPassword);
        userRepository.save(user);
       // mailSender.send(emailConstructor.constructResetPasswordEmail(user, newPassword));

    }


    @Override
    public String delete(Long id_user) {
        try {
            Files.deleteIfExists(Paths.get(SaveImage.Userlocation + "/"+ id_user + ".png"));
            userRepository.deleteById(id_user);
        }catch (Exception e){

        }

        return "Utilisateur supprimé avec succès";
    }

    @Override
    public Optional<User> trouverParemailOrNumeroAndPass(String emailOrNumero, String password) {
        return userRepository.findByEmailOrNumero(emailOrNumero, password);
    }

    @Override
    public User findByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    @Override
    public User ajouter(User user) {
        userRepository.save(user);
        mailSender.send(emailConstructor.constructNewUserEmail(user));
        return user;
    }

    @Override
    public String saveUserImage(MultipartFile multipartFile, Long userImageId) {
        byte[] bytes;
        try {
            Files.deleteIfExists(Paths.get("C:/xampp/htdocs/apiakilina/images/utilisateur"+ userImageId + ".png"));
            bytes = multipartFile.getBytes();
            Path path = Paths.get("C:/xampp/htdocs/apiakilina/images/utilisateur/" + userImageId + ".png");
            Files.write(path, bytes);
            return "User picture saved to server";
        } catch (IOException e) {
            return "User picture Saved";
        }
    }

    @Override
    public Optional<User> trouverUserParID(long id_user) {
        return userRepository.findById(id_user);
    }

    @Override
    public String update(Long id_user, User user) {

        return userRepository.findById(id_user)
                .map(user1 -> {
                    user1.setEmail(user.getEmail());
                    user1.setNumero(user.getNumero());
                    user1.setAddresse(user.getAddresse());
                    userRepository.save(user1);
                    return "Utilisateur modifier avec succèes";
                }).orElseThrow();
    }

}