package com.adobe.engineering.numberconverter;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Application class responsible for running the Number Converter Service.
 */
@OpenAPIDefinition(
    info = @Info(title = "Number Converter",
        version = "${api.version}",
        contact = @Contact(
            name = "Mark Daugherty",
            email = "mark.r.daugherty@gmail.com"
        )
    )
)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
