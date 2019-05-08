
package org.springframework.cloud.mesos.metronome.client.model;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

public class Constraint {
	
	private static final Pattern PARSE_REGEX = Pattern.compile("(?<field>[^ ]+) (?<op>[^ ]+)( (?<param>.+))?");
	
	public Constraint(String raw) {
		Matcher matcher = PARSE_REGEX.matcher(raw);
		Assert.isTrue(matcher.matches(), "Could not parse [" + raw + "] as a Marathon constraint (field operator param?)");
		this.attribute = matcher.group("field");
		this.value = matcher.group("param"); // may be null
		this.operator = Constraint.Operator.fromValue(matcher.group("op"));

	}

    /**
     * The attribute name for this constraint.
     * (Required)
     * 
     */
    private String attribute;
    /**
     * The operator for this constraint.
     * (Required)
     * 
     */
    private Constraint.Operator operator;
    /**
     * The value for this constraint.
     * 
     */
    private String value;

    /**
     * The attribute name for this constraint.
     * (Required)
     * 
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * The attribute name for this constraint.
     * (Required)
     * 
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    /**
     * The operator for this constraint.
     * (Required)
     * 
     */
    public Constraint.Operator getOperator() {
        return operator;
    }

    /**
     * The operator for this constraint.
     * (Required)
     * 
     */
    public void setOperator(Constraint.Operator operator) {
        this.operator = operator;
    }

    /**
     * The value for this constraint.
     * 
     */
    public String getValue() {
        return value;
    }

    /**
     * The value for this constraint.
     * 
     */
    public void setValue(String value) {
        this.value = value;
    }

    public enum Operator {

        EQ("EQ"),
        LIKE("LIKE"),
        UNLIKE("UNLIKE");
        private final String value;
        private final static Map<String, Constraint.Operator> CONSTANTS = new HashMap<String, Constraint.Operator>();

        static {
            for (Constraint.Operator c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Operator(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        public static Constraint.Operator fromValue(String value) {
            Constraint.Operator constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
