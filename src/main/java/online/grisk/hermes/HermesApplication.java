package online.grisk.hermes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.resource.Emailv31;

import java.util.UUID;

@EnableEurekaClient
@SpringBootApplication
public class HermesApplication {

    public static void main(String[] args) {
        SpringApplication.run(HermesApplication.class, args);
    }

    @Value("${api.mail.key.public}")
    String keyPublic;

    @Value("${api.mail.key.secret}")
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
