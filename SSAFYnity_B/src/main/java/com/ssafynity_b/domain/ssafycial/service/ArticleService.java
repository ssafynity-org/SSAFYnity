package com.ssafynity_b.domain.ssafycial.service;

import com.ssafynity_b.domain.ssafycial.dto.ArticleDto;

import java.io.IOException;

public interface ArticleService {

    public void createArticle(ArticleDto articleDto) throws IOException;
}
