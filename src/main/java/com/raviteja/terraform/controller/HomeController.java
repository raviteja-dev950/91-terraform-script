package com.raviteja.terraform.controller;

import com.raviteja.terraform.repository.TerraformRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
public class HomeController {
    private final TerraformRepository repo;
    @Value("${terraform.version}") private String tfVersion;
    @Value("${terraform.workspace}") private String workspace;
    public HomeController(TerraformRepository repo) { this.repo = repo; }

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> res = new HashMap<>();
        res.put("project", "91-terraform-script");
        res.put("owner", "Raviteja");
        res.put("status", "Live - Terraform IaC Simulation");
        res.put("terraformVersion", tfVersion);
        res.put("workspace", workspace);
        res.put("totalResources", repo.count());
        res.put("endpoints", List.of("/terraform/init","/terraform/plan","/terraform/apply","/terraform/state","/terraform/resources?type=aws_instance&name=web","/terraform/validate","/terraform/destroy","/terraform/output"));
        res.put("badge", "91/100 - Tier 9 DevOps");
        return res;
    }
    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> res = new HashMap<>();
        res.put("status", "UP");
        res.put("service", "91-terraform-script");
        res.put("port", "9898");
        return res;
    }
}