package com.ssafynity_b.domain.ssafycial.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class ArticleUpdateRequest {

    @Schema(description = "기사 제목", example = "기사 제목을 입력해주세요.")
    private String title;

    @Schema(description = "기사 내용", example = "기사 내용을 입력해주세요.")
    private String content;

    @Schema(description = "첨부 이미지 파일 리스트")
    private List<MultipartFile> imageList;

}
