package com.ssafynity_b.domain.video.dto.request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PostVideoReq {

    @Column(name = "비디오ID", nullable = false)
    private String videoId;

}
