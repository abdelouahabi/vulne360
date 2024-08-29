package org.vulnepro.nvdproject.service;

import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    private String apiKey;

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

}
