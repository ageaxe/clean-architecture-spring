package com.example.inventory;

import com.example.entity.InventoryApplication;
import com.example.BaseApplicationModel;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class InventoryModel extends BaseApplicationModel {

    @NonNull
    private String clientApp;

    @NonNull
    private String clientIdentifier;


    private Integer decommissionFlag;

    @NonNull
    private InventoryApplication.ValidationStatus validationStatus;

    private String espID;

    private Integer uniqueUserCount;

    private String[] ownerUser;

    private String[] ownerGroup;

    @NonNull
    private String protocol;

    @NonNull
    private InventoryApplication.AudienceType audience;

    private Boolean audienceConfirmed;

    private String connectionName;

    private String uniqueUser6mo;

    private Boolean usageFlag;

    private Integer estimates;

    private String platform;

    private String dgStatus;

    private String dgOrigin;

    private Integer usage;

    private String estimatesType;

    private Map<String, String> serverApiId;


    @Getter
    @RequiredArgsConstructor
    @ToString
    public enum ValidationStatus {
        VALIDATION_CONFIRMED("Validation confirmed"),
        VALIDATION_REQUIRED("Validation required"),
        DECOMMISSION_NOW("Decommission now"),
        DECOMMISSION_IN_FUTURE("Decommission in future"),
        DISABLED("Disabled");

        private final String value;
    }


    @Getter
    @RequiredArgsConstructor
    @ToString
    public enum AudienceType {
        WORKFORCE("workforce"),
        NON_WORKFORCE("non-workforce");
        private final String value;

    }
}
