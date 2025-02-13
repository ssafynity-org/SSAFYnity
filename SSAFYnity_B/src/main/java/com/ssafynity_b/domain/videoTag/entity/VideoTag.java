package com.ssafynity_b.domain.videoTag.entity;

import com.ssafynity_b.domain.tag.entity.Tag;
import com.ssafynity_b.domain.video.entity.Video;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class VideoTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false) // ✅ 비디오 ID와 연결
    private Video video;

    @ManyToOne
    @JoinColumn(nullable = false) // ✅ 태그 ID와 연결
    private Tag tag;
}
