package com.thinkmicroservices.currency.exchange.client.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkmicroservices.currency.exchange.client.config.CurrencyExchangeConfig;
import com.thinkmicroservices.currency.exchange.client.model.CurrencyExchangeRequest;
import com.thinkmicroservices.currency.exchange.client.model.CurrencyExchangeResponse;
import com.thinkmicroservices.currency.exchange.client.service.MessagingService;
import java.util.HashMap;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author cwoodward
 */
@Service
@Slf4j
public class MessagingServiceRabbitMQImpl implements MessagingService {

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;
    
    

    @Override
    public CurrencyExchangeResponse convertCurrency(CurrencyExchangeRequest currencyExchangeRequestMessage) throws Exception {
        
        // Build a new message with the serialized request as the body
        Message newMessage = MessageBuilder
                .withBody(jsonMapper.writeValueAsBytes(currencyExchangeRequestMessage)).build();
        
         log.trace("requestMessage :{}",newMessage);
          
         
        // call rabbit mq with send/receive semantics
        Message response = rabbitTemplate.sendAndReceive(CurrencyExchangeConfig.EXCHANGE, CurrencyExchangeConfig.REQUEST_MESSAGE_QUEUE, newMessage);
        log.trace("raw response:{}",response);
        
        // process the result
        CurrencyExchangeResponse currencyExchangeResponse = null;

        if (response != null) {
            
            // get the correlationId
            String correlationId = newMessage.getMessageProperties().getCorrelationId();
            log.trace("correlationId: {}",correlationId);
            
            // get response header map
            HashMap<String, Object> headers = (HashMap<String, Object>) response.getMessageProperties().getHeaders();
            
            // get the message id from the header
            String msgId = (String) headers.get("spring_returned_message_correlation");
            log.trace("msgId: {}",msgId);
            
            // check the correlationId matches
            if (msgId.equals(correlationId)) {
                
                String responseJson = new String(response.getBody());
                log.trace("response json : {}", responseJson);
                currencyExchangeResponse = jsonMapper.readValue(response.getBody(), CurrencyExchangeResponse.class);
                log.trace("response CurrencyExchangeResponse: {}", currencyExchangeResponse);
                
            }else{
                throw new Exception("Invalid correlation id "+correlationId+". msgId:"+msgId);
            }
        }else{
            log.info("raw result:{}",response);
        }
        
        return currencyExchangeResponse;
    }

}
