package com.adobe.engineering.numberconverter;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Application class responsible for running the Number Converter Service.
 */
@OpenAPIDefinition(info = @Info(title = "Number Converter Service"))
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
