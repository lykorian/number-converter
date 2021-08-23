package com.adobe.engineering.numberconverter.controllers;

import com.adobe.engineering.numberconverter.models.ConversionResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static io.micronaut.http.HttpRequest.GET;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
class RomanNumeralNumberConverterControllerTest {

    @Inject
    @Client("/")
    private HttpClient httpClient;

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = { 0, 4000 })
    void testInvalidQuery(Integer query) {
        final HttpClientResponseException httpClientResponseException = assertThrows(HttpClientResponseException.class,
            () -> httpClient.toBlocking().retrieve(GET("/romannumeral?query=" + query), ConversionResponse.class));

        assertEquals(HttpStatus.BAD_REQUEST, httpClientResponseException.getStatus());
    }

    @ParameterizedTest
    @MethodSource("arguments")
    void testValidQuery(Integer query, String expectedResult) {
        final ConversionResponse conversionResponse = httpClient.toBlocking()
            .retrieve(GET("/romannumeral?query=" + query), ConversionResponse.class);

        assertEquals(String.valueOf(query), conversionResponse.getInput());
        assertEquals(expectedResult, conversionResponse.getOutput());
    }

    static Stream<Arguments> arguments() {
        return Stream.of(
            Arguments.of(1, "I"),
            Arguments.of(2, "II"),
            Arguments.of(3999, "MMMCMXCIX")
        );
    }
}