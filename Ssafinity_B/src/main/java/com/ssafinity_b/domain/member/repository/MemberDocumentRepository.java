package com.ssafinity_b.domain.member.repository;

import com.ssafinity_b.domain.member.document.MemberDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberDocumentRepository extends ElasticsearchRepository<MemberDocument, Long> {
    List<MemberDocument> findByCompany(String keyword);
}
