package com.sy.backEndApiAkilina.serviceImpl;

import com.sy.backEndApiAkilina.models.*;
import com.sy.backEndApiAkilina.repository.BadWordRepository;
import com.sy.backEndApiAkilina.repository.CommentaireRepository;
import com.sy.backEndApiAkilina.repository.IdeeRepository;
import com.sy.backEndApiAkilina.security.services.WordFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordFilterServiceImpl implements WordFilterService {

    @Autowired
    private BadWordRepository badwordRepository;

    @Autowired
    private IdeeRepository ideeRepository;

    private List<String> badWords;
    @Autowired
    private CommentaireRepository commentaireRepository;

    @PostConstruct
    public void init() {
        List<BadWord> badWordsEntities = badwordRepository.findAll();
        badWords = badWordsEntities.stream().map(BadWord::getWord).collect(Collectors.toList());
    }

    @Override
    public Boolean filterIdee(Idee idee) {
        for (String word : badWords) {
            try {
                if (idee.getContenu_idee().toLowerCase().contains(word.toLowerCase()))
                    return true;
            } catch (Exception e) {

            }

        }
        return false;
    }

    @Override
    public Boolean filterCommentaire(Commentaire commentaire) {
        for (String word : badWords) {
            try {
                if (commentaire.getContenu_commentaire().toLowerCase().contains(word.toLowerCase()))
                    return true;
            } catch (Exception e) {

            }
        }
            return false;
        }

    }


