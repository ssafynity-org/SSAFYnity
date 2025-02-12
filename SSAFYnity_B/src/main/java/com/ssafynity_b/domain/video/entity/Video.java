package com.ssafynity_b.domain.video.entity;

import com.ssafynity_b.domain.videoTag.entity.VideoTag;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Column(name = "기업명", nullable = false)
    private String company;

    @Column(name = "게시일", nullable = false)
    private LocalDateTime postedDate;

    @OneToMany(mappedBy = "video")
    List<VideoTag> videoTags;


}
