package com.ssafynity_b.domain.video.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
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

    @Column(name = "게시일", nullable = false)
    private LocalDateTime postedDate;

}
