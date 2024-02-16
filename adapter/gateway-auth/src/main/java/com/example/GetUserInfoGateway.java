package com.example;

import com.example.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.List;
import com.example.usercase.port.GetUserInfo;
@Service
@Slf4j
public class GetUserInfoGateway implements GetUserInfo {
    @Value("${clean.directory-ms.endpoint}")
    private String userInfoEndpoint;
    @Value("#{'${clean.application.admin-groups}'.split(',')}")
    private List<String> adminGroupList;

    @Override
    public User getUser(String userID) {
        boolean isAdmin = false;

        // TODO : Add the logic to get the user details from the user info service
        // For now, hardcoding the user details

        var groups = List.of("group1", "group2");
        return User.builder()
                .id(userID)
                .isAdmin(isAdmin)
                .groups(groups.stream().toArray(String[]::new))
                .build();
    }
}
