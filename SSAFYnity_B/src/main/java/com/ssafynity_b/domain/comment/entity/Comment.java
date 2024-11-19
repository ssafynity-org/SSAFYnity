package com.ssafynity_b.domain.comment.entity;

import com.ssafynity_b.domain.board.entity.Board;
import com.ssafynity_b.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content; //내용

    @Setter
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


    @Setter
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    public Comment(String content) {
        this.content = content;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
