package com.ssafynity_b.domain.video.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafynity_b.domain.video.dto.request.PostVideoReq;
import com.ssafynity_b.domain.video.dto.response.GetVideoRes;

import java.awt.print.Pageable;
import java.util.List;

public interface VideoService {

    public GetVideoRes getVideo(String videoId) throws JsonProcessingException;

    public void postVideo(PostVideoReq request);

//    public List<GetVideoRes> getVideoList(Pageable pageable);

}
