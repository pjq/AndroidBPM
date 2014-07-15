import me.pjq.jacocoandroid.MyActivity;
import me.pjq.jacocoandroid.R;

import java.lang.Exception;


@RunWith(RobolectricGradleTestRunner.class)
public class MyActivityTest {

    @Test
    public void shouldHaveApplicationName() throws Exception{
        String name = new MyActivity().getResources().getString(R.string.app_name);

        assertThat(name, equalsTo("MyActivity"));

    }

}
