package dev.lykorian.numberconverter.models;

import com.google.common.base.MoreObjects;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.concurrent.Immutable;
import javax.validation.constraints.NotNull;

/**
 * Represents the service response for a number conversion to be serialized as JSON.
 */
@Schema(description = "Service response for a number conversion.")
@Introspected
@Immutable
public final class ConversionResponse {

    private final Integer input;

    private final String output;

    /**
     * Create a new conversion response.
     *
     * @param input input query
     * @param output conversion result
     */
    public ConversionResponse(
        @NotNull
        final Integer input,
        @NotNull
        final String output) {
        this.input = input;
        this.output = output;
    }

    /**
     * Get the input value.
     *
     * @return input value
     */
    @Schema(description = "Input value")
    public String getInput() {
        return String.valueOf(input);
    }

    /**
     * Get the conversion result.
     *
     * @return conversion result
     */
    @Schema(description = "Conversion result")
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
