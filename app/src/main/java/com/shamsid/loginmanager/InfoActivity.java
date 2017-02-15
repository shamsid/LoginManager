package com.shamsid.loginmanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class InfoActivity extends Activity {
  @Override protected void onCreate (Bundle savedInstanceState) {
    super.onCreate (savedInstanceState);
    setContentView (R.layout.activity_show_data);
    String name = getIntent ().getStringExtra ("name");
    String url = getIntent ().getStringExtra ("profile_url");
    ImageView profileImage = (ImageView) findViewById (R.id.img_profile_url);
    TextView userName = (TextView) findViewById (R.id.tv_name);

    userName.setText (name);
    Picasso.with (this).load (url).into (profileImage);

  }
}
