package com.ssafynity_b.domain.member.repository;

import com.ssafynity_b.domain.member.document.MemberDocument;

import java.io.IOException;
import java.util.List;

public interface MemberDocumentCustomRepository {

    List<MemberDocument> searchByCompany(String keyword) throws IOException;

}
