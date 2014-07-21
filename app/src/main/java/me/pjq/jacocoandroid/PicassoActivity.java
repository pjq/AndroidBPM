package me.pjq.jacocoandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by pengjianqing on 7/21/14.
 */
@EActivity(R.layout.picasso)
public class PicassoActivity extends Activity{
    @ViewById
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()){
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @AfterViews
    void afterViews(){
        String url = "http://pjq.me/wp-content/uploads/2014/07/200662111018610602.png";
        Picasso.with(getApplicationContext()).setIndicatorsEnabled(true);
        Picasso.with(getApplicationContext()).load(url).into(imageView);
    }
}
