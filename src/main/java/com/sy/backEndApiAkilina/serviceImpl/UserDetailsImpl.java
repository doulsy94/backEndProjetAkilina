package com.sy.backEndApiAkilina.serviceImpl;

import com.sy.backEndApiAkilina.models.User;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails{
    private static final long serialVersionUID = 1L;

    private Long id_user;

    private String username;

    private String numero;


    private String email;

    private String addresse;

    @JsonIgnore
    private String password;


    public Collection<? extends GrantedAuthority> authorities ;

    public UserDetailsImpl(Long id_user, String username, String email, String numero, String addresse, String password,
                           Collection<? extends GrantedAuthority> authorities){
        this.id_user = id_user;
        this.username = username;
        this.email = email;
        this.numero = numero;
        this.addresse = addresse;
        this.password = password;
        this.authorities = authorities;
    }

    public  static UserDetailsImpl build(User user){

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId_user(),
                user.getUsername(),
                user.getEmail(),
                user.getNumero(),
                user.getAddresse(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }

    public Long getId_user(){
        return id_user;
    }

    public String getEmail(){
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getAddresse() {
        return addresse;
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id_user, user.id_user);
    }
}
