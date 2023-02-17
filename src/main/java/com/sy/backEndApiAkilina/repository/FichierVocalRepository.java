package com.sy.backEndApiAkilina.repository;

import com.sy.backEndApiAkilina.models.FichierVocal;
import com.sy.backEndApiAkilina.models.Idee;
import com.sy.backEndApiAkilina.models.Ministere;
import com.sy.backEndApiAkilina.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FichierVocalRepository extends JpaRepository <FichierVocal, Long> {
    List<FichierVocal> findByUser(User user);

    public List<FichierVocal> findByMinistere(Ministere ministere);



}
