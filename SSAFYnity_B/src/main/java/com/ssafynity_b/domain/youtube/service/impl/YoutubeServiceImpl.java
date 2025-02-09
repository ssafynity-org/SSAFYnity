package com.ssafynity_b.domain.youtube.service.impl;

import com.ssafynity_b.domain.youtube.service.YoutubeService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class YoutubeServiceImpl implements YoutubeService {
    //SSAFYnity 유튜브 API키 (application.dev.yml파일로 빼놓을것)
    private static final String API_KEY = "AIzaSyBGbW_w0OYiB8sTTlv7-Fo2jxarzvnmuQM";
    private static final String YOUTUBE_API_URL =
            "https://www.googleapis.com/youtube/v3/videos?part=snippet&id=%s&key=" + API_KEY;

    public String fetchVideoData(String videoId) {
        System.out.println("유튜브 서비스도 찍혔다.");
        System.out.println("비디오ID : " + videoId);

        String url = String.format(YOUTUBE_API_URL, videoId);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        System.out.println("응답이 도착했을까요 ? : " + response);

        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse.toString(); // JSON 형태 그대로 반환
    }
}