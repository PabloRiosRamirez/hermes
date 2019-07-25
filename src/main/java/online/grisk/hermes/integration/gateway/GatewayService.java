package online.grisk.hermes.integration.gateway;

import org.springframework.integration.annotation.Gateway;

import java.util.Map;

public interface GatewayService {
    @Gateway
    Map process(Map payload, Map headers);
}
