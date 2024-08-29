package org.vulnepro.nvdproject.dto;
import org.vulnepro.nvdproject.nvdutils.NVDVulnerability;

import java.util.List;
import java.util.Map;

public class CVEResponseTO {

    private String message;


    private Map<String, NVDVulnerability> vulnerabilities;

    public CVEResponseTO(String message, Map<String, NVDVulnerability> values) {
        this.message = message;
        this.vulnerabilities = values;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public void setVulnerabilities(Map<String, NVDVulnerability> vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, NVDVulnerability> getVulnerabilities() {
        return vulnerabilities;
    }
}
