package com.adobe.engineering.numberconverter.models;

import com.google.common.base.MoreObjects;

public final class ConversionResponse {

    private final Integer input;

    private final String output;

    public ConversionResponse(final Integer input, final String output) {
        this.input = input;
        this.output = output;
    }

    public String getInput() {
        return String.valueOf(input);
    }

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
