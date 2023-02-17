package com.sy.backEndApiAkilina.repository;

import com.sy.backEndApiAkilina.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {



}
