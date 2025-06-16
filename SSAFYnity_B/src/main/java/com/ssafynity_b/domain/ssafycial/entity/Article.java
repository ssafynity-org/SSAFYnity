package com.ssafynity_b.domain.ssafycial.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @OneToMany(mappedBy = "article")
    private List<ArticleImage> imageList;

    public void setImageList(List<ArticleImage> imageList) {
        this.imageList = imageList;
    }

}
