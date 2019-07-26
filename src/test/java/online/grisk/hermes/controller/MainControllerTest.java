package online.grisk.hermes.controller;

import online.grisk.hermes.integration.gateway.GatewayService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

public class MainControllerTest {

    MainController mainController = new MainController();
    GatewayService gateway;
    Map requestEmail = new HashMap();
    Map requestHeaders = new HashMap();

    @Before
    public void setUp() throws Exception {
        gateway = Mockito.mock(GatewayService.class);
        requestEmail.put("address", "pa.riosramirez@gmail.com");
        requestEmail.put("token", "token-test");

        Map response = new HashMap();
        response.put("STATUS", HttpStatus.OK);
        response.put("MESSAGE", "This email has been sent successfully.");

        Mockito.when(gateway.process(response, requestHeaders)).thenReturn(response);
        ReflectionTestUtils.setField(mainController, "gateway", gateway);
    }

    @Test
    public void sendEmail() {
        mainController.sendEmail(requestEmail, requestHeaders);
    }
}