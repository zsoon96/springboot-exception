package com.example.exceptionprac.common;

import lombok.Getter;
import org.springframework.data.domain.Sort;

// 기존의 Pageable을 통해서 페이징 구현이 가능하지만, 별도의 PageRequest 객체를 두어 관리하는 것이 수월함!
@Getter
public class PageRequest {

    private int page; // 페이지 수
    private int size; // 한 페이지에서 보여줄 요소의 수
    private Sort.Direction direction; // 요소 정렬

    // 0보다 작은 페이지를 요청했을 경우, 1 페이지로 가도록 설정
    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    // 요청 사이즈 50보다 크면, 기본 사이즈인 10으로 바인딩 하도록 설정
    public void setSize(int size) {
        int DEFAULT_SIZE = 10;
        int MAX_SIZE = 50;
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    // of() 메서드를 통해 PageReq 객체를 응답
    public org.springframework.data.domain.PageRequest of() {
        return org.springframework.data.domain.PageRequest.of(page - 1, size, Sort.by(direction, "username"));
    }
}
