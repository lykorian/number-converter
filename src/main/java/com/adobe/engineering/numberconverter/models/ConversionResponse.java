package com.adobe.engineering.numberconverter.models;

import com.google.common.base.MoreObjects;

/**
 * Represents the service response for a number conversion to be serialized as JSON.
 */
public final class ConversionResponse {

    private final Integer input;

    private final String output;

    /**
     * Create a new conversion response.
     *
     * @param input input query
     * @param output conversion result
     */
    public ConversionResponse(final Integer input, final String output) {
        this.input = input;
        this.output = output;
    }

    /**
     * Get the input value.
     *
     * @return input value
     */
    public String getInput() {
        return String.valueOf(input);
    }

    /**
     * Get the conversion result.
     *
     * @return conversion result
     */
    public String getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("input", input)
            .add("output", output)
            .toString();
    }
}
