package me.pjq.jacocoandroid;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by pengjianqing on 7/21/14.
 */
public class GsonActivity {

    public void parser1(){
        String json = "{ name:\"Jack\", id:1001}";
        JsonItem gson = new Gson().fromJson(json, JsonItem.class);

    }

    public static class JsonItem{
        public String name;
        public int id;

        @SerializedName("created_at")
        public Date date;
    }
}
