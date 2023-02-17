package com.sy.backEndApiAkilina.serviceImpl;

import com.sy.backEndApiAkilina.models.BadWord;
import com.sy.backEndApiAkilina.repository.BadWordRepository;
import com.sy.backEndApiAkilina.security.services.BadWordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class BadWordServiceImpl implements BadWordService {

    private final BadWordRepository badWordRepository;


    @Override
    public BadWord add(BadWord badWord) {
        return badWordRepository.save(badWord);
    }

    @Override
    public List<BadWord> read() {
        return badWordRepository.findAll();
    }

    @Override
    public String delete(Long id) {
        badWordRepository.deleteById(id);
        return "Gros mots supprimé avec succès";
    }
}
