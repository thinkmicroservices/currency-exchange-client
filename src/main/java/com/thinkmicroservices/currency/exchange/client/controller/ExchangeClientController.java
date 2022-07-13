package com.thinkmicroservices.currency.exchange.client.controller;

import com.thinkmicroservices.currency.exchange.client.model.CurrencyExchangeRequest;
import com.thinkmicroservices.currency.exchange.client.model.CurrencyExchangeResponse;
import com.thinkmicroservices.currency.exchange.client.service.MessagingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/currency")
@Slf4j
public class ExchangeClientController {

    @Value("${spring.application.name}")
    private String serviceName;
    
    @Autowired
    MessagingService messagingService;

    @PostMapping("/convert")
    public CurrencyExchangeResponse convertCurrency(CurrencyExchangeRequest request) throws Exception {
        
        log.trace("{} handling request:{}", serviceName, request);

        return messagingService.convertCurrency(request);

    }
}
