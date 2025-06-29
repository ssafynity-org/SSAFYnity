package com.ssafynity_b.domain.ssafycial.repository.custom;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafynity_b.domain.ssafycial.entity.Article;
import com.ssafynity_b.domain.ssafycial.entity.QArticle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Article> findArticles(int offset, int limit) {
        QArticle article = QArticle.article;

        return queryFactory
                .selectFrom(article)
                .orderBy(article.id.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    @Override
    public long countArticles() {
        QArticle article = QArticle.article;

        return Optional.ofNullable(queryFactory
                        .select(Wildcard.count)
                        .from(article)
                        .fetchOne())
                .orElse(0L);
    }
}
