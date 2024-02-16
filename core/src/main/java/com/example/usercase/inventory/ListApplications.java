package com.example.usercase.inventory;

import com.example.entity.*;
import com.example.usercase.BaseUsecase;
import com.example.usercase.port.CheckAccessDsGateway;
import com.example.usercase.port.GetUserInfo;
import com.example.usercase.port.InventoryDsGateway;
import lombok.NonNull;

import java.util.List;

public class ListApplications extends BaseUsecase {

    private final InventoryDsGateway repository;
    private final CheckAccessDsGateway authzService;

    public ListApplications(InventoryDsGateway repository, GetUserInfo getUserInfo, CheckAccessDsGateway authzService) {
        super(getUserInfo);
        this.repository = repository;
        this.authzService = authzService;
    }

    public Page<InventoryApplication> listApplication(List<SearchCriteria<String>> searchCriteria,
            PageCriteria pageCriteria, @NonNull String userID) {
        User user = this.getUser(userID);
        return this.repository.getApplication(searchCriteria, pageCriteria, user);
    }

    public InventoryApplication getApplicationById(@NonNull String appID, @NonNull String userID) {
        User user = this.getUser(userID);
        authzService.checkApplicationAccess(appID, user);
        return this.repository.getApplication(appID);
    }
}
