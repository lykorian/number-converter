package dev.lykorian.numberconverter.services.impl;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class RomanNumeralNumberConverterTest {

    @Inject
    private RomanNumeralNumberConverter numberConverter;

    @ParameterizedTest
    @CsvFileSource(resources = "/conversions.csv")
    void testConvert(Integer value, String result) {
        assertEquals(result, numberConverter.convert(value));
    }
}