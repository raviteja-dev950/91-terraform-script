package com.raviteja.terraform.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "terraform_resources")
public class TerraformResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String resourceType;
    private String resourceName;
    private String provider;
    private String status;
    private String tfState;
    private String config;

    public TerraformResource() {}
    public TerraformResource(String resourceType, String resourceName, String provider, String status) {
        this.resourceType = resourceType;
        this.resourceName = resourceName;
        this.provider = provider;
        this.status = status;
        this.tfState = "managed";
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getResourceType() { return resourceType; }
    public void setResourceType(String resourceType) { this.resourceType = resourceType; }
    public String getResourceName() { return resourceName; }
    public void setResourceName(String resourceName) { this.resourceName = resourceName; }
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTfState() { return tfState; }
    public void setTfState(String tfState) { this.tfState = tfState; }
    public String getConfig() { return config; }
    public void setConfig(String config) { this.config = config; }
}