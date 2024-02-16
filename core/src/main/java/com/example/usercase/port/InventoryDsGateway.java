package com.example.usercase.port;

import com.example.entity.*;

import java.util.List;

public interface InventoryDsGateway {

    Page<InventoryApplication> getApplication(List<SearchCriteria<String>> searchCriteria, PageCriteria pageCriteria,
            User user);

    InventoryApplication save(InventoryApplication application);

    void delete(String appID);

    InventoryApplication getApplication(String appID, User user);

    InventoryApplication getApplication(String appID);
}
