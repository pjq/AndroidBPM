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
import static org.junit.Assert.assertTrue;
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
        activity = Robolectric.buildActivity(MyActivity.class).create().visible().get();
    }

    @Test
    public void testSomething() throws Exception {
        Activity activity = Robolectric.buildActivity(MyActivity.class).create().get();
        assertTrue(activity != null);
    }
}
