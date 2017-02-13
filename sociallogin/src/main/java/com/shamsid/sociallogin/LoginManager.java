package com.shamsid.sociallogin;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.shamsid.sociallogin.models.User;
import com.shamsid.sociallogin.utils.Helper;
import com.shamsid.sociallogin.utils.Platforms;

/**
 * Created by shamsheR on 12/02/17.
 */

public class LoginManager  {

  private Platforms mSocialPlatform;
  private Context mAppContext;
  private static LoginManager instance;
  private User mUser;
  private String clientId;

  public String getClientId () {
    return clientId;
  }

  public LoginManager setClientId (String clientId) {
    this.clientId = clientId;
    return this;
  }

  private LoginManager(Context context){
    mAppContext = context;
  }

  public static synchronized LoginManager getInstance(Context context){

    if(instance == null){
      instance = new LoginManager (context);
    }
    return instance;
  }

  public static void init(Application application){
    FacebookSdk.sdkInitialize (application.getApplicationContext ());
  }

  public LoginManager choose(Platforms platform){
      this.mSocialPlatform = platform;
      return this;
  }

  public LoginManager login() {

    switch (mSocialPlatform) {

      case FACEBOOK:
        Intent facebookIntent = new Intent (mAppContext, FacebookActivity.class);
        facebookIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
        mAppContext.startActivity (facebookIntent);
        break;

      case GOOGLE_PLUS:
        Intent googlePlusIntent = new Intent (mAppContext, GooglePlusActivity.class);
        googlePlusIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
        googlePlusIntent.putExtra (Helper.CLIENT_ID,getClientId ());
        mAppContext.startActivity (googlePlusIntent);
        break;
    }
    return this;
  }

  public  void setUserInfo(User user) {
    try{
      this.mUser = (User) user.clone ();
      Log.v ("name",mUser.getFullName ());
    }catch (CloneNotSupportedException e){
      e.printStackTrace ();
    }
  }

  public User getUserInfo(){

    return this.mUser;
  }
}
