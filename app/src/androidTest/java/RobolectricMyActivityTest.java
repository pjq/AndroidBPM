import me.pjq.jacocoandroid.MyActivity;
import me.pjq.jacocoandroid.R;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.Exception;


@RunWith(RobolectricGradleTestRunner.class)
public class RobolectricMyActivityTest {

    @Test
    public void shouldHaveApplicationName() throws Exception {
        String name = new MyActivity().getResources().getString(R.string.app_name);

//        assert (name,equals("MyActivity"));
//        assertEquals("123412", name);
    }

}
