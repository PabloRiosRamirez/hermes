package online.grisk.hermes.integration.activator;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

public class EmailServiceActivatorTest {

    MailjetClient mailjetClient;
    EmailServiceActivator emailServiceActivator = new EmailServiceActivator();
    Map requestEmail = new HashMap();

    @Before
    public void setUp() throws Exception {
        requestEmail.put("address", "pa.riosramirez@gmail.com");
        requestEmail.put("token", "token-test");

        mailjetClient = Mockito.mock(MailjetClient.class);
        MailjetResponse mailjetResponse = Mockito.mock(MailjetResponse.class);
        Mockito.when(mailjetResponse.getStatus()).thenReturn(200);
        Mockito.when(mailjetClient.post(Mockito.any(MailjetRequest.class))).thenReturn(mailjetResponse);

        emailServiceActivator.setMailTo("pa.riosramirez@gmail.com");
        emailServiceActivator.setNameTo("GRisk");

        ReflectionTestUtils.setField(emailServiceActivator, "mailjetClient", mailjetClient);

    }

    @Test
    public void resetPassword() {
        emailServiceActivator.invokeResetPassword(requestEmail);
    }

    @Test
    public void registerUser() {
        emailServiceActivator.invokeRegisterUser(requestEmail);
    }
}