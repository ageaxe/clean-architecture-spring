package com.example.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class InventoryApplication extends BaseApplication {

    @NonNull
    private String clientApp;

    @NonNull
    private String clientIdentifier;

    @NonNull
    private Integer decommissionFlag;

    private ValidationStatus validationStatus;

    private String espID;

    private Integer uniqueUserCount;

    private String[] ownerUser;

    private String[] ownerGroup;

    @NonNull
    private String protocol;

    @NonNull
    private AudienceType audience;

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


    @RequiredArgsConstructor
    @ToString
    public enum ValidationStatus {
        VALIDATION_CONFIRMED("Validation confirmed"),
        VALIDATION_REQUIRED("Validation required"),
        DECOMMISSION_NOW("Decommission now"),
        DECOMMISSION_IN_FUTURE("Decommission in future"),
        DISABLED("Disabled");

        @Getter
        private final String value;
    }


    @RequiredArgsConstructor
    @ToString
    public enum AudienceType {
        WORKFORCE("workforce"),
        NON_WORKFORCE("non-workforce");
        @Getter
        private final String value;
    }
}
