package com.sy.backEndApiAkilina.security.services;

import com.sy.backEndApiAkilina.models.Commentaire;
import com.sy.backEndApiAkilina.models.Idee;
import com.sy.backEndApiAkilina.models.Ministere;
import com.sy.backEndApiAkilina.models.User;

public interface WordFilterService {
    Boolean filterIdee(Idee idee);

    Boolean filterCommentaire(Commentaire commentaire);
}
