package com.raviteja.terraform.controller;

import com.raviteja.terraform.entity.TerraformResource;
import com.raviteja.terraform.service.TerraformService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/terraform")
public class TerraformController {
    private final TerraformService service;
    public TerraformController(TerraformService service) { this.service = service; }

    @GetMapping("/init")
    public Map<String, Object> init() { return service.init(); }

    @GetMapping("/plan")
    public Map<String, Object> plan() { return service.plan(); }

    @RequestMapping(value = "/apply", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> apply() { return service.apply(); }

    @RequestMapping(value = "/destroy", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> destroy() { return service.destroy(); }

    @GetMapping("/state")
    public Map<String, Object> state() { return service.state(); }

    @GetMapping("/resources/list")
    public List<TerraformResource> list() { return service.getAll(); }

    @RequestMapping(value = "/resources", method = {RequestMethod.GET, RequestMethod.POST})
    public TerraformResource create(@RequestParam String type,
                                    @RequestParam String name,
                                    @RequestParam(defaultValue = "aws") String provider) {
        return service.addResource(type, name, provider);
    }

    @GetMapping("/validate")
    public Map<String, Object> validate() {
        Map<String, Object> res = new HashMap<>();
        res.put("valid", true);
        res.put("message", "main.tf is valid - 3 resources validated");
        res.put("format", "terraform fmt -check passed");
        return res;
    }

    @GetMapping("/output")
    public Map<String, Object> output() {
        Map<String, Object> res = new HashMap<>();
        res.put("vpc_id", "vpc-0a1b2c3d");
        res.put("instance_ip", "54.12.34.56");
        res.put("s3_bucket", "raviteja-terraform-91");
        res.put("rds_endpoint", "raviteja91.c123.ap-south-1.rds.amazonaws.com");
        return res;
    }
}