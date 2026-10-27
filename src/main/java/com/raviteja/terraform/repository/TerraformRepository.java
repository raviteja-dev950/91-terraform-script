package com.raviteja.terraform.repository;

import com.raviteja.terraform.entity.TerraformResource;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TerraformRepository extends JpaRepository<TerraformResource, Long> {
    List<TerraformResource> findByStatus(String status);
    List<TerraformResource> findByResourceType(String resourceType);
}