package com.adobe.engineering.numberconverter.controllers;

import com.adobe.engineering.numberconverter.models.ConversionResponse;
import com.adobe.engineering.numberconverter.services.NumberConverter;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static io.micronaut.http.HttpRequest.GET;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
class RomanNumeralNumberConverterControllerTest {

    @Inject
    private EmbeddedServer server;

    @Inject
    private NumberConverter numberConverter;

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


}