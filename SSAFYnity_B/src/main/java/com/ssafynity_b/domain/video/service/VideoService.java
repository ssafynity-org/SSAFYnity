package com.ssafynity_b.domain.video.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafynity_b.domain.video.dto.response.GetVideoRes;

public interface VideoService {

    public GetVideoRes getVideo(String videoId) throws JsonProcessingException;

}
