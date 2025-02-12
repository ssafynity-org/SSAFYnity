package com.ssafynity_b.domain.video.repository;

import com.ssafynity_b.domain.video.entity.Video;
import com.ssafynity_b.domain.video.repository.custom.VideoRepositoryCustom;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video,Long>, VideoRepositoryCustom {

    @NotNull
    Page<Video> findAll(@NotNull Pageable pageable);
}
