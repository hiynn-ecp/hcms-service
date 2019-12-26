import com.hiynn.cms.StartApp;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 基础test 需要被继承
 * @author 张朋
 * @date 2019/10/22 16:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest { }





