package com.shamsid.loginmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.shamsid.sociallogin.LoginManager;
import com.shamsid.sociallogin.SocialPlatformNotFound;
import com.shamsid.sociallogin.utils.Platforms;

public class LoginManagerActivity extends Activity {

  String TAG = LoginManagerActivity.class.getName ();

  @Override protected void onCreate (Bundle savedInstanceState) {
    super.onCreate (savedInstanceState);
    setContentView (R.layout.activity_login_manager);
    findViewById (R.id.tv_facebook).setOnClickListener (new View.OnClickListener () {
      @Override public void onClick (View v) {
        loginFacebook();
      }
    });
    findViewById (R.id.tv_google_plus).setOnClickListener (new View.OnClickListener () {
      @Override public void onClick (View v) {
        loginGooglePlus();
      }
    });

    findViewById (R.id.tv_twitter).setOnClickListener (new View.OnClickListener () {
      @Override public void onClick (View v) {
        loginTwitter ();
      }
    });

    findViewById (R.id.tv_linkedIn).setOnClickListener (new View.OnClickListener () {
      @Override public void onClick (View v) {
        loginLinkedIn ();
      }
    });


  }

  private void loginFacebook(){

    try {
      LoginManager.getInstance (this)
          .choose (Platforms.FACEBOOK)
          .login ()
          .subscribe (socialUser -> {
            Intent intent = new Intent (LoginManagerActivity.this,InfoActivity.class);
            intent.putExtra ("name",socialUser.getFullName ());
            intent.putExtra ("profile_url",socialUser.getUserProfileUrl ());
            startActivity (intent);

          }, error -> {
            Log.d (TAG, "error: " + error.getMessage ());
          });
    }catch (SocialPlatformNotFound spf){
      spf.printStackTrace ();
    }

  }

  private void loginGooglePlus(){
    try {
      LoginManager.getInstance (this)
          .choose (Platforms.GOOGLE_PLUS)
          .setClientId (getString (R.string.google_app_id))
          .login ()
          .subscribe (socialUser -> {
            Intent intent = new Intent (LoginManagerActivity.this,InfoActivity.class);
            intent.putExtra ("name",socialUser.getFullName ());
            intent.putExtra ("profile_url",socialUser.getUserProfileUrl ());
            startActivity (intent);
          }, error -> {
            Log.d (TAG, "error: " + error.getMessage ());
          });
    }catch (SocialPlatformNotFound spf){
      spf.printStackTrace ();
    }
  }

  private void loginTwitter(){

    try {
      LoginManager.getInstance (this)
          .choose (Platforms.TWITTER)
          .setClientId (getString (R.string.twitter_public))
          .setClientSecretId (getString (R.string.twitter_secret))
          .login ()
          .subscribe (socialUser -> {
            Intent intent = new Intent (LoginManagerActivity.this,InfoActivity.class);
            intent.putExtra ("name",socialUser.getFullName ());
            intent.putExtra ("profile_url",socialUser.getUserProfileUrl ());
            startActivity (intent);
          }, error -> {
            Log.d (TAG, "error: " + error.getMessage ());
          });
    }catch (SocialPlatformNotFound spf){
      spf.printStackTrace ();
    }
  }

  private void loginLinkedIn(){

    try{
      LoginManager.getInstance (this)
          .choose (Platforms.LINKEDIN)
          .login ()
          .subscribe (socialUser -> {
            Intent intent = new Intent (LoginManagerActivity.this,InfoActivity.class);
            intent.putExtra ("name",socialUser.getFullName ());
            intent.putExtra ("profile_url",socialUser.getUserProfileUrl ());
            startActivity (intent);
          }, error -> {
            Log.d (TAG, "error: " + error.getMessage ());
          });
    }catch (SocialPlatformNotFound spf){
      spf.printStackTrace ();
    }
  }

}
