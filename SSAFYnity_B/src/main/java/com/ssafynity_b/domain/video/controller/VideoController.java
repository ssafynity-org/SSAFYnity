package com.ssafynity_b.domain.video.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafynity_b.domain.video.dto.response.GetVideoRes;
import com.ssafynity_b.domain.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/video")
@RestController
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/{videoId}")
    public ResponseEntity<?> getVideo(@PathVariable String videoId) throws JsonProcessingException {
        GetVideoRes response = videoService.getVideo(videoId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
