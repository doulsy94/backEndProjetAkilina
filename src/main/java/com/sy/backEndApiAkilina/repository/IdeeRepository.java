package com.sy.backEndApiAkilina.repository;

import com.sy.backEndApiAkilina.models.Idee;
import com.sy.backEndApiAkilina.models.Ministere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IdeeRepository extends JpaRepository <Idee, Long>{
    public List<Idee> findByMinistere(Ministere ministere);

    Idee findById(long id_idee);

    @Query(value = "SELECT COUNT(user_id_user) FROM users_liked_idee ul WHERE ul.user_id_user =:id_user AND ul.liked_idee_id_idee =:id_idee ",nativeQuery = true)
    int nombreLikeParUtilisateurSurIde(Long id_user, Long id_idee);

    @Query(value = "SELECT COUNT(user_id_user) FROM users_disliked_idee ul WHERE ul.user_id_user =:id_user AND ul.disliked_idee_id_idee =:id_idee ",nativeQuery = true)
    int nombreDisLikeParUtilisateurSurIde(Long id_user, Long id_idee);

    @Modifying
    @Transactional
   @Query(value = "Delete from users_liked_idee where users_liked_idee.user_id_user=:id_user and users_liked_idee.liked_idee_id_idee=:id_idee",nativeQuery = true)
    int nombreUnLikeParUtilisateurSurIde(Long id_user,Long id_idee);

    @Modifying
    @Transactional
    @Query(value = "Delete from users_disliked_idee where users_disliked_idee.user_id_user=:id_user and users_disliked_idee.disliked_idee_id_idee=:id_idee",nativeQuery = true)
    int nombreDisUnLikeParUtilisateurSurIde(Long id_user,Long id_idee);


}
