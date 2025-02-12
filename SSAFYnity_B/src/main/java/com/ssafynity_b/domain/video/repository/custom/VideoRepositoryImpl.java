package com.ssafynity_b.domain.video.repository.custom;

import static com.ssafynity_b.domain.video.entity.QVideo.video;
import static com.ssafynity_b.domain.videoTag.entity.QVideoTag.videoTag;
import static com.ssafynity_b.domain.tag.entity.QTag.tag;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafynity_b.domain.video.entity.Video;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class VideoRepositoryImpl implements VideoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Video> searchVideos(List<String> tags, List<String> companies, Pageable pageable) {
        List<Video> videoList = queryFactory
                .selectDistinct(video)  // ✅ 중복을 제거하고 Video 엔티티 선택
                .from(video)  // ✅ Video 테이블을 기준으로 조회
                .leftJoin(video.videoTags, videoTag)  // ✅ VideoTag와 LEFT JOIN
                .leftJoin(videoTag.tag, tag)  // ✅ Tag 테이블과 LEFT JOIN
                .where(tagsIn(tags), companiesIn(companies))  // ✅ 태그와 기업 필터링 적용
                .offset(pageable.getOffset())  // ✅ 페이지네이션: 시작 위치 설정
                .limit(pageable.getPageSize())  // ✅ 페이지네이션: 페이지 크기 설정
                .fetch();  // ✅ 실행 후 리스트 반환

        long total = Optional.ofNullable(queryFactory
                        .select(video.count())
                        .from(video)
                        .leftJoin(video.videoTags, videoTag)
                        .leftJoin(videoTag.tag, tag)
                        .where(tagsIn(tags), companiesIn(companies))
                        .fetchOne())  // ✅ Optional로 감싸서 null 체크
                .orElse(0L);  // ✅ null이면 0 반환

        return new PageImpl<>(videoList, pageable, total);
    }

    // ✅ 여러 개의 태그 필터링
    private BooleanExpression tagsIn(List<String> tags) {
        return (tags != null && !tags.isEmpty()) ? tag.name.in(tags) : null;
    }

    // ✅ 여러 개의 기업 필터링
    private BooleanExpression companiesIn(List<String> companies) {
        return (companies != null && !companies.isEmpty()) ? video.company.in(companies) : null;
    }
}
