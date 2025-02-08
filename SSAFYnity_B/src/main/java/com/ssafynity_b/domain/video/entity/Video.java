package com.ssafynity_b.domain.video.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "videos")
@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "동영상Id", nullable = false)
    private String videoId;

    @Column(name = "동영상 썸네일URL", nullable = false)
    private String thumbnail;

    @Column(name = "제목", nullable = false)
    private String title;

    @Column(name = "조회수", nullable = false)
    private Long views;

    @Column(name = "게시일", nullable = false)
    private LocalDateTime postedDate;

    @Column(name = "채널이미지(Base64기준)", nullable = false, columnDefinition = "TEXT")
    private String channelImage;

    @Column(name = "채널설명", nullable = false)
    private String description;

}
