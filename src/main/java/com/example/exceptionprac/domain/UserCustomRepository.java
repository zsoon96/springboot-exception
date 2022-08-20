package com.example.exceptionprac.domain;

import java.util.List;

public interface UserCustomRepository {
    List<Users> findRecentlyRegistered(int limit);
}
