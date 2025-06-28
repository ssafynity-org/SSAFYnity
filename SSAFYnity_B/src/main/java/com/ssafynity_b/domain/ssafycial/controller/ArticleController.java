package com.ssafynity_b.domain.ssafycial.controller;

import com.ssafynity_b.domain.ssafycial.dto.ArticleRequest;
import com.ssafynity_b.domain.ssafycial.dto.ArticleResponse;
import com.ssafynity_b.domain.ssafycial.dto.ArticleUpdateRequest;
import com.ssafynity_b.domain.ssafycial.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "기사")
@RequestMapping("/articles")
@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "생성", description = "기사를 생성합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createArticle(@ModelAttribute ArticleRequest request) throws IOException {
        articleService.createArticle(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "단일 조회", description = "기사 하나를 단일 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getArticle(@Parameter(description = "기사Id", example = "11") @PathVariable Long id) {
        ArticleResponse response = articleService.getArticle(id);
        return ResponseEntity.ok(response);
    }

//    @Operation(summary = "페이지네이션 조회", description = "기사를 페이지네이션 조회합니다.")
//    @GetMapping
//    public ResponseEntity<?> getArticles(
//            @Parameter(description = "페이지 번호", example = "0") @RequestParam(defaultValue = "0") int page,
//            @Parameter(description = "페이지 크기", example = "10") @RequestParam(defaultValue = "10") int size)
//    {
//        ArticleResponse response = articleService.getArticle(articleId);
//        return ResponseEntity.ok(response);
//    }

    @Operation(summary = "수정", description = "기사 하나를 단일 조회합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateArticle(
            @Parameter(description = "기사Id", example = "11") @PathVariable Long id,
            @ModelAttribute ArticleUpdateRequest request
    ) throws IOException {
        articleService.updateArticle(id, request);
        return ResponseEntity.ok("수정이 완료되었습니다.");
    }

    @Operation(summary = "삭제", description = "선택한 기사를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(
            @Parameter(description = "기사Id", example = "11") @PathVariable Long id
    ) throws IOException {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

}
