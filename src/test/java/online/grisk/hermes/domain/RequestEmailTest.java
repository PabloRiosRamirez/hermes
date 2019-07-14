package online.grisk.hermes.domain;

import org.junit.Before;
import org.junit.Test;

public class RequestEmailTest {

    RequestEmail requestEmail;

    @Before
    public void setUp() throws Exception {
        requestEmail = new RequestEmail("pa.riosramirez@gmail.com", "registerByLogin", "token-test");
    }

    @Test
    public void getAddress() {
        requestEmail.getAddress();
    }

    @Test
    public void getStep() {
        requestEmail.getStep();
    }

    @Test
    public void getToken() {
        requestEmail.getToken();
    }
}