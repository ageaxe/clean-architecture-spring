package com.example.usercase.inventory;

import com.example.entity.InventoryApplication;
import com.example.entity.User;
import com.example.usercase.BaseUsecase;
import com.example.usercase.port.CheckAccessDsGateway;
import com.example.usercase.port.GetUserInfo;
import com.example.usercase.port.InventoryDsGateway;
import com.example.usercase.port.PublishEvent;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

@Slf4j
public class SaveApplication extends BaseUsecase {


    private final InventoryDsGateway repository;

    private final CheckAccessDsGateway authzService;


    private final PublishEvent<InventoryApplication> publisher;

    public SaveApplication(InventoryDsGateway repository, CheckAccessDsGateway authzService, PublishEvent<InventoryApplication> publisher, GetUserInfo getUserInfo) {
        super(getUserInfo);
        this.repository = repository;
        this.authzService = authzService;
        this.publisher = publisher;
    }


    public InventoryApplication createApplication(@NonNull InventoryApplication application, @NonNull String userID) {

        User user = this.getUser(userID);

        application = application.toBuilder()
                .id(UUID.randomUUID())
                .updatedBy(user.getId())
                .validationStatus(this.evaluateValidationStatus(application))
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();


        application = this.repository.save(application);
        //publisher.publish(application);
        return application;
    }


    public InventoryApplication updateApplication(@NonNull InventoryApplication application, @NonNull String userID) {

        InventoryApplication.ValidationStatus inputValidationStatus = application.getValidationStatus();
        User user = this.getUser(userID);
        authzService.checkApplicationAccess(application, user);
        InventoryApplication repositoryApplication = this.repository.getApplication(application.getId().toString());

        repositoryApplication = repositoryApplication.toBuilder()
                .decommissionFlag(application.getDecommissionFlag())
                .ownerGroup(application.getOwnerGroup())
                .ownerUser(application.getOwnerUser())
                .audience(application.getAudience())
                .audienceConfirmed(application.getAudienceConfirmed())
                .espID(application.getEspID())
                .protocol(application.getProtocol())
                .serverApiId(application.getServerApiId())
                .validationStatus(this.evaluateValidationStatus(repositoryApplication))
                .updatedBy(user.getId())
                .updatedAt(new Date())
                .build();

        repositoryApplication.setUpdatedBy(user.getId());
        repositoryApplication.setValidationStatus(this.evaluateValidationStatus(repositoryApplication));
        repositoryApplication.setUpdatedAt(new Date());

        repositoryApplication = this.repository.save(repositoryApplication);

        if (inputValidationStatus != repositoryApplication.getValidationStatus()) {
            //publisher.publish(repositoryApplication);
        }
        return repositoryApplication;
    }


    private InventoryApplication.ValidationStatus evaluateValidationStatus(InventoryApplication application) {
        if (application.getDecommissionFlag() == 1) {
            return InventoryApplication.ValidationStatus.DECOMMISSION_NOW;
        } else if (application.getDecommissionFlag() != 0 && application.getOwnerGroup().length != 0 && application.getAudienceConfirmed() && application.getEspID() != null) {
            return InventoryApplication.ValidationStatus.VALIDATION_CONFIRMED;
        } else {
            return InventoryApplication.ValidationStatus.VALIDATION_REQUIRED;
        }
    }
}
