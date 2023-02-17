package com.sy.backEndApiAkilina.repository;

import com.sy.backEndApiAkilina.models.Idee;
import com.sy.backEndApiAkilina.models.Role;
import com.sy.backEndApiAkilina.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // User findByEmailOrNumeroAndPassword(String emailOrNumero, String password);

    Optional<User> findByEmailOrNumero(String email, String numero);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByNumero (String numero);

    User findByRoles(Role role);

    User findByEmail(String email);

    User findByNumero(String numero);

    User findByLikedIdee(Long id);


}
