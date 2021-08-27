package dev.lykorian.numberconverter.controllers;

import dev.lykorian.numberconverter.models.ConversionResponse;
import dev.lykorian.numberconverter.services.NumberConverter;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.hateoas.VndError;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
     * Perform the conversion of an Integer to a Roman numeral given the annotated constraints.
     *
     * @param query Integer value between 1 and 3999
     * @return JSON response containing input value and conversion result
     */
    @Get
    @Timed(extraTags = { "controller", "romanNumeral" })
    @Counted(extraTags = { "controller", "romanNumeral" })
    @ApiResponse(
        responseCode = "400",
        description = "Invalid query value",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = VndError.class)
        )
    )
    @Tag(name = "number-converter")
    public HttpResponse<ConversionResponse> convert(
        @Min(value = 1, message = "Number must be 1 or greater.")
        @Max(value = 3999, message = "Number must be 3999 or less.")
        final Integer query) {
        LOG.debug("converting query value : {}", query);

        final ConversionResponse conversionResponse = new ConversionResponse(query, converter.convert(query));

        LOG.info("returning conversion response : {}", conversionResponse);

        return HttpResponse.ok(conversionResponse);
    }
}
