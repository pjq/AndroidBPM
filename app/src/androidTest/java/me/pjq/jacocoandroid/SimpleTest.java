package me.pjq.jacocoandroid;

import android.app.Activity;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;

import static java.util.EnumSet.allOf;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.text.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.both;


/**
 * Created by pengjianqing on 7/16/14.
 */
@Config(manifest = "./src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class SimpleTest {
    Activity activity;

    @Before
    public void setUp() throws Exception {
//        activity = Robolectric.buildActivity(MyActivity.class).create().visible().get();
    }

    @SmallTest
    public void test1() throws Exception {
        String hello = "";
        assertThat(hello, equalTo("Hello World!"));

    }

//    @SmallTest
//    public void test2() throws Exception {

//        Button btnLaunch = null;
        // Not Null
//        assertThat(btnLaunch, notNullValue());
// Equals
//        assertThat((String) btnLaunch.getText(), equalTo("Launch"));
// All Conditions
        //   assertThat("myValue", allOf(startsWith("my"), containsString("Val")));
// Any Conditions
//        assertThat("myValue", anyOf(startsWith("foo"), containsString("Val")));
// Both
//        assertThat("fab", both(containsString("a")).and(containsString("b")));
// Kind Of
        //   assertThat(activity, isA(MyActivity.class));
// Array Inclusion
//        assertThat(Arrays.asList("foo", "bar"), hasItem("bar"));
// IsNull
        //   assertThat(activity, is(nullValue());
//    }

    @After
    public void tearDown() throws Exception {
//        activity.finish();
    }

}
