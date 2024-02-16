package com.example.restapi.config;

import com.example.entity.InventoryApplication;
import com.example.usercase.inventory.AddOwnerToApplication;
import com.example.usercase.inventory.DeleteApplication;
import com.example.usercase.inventory.ListApplications;
import com.example.usercase.inventory.SaveApplication;
import com.example.usercase.port.CheckAccessDsGateway;
import com.example.usercase.port.GetUserInfo;
import com.example.usercase.port.InventoryDsGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryUseCaseBeanConfig {

    @Autowired
    private GetUserInfo getUserInfo;

    @Autowired
    private InventoryDsGateway repository;

    @Autowired
    private CheckAccessDsGateway<InventoryApplication> authzService;


    @Bean
    public DeleteApplication getDeleteApplication() {
        return new DeleteApplication(repository, authzService, null, getUserInfo);
    }


    @Bean
    public ListApplications getListApplications() {
        return new ListApplications(repository, getUserInfo, authzService);
    }


    @Bean
    public SaveApplication getSaveApplications() {
        return new SaveApplication(repository, authzService, null, getUserInfo);
    }

    @Bean
    public AddOwnerToApplication getAddOwnerToApplication() {
        return new AddOwnerToApplication(repository, getUserInfo);
    }
}
