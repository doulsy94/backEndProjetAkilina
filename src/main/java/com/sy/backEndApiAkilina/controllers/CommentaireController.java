package com.sy.backEndApiAkilina.controllers;

import com.sy.backEndApiAkilina.configuration.ResponseMessage;
import com.sy.backEndApiAkilina.models.Commentaire;
import com.sy.backEndApiAkilina.models.ERole;
import com.sy.backEndApiAkilina.models.Idee;
import com.sy.backEndApiAkilina.models.User;
import com.sy.backEndApiAkilina.repository.CommentaireRepository;
import com.sy.backEndApiAkilina.repository.IdeeRepository;
import com.sy.backEndApiAkilina.repository.RoleRepository;
import com.sy.backEndApiAkilina.repository.UserRepository;
import com.sy.backEndApiAkilina.security.services.CommentaireService;
import com.sy.backEndApiAkilina.security.services.WordFilterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Api(value = "commentaire", description = "MANIPULATION DES DONNEES DE LA TABLE COMMENTAIRE")
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:8100"}, maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/commentaire")
@AllArgsConstructor
public class CommentaireController {

    @Autowired
     private final CommentaireService commentaireService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IdeeRepository ideeRepository;
    private CommentaireRepository commentaireRepository;

    private final RoleRepository roleRepository;

    private final WordFilterService wordFilterService;

    @ApiOperation(value = "Ajout de commentaire")
    @PostMapping("/ajouter/{id_user}/{id_idee}")
    public String add(@RequestBody Commentaire commentaire, @PathVariable("id_user") Long id_user, @PathVariable("id_idee") Long id_idee) {
       try {
        User user= userRepository.findById(id_user).get();
        Idee idee = ideeRepository.findById(id_idee).get();
        commentaire.setIdee(idee);
        commentaire.setUser(user);

        return commentaireService.add(commentaire, user, idee);
    }catch (Exception e){
           return e.getMessage();
       }
    }

    @ApiOperation(value = "Lire toutes les commentaires")
    @GetMapping("/lire")
    public List<Commentaire> read() {
        return commentaireService.read();
    }

    @ApiOperation(value = "Nombre de Commentaire")
    @GetMapping("/afficher_commentaire_nombre")
    public int readNombre() {return commentaireService.read().size();
    }

    @ApiOperation(value = "Modification des commentaires par id")
    @PostMapping ("/modifier/{id_commentaire}/{id_user}")
    public String update(@PathVariable(value = "id_commentaire") Long id_commentaire, @PathVariable("id_user") Long id_user, @RequestBody Commentaire commentaire){
       try {
           Commentaire commentaire1 = commentaireRepository.findById(id_commentaire).get();
           if (commentaire1.getUser().getId_user() == id_user) {
               return commentaireService.update(id_commentaire, commentaire);
           }else {
               return "vous n'etes pas autorisé à faire cette action";
           }
       }catch (Exception e){
           return "heloooo";
       }

    }

    @ApiOperation(value = "Suppression des commentaires par id")
    @DeleteMapping("/supprimer/{id_commentaire}/{id_user}")
    public String delete(@PathVariable Long id_commentaire, @PathVariable Long id_user) {
        try {
            Commentaire commentaire = commentaireRepository.findById(id_commentaire).get();

            User user = userRepository.findById(id_user).get();

            if (user.getRoles().contains(roleRepository.findByName(ERole.ROLE_ADMIN))) {
                commentaireService.delete(id_commentaire);
                return "Commentaire supprimee par l'admin avec succes! ";
            }

            else if (commentaire.getUser().getId_user() == id_user) {
                return commentaireService.delete(id_commentaire);
            } else {
                return "vous n'etes pas autorisé à faire cette action";
            }
        }catch (Exception e){
           return e.getMessage();
        }
    }

    @ApiOperation(value = "Affichage des commentaires par Id idee")
    @GetMapping("/afficherCommentaireParIdIdee/{id_idee}")
    public Object AfficherCommentaireParIdIdee(@PathVariable long id_idee) {
        try {
            Idee idee = ideeRepository.findById(id_idee);
            System.out.println(idee);
            return commentaireService.AfficherCommentaireParIdIdee(idee);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ApiOperation(value = "LIRE COMMENTAIRE Par ID")
    @GetMapping("/lireParId/{id_commentaire}")
    public ResponseEntity<?> readIdeeParID(@PathVariable("id_commentaire") Long id_commentaire) {
        try {
            return new ResponseEntity<>(commentaireRepository.findById(id_commentaire).get(), HttpStatus.OK) ;
        } catch (Exception e) {
            return new ResponseEntity<>("Commentaire non trouvé", HttpStatus.BAD_REQUEST);
        }

    }

  /*  @PatchMapping("/etat/{id_commentaire}/{id_user}")
    public ResponseMessage SetEtat(@RequestBody Commentaire commentaire, @PathVariable Long id_commentaire, @PathVariable Long id_user) {

        if (this.commentaireRepository.findById(id_commentaire) == null) {

            ResponseMessage message = new ResponseMessage("Commentaire n'existe pas !", false);
            return message;
        } else {


            return this.commentaireService.SetEtat(commentaire, id_commentaire);
        }

    }*/

}


