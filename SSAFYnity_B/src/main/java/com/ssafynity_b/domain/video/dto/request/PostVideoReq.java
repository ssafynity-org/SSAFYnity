package com.ssafynity_b.domain.video.dto.request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PostVideoReq {

    @Column(name = "videoID", nullable = false)
    private String videoId;

    @Column(name = "company", nullable = false)
    private String company;

}
