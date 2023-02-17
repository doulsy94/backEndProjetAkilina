package com.sy.backEndApiAkilina.configuration;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class SaveImage {

    public static String localhost = "http://127.0.0.1/";
    public static String serveruser = localhost + "apiakilina/images/utilisateur/";
    public static String servercategorie = localhost + "apiakilina/images/categorie/";

    public static String servervocal = localhost + "apiakilina/vocal/categorie/";

    public static String Vocallocation = "C:/xampp/htdocs/apiakilina/vocal/categorie";
    public static String Categorielocation = "C:/xampp/htdocs/apiakilina/images/categorie";
    public static String Userlocation = "C:/xampp/htdocs/apiakilina/images/utilisateur";

    public static String save(String typeImage, MultipartFile file, String nomFichier) {
        String src = "";
        String server = "";
        String location = "";
        if (typeImage == "user") {
            location = Userlocation;
            server = serveruser;
        } else if (typeImage == "vocal") {
            location = Vocallocation;
            server = servervocal;
        }else{
            location = Categorielocation;
            server = servercategorie;

        }

        /// debut de l'enregistrement
        try {
            int index = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");

            Path chemin = Paths.get(location);
            if (!Files.exists(chemin)) {
                // si le fichier n'existe pas deja
                Files.createDirectories(chemin);
                Files.copy(file.getInputStream(), chemin
                        .resolve(nomFichier));
                src = server + nomFichier;
                        //+ file.getOriginalFilename().substring(index).toLowerCase();
            } else {
                // si le fichier existe pas deja
                String newPath = location + nomFichier;
                        //+ file.getOriginalFilename().substring(index).toLowerCase();
                Path newchemin = Paths.get(newPath);
                if (!Files.exists(newchemin)) {
                    // si le fichier n'existe pas deja
                    Files.copy(file.getInputStream(), chemin
                            .resolve(
                                    nomFichier ));
                    src = server + nomFichier;
                            //+ file.getOriginalFilename().substring(index).toLowerCase();
                } else {
                    // si le fichier existe pas deja on le suprime et le recrèe

                    Files.delete(newchemin);

                    Files.copy(file.getInputStream(), chemin
                            .resolve(
                                    nomFichier));
                    src = server + nomFichier;
                            //+ file.getOriginalFilename().substring(index).toLowerCase();
                }

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // TODO: handle exception
            src = null;
        }

        return src;
    }

}
