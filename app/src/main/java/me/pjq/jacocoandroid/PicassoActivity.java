package me.pjq.jacocoandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by pengjianqing on 7/21/14.
 */
public class PicassoActivity extends Activity{
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.picasso);

        imageView = (ImageView) findViewById(R.id.imageView);

        String url = "http://pjq.me/wp-content/uploads/2014/07/200662111018610602.png";
        Picasso.with(getApplicationContext()).setIndicatorsEnabled(true);
        Picasso.with(getApplicationContext()).load(url).into(imageView);
    }
}
