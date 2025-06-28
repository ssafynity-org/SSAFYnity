package com.ssafynity_b.domain.ssafycial.controller;

import com.ssafynity_b.domain.ssafycial.dto.ArticleRequest;
import com.ssafynity_b.domain.ssafycial.dto.ArticleResponse;
import com.ssafynity_b.domain.ssafycial.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "기사 생성", description = "기사를 생성합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createArticle(@ModelAttribute ArticleRequest articleRequest) throws IOException {
        articleService.createArticle(articleRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "기사 단일 조회", description = "기사 하나를 단일 조회합니다.")
    @GetMapping
    public ResponseEntity<?> getArticle(@Parameter(description = "기사Id", example = "11") @RequestParam Long articleId) {
        ArticleResponse response = articleService.getArticle(articleId);
        return ResponseEntity.ok(response);
    }

}
