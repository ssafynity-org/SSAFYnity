package com.ssafynity_b.domain.youtube.service.impl;

import com.ssafynity_b.domain.video.dto.response.GetVideoRes;
import com.ssafynity_b.domain.youtube.service.YoutubeService;
import jakarta.annotation.PostConstruct;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class YoutubeServiceImpl implements YoutubeService {
    @Value("${youtube.api-key}")  // ✅ application.dev.yml에서 가져오기
    private String API_KEY;

    private String YOUTUBE_VIDEO_URL;

    @PostConstruct
    public void init() {
        this.YOUTUBE_VIDEO_URL = "https://www.googleapis.com/youtube/v3/videos?part=snippet&id=%s&key=" + API_KEY;
    }

    public JSONObject fetchVideoData(String videoId) {

        String videoUrl = String.format(YOUTUBE_VIDEO_URL, videoId);
        RestTemplate restTemplate = new RestTemplate();
        String videoResponse = restTemplate.getForObject(videoUrl, String.class);

        //영상의 channelId 추출
        JSONObject videoJson = new JSONObject(videoResponse);
        JSONArray items = videoJson.getJSONArray("items");

        JSONObject videoSnippet = items.getJSONObject(0).getJSONObject("snippet");
        String channelId = videoSnippet.getString("channelId");

        //채널 정보 요청
        String channelUrl = String.format(YoutubeChannelAPI(channelId), videoId);
        String channelResponse = restTemplate.getForObject(channelUrl, String.class);

        //비디오 정보 + 채널 정보 병합
        JSONObject channelJson = new JSONObject(channelResponse);
        JSONObject combinedResponse = new JSONObject();
        combinedResponse.put("video", videoJson);
        combinedResponse.put("channel", channelJson);

        return combinedResponse; // JSON 형태 그대로 반환
    }

    private String YoutubeChannelAPI(String channelId) {
        return "https://www.googleapis.com/youtube/v3/channels?part=snippet&id=" + channelId + "&key=" + API_KEY;
    }
}