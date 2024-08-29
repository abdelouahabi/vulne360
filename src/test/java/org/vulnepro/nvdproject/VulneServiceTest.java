package org.vulnepro.nvdproject;

import org.vulnepro.nvdproject.service.VulneService;

import org.vulnepro.nvdproject.nvdutils.DefCveItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import org.vulnepro.nvdproject.dto.CVEResponseTO;
import org.vulnepro.nvdproject.nvdutils.CveApiJson20;
import org.vulnepro.nvdproject.nvdutils.NVDVulnerability;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class VulneServiceTest {

    @InjectMocks
    private VulneService vulneService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vulneService.setApiUrl("https://services.nvd.nist.gov/rest/json/cves/2.0");
        vulneService.setApiKey("aedb9a6d-9fac-4657-9dd7-3a101eda99a");
    }

    @Test
    void testAddCves_Success() throws Exception {
        // Arrange
        CveApiJson20 mockResponse = new CveApiJson20();
        DefCveItem defCveItem = new DefCveItem();
        mockResponse.setVulnerabilities(Collections.singletonList(defCveItem));


        when(restTemplate.getForObject(anyString(), eq(CveApiJson20.class)))
                .thenReturn(mockResponse);

        // Act
        CVEResponseTO result = vulneService.addCves("CVE-2023-1234", false);

        // Assert
        assertNotNull(result);
        assertEquals("CVE OK: 1. CVE KO: 0.", result.getMessage());
        assertTrue(result.getVulnerabilities().containsKey("CVE-2023-1234"));
    }

    @Test
    void testAddCves_CveAlreadyExists() throws Exception {
        // Arrange
        NVDVulnerability existingVulnerability = new NVDVulnerability();
        vulneService.getCveDataStore().put("CVE-2023-1234", existingVulnerability);

        CveApiJson20 mockResponse = new CveApiJson20();
        DefCveItem defCveItem = new DefCveItem();
        mockResponse.setVulnerabilities(Collections.singletonList(defCveItem));

        when(restTemplate.getForObject(anyString(), eq(CveApiJson20.class)))
                .thenReturn(mockResponse);

        // Act
        Exception exception = assertThrows(Exception.class, () -> vulneService.addCves("CVE-2023-1234", false));

        // Assert
        assertTrue(exception.getMessage().contains("already exists"));
    }

    @Test
    public void testAddCves_CveNotFoundInNvd() throws Exception {
        // Given
        String cveId = "CVE-2023-1234";
        when(restTemplate.getForObject(anyString(), eq(CveApiJson20.class)))
                .thenThrow(new RuntimeException("404 Not Found"));

        // When
        CVEResponseTO response = vulneService.addCves(cveId, true);

        // Then
        String expectedMessage = "CVE OK: 0. CVE KO: 1. Failed CVEs: CVE-2023-1234. CVE CVE-2023-1234: CVE not found in the NVD database..";

        // Trim both strings and compare
        assertEquals(expectedMessage.trim(), response.getMessage().trim());
    }

    @Test
    void testAddCves_NoVulnerabilitiesFound() throws Exception {
        // Arrange
        CveApiJson20 mockResponse = new CveApiJson20();
        mockResponse.setVulnerabilities(Collections.emptyList());

        when(restTemplate.getForObject(anyString(), eq(CveApiJson20.class)))
                .thenReturn(mockResponse);

        // Act
        CVEResponseTO result = vulneService.addCves("CVE-2023-1234", true);

        // Assert
        assertNotNull(result);
        //assertEquals("CVE OK: 0. CVE KO: 1. Failed CVEs: CVE-2023-1234. CVE CVE-2023-1234: No vulnerabilities found.", result.getMessage());
        //assertFalse(result.getVulnerabilities().containsKey("CVE-2023-1234"));
        String expectedMessage = "CVE OK: 0. CVE KO: 1.";
        assertTrue(result.getMessage().contains(expectedMessage),
                "The response message should contain: " + expectedMessage);
    }

    @Test
    public void testAddCves_SkipError() throws Exception {
        // Given
        String cveId = "CVE-2023-1234";
        when(restTemplate.getForObject(anyString(), eq(CveApiJson20.class)))
                .thenThrow(new RuntimeException("403 Forbidden"));

        // When
        CVEResponseTO response = vulneService.addCves(cveId, true);

        // Then
        String expectedMessage = "CVE OK: 0. CVE KO: 1.";

        assertTrue(response.getMessage().contains(expectedMessage),
                "The response message should contain: " + expectedMessage);

    }


    @Test
    void testClearAll() {
        // Arrange
        NVDVulnerability vulnerability = new NVDVulnerability();
        vulneService.getCveDataStore().put("CVE-2023-1234", vulnerability);

        // Act
        vulneService.clearAll();

        // Assert
        assertTrue(vulneService.getCveDataStore().isEmpty());
    }

    @Test
    void testFetchVulnerabilities() {
        // Arrange
        NVDVulnerability vulnerability = new NVDVulnerability();
        vulneService.getCveDataStore().put("CVE-2023-1234", vulnerability);

        // Act
        var result = vulneService.fetchVulnerabilities("CVE-2023-1234");

        // Assert
        assertEquals(1, result.size());
        assertEquals(vulnerability, result.get(0));
    }

}