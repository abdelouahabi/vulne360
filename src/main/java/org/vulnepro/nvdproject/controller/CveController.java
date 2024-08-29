package org.vulnepro.nvdproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vulnepro.nvdproject.dto.CVEResponseTO;
import org.vulnepro.nvdproject.service.ScoreCalcService;
import org.vulnepro.nvdproject.service.VulneService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vulne")
public class CveController {

    private final VulneService vulneService;
    private final ScoreCalcService scoreCalcService;

    @Autowired
    public CveController(VulneService vulneService, ScoreCalcService scoreCalcService) {
        this.vulneService = vulneService;
        this.scoreCalcService = scoreCalcService;
    }

    @PostMapping("/cves")
    public ResponseEntity<CVEResponseTO> addCVEs(
            @RequestBody String cve,
            @RequestParam(value = "skiperror", defaultValue = "false") boolean skipError
           ) {
        try {
            CVEResponseTO response = vulneService.addCves(cve, skipError);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new CVEResponseTO(e.getMessage(), null));
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity clearCVEs() {
        vulneService.clearAll();
        return ResponseEntity.ok("CVEs cleared successfully");
    }

    @GetMapping("/score/results")
    public ResponseEntity<Double> getScore(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String accessVector) {
        try {
            double averageScore = scoreCalcService.calculateAvgBaseScoreByYearAndAccessVector(year, accessVector);
            return ResponseEntity.ok(averageScore);
        } catch (Exception e) {
            // Log the exception and return an error response
            e.printStackTrace(); // Replace with proper logging
            return ResponseEntity.status(500).body(null);
        }
    }

}
