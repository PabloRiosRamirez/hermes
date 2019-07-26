package online.grisk.hermes.domain.service;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class EmailService {

    @Autowired
    private UUID uuid;

    @Autowired
    private MailjetClient mailjetClient;

    @Value("${MAILJET_TO}")
    private String mailTo;

    @Value("${MAILJET_NAME}")
    private String nameTo;

    protected Map invoke(String address,
                       String subject,
                       String text,
                       String html) {
        JSONObject message = new JSONObject();
        message.put(Emailv31.Message.FROM, new JSONObject()
                .put(Emailv31.Message.EMAIL, mailTo)
                .put(Emailv31.Message.NAME, nameTo)
        )
                .put(Emailv31.Message.SUBJECT, subject)
                .put(Emailv31.Message.TEXTPART, text)
                .put(Emailv31.Message.HTMLPART, html)
                .put(Emailv31.Message.TO, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.EMAIL, address)));
        MailjetRequest mailjetRequest = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES, (new JSONArray()).put(message));

        return sendEmail(mailjetRequest);
    }

    private Map sendEmail(MailjetRequest mailjetRequest) {
        Map response = new HashMap();
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", "An unexpected problem occurred.");
        try {
            MailjetResponse mailjetResponse = mailjetClient.post(mailjetRequest);
            if (mailjetResponse.getStatus() == 200) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "This email has been sent successfully.");
                return response;
            } else {
                return response;
            }
        } catch (Exception e) {
            return response;
        }
    }
}
