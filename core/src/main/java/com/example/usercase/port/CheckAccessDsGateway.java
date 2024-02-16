package com.example.usercase.port;

import com.example.entity.User;

public interface CheckAccessDsGateway<T> {
    void checkApplicationAccess(T application, User user) ;

    void checkApplicationAccess(String appID, User user);
}
