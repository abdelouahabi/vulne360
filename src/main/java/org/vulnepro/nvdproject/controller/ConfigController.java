package org.vulnepro.nvdproject.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/apikey")
public class ConfigController {

    @Value("${nvd.api.key:}")
    private String apikey;


    @PostMapping
    public ResponseEntity<Void> setApiKey(@RequestBody String apiKey) {
        this.apikey = apiKey;
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<String> getApiKey() {
        return ResponseEntity.ok(apikey);
    }

}