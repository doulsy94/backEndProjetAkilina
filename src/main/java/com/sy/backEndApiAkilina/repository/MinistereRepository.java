package com.sy.backEndApiAkilina.repository;
import com.sy.backEndApiAkilina.models.Ministere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinistereRepository extends JpaRepository <Ministere, Long>{

    // Methode de recherche spécifique en fonction du libelle
    Ministere findByLibelle(String libelle);
}
