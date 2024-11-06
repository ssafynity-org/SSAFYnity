package com.ssafinity_b.domain.attendence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

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
    private List<Record> recordList;

    public Attendance(Long memberId, int year, int month){
        this.id = memberId + "-" + year + "-" + month;
        this.memberId = memberId;
        this.year = year;
        this.month = month;
        this.recordList = new ArrayList<>();
    }

}
