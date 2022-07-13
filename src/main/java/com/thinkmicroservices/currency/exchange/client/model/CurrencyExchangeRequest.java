 
package com.thinkmicroservices.currency.exchange.client.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
 

@Data
@NoArgsConstructor
@AllArgsConstructor
 
public class CurrencyExchangeRequest {
   
    ISO4217CurrencyCode localCurrency; 
    ISO4217CurrencyCode targetCurrency; 
    BigDecimal localCurrencyValue; 
  }
 
 
 