package com.adobe.engineering.numberconverter.controllers;

import com.adobe.engineering.numberconverter.models.ConversionResponse;
import com.adobe.engineering.numberconverter.services.NumberConverter;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Controller providing conversion of Integer values into their corresponding Roman numeral representations.
 */
@Controller(value = "/romannumeral")
public class RomanNumeralNumberConverterController {

    /**
     * Request logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(RomanNumeralNumberConverterController.class);

    /**
     * Injected Roman numeral converter service.
     */
    @Inject
    @Named("romanNumeral")
    private NumberConverter converter;

    /**
     * Perform the conversion of an Integer to a Roman numeral given the annotated constraints.  Result will be
     * serialized as JSON to the HTTP response.
     *
     * @param query Integer value between 1 and 3999
     * @return JSON response containing input value and conversion result
     */
    @Get
    @Timed(extraTags = { "controller", "romanNumeral" })
    @Counted(extraTags = { "controller", "romanNumeral" })
    public HttpResponse<ConversionResponse> convert(
        @Min(value = 1, message = "Number must be 1 or greater.")
        @Max(value = 3999, message = "Number must be 3999 or less.")
        final Integer query) {
        LOG.debug("converting query value : {}", query);

        final String output = converter.convert(query);

        final ConversionResponse conversionResponse = new ConversionResponse(query, output);

        LOG.debug("returning conversion response : {}", conversionResponse);

        return HttpResponse.ok(conversionResponse);
    }
}
