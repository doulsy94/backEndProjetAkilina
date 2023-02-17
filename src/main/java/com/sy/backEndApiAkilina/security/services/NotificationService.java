package com.sy.backEndApiAkilina.security.services;

import com.sy.backEndApiAkilina.models.Ministere;
import com.sy.backEndApiAkilina.models.Notification;
import java.util.List;
import java.util.Optional;

public interface NotificationService {

    //methode permettant de creer un notification
    Notification add(Notification notification);

    //methode permettant de lire notification
    List<Notification> read();

    //methode permettant de de supprimer une notification
    String delete(Long id_notification);

    Optional<Notification> trouverNotificationParID(long id_notification);


}
