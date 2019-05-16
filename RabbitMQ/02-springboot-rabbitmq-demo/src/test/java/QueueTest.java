import com.lzhpo.Application;
import com.lzhpo.test1.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p> Author：lzhpo </p>
 * <p> Title：</p>
 * <p> Description：</p>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class QueueTest {

    @Autowired
    private Sender sender;

    /**
     * 测试消息队列
     */
    @Test
    public void test1(){
        sender.send("Hello World！");
    }
}
