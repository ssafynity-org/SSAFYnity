package com.ssafynity_b.domain.video.dto.response;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class GetVideoRes {

    @Column(name = "비디오ID", nullable = false)
    private String videoId;

    @Column(name = "영상 제목", nullable = false)
    private String title;

    @Column(name = "영상 설명", nullable = false)
    private String description;

    @Column(name = "영상 게시일", nullable = false)
    private LocalDateTime publishedAt;

    @Column(name = "썸네일 url", nullable = false)
    private String thumbnail;

    @Column(name = "채널 이름", nullable = false)
    private String channelName;

    @Column(name = "채널 이미지", nullable = false)
    private String channelImage;

}
