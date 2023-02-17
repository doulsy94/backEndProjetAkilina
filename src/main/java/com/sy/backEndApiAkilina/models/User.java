package com.sy.backEndApiAkilina.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Data
@Table(name = "users",
uniqueConstraints = {
        @UniqueConstraint(columnNames = "numero"),
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Long id_user;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 12)
    private String numero;

    private String addresse;

    private String imageuser;


    @NotBlank
    @Size(max=120)
    private String password;

    @NotBlank
    @Size(max=120)
    private String confirmPassword;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY )
    @JoinTable(name = "users_liked_idee",
            uniqueConstraints = @UniqueConstraint(name = "false", columnNames = {"liked_idee_id_idee"}),
            joinColumns = @JoinColumn(name = "user_id_user",unique = false),
            inverseJoinColumns = @JoinColumn(name = "liked_idee_id_idee",unique = false))
    private List<Idee> likedIdee;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY )

    private List<Idee> dislikedIdee;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public User(){

    }

    public User(String username, String email, String numero, String addresse, String password, String confirmPassword){
        this.username = username;
        this.email = email;
        this.numero = numero;
        this.addresse = addresse;
        this.password = password;
        this.confirmPassword = confirmPassword;

    }


    public Long getId_user() {
        return id_user;
    }
    public void setId_user(Long id) {
        this.id_user = id_user;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Idee> getLikedIdee() {
        return likedIdee;
    }

    public String getImageuser() {
        return imageuser;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
