package online.grisk.hermes.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import online.grisk.hermes.integration.gateway.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping({"/api/email"})
@Api(value = "Consumer API Email")
public class MainController {

    @Autowired
    GatewayService gateway;

    @RequestMapping(method = {RequestMethod.POST})
    @ApiOperation("Execution for send email")
    @ApiResponses({@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 500, message = "Server Error")})
    public ResponseEntity<?> sendEmail(@Payload @RequestBody Map payload,@Headers @RequestHeader Map headers) {
        this.verifyParameters(payload);
        Message build = MessageBuilder.withPayload(payload).copyHeaders(headers).build();
        Map process = gateway.process(build);
        ResponseEntity<Map> response = new ResponseEntity<>(process, HttpStatus.valueOf(Integer.parseInt(process.getOrDefault("status", "500").toString())));
        return response;
    }

    private void verifyParameters(Map<String, Object> payload){
        Assert.notEmpty(payload, "Payload required");
    }
}
