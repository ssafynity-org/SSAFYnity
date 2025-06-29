package com.ssafynity_b.domain.ssafycial.repository.custom;

import com.ssafynity_b.domain.ssafycial.entity.Article;

import java.util.List;

public interface ArticleRepositoryCustom {
    List<Article> findArticles(int offset, int limit);
    long countArticles();
}
