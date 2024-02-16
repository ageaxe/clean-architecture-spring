package com.example.usercase.inventory;

import com.example.entity.*;
import com.example.usercase.BaseUsecase;
import com.example.usercase.exception.ResourceNotFoundException;
import com.example.usercase.port.GetUserInfo;
import com.example.usercase.port.InventoryDsGateway;
import com.example.entity.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class AddOwnerToApplication extends BaseUsecase {
    private final InventoryDsGateway repository;

    public AddOwnerToApplication(InventoryDsGateway repository, GetUserInfo getUserInfo) {
        super(getUserInfo);
        this.repository = repository;
    }

    public InventoryApplication addOwner(@NonNull String clientIdentifier, @NonNull String userID) {
        User user = this.getUser(userID);

        SearchCriteria<String> searchCriteria = SearchCriteria.<String>builder()
                .key("clientIdentifier")
                .operation('=')
                .content(clientIdentifier)
                .build();

        List<SearchCriteria<String>> searchCriteriaList = new ArrayList<>();
        searchCriteriaList.add(searchCriteria);
        PageCriteria pageCriteria = PageCriteria.builder().pageNumber(0).size(10).build();

        Page<InventoryApplication> inventoryApplicationPage = this.repository.getApplication(searchCriteriaList,
                pageCriteria, user);
        if (inventoryApplicationPage.getContent().size() == 1) {
            InventoryApplication application = inventoryApplicationPage.getContent().get(0);
            String[] ownerUser = application.getOwnerUser();
            if (ownerUser == null) {
                ownerUser = new String[] { userID };
            } else {
                List<String> ownerUserList = new ArrayList<>(Arrays.asList(ownerUser));
                ownerUserList.add(userID);
                ownerUser = ownerUserList.toArray(new String[0]);
            }
            application.setOwnerUser(ownerUser);
            application = this.repository.save(application);
            return application;
        } else {
            log.warn("Requested resource ID {0} is having count = {1} , should be exactly 1", clientIdentifier,
                    inventoryApplicationPage.getContent().size());
            throw new ResourceNotFoundException("Requested resource ID not found or is having more then one entry");
        }
    }

}
