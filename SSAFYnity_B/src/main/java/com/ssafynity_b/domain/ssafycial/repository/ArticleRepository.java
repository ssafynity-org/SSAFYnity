package com.ssafynity_b.domain.ssafycial.repository;

import com.ssafynity_b.domain.ssafycial.entity.Article;
import com.ssafynity_b.domain.ssafycial.repository.custom.ArticleRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {
}
