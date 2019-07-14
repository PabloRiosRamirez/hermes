package online.grisk.hermes.controller;

import online.grisk.hermes.domain.RequestEmail;
import online.grisk.hermes.integration.gateway.GatewayService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

public class MainControllerTest {

    MainController mainController;
    GatewayService gatewayService;
    RequestEmail requestEmail;

    @Before
    public void setUp() throws Exception {
        mainController = new MainController();
        gatewayService = Mockito.mock(GatewayService.class);
        requestEmail = new RequestEmail("pa.riosramirez@gmail.com", "registerByLogin", "token-test");

        Map response = new HashMap();
        response.put("status", HttpStatus.OK);
        response.put("message", "Se ha enviado correctamente el email.");

        Mockito.when(gatewayService.process(Mockito.any(Message.class))).thenReturn(MessageBuilder.withPayload(response).build() );
        ReflectionTestUtils.setField(mainController, "gatewayService", gatewayService);
    }

    @Test
    public void sendEmail() {
        mainController.sendEmail(requestEmail);
    }
}