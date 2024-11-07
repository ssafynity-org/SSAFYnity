package com.ssafinity_b.domain.board.repository;

import com.ssafinity_b.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
