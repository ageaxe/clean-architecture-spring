package com.example.usercase.inventory;

import com.example.entity.InventoryApplication;
import com.example.entity.User;
import com.example.usercase.BaseUsecase;
import com.example.usercase.port.CheckAccessDsGateway;
import com.example.usercase.port.GetUserInfo;
import com.example.usercase.port.InventoryDsGateway;
import com.example.usercase.port.PublishEvent;
import lombok.NonNull;


public class DeleteApplication extends BaseUsecase {

    private final InventoryDsGateway repository;

    private final CheckAccessDsGateway authzService;


    private final PublishEvent<InventoryApplication> publisher;

    public DeleteApplication(InventoryDsGateway repository, CheckAccessDsGateway authzService, PublishEvent<InventoryApplication> publisher, GetUserInfo getUserInfo) {
        super(getUserInfo);
        this.repository = repository;
        this.authzService = authzService;
        this.publisher = publisher;
    }


    public void deleteApplication(@NonNull String appID, @NonNull  String userID) {
        User user = this.getUser(userID);
        authzService.checkApplicationAccess(appID, user);
        this.repository.delete(appID);
    }

}
