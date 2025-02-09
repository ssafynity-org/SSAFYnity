package com.ssafynity_b.domain.video.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafynity_b.domain.video.dto.request.PostVideoReq;
import com.ssafynity_b.domain.video.dto.response.GetVideoRes;
import com.ssafynity_b.domain.video.entity.Video;
import com.ssafynity_b.domain.video.repository.VideoRepository;
import com.ssafynity_b.domain.video.service.VideoService;
import com.ssafynity_b.domain.youtube.service.YoutubeService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class VideoServiceImpl implements VideoService {

    private final RedisTemplate<String, String> redisTemplate;
    private final YoutubeService youtubeService;
    private final VideoRepository videoRepository;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public GetVideoRes getVideo(String videoId) throws JsonProcessingException {
        String cacheKey = "video:" + videoId;

        // 1️⃣ Redis에서 영상 데이터 검색
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject videoData;
        String cachedData = redisTemplate.opsForValue().get(cacheKey);

        if(cachedData!=null){
            videoData = new JSONObject(cachedData); // ✅ JSON 문자열 → JSONObject 변환
        }else {
            // 2️⃣ Redis에 없으면 YouTube API 호출
            System.out.println("🔄 Redis에 없음 → YouTube API 호출");

            videoData = youtubeService.fetchVideoData(videoId);
            String jsonString = objectMapper.writeValueAsString(videoData.toMap()); // ✅ JSONObject → JSON String 변환
            // 3️⃣ 가져온 데이터를 Redis에 저장 (60분 TTL 설정)
            redisTemplate.opsForValue().set(cacheKey, jsonString, 60, TimeUnit.MINUTES);
        }

        return GetVideoRes.builder()
                .videoId(videoData.getJSONObject("video").getJSONArray("items").getJSONObject(0).getString("id"))
                .title(videoData.getJSONObject("video").getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("title"))
                .description(videoData.getJSONObject("video").getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("description"))
                .publishedAt(LocalDateTime.parse(videoData.getJSONObject("video").getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("publishedAt"),formatter))
                .thumbnail(videoData.getJSONObject("video").getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("maxres").getString("url"))
                .channelName(videoData.getJSONObject("video").getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("channelTitle"))
                .channelImage(videoData.getJSONObject("channel").getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high").getString("url"))
                .build();
    }

    @Override
    public void postVideo(PostVideoReq request) {
        JSONObject videoData = youtubeService.fetchVideoData(request.getVideoId());

        videoRepository.save(Video.builder()
                .videoId(videoData.getJSONObject("video").getJSONArray("items").getJSONObject(0).getString("id"))
                .postedDate(LocalDateTime.parse(videoData.getJSONObject("video").getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("publishedAt"),formatter))
                .build());
    }

//    @Override
//    public List<GetVideoRes> getVideoList(Pageable pageable) {
//        return List.of();
//    }
}
