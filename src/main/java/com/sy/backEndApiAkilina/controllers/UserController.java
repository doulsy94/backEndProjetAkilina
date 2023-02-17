package com.sy.backEndApiAkilina.controllers;

import com.sy.backEndApiAkilina.models.User;
import com.sy.backEndApiAkilina.repository.UserRepository;
import com.sy.backEndApiAkilina.security.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user")
@RestController
@Api(value = "user", description = "MANIPULATION DES DONNEES DE LA TABLE USER")
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:8100"}, maxAge = 3600, allowCredentials="true")
@AllArgsConstructor
public class UserController {

    final private UserService userService;
    private final UserRepository userRepository;

    @ApiOperation(value = "Affichage de tous les utilisateur")
    @GetMapping("/lire")
    public List<User> read() {
        return userService.read();
    }


    @ApiOperation(value = "Nombre de l'utilisateur")
    @GetMapping("/afficher_user_nombre")
    public int readNombre() {
        return userService.read().size();
    }

    @ApiOperation(value = "Supprimer l'utilisateur")
    @DeleteMapping("/suprimer_user/{id_user}")
    public String delete(@PathVariable Long id_user){
        return this.userService.delete(id_user);
    }

    @ApiOperation(value = "LIRE USER Par ID")
    @GetMapping("/lireParId/{id_user}")
    public Object readUserParID(@PathVariable("id_user") Long id_user) {
        try {
            return userRepository.findById(id_user);
        }catch (Exception e){
            return e.getMessage();
        }

    }

    @PostMapping("/modifieruser/{id_user}")
    public String update(@PathVariable Long id_user, @RequestBody User user){
        return userService.update(id_user, user);
    }

}
