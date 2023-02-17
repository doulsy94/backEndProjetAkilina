package com.sy.backEndApiAkilina.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_notification;
    private Date datenotif;

    private String createur;

    private String ministere;

    private String imagecreateur;


    @OneToOne(cascade = CascadeType.ALL)
    private  Idee idee;

    @OneToOne(cascade = CascadeType.ALL)
    private  FichierVocal fichierVocal;


}
