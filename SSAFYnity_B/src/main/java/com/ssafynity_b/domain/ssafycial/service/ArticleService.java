package com.ssafynity_b.domain.ssafycial.service;

import com.ssafynity_b.domain.ssafycial.dto.ArticlePageResponse;
import com.ssafynity_b.domain.ssafycial.dto.ArticleRequest;
import com.ssafynity_b.domain.ssafycial.dto.ArticleResponse;
import com.ssafynity_b.domain.ssafycial.dto.ArticleUpdateRequest;
import com.ssafynity_b.domain.ssafycial.entity.Article;

import java.io.IOException;
import java.util.List;

public interface ArticleService {

    public void createArticle(ArticleRequest request) throws IOException;

    public ArticleResponse getArticle(Long id);

    public ArticlePageResponse getArticles(int page, int size);

    public void updateArticle(Long id, ArticleUpdateRequest request) throws IOException;

    public void deleteArticle(Long id) throws IOException;
}
