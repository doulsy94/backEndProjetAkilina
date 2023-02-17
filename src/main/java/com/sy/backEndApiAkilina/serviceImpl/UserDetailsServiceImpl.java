package com.sy.backEndApiAkilina.serviceImpl;

import com.sy.backEndApiAkilina.models.User;
import com.sy.backEndApiAkilina.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String emailOrNumero) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrNumero(emailOrNumero, emailOrNumero)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable avec cet identifiant: " + emailOrNumero));
        return UserDetailsImpl.build(user);
    }


}
