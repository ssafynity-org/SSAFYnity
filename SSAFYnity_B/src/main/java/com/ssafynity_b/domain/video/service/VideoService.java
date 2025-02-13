package com.ssafynity_b.domain.video.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafynity_b.domain.video.dto.request.PostVideoReq;
import com.ssafynity_b.domain.video.dto.response.GetVideoRes;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface VideoService {

    public GetVideoRes getVideo(String videoId) throws JsonProcessingException;

    public void postVideo(PostVideoReq request);

    public List<GetVideoRes> getVideoList(List<String> tags, List<String> companies, Pageable pageable);

}
