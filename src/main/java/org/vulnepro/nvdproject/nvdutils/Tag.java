
package org.vulnepro.nvdproject.nvdutils;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Tag {

    UNSUPPORTED_WHEN_ASSIGNED("unsupported-when-assigned"),
    EXCLUSIVELY_HOSTED_SERVICE("exclusively-hosted-service"),
    DISPUTED("disputed");
    private final String value;
    private final static Map<String, Tag> CONSTANTS = new HashMap<String, Tag>();

    static {
        for (Tag c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    private Tag(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

    @JsonCreator
    public static Tag fromValue(String value) {
        Tag constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
