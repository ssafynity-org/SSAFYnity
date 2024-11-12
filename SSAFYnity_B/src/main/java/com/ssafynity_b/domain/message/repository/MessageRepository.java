package com.ssafynity_b.domain.message.repository;

import com.ssafynity_b.domain.member.entity.Member;
import com.ssafynity_b.domain.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByReceiver(Member receiver);

}
