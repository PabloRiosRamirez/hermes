package online.grisk.hermes;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HermesApplicationTests {

    HermesApplication hermesApplication = new HermesApplication();

    @Test
    public void contextLoads() {
        HermesApplication.main(new String[0]);
    }

    @Test
    public void testBeans() {
        Assert.assertNotNull(hermesApplication.keyPublic);
        Assert.assertNotNull(hermesApplication.keySecret);
        Assert.assertNotNull(hermesApplication.getUUID());
        Assert.assertNotNull(hermesApplication.mailjetClient());
    }

}
