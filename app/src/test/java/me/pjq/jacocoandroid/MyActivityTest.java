package me.pjq.jacocoandroid;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import dalvik.annotation.TestTargetClass;
import me.pjq.jacocoandroid.MyActivity;

import java.lang.Exception;


@RunWith(RobolectricGradleTestRunner.class)
public class MyActivityTest {

    @Test
    public void shouldHaveApplicationName() throws Exception{
        String name = new MyActivity().getResources().getString(R.string.app_name);

        assertThat(name, equalsTo("MyActivity"));

    }

}
