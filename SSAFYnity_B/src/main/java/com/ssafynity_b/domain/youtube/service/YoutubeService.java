package com.ssafynity_b.domain.youtube.service;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public interface YoutubeService {

    public String fetchVideoData(String videoId);

}
