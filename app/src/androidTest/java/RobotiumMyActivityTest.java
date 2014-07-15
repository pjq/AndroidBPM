
import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;
import me.pjq.jacocoandroid.MyActivity;

import java.lang.Exception;

public class RobotiumMyActivityTest extends ActivityInstrumentationTestCase2<MyActivity> {
    private Solo solo;

    public RobotiumMyActivityTest() {
        super(MyActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testAppName() throws Exception {
    }

    public void testActivity() throws Exception {
        MyActivity activity = (MyActivity) solo.getCurrentActivity();
        String id = activity.getActivityId();
        assertEquals("123412", id);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
