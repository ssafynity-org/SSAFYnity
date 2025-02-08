package com.ssafynity_b.domain.youtube.service.impl;

import com.ssafynity_b.domain.youtube.service.YoutubeService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class YoutubeServiceImpl implements YoutubeService {
    private static final String API_KEY = "YOUR_YOUTUBE_API_KEY";
    private static final String YOUTUBE_API_URL =
            "https://www.googleapis.com/youtube/v3/videos?part=snippet&id=%s&key=" + API_KEY;

    public String fetchVideoData(String videoId) {
        String url = String.format(YOUTUBE_API_URL, videoId);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.toString(); // JSON 형태 그대로 반환
    }
}