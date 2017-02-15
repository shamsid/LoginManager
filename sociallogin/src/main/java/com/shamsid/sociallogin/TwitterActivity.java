package com.shamsid.sociallogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.shamsid.sociallogin.models.Profile;
import com.shamsid.sociallogin.utils.Helper;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.AccountService;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.Crash;
import retrofit2.Call;

public class TwitterActivity extends Activity {

  private TwitterAuthClient mTwitterAuthClient;

  @Override protected void onCreate (Bundle savedInstanceState) {
    super.onCreate (savedInstanceState);

    String twitterSecret = getIntent ().getStringExtra (Helper.TWITTER_CLIENT_SECRET);
    String twitterPublic = getIntent ().getStringExtra (Helper.TWTITTER_CLIENT);

    TwitterAuthConfig authConfig = new TwitterAuthConfig(twitterPublic, twitterSecret);
    Fabric.with(this, new Twitter (authConfig));
    mTwitterAuthClient = new TwitterAuthClient ();


    mTwitterAuthClient.authorize (this, new Callback<TwitterSession> () {
      @Override public void success (Result<TwitterSession> result) {
        getTwitterDetails(result);
      }

      @Override public void failure (TwitterException exception) {
        com.shamsid.sociallogin.LoginManager.getInstance (TwitterActivity.this).onLoginError (exception);
        finish ();
      }
    });



  }

  @Override protected void onActivityResult (int requestCode, int resultCode, Intent data) {
    super.onActivityResult (requestCode, resultCode, data);
    mTwitterAuthClient.onActivityResult (requestCode, resultCode, data);
  }

  private void getTwitterDetails(Result<TwitterSession> result) {
    final Profile profileInformation = new Profile ();

    TwitterSession twitterSession = result.data;
    profileInformation.setAccessToken (twitterSession.getAuthToken ().token);


    Twitter twitter = Twitter.getInstance ();
    TwitterApiClient api     = twitter.core.getApiClient(twitterSession);
    AccountService service = api.getAccountService();
    Call<User> user    = service.verifyCredentials(true, true);
    user.enqueue(new Callback<User>()
    {
      @Override
      public void success(Result<User> userResult) {
        String name = userResult.data.name;
        String photoUrl   = userResult.data.profileImageUrl.replace("_normal", "");
        String userId = String.valueOf (userResult.data.id);

        profileInformation.setFullName (name);
        profileInformation.setUserProfileUrl (photoUrl);
        profileInformation.setProfileId (userId);

        LoginManager.getInstance (TwitterActivity.this).onLoginSuccess (profileInformation);
        finish ();
      }

      @Override
      public void failure(TwitterException twitterException) {
        LoginManager.getInstance (TwitterActivity.this).onLoginError (twitterException);
        finish ();
      }
    });

  }
}


