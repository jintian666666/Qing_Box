package cn.gdust.qing_box;

import org.junit.Test;

import java.util.List;

import cn.gdust.qing_box.api.ExpressClient;
import cn.gdust.qing_box.bean.Express;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void expressTest() throws Exception{
        List<Express> expressList;
        ExpressClient client = new ExpressClient();
        expressList =  client.requestApi("75564949090856");

        for (Express express : expressList) {
            System.out.println(express.toString());
        }
    }
}