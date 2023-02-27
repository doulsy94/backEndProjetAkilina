package com.sy.backEndApiAkilina.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "idee")
@Getter
@Setter
@NoArgsConstructor
public class Idee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_idee;

    @Lob
    private String contenu_idee;

    private Date date;

    private int likes;

   //  private int dislikes;



    private boolean isclick;

    public boolean isIsclick() {
        return isclick;
    }



    // private boolean etat = true;


    @ManyToOne()
    private User user;

    @ManyToOne()
    private Ministere ministere;

    @JsonIgnore
    @OneToOne(mappedBy = "idee", cascade = CascadeType.ALL)
    private Notification notification;

   /* @JsonIgnore
    @OneToMany(mappedBy = "idee", cascade = CascadeType.ALL)
    List <Jaime> jaimes;

    @JsonIgnore
    @OneToMany(mappedBy = "idee", cascade = CascadeType.ALL)
    List<JaimePas> jaimePas;*/
    @JsonIgnore
    @OneToMany(mappedBy = "idee", cascade = CascadeType.ALL)
    List<Commentaire> commentaires;

}
