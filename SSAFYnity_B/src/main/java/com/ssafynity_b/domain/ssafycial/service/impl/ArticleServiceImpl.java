package com.ssafynity_b.domain.ssafycial.service.impl;

import com.ssafynity_b.domain.ssafycial.dto.ArticleDto;
import com.ssafynity_b.domain.ssafycial.entity.Article;
import com.ssafynity_b.domain.ssafycial.entity.ArticleImage;
import com.ssafynity_b.domain.ssafycial.repository.ArticleRepository;
import com.ssafynity_b.domain.ssafycial.service.ArticleService;
import com.ssafynity_b.global.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    @Override
    public void createArticle(ArticleDto articleDto) throws IOException {
        Article article = Article.builder()
                        .title(articleDto.getTitle())
                        .content(articleDto.getContent())
                        .build();

        Long articleId = articleRepository.save(article).getId();

        List<String> urlList = s3Uploader.uploadArticleImageList(articleId, articleDto.getImageList());

        List<ArticleImage> imageList = new ArrayList<>();
        for(String url : urlList) {
            ArticleImage articleImage = ArticleImage.builder()
                    .article(article)
                    .imageUrl(url)
                    .build();

            imageList.add(articleImage);
        }

        article.setImageList(imageList);
    }

}
