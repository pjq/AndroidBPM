package me.pjq.jacocoandroid;

import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;

public class RobotiumMyActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public RobotiumMyActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testAppName() throws Exception {
    }

    public void testActivity() throws Exception {
        MainActivity activity = (MainActivity) solo.getCurrentActivity();
        String id = activity.getActivityId();
        assertEquals("aasdfadsf", id);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
