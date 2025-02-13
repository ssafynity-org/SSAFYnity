package com.ssafynity_b.domain.tag.entity;

import com.ssafynity_b.domain.video.entity.Video;
import com.ssafynity_b.domain.videoTag.entity.VideoTag;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "tag")
    List<VideoTag> videoTags;
}
