package com.ssafynity_b.domain.ssafycial.service;

import com.ssafynity_b.domain.ssafycial.dto.ArticleRequest;
import com.ssafynity_b.domain.ssafycial.dto.ArticleResponse;

import java.io.IOException;

public interface ArticleService {

    public void createArticle(ArticleRequest articleRequest) throws IOException;

    public ArticleResponse getArticle(Long articleId);
}
