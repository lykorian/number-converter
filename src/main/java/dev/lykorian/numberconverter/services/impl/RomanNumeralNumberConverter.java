package dev.lykorian.numberconverter.services.impl;

import dev.lykorian.numberconverter.services.NumberConverter;
import com.google.common.collect.ImmutableMap;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Map;
import java.util.TreeMap;

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
     * Map of thousandths place digits and their corresponding Roman numeral representations.
     */
    private static final Map<String, String> THOUSANDS = ImmutableMap.of(
        "1", "M",
        "2", "MM",
        "3", "MMM"
    );

    /**
     * Map of hundredths place digits and their corresponding Roman numeral representations.
     */
    private static final Map<String, String> HUNDREDS = new ImmutableMap.Builder<String, String>()
        .put("1", "C")
        .put("2", "CC")
        .put("3", "CCC")
        .put("4", "CD")
        .put("5", "D")
        .put("6", "DC")
        .put("7", "DCC")
        .put("8", "DCCC")
        .put("9", "CM")
        .build();

    /**
     * Map of tens digits and their corresponding Roman numeral representations.
     */
    private static final Map<String, String> TENS = new ImmutableMap.Builder<String, String>()
        .put("1", "X")
        .put("2", "XX")
        .put("3", "XXX")
        .put("4", "XL")
        .put("5", "L")
        .put("6", "LX")
        .put("7", "LXX")
        .put("8", "LXXX")
        .put("9", "XC")
        .build();

    /**
     * Map of ones digits and their corresponding Roman numeral representations.
     */
    private static final Map<String, String> ONES = new ImmutableMap.Builder<String, String>()
        .put("1", "I")
        .put("2", "II")
        .put("3", "III")
        .put("4", "IV")
        .put("5", "V")
        .put("6", "VI")
        .put("7", "VII")
        .put("8", "VIII")
        .put("9", "IX")
        .build();

    private static final TreeMap<Integer, String> NUMERAL_MAP = new TreeMap<>();

    static {
        // alt
        NUMERAL_MAP.put(1, "I");
        NUMERAL_MAP.put(5, "V");
        NUMERAL_MAP.put(10, "X");
        NUMERAL_MAP.put(50, "L");
        NUMERAL_MAP.put(100, "C");
        NUMERAL_MAP.put(500, "D");
        NUMERAL_MAP.put(1000, "M");
    }

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
    public String convert(
        @NonNull
        @Min(1)
        @Max(3999)
        final Integer value) {
        final String result = convertValue(String.valueOf(value));

        LOG.info("input value : {}, roman numeral : {}", value, result);

        return result;
    }

    /**
     * Recursively convert the value for each input digit to the corresponding Roman numeral representation.
     *
     * @param value current value to convert
     * @return converted value for the given digit
     */
    private String convertValue(final String value) {
        final int intValue = Integer.parseInt(value);

        final String result;

        if (intValue < 10) {
            result = getConvertedValue(ONES, intValue);
        } else if (intValue < 100) {
            result = getConvertedValue(TENS, intValue) + convertValue(removeDigit(value));
        } else if (intValue < 1000) {
            result = getConvertedValue(HUNDREDS, intValue) + convertValue(removeDigit(value));
        } else {
            result = getConvertedValue(THOUSANDS, intValue) + convertValue(removeDigit(value));
        }

        LOG.debug("int value : {}, result : {}", intValue, result);

        return result;
    }

    /**
     * Get the Roman numeral value from the provided conversion map.
     *
     * @param conversionMap map of digits to Roman numerals
     * @param value current input value
     * @return Roman numeral for current digit
     */
    private String getConvertedValue(final Map<String, String> conversionMap, final Integer value) {
        return conversionMap.getOrDefault(String.valueOf(value).substring(0, 1), "");
    }

    /**
     * Remove the leading digit from a String representation of an Integer value.
     *
     * @param value String value of a given Integer
     * @return String representation of the input value with the leading digit removed
     */
    private String removeDigit(final String value) {
        return value.substring(1);
    }
}
