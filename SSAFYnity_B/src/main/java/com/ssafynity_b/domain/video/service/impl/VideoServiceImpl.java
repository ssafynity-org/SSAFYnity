package com.ssafynity_b.domain.video.service.impl;

import com.ssafynity_b.domain.video.service.VideoService;
import com.ssafynity_b.domain.youtube.service.YoutubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class VideoServiceImpl implements VideoService {

    private final RedisTemplate<String, String> redisTemplate;
    private final YoutubeService youtubeService;

    @Override
    public String getVideo(String videoId) {
        String cacheKey = "video:" + videoId;

        // 1️⃣ Redis에서 영상 데이터 검색
        String videoData = redisTemplate.opsForValue().get(cacheKey);
        if (videoData != null) {
            System.out.println("✅ Redis 캐시에서 데이터 가져옴!");
            return videoData; // Redis에서 가져온 데이터 반환
        }

        // 2️⃣ Redis에 없으면 YouTube API 호출
        System.out.println("🔄 Redis에 없음 → YouTube API 호출");
        videoData = youtubeService.fetchVideoData(videoId);

        // 3️⃣ 가져온 데이터를 Redis에 저장 (60분 TTL 설정)
        redisTemplate.opsForValue().set(cacheKey, videoData, 60, TimeUnit.MINUTES);

        return videoData;
    }
}
