package org.vulnepro.nvdproject.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.vulnepro.nvdproject.dto.CVEResponseTO;
import org.vulnepro.nvdproject.nvdutils.CveApiJson20;
import org.vulnepro.nvdproject.nvdutils.DefCveItem;
import org.vulnepro.nvdproject.nvdutils.NVDVulnerability;

import java.util.*;

@Service
public class VulneService {

    private final Map<String, NVDVulnerability> cveDataStore = new HashMap<>();

    @Value("${nvd.api.key}")
    private String apiKey;

    @Value("${nvd.api.url}")
    private String apiUrl;

    private RestTemplate restTemplate = new RestTemplate();


    public CVEResponseTO addCves(String cveId, boolean skipError) throws Exception {
        String[] cves = cveId.replaceAll("\\s+", "").split(",");
        StringBuilder errorMessages = new StringBuilder();
        StringBuilder responseMessage = new StringBuilder("CVE OK: {cveOK}. CVE KO: {cveKO}.");

        Map<String, NVDVulnerability> tempDataStore = new HashMap<>();
        int unprocessedCves = 0;
        List<String> failedCveIds = new ArrayList<>();

        for (String cveIdStr : cves) {
            try {
                // Fetch CVE data from NVD
                CveApiJson20 response = fetchCveFromNvd(cveIdStr);

                // Check if the response contains vulnerabilities
                if (!hasValidVulnerabilities(response)) {
                    handleCveError(cveIdStr, "No vulnerabilities found", skipError, errorMessages, failedCveIds);
                    unprocessedCves++;
                    continue;
                }

                // Check if the CVE already exists
                if (cveDataStore.containsKey(cveIdStr)) {
                    handleCveError(cveIdStr, "already exists", skipError, errorMessages, failedCveIds);
                    unprocessedCves++;
                    continue;
                }

                // If all checks passed, add the CVE to the temp store
                NVDVulnerability nvdVulnerability = createNvdVulnerability(cveIdStr, response);
                tempDataStore.put(cveIdStr, nvdVulnerability);

            } catch (Exception e) {
                String errorMessage = e.getMessage();
                if (errorMessage != null) {
                    if (errorMessage.contains("403 Forbidden")) {
                        errorMessage = "Bad CVE ID or access forbidden by NVD.";
                    } else if (errorMessage.contains("404")) {
                        errorMessage = "CVE not found in the NVD database.";
                    } else if (errorMessage.contains("[no body]")) {
                        errorMessage = "Empty response body from NVD, possibly a bad CVE ID.";
                    }
                }
                handleCveError(cveIdStr, errorMessage, skipError, errorMessages, failedCveIds);
                unprocessedCves++;
                if (!skipError) {
                    throw new Exception(errorMessages.toString());
                }
            }
        }

        // Update the main data store with the new or updated data
        cveDataStore.putAll(tempDataStore);

        // Replace placeholders in the response message
        String finalMessage = responseMessage.toString()
                .replace("{cveOK}", String.valueOf(tempDataStore.size()))
                .replace("{cveKO}", String.valueOf(unprocessedCves));

        // Append failed CVE IDs to the error message
        if (!failedCveIds.isEmpty()) {
            finalMessage += " Failed CVEs: " + String.join(", ", failedCveIds) + ".";
        }

        // If there are any errors and skipError is true, append the accumulated errors
        if (unprocessedCves > 0 && skipError) {
            finalMessage += " " + errorMessages.toString();
        }

        return new CVEResponseTO(finalMessage, tempDataStore);
    }

    private boolean hasValidVulnerabilities(CveApiJson20 response) {
        return response != null && response.getVulnerabilities() != null && !response.getVulnerabilities().isEmpty();
    }

    private void handleCveError(String cveId, String errorMessage, boolean skipError, StringBuilder errorMessages, List<String> failedCveIds) throws Exception {
        String fullErrorMessage = "CVE " + cveId + ": " + errorMessage + ".";
        failedCveIds.add(cveId);
        if (skipError) {
            errorMessages.append(fullErrorMessage).append(" ");
        } else {
            throw new Exception(fullErrorMessage);
        }
    }

    private CveApiJson20 fetchCveFromNvd(String cveId) throws Exception {
        String url = apiUrl + "?cveId=" + cveId;// + "&apiKey=" + apiKey;
        return restTemplate.getForObject(url, CveApiJson20.class);
    }

    private NVDVulnerability createNvdVulnerability(String cveIdStr, CveApiJson20 response) {
        NVDVulnerability nvdVulnerability = new NVDVulnerability();
        nvdVulnerability.setName(cveIdStr);
        nvdVulnerability.setResult(response);
        return nvdVulnerability;
    }

    public Map<String, NVDVulnerability> getCveDataStore() {
        return cveDataStore;
    }

    public List<NVDVulnerability> fetchVulnerabilities(String cveId) {
        // Fetch vulnerabilities based on cveId
        return List.of(cveDataStore.get(cveId)); // Return the fetched vulnerabilities
    }

    public void clearAll() {
        cveDataStore.clear();
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}
