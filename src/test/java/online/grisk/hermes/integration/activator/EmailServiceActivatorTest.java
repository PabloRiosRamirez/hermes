package online.grisk.hermes.integration.activator;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import online.grisk.hermes.domain.validation.RequestEmail;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class EmailServiceActivatorTest {

    MailjetClient mailjetClient;
    EmailServiceActivator emailServiceActivator = new EmailServiceActivator();
    RequestEmail requestEmail;

    @Before
    public void setUp() throws Exception {
        requestEmail = new RequestEmail("pa.riosramirez@gmail.com", "registerByLogin", "token-test");

        mailjetClient = Mockito.mock(MailjetClient.class);
        MailjetResponse mailjetResponse = Mockito.mock(MailjetResponse.class);
        Mockito.when(mailjetResponse.getStatus()).thenReturn(200);
        Mockito.when(mailjetClient.post(Mockito.any(MailjetRequest.class))).thenReturn(mailjetResponse);

        emailServiceActivator.mailTo = "pa.riosramirez@gmail.com";
        emailServiceActivator.nameTo = "GRisk";

        ReflectionTestUtils.setField(emailServiceActivator, "mailjetClient", mailjetClient);

    }

    @Test
    public void resetPassword() {
        emailServiceActivator.resetPassword(requestEmail);
    }

    @Test
    public void registerUser() {
        emailServiceActivator.registerUser(requestEmail);
    }
}