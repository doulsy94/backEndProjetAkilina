package com.sy.backEndApiAkilina.controllers;

import com.sy.backEndApiAkilina.models.*;
import com.sy.backEndApiAkilina.repository.*;
import com.sy.backEndApiAkilina.security.services.CommentaireService;
import com.sy.backEndApiAkilina.security.services.IdeeService;
import com.sy.backEndApiAkilina.security.services.WordFilterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(value = "idee", description = "MANIPULATION DES DONNEES DE LA TABLE IDEE")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8100"}, maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/idee")
@AllArgsConstructor
//@ToString
public class IdeeController {


    private final IdeeService ideeService;

    private final CommentaireService commentaireService;

    private final MinistereRepository ministereRepository;
    private final IdeeRepository ideeRepository;
    private final CommentaireRepository commentaireRepository;
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final WordFilterService wordFilterService;

    @ApiOperation(value = "Ajout d'idée")
    @PostMapping("/ajouter/{id_user}/{id_ministere}")
    public ResponseEntity<Object> add(@RequestBody Idee idee, @PathVariable("id_user") Long id_user, @PathVariable("id_ministere") Long id_ministere) {
        try {
            User user = userRepository.findById(id_user).get();
            Ministere ministere = ministereRepository.findById(id_ministere).get();
            idee.setUser(user);
            idee.setMinistere(ministere);

            return ResponseHandler.generateResponse("OK", HttpStatus.OK, ideeService.add(idee, user, ministere));

        } catch (Exception e) {
            return ResponseHandler.generateResponse("Non OK", HttpStatus.FORBIDDEN, "Veuillez utilisez des mots approprié");
        }

    }

    // Liste = read = lecture
    @ApiOperation(value = "Lire idée de l'utilisateur")
    @GetMapping("/lire")
    public List<Idee> read() {
        return ideeService.read();
    }

    @ApiOperation(value = "Nombre de IDEE")
    @GetMapping("/afficher_idee_nombre")
    public int readNombre() {
        return ideeService.read().size();
    }

