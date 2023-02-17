package com.sy.backEndApiAkilina.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ministere")
@Getter
@Setter
@NoArgsConstructor
public class Ministere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_ministere;

    @Column(unique = true)
    private String libelle;

    private String description;

    private String image;

    @JsonIgnore
    @OneToMany(mappedBy = "ministere", cascade = CascadeType.ALL)
    List <Idee> idees;

    @JsonIgnore
    @OneToMany(mappedBy = "ministere", cascade = CascadeType.ALL)
    List <FichierVocal> fichierVocals;
    @ManyToOne()
    private User user;
}
