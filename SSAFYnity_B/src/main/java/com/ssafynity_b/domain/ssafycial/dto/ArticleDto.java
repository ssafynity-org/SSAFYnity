package com.ssafynity_b.domain.ssafycial.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
public class ArticleDto {

    //기사 제목
    private String title;
    //기사 내용
    private String content;
    //이미지 리스트
    private List<MultipartFile> imageList;

}
