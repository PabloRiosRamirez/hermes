package online.grisk.hermes.domain.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class ServiceActivator {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    protected HttpEntity<Object> buildHttpEntity(Map<String, Object> payload, Map<String, Object> headers, online.grisk.hermes.domain.entity.ServiceActivator serviceActivator) {
        HttpHeaders httpHeaders = createHttpHeaders(headers, serviceActivator);
        return new HttpEntity<>(payload, httpHeaders);
    }

    private HttpHeaders createHttpHeaders(Map<String, Object> mapHeaders, online.grisk.hermes.domain.entity.ServiceActivator serviceActivator) {
        HttpHeaders httpHeaders = new HttpHeaders();
        mapHeaders.forEach((k, v) -> {
            httpHeaders.add(k.toLowerCase(), k);
            httpHeaders.setBasicAuth(serviceActivator.getServiceUsername(), serviceActivator.getServicePassword());
        });
        return httpHeaders;
    }

    protected ResponseEntity<JsonNode> executeRequest(online.grisk.hermes.domain.entity.ServiceActivator serviceActivator, HttpEntity<Object> httpEntity) throws Exception {
        ResponseEntity response;
        try {
            response = this.restTemplate.exchange(serviceActivator.getUri(), HttpMethod.POST, httpEntity, JsonNode.class);
        } catch (RestClientResponseException e) {
            throw new Exception(this.buildErrorMessage(serviceActivator.getServiceId(), e));
        } catch (Exception e) {
            throw new Exception();
        }
        return response;
    }


    private String buildErrorMessage(String nameServiceActivator, RestClientResponseException e) throws Exception {
        JsonNode jsonNode = this.objectMapper.readTree(e.getResponseBodyAsString());
        return jsonNode.get("message") != null ? String.format("An error ocurred executing %s service activator: %S (STATUS: %d)", nameServiceActivator, jsonNode.get("message").asText(), e.getRawStatusCode()) : String.format("An error ocurred executing %s service activator: %S (STATUS: %d)", nameServiceActivator, e.getMessage(), e.getRawStatusCode());
    }

    protected void addServiceResponseToResponseMap(Map<String, Object> payload, ResponseEntity<JsonNode> response, String serviceId) {
        payload.put(serviceId.toUpperCase() + "_" + "RESPONSE", response.getBody());
        payload.put("CURRENT_RESPONSE", response.getBody());
        payload.put("STATUS", response.getStatusCodeValue());
    }
}