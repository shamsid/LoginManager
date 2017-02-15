package com.shamsid.sociallogin;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.shamsid.sociallogin.models.Profile;
import com.shamsid.sociallogin.utils.Helper;
import com.shamsid.sociallogin.utils.Platforms;
import rx.Observable;
import rx.subjects.PublishSubject;

public  class LoginManager {

  private Platforms mSocialPlatform;
  private Context mAppContext;

  @SuppressLint("StaticFieldLeak") private static LoginManager instance;

  private Profile mProfile = null;
  private String clientId;
  private PublishSubject<Profile> userEmitter;

  public String getClientSecretId () {
    return clientSecretId;
  }

  public LoginManager setClientSecretId (String clientSecretId) {
    this.clientSecretId = clientSecretId;
    return this;
  }

  private String clientSecretId;

  public String getClientId () {
    return clientId;
  }

  public LoginManager setClientId (String clientId) {
    this.clientId = clientId;
    return this;
  }

  private LoginManager (Context context) {
    mAppContext = context;
  }

  public static synchronized LoginManager getInstance (Context context) {

    if (instance == null) {
      instance = new LoginManager (context);
    }
    return instance;
  }

  public static void init (Application application) {
    FacebookSdk.sdkInitialize (application.getApplicationContext ());

  }

  public LoginManager choose (Platforms platform) {
    this.mSocialPlatform = platform;
    return this;
  }

  public Observable<Profile> login () throws SocialPlatformNotFound {
    userEmitter = PublishSubject.create ();
    mAppContext.startActivity (getIntent ());
    return userEmitter;
  }

  private Intent getIntent ()  throws SocialPlatformNotFound{
    Intent socialIntent;
    switch (mSocialPlatform) {

      case FACEBOOK:
        socialIntent = new Intent (mAppContext, FacebookActivity.class);
        socialIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
        break;

      case GOOGLE_PLUS:
        socialIntent = new Intent (mAppContext, GooglePlusActivity.class);
        socialIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
        socialIntent.putExtra (Helper.CLIENT_ID, getClientId ());
        break;

      case TWITTER:
        socialIntent = new Intent (mAppContext,TwitterActivity.class);
        socialIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
        socialIntent.putExtra (Helper.TWTITTER_CLIENT,getClientId ());
        socialIntent.putExtra (Helper.TWITTER_CLIENT_SECRET,getClientSecretId());
        break;

      case LINKEDIN:
        socialIntent = new Intent (mAppContext,LinkedInActivity.class);
        socialIntent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
        break;

      default:
        throw new SocialPlatformNotFound ();
    }
    return socialIntent;
  }

  void onLoginSuccess(Profile socialProfile) {
    if (userEmitter != null) {
      Profile copy = new Profile (socialProfile);
      userEmitter.onNext(copy);
      userEmitter.onCompleted();
    }
  }

  void onLoginError(Throwable throwable) {
    if (userEmitter != null) {
      Throwable copy = new Throwable(throwable);
      userEmitter.onError(copy);
    }
  }

  void onLoginCancel() {
    if (userEmitter != null) {
      userEmitter.onCompleted();
    }
  }
}
