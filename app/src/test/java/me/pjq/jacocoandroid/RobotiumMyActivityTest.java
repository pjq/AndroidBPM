package me.pjq.jacocoandroid;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
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
import com.robotium.solo.Solo;

import java.lang.Exception;
import java.lang.Override;


public class MyActivityTest extends ActivityInstrumentationTestCase2<MyActivity>{
    private Solo solo;

    public MyActivityTest(){
        super(MyActivityTest.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testAppName() throws Exception{
    }

    @Override
    public void tearDown throws Exception{
        solo.finishOpenedActivities();
    }

}
