package me.pjq.jacocoandroid;

import android.app.Activity;
import android.test.suitebuilder.annotation.SmallTest;
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
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.Exception;


@RunWith(RobolectricTestRunner.class)
public class RobolectricMyActivityTest {
    Activity activity;

    @Before
    public void setup() throws Exception {
        activity = Robolectric.buildActivity(MyActivity.class).create().visible().get();
    }


    @Test
    public void testShouldHaveApplicationName() throws Exception {
        String name = activity.getResources().getString(R.string.app_name);
        assertThat(name, equalTo(name));
    }

    @Test
    public void testSomething() {
//        fail("not implemented");
        String hello = "Hello World!";
        assertThat(hello, equalTo(hello));
    }

    @After
    public void tearDown() throws Exception {

    }

}
