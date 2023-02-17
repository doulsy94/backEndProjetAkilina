package com.sy.backEndApiAkilina.controllers;

import com.sy.backEndApiAkilina.configuration.AudioConfig;
import com.sy.backEndApiAkilina.configuration.SaveImage;
import com.sy.backEndApiAkilina.models.FichierVocal;
import com.sy.backEndApiAkilina.models.Ministere;
import com.sy.backEndApiAkilina.models.User;
import com.sy.backEndApiAkilina.repository.FichierVocalRepository;
import com.sy.backEndApiAkilina.repository.MinistereRepository;
import com.sy.backEndApiAkilina.repository.UserRepository;
import com.sy.backEndApiAkilina.security.services.FichierVocalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Api(value = "vocal", description = "MANIPULATION DES DONNEES DE LA TABLE VOCAL")
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:8100"}, maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/vocal")

@AllArgsConstructor
public class FichierVocalController {
    private final UserRepository userRepository;
    private final FichierVocalService fichierVocalService;

    private final MinistereRepository ministereRepository;
    private final FichierVocalRepository fichierVocalRepository;

    @ApiOperation(value = "Ajout de vocal")
    @PostMapping("/ajouter/{id_user}/{id_ministere}")
    public String add(@RequestParam(value = "file", required = false) MultipartFile file,
                      @PathVariable("id_user") Long id_user,
                      @PathVariable("id_ministere") Long id_ministere) throws Exception {
        try {
            System.err.println("hello");
           // String uploadDir = "C:\\xampp\\htdocs\\apiakilina\\vocal";

            File convFile = new File(file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
           // AudioConfig.saveAudio(uploadDir, convFile);

            FichierVocal fichierVocal = new FichierVocal();
            User user = userRepository.findById(id_user).get();
            Ministere ministere = ministereRepository.findById(id_ministere).get();
            fichierVocal.setUser(user);

            fichierVocal.setMinistere(ministere);
            if (file != null) {
                System.out.println("ggggg");
                fichierVocal.setFileName(SaveImage.save("vocal", file, file.getOriginalFilename()));
            }
            //fichierVocal.setFileName(file.getOriginalFilename());
            fichierVocalService.add(fichierVocal, id_user, id_ministere);


        } catch (Exception e) {
            System.err.println(e.getMessage());
            return e.getMessage();
        }
        return "enregistrer avec succès";
    }


    @ApiOperation(value = "Lire les vocales de l'utilisateur")
    @GetMapping("/lire")
    public List<FichierVocal> read() {
        return fichierVocalService.read();
    }

    @ApiOperation(value = "Supprimer vocal de l'utilisateur")
    @DeleteMapping("/supprimer/{id}/{id_user}")
    public String delete(@PathVariable Long id, @PathVariable Long id_user) {
        try {
            FichierVocal fichierVocal = fichierVocalRepository.findById(id).get();

            if (fichierVocal.getUser().getId_user() == id_user) {
                return fichierVocalService.delete(id);
            } else {
                return "vous n'etes pas autorisé à faire cette action";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ApiOperation(value = "Affichage des vocales par libelle du ministere")
    @GetMapping("/afficherVocalParLibelleMinistere/{libelle}")
    public List<FichierVocal> AfficherVocalParLibelleMinistere(@PathVariable String libelle) {
        return fichierVocalService.AfficherVocalParLibelleMinistere(libelle);
    }

    @ApiOperation(value = "Affichage des vocales par Id ministere")
    @GetMapping("/afficherVocalParIdMinistere/{id_ministere}")
    public Object AfficherVocalParIdMinistere(@PathVariable long id_ministere) {
        try {
            Ministere ministere = ministereRepository.findById(id_ministere).get();
            System.out.println(ministere);
            return fichierVocalService.AfficherVocalParIdMinistere(ministere);
        }catch (Exception e){
           return e.getMessage();
        }

    }

}
