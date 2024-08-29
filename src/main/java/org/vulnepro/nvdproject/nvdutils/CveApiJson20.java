
package org.vulnepro.nvdproject.nvdutils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * JSON Schema for NVD Vulnerability Data API version 2.2.1
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "resultsPerPage",
    "startIndex",
    "totalResults",
    "format",
    "version",
    "timestamp",
    "vulnerabilities"
})
public class CveApiJson20 {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("resultsPerPage")
    private Integer resultsPerPage;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("startIndex")
    private Integer startIndex;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("totalResults")
    private Integer totalResults;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("format")
    private String format;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("version")
    private String version;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("timestamp")
    private Date timestamp;
    /**
     * NVD feed array of CVE
     * (Required)
     * 
     */
    @JsonProperty("vulnerabilities")
    @JsonPropertyDescription("NVD feed array of CVE")
    private List<DefCveItem> vulnerabilities = new ArrayList<DefCveItem>();

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("resultsPerPage")
    public Integer getResultsPerPage() {
        return resultsPerPage;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("resultsPerPage")
    public void setResultsPerPage(Integer resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public CveApiJson20 withResultsPerPage(Integer resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("startIndex")
    public Integer getStartIndex() {
        return startIndex;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("startIndex")
    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public CveApiJson20 withStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("totalResults")
    public Integer getTotalResults() {
        return totalResults;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("totalResults")
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public CveApiJson20 withTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("format")
    public String getFormat() {
        return format;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("format")
    public void setFormat(String format) {
        this.format = format;
    }

    public CveApiJson20 withFormat(String format) {
        this.format = format;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    public CveApiJson20 withVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("timestamp")
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("timestamp")
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public CveApiJson20 withTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * NVD feed array of CVE
     * (Required)
     * 
     */
    @JsonProperty("vulnerabilities")
    public List<DefCveItem> getVulnerabilities() {
        return vulnerabilities;
    }

    /**
     * NVD feed array of CVE
     * (Required)
     * 
     */
    @JsonProperty("vulnerabilities")
    public void setVulnerabilities(List<DefCveItem> vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

    public CveApiJson20 withVulnerabilities(List<DefCveItem> vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CveApiJson20 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("resultsPerPage");
        sb.append('=');
        sb.append(((this.resultsPerPage == null)?"<null>":this.resultsPerPage));
        sb.append(',');
        sb.append("startIndex");
        sb.append('=');
        sb.append(((this.startIndex == null)?"<null>":this.startIndex));
        sb.append(',');
        sb.append("totalResults");
        sb.append('=');
        sb.append(((this.totalResults == null)?"<null>":this.totalResults));
        sb.append(',');
        sb.append("format");
        sb.append('=');
        sb.append(((this.format == null)?"<null>":this.format));
        sb.append(',');
        sb.append("version");
        sb.append('=');
        sb.append(((this.version == null)?"<null>":this.version));
        sb.append(',');
        sb.append("timestamp");
        sb.append('=');
        sb.append(((this.timestamp == null)?"<null>":this.timestamp));
        sb.append(',');
        sb.append("vulnerabilities");
        sb.append('=');
        sb.append(((this.vulnerabilities == null)?"<null>":this.vulnerabilities));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.startIndex == null)? 0 :this.startIndex.hashCode()));
        result = ((result* 31)+((this.totalResults == null)? 0 :this.totalResults.hashCode()));
        result = ((result* 31)+((this.resultsPerPage == null)? 0 :this.resultsPerPage.hashCode()));
        result = ((result* 31)+((this.format == null)? 0 :this.format.hashCode()));
        result = ((result* 31)+((this.vulnerabilities == null)? 0 :this.vulnerabilities.hashCode()));
        result = ((result* 31)+((this.version == null)? 0 :this.version.hashCode()));
        result = ((result* 31)+((this.timestamp == null)? 0 :this.timestamp.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CveApiJson20) == false) {
            return false;
        }
        CveApiJson20 rhs = ((CveApiJson20) other);
        return ((((((((this.startIndex == rhs.startIndex)||((this.startIndex!= null)&&this.startIndex.equals(rhs.startIndex)))&&((this.totalResults == rhs.totalResults)||((this.totalResults!= null)&&this.totalResults.equals(rhs.totalResults))))&&((this.resultsPerPage == rhs.resultsPerPage)||((this.resultsPerPage!= null)&&this.resultsPerPage.equals(rhs.resultsPerPage))))&&((this.format == rhs.format)||((this.format!= null)&&this.format.equals(rhs.format))))&&((this.vulnerabilities == rhs.vulnerabilities)||((this.vulnerabilities!= null)&&this.vulnerabilities.equals(rhs.vulnerabilities))))&&((this.version == rhs.version)||((this.version!= null)&&this.version.equals(rhs.version))))&&((this.timestamp == rhs.timestamp)||((this.timestamp!= null)&&this.timestamp.equals(rhs.timestamp))));
    }

}
