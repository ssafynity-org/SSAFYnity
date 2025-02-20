package com.ssafynity_b.domain.video.controller;

import co.elastic.clients.elasticsearch.nodes.Http;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafynity_b.domain.video.dto.request.PostVideoReq;
import com.ssafynity_b.domain.video.dto.response.GetVideoRes;
import com.ssafynity_b.domain.video.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Tag(name = "Video 컨트롤러")
@RequiredArgsConstructor
@RequestMapping("/api/video")
@RestController
public class VideoController {

    private final VideoService videoService;

    @Operation(summary = "영상 단건 조회")
    @GetMapping("/{videoId}")
    public ResponseEntity<?> getVideo(@PathVariable String videoId) throws JsonProcessingException {
        GetVideoRes response = videoService.getVideo(videoId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "영상 정보를 DB에 저장")
    @PostMapping
    public ResponseEntity<Void> postVideo(@RequestBody PostVideoReq request) {
        videoService.postVideo(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "영상 정보 페이지네이션 조회")
    @GetMapping("/videolist")
    public ResponseEntity<List<GetVideoRes>> getVideoList(@RequestParam(required = false) List<String> tags, @RequestParam(required = false) List<String> companies, Pageable pageable) {
        long start = System.nanoTime(); // 시작 시간 측정
        List<GetVideoRes> videoList = videoService.getVideoList(tags, companies, pageable);
        long executionTime = (System.nanoTime() - start) / 1_000_000; // 밀리초 변환
        System.out.println("걸린시간 : " + executionTime);
        return ResponseEntity.ok(videoList);
    }


}
