package com.ssafynity_b.domain.member.repository.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.ssafynity_b.domain.member.document.MemberDocument;
import com.ssafynity_b.domain.member.repository.MemberDocumentCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MemberDocumentCustomRepositoryImpl implements MemberDocumentCustomRepository {

    private final ElasticsearchClient elasticsearchClient;

    @Override
    public List<MemberDocument> searchByCompany(String keyword) throws IOException {
        // 정확 일치 쿼리
        TermQuery termQuery = TermQuery.of(t -> t
                .field("company.keyword")
                .value(keyword)
                .boost(2.0f)
        );

        // 부분 일치 쿼리
        MatchQuery matchQuery = MatchQuery.of(m -> m
                .field("company")
                .query(keyword)
                .boost(1.0f)
        );

        // Bool 쿼리 생성
        BoolQuery boolQuery = BoolQuery.of(b -> b
                .should(termQuery._toQuery())
                .should(matchQuery._toQuery())
        );

        // 검색 요청 생성
        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index("ssafynity_member")
                .query(boolQuery._toQuery())
        );

        // 검색 실행
        SearchResponse<MemberDocument> response = elasticsearchClient.search(searchRequest, MemberDocument.class);

        // 결과 반환
        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }
}
