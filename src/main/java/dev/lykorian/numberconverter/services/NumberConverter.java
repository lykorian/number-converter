package dev.lykorian.numberconverter.services;

/**
 * Service definition for number converters that accept an input number and convert to an alternative representation.
 */
public interface NumberConverter {

    /**
     * Convert an Integer value to another format.
     *
     * @param value Integer value
     * @return conversion result
     */
    String convert(Integer value);
}
