package com.example.usercase;

import com.example.entity.User;
import com.example.usercase.port.GetUserInfo;

public class BaseUsecase {
    private final GetUserInfo userInfoService;

    public BaseUsecase(GetUserInfo repository){
        this.userInfoService = repository;

    }
    protected User getUser(String userID) {
        return userInfoService.getUser(userID);

    }
}
