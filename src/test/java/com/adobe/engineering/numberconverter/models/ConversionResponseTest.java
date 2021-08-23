package com.adobe.engineering.numberconverter.models;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class ConversionResponseTest {

    @ParameterizedTest
    @MethodSource("arguments")
    void testInput(Integer input, String output) {
        final ConversionResponse conversionResponse = new ConversionResponse(input, output);

        assertEquals(String.valueOf(input), conversionResponse.getInput());
        assertEquals(output, conversionResponse.getOutput());
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void testToString(Integer input, String output) {
        final ConversionResponse conversionResponse = new ConversionResponse(input, output);

        final String toString = new StringBuilder(ConversionResponse.class.getSimpleName())
            .append("{")
            .append("input=")
            .append(input)
            .append(", ")
            .append("output=")
            .append(output)
            .append("}")
            .toString();

        assertEquals(toString, conversionResponse.toString());
    }

    static Stream<Arguments> arguments() {
        return Stream.of(
            Arguments.of(1, "I"),
            Arguments.of(2, "II")
        );
    }
}