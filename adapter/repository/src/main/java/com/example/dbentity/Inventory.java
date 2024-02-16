package com.example.dbentity;

import com.example.dbentity.converter.AudienceTypeConverter;
import com.example.dbentity.converter.ValidationStatusConverter;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLHStoreType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory")
@EntityListeners(AuditingEntityListener.class)
@TypeDefs({ @TypeDef(name = "string-array", typeClass = StringArrayType.class),
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
        @TypeDef(name = "hstore", typeClass = PostgreSQLHStoreType.class),
        @TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class) })
public class Inventory extends Auditable<String> {

    @Id
    @Column(name = "id")
    @Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pg-uuid")
    @GenericGenerator(name = "pg-uuid", strategy = "uuid2", parameters = @org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class", value = "com.example.util.PostgreSQLUUIDGenerationStrategy"))
    private UUID id;

    @Column(name = "client_app")
    private String clientApp;
    @Column(name = "client_identifier")

    private String clientIdentifier;
    @Type(type = "string-array")

    @Column(columnDefinition = "text[]")
    private String[] espApplication;

    @Column(name = "decom_flag")
    private Integer decommitionFlag;

    @Column(name = "unique_user_count")
    private Integer uniqueUserCount;

    @NotNull
    @Column(columnDefinition = "validation_status")
    @Convert(converter = ValidationStatusConverter.class)
    private ValidationStatus validationStatus;

    @Type(type = "string-array")
    @Column(columnDefinition = "text[]")

    private String[] ownerUser;
    @Type(type = "string-array")
    @Column(columnDefinition = "text[]")

    private String[] ownerGroup;
    @Column(name = "protocol")

    private String protocol;
    @Column(columnDefinition = "audience_type")
    @Convert(converter = AudienceTypeConverter.class)

    private AudienceType audience;
    @Column(name = "dirty")

    private Boolean dirty;
    @Column(name = "audience_confirmed")

    private Boolean audienceConfirmed;
    @Column(name = "connection_name")

    private String connectionName;
    @Column(name = "unique_user_6mo")

    private String uniqueUser6mo;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "esp_id", referencedColumnName = "bas_sys_id")

    private EspLookup espLookup;
    @Column(name = "usage_flag")
    private Boolean usageFlag;
    @Column(name = "estimates")

    private Integer estimates;
    @Column(name = "platform")

    private String platform;

    @Column(name = "dg_status")
    private String dgStatus;
    @Column(name = "dg_origin")
    private String dgOrigin;
    @Column(name = "usage")
    private Integer usage;
    @Column(name = "estimates_type")
    private String estimatesType;
    @Type(type = "hstore")
    @Column(columnDefinition = "hstore")
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
