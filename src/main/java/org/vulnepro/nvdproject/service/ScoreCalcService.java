package org.vulnepro.nvdproject.service;

import org.springframework.stereotype.Service;
import org.vulnepro.nvdproject.nvdutils.DefCveItem;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Service
public class ScoreCalcService {

    private final VulneService vulneService;
    private final Map<String, Double> cveScores = new HashMap<>();

    public ScoreCalcService(VulneService vulneService) {
        this.vulneService = vulneService;
    }


    public double calculateAvgBaseScoreByYearAndAccessVector(Integer year, String accessVector) {
        return vulneService.getCveDataStore().values().stream()
                .flatMap(nvdVulnerability -> nvdVulnerability.getResult().getVulnerabilities().stream())
                .map(DefCveItem::getCve)
                .filter(defCveItem -> defCveItem.getMetrics() != null)
                .filter(defCveItem -> {
                    if (year == null) {
                        return true;
                    }
                    try {
                        long publishedTimestamp = defCveItem.getPublished().getTime();
                        LocalDate publishedDate = Instant.ofEpochMilli(publishedTimestamp)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        return publishedDate.getYear() == year;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .flatMap(defCveItem -> defCveItem.getMetrics().getCvssMetricV31().stream())
                .filter(cvssMetricV31 -> cvssMetricV31.getCvssData() != null)
                .filter(cvssMetricV31 -> {
                    if (accessVector == null) {
                        return true;
                    }
                    return accessVector.equals(cvssMetricV31.getCvssData().getAttackVector().name());
                })
                .mapToDouble(cvssMetricV31 -> cvssMetricV31.getCvssData().getBaseScore())
                .average()
                .orElse(0.0);
    }


}
