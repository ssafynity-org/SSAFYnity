package com.ssafynity_b.domain.notification.repository;

import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllBySender(Member sender);

}
