package me.pjq.jacocoandroid;

import android.app.Activity;
import me.pjq.jacocoandroid.MyActivity;
import me.pjq.jacocoandroid.R;
import me.pjq.jacocoandroid.RobolectricGradleTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;

import static org.junit.Assert.fail;

import java.lang.Exception;


@RunWith(RobolectricGradleTestRunner.class)
public class RobolectricMyActivityTest {
    Activity activity;

    @Before
    public void setup() throws Exception{
        activity = Robolectric.buildActivity(MyActivity.class).create().visible().get();
    }


    @Test
    public void testShouldHaveApplicationName() throws Exception {
        String name = activity.getResources().getString(R.string.app_name);
        Assert.assertEquals(name, equals("MyActivity"));
    }

    @Test
    public void testSomething() {
        fail("not implemented");
    }

}
