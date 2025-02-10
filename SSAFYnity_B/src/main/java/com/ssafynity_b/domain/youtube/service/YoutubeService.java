package com.ssafynity_b.domain.youtube.service;

import com.ssafynity_b.domain.video.dto.response.GetVideoRes;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public interface YoutubeService {

    public JSONObject fetchVideoData(String videoId);

}
