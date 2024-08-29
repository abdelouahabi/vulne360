
package org.vulnepro.nvdproject.nvdutils;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "organization",
    "comment",
    "lastModified"
})
public class VendorComment {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("organization")
    private String organization;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("comment")
    private String comment;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("lastModified")
    private Date lastModified;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("organization")
    public String getOrganization() {
        return organization;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("organization")
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public VendorComment withOrganization(String organization) {
        this.organization = organization;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("comment")
    public void setComment(String comment) {
        this.comment = comment;
    }

    public VendorComment withComment(String comment) {
        this.comment = comment;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("lastModified")
    public Date getLastModified() {
        return lastModified;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("lastModified")
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public VendorComment withLastModified(Date lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(VendorComment.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("organization");
        sb.append('=');
        sb.append(((this.organization == null)?"<null>":this.organization));
        sb.append(',');
        sb.append("comment");
        sb.append('=');
        sb.append(((this.comment == null)?"<null>":this.comment));
        sb.append(',');
        sb.append("lastModified");
        sb.append('=');
        sb.append(((this.lastModified == null)?"<null>":this.lastModified));
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
        result = ((result* 31)+((this.comment == null)? 0 :this.comment.hashCode()));
        result = ((result* 31)+((this.lastModified == null)? 0 :this.lastModified.hashCode()));
        result = ((result* 31)+((this.organization == null)? 0 :this.organization.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof VendorComment) == false) {
            return false;
        }
        VendorComment rhs = ((VendorComment) other);
        return ((((this.comment == rhs.comment)||((this.comment!= null)&&this.comment.equals(rhs.comment)))&&((this.lastModified == rhs.lastModified)||((this.lastModified!= null)&&this.lastModified.equals(rhs.lastModified))))&&((this.organization == rhs.organization)||((this.organization!= null)&&this.organization.equals(rhs.organization))));
    }

}
