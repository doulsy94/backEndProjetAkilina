package com.sy.backEndApiAkilina.repository;

import com.sy.backEndApiAkilina.models.BadWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadWordRepository extends JpaRepository<BadWord, Long> {
    boolean existsBadWordByWord(String word);
}
