package online.grisk.hermes.controller;

import online.grisk.hermes.domain.RequestEmail;
import online.grisk.hermes.integration.gateway.GatewayService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class MainController {

    @Autowired
    GatewayService gatewayService;

    @PostMapping(value = "/v1/rest/api/email")
    public ResponseEntity<Map> sendEmail(@Valid @RequestBody RequestEmail requestEmail) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", new Date());
        Message process = gatewayService.process(MessageBuilder.withPayload(requestEmail).build());
        Map processPayload = (Map) process.getPayload();
        Map processHeaders = process.getHeaders();
        response.put("uuid", processHeaders.getOrDefault("id", UUID.randomUUID()));
        response.put("message", processPayload.getOrDefault("message", ""));
        response.put("status", ((HttpStatus) processPayload.getOrDefault("status", HttpStatus.INTERNAL_SERVER_ERROR)).value());
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
