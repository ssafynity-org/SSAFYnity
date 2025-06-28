package com.ssafynity_b.domain.ssafycial.service.impl;

import com.ssafynity_b.domain.ssafycial.dto.ArticleRequest;
import com.ssafynity_b.domain.ssafycial.dto.ArticleResponse;
import com.ssafynity_b.domain.ssafycial.entity.Article;
import com.ssafynity_b.domain.ssafycial.entity.ArticleImage;
import com.ssafynity_b.domain.ssafycial.repository.ArticleRepository;
import com.ssafynity_b.domain.ssafycial.service.ArticleService;
import com.ssafynity_b.global.s3.S3Uploader;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    @Override
    public void createArticle(ArticleRequest articleRequest) throws IOException {
        //1. 제목, 내용만 넣어서 기사 생성
        Article article = Article.builder()
                        .title(articleRequest.getTitle())
                        .content(articleRequest.getContent())
                        .build();

        //2. 기사 Id를 경로로 지정한뒤, 첨부 이미지 파일 리스트를 해당 S3 저장소에 업로드
        Long articleId = articleRepository.save(article).getId();
        List<String> urlList = s3Uploader.uploadArticleImageList(articleId, articleRequest.getImageList());

        System.out.println("urlList : " + urlList.toString());

        //3. S3 저장소에 업로드 한 뒤, 얻은 UrlList를 아까 생성했던 기사 객체에 같이 저장함
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

    @Override
    public ArticleResponse getArticle(Long articleId) {
        //1. 기사 조회
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("해당 기사가 없습니다. id=" + articleId));

        //2. 응답Dto로 반환(이미지 리스트는 연관관계인 ArticleImage에서 List<String> 형태로 추출)
        return ArticleResponse.builder()
                .title(article.getTitle())
                .content(article.getContent())
                .imageList(article.getImageList().stream()
                        .map(ArticleImage::getImageUrl)
                        .collect(Collectors.toList()))
                .build();
    }

}
