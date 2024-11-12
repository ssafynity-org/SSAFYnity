package com.ssafynity_b.domain.member.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ssafynity_b.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@Document(indexName = "ssafynity_member")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberDocument {

    @Id
    private Long memberId;

    private String email;

    private String password;

    private String name;

    private String company;

    public MemberDocument(Member savedMember) {
        this.memberId = savedMember.getMemberId();
        this.email = savedMember.getEmail();
        this.password = savedMember.getPassword();
        this.name = savedMember.getName();
        this.company = savedMember.getCompany();
    }
}
