package com.ssafynity_b.domain.member.repository;

import com.ssafynity_b.domain.member.document.MemberDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberDocumentRepository extends ElasticsearchRepository<MemberDocument, Long>, MemberDocumentCustomRepository {

    List<MemberDocument> findByName(String keyword);
}
