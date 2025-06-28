package com.ssafynity_b.domain.ssafycial.service;

import com.ssafynity_b.domain.ssafycial.dto.ArticleRequest;
import com.ssafynity_b.domain.ssafycial.dto.ArticleResponse;
import com.ssafynity_b.domain.ssafycial.dto.ArticleUpdateRequest;

import java.io.IOException;

public interface ArticleService {

    public void createArticle(ArticleRequest request) throws IOException;

    public ArticleResponse getArticle(Long id);

    public void updateArticle(Long id, ArticleUpdateRequest request) throws IOException;

    public void deleteArticle(Long id) throws IOException;
}
