package com.ssafynity_b.domain.video.repository.custom;

import com.ssafynity_b.domain.video.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VideoRepositoryCustom {
    Page<Video> searchVideos(List<String> tags, List<String> companies, Pageable pageable);
}
