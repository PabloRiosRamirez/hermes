package online.grisk.hermes.restcontroller;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import online.grisk.hermes.dto.ResponseRestAPI;
import online.grisk.hermes.entity.Email;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class MainController {

    private static final Log logger = LogFactory.getLog(MainController.class);

    @Autowired
    UUID uuid;

    @Autowired
    MailjetClient mailjetClient;

    @Value("${MAILJET_TO}")
    String mailTo;

    @Value("${MAILJET_NAME}")
    String nameTo;

    @PostMapping(value = "/v1/rest/mail/send")
    public ResponseEntity<ResponseRestAPI> sendEmail(@RequestBody Email email) {
        ResponseEntity<ResponseRestAPI> problemaInesperado = new ResponseEntity<ResponseRestAPI>(new ResponseRestAPI(uuid,
                HttpStatus.INTERNAL_SERVER_ERROR, "Ha ocurrido un problema interno, no se ha podido enviar correctamente el correo electrónico.", new Date(), null),
                HttpStatus.INTERNAL_SERVER_ERROR);
        try {
            logger.info("Entrando a SendEmail");
            logger.info("Email " +email.toString());
            MailjetRequest emailRequest;
            MailjetResponse response;

            JSONObject message = new JSONObject();
            message.put(Emailv31.Message.FROM, new JSONObject()
                    .put(Emailv31.Message.EMAIL, mailTo)
                    .put(Emailv31.Message.NAME, nameTo)
            )
                    .put(Emailv31.Message.SUBJECT, email.getSubject())
                    .put(Emailv31.Message.TEXTPART, email.getText())
                    .put(Emailv31.Message.HTMLPART, email.getHtml())
                    .put(Emailv31.Message.TO, new JSONArray()
                            .put(new JSONObject()
                                    .put(Emailv31.Message.EMAIL, email.getAddress())));
            emailRequest = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES, (new JSONArray()).put(message));
            logger.info("Enviando a MailJet");
            response = mailjetClient.post(emailRequest);
            logger.info("Respondio a MailJet");
            logger.info("response " +response.toString());
            logger.info("response status " +response.getStatus());
            if (response.getStatus() == 200) {
                return new ResponseEntity<ResponseRestAPI>(new ResponseRestAPI(uuid,
                        HttpStatus.OK,
                        "Correo electrónico enviado correctamente.",
                        new Date(), null),
                        HttpStatus.OK);
            } else {
                return problemaInesperado;
            }
        } catch (Exception e) {
            return problemaInesperado;
        }
    }
}
