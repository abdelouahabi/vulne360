package org.vulnepro.nvdproject.service;

import org.springframework.stereotype.Service;
import org.vulnepro.nvdproject.nvdutils.DefCveItem;
import org.vulnepro.nvdproject.nvdutils.NVDVulnerability;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScoreCalcService {

    private final VulneService vulneService;
    private Map<String, Double> cveScores = new HashMap<>();

    public ScoreCalcService(VulneService vulneService) {
        this.vulneService = vulneService;
    }

    /**
     * Calculates and stores the average base score for CVE items based on a given year and access vector.
     *
     * @param year         The year of CVEs to filter by.
     * @param accessVector The access vector to filter by (e.g., "NETWORK").
     * @return The average base score.
     */

    public double calculateAvgBaseScoreByYearAndAccessVector(Integer year, String accessVector) {
        return vulneService.getCveDataStore().values().stream()
                .flatMap(nvdVulnerability -> nvdVulnerability.getResult().getVulnerabilities().stream())
                .map(DefCveItem::getCve) // Get CveItem from DefCveItem
                .filter(defCveItem -> defCveItem.getMetrics() != null) // Ensure Metrics is not null
                .filter(defCveItem -> {
                    if (year == null) {
                        return true; // Skip year filter if year is null
                    }
                    try {
                        // Convert published Date to long timestamp in milliseconds
                        long publishedTimestamp = defCveItem.getPublished().getTime();
                        LocalDate publishedDate = Instant.ofEpochMilli(publishedTimestamp)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        return publishedDate.getYear() == year; // Filter by year
                    } catch (Exception e) {
                        // Handle conversion error
                        e.printStackTrace();
                        return false;
                    }
                })
                .flatMap(defCveItem -> defCveItem.getMetrics().getCvssMetricV31().stream()) // Flatten CvssMetricV31 list
                .filter(cvssMetricV31 -> cvssMetricV31.getCvssData() != null) // Ensure CvssData is not null
                .filter(cvssMetricV31 -> {
                    if (accessVector == null) {
                        return true; // Skip accessVector filter if accessVector is null
                    }
                    return accessVector.equals(cvssMetricV31.getCvssData().getAttackVector().name()); // Compare with accessVector
                })
                .mapToDouble(cvssMetricV31 -> cvssMetricV31.getCvssData().getBaseScore()) // Map to base score
                .average() // Calculate average
                .orElse(0.0); // Default to 0.0 if no scores are present
    }


}
