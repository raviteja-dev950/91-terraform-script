package com.raviteja.terraform.service;

import com.raviteja.terraform.entity.TerraformResource;
import com.raviteja.terraform.repository.TerraformRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TerraformService {
    private final TerraformRepository repo;
    private String workspace = "default";
    private boolean initialized = false;
    private LocalDateTime lastApply;
    private int version = 0;

    public TerraformService(TerraformRepository repo) { this.repo = repo; }

    public Map<String, Object> init() {
        initialized = true;
        Map<String, Object> res = new HashMap<>();
        res.put("message", "Terraform initialized in workspace: " + workspace);
        res.put("terraformVersion", "1.7.0");
        res.put("backend", "local - terraform.tfstate");
        res.put("provider", "aws, random, local");
        res.put("status", "Initialized");
        return res;
    }

    public Map<String, Object> plan() {
        Map<String, Object> res = new HashMap<>();
        if (!initialized) {
            res.put("error", "Run terraform init first");
            res.put("status", "Failed");
            return res;
        }
        List<TerraformResource> pending = repo.findByStatus("planned");
        res.put("add", pending.size());
        res.put("change", 0);
        res.put("destroy", 0);
        res.put("message", "Plan: " + pending.size() + " to add, 0 to change, 0 to destroy");
        res.put("resources", pending);
        return res;
    }

    public Map<String, Object> apply() {
        List<TerraformResource> planned = repo.findByStatus("planned");
        for (TerraformResource r : planned) { r.setStatus("applied"); repo.save(r); }
        version++; lastApply = LocalDateTime.now();
        Map<String, Object> res = new HashMap<>();
        res.put("message", "Apply complete! Resources: " + planned.size() + " added");
        res.put("version", version);
        res.put("timestamp", lastApply);
        res.put("appliedCount", planned.size());
        return res;
    }

    public Map<String, Object> destroy() {
        long count = repo.count(); repo.deleteAll();
        Map<String, Object> res = new HashMap<>();
        res.put("message", "Destroy complete! Resources: " + count + " destroyed");
        res.put("destroyedCount", count);
        return res;
    }

    public Map<String, Object> state() {
        Map<String, Object> res = new HashMap<>();
        res.put("workspace", workspace);
        res.put("version", version);
        res.put("serial", version);
        res.put("terraform_version", "1.7.0");
        res.put("resources", repo.findAll());
        res.put("totalResources", repo.count());
        res.put("lastApply", lastApply);
        return res;
    }

    public TerraformResource addResource(String type, String name, String provider) {
        TerraformResource r = new TerraformResource(type, name, provider, "planned");
        return repo.save(r);
    }
    public List<TerraformResource> getAll() { return repo.findAll(); }
}