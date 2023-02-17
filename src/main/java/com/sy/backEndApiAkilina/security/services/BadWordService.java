package com.sy.backEndApiAkilina.security.services;

import com.sy.backEndApiAkilina.models.BadWord;
import com.sy.backEndApiAkilina.models.Ministere;

import java.util.List;

public interface BadWordService {
    BadWord add(BadWord badWord);
    List<BadWord> read();
    String delete(Long id);

}
