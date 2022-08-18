package com.example.exceptionprac.dto;

// 검색을 위한 type
// String 객체로 관리하는 것 보다 enum으로 관리하는 것이 예외처리, service 단에서의 추가적인 처리 등에 있어서 훨씬 효율적임!
public enum AccountSearchType {
    EMAIL,
    NAME,
    ALL
}
