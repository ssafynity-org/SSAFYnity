package com.ssafynity_b.domain.ssafycial.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePageResponse {

    private List<ArticleResponse> articles;

    private long totalCount;
}
