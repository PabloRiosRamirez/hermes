package online.grisk.hermes;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ImportResource("classpath:integration.cfg.xml")
public class HermesApplicationTests {

    HermesApplication hermesApplication = new HermesApplication();

    @Test
    public void contextLoads() {
    }

    @Test
    public void testBeans() {
        Assert.assertNotNull(hermesApplication.getUUID());
        Assert.assertNotNull(hermesApplication.mailjetClient());
    }

}
