package com.example.usercase.port;

import com.example.entity.User;

public interface GetUserInfo {
    User getUser(String userID);
}
