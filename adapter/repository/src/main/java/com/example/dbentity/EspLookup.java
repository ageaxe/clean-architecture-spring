package com.example.dbentity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "esp_lookup")
public class EspLookup {

    @Column(name="asg_u_service_domain")
    private String asgUServiceDomain;
    @Column(name="service_u_service_asg")
    private String serviceUServiceAsg;
    @Column(name="bas_u_service_offering")
    private String basUServiceOffering;
    @Column(name="bas_name")
    private String basName;
    @Id
    @Column(name="bas_sys_id")
    private String basSysId;
    @Column(name="bas_application_type")
    private String basApplicationType;
    @Column(name="bas_u_cloud")
    private String basUCloud;
    @Column(name="bas_u_eman_link")
    private String basUEmanLink;
    @Column(name="bas_url")
    private String basUrl;
    @Column(name="bas_operational_status")
    private String basOperationalStatus;
    @Column(name="bas_install_type")
    private String basInstallType;
    @Column(name="bas_u_support_manager")
    private String basUSupportManager;
    @Column(name="bas_u_responsibility_manager")
    private String basUResponsibilityManager;
    @Column(name="offering_owned_by")
    private String offeringOwnedBy;
    @Column(name="service_owned_by")
    private String serviceOwnedBy;
    @Column(name="service_assigned_to")
    private String serviceAssignedTo;
    @Column(name="service_u_service_architect")
    private String serviceUServiceArchitect;
    @Column(name="service_u_service_security_prime")
    private String serviceUServiceSecurityPrime;
    @Column(name="asg_u_portfolio_manager")
    private String asgUPortfolioManager;
    @Column(name="asg_owned_by")
    private String asgOwnedBy;
    @Column(name="asg_u_chief_architect")
    private String asgUChiefArchitect;
}
