package com.ssafynity_b.domain.ssafycial.service.impl;

import com.ssafynity_b.domain.ssafycial.dto.ArticlePageResponse;
import com.ssafynity_b.domain.ssafycial.dto.ArticleRequest;
import com.ssafynity_b.domain.ssafycial.dto.ArticleResponse;
import com.ssafynity_b.domain.ssafycial.dto.ArticleUpdateRequest;
import com.ssafynity_b.domain.ssafycial.entity.Article;
import com.ssafynity_b.domain.ssafycial.entity.ArticleImage;
import com.ssafynity_b.domain.ssafycial.repository.ArticleRepository;
import com.ssafynity_b.domain.ssafycial.service.ArticleService;
import com.ssafynity_b.global.s3.S3Uploader;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

        //3. S3 저장소에 업로드 한 뒤, 얻은 UrlList를 아까 생성했던 기사 객체에 같이 저장함(=> 영속성 컨텍스트 활용)
        updateArticleImageList(article, articleId, articleRequest.getImageList());
    }

    @Override
    public ArticleResponse getArticle(Long id) {
        //1. 기사 조회
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 기사가 없습니다. id=" + id));

        //2. 응답Dto로 반환(이미지 리스트는 연관관계인 ArticleImage에서 List<String> 형태로 추출)
        return ArticleResponse.builder()
                .title(article.getTitle())
                .content(article.getContent())
                .imageList(article.getImageList().stream()
                        .map(ArticleImage::getImageUrl)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public ArticlePageResponse getArticles(int page, int size) {
        int offset = page*size;

        List<Article> articles = articleRepository.findArticles(offset, size);
        long count = articleRepository.countArticles();

        List<ArticleResponse> responses = articles.stream()
                .map(article -> ArticleResponse.builder()
                        .title(article.getTitle())
                        .content(article.getContent())
                        .imageList(article.getImageList().stream()
                                .map(ArticleImage::getImageUrl)
                                .collect(Collectors.toList()))
                        .build())
                .toList();


        return ArticlePageResponse.builder()
                .articles(responses)
                .totalCount(count)
                .build();
    }

    @Transactional
    @Override
    public void updateArticle(Long id, ArticleUpdateRequest request) throws IOException {
        //1. 기사 조회
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 기사가 없습니다. id=" + id));

        //2. s3와 Repository에 이미지 덮어씌우기
        updateArticleImageList(article, id, request.getImageList());

        //3. 기사 수정
        article.updateTitle(request.getTitle());
        article.updateContent(request.getContent());
    }

    @Override
    public void deleteArticle(Long id) throws IOException {
        //1. 기사 조회
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 기사가 없습니다. id=" + id));

        //2. s3에서 해당 기사의 첨부 이미지 삭제
        s3Uploader.deleteArticleImage(article.getId());

        //3. 해당 기사 삭제
        articleRepository.delete(article);
    }

    private void updateArticleImageList(Article article, Long articleId, List<MultipartFile> imageList) throws IOException {
        List<String> urlList = s3Uploader.uploadArticleImageList(articleId, imageList);

        List<ArticleImage> newImageList = new ArrayList<>();
        for (String url : urlList) {
            ArticleImage articleImage = ArticleImage.builder()
                    .article(article)
                    .imageUrl(url)
                    .build();
            newImageList.add(articleImage);
        }

        article.updateImageList(newImageList);
    }

}
