package com.ssafinity_b.domain.attendence.document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    private String id;
    private Long memberId;
    private int year;
    private int month;
    private Map<Integer, Record> records;

    public Attendance(Long memberId, int year, int month){
        this.id = memberId + "-" + year + "-" + month;
        this.memberId = memberId;
        this.year = year;
        this.month = month;
        this.records = new HashMap<>();
    }

}
