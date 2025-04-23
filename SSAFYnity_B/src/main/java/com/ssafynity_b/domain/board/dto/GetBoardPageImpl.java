package com.ssafynity_b.domain.board.dto;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class GetBoardPageImpl<T> extends PageImpl<T> {

    private final boolean nextButton;

    private final boolean lastButton;


    public GetBoardPageImpl(List<T> content, Pageable pageable, long total, boolean nextButton, boolean lastButton) {
        super(content, pageable, total);
        this.nextButton = nextButton;
        this.lastButton = lastButton;
    }

    public boolean isNextButton() {
        return nextButton;
    }

    public boolean isLastButton() {
        return lastButton;
    }
}
