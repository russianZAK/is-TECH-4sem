package is.technologies.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 This class represents the Swagger configuration for the application.This class enables Swagger 2 and defines a Docket bean to configure Swagger for the Employee API.
 The Employee API is selected by specifying the base package of the controllers in the application.
 */

@EnableSwagger2
@Configuration
public class SwaggerConfig {

  /**
   Creates a Docket bean to configure Swagger.
   @return Docket - an instance of the Docket class
   */
  @Bean
  public Docket employeeApi()
  {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("is.technologies.controllers"))
        .build();
  }
}
