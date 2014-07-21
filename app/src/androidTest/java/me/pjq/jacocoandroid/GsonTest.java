package me.pjq.jacocoandroid;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by pengjianqing on 7/21/14.
 */
@RunWith(RobolectricGradleTestRunner.class)
public class GsonTest {

    @Test
    public void testGsonParser() throws Exception {
        String json = "{ \"name\":\"Jack\", \"id\":1001,  \"created_at\": \"2014-07-22 19:12:38\"}";
        GsonBuilder gsonBuilder = new GsonBuilder();

        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        gsonBuilder.setDateFormat(dateFormat);
        Gson gson = gsonBuilder.create();
        GsonActivity.JsonItem item = gson.fromJson(json, GsonActivity.JsonItem.class);

        assertThat("Jack", equalTo(item.name));
        assertThat(1001, equalTo(item.id));
    }
}
