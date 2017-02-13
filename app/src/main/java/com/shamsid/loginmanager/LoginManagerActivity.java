package com.shamsid.loginmanager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.shamsid.sociallogin.LoginManager;
import com.shamsid.sociallogin.utils.Platforms;

public class LoginManagerActivity extends Activity {

  LoginManager loginManager;

  @Override protected void onCreate (Bundle savedInstanceState) {
    super.onCreate (savedInstanceState);
    setContentView (R.layout.activity_login_manager);
    findViewById (R.id.tv_facebook).setOnClickListener (new View.OnClickListener () {
      @Override public void onClick (View v) {
        loginGooglePlus();
      }
    });


  }

  private void loginFacebook(){

    loginManager = LoginManager.getInstance (this)
        .choose (Platforms.FACEBOOK)
        .login ();

    Log.v ("name",loginManager.getUserInfo ().getFullName ());
    Log.v ("profile_url",loginManager.getUserInfo ().getUserProfileUrl ());
    Log.v ("name",loginManager.getUserInfo ().getEmailAddress ());
    Log.v ("name",loginManager.getUserInfo ().getId ());
  }

  private void loginGooglePlus(){

    loginManager = LoginManager.getInstance (this)
        .setClientId ("AIzaSyBb__LIXSFpaDvroDX9q0KhFvOmHGxZDmU")
        .choose (Platforms.GOOGLE_PLUS)
        .login ();

    Log.v ("name",loginManager.getUserInfo ().getFullName ());
    Log.v ("profile_url",loginManager.getUserInfo ().getUserProfileUrl ());
    Log.v ("name",loginManager.getUserInfo ().getEmailAddress ());
    Log.v ("name",loginManager.getUserInfo ().getId ());
  }
}
