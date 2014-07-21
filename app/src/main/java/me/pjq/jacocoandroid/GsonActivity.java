package me.pjq.jacocoandroid;

import android.support.v4.app.FragmentActivity;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by pengjianqing on 7/21/14.
 */
public class GsonActivity extends FragmentActivity{
    public static class JsonItem{
        public String name;
        public int id;

        @SerializedName("created_at")
        public Date date;
    }
}
