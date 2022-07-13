package com.thinkmicroservices.currency.exchange.client;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
 
/**
 * 
 * @author cwoodward
 */
 
@SpringBootApplication
@ComponentScan
@Slf4j
public class CurrencyExchangeClientApplication {

    @Value("${configuration.source:DEFAULT}")
    String configSource;
    
    @Value("${spring.application.name}")
    private String serviceName;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        
             SpringApplication.run(CurrencyExchangeClientApplication.class, args);
    }

 
    /**
     *
     */
    @PostConstruct
    private void displayInfo() {
        
        log.info("Service-Name:{}, configuration.source={}", serviceName, configSource);
    }
       
}
