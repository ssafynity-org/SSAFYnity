package com.ssafynity_b.domain.member.document;

import com.ssafynity_b.domain.member.entity.Member;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@Document(indexName = "member")
public class MemberDocument {

    @Id
    private Long memberId;

    @Field(type = FieldType.Text, analyzer = "nori")
    private String company;

//    @Field(index = false)
    private String email;

//    @Field(index = false)
    private String password;

//    @Field(index = false)
    private String name;

    public MemberDocument(Member savedMember) {
        this.memberId = savedMember.getMemberId();
        this.email = savedMember.getEmail();
        this.password = savedMember.getPassword();
        this.name = savedMember.getName();
        this.company = savedMember.getCompany();
    }
}
