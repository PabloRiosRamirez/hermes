package online.grisk.hermes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.ClientOptions;
import org.springframework.context.annotation.ImportResource;

import java.util.UUID;

@EnableEurekaClient
@SpringBootApplication
@ImportResource("classpath:integration.cfg.xml")
public class HermesApplication {

    public static void main(String[] args) {
        SpringApplication.run(HermesApplication.class, args);
    }

    @Value("${MAILJET_KEY_PUBLIC}")
    String keyPublic;

    @Value("${MAILJET_KEY_SECRET}")
    String keySecret;

    @Bean
    public MailjetClient mailjetClient() {
        return new MailjetClient(keyPublic, keySecret,
                new ClientOptions("v3.1"));
    }

    @Bean
    UUID getUUID() {
        return UUID.randomUUID();
    }

}
