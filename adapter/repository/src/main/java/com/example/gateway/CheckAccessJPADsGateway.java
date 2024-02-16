package com.example.gateway;

import com.example.entity.InventoryApplication;
import com.example.entity.User;
import com.example.usercase.port.CheckAccessDsGateway;
import com.example.usercase.port.InventoryDsGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckAccessJPADsGateway implements CheckAccessDsGateway<InventoryApplication> {

    @Autowired
    private InventoryDsGateway inventoryDsGateway;

    @Override
    public void checkApplicationAccess(InventoryApplication application, User user) {
        inventoryDsGateway.getApplication(application.getId().toString(), user);

    }

    @Override
    public void checkApplicationAccess(String appID, User user) {
        inventoryDsGateway.getApplication(appID, user);
    }
}
