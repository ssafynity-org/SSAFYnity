package com.ssafynity_b.domain.ssafycial.controller;

import com.ssafynity_b.domain.ssafycial.dto.ArticleDto;
import com.ssafynity_b.domain.ssafycial.service.ArticleService;
import com.ssafynity_b.global.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/article")
@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createArticle(@ModelAttribute ArticleDto articleDto) throws IOException {
        articleService.createArticle(articleDto);
        return ResponseEntity.ok().build();
    }

}
