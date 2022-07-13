 
package com.thinkmicroservices.currency.exchange.client.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author cwoodward
 */
public class SwaggerConfig {
    @Bean
  public OpenAPI currencyExchangeClientAPI() {
      return new OpenAPI()
              .info(new Info().title("Currency Exchange Client API")
              .description("Client front-end service")
              .version("v0.0.1")
              .license(new License().name("Apache 2.0").url("http://springdoc.org")));
  } 
}
