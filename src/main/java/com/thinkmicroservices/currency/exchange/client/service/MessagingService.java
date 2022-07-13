package com.thinkmicroservices.currency.exchange.client.service;


import com.thinkmicroservices.currency.exchange.client.model.CurrencyExchangeRequest;
import com.thinkmicroservices.currency.exchange.client.model.CurrencyExchangeResponse;
import org.springframework.stereotype.Service;



/**
 *
 * @author cwoodward
 */

@Service
public interface MessagingService {
     public CurrencyExchangeResponse convertCurrency(CurrencyExchangeRequest currencyExchangeRequestMessage) throws Exception;
}
