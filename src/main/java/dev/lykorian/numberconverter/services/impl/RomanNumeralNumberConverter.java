package dev.lykorian.numberconverter.services.impl;

import com.google.common.collect.ImmutableMap;
import dev.lykorian.numberconverter.services.NumberConverter;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micronaut.cache.annotation.Cacheable;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Map;

/**
 * Number converter implementing conversion of Integer values to Roman numerals.
 */
@Singleton
@Named("romanNumeral")
public class RomanNumeralNumberConverter implements NumberConverter {

    /**
     * Service logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(RomanNumeralNumberConverter.class);

    /**
     * Map of thousands place digits and their corresponding Roman numeral representations.
     */
    private static final Map<Integer, String> THOUSANDS = ImmutableMap.of(
        1, "M",
        2, "MM",
        3, "MMM"
    );

    /**
     * Map of hundreds place digits and their corresponding Roman numeral representations.
     */
    private static final Map<Integer, String> HUNDREDS = new ImmutableMap.Builder<Integer, String>()
        .put(1, "C")
        .put(2, "CC")
        .put(3, "CCC")
        .put(4, "CD")
        .put(5, "D")
        .put(6, "DC")
        .put(7, "DCC")
        .put(8, "DCCC")
        .put(9, "CM")
        .build();

    /**
     * Map of tens digits and their corresponding Roman numeral representations.
     */
    private static final Map<Integer, String> TENS = new ImmutableMap.Builder<Integer, String>()
        .put(1, "X")
        .put(2, "XX")
        .put(3, "XXX")
        .put(4, "XL")
        .put(5, "L")
        .put(6, "LX")
        .put(7, "LXX")
        .put(8, "LXXX")
        .put(9, "XC")
        .build();

    /**
     * Map of ones digits and their corresponding Roman numeral representations.
     */
    private static final Map<Integer, String> ONES = new ImmutableMap.Builder<Integer, String>()
        .put(1, "I")
        .put(2, "II")
        .put(3, "III")
        .put(4, "IV")
        .put(5, "V")
        .put(6, "VI")
        .put(7, "VII")
        .put(8, "VIII")
        .put(9, "IX")
        .build();

    /**
     * Map of place values to conversion maps.
     */
    private static final Map<Integer, Map<Integer, String>> PLACE_VALUE_CONVERSION_MAP = ImmutableMap.of(
        1, ONES,
        2, TENS,
        3, HUNDREDS,
        4, THOUSANDS
    );

    /**
     * Convert an Integer value to the corresponding Roman numeral representation given the provided constraints.
     * <p>
     * NOTE: constraints are replicated here to ensure valid input regardless of where service is consumed.
     * Controllers/consumers are responsible for providing context-appropriate messaging to the client.
     *
     * @param value Integer value
     * @return Roman numeral
     */
    @Override
    @NonNull
    @Timed(extraTags = { "service", "romanNumeral" })
    @Counted(extraTags = { "service", "romanNumeral" })
    @Cacheable("roman-numeral-number-converter")
    public String convert(
        @NonNull
        @Min(1)
        @Max(3999)
        final Integer value) {
        LOG.debug("input value : {}", value);

        final String result = convertValue(String.valueOf(value));

        LOG.debug("returning roman numeral : {}", result);

        return result;
    }

    /**
     * Recursively convert the value for each input digit to the corresponding Roman numeral representation.
     *
     * @param value current value to convert
     * @return converted value for the given digit
     */
    private String convertValue(final String value) {
        final String result;

        if (value == null) {
            result = "";
        } else {
            final Map<Integer, String> conversionMap = PLACE_VALUE_CONVERSION_MAP.get(value.length());

            result = getConvertedValue(conversionMap, value) + convertValue(removeDigit(value));
        }

        LOG.debug("value : {}, result : {}", value, result);

        return result;
    }

    /**
     * Get the Roman numeral representation of the first digit given the provided conversion map and input value.
     *
     * @param conversionMap map of digits to Roman numerals
     * @param value current input value
     * @return Roman numeral for current digit
     */
    private String getConvertedValue(final Map<Integer, String> conversionMap, final String value) {
        final int firstDigitValue = Integer.parseInt(value.substring(0, 1));

        return conversionMap.getOrDefault(firstDigitValue, "");
    }

    /**
     * Remove the leading digit from a String representation of an Integer value.
     *
     * @param value String value of a given Integer
     * @return String representation of the input value with the leading digit removed or null if there is only a single
     * digit
     */
    private String removeDigit(final String value) {
        return value.length() > 1 ? value.substring(1) : null;
    }
}
