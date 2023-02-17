package com.sy.backEndApiAkilina.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "fichier_vocal")
public class FichierVocal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


   @Column(nullable = false)
    private String fileName;


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_creation")
    private Date dateCreation;

    @ManyToOne
    private User user;

    @ManyToOne()
    private Ministere ministere;

    @JsonIgnore
    @OneToOne(mappedBy = "fichierVocal", cascade = CascadeType.ALL)
    private Notification notification;


}
