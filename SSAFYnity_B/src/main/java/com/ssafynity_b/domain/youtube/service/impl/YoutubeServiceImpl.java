package com.ssafynity_b.domain.youtube.service.impl;

import com.ssafynity_b.domain.youtube.service.YoutubeService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class YoutubeServiceImpl implements YoutubeService {
    //SSAFYnity 유튜브 API키 (application.dev.yml파일로 빼놓을것)
    private static final String API_KEY = "AIzaSyBGbW_w0OYiB8sTTlv7-Fo2jxarzvnmuQM";
    private static final String YOUTUBE_VIDEO_URL =
            "https://www.googleapis.com/youtube/v3/videos?part=snippet&id=%s&key=" + API_KEY;

    public String fetchVideoData(String videoId) {
        System.out.println("유튜브 서비스도 찍혔다.");
        System.out.println("비디오ID : " + videoId);

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

        System.out.println("비디오 응답이 도착했을까요 ? : " + videoResponse);
        System.out.println("채널 응답이 도착했을까요 ? : " + channelResponse);

        // 4️⃣ 비디오 정보 + 채널 정보 병합
        JSONObject channelJson = new JSONObject(channelResponse);
        JSONObject combinedResponse = new JSONObject();
        combinedResponse.put("video", videoJson);
        combinedResponse.put("channel", channelJson);

        return combinedResponse.toString(); // JSON 형태 그대로 반환
    }

    private String YoutubeChannelAPI(String channelId) {
        return "https://www.googleapis.com/youtube/v3/channels?part=snippet&id=" + channelId + "&key=" + API_KEY;
    }
}