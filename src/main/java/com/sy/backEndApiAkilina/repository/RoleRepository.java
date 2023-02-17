package com.sy.backEndApiAkilina.repository;

import com.sy.backEndApiAkilina.models.ERole;
import com.sy.backEndApiAkilina.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(ERole name);


}