    @ApiOperation(value = "Modifier idée de l'utilisateur")
    @PostMapping("/modifier/{id_idee}/{id_user}")
    public String update(@PathVariable Long id_idee, @PathVariable Long id_user, @RequestBody Idee idee) {
        try {
            Idee idee1 = ideeRepository.findById(id_idee).get();

            if (idee1.getUser().getId_user() == id_user) {
                return ideeService.update(id_idee, idee);
            } else {
                return "vous n'etes pas autorisé à faire cette action";
            }
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @ApiOperation(value = "Supprimer idée de l'utilisateur")
    @DeleteMapping("/supprimer/{id_idee}/{id_user}")
    public String delete(@PathVariable(value = "id_idee") Long id_idee, @PathVariable(value = "id_user") Long id_user) {
        try {
            Idee idee = ideeRepository.findById(id_idee).get();

            User user = userRepository.findById(id_user).get();

            if (user.getRoles().contains(roleRepository.findByName(ERole.ROLE_ADMIN))) {
                ideeService.delete(id_idee);
                return "idee supprimee par l'admin avec succes! ";
            } else if (idee.getUser().getId_user() == id_user) {
                return ideeService.delete(id_idee);
            } else {
                return "vous n'etes pas autorisé à faire cette action";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ApiOperation(value = "Affichage des idées par libelle du ministere")
    @GetMapping("/afficherIdeeParLibelleMinistere/{libelle}")
    public Object AfficherIdeeParLibelleMinistere(@PathVariable String libelle) {
        try {
            return ideeService.AfficherIdeeParLibelleMinistere(libelle);
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @ApiOperation(value = "Affichage des idées par Id ministere")
    @GetMapping("/afficherIdeeParIdMinistere/{id_ministere}")
    public Object AfficherIdeeParIdMinistere(@PathVariable long id_ministere) {
        try {
            Ministere ministere = ministereRepository.findById(id_ministere).get();
            System.out.println(ministere);
            return ideeService.AfficherIdeeParIdMinistere(ministere);
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @ApiOperation(value = "LIRE IDEE Par ID")
    @GetMapping("/lireParId/{id_idee}")
    public Object readIdeeParID(@PathVariable("id_idee") Long id_idee) {
        try {
            return ideeRepository.findById(id_idee);
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @ApiOperation(value = "Faire like")
    @PostMapping("/like/{id_user}/{id_idee}")
    public ResponseEntity<?> likeIdee(@PathVariable("id_user") Long id_user, @PathVariable("id_idee") Long id_idee){

        Idee idee = ideeService.trouverIdeeParID((id_idee));
        if (idee == null) {
            return new ResponseEntity<>("Idee not found", HttpStatus.NOT_FOUND);
        }
        User user = userRepository.findById(id_user).get();
        System.err.println(user.getId_user());
        if (user == null){
            return new ResponseEntity<>("User not found",  HttpStatus.NOT_FOUND);
        }
        System.err.println(ideeRepository.nombreLikeParUtilisateurSurIde(id_user,id_idee));

        if (ideeRepository.nombreLikeParUtilisateurSurIde(user.getId_user(),id_idee)>=1)
        {
            return ResponseEntity.ok().body("Vous ne pouvez pas aimer");
        }else {
            idee.setLikes(idee.getLikes()+1);
            idee.setIsclick(true);
            List<Idee> ideeList = user.getLikedIdee();
            ideeList.add(idee);
            user.setLikedIdee(ideeList);
            userRepository.save(user);
            return new ResponseEntity<>(ideeRepository.nombreLikeParUtilisateurSurIde(user.getId_user(),id_idee), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Faire unlike")
    @PostMapping("/unlike/{id_user}/{id_idee}")
    public ResponseEntity<?> unLikeIdee(@PathVariable("id_user") Long id_user, @PathVariable("id_idee") Long id_idee){

        Idee idee = ideeService.trouverIdeeParID((id_idee));
        if (idee == null) {
            return new ResponseEntity<>("Idee not found", HttpStatus.NOT_FOUND);
        }
        User user = userRepository.findById(id_user).get();
        if (user == null){
            return new ResponseEntity<>("User not found",  HttpStatus.NOT_FOUND);
        }
        System.err.println(ideeRepository.nombreLikeParUtilisateurSurIde(id_user,id_idee));

        if (ideeRepository.nombreLikeParUtilisateurSurIde(id_user,id_idee)==1)
        {
            idee.setLikes(idee.getLikes()-1);
            idee.setIsclick(false);
            System.out.println("ideeeeeeeeeeeeeeee");
            System.err.println(idee);
            List<Idee> ideeList = user.getLikedIdee();

            ideeList.add(idee);
            user.setLikedIdee(ideeList);
             ideeRepository.nombreUnLikeParUtilisateurSurIde(id_user, id_idee);

            userRepository.save(user);
            return new ResponseEntity<>(ideeRepository.nombreLikeParUtilisateurSurIde(id_user,id_idee), HttpStatus.OK);

        }else {
            return ResponseEntity.ok().body("Vous ne pouvez pas unlike");
        }
    }



/*    @ApiOperation(value = "Faire dislike")
    @PostMapping("/dislike/{id_user}/{id_idee}")
    public ResponseEntity<?> dislikeIdee(@PathVariable("id_user") Long id_user, @PathVariable("id_idee") Long id_idee){

        Idee idee = ideeService.trouverIdeeParID((id_idee));
        if (idee == null) {
            return new ResponseEntity<>("Idee not found", HttpStatus.NOT_FOUND);
        }
        User user = userRepository.findById(id_user).get();
        if (user == null){
            return new ResponseEntity<>("User not found",  HttpStatus.NOT_FOUND);
        }
        System.err.println(ideeRepository.nombreDisLikeParUtilisateurSurIde(id_user,id_idee));

        if (ideeRepository.nombreDisLikeParUtilisateurSurIde(id_user,id_idee)>=1)
        {
            return ResponseEntity.ok().body("Vous ne pouvez pas aimer");
        }else {
            idee.setDislikes(idee.getDislikes()+1);
            List<Idee> ideeList = user.getDislikedIdee();
            ideeList.add(idee);
            user.setDislikedIdee(ideeList);
            userRepository.save(user);
            return new ResponseEntity<>(idee, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Faire disunlike")
    @PostMapping("/disunlike/{id_user}/{id_idee}")
    public ResponseEntity<?> disunLikeIdee(@PathVariable("id_user") Long id_user, @PathVariable("id_idee") Long id_idee){

        Idee idee = ideeService.trouverIdeeParID((id_idee));
        if (idee == null) {
            return new ResponseEntity<>("Idee not found", HttpStatus.NOT_FOUND);
        }
        User user = userRepository.findById(id_user).get();
        if (user == null){
            return new ResponseEntity<>("User not found",  HttpStatus.NOT_FOUND);
        }
        System.err.println(ideeRepository.nombreDisLikeParUtilisateurSurIde(id_user,id_idee));

        if (ideeRepository.nombreDisLikeParUtilisateurSurIde(id_user,id_idee)==1)
        {
            idee.setDislikes(idee.getDislikes()-1);

            List<Idee> ideeList = user.getDislikedIdee();

            ideeList.add(idee);
            user.setDislikedIdee(ideeList);
            ideeRepository.nombreDisUnLikeParUtilisateurSurIde(id_user, id_idee);

            userRepository.save(user);
            return new ResponseEntity<>(idee, HttpStatus.OK);

        }else {
            return ResponseEntity.ok().body("Vous ne pouvez pas disunlike");
        }
    }*/
}

