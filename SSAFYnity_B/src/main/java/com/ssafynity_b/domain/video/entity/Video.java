package com.ssafynity_b.domain.video.entity;

import com.ssafynity_b.domain.company.entity.Company;
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

    @Column(name = "video_id", nullable = false)
    private String videoId;

    @Column(name = "posted_date", nullable = false)
    private LocalDateTime postedDate;

    @OneToMany(mappedBy = "video")
    List<VideoTag> videoTags;

    @ManyToOne(fetch = FetchType.LAZY)
    Company company;
}
